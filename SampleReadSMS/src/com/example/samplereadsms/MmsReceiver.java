package com.example.samplereadsms;

import java.io.ByteArrayInputStream;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MmsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		byte[] data = intent.getByteArrayExtra("data");
		PduParser parser = new PduParser(data);
		ByteArrayInputStream pduDataStream = new ByteArrayInputStream(data);
		PduHeaders headers = parser.parseHeaders(pduDataStream);
	}

}
