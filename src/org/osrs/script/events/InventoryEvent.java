package org.osrs.script.events;

public class InventoryEvent {
    public enum EventType {
        ADD_ITEM, UPDATE_ITEM_STACK, REMOVE_ITEM
    }
    
    private EventType eventType;
    private int itemID;
    private int stackChange; // can be negative (partial deposit)
    private int inventoryIndex;
    
    public InventoryEvent(EventType type, int id, int count, int index) {
        this.eventType = type;
        this.itemID = id;
        this.stackChange = count;
        this.inventoryIndex = index;
    }
    
    public EventType getEventType() {
        return eventType;
    }

    public int getItemID() {
        return itemID;
    }

    public int getStackChange() {
        return stackChange;
    }

    public int getInventoryIndex() {
        return inventoryIndex;
    }
    
    @Override
    public String toString() {
        return String.format("InventoryEvent[type=%s, itemID=%d, stackChange=%d, inventoryIndex=%d]",
                             eventType, itemID, stackChange, inventoryIndex);
    }
}
