package com.example.sample5googlemap;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.TextView;

public class InfoBitmap {
	View infoWindow;
	TextView nameView;
	TextView addressView;
	TextView typeView;
	int measureSpec;
	
	public InfoBitmap(Context context) {
		infoWindow = LayoutInflater.from(context).inflate(R.layout.info_window_layout, null);
		nameView = (TextView)infoWindow.findViewById(R.id.name);
		addressView = (TextView)infoWindow.findViewById(R.id.address);
		typeView = (TextView)infoWindow.findViewById(R.id.type);
		measureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
	}
	
	public Bitmap getMarkerBitmap(POI poi) {
		nameView.setText(poi.name);
		addressView.setText(poi.upperAddrName + poi.middleAddrName);
		typeView.setText(poi.upperBizName);
		infoWindow.measure(measureSpec, measureSpec);
		infoWindow.layout(0, 0, infoWindow.getMeasuredWidth(), infoWindow.getMeasuredHeight());
		
		return getViewBitmap(infoWindow);
	}
	
    private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }

	
	
}
