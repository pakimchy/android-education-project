package com.example.examplescanwifi;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;

public class MyScanResult {
	ScanResult mResult;
	WifiConfiguration mConfiguration;

	public MyScanResult(ScanResult result) {
		mResult = result;
	}
	
	public MyScanResult(WifiConfiguration configuration) {
		mConfiguration = configuration;
	}
	
	public MyScanResult(ScanResult result, WifiConfiguration configuration) {
		mResult = result;
		mConfiguration = configuration;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (mResult != null) {
			sb.append("SSID : " + mResult.SSID + "\n\r");
			sb.append("BSSID : " + mResult.BSSID + "\n\r");
			sb.append("capabilities : " + mResult.capabilities + "\n\r");
			sb.append("frequency : " + mResult.frequency + "\n\r");
			sb.append("level : " + mResult.level + "\n\r");
			if (mConfiguration != null) {
				sb.append("Configured Network");
			} else {
				sb.append("Not Configured");
			}
//			sb.append("timestamp : " + mResult.timestamp);
		} else if (mConfiguration != null) {
			sb.append("SSID : " + mConfiguration.SSID + "\n\r");
			sb.append("BSSID : " + mConfiguration.BSSID + "\n\r");
			sb.append("NetworkId : " + mConfiguration.networkId+"\n\r");
			sb.append("Not found");
		}
		return sb.toString();
	}
}
