package com.example.sample7slidingmenu;

public class MenuItem {
	public String name;
	public int menuId;
	
	public MenuItem(String name, int menuId) {
		this.name = name;
		this.menuId = menuId;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
