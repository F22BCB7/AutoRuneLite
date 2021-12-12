package org.osrs.script.events;

public class VarcEvent {
	public static final int GET_STRING_EVENT_TYPE = 0;
	public static final int PUT_STRING_EVENT_TYPE = 1;
	public static final int GET_INT_EVENT_TYPE = 2;
	public static final int PUT_INT_EVENT_TYPE = 3;
	
	public int mapKey;
	public Object value;
	public int eventType;
	public VarcEvent(int key, Object value, int type){
		this.mapKey=key;
		this.value=value;
		this.eventType=type;
	}
}
