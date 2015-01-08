package com.example.sample6customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

	public MyView(Context context) {
		super(context);
		init();
	}

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private static final int LENGTH = 300;
	private static final int COUNT = 30;
	
	float[] points = new float[(COUNT+1) * 2 * 2];

	Paint mPaint = new Paint();
	
	private void init() {
		initLinePoint();
	}
	
	private void initLinePoint() {
		
		float delta = (float)LENGTH / COUNT;
		
		for (int i = 0; i <= COUNT; i++) {
			points[i * 4] = 0;
			points[i * 4 + 1] = i * delta;
			points[i * 4 + 2] = LENGTH - i * delta;
			points[i * 4 + 3] = 0;
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		
		canvas.save();
		
		canvas.translate(100, 100);
		canvas.rotate(30);
		
		mPaint.setAntiAlias(true);
		
		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(2);
		canvas.drawLines(points, mPaint);
		
		mPaint.setColor(Color.BLUE);
		mPaint.setStrokeWidth(5);
		canvas.drawPoints(points, mPaint);
		
		canvas.restore();
	}
	
	

}
