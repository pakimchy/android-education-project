package com.begentgroup.verticalseekbar;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AbsSeekBar;
import android.widget.ProgressBar;
import android.widget.SeekBar;

public class VerticalSeekBar extends SeekBar {
    public VerticalSeekBar(Context context) {
        super(context);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    protected void onDraw(Canvas c) {
        c.rotate(-90);
        c.translate(-getHeight(),0);

        super.onDraw(c);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                invalidate();
            	callOnStartTrackingTouch();
                trackTouchEvent(event);
                attemptClaimDrag();
                break;
            case MotionEvent.ACTION_MOVE:
            	if (mIsDragging) {
            		trackTouchEvent(event);
            	} else {
                    setPressed(true);
                    invalidate();
                    callOnStartTrackingTouch();
                    trackTouchEvent(event);
                    attemptClaimDrag();
            	}
                break;
            case MotionEvent.ACTION_UP:
                if (mIsDragging) {
                    trackTouchEvent(event);
                    callOnStopTrackingTouch();
                    setPressed(false);
                } else {
                    callOnStartTrackingTouch();
                    trackTouchEvent(event);
                    callOnStopTrackingTouch();
                }
                invalidate();
                break;

            case MotionEvent.ACTION_CANCEL:
                if (mIsDragging) {
                    callOnStopTrackingTouch();
                    setPressed(false);
                }
                invalidate();
                break;
        }
        return true;
    }

    private void trackTouchEvent(MotionEvent event) {
    	int i=0;
    	i=getMax() - (int) (getMax() * event.getY() / getHeight());
        callSetProgress(i,true);
        Log.i("Progress",getProgress()+"");
        onSizeChanged(getWidth(), getHeight(), 0, 0);
    }
    
    private void attemptClaimDrag() {
        if (getParent() != null) {
        	getParent().requestDisallowInterceptTouchEvent(true);
        }
    }
    
    boolean mIsDragging = false;
    
    protected void callOnStartTrackingTouch() {
    	mIsDragging = true;
    	try {
			Method m = AbsSeekBar.class.getDeclaredMethod("onStartTrackingTouch");
			m.setAccessible(true);
			m.invoke(this);
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    protected void callOnStopTrackingTouch() {
    	mIsDragging = false;
    	try {
			Method m = AbsSeekBar.class.getDeclaredMethod("onStopTrackingTouch");
			m.setAccessible(true);
			m.invoke(this);
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    protected void callSetProgress(int progress, boolean fromUser) {
    	try {
			Method m = ProgressBar.class.getDeclaredMethod("setProgress", int.class, boolean.class);
			m.setAccessible(true);
			m.invoke(this, progress, fromUser);
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}