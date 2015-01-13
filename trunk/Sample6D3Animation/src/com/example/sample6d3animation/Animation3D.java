package com.example.sample6d3animation;

import android.graphics.Camera;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Animation3D extends Animation {
	int centerX, centerY;
	Camera mCamera;
	
	public Animation3D() {
		mCamera = new Camera();
	}
	
	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		centerX = width / 2;
		centerY = height / 2;
	}
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		mCamera.save();
		
		float deg = interpolatedTime * 45;
		mCamera.rotateY(deg);
		
		mCamera.getMatrix(t.getMatrix());
		
		mCamera.restore();
		
		t.getMatrix().preTranslate(-centerX, -centerY);
		t.getMatrix().postTranslate(centerX, centerY);
		
	}
}
