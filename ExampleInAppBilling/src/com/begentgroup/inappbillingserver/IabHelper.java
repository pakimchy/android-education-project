package com.begentgroup.inappbillingserver;

import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import com.android.vending.billing.IInAppBillingService;

public class IabHelper {

	// Item types
	public static final String ITEM_TYPE_INAPP = "inapp";
	public static final String ITEM_TYPE_SUBS = "subs";

	public static final int IAB_API_VERSION = 3;

	// some fields on the getSkuDetails response bundle
	public static final String GET_SKU_DETAILS_ITEM_LIST = "ITEM_ID_LIST";
	public static final String GET_SKU_DETAILS_ITEM_TYPE_LIST = "ITEM_TYPE_LIST";

	// Billing response codes
	public static final int BILLING_RESPONSE_RESULT_OK = 0;
	public static final int BILLING_RESPONSE_RESULT_USER_CANCELED = 1;
	public static final int BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE = 3;
	public static final int BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE = 4;
	public static final int BILLING_RESPONSE_RESULT_DEVELOPER_ERROR = 5;
	public static final int BILLING_RESPONSE_RESULT_ERROR = 6;
	public static final int BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED = 7;
	public static final int BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED = 8;

	// Keys for the responses from InAppBillingService
	public static final String RESPONSE_CODE = "RESPONSE_CODE";
	public static final String RESPONSE_GET_SKU_DETAILS_LIST = "DETAILS_LIST";
	public static final String RESPONSE_BUY_INTENT = "BUY_INTENT";
	public static final String RESPONSE_INAPP_PURCHASE_DATA = "INAPP_PURCHASE_DATA";
	public static final String RESPONSE_INAPP_SIGNATURE = "INAPP_DATA_SIGNATURE";
	public static final String RESPONSE_INAPP_ITEM_LIST = "INAPP_PURCHASE_ITEM_LIST";
	public static final String RESPONSE_INAPP_PURCHASE_DATA_LIST = "INAPP_PURCHASE_DATA_LIST";
	public static final String RESPONSE_INAPP_SIGNATURE_LIST = "INAPP_DATA_SIGNATURE_LIST";
	public static final String INAPP_CONTINUATION_TOKEN = "INAPP_CONTINUATION_TOKEN";

	// IAB Helper error codes
	public static final int IABHELPER_ERROR_BASE = -1000;
	public static final int IABHELPER_REMOTE_EXCEPTION = -1001;
	public static final int IABHELPER_BAD_RESPONSE = -1002;
	public static final int IABHELPER_VERIFICATION_FAILED = -1003;
	public static final int IABHELPER_SEND_INTENT_FAILED = -1004;
	public static final int IABHELPER_USER_CANCELLED = -1005;
	public static final int IABHELPER_UNKNOWN_PURCHASE_RESPONSE = -1006;
	public static final int IABHELPER_MISSING_TOKEN = -1007;
	public static final int IABHELPER_UNKNOWN_ERROR = -1008;
	public static final int IABHELPER_SUBSCRIPTIONS_NOT_AVAILABLE = -1009;
	public static final int IABHELPER_INVALID_CONSUMPTION = -1010;

	Context mAppContext;

	public interface OnSetupCompleteListener {
		public void onSetupComplete();

		public void onSetupError(String errorMessage);
	}

	public interface OnPurchaseListener {
		public void onSuccess(Purchase purchase);

		public void onError(String message);
	}

	IInAppBillingService mService = null;
	ServiceConnection mConnection = null;
	boolean isSetupDone = false;
	boolean isDisposed = false;
	boolean isSupportInApp = false;
	boolean isSupportSubs = false;

	int mRequestCode;
	OnPurchaseListener mPurchaseListener;
	String mPurchaseItemType;
	boolean isRequest = false;

	public IabHelper(Context context) {
		mAppContext = context;
	}

