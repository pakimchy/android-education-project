package com.example.examplexmpp;

import java.util.Date;

public class ChatMessage {
	public String mateId;
	public String message;
	public long created;
	public boolean bReceived;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (bReceived) {
			sb.append(mateId);
		} else {
			sb.append("me");
		}
		sb.append(":").append(message).append("(").append(new Date(created).toString()).append(")");
		return sb.toString();
	}
}
