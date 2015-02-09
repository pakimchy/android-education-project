package com.example.testactionbarcustom;

import java.lang.reflect.Field;

import android.content.Context;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.util.AttributeSet;

public class MyActionMenuItemView extends ActionMenuItemView {

	public MyActionMenuItemView(Context context) {
		super(context);
		init();
	}

	public MyActionMenuItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init() {
		try {
			Field maxIconSizeField = ActionMenuItemView.class.getDeclaredField("mMaxIconSize");
			maxIconSizeField.setAccessible(true);
			maxIconSizeField.setInt(this, 128);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public boolean hasText() {
		return true;
	}
	
	
	

}
