package com.example.sample6basicwidget;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	TextView messageView;
	CheckBox checkBox;
	RadioGroup group;
	SwitchCompat sw;
	EditText editView;
	InputMethodManager imm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		messageView = (TextView)findViewById(R.id.text_message);
//		tv.setText(R.string.hi);
		String text = getResources().getString(R.string.hi);
//		int count = 10;
		messageView.setText(Html.fromHtml(text));
		
//		Button btn = (Button)findViewById(R.id.button1);
//		btn.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(MainActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
//			}
//		});
		
		checkBox = (CheckBox)findViewById(R.id.checkBox1);
		
		checkBox.setChecked(true);
		
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Toast.makeText(MainActivity.this, "checked changed!!!", Toast.LENGTH_SHORT).show();
			}
		});
		
		group = (RadioGroup)findViewById(R.id.radioGroup1);
		int selectedId = group.getCheckedRadioButtonId();
		showSelectRadio(selectedId);
		group.check(R.id.radio0);
		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				showSelectRadio(checkedId);
			}
		});
		
		editView = (EditText)findViewById(R.id.editText2);
		editView.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEND) {
					// ...
					Toast.makeText(MainActivity.this, "send id/pw...", Toast.LENGTH_SHORT).show();
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
					return true;
				}
				return false;
			}
		});
		editView.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				editView.getText().toString();
				String text = s.toString();
				Toast.makeText(MainActivity.this, "current : " + text, Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
	}
	
	
	private void showSelectRadio(int id) {
		switch(id) {
		case R.id.radio0 :
			Toast.makeText(this, "select : 남", Toast.LENGTH_SHORT).show();
			break;
		case R.id.radio1 :
			Toast.makeText(this, "select : 여", Toast.LENGTH_SHORT).show();
		}
	}
	public void onClickButton(View v) { 
		boolean isChecked = checkBox.isChecked();
		String message;
		if (isChecked) {
			message = getResources().getString(R.string.check_message);
		} else {
			message = getResources().getString(R.string.not_check_message);
		}
		Toast.makeText(this, "Button Clicked : " + message, Toast.LENGTH_SHORT).show();
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
