package org.osrs.api.methods;

import org.osrs.api.objects.RSInterface;
import org.osrs.api.objects.RSWidget;

public class GrandExchange extends MethodDefinition{
	private RSWidget inventoryWidget = null;
	public GrandExchange(MethodContext context){
		super(context);
	}
	public boolean isGEInventoryOpen(){
		RSWidget inventory = getInventoryWidget();
		if(inventory!=null){
			return inventory.isDisplayed();
		}
		return false;
	}
	public RSWidget getInventoryWidget(){
		if(methods.bank.isOpen())
			return null;
		if(inventoryWidget!=null)
			return inventoryWidget;
		findInventoryLoop:for(RSInterface i : methods.widgets.getAll()){
			if(i!=null){
				for(RSWidget ic : i.getChildren()){
					if(ic!=null && ic.isDisplayed()){
						RSWidget[] children = ic.getChildren();
						if(children.length!=28)
							continue;
						for(RSWidget ic2 : children){
							if(ic2.type()==5 && ic2.isDisplayed() && ic2.itemID()!=-1){
								inventoryWidget = ic;
								break findInventoryLoop;
							}
						}
					}
				}
			}
		}
		return inventoryWidget;
	}
}
