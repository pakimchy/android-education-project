package com.example.sample7setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

	EditText idView, pwView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idView = (EditText)findViewById(R.id.edit_userid);
        pwView = (EditText)findViewById(R.id.edit_password);
        
        Button btn = (Button)findViewById(R.id.btn_save);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String userid = idView.getText().toString();
				String password = pwView.getText().toString();
				PropertyManager.getInstance().setUserId(userid);
				PropertyManager.getInstance().setPassword(password);
			}
		});
        
        String userid = PropertyManager.getInstance().getUserId();
        String password = PropertyManager.getInstance().getPassword();
        idView.setText(userid);
        pwView.setText(password);
        
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
        	startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
