package com.example.sampleripplecolor;

import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class MyResources extends Resources {

	public MyResources(Resources res) {
		super(res.getAssets(), res.getDisplayMetrics(), res.getConfiguration());
	}
	
	@Override
	public Drawable getDrawable(int id) throws NotFoundException {
		Drawable d = null;
		try {
			d = super.getDrawable(id);
		} catch (NotFoundException e){
			TypedValue value;
			value = new TypedValue();
			getValue(id, value, true);
			d = loadDrawable(value,id);
		}
		return d;
	}

	private Drawable loadDrawable(TypedValue value, int id) {
        if (value.string == null) {
            throw new NotFoundException("Resource \"" + getResourceName(id) + "\" ("
                    + Integer.toHexString(id) + ")  is not a Drawable (color or path): " + value);
        }

        final String file = value.string.toString();
        Drawable dr = null;
        try {
            if (file.endsWith(".xml")) {
                final XmlResourceParser rp = getXml(id);
                dr = RippleColorDrawable.createFromXml(this, rp);
//                dr = Drawable.createFromXml(this, rp, theme);
                rp.close();
            } 
        } catch (Exception e) {
            final NotFoundException rnf = new NotFoundException(
                    "File " + file + " from drawable resource ID #0x" + Integer.toHexString(id));
            rnf.initCause(e);
            throw rnf;
        }
		
		return dr;
	}
}
