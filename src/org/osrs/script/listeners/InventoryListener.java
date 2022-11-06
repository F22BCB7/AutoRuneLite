package org.osrs.script.listeners;

import java.util.EventListener;

import org.osrs.script.events.InventoryEvent;

public interface InventoryListener extends EventListener{
	abstract void updateInventory(InventoryEvent event);
}
