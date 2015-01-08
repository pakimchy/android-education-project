package com.example.sample6customview;

import java.io.InputStream;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PathDashPathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class MyView extends View {

	public MyView(Context context) {
		super(context);
		init();
	}

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TypedArray ta = context.obtainStyledAttributes(attrs,
		// R.styleable.MyView);
		// int[] ids = {R.attr.message};
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.MyView, R.attr.myViewStyle, 0);
		message = ta.getString(R.styleable.MyView_message);
		ta.recycle();
		init();
	}

	String message = "Hello, Android";

	private static final int LENGTH = 300;
	private static final int COUNT = 30;

	float[] points = new float[(COUNT + 1) * 2 * 2];

	Paint mPaint = new Paint();

	GestureDetector mDetector;
	ScaleGestureDetector mScaleDetector;
	
	float mScaleFactor = 1.0f;
	
	private void init() {
		mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
			@Override
			public boolean onScale(ScaleGestureDetector detector) {
				float factor = detector.getScaleFactor();
				mScaleFactor *= factor;
				invalidate();
				return super.onScale(detector);
			}
		});
		mDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener(){
			@Override
			public boolean onDown(MotionEvent e) {
				return true;
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				Log.i("MyView", "fling....");
				return super.onFling(e1, e2, velocityX, velocityY);
			}
			
			@Override
			public boolean onDoubleTap(MotionEvent e) {
				return super.onDoubleTap(e);
			}
		});
		initLinePoint();
		initPath();
		initBitmap();
		mPaint.setTextSize(40);
	}

	public void setText(String text) {
		message = text;
		requestLayout();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getPaddingLeft() + getPaddingRight();
		int height = getPaddingTop() + getPaddingBottom();
		int maxWidth = 0;
		if (mBitmap != null) {
			maxWidth = mBitmap.getWidth();
			height += mBitmap.getHeight();
		}
		
		if (message != null) {
			Rect bounds = new Rect();
			mPaint.getTextBounds(message, 0, message.length() - 1, bounds);
			int tw = bounds.right - bounds.left;
			int th = bounds.bottom - bounds.top;
			maxWidth = (maxWidth>tw)? maxWidth : tw;
			height += th;
		}
		width += maxWidth;
		width = resolveSize(width, widthMeasureSpec);
		height = resolveSize(height, heightMeasureSpec);

		setMeasuredDimension(width, height);
		
//		int mode = MeasureSpec.getMode(widthMeasureSpec);
//		int size = MeasureSpec.getSize(widthMeasureSpec);
//		switch(mode) {
//		case MeasureSpec.AT_MOST :
//			width = (width>size)?size:width;
//			break;
//		case MeasureSpec.EXACTLY :
//			width = size;
//			break;
//		case MeasureSpec.UNSPECIFIED :
//			default :
//				break;
//		}
	}
	
	float bx, by;
	float tx, ty;
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		
		int height = 0;
		if (mBitmap != null) {
			height+= mBitmap.getHeight();
			bx = ( (right - left) - mBitmap.getWidth() ) / 2;
		}
		if (message != null) {
			Rect bounds = new Rect();
			mPaint.getTextBounds(message, 0, message.length() - 1, bounds);
			int tw = bounds.right - bounds.left;
			int th = bounds.bottom - bounds.top;
			height += th;
			tx = ( (right - left) - tw) / 2;
		}
		
		by = (bottom - top - height) / 2;
		if (mBitmap != null) {
			ty = by+mBitmap.getHeight();
		} else {
			ty = by;
		}
		if (message != null) {
			ty += (-mPaint.ascent());
		}
	}

	Bitmap mBitmap;
	Matrix mMatrix = new Matrix();

	private void initBitmap() {
		InputStream is = getContext().getResources().openRawResource(
				R.raw.photo1);
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = 2;
		mBitmap = BitmapFactory.decodeStream(is, null, opts);

		Bitmap bm = Bitmap.createScaledBitmap(mBitmap, 200, 200, false);

		mBitmap.recycle();

		mBitmap = bm;

	}

	Path mPath;
	Path mBasePath;
	Path mArrowPath;

	float[] vertixs = { 100, 100, 200, 50, 300, 100, 50, 200, 200, 200, 350,
			200, 100, 300, 200, 350, 300, 300 };

	private void initPath() {
		mPath = new Path();
		mPath.moveTo(200, 200);
		mPath.lineTo(100, 100);
		mPath.lineTo(300, 100);
		mPath.lineTo(400, 200);
		mPath.lineTo(300, 300);
		mPath.lineTo(100, 300);

		mBasePath = new Path();
		mBasePath.addCircle(300, 300, 200, Direction.CW);

		mArrowPath = new Path();
		mArrowPath.moveTo(0, 0);
		mArrowPath.lineTo(-5, -5);
		mArrowPath.lineTo(0, -5);
		mArrowPath.lineTo(5, 0);
		mArrowPath.lineTo(0, 5);
		mArrowPath.lineTo(-5, 5);

	}

	private void initLinePoint() {

		float delta = (float) LENGTH / COUNT;

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
		// drawLineAndPoint(canvas);
		// drawRect(canvas);
		// drawCircle(canvas);
		// drawArc(canvas);
		// drawPath(canvas);
		// drawText(canvas);
		// drawBitmap(canvas);
		// drawBitmapMesh(canvas);
		// drawPathEffect(canvas);
		// drawColor(canvas);
		// drawShader(canvas);
		drawColorFilter(canvas);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean isConsumed = mDetector.onTouchEvent(event);
		mScaleDetector.onTouchEvent(event);
		return isConsumed || super.onTouchEvent(event);
	}

	private void drawColorFilter(Canvas canvas) {
		canvas.save();
		canvas.scale(mScaleFactor, mScaleFactor);
		if (mBitmap != null) {
			ColorMatrix m = new ColorMatrix();
			m.setSaturation(0);

			ColorMatrixColorFilter filter = new ColorMatrixColorFilter(m);
			mPaint.setColorFilter(filter);

			canvas.drawBitmap(mBitmap, bx, by, mPaint);
		}
		if (message != null) {
			canvas.drawText(message, tx, ty, mPaint);
		}
		canvas.restore();
	}

	private void drawShader(Canvas canvas) {
		// int[] colors = {Color.RED, Color.YELLOW, Color.BLUE};
		// float[] position = {0, 0.3f, 1};
		// LinearGradient shader = new LinearGradient(50, 0, 150, 0, colors,
		// position, TileMode.REPEAT);
		// RadialGradient shader = new RadialGradient(100, 100, 100, Color.RED,
		// Color.BLUE, TileMode.CLAMP);
		int[] colors = { Color.RED, Color.BLUE, Color.RED };
		SweepGradient shader = new SweepGradient(100, 100, colors, null);
		mPaint.setShader(shader);
		canvas.save();
		canvas.translate(100, 100);
		canvas.drawCircle(100, 100, 100, mPaint);
		canvas.restore();
	}

	private void drawColor(Canvas canvas) {
		int color = Color.argb(0x80, 0xFF, 0x80, 0x00);
		mPaint.setColor(color);
		canvas.drawRect(100, 100, 300, 300, mPaint);
		mPaint.setAlpha(0x80);
		canvas.drawBitmap(mBitmap, 200, 200, mPaint);
	}

	private void drawPathEffect(Canvas canvas) {
		mPaint.setColor(Color.BLACK);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(10);

		// float[] intervals = {20, 10, 10, 5};
		// DashPathEffect patheffect = new DashPathEffect(intervals, 10);

		PathDashPathEffect patheffect = new PathDashPathEffect(mArrowPath, 10,
				0, PathDashPathEffect.Style.ROTATE);
		mPaint.setPathEffect(patheffect);
		// canvas.drawRect(100, 100, 300, 300, mPaint);
		canvas.drawCircle(200, 200, 100, mPaint);
	}

	int delta = 0;
	boolean direction = true;

	private void drawBitmapMesh(Canvas canvas) {
		if (direction) {
			delta++;
			if (delta > 20) {
				direction = false;
			}
		} else {
			delta--;
			if (delta < -20) {
				direction = true;
			}
		}
		vertixs[8] = 200 + delta;
		vertixs[9] = 200 - delta;
		canvas.drawBitmapMesh(mBitmap, 2, 2, vertixs, 0, null, 0, mPaint);
		invalidate();
	}

	private void drawBitmap(Canvas canvas) {
		mMatrix.setScale(1, -1, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
		mMatrix.postTranslate(100, 300);
		mMatrix.postSkew(0.5f, 0, 100, 300);
		canvas.drawBitmap(mBitmap, mMatrix, mPaint);

		canvas.drawBitmap(mBitmap, 100, 100, mPaint);
	}

	float hOffset;
	private static final String HELLO_ANDROID = "Hello, Android!!!";

	private void drawText(Canvas canvas) {
		mPaint.setColor(Color.BLACK);
		mPaint.setTextSize(60);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(2);
		mPaint.setTextSkewX(-0.5f);

		canvas.drawText(HELLO_ANDROID, 0, 60, mPaint);

		canvas.drawTextOnPath(HELLO_ANDROID, mBasePath, hOffset, 0, mPaint);
		hOffset += 5;
		if (hOffset > 200 * Math.PI)
			hOffset = 0;
		invalidate();
	}

	private void drawPath(Canvas canvas) {
		mPaint.setColor(Color.GREEN);

		canvas.drawPath(mPath, mPaint);
	}

	private void drawArc(Canvas canvas) {

		mPaint.setColor(Color.CYAN);

		RectF rect = new RectF(100, 100, 300, 200);
		canvas.drawArc(rect, 30, 120, true, mPaint);

		rect = new RectF(100, 300, 300, 400);
		canvas.drawArc(rect, 30, 120, false, mPaint);

	}

	private void drawCircle(Canvas canvas) {
		mPaint.setColor(Color.BLUE);

		canvas.drawCircle(200, 200, 100, mPaint);

		RectF rect = new RectF(100, 400, 300, 500);
		canvas.drawOval(rect, mPaint);
	}

	private void drawRect(Canvas canvas) {
		mPaint.setColor(Color.DKGRAY);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(10);

		canvas.drawRect(100, 100, 300, 300, mPaint);

		RectF rect = new RectF(100, 400, 300, 600);
		canvas.drawRoundRect(rect, 20, 30, mPaint);
	}

	private void drawLineAndPoint(Canvas canvas) {
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
