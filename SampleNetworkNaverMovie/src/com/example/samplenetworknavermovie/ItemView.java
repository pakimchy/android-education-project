package com.example.samplenetworknavermovie;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemView extends FrameLayout {

	public ItemView(Context context) {
		super(context);
		init();
	}

	MovieItem mItem;
	
	ImageView iconView;
	TextView titleView;
	TextView directorView;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		iconView = (ImageView)findViewById(R.id.image_icon);
		titleView = (TextView)findViewById(R.id.text_title);
		directorView = (TextView)findViewById(R.id.text_director);
	}

	ImageRequest mRequest;
	public void setMovieItem(MovieItem item) {
		mItem = item;
		titleView.setText(Html.fromHtml(item.title));
		directorView.setText(item.director);
		NetworkManager.getInstance().displayImage(item.image, iconView);
//		if (mRequest != null) {
//			mRequest.setCancel(true);
//		}
//		if (item.image != null && !item.image.equals("")) {
//			mRequest = new ImageRequest(item.image);
//			iconView.setImageResource(R.drawable.ic_stub);
//			NetworkManager.getInstance().getImage(getContext(), mRequest, new NetworkManager.OnResultListener<Bitmap>() {
//
//				@Override
//				public void onSuccess(NetworkRequest request, Bitmap result) {
//					if (mRequest == request) {
//						iconView.setImageBitmap(result);
//						mRequest = null;
//					}
//				}
//
//				@Override
//				public void onFail(NetworkRequest request, int code) {
//					if (mRequest == request) {
//						iconView.setImageResource(R.drawable.ic_error);
//						mRequest = null;
//					}
//				}
//				
//			});
//		} else {
//			iconView.setImageResource(R.drawable.ic_empty);
//		}
		
	}
}
