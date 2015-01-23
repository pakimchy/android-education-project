package com.example.sampleripplecolor;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Xml;

public class RippleColorDrawable extends Drawable {

	long startTime = -1;
	int interval = 100;
	int duration = 1000;
	int centerColor = Color.BLUE;
	int outsideColor = Color.WHITE;
	
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

    @Override
    public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs)
    		throws XmlPullParserException, IOException {
    	TypedArray a = r.obtainAttributes(attrs, R.styleable.RippleColorDrawable);
    	centerColor = a.getColor(R.styleable.RippleColorDrawable_centerColor, Color.BLUE);
    	outsideColor = a.getColor(R.styleable.RippleColorDrawable_outsideColor, Color.WHITE);
    	interval = a.getInt(R.styleable.RippleColorDrawable_interval, 100);
    	duration = a.getInt(R.styleable.RippleColorDrawable_duration, 1000);
    	a.recycle();
    }
    Runnable resetRunnable = new Runnable() {
		
		@Override
		public void run() {
			startTime = -1;
		}
	};
	@Override
	public void draw(Canvas canvas) {
		Rect rect = getBounds();
		int centerX = (rect.left + rect.right) / 2;
		long currentTime = SystemClock.uptimeMillis();
		if (startTime == -1) {
			startTime = currentTime;
			long time = currentTime + duration + interval;
			scheduleSelf(resetRunnable, time);
		}
		int delay = (int) (currentTime - startTime);

		if (delay > duration) {
			startTime = -1;
			mPaint.setShader(null);
			mPaint.setColor(centerColor);
			canvas.drawRect(rect, mPaint);
		} else {
			int rest = interval - delay % interval;
			float fraction = (float) delay / (float) duration;
			int[] colors = { outsideColor, centerColor, outsideColor };
			int length = (int) ((rect.right - rect.left) * fraction);
			LinearGradient shader = new LinearGradient(centerX - length, 0,
					centerX + length, 0, colors, null, Shader.TileMode.CLAMP);
			mPaint.setShader(shader);
			canvas.drawRect(rect, mPaint);
			long time = currentTime + rest;
			scheduleSelf(what, time);
		}
	}

	Runnable what = new Runnable() {

		@Override
		public void run() {
			invalidateSelf();
		}
	};

	@Override
	public void setAlpha(int alpha) {

	}

	@Override
	public void setColorFilter(ColorFilter cf) {

	}

	@Override
	public int getOpacity() {
		return 0;
	}

}
