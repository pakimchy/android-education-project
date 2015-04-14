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
	
	public static final String FONT_NAMUM = "nanum";
	Typeface nanum;
	
	public static final String FONT_NOTO = "noto";
	Typeface noto;
	
	public static final String FONT_ROBOTO = "roboto";
	Typeface roboto;
	
	public Typeface getTypeface(Context context, String name) {
		if (FONT_NAMUM.equals(name)) {
			if (nanum == null) {
				nanum = Typeface.createFromAsset(context.getAssets(), "nanumgothic.ttf");
			}
			return nanum;
		}
		if (FONT_NOTO.equals(name)) {
			if (noto == null) {
				noto = Typeface.createFromAsset(context.getAssets(), "NotoSansKR-Regular.otf");
			}
			return noto;
		}
		if (FONT_ROBOTO.equals(name)) {
			if (roboto == null) {
				roboto = Typeface.createFromAsset(context.getAssets(), "Roboto-Regular.ttf");
			}
			return roboto;
		}
		return null;
	}
}
