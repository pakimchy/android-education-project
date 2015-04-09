package com.example.sample7viewanimation;

import android.graphics.Camera;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class My3DAnimation extends Animation {

	int centerX, centerY;
	
	Camera mCamera;
	
	public My3DAnimation() {
		mCamera = new Camera();
	}
	
	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {	
		super.initialize(width, height, parentWidth, parentHeight);
		centerX = width/2;
		centerY = height/2;
	}
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		mCamera.save();
		mCamera.rotateY(interpolatedTime * 90);
		mCamera.getMatrix(t.getMatrix());
		mCamera.restore();
		
		t.getMatrix().preTranslate(-centerX, -centerY);
		t.getMatrix().postTranslate(centerX, centerY);
	}
	
}
