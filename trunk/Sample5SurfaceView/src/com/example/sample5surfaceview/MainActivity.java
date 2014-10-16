package com.example.sample5surfaceview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends ActionBarActivity implements
		SurfaceHolder.Callback {

	SurfaceView surfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
		surfaceView.getHolder().addCallback(this);
		isRunning = true;
		new Thread(drawingRunnable).start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		isRunning = false;
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

	Surface mSurface = null;

	boolean isRunning = false;
	
	Runnable drawingRunnable = new Runnable() {
		
		@Override
		public void run() {
			while(isRunning) {
				if (mSurface != null) {
					Canvas canvas = null;
					try {
						canvas = mSurface.lockCanvas(null);
						draw(canvas);
					} finally {
						if (canvas != null) {
							mSurface.unlockCanvasAndPost(canvas);
						}
					}
				}
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};
	
	Paint mPaint = new Paint();
	int x = 300, y = 0;
	
	private void draw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(3);
		
		canvas.drawLine(0, y, x, 0, mPaint);
		x+=5;
		y-=5;
		if (x > 300) { 
			x = 0;
		}
		if (y < 0) {
			y = 300;
		}
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mSurface = holder.getSurface();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		mSurface = holder.getSurface();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mSurface = null;
	}
}
