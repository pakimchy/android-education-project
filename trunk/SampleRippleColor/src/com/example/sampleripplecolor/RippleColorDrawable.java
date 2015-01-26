package com.example.sampleripplecolor;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.animation.ScaleAnimation;

public class RippleColorDrawable extends Drawable implements Drawable.Callback {

	long startTime = -1;
	int interval = 100;
	int duration = 1000;
	int centerColor = Color.BLUE;
	int outsideColor = Color.WHITE;
	
	Drawable mDrawable;
    private int WIDTH;
    private int HEIGHT;
    private int FRAME_RATE = 10;
    private int DURATION = 400;
    private int PAINT_ALPHA = 90;
    private Handler canvasHandler;
    private float radiusMax = 0;
    private boolean animationRunning = false;
    private int timer = 0;
    private int timerEmpty = 0;
    private int durationEmpty = -1;
    private float x = -1;
    private float y = -1;
    private int zoomDuration;
    private float zoomScale;
    private ScaleAnimation scaleAnimation;
    private Boolean hasToZoom;
    private Boolean isCentered;
    private Integer rippleType;
    private Paint paint;
    private Bitmap originBitmap;
    private int rippleColor;
    private int ripplePadding;
	private int holo_red_light;
	
	Paint mPaint = new Paint();

	public static Drawable createFromXml(Resources r, XmlPullParser parser) 
			throws XmlPullParserException, IOException {
		AttributeSet attrs = Xml.asAttributeSet(parser);
        int type;
        while ((type=parser.next()) != XmlPullParser.START_TAG &&
                type != XmlPullParser.END_DOCUMENT) {
            // Empty loop
        }

        if (type != XmlPullParser.START_TAG) {
            throw new XmlPullParserException("No start tag found");
        }

        Drawable drawable = createFromXmlInner(r, parser, attrs);

        if (drawable == null) {
            throw new RuntimeException("Unknown initial tag: " + parser.getName());
        }

        return drawable;
		
	}
	
    public static Drawable createFromXmlInner(Resources r, XmlPullParser parser, AttributeSet attrs)
            throws XmlPullParserException, IOException {
    	final String name = parser.getName();
    	final Drawable drawable;
    	if (name.equals("rippledrawable")) {
    		drawable = new RippleColorDrawable();
    	} else {
            throw new XmlPullParserException(parser.getPositionDescription() +
                    ": invalid drawable tag " + name);
        }
    	drawable.inflate(r, parser, attrs);
        return drawable;
    }
    
    protected RippleColorDrawable() {
    	
    }
    
    public RippleColorDrawable(Drawable d) {
    	mDrawable = d;
    }

