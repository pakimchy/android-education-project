package com.example.exampleaddressbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddressItemView extends LinearLayout {

	public AddressItemView(Context context) {
		super(context);
		init();
	}
	
	TextView nameView, phoneView, homeView, officeView;
	Address mAddress;
	
	private void init() {
		setOrientation(LinearLayout.VERTICAL);
		LayoutInflater.from(getContext()).inflate(R.layout.address_item, this);
		nameView = (TextView)findViewById(R.id.text_name);
		phoneView = (TextView)findViewById(R.id.text_phone);
		homeView = (TextView)findViewById(R.id.text_home);
		officeView = (TextView)findViewById(R.id.text_office);
	}
	
	public void setAddress(Address addr) {
		mAddress = addr;
		nameView.setText("Name : " + addr.name);
		phoneView.setText("Phone : " + addr.phone);
		homeView.setText("Home : " + addr.home);
		officeView.setText("Office : " + addr.office);
	}
}
