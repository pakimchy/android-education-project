package com.begentgroup.inappbillingserver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.begentgroup.inappbillingserver.IabHelper.OnConsumeFinishedListener;
import com.begentgroup.inappbillingserver.IabHelper.OnPurchaseListener;
import com.begentgroup.inappbillingserver.IabHelper.OnSetupCompleteListener;
import com.begentgroup.inappbillingserver.IabHelper.QueryInventoryListener;
import com.begentgroup.inappbillingserver.NetworkManager.OnResultListener;

public class MainActivity extends ActionBarActivity {

    // SKUs for our products: the premium upgrade (non-consumable) and gas (consumable)
    static final String SKU_PREMIUM = "premium";
    static final String SKU_GAS = "gas";

    // SKU for our subscription (infinite gas)
    static final String SKU_INFINITE_GAS = "infinite_gas";

    static final int RC_REQUEST = 10001;
    
	IabHelper mHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mHelper = new IabHelper(this);
		mHelper.setup(new OnSetupCompleteListener() {
			
			@Override
			public void onSetupError(String errorMessage) {
				
			}
			
			@Override
			public void onSetupComplete() {
				mHelper.queryInventoryAsync(queryListener);
			}
		});
		
		Button btn = (Button)findViewById(R.id.btn_gas);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mHelper.launchPurchaseFlow(MainActivity.this, SKU_GAS, IabHelper.ITEM_TYPE_INAPP, RC_REQUEST, new IabHelper.OnPurchaseListener() {
					
					@Override
					public void onSuccess(Purchase purchase) {
						NetworkManager.getInstnace().verifyResult(MainActivity.this, purchase, new OnResultListener<Purchase>() {

							@Override
							public void onSuccess(Purchase result) {
								mHelper.consumeAsync(result, mConsumeListener);
							}

							@Override
							public void onFail(int code) {
								
							}
						});
					}
					
					@Override
					public void onError(String message) {
						
					}
				}, null);
			}
		});
		
		btn = (Button)findViewById(R.id.btn_premium);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mHelper.launchPurchaseFlow(MainActivity.this, SKU_PREMIUM, IabHelper.ITEM_TYPE_INAPP, RC_REQUEST, new OnPurchaseListener() {
					
					@Override
					public void onSuccess(Purchase purchase) {
						NetworkManager.getInstnace().verifyResult(MainActivity.this, purchase, new OnResultListener<Purchase>() {

							@Override
							public void onSuccess(Purchase result) {
								Toast.makeText(MainActivity.this, "premium... only one purchase... update data", Toast.LENGTH_SHORT).show();
							}

							@Override
							public void onFail(int code) {
								
							}
						});
					}
					
					@Override
					public void onError(String message) {
						
					}
				}, null);
			}
		});
		
		btn = (Button)findViewById(R.id.btn_infinite);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mHelper.launchPurchaseFlow(MainActivity.this, SKU_INFINITE_GAS, IabHelper.ITEM_TYPE_SUBS, RC_REQUEST, new OnPurchaseListener() {
					
					@Override
					public void onSuccess(Purchase purchase) {
						NetworkManager.getInstnace().verifyResult(MainActivity.this, purchase, new OnResultListener<Purchase>() {

							@Override
							public void onSuccess(Purchase result) {
								Toast.makeText(MainActivity.this, "infinite subs purchase... update data", Toast.LENGTH_SHORT).show();
							}

							@Override
							public void onFail(int code) {
								
							}
						});
					}
					
					@Override
					public void onError(String message) {
						
					}
				}, null);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (mHelper.handleActivityResult(requestCode, resultCode, data)) {
			return;
		}
	}
	@Override
	protected void onDestroy() {
		if(mHelper != null) {
			mHelper.dispose();
			mHelper = null;
		}
	}
	OnConsumeFinishedListener mConsumeListener = new OnConsumeFinishedListener() {
		
		@Override
		public void onSuccess(Purchase purchase) {
			Toast.makeText(MainActivity.this, "Update Data...", Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void onError(Purchase purchase, String message) {
			
		}
	};
	QueryInventoryListener queryListener = new QueryInventoryListener() {
		
		@Override
		public void onSuccess(Inventory inventory) {
			Purchase gas = inventory.getPurchase(SKU_GAS);
			NetworkManager.getInstnace().verifyResult(MainActivity.this, gas, new OnResultListener<Purchase>() {
				
				@Override
				public void onSuccess(Purchase result) {
					Toast.makeText(MainActivity.this, "Gas purchase", Toast.LENGTH_SHORT).show();
					mHelper.consumeAsync(result, mConsumeListener);
				}
				
				@Override
				public void onFail(int code) {
					
				}
			});
			
			Purchase premium = inventory.getPurchase(SKU_PREMIUM);
			NetworkManager.getInstnace().verifyResult(MainActivity.this, premium, new OnResultListener<Purchase>() {

				@Override
				public void onSuccess(Purchase result) {
					Toast.makeText(MainActivity.this, "Premium purchase", Toast.LENGTH_SHORT).show();
					// not consume
					// only one purchase
					// update data
				}

				@Override
				public void onFail(int code) {
					
				}
			});
			Purchase infinite = inventory.getPurchase(SKU_INFINITE_GAS);
			NetworkManager.getInstnace().verifyResult(MainActivity.this, infinite, new OnResultListener<Purchase>() {

				@Override
				public void onSuccess(Purchase result) {
					Toast.makeText(MainActivity.this, "infinite (subscription)", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onFail(int code) {
					
				}
			});
			
		}
		
		@Override
		public void onError(String message) {
			
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
