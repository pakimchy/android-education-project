package com.example.sample5customview;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.ComposeShader;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

	public MyView(Context context) {
		super(context);
		init();
	}
	
	public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
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
	
	public String getViewMessage() {
		return "Hello, MyView!";
	}
	
	public void setBitmap(Bitmap bitmap) {
		mBitmap = bitmap;
		requestLayout();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getPaddingLeft()  + getPaddingRight();
		int height = getPaddingTop() + getPaddingBottom();
		if (mBitmap != null) {
			width += mBitmap.getWidth();
			height += mBitmap.getHeight();
		}
		
//		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//		switch(widthMode) {
//		case MeasureSpec.UNSPECIFIED :
//			break;
//		case MeasureSpec.EXACTLY :
//			width = widthSize;
//			break;
//		case MeasureSpec.AT_MOST :
//			width = (width < widthSize)?width:widthSize;
//			break;
//		}
//		
		width = resolveSize(width, widthMeasureSpec);
		height = resolveSize(height, heightMeasureSpec);
		setMeasuredDimension(width, height);
 	}
	
	int mX, mY;
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		mX = (right - left - mBitmap.getWidth()) / 2;
		mY = (bottom - top - mBitmap.getHeight()) / 2;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.WHITE);
//		drawColorEffect(canvas);
		drawSimpleBitmap(canvas);
	}
	
	
	int dx = 0;
	private void drawSimpleBitmap(Canvas canvas) {
		canvas.drawBitmap(mBitmap, mX + dx, mY, mPaint);
		dx++;
		if (dx > 100) {
			dx = 0;
		}
		invalidate();
	}
	
	private void drawColorEffect(Canvas canvas) {
		canvas.drawBitmap(mBitmap, 0,  0, mPaint);
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		
		ColorMatrix matrixB = new ColorMatrix();
		matrixB.setScale(1f, .95f, .82f, 1.0f);
		cm.setConcat(matrixB, cm);

		ColorFilter filter = new ColorMatrixColorFilter(cm);
		mPaint.setColorFilter(filter);
		canvas.drawBitmap(mBitmap, 0, 300, mPaint);
	}
	
	private void drawShader(Canvas canvas) {
//		int[] colors = {Color.BLUE, Color.YELLOW, Color.RED };
//		float[] positions = {0, 0.3f, 1};
//		Shader shader = new LinearGradient(100, 100, 300, 300, colors, positions, Shader.TileMode.CLAMP);
		
//		Shader shader1 = new RadialGradient(200, 200, 100, Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);
//		int[] colors = {Color.BLUE, Color.RED, Color.BLUE};
//		Shader shader2 = new SweepGradient(200, 200, colors, null);
//		
//		Shader shader = new ComposeShader(shader2, shader1, Mode.DST_IN);
		Shader shader = new BitmapShader(mBitmap, TileMode.REPEAT, TileMode.REPEAT);
		mPaint.setShader(shader);
		canvas.drawCircle(100, 100, 100, mPaint);
//		canvas.drawRect(100, 100, 300, 300, mPaint);
	}
	
	private void drawPathEffect(Canvas canvas) {
		float[] intervals = { 10, 5, 20, 5};
//		PathEffect pathEffect = new DashPathEffect(intervals, 5);
		Path shape = new Path();
		shape.moveTo(0, 0);
		shape.lineTo(-5, -5);
		shape.lineTo(0, -5);
		shape.lineTo(5, 0);
		shape.lineTo(0, 5);
		shape.lineTo(-5, 5);
		
		PathEffect pathEffect = new PathDashPathEffect(shape, 10, 0, PathDashPathEffect.Style.ROTATE);
		mPaint.setPathEffect(pathEffect);
		mPaint.setColor(Color.BLUE);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(10);
		
		Path path = new Path();
		path.moveTo(100, 100);
		path.lineTo(300, 300);
//		canvas.drawPath(path, mPaint);
//		canvas.drawLine(100, 100, 300, 300, mPaint);
		canvas.drawCircle(200, 200, 100, mPaint);
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
