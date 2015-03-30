package com.example.sample7multitypelist;

import java.util.Date;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;


public class MainActivity extends ActionBarActivity {

	ListView listView;
	EditText messageView;
	RadioGroup group;
	MyAdapter mAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView1);
        mAdapter = new MyAdapter();
        listView.setAdapter(mAdapter);
        messageView = (EditText)findViewById(R.id.edit_message);
        group = (RadioGroup)findViewById(R.id.radioGroup1);
        Button btn = (Button)findViewById(R.id.btn_send);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String message = messageView.getText().toString();
				if (message != null && !message.equals("")) {
					switch(group.getCheckedRadioButtonId()) {
					case R.id.radio_send :
						SendData sd = new SendData();
						sd.message = message;
						mAdapter.add(sd);
						break;
					case R.id.radio_receive :
						ReceiveData rd = new ReceiveData();
						rd.iconId = R.drawable.ic_launcher;
						rd.message = message;
						mAdapter.add(rd);
						break;
					case R.id.radio_date :
						DateData dd = new DateData();
						dd.date = new Date().toString();
						mAdapter.add(dd);
						break;
					}
					messageView.setText("");
				}
			}
		});
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
