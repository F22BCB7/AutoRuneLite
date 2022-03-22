package org.osrs.api.methods;

import org.osrs.api.objects.RSInterface;
import org.osrs.api.objects.RSWidget;

public class Crafting extends MethodDefinition{
	private RSInterface craftingMenuParent=null;
	public RSWidget quantity1Button=null;
	public RSWidget quantity5Button=null;
	public RSWidget quantity10Button=null;
	public RSWidget quantityCustomButton=null;
	public RSWidget quantityXButton=null;
	public RSWidget quantityAllButton=null;
	public RSWidget[] itemButtons = new RSWidget[0];
	public Crafting(MethodContext context){
		super(context);
	}
	public boolean isSpinnerInterfaceOpen(){
		if(craftingMenuParent!=null){
			RSWidget[] children = craftingMenuParent.getChildren();
			if(children!=null){
				for(RSWidget w : children){
					if(w.fontID()==496 && w.disabledText().equals("What would you like to spin?")){
						return true;
					}
				}
			}
		}
		return false;
	}
	public boolean isClayOvenInterfaceOpen(){
		if(craftingMenuParent!=null){
			RSWidget[] children = craftingMenuParent.getChildren();
			if(children!=null){
				for(RSWidget w : children){
					if(w.fontID()==496 && w.disabledText().equals("What would you like to fire in the oven?")){
						return true;
					}
				}
			}
		}
		return false;
	}
	public RSWidget getItemButton(int id){
		for(RSWidget button : itemButtons){
			RSWidget[] children = button.getChildren();
			if(children!=null){
				for(RSWidget child : children){
					if(child.itemID()==id)
						return button;
				}
			}
		}
		return null;
	}
	public boolean isQuantitySetTo(int amount){
		if(amount==1){
			if(quantity1Button!=null){
				RSWidget[] children = quantity1Button.getChildren();
				if(children!=null){
					for(RSWidget child : children){
						if(child!=null){
							if(child.disabledText().startsWith("<col=ffffff>") && child.disabledText().endsWith("</col>"))
								return true;
						}
					}
				}
			}
		}
		else if(amount==5){
			if(quantity5Button!=null){
				RSWidget[] children = quantity5Button.getChildren();
				if(children!=null){
					for(RSWidget child : children){
						if(child!=null){
							if(child.disabledText().startsWith("<col=ffffff>") && child.disabledText().endsWith("</col>"))
								return true;
						}
					}
				}
			}
		}
		else if(amount==10){
			if(quantity10Button!=null){
				RSWidget[] children = quantity10Button.getChildren();
				if(children!=null){
					for(RSWidget child : children){
						if(child!=null){
							if(child.disabledText().startsWith("<col=ffffff>") && child.disabledText().endsWith("</col>"))
								return true;
						}
					}
				}
			}
		}
		else if(amount==0){
			if(quantityAllButton!=null){
				RSWidget[] children = quantityAllButton.getChildren();
				if(children!=null){
					for(RSWidget child : children){
						if(child!=null){
							if(child.disabledText().startsWith("<col=ffffff>") && child.disabledText().endsWith("</col>"))
								return true;
						}
					}
				}
			}
		}
		else{
			if(quantityCustomButton!=null){
				RSWidget[] children = quantityCustomButton.getChildren();
				if(children!=null){
					for(RSWidget child : children){
						if(child!=null){
							String text = child.disabledText();
							if(text.startsWith("<col=ffffff>") && text.endsWith("</col>")){
								try{
									return Integer.parseInt(text.replace("<col=ffffff>", "").replace("</col>", "")) == amount;
								}
								catch(Exception e){
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	/**
	 * 0 - All
	 * -1 - Not open
	 * Number - Amount
	 * @return
	 */
	public int getSelectedQuantityAmount(){
		if(quantity1Button!=null){
			RSWidget[] children = quantity1Button.getChildren();
			if(children!=null){
				for(RSWidget child : children){
					if(child!=null){
						if(child.disabledText().startsWith("<col=ffffff>") && child.disabledText().endsWith("</col>"))
							return 1;
					}
				}
			}
		}
		if(quantity5Button!=null){
			RSWidget[] children = quantity5Button.getChildren();
			if(children!=null){
				for(RSWidget child : children){
					if(child!=null){
						if(child.disabledText().startsWith("<col=ffffff>") && child.disabledText().endsWith("</col>"))
							return 5;
					}
				}
			}
		}
		if(quantity10Button!=null){
			RSWidget[] children = quantity10Button.getChildren();
			if(children!=null){
				for(RSWidget child : children){
					if(child!=null){
						if(child.disabledText().startsWith("<col=ffffff>") && child.disabledText().endsWith("</col>"))
							return 10;
					}
				}
			}
		}
		if(quantityAllButton!=null){
			RSWidget[] children = quantityAllButton.getChildren();
			if(children!=null){
				for(RSWidget child : children){
					if(child!=null){
						if(child.disabledText().startsWith("<col=ffffff>") && child.disabledText().endsWith("</col>"))
							return 0;
					}
				}
			}
		}
		if(quantityCustomButton!=null){
			RSWidget[] children = quantityCustomButton.getChildren();
			if(children!=null){
				for(RSWidget child : children){
					if(child!=null){
						String text = child.disabledText();
						if(text.startsWith("<col=ffffff>") && text.endsWith("</col>")){
							try{
								return Integer.parseInt(text.replace("<col=ffffff>", "").replace("</col>", ""));
							}
							catch(Exception e){}
						}
					}
				}
			}
		}
		return -1;
	}
	public void updateCraftingWidgets(RSInterface craftingParent){
		craftingMenuParent = craftingParent;
		if(craftingMenuParent!=null){
			RSWidget[] children = craftingMenuParent.getChildren();
			if(children!=null){
				for(RSWidget child : children){
					if(child!=null){
						if(child.isVisible()){
							if(child.opbase().startsWith("<col=ff9040>")){
								RSWidget[] selectableItems = new RSWidget[itemButtons.length+1];
								for(int i=0;i<itemButtons.length;++i){
									selectableItems[i]=itemButtons[i];
								}
								selectableItems[itemButtons.length] = child;
								itemButtons = selectableItems;
								continue;
							}
							RSWidget[] children2 = child.getChildren();
							if(children2!=null){
								for(RSWidget child2 : children2){
									if(child2!=null && child2.fontID()==494){
										if(child2.disabledText().equals("1") || child2.disabledText().equals("<col=ffffff>1</col>"))
											quantity1Button=child;
										else if(child2.disabledText().equals("5") || child2.disabledText().equals("<col=ffffff>5</col>"))
											quantity5Button=child;
										else if(child2.disabledText().equals("10") || child2.disabledText().equals("<col=ffffff>10</col>"))
											quantity10Button=child;
										else if(child2.disabledText().equals("X"))
											quantityXButton=child;
										else if(child2.disabledText().equals("All") || child2.disabledText().equals("<col=ffffff>All</col>"))
											quantityAllButton=child;
										else{
											String text = child2.disabledText();
											if(text.length()>0){
												if(text.startsWith("<col=ffffff>") && text.endsWith("</col>")){
													text = text.replace("<col=ffffff>", "").replace("</col>", "");
												}
												if(isInteger(text)){
													quantityCustomButton = child;
												}
											}
										}
									}
								}
							}
							
						}
					}
				}
			}
		}
		else{
			quantity1Button = null;
			quantity5Button = null;
			quantity10Button = null;
			quantityXButton = null;
			quantityAllButton = null;
			quantityCustomButton = null;
			itemButtons = new RSWidget[0];
		}
	}
	private boolean isInteger(String s) {
		try{
			Integer.parseInt(s);
		    return true;
		}
		catch(Exception e){
			return false;
		}
	}
}
