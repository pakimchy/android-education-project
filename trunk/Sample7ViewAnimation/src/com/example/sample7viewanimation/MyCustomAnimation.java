package com.example.sample7viewanimation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

public class MyCustomAnimation extends Animation {

	int xMax, yMax;
	
	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		xMax = parentWidth - width;
		yMax = parentHeight - height;
	}
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		float x = interpolatedTime * xMax;
		float y = interpolatedTime * interpolatedTime * yMax;
		t.getMatrix().setTranslate(x, y);
	}
}
