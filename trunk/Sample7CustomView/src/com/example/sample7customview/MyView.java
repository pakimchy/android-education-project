package com.example.sample7customview;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
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

	Paint mPaint;

	private void init() {
		mPaint = new Paint();

		// initPointAndLine();
		// initPath();
		initBitmap();
	}

	Bitmap mBitmap;

	Matrix mMatrix;
	
	float[] vertics = { 100, 100, 200, 200, 300, 250, 400, 200, 500, 100,
			            100, 500, 200, 600, 300, 650, 400, 600, 500, 500
	};
	private void initBitmap() {
//		mBitmap = ((BitmapDrawable) getResources().getDrawable(
//				R.drawable.gallery_photo_1)).getBitmap();
		InputStream is = getResources().openRawResource(R.drawable.photo1);
		mBitmap = BitmapFactory.decodeStream(is);
		mMatrix = new Matrix();
	}

	Path mPath;
	Path mTextOnPath;

	private void initPath() {
		mPath = new Path();
		mPath.moveTo(200, 200);
		mPath.lineTo(100, 100);
		mPath.lineTo(300, 100);
		mPath.lineTo(400, 200);
		mPath.lineTo(300, 300);
		mPath.lineTo(100, 300);

		mTextOnPath = new Path();

		mTextOnPath.moveTo(100, 100);
		mTextOnPath.quadTo(200, 400, 600, 100);
	}

	float[] points;

	int length = 300;
	int count = 60;

	private void initPointAndLine() {
		int size = (count + 1) * 2 * 2;
		points = new float[size];
		float delta = (float) length / (float) count;
		for (int i = 0; i <= count; i++) {
			points[i * 4 + 0] = 0;
			points[i * 4 + 1] = i * delta;
			points[i * 4 + 2] = length - i * delta;
			points[i * 4 + 3] = 0;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.WHITE);

		// mPaint.setColor(Color.RED);
		// canvas.drawRect(100, 100, 300, 300, mPaint);

		// drawLineAndPoint(canvas);
		// drawRect(canvas);
		// drawCircle(canvas);
		// drawPath(canvas);
		// drawText(canvas);
		drawBitmap(canvas);
	}
	
	private void drawBitmap(Canvas canvas) {
//		canvas.drawBitmap(mBitmap, 100,  100, mPaint);
//		mMatrix.setScale(1, -1);
//		mMatrix.postSkew(-0.5f, 0);
////		mMatrix.postRotate(45);
//		mMatrix.postTranslate(100, 100 + 2*mBitmap.getHeight());
//		canvas.drawBitmap(mBitmap, mMatrix, mPaint);

		canvas.drawBitmapMesh(mBitmap, 4, 1, vertics, 0, null, 0, mPaint);
		
	}

	String text = "Hello, Android!!!";
	int hOffset = 0;

	private void drawText(Canvas canvas) {
		mPaint.setTextSize(40);
		mPaint.setTextSkewX(-0.5f);
		mPaint.setFakeBoldText(true);
		// mPaint.setStyle(Style.STROKE);
		// mPaint.setStrokeWidth(3);
		canvas.drawText(text, 40, 100, mPaint);

		canvas.drawTextOnPath(text, mTextOnPath, hOffset, 0, mPaint);
		hOffset += 2;
		if (hOffset > 300) {
			hOffset = 0;
		}
		invalidate();
		// canvas.drawPath(mTextOnPath,mPaint);
	}

	private void drawPath(Canvas canvas) {
		mPaint.setColor(Color.BLUE);
		canvas.drawPath(mPath, mPaint);
	}

	private void drawCircle(Canvas canvas) {
		mPaint.setColor(Color.BLUE);

		canvas.drawCircle(200, 200, 100, mPaint);

		RectF oval = new RectF(100, 400, 300, 500);
		canvas.drawOval(oval, mPaint);

		RectF oval2 = new RectF(100, 600, 300, 800);
		canvas.drawArc(oval2, 45, 90, false, mPaint);
	}

	private void drawRect(Canvas canvas) {
		mPaint.setColor(Color.BLUE);
		Rect r = new Rect(100, 100, 300, 300);
		canvas.drawRect(r, mPaint);

		mPaint.setAntiAlias(true);
		RectF rect = new RectF(100, 400, 300, 600);
		canvas.drawRoundRect(rect, 100, 50, mPaint);
	}

	private void drawLineAndPoint(Canvas canvas) {

		canvas.save();

		canvas.translate(100, 100);

		canvas.rotate(30, 100, 100);

		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(3);
		canvas.drawLines(points, mPaint);

		mPaint.setColor(Color.BLUE);
		mPaint.setStrokeWidth(5);
		canvas.drawPoints(points, mPaint);

		canvas.restore();
	}

}
