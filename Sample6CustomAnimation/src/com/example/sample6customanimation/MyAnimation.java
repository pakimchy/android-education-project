package com.example.sample6customanimation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

public class MyAnimation extends Animation {
	
	int dx, dy;
	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		dx = parentWidth - width;
		dy = parentHeight - height;
	}
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		float deltaX = interpolatedTime * dx;
		float deltaY = interpolatedTime * interpolatedTime * dy;
		t.getMatrix().setTranslate(deltaX, deltaY);
		if (interpolatedTime > 0.5) {
			float scale = 1.5f - interpolatedTime;
			t.getMatrix().postScale(scale, scale);
		}
	}
}
