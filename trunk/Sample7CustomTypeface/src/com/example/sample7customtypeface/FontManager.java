package com.example.sample7customtypeface;

import android.content.Context;
import android.graphics.Typeface;

public class FontManager {
	private static FontManager instance;
	public static FontManager getInstance() {
		if (instance == null) {
			instance = new FontManager();
		}
		return instance;
	}
	private FontManager() {
		
	}
	
	public static final String FONT_NAMUM = "nanumgothic";
	Typeface nanum;
	
	public Typeface getFont(Context context, String name) {
		if (FONT_NAMUM.equals(name)) {
			if (nanum == null) {
				nanum = Typeface.createFromAsset(context.getAssets(), "nanumgothic.ttf");
			}
			return nanum;
		}
		return null;
	}
}
