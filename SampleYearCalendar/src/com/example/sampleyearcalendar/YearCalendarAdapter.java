package com.example.sampleyearcalendar;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class YearCalendarAdapter extends BaseAdapter {

	Context mContext;
	View monthView;
	GridView monthGrid;
	TextView titleView;
	
	CalendarAdapter mMonthAdapter;
	HashMap<Integer,WeakReference<Bitmap>> mMonthBitmapMap = new HashMap<Integer,WeakReference<Bitmap>>();
	final static int MONTH_COUNT = (2100 - 1970 + 1) * 12;
	int monthWidth, monthHeight;
	int imageWidth, imageHeight;
	
	public YearCalendarAdapter(Context context) {
		mContext = context;
		monthView = LayoutInflater.from(context).inflate(R.layout.month_calendar_layout, null);
		monthGrid = (GridView)monthView.findViewById(R.id.month_grid);
		titleView = (TextView)monthView.findViewById(R.id.year_month);
		mMonthAdapter = new CalendarAdapter(context, null);
		monthGrid.setAdapter(mMonthAdapter);
		monthWidth = context.getResources().getDimensionPixelSize(R.dimen.month_item_width);
		monthHeight = context.getResources().getDimensionPixelSize(R.dimen.month_item_height);
		imageWidth = context.getResources().getDimensionPixelSize(R.dimen.image_width);
		imageHeight = context.getResources().getDimensionPixelSize(R.dimen.image_height);
	}
	
	@Override
	public int getCount() {
		// 1970.1 ==> 0 , until 2100.12
		
		return MONTH_COUNT;
	}
	
	public int getPositionOfDate(int year, int month) {
		// month 1 ==> 0
		// month 12 ==> 11
		return (year - 1970) + month;
	}

	@Override
	public YearMonth getItem(int position) {
		YearMonth ym = new YearMonth();
		ym.year = position / 12 + 1970;
		ym.month = position % 12;
		return ym;
	}
	
	public Bitmap getMonthBitmapOfPosition(int position) {
		WeakReference<Bitmap>  data = mMonthBitmapMap.get((Integer)position);
		Bitmap bitmap = null;
		if (data != null) {
			bitmap = data.get();
			if (bitmap == null) {
				mMonthBitmapMap.remove((Integer)position);
			}
		}
		if (bitmap == null) {
			bitmap = captureBitmapOfPosition(position);
			WeakReference<Bitmap> value = new WeakReference<Bitmap>(bitmap);
			mMonthBitmapMap.put((Integer)position, value);
		}
		return bitmap;
	}
	
	public Bitmap captureBitmapOfPosition(int position) {
		int year = position / 12 + 1970;
		int month = position % 12;
		CalendarData data = CalendarManager.getInstance().getCalendarData(year, month);
		mMonthAdapter.setCalendarData(data);
		titleView.setText(year + "-" + (month + 1));
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(monthWidth, MeasureSpec.EXACTLY);
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(monthHeight, MeasureSpec.EXACTLY);
		monthView.measure(widthMeasureSpec, heightMeasureSpec);
		monthView.layout(0, 0, monthWidth, monthHeight);
		
		return getViewBitmap(monthView);
	}
	
    private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

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


	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(mContext);
			AbsListView.LayoutParams params = new AbsListView.LayoutParams(imageWidth, imageHeight);
			imageView.setLayoutParams(params);
			imageView.setScaleType(ScaleType.FIT_XY);
		} else {
			imageView = (ImageView)convertView;
		}
		imageView.setImageBitmap(getMonthBitmapOfPosition(position));
		return imageView;
	}

}
