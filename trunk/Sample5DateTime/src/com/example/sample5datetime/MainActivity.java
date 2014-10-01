package com.example.sample5datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.TimePicker.OnTimeChangedListener;

public class MainActivity extends ActionBarActivity {

	DatePicker datePicker;
	TextView messageView;
	TimePicker timePicker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView)findViewById(R.id.message);
		datePicker = (DatePicker)findViewById(R.id.datePicker1);
		timePicker = (TimePicker)findViewById(R.id.timePicker1);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		Date d = null;
		try {
			d = sdf.parse("2013-10-01T14:15:25.356+0900");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar c = new GregorianCalendar();
		if (d != null) {
			c.setTime(d);
		}
		c.add(Calendar.DAY_OF_YEAR, -1);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		datePicker.init(year, month, day, new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				Calendar c = Calendar.getInstance();
				c.set(Calendar.YEAR, year);
				c.set(Calendar.MONTH, monthOfYear);
				c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				c.set(Calendar.HOUR_OF_DAY, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				c.set(Calendar.MILLISECOND, 0);
				Date d = c.getTime();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				messageView.setText("date : " + sdf.format(d));
//				Toast.makeText(MainActivity.this, "year : " + year + ", month : " + monthOfYear + ", day : " + dayOfMonth, Toast.LENGTH_SHORT).show();
			}
		});
		
		timePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(c.get(Calendar.MINUTE));
//		timePicker.setIs24HourView(true);
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				Toast.makeText(MainActivity.this, "time : " + hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
			}
		});
		c.set(Calendar.YEAR, 2013);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		datePicker.setMinDate(c.getTimeInMillis());
		
		c.set(Calendar.YEAR, 2014);
		c.set(Calendar.MONTH, 11);
		c.set(Calendar.DAY_OF_MONTH, 31);
		datePicker.setMaxDate(c.getTimeInMillis());
		
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
