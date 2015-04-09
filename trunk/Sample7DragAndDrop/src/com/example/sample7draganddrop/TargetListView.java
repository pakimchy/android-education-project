package com.example.sample7draganddrop;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

public class TargetListView extends ListView implements DropTarget {

	public TargetListView(Context context) {
		super(context);
	}

	
	public TargetListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public interface OnItemDropListener {
		public void onItemDrop(ListView listview, int position, String data);
	}
	OnItemDropListener mItemDropListener;
	
	public void setOnItemDropListener(OnItemDropListener listener) {
		mItemDropListener = listener;
	}

	@Override
	public void onDrop(DragSource source, int x, int y, int xOffset,
			int yOffset, DragView dragView, Object dragInfo) {
		if (mItemDropListener != null) {
			int position = pointToPosition(x, y);
			String data = (String)dragInfo;
			mItemDropListener.onItemDrop(this, position, data);
		}
	}

	@Override
	public void onDragEnter(DragSource source, int x, int y, int xOffset,
			int yOffset, DragView dragView, Object dragInfo) {
		
	}

	@Override
	public void onDragOver(DragSource source, int x, int y, int xOffset,
			int yOffset, DragView dragView, Object dragInfo) {
		
	}

	@Override
	public void onDragExit(DragSource source, int x, int y, int xOffset,
			int yOffset, DragView dragView, Object dragInfo) {
		
	}

	@Override
	public boolean acceptDrop(DragSource source, int x, int y, int xOffset,
			int yOffset, DragView dragView, Object dragInfo) {
		return true;
	}

	@Override
	public Rect estimateDropLocation(DragSource source, int x, int y,
			int xOffset, int yOffset, DragView dragView, Object dragInfo,
			Rect recycle) {
		return null;
	}

}