    @Override
    public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs)
    		throws XmlPullParserException, IOException {
//    	TypedArray a = r.obtainAttributes(attrs, R.styleable.RippleColorDrawable);
//    	centerColor = a.getColor(R.styleable.RippleColorDrawable_centerColor, Color.BLUE);
//    	outsideColor = a.getColor(R.styleable.RippleColorDrawable_outsideColor, Color.WHITE);
//    	interval = a.getInt(R.styleable.RippleColorDrawable_interval, 100);
//    	duration = a.getInt(R.styleable.RippleColorDrawable_duration, 1000);
//    	a.recycle();
    	
        final TypedArray typedArray = r.obtainAttributes(attrs, R.styleable.RippleView);
        rippleColor = typedArray.getColor(R.styleable.RippleView_rv_color, r.getColor(R.color.rippelColor));
        rippleType = typedArray.getInt(R.styleable.RippleView_rv_type, 0);
        hasToZoom = typedArray.getBoolean(R.styleable.RippleView_rv_zoom, false);
        isCentered = typedArray.getBoolean(R.styleable.RippleView_rv_centered, false);
        DURATION = typedArray.getInteger(R.styleable.RippleView_rv_rippleDuration, DURATION);
        FRAME_RATE = typedArray.getInteger(R.styleable.RippleView_rv_framerate, FRAME_RATE);
        PAINT_ALPHA = typedArray.getInteger(R.styleable.RippleView_rv_alpha, PAINT_ALPHA);
        ripplePadding = typedArray.getDimensionPixelSize(R.styleable.RippleView_rv_ripplePadding, 0);
        canvasHandler = new Handler();
        zoomScale = typedArray.getFloat(R.styleable.RippleView_rv_zoomScale, 1.03f);
        zoomDuration = typedArray.getInt(R.styleable.RippleView_rv_zoomDuration, 200);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(rippleColor);
        paint.setAlpha(PAINT_ALPHA);
        
        int resId = typedArray.getResourceId(R.styleable.RippleView_android_drawable, R.drawable.ripple_drawable);
        mDrawable = r.getDrawable(resId);
        mDrawable.setCallback(this);
        holo_red_light = r.getColor(android.R.color.holo_red_light);
        typedArray.recycle();
    }

    boolean isFirst = false;
    
	@Override
	public boolean setVisible(boolean visible, boolean restart) {
//		if (visible) {
//			startTime = -1;
//		}
		if (visible) {
			animationRunning = false;
	        if (!animationRunning)
	        {
	        	isFirst = true;
	        	
	            animationRunning = true;

	            timer = 0;
                durationEmpty = -1;
                timerEmpty = 0;

	            
	            if (rippleType == 1 && originBitmap == null)
	                originBitmap = getBitmap();

	        }
			
		}
		mDrawable.setVisible(visible, restart);
		return true;
	}
	
	private Bitmap getBitmap() {
		Rect rect = getBounds();
		int width = rect.right - rect.left;
		int height = rect.bottom - rect.top;
		if (width != 0 && height != 0) {
			Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(bm);
			c.translate(-rect.left, -rect.top);
			mDrawable.draw(c);
			return bm;
		}
		return null;
	}
	@Override
	public void draw(Canvas canvas) {
//		Rect rect = getBounds();
//		int centerX = (rect.left + rect.right) / 2;
//		long currentTime = SystemClock.uptimeMillis();
//		if (startTime == -1) {
//			startTime = currentTime;
//		}
//		int delay = (int) (currentTime - startTime);
//
//		if (delay > duration) {
//			startTime = -1;
//			mPaint.setShader(null);
//			mPaint.setColor(centerColor);
//			canvas.drawRect(rect, mPaint);
//		} else {
//			int rest = interval - delay % interval;
//			float fraction = (float) delay / (float) duration;
//			int[] colors = { outsideColor, centerColor, outsideColor };
//			int length = (int) ((rect.right - rect.left) * fraction);
//			LinearGradient shader = new LinearGradient(centerX - length, 0,
//					centerX + length, 0, colors, null, Shader.TileMode.CLAMP);
//			mPaint.setShader(shader);
//			canvas.drawRect(rect, mPaint);
//			long time = currentTime + rest;
//			scheduleSelf(what, time);
//		}
		mDrawable.draw(canvas);
		
        if (animationRunning)
        {
            if (DURATION <= timer * FRAME_RATE)
            {
                animationRunning = false;
                timer = 0;
                durationEmpty = -1;
                timerEmpty = 0;
//                canvas.restore();
                invalidateSelf();
                return;
            } else {
            	long when = SystemClock.uptimeMillis() + FRAME_RATE;
                scheduleSelf(what, when);
            }

            
//            if (timer == 0)
//                canvas.save();

            if (isFirst) {
                Rect rect = getBounds();
                
                WIDTH = rect.right - rect.left;
                HEIGHT = rect.bottom - rect.top;
                
	            radiusMax = Math.max(WIDTH, HEIGHT);
	
	            if (rippleType != 2)
	                radiusMax /= 2;
	
	            radiusMax -= ripplePadding;
	            
	            this.x = (rect.right + rect.left) / 2;
	            this.y = (rect.bottom + rect.top) / 2;
	            isFirst = false;
            }

            canvas.drawCircle(x, y, (radiusMax * (((float) timer * FRAME_RATE) / DURATION)), paint);

            paint.setColor(holo_red_light);

            if (rippleType == 1 && originBitmap != null && (((float) timer * FRAME_RATE) / DURATION) > 0.4f)
            {
                if (durationEmpty == -1)
                    durationEmpty = DURATION - timer * FRAME_RATE;

                timerEmpty++;
                final Bitmap tmpBitmap = getCircleBitmap((int) ((radiusMax) * (((float) timerEmpty * FRAME_RATE) / (durationEmpty))));
                canvas.drawBitmap(tmpBitmap, 0, 0, paint);
                tmpBitmap.recycle();
            }

            paint.setColor(rippleColor);

            if (rippleType == 1)
            {
                if ((((float) timer * FRAME_RATE) / DURATION) > 0.6f)
                    paint.setAlpha((int) (PAINT_ALPHA - ((PAINT_ALPHA) * (((float) timerEmpty * FRAME_RATE) / (durationEmpty)))));
                else
                    paint.setAlpha(PAINT_ALPHA);
            }
            else
                paint.setAlpha((int) (PAINT_ALPHA - ((PAINT_ALPHA) * (((float) timer * FRAME_RATE) / DURATION))));

            timer++;
        }
		
	}

    private Bitmap getCircleBitmap(final int radius) {
        final Bitmap output = Bitmap.createBitmap(originBitmap.getWidth(), originBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect((int)(x - radius), (int)(y - radius), (int)(x + radius), (int)(y + radius));

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(x, y, radius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(originBitmap, rect, rect, paint);

        return output;
    }	
	Runnable what = new Runnable() {

		@Override
		public void run() {
			invalidateSelf();
		}
	};

    @Override
    public void invalidateDrawable(Drawable who) {
    	invalidateSelf();
    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
    	scheduleSelf(what, when);
    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {
    	unscheduleSelf(what);
    }

    @Override
    public boolean getPadding(Rect padding) {
        return mDrawable.getPadding(padding);
    }
    
	@Override
	public void setAlpha(int alpha) {
		mDrawable.setAlpha(alpha);
	}

    @Override
    public int getAlpha() {
        return mDrawable.getAlpha();
    }
	
	@Override
	public void setColorFilter(ColorFilter cf) {
		mDrawable.setColorFilter(cf);
	}

	@Override
	public int getOpacity() {
		return mDrawable.getOpacity();
	}
	
    @Override
    public boolean isStateful() {
        return mDrawable.isStateful();
    }

    @Override
    protected boolean onStateChange(int[] state) {
        return mDrawable.setState(state);
    }

    @Override
    protected boolean onLevelChange(int level) {
        mDrawable.setLevel(level);
        invalidateSelf();
        return true;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        mDrawable.setBounds(bounds);
    }
    
    @Override
    public int getIntrinsicWidth() {
        return mDrawable.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mDrawable.getIntrinsicHeight();
    }
}
