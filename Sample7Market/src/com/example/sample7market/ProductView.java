package com.example.sample7market;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductView extends FrameLayout {

	public ProductView(Context context) {
		super(context);
		init();
	}

	ImageView imageView;
	TextView nameView, priceView;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_product, this);
		imageView = (ImageView)findViewById(R.id.image_product);
		nameView = (TextView)findViewById(R.id.text_name);
		priceView = (TextView)findViewById(R.id.text_price);
	}
	
	public void setProduct(Product product) {
		nameView.setText(product.productName);
		priceView.setText("price : " + product.productPrice);
		ImageLoader.getInstance().displayImage(product.productImage, imageView);
	}
	

}
