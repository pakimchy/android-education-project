package com.example.sample7notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
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
    	Intent[] intents = new Intent[2];
    	intents[0] = new Intent(this, MainActivity.class);
    	intents[0].addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    	
    	Intent intent = new Intent(this, NotificationActivity.class);
    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	intents[1] = intent;
    	PendingIntent pi = PendingIntent.getActivities(this, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
    	builder.setContentIntent(pi);
    	
    	builder.setAutoCancel(true);
    	builder.setDefaults(NotificationCompat.DEFAULT_ALL);
    	
    	Notification n = builder.build();
    	mNM.notify(mId++, n);
    	mCount++;
    }

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
