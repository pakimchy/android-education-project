package com.example.testappwidget;

import java.util.ArrayList;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

public class ListProvider implements RemoteViewsFactory {
	Context mContext;
	public ListProvider(Context context) {
		mContext = context;
	}
	ArrayList<String> items = new ArrayList<String>();
	
	@Override
	public void onCreate() {
		items.add("item1");
		items.add("item2");
		items.add("item3");
		items.add("item4");
		items.add("item5");
	}

	@Override
	public void onDataSetChanged() {
		
	}

	@Override
	public void onDestroy() {

	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public RemoteViews getViewAt(int position) {
		RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.item_view);
		remoteViews.setTextViewText(R.id.text_message, items.get(position));
		return remoteViews;
	}

	@Override
	public RemoteViews getLoadingView() {
		RemoteViews remoteView = new RemoteViews(mContext.getPackageName(), R.layout.loading_view);
		remoteView.setImageViewResource(R.id.image_loading, R.drawable.gallery_photo_2);
		return remoteView;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

}
