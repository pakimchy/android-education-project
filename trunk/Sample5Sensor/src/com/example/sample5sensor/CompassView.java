package com.example.sample5sensor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

public class CompassView extends View {

	public CompassView(Context context) {
		super(context);
		init();
	}

	public CompassView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public CompassView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	Bitmap mBitmap;
	Paint mPaint;
	Matrix mMatrix;
	float degree;
	private void init() {
		mBitmap = ((BitmapDrawable)getContext().getResources().getDrawable(R.drawable.comass)).getBitmap();
		mPaint = new Paint();
		mMatrix = new Matrix();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = mBitmap.getWidth();
		int height = mBitmap.getHeight();
		setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
	}

	public void setDegree(float degree) {
		this.degree = degree;
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		mMatrix.setRotate(-degree, mBitmap.getWidth()/ 2, mBitmap.getHeight()/2);
		canvas.drawBitmap(mBitmap, mMatrix, mPaint);
//		super.onDraw(canvas);
	}
	
}
