package com.example.sampletokenviewlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class TokenLayout extends FrameLayout {

	public TokenLayout(Context context) {
		super(context);
	}

	public TokenLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public TokenLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		measureChildren(widthMeasureSpec, heightMeasureSpec);
		int width = 0;
		int height = 0;
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int maxWidth = LayoutParams.WRAP_CONTENT;
		switch (widthMode) {
		case MeasureSpec.AT_MOST:
		case MeasureSpec.EXACTLY:
			maxWidth = widthSize;
			break;
		}
		int lineWidth = 0;
		int lineMaxHeight = 0;

		for (int i = 0; i < getChildCount(); i++) {
			View v = getChildAt(i);
			LayoutParams params = (LayoutParams) v.getLayoutParams();
			int childWidth = v.getMeasuredWidth() + params.leftMargin
					+ params.rightMargin;
			int childHeight = v.getMeasuredHeight() + params.topMargin
					+ params.bottomMargin;
			if (maxWidth != LayoutParams.WRAP_CONTENT
					&& (lineWidth + childWidth) > maxWidth) {
				width = (width > lineWidth) ? width : lineWidth;
				height += lineMaxHeight;
				lineWidth = 0;
				lineMaxHeight = 0;
			}
			lineWidth += childWidth;
			lineMaxHeight = (lineMaxHeight > childHeight) ? lineMaxHeight
					: childHeight;
		}

		if (lineWidth != 0) {
			width = (width > lineWidth) ? width : lineWidth;
			height += lineMaxHeight;
		}
		width += (getPaddingLeft() + getPaddingRight());
		height += (getPaddingTop() + getPaddingBottom());

		setMeasuredDimension(resolveSize(width, widthMeasureSpec),
				resolveSize(height, heightMeasureSpec));
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		int x = getPaddingLeft();
		int y = getPaddingTop();
		int maxRight = getMeasuredWidth() - getPaddingRight();
		int maxHeight = 0;
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			LayoutParams params = (LayoutParams) child.getLayoutParams();
			int childWidth = child.getMeasuredWidth() + params.leftMargin
					+ params.rightMargin;
			int childHeight = child.getMeasuredHeight() + params.topMargin
					+ params.bottomMargin;
			if (x + childWidth > maxRight) {
				x = getPaddingLeft();
				y += maxHeight;
				maxHeight = 0;
			}
			x += params.leftMargin;
			child.layout(x, y + params.topMargin, x + child.getMeasuredWidth(),
					y + params.topMargin + child.getMeasuredHeight());
			x += (child.getMeasuredWidth() + params.rightMargin);
			maxHeight = (maxHeight > childHeight) ? maxHeight : childHeight;
		}

	}

}
