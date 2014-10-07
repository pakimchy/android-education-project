package com.example.sample5paintshop2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PaintView extends View {

	public PaintView(Context context) {
		super(context);
		init();
	}

	public PaintView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public PaintView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	Canvas mCanvas;
	Paint mPaint;
	Bitmap mBitmap;
	
	private void init() {
		mPaint = new Paint();
		mPaint.setColor(Color.BLUE);
		offScreenPaint = new Paint();
		offScreenPaint.setColor(Color.BLUE);
		offScreenPaint.setStrokeWidth(5);
		mBitmap = ((BitmapDrawable)getContext().getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
	}
	
	public void setStrokeWidth(int width) {
		offScreenPaint.setStrokeWidth(width);
	}
	
	Bitmap offScreenBitmap;
	Canvas offScreenCanvas;
	Paint offScreenPaint;
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		int width = right - left - getPaddingRight() - getPaddingLeft();
		int height = bottom - top - getPaddingTop() - getPaddingBottom();
		if (width == 0 || height == 0) {
			return;
		}
		
		if (offScreenBitmap == null) {
			offScreenBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
			offScreenCanvas = new Canvas(offScreenBitmap);
			offScreenCanvas.drawColor(Color.TRANSPARENT);
		}
		
		if (offScreenBitmap.getWidth() != width || offScreenBitmap.getHeight() != height) {
			Bitmap bm = Bitmap.createBitmap(width, height, Config.ARGB_8888);
			Canvas c = new Canvas(bm);
			c.drawBitmap(offScreenBitmap, 0, 0, null);
			offScreenBitmap.recycle();
			offScreenBitmap = bm;
			offScreenCanvas = c;
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
//		canvas.drawBitmap(mBitmap, 0, 0, mPaint);
		canvas.drawBitmap(offScreenBitmap, 0, 0, mPaint);
	}

	float oldX, oldY;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN :
			oldX = x;
			oldY = y;
			return true;
		case MotionEvent.ACTION_MOVE :
			offScreenCanvas.drawLine(oldX, oldY, x, y, offScreenPaint);
			oldX = x;
			oldY = y;
			invalidate();
			return true;
		}
		return super.onTouchEvent(event);
	}
}
