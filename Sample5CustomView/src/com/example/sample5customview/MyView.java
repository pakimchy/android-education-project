package com.example.sample5customview;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
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
	
	private void initLineAndPoint() {
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
	}
	
	Path mPath;
	
	private void initPath() {
		mPath = new Path();
		mPath.moveTo(100, 100);
		mPath.lineTo(50, 50);
		mPath.lineTo(150, 50);
		mPath.lineTo(200, 100);
		mPath.lineTo(150, 150);
		mPath.lineTo(50, 150);
	}
	
	Path mTextPath;
	
	private void initTextPath() {
		mTextPath = new Path();
		RectF oval = new RectF(100,100,300,300);
		mTextPath.addArc(oval, 180, 180);
	}
	private static final String HELLO_TEXT = "Hello, Android";
	
	Bitmap mBitmap;
	Matrix mMatrix;
	
	private void initBitmap() {
		InputStream is = getContext().getResources().openRawResource(R.drawable.gallery_photo_1);
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = 1;
		mBitmap = BitmapFactory.decodeStream(is);
		Bitmap bm = Bitmap.createScaledBitmap(mBitmap, 200, 200, false);
		mBitmap.recycle();
		mBitmap = bm;
		mMatrix = new Matrix();
		mMatrix.reset();
	}
	
	float[] vertexs = new float[] { 100, 100, 150, 150, 200, 100, 250, 150, 300, 100,
								    100, 300, 150, 350, 200, 300, 250, 350, 300, 300
	};
	
	private void init() {
		mPaint = new Paint();
		
		initBitmap();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.WHITE);
		drawBitmapMesh(canvas);
	}

	private void drawBitmapMesh(Canvas canvas) {
		canvas.drawBitmapMesh(mBitmap, 4, 1, vertexs, 0, null, 0, mPaint);
	}
	private void drawBitmap(Canvas canvas) {
//		mMatrix.setTranslate(100, 100);
//		canvas.drawBitmap(mBitmap, 100,  100, mPaint);
//		mMatrix.setScale(1, -1, mBitmap.getWidth() / 2, mBitmap.getHeight());
//		mMatrix.postSkew(0.5f, 0, 0, mBitmap.getHeight());
//		mMatrix.postTranslate(100, 100);
		
		mMatrix.setRotate(45, mBitmap.getWidth()/2, mBitmap.getHeight()/ 2);
		mMatrix.postTranslate(100, 100);
		canvas.drawBitmap(mBitmap, mMatrix, mPaint);
	}
	

	
	
	int mOffset = 0;
	
	private void drawText(Canvas canvas) {
		mPaint.setColor(Color.BLUE);
		mPaint.setTextSize(40);
		mPaint.setFakeBoldText(true);
		mPaint.setTextSkewX(-0.5f);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(2);
//		canvas.drawText(HELLO_TEXT, 100, 100, mPaint);
		canvas.drawTextOnPath(HELLO_TEXT, mTextPath, mOffset, 0, mPaint);
		mOffset++;
		if (mOffset > 100) {
			mOffset = 0;
		}
		invalidate();
	}
	
	private void drawPath(Canvas canvas) {
		mPaint.setColor(Color.BLUE);
		canvas.drawPath(mPath, mPaint);
	}

	private void drawArc(Canvas canvas) {
		mPaint.setColor(Color.BLUE);
		RectF rect = new RectF(100,100, 400, 300);
		canvas.drawArc(rect, 45, 90, true, mPaint);
	}
	private void drawOval(Canvas canvas) {
		mPaint.setColor(Color.BLUE);
		RectF rect = new RectF(100,100, 400, 300);
		canvas.drawOval(rect, mPaint);
	}
	private void drawCircle(Canvas canvas) {
		mPaint.setColor(Color.BLUE);
		mPaint.setAntiAlias(true);
		canvas.drawCircle(200, 200, 100, mPaint);
	}
	
	private void drawRoundRect(Canvas canvas) {
		mPaint.setColor(Color.BLUE);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(5);
		
		RectF rect = new RectF(100, 100, 300, 300);
		canvas.drawRoundRect(rect, 30, 15, mPaint);
	}
	
	private void drawRect(Canvas canvas) {
		mPaint.setColor(Color.BLUE);
		
		Rect rect = new Rect(100, 100, 300, 300);
		canvas.drawRect(rect, mPaint);
	}
	
	private void drawLineAndPoint(Canvas canvas) {
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
