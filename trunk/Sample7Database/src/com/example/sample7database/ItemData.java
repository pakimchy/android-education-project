package com.example.sample7database;

import java.io.Serializable;

public class ItemData implements Serializable {
	public long id = -1;
	public String name;
	public String email;
	public String phone;
	public String address;
	
	@Override
	public String toString() {
		return name+"\n\r"+email+"\n\r" + phone + "\n\r" + address;
	}
}
