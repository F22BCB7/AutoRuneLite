package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Rectangle;

import org.osrs.api.objects.type.Graphical;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.ItemDefinition;
import org.osrs.util.Data;

public class BankItem extends Interactable implements Graphical{
	private String itemName;
	private int id;
	private int itemStackSize;
	private RSWidget itemWidget;
	private ItemDefinition definition;
	private int index;
	public BankItem(RSWidget item, String name, int id, int stacksize, int index){
		methods = ((Client)Data.clientInstance).getMethodContext();
		this.itemWidget=item;
		this.itemName=name;
		this.id = id;
		this.definition = getDefinition();
		this.itemStackSize = stacksize;
		this.index=index;
	}

	public Rectangle getBounds(){
		return itemWidget.getBounds();
	}
	/**
	 * Returns the Items definition
	 * @return itemDefinition
	 */
	public ItemDefinition getDefinition() {
		if(definition!=null)
			return definition;
		definition = ((Client)Data.clientInstance).invoke_getItemDefinition(id);
		return definition;
	}
	public int getID(){
		return id;
	}
	/**
	 * Returns the bank item index
	 * @return index
	 */
	public int getIndex(){
		return index;
	}
	/**
	 * Returns the item name
	 * @return name
	 */
	public String getName(){
		return itemName;
	}
	/**
	 * Returns the items stack size
	 * @return
	 */
	public int getStackSize(){
		return itemStackSize;
	}
	/**
	 * Returns the Widget that holds this item
	 * @return widget
	 */
	public RSWidget getWidget(){
		return itemWidget;
	}
	@Override
	public Point getCenterPoint() {
		return itemWidget.getCenterPoint();
	}
	@Override
	public Point getRandomPoint() {
		return itemWidget.getRandomPoint();
	}
	@Override
	public boolean isHovering() {
		return itemWidget.isHovering();
	}
}
