package com.example.sample5viewanimation;

import android.graphics.Matrix;
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
//		super.applyTransformation(interpolatedTime, t);
		Matrix m = t.getMatrix();
		float x = interpolatedTime * dx;
		float y = interpolatedTime * interpolatedTime * dy;
		m.setTranslate(x, y);
	}
}
