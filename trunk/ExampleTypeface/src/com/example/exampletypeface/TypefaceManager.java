package com.example.exampletypeface;

import android.content.Context;
import android.graphics.Typeface;

public class TypefaceManager {
	private static final TypefaceManager instance = new TypefaceManager();
	
	public static TypefaceManager getInstance() {
		return instance;
	}
	
	private TypefaceManager() {
		
	}
	
	public static final String FONT_NAME_NAUM = "nanumgothic";
	private Typeface nanumgothic;
	
	
	public Typeface getTypeface(Context context, String fontName) {
		if (FONT_NAME_NAUM.equals(fontName)) {
			if (nanumgothic == null) {
				nanumgothic = Typeface.createFromAsset(context.getAssets(), "nanumgothic.ttf");
			}
			return nanumgothic;
		}
		return Typeface.DEFAULT;
	}
}
