package com.example.examplenetworkmelon;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class Products {
	@SerializedName("TotalCount")
	public int totalCount;
	
	@SerializedName("Product")
	public ArrayList<Product> productList;
}
