package com.example.examplenetworkmelon;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ProductAdapter extends BaseAdapter {

	List<Product> items = new ArrayList<Product>();
	Context mContext;
	
	public ProductAdapter(Context context) {
		mContext = context;
	}
	
	public void add(Product product) {
		product.productImage = product.productImage.replaceAll("\\\\", "");
		items.add(product);
		notifyDataSetChanged();
	}
	
	public void addAll(List<Product> products) {
		for (Product p : products) {
			p.productImage = p.productImage.replaceAll("\\\\", "");
			items.add(p);
		}
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemView view;
		if (convertView == null) {
			view = new ItemView(mContext);
		} else {
			view = (ItemView)convertView;
		}
		view.setProduct(items.get(position));
		return view;
	}

}
