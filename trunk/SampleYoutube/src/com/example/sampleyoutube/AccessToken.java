package com.example.sampleyoutube;

import com.google.gson.annotations.SerializedName;

public class AccessToken {
	@SerializedName("access_token")
	public String accessToken;
	@SerializedName("token_type")
	public String tokenType;
	@SerializedName("expires_in")
	public long expiresIn;
	@SerializedName("refresh_token")
	public String refreshToken;
	
	public long expiresTime;
	
	public void setExpiresTime() {
		expiresTime = System.currentTimeMillis() + expiresIn * 1000;
	}
	
	public boolean isValidToken() {
		if (accessToken == null || accessToken.equals("")) {
			return false;
		}
		return true;
	}
	
	public boolean isExired() {
		long currentTime = System.currentTimeMillis();
		if (currentTime > expiresTime) {
			return true;
		}
		return false;
	}
	
	public boolean isRefreshable() {
		if (refreshToken != null && !refreshToken.equals("")) {
			return true;
		}
		return false;
	}
}
