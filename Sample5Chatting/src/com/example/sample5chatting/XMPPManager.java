package com.example.sample5chatting;

import java.util.ArrayList;
import java.util.Collection;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class XMPPManager {
	private static XMPPManager instance;
	public static XMPPManager getInstance() {
		if (instance == null) {
			instance = new XMPPManager();
		}
		return instance;
	}
	
	private static final String DOMAIN = "dongja94.gonetis.com";
	private static final int PORT = 5222;
	
	ConnectionConfiguration config;
	XMPPConnection mConnection;
	Handler mHandler;
	
	private XMPPManager() {
		mHandler = new Handler(Looper.getMainLooper());
		
		config = new ConnectionConfiguration(DOMAIN, PORT);
		mConnection = new XMPPConnection(config);
	}
	
	public interface OnLoginListener {
		public void onSuccess();
		public void onFail();
	}
	
	public void login(final String username, final String password, final OnLoginListener listener) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if (!mConnection.isConnected()) {
					try {
						mConnection.connect();
					} catch (XMPPException e) {
						e.printStackTrace();
						mHandler.post(new Runnable() {
							
							@Override
							public void run() {
								listener.onFail();
							}
						});
						return;
					}
				}
				
				if (mConnection.isConnected()) {
					try {
						mConnection.login(username, password);
					} catch (XMPPException e) {
						e.printStackTrace();
						mHandler.post(new Runnable() {
							
							@Override
							public void run() {
								listener.onFail();
							}
						});
						return;
					}
				}
				
				// ...
				
				Presence p = new Presence(Presence.Type.available);
				p.setStatus("i am happy");
				mConnection.sendPacket(p);
				
				mConnection.getChatManager().addChatListener(new ChatManagerListener() {
					
					@Override
					public void chatCreated(Chat chat, boolean createdLocally) {
						if (!createdLocally) {
							/// ....
							chat.addMessageListener(new MessageListener() {
								
								@Override
								public void processMessage(Chat chat, Message message) {
									Log.i("XMPPManager", "from : " + message.getFrom() + ", message : " + message.getBody());
								}
							});
						}
					}
				});
				
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						listener.onSuccess();
					}
				});
			}
		}).start();
	}
	
	public interface OnRosterListener {
		public void onResult(ArrayList<UserInfo> list);
	}
	
	public void getRoster(final OnRosterListener listener) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Roster roster = mConnection.getRoster();
				Collection<RosterEntry> list = roster.getEntries();
				final ArrayList<UserInfo> userlist = new ArrayList<UserInfo>();
				for (RosterEntry re : list) {
					UserInfo info = new UserInfo();
					info.user = re;
					info.status = roster.getPresence(re.getUser());
					userlist.add(info);
				}
				roster.addRosterListener(new RosterListener() {
					
					@Override
					public void presenceChanged(Presence arg0) {
						
					}
					
					@Override
					public void entriesUpdated(Collection<String> arg0) {
						
					}
					
					@Override
					public void entriesDeleted(Collection<String> arg0) {
						
					}
					
					@Override
					public void entriesAdded(Collection<String> arg0) {
						
					}
				});
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						listener.onResult(userlist);
					}
				});
			}
		}).start();
	}
	
	public interface OnMessagReceiveListener {
		public void onReceivedMessage(String from, String message);
	}
	
	public Chat createChat(String user, final OnMessagReceiveListener listener) {
		Chat chat = mConnection.getChatManager().createChat(user, new MessageListener() {
			
			@Override
			public void processMessage(Chat arg0, final Message message) {
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						listener.onReceivedMessage(message.getFrom(), message.getBody());
					}
				});
			}
		});
		return chat;
	}

	public interface OnMessageSendListener {
		public void onMessageSent();
	}
	public void sendMessage(final Chat chat, final String message, final OnMessageSendListener listener) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					chat.sendMessage(message);
					mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							listener.onMessageSent();
						}
					});
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
}
