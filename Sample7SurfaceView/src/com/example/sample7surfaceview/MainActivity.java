package com.example.sample7surfaceview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity implements SurfaceHolder.Callback{

	SurfaceView surfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surfaceView = (SurfaceView)findViewById(R.id.surfaceView1);
        surfaceView.getHolder().addCallback(this);
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				isDrawing = true;
				new Thread(drawRunnable).start();
			}
		});
    }

    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	isDrawing = false;
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

    boolean isDrawing = false;
    Runnable drawRunnable = new Runnable() {
		
		@Override
		public void run() {
			while(isDrawing) {
				if (mHolder != null) {
					Canvas c = null;
					try {
						c = mHolder.lockCanvas();
						draw(c);
					} finally {
						if (c != null) {
							mHolder.unlockCanvasAndPost(c);
						}
					}
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};
	Paint mPaint = new Paint();
	int delta = 0;
	private void draw(Canvas c) {
		c.drawColor(Color.WHITE);
		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(3);
		delta += 5;
		if (delta > 300) {
			delta = 0;
		}
		
		c.drawLine(0, delta, 300 - delta, 0, mPaint);
	}

    SurfaceHolder mHolder;
    
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mHolder = holder;
	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		mHolder = holder;
	}


	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mHolder = null;
	}
}
