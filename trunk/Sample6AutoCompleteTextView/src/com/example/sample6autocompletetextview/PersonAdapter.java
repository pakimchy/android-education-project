package com.example.sample6autocompletetextview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class PersonAdapter extends ArrayAdapter<Person> {

	public PersonAdapter(Context context) {
		super(context, 0);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PersonView view;
		if (convertView == null) {
			view = new PersonView(getContext());
		} else {
			view = (PersonView)convertView;
		}
		view.setPerson((Person)getItem(position));
		return view;
	}

}
