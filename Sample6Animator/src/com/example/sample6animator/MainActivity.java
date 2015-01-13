package com.example.sample6animator;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	TextView messageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView)findViewById(R.id.textView1);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ValueAnimator animator = ValueAnimator.ofInt(Color.WHITE, Color.BLACK);
				animator.setEvaluator(new ArgbEvaluator());
				animator.setDuration(2000);
				animator.setInterpolator(new LinearInterpolator());
				animator.addUpdateListener(new AnimatorUpdateListener() {
					
					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						int color = (Integer)animation.getAnimatedValue();
						messageView.setTextColor(color);
					}
				});
				animator.setRepeatCount(ValueAnimator.INFINITE);
				animator.setRepeatMode(ValueAnimator.REVERSE);
				animator.start();
			}
		});
		
		btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ObjectAnimator animator = ObjectAnimator.ofInt(messageView, "textColor", Color.WHITE, Color.BLACK);
				animator.setEvaluator(new ArgbEvaluator());
				animator.setDuration(2000);
				animator.setInterpolator(new LinearInterpolator());
				animator.setRepeatCount(ValueAnimator.INFINITE);
				animator.setRepeatMode(ValueAnimator.REVERSE);
				animator.start();				
			}
		});
		
		btn = (Button)findViewById(R.id.button3);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AnimatorSet animator = (AnimatorSet)AnimatorInflater.loadAnimator(MainActivity.this, R.animator.set_animator);
				animator.setTarget(messageView);
				animator.start();
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
