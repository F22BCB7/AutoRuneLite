package org.osrs.api.methods;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.osrs.api.objects.InventoryItem;
import org.osrs.api.objects.RSWidget;
import org.osrs.api.objects.WidgetItem;
import org.osrs.api.wrappers.Client;
import org.osrs.util.Data;

public class Inventory extends MethodDefinition{
	private final int INVENTORY_ITEM_CONTAINER_PARENT = 149;
	private final int INVENTORY_ITEM_CONTAINER_CHILD = 0;
	private final int BANK_INVENTORY_INTERFACE_PARENT = 15;
	private final int BANK_INVENTORY_INTERFACE_CHILD = 3;
	private final int INVENTORY_INTERFACE_RESIZE_MODE_PARENT = 164;
	private final int INVENTORY_INTERFACE_RESIZE_MODE_CHILD = 73;

	//TODO use ItemContainer instead of widgets - itemContainer.bucket[30].next.ids[equipIndex]
	public Inventory(MethodContext context){
		super(context);
	}
	/**
	 * Checks the inventory to see if it contains an item
	 * with the given id.
	 * @param itemId
	 * @return true if found
	 */
	public boolean containsItem(int itemId) {
		return getItem(itemId) != null;
	}   
	/**
	 * Checks the inventory to see if it contains all items
	 * with the given ids.
	 * @param itemIds
	 * @return true if found
	 */
	public boolean containsItems(int...itemIDs){
		for(int id : itemIDs){
			if(!containsItem(id))
				return false;
		}
		return true;
	}
	/**
	 * Returns the amount of items found in the inventory.
	 * @return itemCount
	 */
	public int getCount(){
		int count = 0;
		RSWidget inventory = methods.widgets.getChild(INVENTORY_ITEM_CONTAINER_PARENT, INVENTORY_ITEM_CONTAINER_CHILD);
		if(methods.bank.isOpen()){
			inventory = methods.widgets.getChild(BANK_INVENTORY_INTERFACE_PARENT, BANK_INVENTORY_INTERFACE_CHILD);
			if(inventory!=null){
				for(RSWidget child : inventory.getChildren()){
					if(child!=null){
						if(child.getInternal().itemID()!=-1 && child.getInternal().itemID()!=6512){
							count++;
						}
					}
				}
			}
			return count;
		}
		if (inventory == null) return count;
		int[] ids = inventory.getInternal().itemIDs();
		if (ids == null) return count;
		for (int i = 0; i < ids.length; i++) {
			if(ids[i]-1!=-1){
				count++;
			}
		}
		return count;
	}
	/**
	 * Returns the amount of items found in the inventory
	 * with the given id.
	 * @param id
	 * @return itemCount
	 */
	public int getCount(int id){
		int count = 0;
		RSWidget inventory = methods.widgets.getChild(INVENTORY_ITEM_CONTAINER_PARENT, INVENTORY_ITEM_CONTAINER_CHILD);
		if(methods.bank.isOpen()){
			inventory = methods.widgets.getChild(BANK_INVENTORY_INTERFACE_PARENT, BANK_INVENTORY_INTERFACE_CHILD);
			if(inventory!=null){
				for(RSWidget child : inventory.getChildren()){
					if(child!=null){
						if(child.getInternal().itemID()==id && child.getInternal().itemID()!=6512){
							count++;
						}
					}
				}
			}
			return count;
		}
		if (inventory == null) return count;
		int[] ids = inventory.getInternal().itemIDs();
		if (ids == null) return count;
		for (int i = 0; i < ids.length; i++) {
			if(ids[i]-1!=-1 && ids[i]-1==id){
				count++;
			}
		}
		return count;
	}
	/**
	 * Returns the amount of items found in the inventory
	 * with the given id(s).
	 * @param itemID(s)
	 * @return itemCount
	 */
	public int getCount(int...itemIDs){
		int count = 0;
		RSWidget inventory = methods.widgets.getChild(INVENTORY_ITEM_CONTAINER_PARENT, INVENTORY_ITEM_CONTAINER_CHILD);
		if(methods.bank.isOpen()){
			inventory = methods.widgets.getChild(BANK_INVENTORY_INTERFACE_PARENT, BANK_INVENTORY_INTERFACE_CHILD);
			if(inventory!=null){
				for(RSWidget child : inventory.getChildren()){
					if(child!=null){
						if(child.getInternal().itemID()!=-1 && child.getInternal().itemID()!=6512){
							for(int id : itemIDs){
								if(child.getInternal().itemID()==id){
									count++;
									break;
								}
							}
						}
					}
				}
			}
			return count;
		}
		if (inventory == null) return count;
		int[] ids = inventory.getInternal().itemIDs();
		if (ids == null) return count;
		for (int i = 0; i < ids.length; i++) {
			if(ids[i]-1!=-1){
				for(int id : itemIDs){
					if(ids[i]-1==id){
						count++;
						break;
					}
				}
			}
		}
		return count;
	}
	/**
	 * Returns the amount of items found in the inventory
	 * that do not have an id found in the given id(s).
	 * @param itemID(s)
	 * @return
	 */
	public int getCountExcept(int... itemIDs) {
		int count = 0;
		RSWidget inventory = methods.widgets.getChild(INVENTORY_ITEM_CONTAINER_PARENT, INVENTORY_ITEM_CONTAINER_CHILD);
		if(methods.bank.isOpen()){
			inventory = methods.widgets.getChild(BANK_INVENTORY_INTERFACE_PARENT, BANK_INVENTORY_INTERFACE_CHILD);
			if(inventory!=null){
				for(RSWidget child : inventory.getChildren()){
					if(child!=null){
						if(child.getInternal().itemID()!=-1 && child.getInternal().itemID()!=6512){
							boolean flag=false;
							for(int id : itemIDs){
								if(child.getInternal().itemID()==id){
									flag=true;
									break;
								}
							}
							if(!flag)
								count++;
						}
					}
				}
			}
			return count;
		}
		if (inventory == null) return count;
		int[] ids = inventory.getInternal().itemIDs();
		if (ids == null) return count;
		for (int i = 0; i < ids.length; i++) {
			if(ids[i]-1!=-1){
				boolean flag=false;
				for(int id : itemIDs){
					if(ids[i]-1==id){
						flag=true;
						break;
					}
				}
				if(!flag)
					count++;
			}
		}
		return count;
	}
	/**
	 * Returns the amount of items found in the inventory
	 * with the given id.
	 * @param id
	 * @return itemCount
	 */
	public int getStackSize(int id){
		RSWidget inventory = methods.widgets.getChild(INVENTORY_ITEM_CONTAINER_PARENT, INVENTORY_ITEM_CONTAINER_CHILD);
		if(methods.bank.isOpen()){
			inventory = methods.widgets.getChild(BANK_INVENTORY_INTERFACE_PARENT, BANK_INVENTORY_INTERFACE_CHILD);
			if(inventory!=null){
				for(RSWidget child : inventory.getChildren()){
					if(child!=null){
						if(child.getInternal().itemID()==id){
							return child.getInternal().itemQuantity();
						}
					}
				}
			}
			return 0;
		}
		if (inventory == null) return 0;
		int[] ids = inventory.getInternal().itemIDs();
		int[] stacks = inventory.getInternal().itemQuantities();
		if (ids == null || stacks == null) return 0;
		for (int i = 0; i < ids.length; i++) {
			if(ids[i]-1!=-1 && ids[i]-1==id){
				return stacks[i];
			}
		}
		return 0;
	}
	/**
	 * Returns the first inventory index that contains
	 * an item with the given id.
	 * @param itemID
	 * @return inventoryIndex
	 */
	public int getIndex(int itemID) {
		RSWidget inventory = methods.widgets.getChild(INVENTORY_ITEM_CONTAINER_PARENT, INVENTORY_ITEM_CONTAINER_CHILD);
		if(methods.bank.isOpen()){
			inventory = methods.widgets.getChild(BANK_INVENTORY_INTERFACE_PARENT, BANK_INVENTORY_INTERFACE_CHILD);
			if(inventory!=null){
				for(RSWidget child : inventory.getChildren()){
					if(child!=null){
						if(child.getInternal().itemID()==itemID){
							return child.getIndex();
						}
					}
				}
			}
			return 0;
		}
		if (inventory == null) return -1;
		int[] ids = inventory.getInternal().itemIDs();
		if (ids == null) return -1;
		for (int i = 0; i < ids.length; i++) {
			if(ids[i]-1!=-1 && ids[i]-1==itemID){
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns the first inventory index that contains 
	 * an with the same ID as the item given.
	 * @param item
	 * @return index
	 */
	public int getIndex(WidgetItem item) {
		return getIndex(item.getID());
	}
	/**
	 * Returns the item at the given index.
	 * Will return null if no item is at index.
	 * @param index
	 * @return item
	 */
	public InventoryItem getItemAtIndex(int index){
		RSWidget inventory = methods.widgets.getChild(INVENTORY_ITEM_CONTAINER_PARENT, INVENTORY_ITEM_CONTAINER_CHILD);
		if(methods.bank.isOpen()){
			inventory = methods.widgets.getChild(BANK_INVENTORY_INTERFACE_PARENT, BANK_INVENTORY_INTERFACE_CHILD);
			if(inventory!=null){
				for(RSWidget child : inventory.getChildren()){
					if(child!=null){
						if(child.getIndex()==index && child.getInternal().itemID()!=-1 && child.getInternal().itemID()!=6512){
							return new InventoryItem(child.getInternal().itemID(), child.getInternal().itemQuantity(), child.getIndex());
						}
					}
				}
			}
			return null;
		}
		if (inventory == null) return null;
		int[] ids = inventory.getInternal().itemIDs();
		int[] stacks = inventory.getInternal().itemQuantities();
		if (ids == null || stacks == null) return null;
		for (int i = 0; i < ids.length; i++) {
			if(ids[i]-1!=-1 && index==i){
				return new InventoryItem(ids[i]-1, stacks[i], i);
			}
		}
		return null;
	}
	/**
	 * Returns the first inventory item with given id
	 * @param id
	 * @return item
	 */
	public InventoryItem getItem(int id){
		RSWidget inventory = methods.widgets.getChild(INVENTORY_ITEM_CONTAINER_PARENT, INVENTORY_ITEM_CONTAINER_CHILD);
		if(methods.bank.isOpen()){
			inventory = methods.widgets.getChild(BANK_INVENTORY_INTERFACE_PARENT, BANK_INVENTORY_INTERFACE_CHILD);
			if(inventory!=null){
				for(RSWidget child : inventory.getChildren()){
					if(child!=null){
						if(child.getInternal().itemID()==id){
							return new InventoryItem(child.getInternal().itemID(), child.getInternal().itemQuantity(), child.getIndex());
						}
					}
				}
			}
			return null;
		}
		if (inventory == null) return null;
		int[] ids = inventory.getInternal().itemIDs();
		int[] stacks = inventory.getInternal().itemQuantities();
		if (ids == null || stacks == null) return null;
		for (int i = 0; i < ids.length; i++) {
			if(ids[i]-1!=-1 && ids[i]-1==id){
				return new InventoryItem(ids[i]-1, stacks[i], i);
			}
		}
		return null;
	}
	/**
	 * Returns the first inventory item with an
	 * id contained in the given list.
	 * @param itemID(s)
	 * @return item
	 */
	public InventoryItem getItem(int...itemIDs){
		RSWidget inventory = methods.widgets.getChild(INVENTORY_ITEM_CONTAINER_PARENT, INVENTORY_ITEM_CONTAINER_CHILD);
		if(methods.bank.isOpen()){
			inventory = methods.widgets.getChild(BANK_INVENTORY_INTERFACE_PARENT, BANK_INVENTORY_INTERFACE_CHILD);
			if(inventory!=null){
				for(RSWidget child : inventory.getChildren()){
					if(child!=null){
						if(child.getInternal().itemID()!=-1 && child.getInternal().itemID()!=6512){
							for(int id : itemIDs){
								if(child.getInternal().itemID()==id){
									return new InventoryItem(child.getInternal().itemID(), child.getInternal().itemQuantity(), child.getIndex());
								}
							}
						}
					}
				}
			}
			return null;
		}
		if (inventory == null) return null;
		int[] ids = inventory.getInternal().itemIDs();
		int[] stacks = inventory.getInternal().itemQuantities();
		if (ids == null || stacks == null) return null;
		for (int i = 0; i < ids.length; i++) {
			if(ids[i]-1!=-1){
				for(int id : itemIDs){
					if(ids[i]-1==id)
						return new InventoryItem(ids[i]-1, stacks[i], i);
				}
			}
		}
		return null;
	}
	/**
	 * Returns the first inventory item a given name.
	 * @param name
	 * @return item
	 */
	public InventoryItem getItem(String name){
		RSWidget inventory = methods.widgets.getChild(INVENTORY_ITEM_CONTAINER_PARENT, INVENTORY_ITEM_CONTAINER_CHILD);
		if(methods.bank.isOpen()){
			inventory = methods.widgets.getChild(BANK_INVENTORY_INTERFACE_PARENT, BANK_INVENTORY_INTERFACE_CHILD);
			if(inventory!=null){
				for(RSWidget child : inventory.getChildren()){
					if(child!=null){
						if(child.getInternal().itemID()!=-1 && child.getInternal().itemID()!=6512){
							InventoryItem item = new InventoryItem(child.getInternal().itemID(), child.getInternal().itemQuantity(), child.getIndex());
							if(name.equals(item.getName()))
								return item;
						}
					}
				}
			}
			return null;
		}
		if (inventory == null) return null;
		int[] ids = inventory.getInternal().itemIDs();
		int[] stacks = inventory.getInternal().itemQuantities();
		if (ids == null || stacks == null) return null;
		for (int i = 0; i < ids.length; i++) {
			if(ids[i]-1!=-1){
				InventoryItem item = new InventoryItem(ids[i]-1, stacks[i], i);
				if(name.equals(item.getName()))
					return item;
			}
		}
		return null;
	}
	/**
	 * Returns the first itemID with the given name.
	 * Returns -1 if no item found in inventory.
	 * @param name
	 * @return itemID
	 */
	public int getItemID(String name){
		RSWidget inventory = methods.widgets.getChild(INVENTORY_ITEM_CONTAINER_PARENT, INVENTORY_ITEM_CONTAINER_CHILD);
		if(methods.bank.isOpen()){
			inventory = methods.widgets.getChild(BANK_INVENTORY_INTERFACE_PARENT, BANK_INVENTORY_INTERFACE_CHILD);
			if(inventory!=null){
				for(RSWidget child : inventory.getChildren()){
					if(child!=null){
						if(child.getInternal().itemID()!=-1 && child.getInternal().itemID()!=6512){
							InventoryItem item = new InventoryItem(child.getInternal().itemID(), child.getInternal().itemQuantity(), child.getIndex());
							if(name.equals(item.getName()))
								return item.getID();
						}
					}
				}
			}
			return -1;
		}
		if (inventory == null) return -1;
		int[] ids = inventory.getInternal().itemIDs();
		int[] stacks = inventory.getInternal().itemQuantities();
		if (ids == null || stacks == null) return -1;
		for (int i = 0; i < ids.length; i++) {
			if(ids[i]-1!=-1){
				InventoryItem item = new InventoryItem(ids[i]-1, stacks[i], i);
				if(name.equals(item.getName()))
					return ids[i]-1;
			}
		}
		return -1;
	}
	/**
	 * Checks to see if the inventory is being displayed or not.
	 * @return true if displayed
	 */
	public boolean isDisplayed() {
		if(methods.bank.isOpen()){
			RSWidget inventory = methods.widgets.getChild(BANK_INVENTORY_INTERFACE_PARENT, BANK_INVENTORY_INTERFACE_CHILD);
			if(inventory!=null){
				return true;
			}
		}
		else{
			RSWidget widget = getInventoryWidget();
			if(widget!=null){
				if(((Client)Data.clientInstance).resizeMode()){
					return !widget.getInternal().isHidden();
				}
			}
		}
		return methods.tabs.inventory.isSelected();
	}
	/**
	 * Grabs all the inventory items.
	 * @return all inventory items.
	 */
	public InventoryItem[] getItems() {
		ArrayList<InventoryItem> items = new ArrayList<InventoryItem>();
		RSWidget inventory = methods.widgets.getChild(INVENTORY_ITEM_CONTAINER_PARENT, INVENTORY_ITEM_CONTAINER_CHILD);
		if(methods.bank.isOpen()){
			inventory = methods.widgets.getChild(BANK_INVENTORY_INTERFACE_PARENT, BANK_INVENTORY_INTERFACE_CHILD);
			if(inventory!=null){
				for(RSWidget child : inventory.getChildren()){
					if(child!=null){
						if(child.getInternal().itemID()!=-1 && child.getInternal().itemID()!=6512){
							items.add(new InventoryItem(child.getInternal().itemID(), child.getInternal().itemQuantity(), child.getIndex()));
						}
					}
				}
			}
			return items.toArray(new InventoryItem[]{});
		}
		if (inventory == null) return new InventoryItem[]{};
		int[] ids = inventory.getInternal().itemIDs();
		int[] stacks = inventory.getInternal().itemQuantities();
		if (ids == null || stacks == null) return null;
		for (int i = 0; i < ids.length; i++) {
			InventoryItem item = null;
			if(ids[i]-1!=-1){
				item = new InventoryItem(ids[i]-1, stacks[i], i);
			}
			items.add(item);
		}
		return items.toArray(new InventoryItem[]{});
	}
	/**
	 * Returns all inventory items with the given id(s).
	 * @param id(s)
	 * @return matching items
	 * 
	 * 
	 */
	public InventoryItem[] getItems(int...itemIDs){
		ArrayList<InventoryItem> items = new ArrayList<InventoryItem>();
		RSWidget inventory = methods.widgets.getChild(INVENTORY_ITEM_CONTAINER_PARENT, INVENTORY_ITEM_CONTAINER_CHILD);
		if(methods.bank.isOpen()){
			inventory = methods.widgets.getChild(BANK_INVENTORY_INTERFACE_PARENT, BANK_INVENTORY_INTERFACE_CHILD);
			if(inventory!=null){
				for(RSWidget child : inventory.getChildren()){
					if(child!=null){
						if(child.getInternal().itemID()!=-1 && child.getInternal().itemID()!=6512){
							for(int id : itemIDs){
								if(child.getInternal().itemID()==id){
									items.add(new InventoryItem(child.getInternal().itemID(), child.getInternal().itemQuantity(), child.getIndex()));
								}
							}
						}
					}
				}
			}
			return items.toArray(new InventoryItem[]{});
		}
		if (inventory == null) return null;
		int[] ids = inventory.getInternal().itemIDs();
		int[] stacks = inventory.getInternal().itemQuantities();
		if (ids == null || stacks == null) return null;
		for (int i = 0; i < ids.length; i++) {
			if(ids[i]-1!=-1){
				for(int id : itemIDs){
					if(ids[i]-1==id)
						items.add(new InventoryItem(ids[i]-1, stacks[i], i));
				}
			}
		}
		return items.toArray(new InventoryItem[]{});
	}
	/**
	 * Retrieves all inventory items that are not
	 * defined by id in param
	 * @param excludedIDs
	 * @return All other inventory items
	 */
	public InventoryItem[] getItemsExcluding(int...itemIDs){
		ArrayList<InventoryItem> items = new ArrayList<InventoryItem>();
		RSWidget inventory = methods.widgets.getChild(INVENTORY_ITEM_CONTAINER_PARENT, INVENTORY_ITEM_CONTAINER_CHILD);
		if(methods.bank.isOpen()){
			inventory = methods.widgets.getChild(BANK_INVENTORY_INTERFACE_PARENT, BANK_INVENTORY_INTERFACE_CHILD);
			if(inventory!=null){
				for(RSWidget child : inventory.getChildren()){
					if(child!=null){
						if(child.getInternal().itemID()!=-1 && child.getInternal().itemID()!=6512){
							boolean flag=false;
							for(int id : itemIDs){
								if(child.getInternal().itemID()==id){
									flag=true;
									break;
								}
							}
							if(!flag)
								items.add(new InventoryItem(child.getInternal().itemID(), child.getInternal().itemQuantity(), child.getIndex()));
						}
					}
				}
			}
			return items.toArray(new InventoryItem[]{});
		}
		if (inventory == null) return new InventoryItem[]{};
		int[] ids = inventory.getInternal().itemIDs();
		int[] stacks = inventory.getInternal().itemQuantities();
		if (ids == null || stacks == null) return new InventoryItem[]{};
		for (int i = 0; i < ids.length; i++) {
			if(ids[i]-1!=-1){
				boolean flag=false;
				for(int id : itemIDs){
					if(ids[i]-1==id){
						flag=true;
						break;
					}
				}
				if(!flag)
					items.add(new InventoryItem(ids[i]-1, stacks[i], i));
			}
		}
		return items.toArray(new InventoryItem[]{});
	}
	/**
	 * Retrieves the next inventory item with the given id(s) ignoring
	 * an inventory item specified. Useful in situations when trying
	 * to speed-drop and wanting to cut-down time from 
	 * client->server communication lag; hovering, ect. 
	 * @param ignore
	 * @param ids
	 * @return the next item
	 */
	public InventoryItem getNextItem(InventoryItem ignore, int...itemIDs){
		RSWidget inventory = methods.widgets.getChild(INVENTORY_ITEM_CONTAINER_PARENT, INVENTORY_ITEM_CONTAINER_CHILD);
		if(methods.bank.isOpen()){
			inventory = methods.widgets.getChild(BANK_INVENTORY_INTERFACE_PARENT, BANK_INVENTORY_INTERFACE_CHILD);
			if(inventory!=null){
				for(RSWidget child : inventory.getChildren()){
					if(child!=null && child.getIndex()!=ignore.getIndex()){
						if(child.getInternal().itemID()!=-1 && child.getInternal().itemID()!=6512){
							boolean flag=false;
							for(int id : itemIDs){
								if(child.getInternal().itemID()==id){
									flag=true;
									break;
								}
							}
							if(!flag)
								return new InventoryItem(child.getInternal().itemID(), child.getInternal().itemQuantity(), child.getIndex());
						}
					}
				}
			}
			return null;
		}
		if (inventory == null) return null;
		int[] ids = inventory.getInternal().itemIDs();
		int[] stacks = inventory.getInternal().itemQuantities();
		if (ids == null || stacks == null) return null;
		for (int i = 0; i < ids.length; i++) {
			if(i==ignore.getIndex())
				continue;
			if(ids[i]-1!=-1){
				for(int id : itemIDs){
					if(ids[i]-1==id)
						return new InventoryItem(ids[i]-1, stacks[i], i);
				}
			}
		}
		return null;
	}
	/**
	 * Checks to see if the inventory is full
	 * @return true if full
	 */
	public boolean isFull(){
		return getCount()==28;
	}
	/**
	 * Returns the selected index if one is
	 * @return item
	 */
	public int getSelectedIndex(){
		if(hasSelectedItem()){
			return ((Client)Data.clientInstance).lastSelectedItemIndex();
		}
		return -1;
	}
	/**
	 * Returns the selected item if one is
	 * @return item
	 */
	public InventoryItem getSelectedItem(){
		if(hasSelectedItem()){
			return getItemAtIndex(((Client)Data.clientInstance).lastSelectedItemIndex());
		}
		return null;
	}
	/**
	 * Returns if the inventory has an item selected
	 * @return
	 */
	public boolean hasSelectedItem(){
		return ((Client)Data.clientInstance).itemSelectionState()==1;
	}
	public RSWidget getInventoryWidget(){
		if(((Client)Data.clientInstance).resizeMode()){
			if(methods.bank.isOpen())
				return methods.widgets.getChild(BANK_INVENTORY_INTERFACE_PARENT, BANK_INVENTORY_INTERFACE_CHILD);
			return methods.widgets.getChild(INVENTORY_INTERFACE_RESIZE_MODE_PARENT, INVENTORY_INTERFACE_RESIZE_MODE_CHILD);
		}
		return methods.widgets.getChild(INVENTORY_ITEM_CONTAINER_PARENT, INVENTORY_ITEM_CONTAINER_CHILD);
	}
	public int getWidgetID(){
		RSWidget widget = getInventoryWidget();
		if(widget!=null)
			return widget.getID();
		return -1;
	}
	public RSWidget getInventoryContainer(){
		if(methods.bank.isOpen())
			return methods.widgets.getChild(BANK_INVENTORY_INTERFACE_PARENT, BANK_INVENTORY_INTERFACE_CHILD);
		return methods.widgets.getChild(INVENTORY_ITEM_CONTAINER_PARENT, INVENTORY_ITEM_CONTAINER_CHILD);
	}
	public int getContainerID(){
		RSWidget container = getInventoryContainer();
		if(container!=null)
			return container.getID();
		return -1;
	}
	public Rectangle getBounds(){
		RSWidget widget = getInventoryWidget();
		if(widget!=null){
			Point loc = widget.getAbsolutePosition();
			if(((Client)Data.clientInstance).resizeMode())
				return new Rectangle(loc.x+20, loc.y+14, 42*4, 36*7);
			return new Rectangle(loc.x+20, loc.y+14, 42*4, 36*7);
		}
		return new Rectangle(-1, -1, -1, -1);
	}
}
