package org.osrs.script.events;

public class MessageEvent{
	public int type;
	public String message;
	public String sender;
	public String unknown;
	public MessageEvent(int type, String message, String sender, String unknown){
		this.type=type;
		this.message=message;
		this.sender=sender;
		this.unknown=unknown;
	}
}
