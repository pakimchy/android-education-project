package com.example.examplebasicwidget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

//	TextView messageView;
//	CheckBox checkNotiView;
//	RadioGroup groupView;
//	EditText emailView;
//	EditText passwordView;
//	private static final String EMAIL_PATTERN = 
//			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//	InputMethodManager mIMM;
	
//	ProgressBar hProgressBar;
//	SeekBar seekBar;
//	RatingBar ratingBar;
//	DatePicker datePicker;
//	TimePicker timePicker;
//	Chronometer chrono;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		chrono = (Chronometer)findViewById(R.id.chronometer1);
//		chrono.setBase(SystemClock.elapsedRealtime());
//		chrono.start();
//		chrono.setOnChronometerTickListener(new OnChronometerTickListener() {
//			
//			@Override
//			public void onChronometerTick(Chronometer chronometer) {
//				long timegap = (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000L;
//				Log.i("Chronometer", "time Gap : " + timegap);
//			}
//		});
//		datePicker = (DatePicker)findViewById(R.id.datePicker1);
//		timePicker = (TimePicker)findViewById(R.id.timePicker1);
//		datePicker.init(2014, 10, 10, new DatePicker.OnDateChangedListener() {
//			
//			@Override
//			public void onDateChanged(DatePicker view, int year, int monthOfYear,
//					int dayOfMonth) {
//				
//			}
//		});
//		timePicker.setCurrentHour(14);
//		timePicker.setCurrentMinute(10);
//		timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//			
//			@Override
//			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//				
//			}
//		});
//		ratingBar = (RatingBar)findViewById(R.id.ratingBar1);
//		ratingBar.setRating(1.5f);
//		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
//			
//			@Override
//			public void onRatingChanged(RatingBar ratingBar, float rating,
//					boolean fromUser) {
//				
//			}
//		});
//		seekBar = (SeekBar)findViewById(R.id.seekBar1);
//		seekBar.setMax(100);
//		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
//			
//			@Override
//			public void onStopTrackingTouch(SeekBar seekBar) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onStartTrackingTouch(SeekBar seekBar) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onProgressChanged(SeekBar seekBar, int progress,
//					boolean fromUser) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		hProgressBar = (ProgressBar)findViewById(R.id.progressBar4);
//		hProgressBar.setMax(10000);
//		hProgressBar.setSecondaryProgress(5000);
//		hProgressBar.setProgress(3000);
//		emailView = (EditText)findViewById(R.id.edit_email);
//		passwordView = (EditText)findViewById(R.id.edit_password);
//		mIMM = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//		passwordView.setOnEditorActionListener(new OnEditorActionListener() {
//			
//			@Override
//			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//				if (actionId == EditorInfo.IME_ACTION_SEND) {
//					String email = emailView.getText().toString();
//					if (email.matches(EMAIL_PATTERN)) {
//						Toast.makeText(MainActivity.this, "서버 전송", Toast.LENGTH_SHORT).show();
//						mIMM.hideSoftInputFromWindow(passwordView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//					} else {
//						Toast.makeText(MainActivity.this, "이메일 형식 불일치", Toast.LENGTH_SHORT).show();
//					}
//				}
//				return true;
//			}
//		});
//		passwordView.addTextChangedListener(new TextWatcher() {
//			
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				String text = s.toString();
//				if (text.length() < 8) {
//					passwordView.setTextColor(Color.RED);
//				} else {
//					passwordView.setTextColor(Color.BLACK);
//				}
//			}
//			
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//				
//			}
//			
//			@Override
//			public void afterTextChanged(Editable s) {
//				
//			}
//		});
//		groupView = (RadioGroup)findViewById(R.id.group_sex);
//		groupView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//			
//			@Override
//			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				if (checkedId == R.id.radio_male) {
//					Toast.makeText(MainActivity.this, "남", Toast.LENGTH_SHORT).show();
//				} else if (checkedId == R.id.radio_female) {
//					Toast.makeText(MainActivity.this, "여", Toast.LENGTH_SHORT).show();
//				}
//			}
//		});
//		int id = groupView.getCheckedRadioButtonId();
//		if (id == R.id.radio_female) {
//			groupView.check(R.id.radio_male);
//		}
//		checkNotiView = (CheckBox)findViewById(R.id.check_noti);
//		checkNotiView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//			
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				Toast.makeText(MainActivity.this, "알림 : " + isChecked, Toast.LENGTH_SHORT);
//			}
//		});
//		
//		if (!checkNotiView.isChecked()) {
//			checkNotiView.setChecked(true);
//		}
//		messageView = (TextView)findViewById(R.id.message);
//		String text = getResources().getString(R.string.text_message);
//		messageView.setText(Html.fromHtml(text));
//		
//		Button btn = (Button)findViewById(R.id.button1);
//		btn.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(MainActivity.this, "버튼이 눌렸어요", Toast.LENGTH_SHORT).show();
//			}
//		});
	}

	
	public void calDate() {
		Calendar c = Calendar.getInstance(Locale.KOREA);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		long lastmidnight = c.getTimeInMillis();
		c.add(Calendar.DAY_OF_YEAR, 1);
		long nextmidnight = c.getTimeInMillis();
	}
	
	public void convertDate() {
		String ds = "2014-11-10T10:25:40.000+0900";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		try {
			Date d = sdf.parse(ds);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void convertString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.KOREA);
		String ds = sdf.format(new Date());
	}
	
	public void subDate(Date d1, Date d2) {
		int day = (int)((d2.getTime() - d1.getTime()) / (24L * 60L * 60L * 1000L));
	}
	
	public void onButtonClick(View v) {
		Toast.makeText(this, "버튼이 눌렸어요", Toast.LENGTH_SHORT).show();
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
