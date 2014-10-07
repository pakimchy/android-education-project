package com.example.sample5paintshop;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
	

	Path mPath;
	Paint mPaint;
	ArrayList<DrawObject> mObject = new ArrayList<DrawObject>();
	
	private void init() {
		mPaint = new Paint();
		mPaint.setColor(Color.BLUE);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(5);
	}
	
	public void setStrokeWidth(int width) {
		mPaint = new Paint(mPaint);
		mPaint.setStrokeWidth(width);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.WHITE);
		
		for (DrawObject obj : mObject) {
			canvas.drawPath(obj.mPath, obj.mPaint);
		}
	}
	
	public void cancel() {
		if (mObject.size() > 0) {
			mObject.remove(mObject.size() - 1);
			invalidate();
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN :
			mPath = new Path();
			DrawObject obj = new DrawObject();
			obj.mPath = mPath;
			obj.mPaint = mPaint;
			mObject.add(obj);
			mPath.moveTo(event.getX(), event.getY());
			return true;
		case MotionEvent.ACTION_MOVE :
			mPath.lineTo(event.getX(), event.getY());
			invalidate();
			return true;
		case MotionEvent.ACTION_UP :
			return true;
		}
		return super.onTouchEvent(event);
	}

}
