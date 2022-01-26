package org.osrs.api.methods;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

import org.osrs.api.objects.EquipmentItem;
import org.osrs.api.objects.InventoryItem;
import org.osrs.api.objects.RSInterface;
import org.osrs.api.objects.RSWidget;
import org.osrs.api.objects.WidgetItem;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.ItemStorage;
import org.osrs.util.Data;

public class Inventory extends MethodDefinition{
	private RSWidget inventoryWidget = null;
	private InventoryItem[] inventoryItems = null;
	public Inventory(MethodContext context){
		super(context);
	}
	public ItemStorage getInventoryItemStorage(){
		return methods.game.getItemStorages()[29];
	}
	/**
	 * Called after a gameCycle has passed, and will
	 * pre-cache the items. Not for use in scripts.
	 */
	public void updateInventoryItems(){
		try{
			if(inventoryItems==null)
				inventoryItems = new InventoryItem[28];
			int index=0;
			ItemStorage items = getInventoryItemStorage();
			if(items!=null){
				int[] ids = items.ids();
				int[] sizes = items.stackSizes();
				for(int i=0;i<Math.min(ids.length, sizes.length);i++){
					if(inventoryItems[index]==null){
						inventoryItems[index] = new InventoryItem(ids[i], sizes[i], index);
					}
					else{
						inventoryItems[index].updateInfo(ids[i], sizes[i]);
					} 
					index++;
				}
			}
			for(int i=index;i<inventoryItems.length;i++){
				if(inventoryItems[i]==null){
					inventoryItems[i] = new InventoryItem(-1, 0, i);
				}
				else{
					inventoryItems[i].updateInfo(-1, 0);
				}
			}
		}
		catch(NullPointerException e){
			//Can be thrown even with null checks; 
			//e.g. widget changes (opening bank/g.e) mid-processing (like painting InventoryDebug)
			//Nothing IS wrong, so we ignore it, and it updates next cycle. Fuck you try-catch haters :)
		}
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
		if(inventoryItems!=null){
			for(InventoryItem item : inventoryItems){
				if(item!=null && item.getID()!=-1 && item.getID()!=6512){
					count++;
				}
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
		if(inventoryItems!=null){
			for(InventoryItem item : inventoryItems){
				if(item!=null && item.getID()==id && item.getID()!=6512){
					count++;
				}
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
		if(inventoryItems!=null){
			for(InventoryItem item : inventoryItems){
				if(item!=null && item.getID()!=-1 && item.getID()!=6512){
					for(int id : itemIDs){
						if(item.getID()==id){
							count++;
							break;
						}
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
		if(inventoryItems!=null){
			for(InventoryItem item : inventoryItems){
				if(item!=null && item.getID()!=-1 && item.getID()!=6512){
					boolean flag=false;
					for(int id : itemIDs){
						if(item.getID()==id){
							flag=true;
							break;
						}
					}
					if(!flag)
						count++;
				}
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
		if(inventoryItems!=null){
			for(InventoryItem item : inventoryItems){
				if(item!=null && item.getID()==id && item.getID()!=6512){
					return item.getStackSize();
				}
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
		if(inventoryItems!=null){
			for(InventoryItem item : inventoryItems){
				if(item!=null && item.getID()==itemID && item.getID()!=6512){
					return item.getIndex();
				}
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
		if(inventoryItems!=null && index<inventoryItems.length)
			return inventoryItems[index];
		return null;
	}
	/**
	 * Returns the first inventory item with given id
	 * @param id
	 * @return item
	 */
	public InventoryItem getItem(int id){
		if(inventoryItems!=null){
			for(InventoryItem item : inventoryItems){
				if(item!=null && item.getID()==id){
					return item;
				}
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
		if(inventoryItems!=null){
			for(InventoryItem item : inventoryItems){
				for(int id : itemIDs){
					if(item.getID()==id){
						return item;
					}
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
		if(inventoryItems!=null){
			for(InventoryItem item : inventoryItems){
				if(item.getName().equals(name))
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
		if(inventoryItems!=null){
			for(InventoryItem item : inventoryItems){
				if(item.getName().equals(name))
					return item.getID();
			}
		}
		return -1;
	}
	/**
	 * Checks to see if the inventory is being displayed or not.
	 * @return true if displayed
	 */
	public boolean isDisplayed() {
		RSWidget inventory = getInventoryItemsWidget();
		if(inventory!=null){
			return inventory.isDisplayed() || 
					methods.bank.isOpen();
		}
		return false;
	}
	/**
	 * Grabs all the inventory items.
	 * @return all inventory items.
	 */
	public InventoryItem[] getItems() {
		if(inventoryItems==null){
			inventoryItems = new InventoryItem[28];
			for(int i=0;i<28;++i){
				inventoryItems[i] = new InventoryItem(-1, 0, i);
			}
		}
		return inventoryItems;
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
		if(inventoryItems!=null){
			for(InventoryItem item : inventoryItems){
				for(int id : itemIDs){
					if(item.getID()==id){
						items.add(item);
					}
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
		if(inventoryItems!=null){
			for(InventoryItem item : inventoryItems){
				if(item!=null && item.getID()!=-1 && item.getID()!=6512){
					boolean flag=false;
					for(int id : itemIDs){
						if(item.getID()==id){
							flag=true;
							break;
						}
					}
					if(!flag)
						items.add(item);
				}
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
		if(inventoryItems!=null){
			for(InventoryItem item : inventoryItems){
				if(item.getIndex()==ignore.getIndex())
					continue;
				for(int id : itemIDs){
					if(item.getID()==id){
						return item;
					}
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
	public RSWidget getInventoryItemsWidget(){
		if(methods.grandExchange.isGEInventoryOpen()){
			return methods.grandExchange.getInventoryWidget();
		}
		if(methods.equipment.isEquipmentStatsOpen()){
			for(RSInterface iface : methods.widgets.getAll()){
				if(iface!=null){
					for(RSWidget wP : iface.getChildren()){
						if(wP!=null){
							RSWidget[] children = wP.getChildren();
							if(children==null || children.length!=29)
								continue;
							RSWidget check = children[28];
							if(check!=null && check.modelZoom()==1777)
								return wP;
						}
					}
				}
			}
			return null;
		}
		if(inventoryWidget!=null)
			return inventoryWidget;
		findInventoryLoop:for(RSInterface i : methods.widgets.getAll()){
			if(i!=null){
				for(RSWidget ic : i.getChildren()){
					if(ic!=null){
						if(ic.type()==2){
							inventoryWidget = ic;
							break findInventoryLoop;
						}
					}
				}
			}
		}
		return inventoryWidget;
	}
	public int getWidgetID(){
		RSWidget widget = getInventoryItemsWidget();
		if(widget!=null)
			return widget.id();
		return -1;
	}
	public Rectangle getBounds(){
		RSWidget widget = getInventoryItemsWidget();
		if(widget!=null){
			Point loc = widget.getAbsolutePosition();
			return new Rectangle(loc.x, loc.y, 42*4, 36*7);
		}
		return new Rectangle(-1, -1, -1, -1);
	}
}
