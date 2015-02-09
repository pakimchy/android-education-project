package com.example.testactionbarcustom;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.support.v7.internal.view.menu.MenuView;
import android.support.v7.internal.view.menu.MenuView.ItemView;
import android.support.v7.widget.ActionMenuPresenter;
import android.support.v7.widget.ActionMenuView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class MyActionMenuPresenter extends ActionMenuPresenter {

	LayoutInflater mInflater;
	public MyActionMenuPresenter(Context context) {
		super(context);
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public MenuView getMenuView(ViewGroup root) {
		ActionMenuView menu = (ActionMenuView)super.getMenuView(root);
		return menu;
	}
	
	@Override
	public ItemView createItemView(ViewGroup parent) {
//		ActionMenuItemView menu = (ActionMenuItemView)super.createItemView(parent);
		MyActionMenuItemView menu = (MyActionMenuItemView)mInflater.inflate(R.layout.my_action_menu_item_view, parent, false);
		parent.setBackgroundColor(Color.RED);
		menu.setBackgroundColor(Color.YELLOW);
		return menu;
	}
	
	

}
