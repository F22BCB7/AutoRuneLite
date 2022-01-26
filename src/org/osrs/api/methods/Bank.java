package org.osrs.api.methods;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import org.osrs.api.objects.BankItem;
import org.osrs.api.objects.BankTab;
import org.osrs.api.objects.RSInterface;
import org.osrs.api.objects.RSWidget;

public class Bank extends MethodDefinition{
	private RSInterface depositBoxParent=null;
	private RSWidget depositBoxWindow=null;
	private RSInterface bankParent=null;
	private RSWidget bankWindow=null;
	private RSWidget bankItemCount=null;
	private RSWidget bankMaxSpace=null;
	private RSWidget exitButton=null;
	private RSWidget bankPlaceholderButton=null;
	private RSWidget bankSwapButton=null;
	private RSWidget bankInsertButton=null;
	private RSWidget bankWidthdrawAsItemButton=null;
	private RSWidget bankWidthdrawAsNoteButton=null;
	private RSWidget itemQuantity1Button=null;
	private RSWidget itemQuantity5Button=null;
	private RSWidget itemQuantity10Button=null;
	private RSWidget itemQuantityXButton=null;
	private RSWidget enterAmountDialog=null;
	private RSWidget itemQuantityAllButton=null;
	private RSWidget depositInventoryButton=null;
	private RSWidget depositEquipmentButton=null;
	private RSWidget depositLootbagButton=null;
	private RSWidget bankShowEquipmentButton=null;
	private RSWidget bankShowMenuButton=null;
	private RSInterface bankTutorialParent=null;
	private RSWidget bankTutorialExitButton=null;
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
		if(isOpen() && exitButton!=null)
			return exitButton.click();
		return false;
	}
	public boolean depositInventory(){
		int count = methods.inventory.getCount();
		if(count>0){
			if(depositInventoryButton!=null){
				if(depositInventoryButton.click()){
					for(int i=0;i<20;++i){
						sleep(random(100, 200));
						if(methods.inventory.getCount()!=count)
							break;
					}
				}
			}
			else
				return false;
		}
		return methods.inventory.getCount()==0;
	}
	public boolean depositEquipment(){
		int count = methods.equipment.getCount();
		if(count>0){
			if(depositEquipmentButton!=null){
				if(depositEquipmentButton.click()){
					for(int i=0;i<20;++i){
						sleep(random(100, 200));
						if(methods.equipment.getCount()!=count)
							break;
					}
				}
			}
			else
				return false;
		}
		return methods.equipment.getCount()==0;
	}
	public RSInterface getBankParentInterface(){
		return bankParent;
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
			if(s!=null && !s.equals(""))
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
			if(s!=null && !s.equals(""))
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
	public int getSelectedTabIndex(){
		return methods.game.client().invoke_getVarp(4150);
	}
	public BankTab getSelectedBankTab(){
		for(BankTab tab : getTabs()){
			if(tab.isSelected())
				return tab;
		}
		return null;
	}
	/**
	 * Grabs the bank size (how many items your bank has).
	 * Returns -1 if widget not found (outdated).
	 * @return bankSize
	 */
	public int getUsedBankspaceCount(){
		if(bankItemCount!=null){
			String s = bankItemCount.disabledText();
			if(s!=null && !s.equals(""))
				return Integer.parseInt(bankItemCount.disabledText());
		}
		return -1;
	}
	public int getQuantityAmountSetting(){
		int setting = getQuantitySetting();
		if(setting==0)
			return 1;
		else if(setting==4)
			return 5;
		else if(setting==8)
			return 10;
		else if(setting==12)
			return methods.varps.get(304)/2;
		else if(setting==16)
			return Integer.MAX_VALUE;
		return 1;
	}
	/**
	 * Sets quantity to work with in bank.
	 * If wanting ALL, use Integer.MAX_VALUE.
	 * @param amount
	 * @return
	 */
	public boolean setQuantityAmountSetting(int amount){
		int currentAmount = getQuantityAmountSetting();
		int currentSetting = getQuantitySetting();
		RSWidget button = null;
		if(amount!=currentAmount){
			if(amount==1){
				if(itemQuantity1Button!=null){
					button = itemQuantity1Button;
				}
			}
			else if(amount==5){
				if(itemQuantity5Button!=null){
					button = itemQuantity5Button;
				}
			}
			else if(amount==10){
				if(itemQuantity10Button!=null){
					button = itemQuantity10Button;
				}
			}
			else if(amount==Integer.MAX_VALUE){
				if(itemQuantityAllButton!=null){
					button = itemQuantityAllButton;
				}
			}
			else{
				if(itemQuantityAllButton!=null){
					button = itemQuantityXButton;
					if(button!=null){
						if(button.click("Set custom quantity")){
							for(int i=0;i<20;++i){
								sleep(random(100, 200));
								if(enterAmountDialog!=null && enterAmountDialog.isDisplayed()){
									methods.keyboard.sendKeys(""+amount);
									methods.keyboard.sendKey((char)KeyEvent.VK_ENTER);
									for(i=0;i<20;++i){
										sleep(random(100, 200));
										if(getQuantitySetting()!=currentSetting)
											break;
									}
									break;
								}
							}
						}
					}
					return 12==getQuantitySetting();
				}
			}
			if(button!=null){
				if(button.click()){
					for(int i=0;i<20;++i){
						sleep(random(100, 200));
						if(currentAmount!=getQuantityAmountSetting())
							break;
					}
				}
			}
		}
		return amount==getQuantityAmountSetting();
	}
	/**
	 * @param setting - should be...
	 * 0 - widthdraw 1, 
	 * 4 - widthdraw 5, 
	 * 8 - widthdraw 10, 
	 * or 16 - widthdraw all.
	 * 
	 * 12, for widthdraw X/custom amount, 
	 * should be set via setQuantityAmountSetting(int amount) instead.
	 * @return
	 */
	public boolean setQuantitySetting(int setting){
		int currentSetting = getQuantitySetting();
		if(currentSetting!=setting){
			RSWidget button = null;
			if(setting==0){
				if(itemQuantity1Button!=null){
					button = itemQuantity1Button;
				}
			}
			else if(setting==4){
				if(itemQuantity5Button!=null){
					button = itemQuantity5Button;
				}
			}
			else if(setting==8){
				if(itemQuantity10Button!=null){
					button = itemQuantity10Button;
				}
			}
			else if(setting==16){
				if(itemQuantityAllButton!=null){
					button = itemQuantityAllButton;
				}
			}
			if(button!=null){
				if(button.click()){
					for(int i=0;i<20;++i){
						sleep(random(100, 200));
						if(currentSetting!=getQuantitySetting())
							break;
					}
				}
			}
		}
		return setting==getQuantitySetting();
	}
	/**
	 * @return 
	 * 0 = Setting '1'
	 * 4 = Setting '5'
	 * 8 = Setting '10'
	 * 12 = Setting 'X'/Custom amount
	 * 16 = All
	 */
	public int getQuantitySetting(){
		return methods.varps.get(1666);
	}
	/**
	 * Checks to see if the bank is full
	 * @return bankFull
	 */
	public boolean isBankFull(){
		return getUsedBankspaceCount()>=getMaxBankSize();
	}
	public boolean isNoteMode(){
		return methods.varps.get(115)==1;
	}
	public boolean setNoteMode(boolean val){
		if(val!=isNoteMode()){
			if(val && bankWidthdrawAsNoteButton!=null){
				if(bankWidthdrawAsNoteButton.click()){
					for(int i=0;i<20;++i){
						sleep(random(100, 200));
						if(val==isNoteMode())
							break;
					}
				}
			}
			else if(!val && bankWidthdrawAsItemButton!=null){
				if(bankWidthdrawAsItemButton.click()){
					for(int i=0;i<20;++i){
						sleep(random(100, 200));
						if(val==isNoteMode())
							break;
					}
				}
			}
		}
		return val==isNoteMode();
	}
	public boolean isInsertMode(){
		return (methods.varps.get(304)%2)==1;
	}
	public boolean setInsertMode(boolean val){
		if(val!=isInsertMode()){
			if(val && bankInsertButton!=null){
				if(bankInsertButton.click()){
					for(int i=0;i<20;++i){
						sleep(random(100, 200));
						if(val==isInsertMode())
							break;
					}
				}
			}
			else if(!val && bankSwapButton!=null){
				if(bankSwapButton.click()){
					for(int i=0;i<20;++i){
						sleep(random(100, 200));
						if(val==isInsertMode())
							break;
					}
				}
			}
		}
		return val==isInsertMode();
	}
	public boolean isPlaceholdersEnabled(){
		if(bankPlaceholderButton!=null){
			return bankPlaceholderButton.spriteID()==179;
		}
		return false;
	}
	public boolean setPlaceholderSetting(boolean val){
		if(bankPlaceholderButton!=null){
			if(bankPlaceholderButton.spriteID()==179){
				if(!val){
					if(bankPlaceholderButton.click()){
						for(int i=0;i<20;++i){
							sleep(random(100, 200));
							if(bankPlaceholderButton.spriteID()!=179)
								break;
						}
						return val==(bankPlaceholderButton.spriteID()!=179);
					}
				}
				else
					return val==(bankPlaceholderButton.spriteID()==179);
			}
			else{
				if(val){
					if(bankPlaceholderButton.click()){
						for(int i=0;i<20;++i){
							sleep(random(100, 200));
							if(bankPlaceholderButton.spriteID()==179)
								break;
						}
						return val==(bankPlaceholderButton.spriteID()==179);
					}
				}
				else
					return val==(bankPlaceholderButton.spriteID()!=179);
			}
		}
		return false;
	}
	public boolean isShowingWornItems(){
		if(bankShowEquipmentButton!=null){
			for(RSWidget w : bankShowEquipmentButton.getChildren())
				if(w.spriteID()==196)
					return true;
		}
		return false;
	}
	public boolean closeWornItems(){
		if(isShowingWornItems()){
			if(bankShowEquipmentButton.click()){
				for(int i=0;i<20;++i){
					sleep(random(100, 200));
					if(!isShowingWornItems())
						break;
				}
			}
		}
		return !isShowingWornItems();
	}
	public boolean isShowingBankMenu(){
		if(bankShowMenuButton!=null){
			for(RSWidget w : bankShowMenuButton.getChildren())
				if(w.spriteID()==196)
					return true;
		}
		return false;
	}
	public boolean closeBankMenu(){
		if(isShowingBankMenu()){
			if(bankShowMenuButton.click()){
				for(int i=0;i<20;++i){
					sleep(random(100, 200));
					if(!isShowingBankMenu())
						break;
				}
			}
		}
		return !isShowingBankMenu();
	}
	public boolean isBankTutorialOpen(){
		return bankTutorialExitButton!=null;
	}
	public boolean closeBankTutorial(){
		if(isBankTutorialOpen()){
			if(bankTutorialExitButton.click()){
				for(int i=0;i<20;++i){
					sleep(random(100, 200));
					if(!isBankTutorialOpen())
						break;
				}
			}
		}
		return !isBankTutorialOpen();
	}
	public boolean isDepositBoxOpen(){
		return depositBoxWindow!=null && depositBoxWindow.isVisible();
	}
	public boolean closeDepositBox(){
		return close();
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
		for(RSInterface i : methods.widgets.getAll()){
			if(i!=null){
				for(RSWidget w : i.getChildren()){
					if(w!=null){
						if(w.isDisplayed()){
							if(w.spriteID()==2524){
								bankTutorialParent = i;
							}
							else if(w.disabledText().equals("The Bank of Gielinor")){
								bankParent = i;
								bankWindow = methods.widgets.getChild(w.getParentID());
							}
						}
						RSWidget[] children = w.getChildren();
						if(children==null)
							continue;
						for(RSWidget child : children){
							if(child!=null){
								if(child.isDisplayed()){
									if(child.disabledText().equals("The Bank of Gielinor")){
										depositBoxWindow = methods.widgets.getChild(w.getParentID());
										depositBoxParent = i;
									}
								}
							}
						}
					}
				}
			}
		}
		if(bankParent!=null){
			for(RSWidget w : bankParent.getChildren()){
				if(w!=null){
					if(w.isDisplayed()){
						if(w.fontID()==494){
							if(w.verticalMargin()==0)
								bankItemCount=w;
							else if(w.verticalMargin()==2)
								bankMaxSpace=w;
						}
						else if(w.fontID()==496){
							if(w.disabledText().equals("Enter amount:"))
								enterAmountDialog = w;
						}
					}
					if(w.isVisible()){
						if(w.opbase().equals("<col=ff9040>Always set placeholders</col>"))
							bankPlaceholderButton = w;
						else if(w.containsAction("Swap"))
							bankSwapButton = w;
						else if(w.containsAction("Insert"))
							bankInsertButton = w;
						else if(w.containsAction("Item"))
							bankWidthdrawAsItemButton = w;
						else if(w.containsAction("Note"))
							bankWidthdrawAsNoteButton = w;
						else if(w.containsAction("Set custom quantity"))
							itemQuantityXButton = w;
						else if(w.containsAction("Default quantity: 1"))
							itemQuantity1Button = w;
						else if(w.containsAction("Default quantity: 5"))
							itemQuantity5Button = w;
						else if(w.containsAction("Default quantity: 10"))
							itemQuantity10Button = w;
						else if(w.containsAction("Default quantity: All"))
							itemQuantityAllButton = w;
						else if(w.containsAction("Deposit inventory"))
							depositInventoryButton = w;
						else if(w.containsAction("Deposit worn items"))
							depositEquipmentButton = w;
						else if(w.containsAction("Show worn items") || w.containsAction("Hide worn items"))
							bankShowEquipmentButton = w;
						else if(w.containsAction("Show menu") || w.containsAction("Hide menu"))
							bankShowMenuButton = w;
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
									if(child.spriteID()==535){
										exitButton = child;
									}
									else if(child.containsAction("View all items")){
										RSWidget texture = children[0];
										mainTab = new BankTab(child, -1, texture);
									}
									else if(child.containsAction("View tab")){
										RSWidget texture = children[child.index()-mainTab.getWidget().index()];
										additionalTabs[child.index()-mainTab.getWidget().index()-1] = new BankTab(child, child.index()-mainTab.getWidget().index()-1, texture);
									}
									else if(child.containsAction("New tab")){
										RSWidget texture = children[child.index()-mainTab.getWidget().index()];
										addTab = new BankTab(child, -2, texture);
										for(int i=child.index()-mainTab.getWidget().index()-1;i<9;++i)
											additionalTabs[i] = null;
									}
								}
							}
						}
					}
				}
			}
			if(bankTutorialExitButton!=null && !bankTutorialExitButton.isDisplayed())
				bankTutorialExitButton = null;
			if(bankTutorialParent!=null){
				for(RSWidget w : bankTutorialParent.getChildren()){
					if(w!=null){
						RSWidget[] children = w.getChildren();
						if(children==null)
							continue;
						for(RSWidget child : children){
							if(child!=null){
								if(child.isDisplayed() && child.spriteID()==535)
									bankTutorialExitButton = child;
							}
						}
					}
				}
			}
		}
		if(depositBoxParent!=null){
			for(RSWidget w : depositBoxParent.getChildren()){
				if(w!=null){
					if(w.isDisplayed()){
						if(w.containsAction("Deposit inventory"))
							depositInventoryButton = w;
						else if(w.containsAction("Deposit worn items"))
							depositEquipmentButton = w;
						else if(w.containsAction("Deposit loot"))
							depositLootbagButton = w;
					}
					if(w.isVisible()){
						if(w.containsAction("Set custom quantity"))
							itemQuantityXButton = w;
						else if(w.containsAction("Default quantity: <col=ff9040>1</col>"))
							itemQuantity1Button = w;
						else if(w.containsAction("Default quantity: <col=ff9040>5</col>"))
							itemQuantity5Button = w;
						else if(w.containsAction("Default quantity: <col=ff9040>10</col>"))
							itemQuantity10Button = w;
						else if(w.containsAction("Default quantity: All"))
							itemQuantityAllButton = w;
					}
					RSWidget[] children = w.getChildren();
					if(children==null)
						continue;
					for(RSWidget child : children){
						if(child!=null){
							if(child.isVisible()){
								if(child.spriteID()==535){
									exitButton = child;
								}
							}
						}
					}
				}
			}
		}
	}
}
