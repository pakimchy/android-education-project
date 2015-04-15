package com.example.sample7market;

import com.google.gson.annotations.SerializedName;

public class Product {
	@SerializedName("ProductCode")
	int productCode;
	
	@SerializedName("ProductName")
	String productName;
	
	@SerializedName("ProductPrice")
	int productPrice;
	
	@SerializedName("ProductImage")
	String productImage;
	
	@Override
	public String toString() {
		return productName;
	}
}
