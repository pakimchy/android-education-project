package com.example.sample5customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MyView extends View {

	public MyView(Context context) {
		super(context);
		init();
	}
	
	private final static int COUNT = 30;
	private final static float WIDTH = 300;
	
	float[] mPoints;
	Paint mPaint;
	
	private void init() {
		int pointCount = COUNT+1;
		mPoints = new float[pointCount * 2 * 2];
		float delta = WIDTH / COUNT;
		int index = 0;
		for (int i = 0; i <= COUNT; i++) {
			mPoints[index++] = 0;
			mPoints[index++] = i * delta;
			mPoints[index++] = WIDTH - i * delta;
			mPoints[index++] = 0;
		}
		
		mPaint = new Paint();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.drawColor(Color.WHITE);
		
		canvas.save();
		
		canvas.translate(100, 100);
		mPaint.setColor(Color.BLUE);
		mPaint.setStrokeWidth(2);
		canvas.drawLines(mPoints, mPaint);
		
		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(5);
		canvas.drawPoints(mPoints, mPaint);
		
		canvas.restore();
	}

}
