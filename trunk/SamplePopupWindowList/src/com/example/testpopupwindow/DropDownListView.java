package com.example.testpopupwindow;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class DropDownListView extends ListView {
    
    /**
     * True if this wrapper should fake focus.
     */
    private boolean mHijackFocus;

    public DropDownListView(Context context) {
    	this(context, true);
    }
    
    public DropDownListView(Context context, AttributeSet attrs, int defStyle) {
		this(context, attrs, defStyle, true);
		
	}

	public DropDownListView(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.dropDownListViewStyle, true);
		
	}

	public DropDownListView(Context context, boolean hijackFocus) {
        this(context, null, R.attr.dropDownListViewStyle, hijackFocus);
    }
	
	public DropDownListView(Context context, AttributeSet attrs, int defStyle, boolean hijackFocus) {
		super(context, attrs, defStyle);
        mHijackFocus = hijackFocus;
        setCacheColorHint(0); // Transparent, since the background drawable could be anything.
	}

    /**
     * <p>Returns the focus state in the drop down.</p>
     *
     * @return true always if hijacking focus
     */
    @Override
    public boolean hasWindowFocus() {
        return mHijackFocus || super.hasWindowFocus();
    }

    /**
     * <p>Returns the focus state in the drop down.</p>
     *
     * @return true always if hijacking focus
     */
    @Override
    public boolean isFocused() {
        return mHijackFocus || super.isFocused();
    }

    /**
     * <p>Returns the focus state in the drop down.</p>
     *
     * @return true always if hijacking focus
     */
    @Override
    public boolean hasFocus() {
        return mHijackFocus || super.hasFocus();
    }

}
