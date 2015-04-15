package com.example.sample7market;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter {

	List<Product> productlist = new ArrayList<Product>();
	
	public MyAdapter() {
		
	}
	
	public void addAll(List<Product> products) {
		productlist.addAll(products);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return productlist.size();
	}

	@Override
	public Object getItem(int position) {
		return productlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ProductView view;
		if (convertView == null) {
			view = new ProductView(parent.getContext());
		} else {
			view = (ProductView)convertView;
		}
		
		view.setProduct(productlist.get(position));
		return view;
	}

}
