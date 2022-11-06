package org.osrs.script.events;

public class InventoryEvent {
	public static final int ADD_ITEM = 1;
	public static final int UPDATE_ITEM_STACK = 2;
	public static final int REMOVE_ITEM = 3;
	public int eventType = -1;
	public int itemID = -1;
	public int stackChange = 0;//can be negative (partial deposit)
	public int inventoryIndex = -1;
	public InventoryEvent(int type, int id, int count, int index) {
		eventType = type;
		itemID = id;
		stackChange = count;
		inventoryIndex = index;
	}
}
