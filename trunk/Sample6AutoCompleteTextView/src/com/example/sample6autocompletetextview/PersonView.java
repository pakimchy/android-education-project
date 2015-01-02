package com.example.sample6autocompletetextview;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonView extends FrameLayout {

	public PersonView(Context context) {
		super(context);
		init();
	}
	
	ImageView iconView;
	TextView nameView;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		iconView = (ImageView)findViewById(R.id.image_icon);
		nameView = (TextView)findViewById(R.id.text_name);
	}
	
	public void setPerson(Person p) {
		iconView.setImageResource(p.resId);
		nameView.setText(p.name);
	}
}
