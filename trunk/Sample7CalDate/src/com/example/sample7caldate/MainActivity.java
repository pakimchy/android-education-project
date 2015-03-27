package com.example.sample7caldate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

	DatePicker picker;
	EditText numberView;
	Calendar calendar;
	EditText stringView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        picker = (DatePicker)findViewById(R.id.datePicker1);
        numberView = (EditText)findViewById(R.id.edit_number);
        stringView = (EditText)findViewById(R.id.edit_datestring);
        picker.init(2015, 2, 27, new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, monthOfYear);
				calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
			}
		});
        calendar = Calendar.getInstance();
        
        Button btn = (Button)findViewById(R.id.btn_cal);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int number = Integer.parseInt(numberView.getText().toString());
				calendar.add(Calendar.DAY_OF_YEAR, number);
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH);
				// ..
				Date date = calendar.getTime();
				long time = calendar.getTimeInMillis();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
				Toast.makeText(MainActivity.this, "date : " + sdf.format(date), Toast.LENGTH_SHORT).show();
			}
		});
        
        btn = (Button)findViewById(R.id.btn_convert);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
				try {
					Date date = sdf.parse(stringView.getText().toString());
					Calendar c = Calendar.getInstance();
					c.setTime(date);
					c.add(Calendar.DAY_OF_YEAR, Integer.parseInt(numberView.getText().toString()));
					Date d =c.getTime();
					Toast.makeText(MainActivity.this, "date : " + sdf.format(d), Toast.LENGTH_SHORT).show();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
        
        btn = (Button)findViewById(R.id.btn_sub);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
				try {
					Date date = sdf.parse(stringView.getText().toString());
					Calendar c = Calendar.getInstance();
					long current = c.getTimeInMillis();
					c.setTime(date);
					long set = c.getTimeInMillis();
					long delta = current - set;
					long day = delta / (24 * 60 * 60 * 1000);
					Toast.makeText(MainActivity.this, "day : " + day, Toast.LENGTH_SHORT).show();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
