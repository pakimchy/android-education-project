package com.example.sample7market;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ProductList {
	@SerializedName("TotalCount")
	int totalCount;
	@SerializedName("Product")
	ArrayList<Product> productList;
}
