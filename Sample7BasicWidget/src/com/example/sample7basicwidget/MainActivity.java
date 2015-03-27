package com.example.sample7basicwidget;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

	CheckBox pushSettingView;
	RadioGroup group;
	SwitchCompat switchView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pushSettingView = (CheckBox)findViewById(R.id.check_push);
        TextView messageView = (TextView)findViewById(R.id.text_message);
//        String message = getResources().getString(R.string.text_message);
//        messageView.setText(Html.fromHtml(message));
        int messageNo = 10;
        messageView.setText(""+messageNo);
//        messageView.setText(R.string.text_message);
        
        pushSettingView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Toast.makeText(MainActivity.this, "checked : " + isChecked, Toast.LENGTH_SHORT).show();
			}
		});
        
        group = (RadioGroup)findViewById(R.id.radioGroup1);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId) {
				case R.id.radio_m :
				case R.id.radio_f :
					break;
				}
			}
		});
        
    }

    public void onButtonClick(View view) {
    	boolean isChecked = pushSettingView.isChecked();
    	int radioid = group.getCheckedRadioButtonId();
    	switch(radioid) {
    	case R.id.radio_m :
    	case R.id.radio_f :
    		break;
    	}
    	
    	Toast.makeText(this, "push : " + isChecked, Toast.LENGTH_SHORT).show();
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
