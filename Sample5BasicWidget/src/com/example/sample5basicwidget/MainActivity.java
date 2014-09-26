package com.example.sample5basicwidget;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
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
	RadioGroup radioGroup;
	EditText emailView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView) findViewById(R.id.message);
		emailView = (EditText) findViewById(R.id.editText2);
		emailView.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String text = s.toString();
				Toast.makeText(MainActivity.this, "text : " + text,
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		emailView.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEND) {
					Toast.makeText(MainActivity.this, "send button pressed",
							Toast.LENGTH_SHORT).show();
				}
				return false;
			}
		});
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		radioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						showSelectedRadio(checkedId);
					}
				});
		checkBox = (CheckBox) findViewById(R.id.checkBox1);
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					Toast.makeText(MainActivity.this, "isChecked true",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this, "isChecked false",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String msg = getResources().getString(R.string.hellostring);
				messageView.setText(Html.fromHtml(msg));

				Button btn = (Button) v;
				// if (btn.isSelected()) {
				// btn.setSelected(false);
				// } else {
				// btn.setSelected(true);
				// }
				btn.setSelected(!btn.isSelected());

				// if (checkBox.isChecked()) {
				// Toast.makeText(MainActivity.this, "checked",
				// Toast.LENGTH_SHORT).show();
				// } else {
				// Toast.makeText(MainActivity.this, "unchecked",
				// Toast.LENGTH_SHORT).show();
				// }
				int id = radioGroup.getCheckedRadioButtonId();
				showSelectedRadio(id);
			}
		});
	}

	private void showSelectedRadio(int id) {
		switch (id) {
		case R.id.radio0:
			Toast.makeText(this, "Raiod1 selected", Toast.LENGTH_SHORT).show();
			break;
		case R.id.radio1:
			Toast.makeText(this, "Raiod2 selected", Toast.LENGTH_SHORT).show();
			break;
		case R.id.radio2:
			Toast.makeText(this, "Raiod3 selected", Toast.LENGTH_SHORT).show();
			break;
		}
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