	public void setup(final OnSetupCompleteListener listener) {
		if (isSetupDone)
			throw new IllegalStateException("IAB helper is already set up.");

		mConnection = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName name) {
				mService = null;
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				if (isDisposed)
					return;
				mService = IInAppBillingService.Stub.asInterface(service);
				String packageName = mAppContext.getPackageName();
				try {
					int response = mService.isBillingSupported(IAB_API_VERSION,
							packageName, ITEM_TYPE_INAPP);
					if (response != BILLING_RESPONSE_RESULT_OK) {
						if (listener != null) {
							listener.onSetupError("Error checking for billing v3 support.");
						}
						isSupportInApp = false;
						return;
					}
					isSupportInApp = true;
					response = mService.isBillingSupported(IAB_API_VERSION,
							packageName, ITEM_TYPE_SUBS);
					if (response == BILLING_RESPONSE_RESULT_OK) {
						isSupportSubs = true;
					}
					isSetupDone = true;

				} catch (RemoteException e) {
					e.printStackTrace();
					if (listener != null) {
						listener.onSetupError("RemoteException while setting up in-app billing.");
					}
					return;
				}

				if (listener != null) {
					listener.onSetupComplete();
				}
			}
		};
		Intent serviceIntent = new Intent(
				"com.android.vending.billing.InAppBillingService.BIND");
		serviceIntent.setPackage("com.android.vending");

		if (!mAppContext.getPackageManager()
				.queryIntentServices(serviceIntent, 0).isEmpty()) {
			mAppContext.bindService(serviceIntent, mConnection,
					Context.BIND_AUTO_CREATE);
		} else {
			if (listener != null) {
				listener.onSetupError("Billing service unavailable on device.");
			}
		}
	}

	public void dispose() {
		isSetupDone = false;
		if (mConnection != null) {
			if (mAppContext != null) {
				mAppContext.unbindService(mConnection);
			}
		}
		isDisposed = true;
		mConnection = null;
		mAppContext = null;
		mService = null;
	}

	public void launchPurchaseFlow(Activity activity, String sku,
			String itemType, int requestCode, OnPurchaseListener listener,
			String payload) {
		checkNotDisposed();
		checkSetupDone("launchPurchaseFlow");
		startRequest();

		if (itemType.equals(ITEM_TYPE_SUBS) && !isSupportSubs) {
			sendPurchaseError(listener, "Subscriptions are not available.");
			endRequest();
			return;
		}

		try {
			Bundle buyIntentBundle = mService.getBuyIntent(IAB_API_VERSION,
					mAppContext.getPackageName(), sku, itemType, payload);
			int response = getResponseCodeFromBundle(buyIntentBundle);
			if (response != BILLING_RESPONSE_RESULT_OK) {
				sendPurchaseError(listener, "Unable to buy item");
				endRequest();
				return;
			}
			PendingIntent pendingIntent = buyIntentBundle
					.getParcelable(RESPONSE_BUY_INTENT);
			mRequestCode = requestCode;
			mPurchaseListener = listener;
			mPurchaseItemType = itemType;
			activity.startIntentSenderForResult(
					pendingIntent.getIntentSender(), requestCode, new Intent(),
					Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
		} catch (RemoteException e) {
			e.printStackTrace();
			endRequest();
			sendPurchaseError(listener, "Failed to send intent.");
		} catch (SendIntentException e) {
			e.printStackTrace();
			endRequest();
			sendPurchaseError(listener,
					"Remote exception while starting purchase flow");
		}
	}

	public boolean handleActivityResult(int requestCode, int resultCode,
			Intent data) {
		if (requestCode != mRequestCode)
			return false;

		checkNotDisposed();
		checkSetupDone("handleActivityResult");
		endRequest();

		if (data == null) {
			sendPurchaseError("Null data in IAB result");
			return true;
		}

		int responseCode = getResponseCodeFromIntent(data);
		String purchaseData = data.getStringExtra(RESPONSE_INAPP_PURCHASE_DATA);
		String dataSignature = data.getStringExtra(RESPONSE_INAPP_SIGNATURE);

		if (resultCode == Activity.RESULT_OK
				&& responseCode == BILLING_RESPONSE_RESULT_OK) {
			if (purchaseData == null || dataSignature == null) {
				sendPurchaseError("IAB returned null purchaseData or dataSignature");
				return true;
			}

			Purchase purchase = null;
			try {
				purchase = new Purchase(mPurchaseItemType, purchaseData,
						dataSignature);
			} catch (JSONException e) {
				e.printStackTrace();
				sendPurchaseError("Failed to parse purchase data.");
			}
			if (mPurchaseListener != null) {
				mPurchaseListener.onSuccess(purchase);
			}
		} else if (resultCode == Activity.RESULT_OK) {
			sendPurchaseError("Problem purchashing item.");
		} else if (resultCode == Activity.RESULT_CANCELED) {
			sendPurchaseError("User canceled.");
		} else {
			sendPurchaseError("Unknown purchase response.");
		}
		return true;
	}

	public interface OnConsumeFinishedListener {
		public void onSuccess(Purchase purchase);

		public void onError(Purchase purchase, String message);
	}

	public void consumeAsync(final Purchase purchase,
			final OnConsumeFinishedListener listener) {
        checkNotDisposed();
        checkSetupDone("consume");
        startRequest();
		new AsyncTask<Void, Integer, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					consume(purchase);
					return true;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				return false;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				endRequest();
				if (result != null && result) {
					listener.onSuccess(purchase);
				} else {
					listener.onError(purchase, "consume error");
				}
			}
		}.execute();
	}

	public void consume(Purchase itemInfo) {
		checkNotDisposed();
		checkSetupDone("consume");

		if (!itemInfo.mItemType.equals(ITEM_TYPE_INAPP)) {
			throw new IllegalArgumentException("Items of type '"
					+ itemInfo.mItemType + "' can't be consumed.");
		}

		try {
			String token = itemInfo.getToken();
			String sku = itemInfo.getSku();
			if (token == null || token.equals("")) {
				throw new IllegalArgumentException(
						"PurchaseInfo is missing token for sku: " + sku + " "
								+ itemInfo);
			}
			int response = mService.consumePurchase(IAB_API_VERSION,
					mAppContext.getPackageName(), token);
			if (response != BILLING_RESPONSE_RESULT_OK) {
				throw new IllegalArgumentException("Error consuming sku " + sku);
			}
		} catch (RemoteException e) {
			throw new IllegalArgumentException(
					"Remote exception while consuming. PurchaseInfo: "
							+ itemInfo, e);
		}
	}

	public interface QueryInventoryListener {
		public void onSuccess(Inventory inventory);
		public void onError(String message);
	}
	
	public void queryInventoryAsync(final QueryInventoryListener listener) {
        checkNotDisposed();
        checkSetupDone("queryInventory");
        startRequest();
		new AsyncTask<Void, Void, Inventory>() {
			@Override
			protected Inventory doInBackground(Void... params) {
				Inventory inv = queryInventory();
				return inv;
			}
			@Override
			protected void onPostExecute(Inventory result) {
				endRequest();
				if (result == null) {
					listener.onError("fail...");
				} else {
					listener.onSuccess(result);
				}
			}
		}.execute();
	}
	public Inventory queryInventory() {
		checkNotDisposed();
		checkSetupDone("queryInventory");

		try {
			Inventory inv = new Inventory();
			int r = queryPurchases(inv, ITEM_TYPE_INAPP);
			if (r != BILLING_RESPONSE_RESULT_OK) {
				return null;
			}
			r = querySkuDetails(ITEM_TYPE_INAPP, inv);
			if (r != BILLING_RESPONSE_RESULT_OK) {
				return null;
			}
			if (isSupportSubs) {
				r = queryPurchases(inv, ITEM_TYPE_SUBS);
				if (r != BILLING_RESPONSE_RESULT_OK) {
					return null;
				}

				r = querySkuDetails(ITEM_TYPE_SUBS, inv);
				if (r != BILLING_RESPONSE_RESULT_OK) {
					return null;
				}
			}

		} catch (RemoteException | JSONException e) {

		}
		return null;
	}

	public int queryPurchases(Inventory inv, String itemType)
			throws RemoteException, JSONException {
		String continueToken = null;
		do {
			Bundle ownedItems = mService.getPurchases(IAB_API_VERSION,
					mAppContext.getPackageName(), itemType, continueToken);
			int response = getResponseCodeFromBundle(ownedItems);
			if (response != BILLING_RESPONSE_RESULT_OK) {
				return response;
			}
			if (!ownedItems.containsKey(RESPONSE_INAPP_ITEM_LIST)
					|| !ownedItems
							.containsKey(RESPONSE_INAPP_PURCHASE_DATA_LIST)
					|| !ownedItems.containsKey(RESPONSE_INAPP_SIGNATURE_LIST)) {
				return IABHELPER_BAD_RESPONSE;
			}

			ArrayList<String> ownedSkus = ownedItems
					.getStringArrayList(RESPONSE_INAPP_ITEM_LIST);
			ArrayList<String> purchaseDataList = ownedItems
					.getStringArrayList(RESPONSE_INAPP_PURCHASE_DATA_LIST);
			ArrayList<String> signatureList = ownedItems
					.getStringArrayList(RESPONSE_INAPP_SIGNATURE_LIST);
			for (int i = 0; i < purchaseDataList.size(); ++i) {
				String purchaseData = purchaseDataList.get(i);
				String signature = signatureList.get(i);
				String sku = ownedSkus.get(i);
				Purchase purchase = new Purchase(itemType, purchaseData,
						signature);
				inv.addPurchase(purchase);
			}

			continueToken = ownedItems.getString(INAPP_CONTINUATION_TOKEN);

		} while (!TextUtils.isEmpty(continueToken));

		return BILLING_RESPONSE_RESULT_OK;
	}

	public int querySkuDetails(String itemType, Inventory inv)
			throws RemoteException, JSONException {
		ArrayList<String> skuList = new ArrayList<String>();
		skuList.addAll(inv.getAllOwnedSkus(itemType));
		if (skuList.size() == 0) {
			return BILLING_RESPONSE_RESULT_OK;
		}
		Bundle querySkus = new Bundle();
		querySkus.putStringArrayList(GET_SKU_DETAILS_ITEM_LIST, skuList);
		Bundle skuDetails = mService.getSkuDetails(3,
				mAppContext.getPackageName(), itemType, querySkus);

		if (!skuDetails.containsKey(RESPONSE_GET_SKU_DETAILS_LIST)) {
			int response = getResponseCodeFromBundle(skuDetails);
			if (response != BILLING_RESPONSE_RESULT_OK) {
				return response;
			} else {
				return IABHELPER_BAD_RESPONSE;
			}
		}

		ArrayList<String> responseList = skuDetails
				.getStringArrayList(RESPONSE_GET_SKU_DETAILS_LIST);

		for (String thisResponse : responseList) {
			SkuDetails d = new SkuDetails(itemType, thisResponse);
			inv.addSkuDetails(d);
		}
		return BILLING_RESPONSE_RESULT_OK;

	}

	private void sendPurchaseError(String message) {
		sendPurchaseError(mPurchaseListener, message);
	}

	private void sendPurchaseError(OnPurchaseListener listener, String message) {
		if (listener != null) {
			listener.onError(message);
		}
	}

	private void startRequest() {
		if (isRequest)
			throw new IllegalStateException(
					"another purchase request is in progress");
		isRequest = true;
	}

	private void endRequest() {
		isRequest = false;
	}

	private void checkNotDisposed() {
		if (isDisposed)
			throw new IllegalStateException(
					"IabHelper was disposed of, so it cannot be used.");
	}

	void checkSetupDone(String operation) {
		if (!isSetupDone) {
			throw new IllegalStateException(
					"IAB helper is not set up. Can't perform operation: "
							+ operation);
		}
	}

	// Workaround to bug where sometimes response codes come as Long instead of
	// Integer
	int getResponseCodeFromBundle(Bundle b) {
		Object o = b.get(RESPONSE_CODE);
		if (o == null) {
			return BILLING_RESPONSE_RESULT_OK;
		} else if (o instanceof Integer)
			return ((Integer) o).intValue();
		else if (o instanceof Long)
			return (int) ((Long) o).longValue();
		else {
			throw new RuntimeException("Unexpected type for response code: "
					+ o.getClass().getName());
		}
	}

	// Workaround to bug where sometimes response codes come as Long instead of
	// Integer
	int getResponseCodeFromIntent(Intent i) {
		return getResponseCodeFromBundle(i.getExtras());
	}

}
