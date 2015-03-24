package com.example.examplexmpp;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.examplexmpp.XMPPManager.OnActionListener;
import com.example.examplexmpp.XMPPManager.OnFileReceiveListener;
import com.example.examplexmpp.XMPPManager.OnMessageListener;

public class ChattingService extends Service {
	private static final String TAG = "ChattingService";

	public static final String ACTION_CHATTING_MESSAGE = "com.example.examplexmpp.action.CHATTING_MESSAGE";
	
	public static final String ACTION_REQUEST_FILE_TRANSFER = "com.example.examplexmpp.action.REQUEST_FILE_TRANSFER";
	
	public static final String EXTRA_MATE_ID = "userid";
	
	NotificationManager mNM;
	int mId = 1;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	OnMessageListener mMessageListener = new OnMessageListener() {

		@Override
		public void onReceived(Chat chat, Message message) {
			final ChatMessage cm = new ChatMessage();
			cm.mateId = XMPPManager.getInstance().getUserId(chat.getParticipant());
			cm.message = message.getBody();
			cm.bReceived = true;
			DBManager.getInstance().addChatMessage(cm);
			Intent intent = new Intent(ACTION_CHATTING_MESSAGE);
			intent.putExtra(EXTRA_MATE_ID, cm.mateId);
			sendOrderedBroadcast(intent, null, new BroadcastReceiver() {
				
				@Override
				public void onReceive(Context context, Intent intent) {
					int code = getResultCode();
					if (code == Activity.RESULT_CANCELED) {
						showNotification(cm);
					}
				}
			}, null, Activity.RESULT_CANCELED, null, null);
		}		
	};
	
	private void showNotification(ChatMessage cm) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setTicker(cm.message);
		builder.setContentTitle(cm.mateId);
		builder.setContentText(cm.message);
		builder.setAutoCancel(true);
		builder.setDefaults(NotificationCompat.DEFAULT_ALL);

		Intent intent = new Intent(this, ChattingActivity.class);
		intent.putExtra(ChattingActivity.EXTRA_USER_ID, cm.mateId);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(ChattingActivity.class);
		stackBuilder.addNextIntent(intent);
		PendingIntent pi = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pi);
		mNM.notify(mId, builder.build());	
	}
	
	OnFileReceiveListener mFileListener = new OnFileReceiveListener() {
		
		@Override
		public void onReceivedFile(FileTransferRequest request) {
			
		}
	};
	
	@Override
	public void onCreate() {
		super.onCreate();
		mNM = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		XMPPManager.getInstance().registerMessageListener(mMessageListener);
		XMPPManager.getInstance().registerOnFileReceiveListener(mFileListener);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (!XMPPManager.getInstance().isLogin()) {
			String userid = PropertyManager.getInstance().getUserId();
			String password = PropertyManager.getInstance().getPassword();
			if (!userid.equals("")) {
				XMPPManager.getInstance().login(userid, password, new OnActionListener<Void>() {
					
					@Override
					public void onSuccess(Void... data) {
						Log.i(TAG, "loginSuccess");
					}
					
					@Override
					public void onFail(int code) {
						Log.i(TAG, "login Fail");
					}
				});
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}
	
}
