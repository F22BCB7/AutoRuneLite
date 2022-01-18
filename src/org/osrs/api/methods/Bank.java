package org.osrs.api.methods;

import java.util.ArrayList;
import org.osrs.api.objects.BankItem;
import org.osrs.api.objects.BankTab;
import org.osrs.api.objects.RSInterface;
import org.osrs.api.objects.RSWidget;

public class Bank extends MethodDefinition{
	private RSInterface bankParent=null;
	private RSWidget bankWindow=null;
	private RSWidget bankItemCount=null;
	private RSWidget bankMaxSpace=null;
	private RSWidget bankTabs=null;
	private RSWidget bankExitButton=null;
	private BankItem[] bankItems = new BankItem[1200];
	private BankTab mainTab = null;
	private BankTab[] additionalTabs = new BankTab[9];
	private BankTab addTab = null;
	private int cachedItemCount=-1;
	private int cachedMaxSpace=-1;
	public Bank(MethodContext context){
		super(context);
	}
	public boolean close(){
		if(isOpen() && bankExitButton!=null)
			return bankExitButton.click();
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
	public int getItemCount(){
		if(bankItemCount!=null){
			String s = bankItemCount.disabledText();
			if(s!=null)
				cachedItemCount = Integer.parseInt(s);
		}
		return cachedItemCount;
	}
	/**
	 * Grabs the stack size of a given item by id from
	 * the bank.
	 * @param id
	 * @return itemCount
	 */
	public int getItemStackSize(int id){
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
		ArrayList<BankItem> items = new ArrayList<BankItem>();
		for(BankItem item : bankItems){
			if(item!=null)
				items.add(item);
		}
		return items.toArray(new BankItem[]{});
	}	
	/**
	 * Grabs the max bank size (how many items your bank will hold).
	 * Returns -1 if widget not found (outdated).
	 * @return maxBankSize
	 */
	public int getMaxBankSize(){
		if(bankMaxSpace!=null){
			String s = bankMaxSpace.disabledText();
			if(s!=null)
				cachedMaxSpace = Integer.parseInt(s);
		}
		return cachedMaxSpace;
	}
	public BankTab[] getTabs(){
		ArrayList<BankTab> tabs = new ArrayList<BankTab>();
		tabs.add(mainTab);
		for(BankTab tab : additionalTabs)
			if(tab!=null)
				tabs.add(tab);
		if(addTab!=null)
			tabs.add(addTab);
		return tabs.toArray(new BankTab[]{});
	}
	/**
	 * Grabs the bank size (how many items your bank has).
	 * Returns -1 if widget not found (outdated).
	 * @return bankSize
	 */
	public int getUsedBankspaceCount(){
		if(bankItemCount!=null){
			return Integer.parseInt(bankItemCount.disabledText());
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
		if(bankWindow!=null && bankWindow.isDisplayed())
			return true;
		return false;
	}
	public void updateBankWidgets(){
		if(bankParent==null){
			findBankLoop:for(RSInterface i : methods.widgets.getAll()){
				if(i!=null){
					for(RSWidget w : i.getChildren()){
						if(w!=null){
							if(w.isDisplayed() && w.disabledText().equals("The Bank of Gielinor")){
								bankParent = i;
								bankWindow = methods.widgets.getChild(w.getParentID());
								break findBankLoop;
							}
						}
					}
				}
			}
		}
		if(bankParent!=null){
			for(RSWidget w : bankParent.getChildren()){
				if(w!=null){
					if(w.isDisplayed() && w.fontID()==494){
						//item count and max space
						if(w.verticalMargin()==0)
							bankItemCount=w;
						else if(w.verticalMargin()==2)
							bankMaxSpace=w;
					}
					RSWidget[] children = w.getChildren();
					if(children==null)
						continue;
					if(children.length>1200){//maximum 1200 item slots in bank
						for(int i=0;i<1200;++i){
							RSWidget item = children[i];
							if(item!=null && item.getInternal()!=null && item.getInternal().itemID()!=-1 && item.getInternal().itemID()!=6512){
								bankItems[i] = new BankItem(item, item.getInternal().targetVerb(), item.getInternal().itemID(), item.getInternal().itemQuantity(), item.getIndex());
							}
							else
								bankItems[i] = null;
						}
					}
					else{
						for(RSWidget child : children){
							if(child!=null){
								if(child.isVisible()){
									if(child.containsAction("View all items")){
										bankTabs = w;
										mainTab = new BankTab(child, -1);
									}
									else if(child.containsAction("View tab")){
										additionalTabs[child.index()-mainTab.getWidget().index()-1] = new BankTab(child, child.index()-mainTab.getWidget().index()-1);
									}
									else if(child.containsAction("New tab")){
										addTab = new BankTab(child, -2);
										for(int i=child.index()-mainTab.getWidget().index()-1;i<9;++i)
											additionalTabs[i] = null;
									}
									else if(child.spriteID()==535)
										bankExitButton = child;
								}
							}
						}
					}
				}
			}
		}
	}
}
