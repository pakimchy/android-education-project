package com.example.sample7notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.BigPictureStyle;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

	NotificationManager mNM;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNM = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Button btn = (Button)findViewById(R.id.btn_send_noti);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendNotification();
			}
		});
        
        btn = (Button)findViewById(R.id.btn_progress);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				progress = 0;
				mHandler.post(progressRunnable);
			}
		});
        
        btn = (Button)findViewById(R.id.btn_bit_text_style);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendNotificationBigPictureStyle();
			}
		});
    }
    
    private void sendNotificationStyle() {
    	NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
    	builder.setSmallIcon(R.drawable.ic_launcher);
    	builder.setTicker("Ticker...");
    	BigTextStyle style = new NotificationCompat.BigTextStyle();
    	style.bigText("Big Text...Big Text...Big Text...Big Text...Big Text...Big Text...Big Text...Big Text...Big Text...Big Text...Big Text...Big Text...Big Text...Big Text...Big Text...Big Text...Big Text...Big Text...Big Text...Big Text...Big Text...");
    	builder.setStyle(style);
    	builder.setContentTitle("title...");
    	mNM.notify(1, builder.build());
    	
    }

    
    private void sendNotificationBigPictureStyle() {
    	NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
    	builder.setSmallIcon(R.drawable.ic_launcher);
    	builder.setTicker("Ticker...");
    	BigPictureStyle style = new NotificationCompat.BigPictureStyle();
    	style.bigPicture(((BitmapDrawable)getResources().getDrawable(R.drawable.photo1)).getBitmap());
    	builder.setStyle(style);
    	builder.setContentTitle("title...");
    	mNM.notify(1, builder.build());
    }
    int mId = 1;
    int mCount = 1;
    private void sendNotification() {
    	NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
    	builder.setSmallIcon(R.drawable.ic_launcher);
    	builder.setTicker("my notification...");
    	builder.setContentTitle("title..." + mCount);
    	builder.setContentText("Text...");
    	builder.setWhen(System.currentTimeMillis());
    	TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
    	
//    	Intent[] intents = new Intent[2];
//    	intents[0] = new Intent(this, MainActivity.class);
//    	stackBuilder.addNextIntent(intents[0]);
//    	intents[0].addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

    	stackBuilder.addParentStack(NotificationActivity.class);
    	Intent intent = new Intent(this, NotificationActivity.class);
//    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//    	intents[1] = intent;
    	stackBuilder.addNextIntent(intent);
//    	PendingIntent pi = PendingIntent.getActivities(this, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
    	PendingIntent pi = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    	builder.setContentIntent(pi);
    	
    	Intent deleteIntent = new Intent(this, DeleteService.class);
    	PendingIntent dpi = PendingIntent.getService(this, 0, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    	builder.setDeleteIntent(dpi);
    	
//    	builder.setOngoing(true);
    	
    	builder.setAutoCancel(true);
    	builder.setDefaults(NotificationCompat.DEFAULT_ALL);
    	
    	Notification n = builder.build();
    	mNM.notify(mId++, n);
    	mCount++;
    }
    
    Handler mHandler = new Handler(Looper.getMainLooper());
    
    int progress = 0;
    int mProgressId = 100;
    Runnable progressRunnable = new Runnable() {
		
		@Override
		public void run() {
			if (progress <= 100) {
				NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
				builder.setSmallIcon(R.drawable.ic_launcher);
				builder.setTicker("progress");
				builder.setContentTitle("progress");
				builder.setContentText("progress : " + progress);
				builder.setProgress(100, progress, false);
				builder.setDefaults(NotificationCompat.DEFAULT_ALL);
				builder.setOngoing(true);
				builder.setOnlyAlertOnce(true);
				mNM.notify(mProgressId,builder.build());
				progress += 5;
				mHandler.postDelayed(this, 500);
			} else {
				mNM.cancel(mProgressId);
			}
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
