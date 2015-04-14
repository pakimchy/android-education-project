package com.example.sample7networkmodel;

import com.example.sample7networkmodel.NetworkManager.OnResultListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieItemView extends FrameLayout {

	public MovieItemView(Context context) {
		super(context);
		init();
	}
	
	ImageView iconView;
	TextView titleView;
	MovieItem mItem;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_movie, this);
		iconView = (ImageView)findViewById(R.id.image_icon);
		titleView = (TextView)findViewById(R.id.text_title);
	}
	
	ImageRequest mRequest;
	public void setMovieItem(MovieItem item) {
		mItem = item;
		titleView.setText(Html.fromHtml(item.title));
		ImageLoader.getInstance().displayImage(item.image, iconView);
		
//		if (mRequest != null) {
//			mRequest.cancel();
//			mRequest = null;
//		}
//		if (item.image != null && !item.image.equals("")) {
//			mRequest = new ImageRequest(item.image);
//			NetworkManager.getInstance().getNetworkData(getContext(), mRequest, new OnResultListener<Bitmap>() {
//
//				@Override
//				public void onSuccess(NetworkRequest<Bitmap> request,
//						Bitmap result) {
//					if (((ImageRequest)request).getImageUrl().equals(mItem.image)) {
//						iconView.setImageBitmap(result);
//						mRequest = null;
//					}
//				}
//
//				@Override
//				public void onFail(NetworkRequest<Bitmap> request, int code) {
//					if (((ImageRequest)request).getImageUrl().equals(mItem.image)) {
//						iconView.setImageResource(R.drawable.ic_error);
//						mRequest = null;
//					}
//				}
//				
//			});
//			iconView.setImageResource(R.drawable.ic_stub);
//		} else {
//			iconView.setImageResource(R.drawable.ic_empty);
//		}
	}

}
