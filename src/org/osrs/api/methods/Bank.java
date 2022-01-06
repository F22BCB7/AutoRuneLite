package org.osrs.api.methods;

import java.util.ArrayList;
import org.osrs.api.objects.BankItem;
import org.osrs.api.objects.RSInterface;
import org.osrs.api.objects.RSWidget;

public class Bank extends MethodDefinition{
	private final int BANK_PARENT = 12;
	private final int BANK_WINDOW = 0;
	private final int BANK_ITEM_COUNT = 5;
	private final int BANK_MAX_SPACE = 8;
	private final int BANK_TABS = 10;
	private final int BANK_ITEM_SLOTS = 12;
	
	public Bank(MethodContext context){
		super(context);
	}
	public boolean close(){
		for(RSInterface iface : methods.widgets.getAll()){
			if(iface!=null){
				for(RSWidget w : iface.getChildren()){
					if(w==null)
						continue;
					for(RSWidget child : w.getChildren()){
						if(child!=null && child.isDisplayed() && child.spriteID()==535){
							return child.click();
						}
					}
				}
			}
		}
		return false;
	}
	/**
	 * Grabs a specified item by id from the bank.
	 * Will be NULL if none found.
	 * @param id
	 * @return interfaceItem
	 */
	public BankItem getItem(int id){
		for(BankItem item : getItems())
			if(item.getID()==id)
				return item;
		return null;
	}
	/**
	 * Grabs the stack size of a given item by id from
	 * the bank.
	 * @param id
	 * @return itemCount
	 */
	public int getItemCount(int id){
		BankItem item = getItem(id);
		if(item!=null)
			return item.getStackSize();
		return 0;
	}
	/**
	 * Grabs all items from the bank.
	 * Does not return NULL, but rather BankItem[0].
	 * @return bankItems
	 */
	public BankItem[] getItems(){
		RSWidget bankSlots = methods.widgets.getChild(BANK_PARENT, BANK_ITEM_SLOTS);
		if(bankSlots!=null){
			ArrayList<BankItem> items = new ArrayList<BankItem>();
			for(RSWidget item : bankSlots.getChildren()){
				if(item!=null && item.getInternal()!=null && item.getInternal().itemID()!=-1 && item.getInternal().itemID()!=6512){
					items.add(new BankItem(item, item.getInternal().targetVerb(), item.getInternal().itemID(), item.getInternal().itemQuantity(), item.getIndex()));
				}
			}
			return items.toArray(new BankItem[]{});
		}
		return new BankItem[]{};
	}	
	/**
	 * Grabs the max bank size (how many items your bank will hold).
	 * Returns -1 if widget not found (outdated).
	 * @return maxBankSize
	 */
	public int getMaxBankSize(){
		RSWidget w = methods.widgets.getChild(BANK_PARENT, BANK_MAX_SPACE);
		if(w!=null){
			return Integer.parseInt(w.getInternal().disabledText());
		}
		return -1;
	}
	public RSWidget[] getTabs(){
		RSWidget w = methods.widgets.getChild(BANK_PARENT, BANK_TABS);
		if(w!=null){
			ArrayList<RSWidget> temp = new ArrayList<RSWidget>();
			for(RSWidget w2 : w.getChildren()){
				if(w2.getInternal().isHidden())
					continue;
				temp.add(w2);
			}
			return temp.toArray(new RSWidget[]{});
		}
		return new RSWidget[]{};
	}
	/**
	 * Grabs the bank size (how many items your bank has).
	 * Returns -1 if widget not found (outdated).
	 * @return bankSize
	 */
	public int getUsedBankspaceCount(){
		RSWidget w = methods.widgets.getChild(BANK_PARENT, BANK_ITEM_COUNT);
		if(w!=null){
			return Integer.parseInt(w.getInternal().disabledText());
		}
		return -1;
	}
	/**
	 * Checks to see if the bank is full
	 * @return bankFull
	 */
	public boolean isBankFull(){
		return getUsedBankspaceCount()>=getMaxBankSize();
	}
	/**
	 * Checks to see if the bank is open
	 * @return bankOpen
	 */
	public boolean isOpen() {
		RSWidget ic = methods.widgets.getChild(BANK_PARENT, BANK_WINDOW);
		if(ic!=null){
			return true;
		}
		return false;
	}
}
