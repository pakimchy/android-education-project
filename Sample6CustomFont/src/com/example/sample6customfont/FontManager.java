package com.example.sample6customfont;

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
	
	Typeface nanum;
	public static final String FONT_NAME_NANUM = "nanum";
	
	Typeface roboto;
	public static final String FONT_NAME_ROBOTO = "croboto";
	
	Typeface noto;
	public static final String FONT_NAME_NOTO = "noto";
	
	public Typeface getTypeface(Context context, String fontName) {
		if (FONT_NAME_NANUM.equals(fontName)) {
			if (nanum == null) {
				nanum = Typeface.createFromAsset(context.getAssets(), "nanumgothic.ttf");
			}
			return nanum;
		}
		if (FONT_NAME_ROBOTO.equals(fontName)) {
			if (roboto == null) {
				roboto = Typeface.createFromAsset(context.getAssets(), "Roboto-Regular.ttf");
			}
			return roboto;
		}
		if (FONT_NAME_NOTO.equals(fontName)) {
			if (noto == null) {
				noto = Typeface.createFromAsset(context.getAssets(), "NotoSansKR-Regular.otf");
			}
			return noto;
		}

		return null;
	}
}
