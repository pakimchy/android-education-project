package com.example.sample5applicationcomponent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity {
	
	public static final String PARAM_MESSAGE = "message";
	public static final String PARAM_AGE = "age";
	public static final String PARAM_RESULT = "result";
	public static final String PARAM_PERSON = "person";
	
	TextView messageView;
	EditText resultView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.my_activity_layout);
		messageView = (TextView)findViewById(R.id.message);
		resultView = (EditText)findViewById(R.id.edit_result);
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String result = resultView.getText().toString();
				Intent data = new Intent();
				data.putExtra(PARAM_RESULT, result);
				setResult(Activity.RESULT_OK, data);
				finish();
			}
		});
		
		Intent i = getIntent();
		String text = i.getStringExtra(PARAM_MESSAGE);
		Person p = i.getParcelableExtra(PARAM_PERSON);
		
		Toast.makeText(this, "person " + p.name + "," + p.age, Toast.LENGTH_SHORT).show();
//		Person p = (Person)i.getSerializableExtra("person");
		
		int age = i.getIntExtra(PARAM_AGE, 0);
		messageView.setText(text + ", age : " + age);
	}

}
