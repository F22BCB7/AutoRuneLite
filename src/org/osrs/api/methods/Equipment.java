package org.osrs.api.methods;

import org.osrs.api.objects.EquipmentItem;
import org.osrs.api.objects.RSInterface;
import org.osrs.api.objects.RSWidget;
import org.osrs.api.wrappers.ItemStorage;

public class Equipment extends MethodDefinition{
	public static final int HELMET_INDICE = 0;
	public static final int CAPE_INDICE = 1;
	public static final int NECK_INDICE = 2;
	public static final int WEAPON_INDICE = 3;
	public static final int BODY_INDICE = 4;
	public static final int SHIELD_INDICE = 5;
	public static final int LEGS_INDICE = 7;
	public static final int HANDS_INDICE = 9;
	public static final int FEET_INDICE = 10;
	public static final int RING_INDICE = 12;
	public static final int AMMO_INDICE = 13;
	public static final int[] equipmentIndices = new int[]{
			HELMET_INDICE, 
			CAPE_INDICE, NECK_INDICE, 
			WEAPON_INDICE, BODY_INDICE, SHIELD_INDICE, 
			LEGS_INDICE, 
			HANDS_INDICE, FEET_INDICE, RING_INDICE, 
			AMMO_INDICE
		};
	public Equipment(MethodContext context){
		super(context);
	}
	private RSInterface equipmentParent = null;
	private RSWidget equipmentWindow = null;
	private RSInterface equipmentStatsParent = null;
	private RSWidget equipmentStatsWindow = null;
	private RSWidget equipmentStatsButton = null;
	private RSWidget equipmentStatsExitButton = null;
	private RSInterface priceCheckerParent = null;
	private RSWidget priceCheckerWindow = null;
	private RSWidget priceCheckerExitButton = null;
	private RSWidget priceCheckerButton = null;
	private RSInterface itemsKeptOnDeathParent = null;
	private RSWidget itemsKeptOnDeathWindow = null;
	private RSWidget itemsKeptOnDeathExitButton = null;
	private RSWidget itemsKeptOnDeathButton = null;
	private RSWidget callFollowerButton = null;
	private EquipmentItem[] equipmentItems = null;
	public ItemStorage getEquipmentItemStorage(){
		return methods.game.getItemStorages()[30];
	}
	public void updateEquipmentItems(){
		try{
			if(equipmentItems==null)
				equipmentItems = new EquipmentItem[14];
			int index=0;
			ItemStorage items = getEquipmentItemStorage();
			if(items!=null){
				int[] ids = items.ids();
				int[] sizes = items.stackSizes();
				for(int i=0;i<Math.min(ids.length, sizes.length);i++){
					if(equipmentItems[index]==null){
						equipmentItems[index] = new EquipmentItem(ids[i], sizes[i], index);
					}
					else{
						equipmentItems[index].updateInfo(ids[i], sizes[i]);
					} 
					index++;
				}
			}
			for(int i=index;i<equipmentItems.length;i++){
				if(equipmentItems[i]==null){
					equipmentItems[i] = new EquipmentItem(-1, 0, i);
				}
				else{
					equipmentItems[i].updateInfo(-1, 0);
				}
			}
		}
		catch(Exception e){
			//Can be thrown even with null checks; 
			//e.g. widget changes (opening bank/g.e) mid-processing (like painting EquipmentDebug)
			//Nothing IS wrong, so we ignore it, and it updates next cycle. Fuck you try-catch haters :)
		}
	}
	public EquipmentItem getHelmet(){
		if(equipmentItems!=null)
			return equipmentItems[HELMET_INDICE];
		return null;
	}
	public EquipmentItem getCape(){
		if(equipmentItems!=null)
			return equipmentItems[CAPE_INDICE];
		return null;
	}
	public EquipmentItem getNeck(){
		if(equipmentItems!=null)
			return equipmentItems[NECK_INDICE];
		return null;
	}
	public EquipmentItem getWeapon(){
		if(equipmentItems!=null)
			return equipmentItems[WEAPON_INDICE];
		return null;
	}
	public EquipmentItem getBody(){
		if(equipmentItems!=null)
			return equipmentItems[BODY_INDICE];
		return null;
	}
	public EquipmentItem getShield(){
		if(equipmentItems!=null)
			return equipmentItems[SHIELD_INDICE];
		return null;
	}
	public EquipmentItem getLegs(){
		if(equipmentItems!=null)
			return equipmentItems[LEGS_INDICE];
		return null;
	}
	public EquipmentItem getHands(){
		if(equipmentItems!=null)
			return equipmentItems[HANDS_INDICE];
		return null;
	}
	public EquipmentItem getFeet(){
		if(equipmentItems!=null)
			return equipmentItems[FEET_INDICE];
		return null;
	}
	public EquipmentItem getRing(){
		if(equipmentItems!=null)
			return equipmentItems[RING_INDICE];
		return null;
	}
	public EquipmentItem getAmmo(){
		if(equipmentItems!=null)
			return equipmentItems[AMMO_INDICE];
		return null;
	}
	public EquipmentItem[] getItems(){
		if(equipmentItems!=null)
			return equipmentItems;
		return new EquipmentItem[14];
	}
	public boolean isDisplayed(){
		if(equipmentWindow!=null && equipmentWindow.isDisplayed())
			return true;
		if(equipmentStatsWindow!=null && equipmentStatsWindow.isDisplayed())
			return true;
		if(methods.bank.isShowingWornItems())
			return true;
		return false;
	}
	/**
	 * Returns the number of items equipped excluding stack sizes.
	 * @return Amount of items currently equipped.
	 */
	public int getCount() {
		int count=0;
		for(EquipmentItem item : equipmentItems){
			if(item!=null && item.getID()!=-1)
				count++;
		}
		return count;
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
		return equipmentStatsWindow!=null && equipmentStatsWindow.isDisplayed();
	}
	public boolean openEquipmentStats(){
		if(!isEquipmentStatsOpen()){
			if(!methods.tabs.equipment.isSelected()){
				if(methods.tabs.equipment.click()){
					for(int i=0;i<20;++i){
						sleep(random(100, 200));
						if(methods.tabs.equipment.isSelected())
							break;
					}
				}
			}
			if(methods.tabs.equipment.isSelected()){
				if(equipmentStatsButton!=null && equipmentStatsButton.isDisplayed()){
					if(equipmentStatsButton.click()){
						for(int i=0;i<20;++i){
							sleep(random(100, 200));
							if(isEquipmentStatsOpen())
								break;
						}
					}
				}
			}
		}
		return isEquipmentStatsOpen();
	}
	public boolean closeEquipmentStats(){
		if(isEquipmentStatsOpen()){
			if(equipmentStatsExitButton!=null){
				if(equipmentStatsExitButton.click()){
					for(int i=0;i<20;++i){
						sleep(random(100, 200));
						if(!isEquipmentStatsOpen())
							break;
					}
				}
			}
		}
		return !isEquipmentStatsOpen();
	}
	public boolean isPriceCheckerOpen(){
		return priceCheckerWindow!=null && priceCheckerWindow.isVisible();
	}
	public boolean openPriceChecker(){
		if(!isPriceCheckerOpen()){
			if(!methods.tabs.equipment.isSelected()){
				if(methods.tabs.equipment.click()){
					for(int i=0;i<20;++i){
						sleep(random(100, 200));
						if(methods.tabs.equipment.isSelected())
							break;
					}
				}
			}
			if(methods.tabs.equipment.isSelected()){
				if(priceCheckerButton!=null && priceCheckerButton.isDisplayed()){
					if(priceCheckerButton.click()){
						for(int i=0;i<20;++i){
							sleep(random(100, 200));
							if(isPriceCheckerOpen())
								break;
						}
					}
				}
			}
		}
		return isPriceCheckerOpen();
	}
	public boolean closePriceChecker(){
		if(isPriceCheckerOpen()){
			if(priceCheckerExitButton!=null){
				if(priceCheckerExitButton.click()){
					for(int i=0;i<20;++i){
						sleep(random(100, 200));
						if(!isPriceCheckerOpen())
							break;
					}
				}
			}
		}
		return !isPriceCheckerOpen();
	}
	public boolean isItemsKeptOnDeathOpen(){
		return itemsKeptOnDeathWindow!=null && itemsKeptOnDeathWindow.isVisible();
	}
	public boolean openItemsKeptOnDeath(){
		if(!isItemsKeptOnDeathOpen()){
			if(!methods.tabs.equipment.isSelected()){
				if(methods.tabs.equipment.click()){
					for(int i=0;i<20;++i){
						sleep(random(100, 200));
						if(methods.tabs.equipment.isSelected())
							break;
					}
				}
			}
			if(methods.tabs.equipment.isSelected()){
				if(itemsKeptOnDeathButton!=null && itemsKeptOnDeathButton.isDisplayed()){
					if(itemsKeptOnDeathButton.click()){
						for(int i=0;i<20;++i){
							sleep(random(100, 200));
							if(isItemsKeptOnDeathOpen())
								break;
						}
					}
				}
			}
		}
		return isItemsKeptOnDeathOpen();
	}
	public boolean closeItemsKeptOnDeath(){
		if(isItemsKeptOnDeathOpen()){
			if(itemsKeptOnDeathExitButton!=null){
				if(itemsKeptOnDeathExitButton.click()){
					for(int i=0;i<20;++i){
						sleep(random(100, 200));
						if(!isItemsKeptOnDeathOpen())
							break;
					}
				}
			}
		}
		return !isItemsKeptOnDeathOpen();
	}
	public boolean callFollower(){
		if(!methods.tabs.equipment.isSelected()){
			if(methods.tabs.equipment.click()){
				for(int i=0;i<20;++i){
					sleep(random(100, 200));
					if(methods.tabs.equipment.isSelected())
						break;
				}
			}
		}
		if(methods.tabs.equipment.isSelected()){
			if(callFollowerButton!=null && callFollowerButton.isDisplayed()){
				if(callFollowerButton.click()){
					for(int i=0;i<20;++i){
						sleep(random(100, 200));
						//if(methods.summoning.isFollowerNearby())
							//break;
					}
					return true;//TODO need summoning API and verify follower was called successfully.
				}
			}
		}
		return false;
	}
	public boolean equipmentTabSelected(){
		return methods.tabs.equipment.isSelected();
	}
	public void updateEquipmentWidgets(RSInterface equipParent, RSWidget equipWindow, RSInterface equipStatParent, RSWidget equipStatWindow,
			RSInterface pricerParent, RSWidget pricerWindow, RSInterface deathParent, RSWidget deathWindow){
		equipmentParent = equipParent;
		equipmentWindow = equipWindow;
		equipmentStatsParent = equipStatParent;
		equipmentStatsWindow = equipStatWindow;
		priceCheckerParent = pricerParent;
		priceCheckerWindow = pricerWindow;
		itemsKeptOnDeathParent = deathParent;
		itemsKeptOnDeathWindow = deathWindow;
		int index=0;
		if(equipmentParent!=null){
			for(RSWidget w : equipmentParent.getChildren()){
				if(w!=null){
					if(w.isVisible()){
						if(w.clickMask()==2046)
							equipmentItems[equipmentIndices[index++]].updateWidget(w);
						else if(w.containsAction("View equipment stats"))
							equipmentStatsButton = w;
						else if(w.containsAction("View guide prices"))
							priceCheckerButton = w;
						else if(w.containsAction("View items kept on death"))
							itemsKeptOnDeathButton = w;
						else if(w.containsAction("Call follower"))
							callFollowerButton = w;
					}
				}
			}
		}
		index=0;
		if(equipmentStatsParent!=null){
			for(RSWidget w : equipmentStatsParent.getChildren()){
				if(w!=null){
					if(w.isDisplayed()){
						if(w.clickMask()==2046){
							equipmentItems[equipmentIndices[index++]].updateWidget(w);
						}
					}
					RSWidget[] children = w.getChildren();
					if(children!=null){
						for(RSWidget child : children){
							if(child!=null){
								if(child.isDisplayed()){
									if(child.spriteID()==535)
										equipmentStatsExitButton = w;
								}
							}
						}
					}
				}
			}
		}
		index=0;
		if(methods.bank.isShowingWornItems()){
			RSInterface parent = methods.bank.getBankParentInterface();
			if(parent!=null){
				for(RSWidget w : parent.getChildren()){
					if(w!=null){
						if(w.isDisplayed()){
							if(w.clickMask()==1030){
								equipmentItems[equipmentIndices[index++]].updateWidget(w);
							}
						}
					}
				}
			}
		}
		if(priceCheckerParent!=null){
			for(RSWidget w : priceCheckerParent.getChildren()){
				if(w!=null){
					RSWidget[] children = w.getChildren();
					if(children!=null){
						for(RSWidget child : children){
							if(child!=null){
								if(child.isDisplayed()){
									if(child.spriteID()==535)
										priceCheckerExitButton = w;
								}
							}
						}
					}
				}
			}
		}
		if(itemsKeptOnDeathParent!=null){
			for(RSWidget w : itemsKeptOnDeathParent.getChildren()){
				if(w!=null){
					RSWidget[] children = w.getChildren();
					if(children!=null){
						for(RSWidget child : children){
							if(child!=null){
								if(child.isDisplayed()){
									if(child.spriteID()==535)
										itemsKeptOnDeathExitButton = w;
								}
							}
						}
					}
				}
			}
		}
	}
}
