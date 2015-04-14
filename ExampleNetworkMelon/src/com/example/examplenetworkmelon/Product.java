package com.example.examplenetworkmelon;

import com.google.gson.annotations.SerializedName;

public class Product {
	@SerializedName("ProductCode")
	public int productCode;
	
	@SerializedName("ProductName")
	public String productName;
	
	@SerializedName("ProductPrice")
	public int productPrice;
	
	@SerializedName("ProductImage")
	public String productImage;
}
