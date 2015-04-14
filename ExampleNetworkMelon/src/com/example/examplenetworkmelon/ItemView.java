package com.example.examplenetworkmelon;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemView extends LinearLayout {

	public ItemView(Context context) {
		super(context);
		init();
	}
	
	ImageView iconView;
	TextView nameView;
	TextView priceView;
	Product product;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		setOrientation(LinearLayout.HORIZONTAL);
		iconView = (ImageView)findViewById(R.id.image_icon);
		nameView = (TextView)findViewById(R.id.text_name);
		priceView = (TextView)findViewById(R.id.text_price);
	}
	
	public void setProduct(Product product) {
		this.product = product;
		nameView.setText(product.productName);
		priceView.setText("가격 : "+product.productPrice);
		ImageLoader.getInstance().displayImage(product.productImage, iconView);
	}

}
