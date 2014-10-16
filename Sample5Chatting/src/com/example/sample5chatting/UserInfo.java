package com.example.sample5chatting;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.packet.Presence;

public class UserInfo {
	RosterEntry user;
	Presence status;
	@Override
	public String toString() {
		return user.getUser() + "(" + status.getStatus() + ")";
	}
}
