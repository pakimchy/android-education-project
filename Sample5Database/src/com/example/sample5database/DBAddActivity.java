package com.example.sample5database;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DBAddActivity extends Activity {

	/** Called when the activity is first created. */
	EditText nameView;
	EditText ageView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.db_add_activity);
	    nameView = (EditText)findViewById(R.id.name);
	    ageView = (EditText)findViewById(R.id.age);
	    Button btn = (Button)findViewById(R.id.btn_add);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Person p = new Person();
				p.name = nameView.getText().toString();
				p.age = Integer.parseInt(ageView.getText().toString());
				DBManager.getInstance().addPerson(p);
				nameView.setText("");
				ageView.setText("0");
			}
		});
	}

}
