package org.osrs.api.methods;

import java.util.ArrayList;

import org.osrs.api.objects.RSInterface;
import org.osrs.api.objects.RSPlayer;
import org.osrs.api.objects.RSWidget;
import org.osrs.api.objects.WidgetItem;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.ItemDefinition;
import org.osrs.api.wrappers.ItemStorage;
import org.osrs.api.wrappers.PlayerDefinition;

public class Equipment extends MethodDefinition{
	public Equipment(MethodContext context){
		super(context);
	}
	/* Widget Indexs */

	public static final int INTERFACE_EQUIPMENT = 387;
	public static final int ITEM_SLOTS = 12;
	
	public static final int HELMET = 15;
	public static final int CAPE = 16;
	public static final int NECK = 17;
	public static final int WEAPON = 18;
	public static final int BODY = 19;
	public static final int SHIELD = 20;
	public static final int LEGS = 21;
	public static final int HANDS = 22;
	public static final int FEET = 23;
	public static final int RING = 24;
	public static final int AMMO = 25;
	public static final int[] childItemSlots = new int[]{HELMET, CAPE, NECK, WEAPON, BODY, SHIELD, LEGS, HANDS, FEET, RING, AMMO};
	/**
	 * Gets the equipment array.
	 * @return An array containing all equipped items
	 * TODO use ItemContainer instead of widgets - itemContainer.bucket[30].next.ids[equipIndex]
	 */
	public WidgetItem[] getItems() {
		RSPlayer local = methods.players.getLocalPlayer();
		if(local!=null){
			PlayerDefinition localDef = local.getDefinition();
			if(localDef!=null){
				int[] equips = localDef.equipmentIDs();
				RSInterface iface = methods.widgets.get(INTERFACE_EQUIPMENT);
				if(iface!=null){
					RSWidget[] equip = iface.getChildren();
					ArrayList<WidgetItem> items = new ArrayList<WidgetItem>();
					for(int i=0;i<childItemSlots.length;++i){
						int index = childItemSlots[i];
						if(equips[i]<=512)
							continue;//nothing equipped
						RSWidget ic = equip[index];
						if(ic==null)
							continue;
						RSWidget w = ic.getChild(1);
						if(w!=null){
							ItemDefinition def = ((Client)methods.botInstance).invoke_getItemDefinition(w.getInternal().itemID());//TODO get ItemDef
							items.add(new WidgetItem(ic, def.name(), equips[i]-512, w.getInternal().itemQuantity()));
						}
					}
					return items.toArray(new WidgetItem[]{});
				}
			}
		}
		return new WidgetItem[]{};
	}
	public ItemStorage getEquipmentItemStorage(){
		return methods.game.getItemStorages()[30];
	}
	/**
	 * Returns the number of items equipped excluding stack sizes.
	 * @return Amount of items currently equipped.
	 */
	public int getCount() {
		ItemStorage storage = getEquipmentItemStorage();
		if(storage!=null)
			return Math.min(storage.ids().length, storage.stackSizes().length);
		return -1;
	}
	/**
	 * Returns the number of items matching a given ID equipped excluding stack
	 * sizes.
	 * @param itemID The item ID to count. Same as the equipment/item id in the
	 *               inventory.
	 * @return Amount of specified item currently equipped.
	 * @see #getItems()
	 */
	public int getCount(final int itemID) {
		int count = 0;
		ItemStorage storage = getEquipmentItemStorage();
		if(storage!=null){
			for(int id : storage.ids()){
				if(id==itemID)
					count++;
			}
		}
		return count;
	}
	public boolean containsItem(int itemID){
		ItemStorage storage = getEquipmentItemStorage();
		if(storage!=null){
			for(int id : storage.ids()){
				if(id==itemID)
					return true;
			}
		}
		return false;
	}
	/**
	 * Checks whether the player has all of the given items equipped.
	 * @param items The item ID to check for. Same as the equipment/item id in the
	 *              inventory.
	 * @return <tt>true</tt> if specified item is currently equipped; otherwise
	 *         <tt>false</tt>.
	 */
	public boolean containsAll(final int... items) {
		for (int item : items) {
			if(!containsItem(item))
				return false;
		}
		return true;
	}
	/**
	 * Checks if the player has one (or more) of the given items equipped.
	 * @param items The IDs of items to check for.
	 * @return <tt>true</tt> if the player has one (or more) of the given items
	 *         equipped; otherwise <tt>false</tt>.
	 */
	public boolean containsOneOf(final int... items) {
		for(int item : items){
			if(containsItem(item))
				return true;
		}
		return false;
	}
	public boolean isEquipmentStatsOpen(){
		for(RSInterface iface : methods.widgets.getAll()){
			if(iface!=null){
				for(RSWidget w : iface.getChildren()){
					if(w!=null && w.isDisplayed() && w.clickMask()==0 && 
							w.spriteID()==297 && w.alpha()==0 && w.boundsIndex()==1){
						return true;
					}
				}
			}
		}
		return false;
	}
}
