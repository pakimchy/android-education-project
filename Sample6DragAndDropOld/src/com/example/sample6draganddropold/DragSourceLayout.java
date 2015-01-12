package com.example.sample6draganddropold;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class DragSourceLayout extends LinearLayout implements DragSource {

	public DragSourceLayout(Context context) {
		super(context);
	}

	public DragSourceLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	DragController mDragController;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return mDragController.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragController.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mDragController.onTouchEvent(ev);
    }

    @Override
    public boolean dispatchUnhandledMove(View focused, int direction) {
        return mDragController.dispatchUnhandledMove(focused, direction);
    }
	
	@Override
	public boolean allowDrag() {
		return true;
	}

	@Override
	public void setDragController(DragController dragger) {
		mDragController = dragger;
	}

	@Override
	public void onDropCompleted(View target, boolean success) {
		
	}	
	
}
