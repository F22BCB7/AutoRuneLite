package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Rectangle;

import org.osrs.api.objects.type.Graphical;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.ItemDefinition;
import org.osrs.util.Data;

public class WidgetItem extends Interactable implements Graphical{
	private String itemName;
	private int id;
	private int itemStackSize;
	private RSWidget itemWidget;
	private ItemDefinition definition;
	public WidgetItem(RSWidget item, String name, int id, int stacksize){
		methods = ((Client)Data.clientInstance).getMethodContext();
		this.itemWidget=item;
		this.itemName=name;
		this.id = id;
		this.itemStackSize = stacksize;	
		this.definition = getDefinition();
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
	public Rectangle getBounds(){
		return itemWidget.getBounds();
	}
	public boolean isHovering(){
		return itemWidget.isHovering();
	}
	@Override
	public Point getCenterPoint() {
		Rectangle bounds = getBounds();
		if(bounds.x!=-1 && bounds.y!=-1 && bounds.width!=0 && bounds.height!=0)
			return new Point(bounds.x+(bounds.width/2), bounds.y+(bounds.height/2));
		return new Point(-1, -1);
	}
	@Override
	public Point getRandomPoint() {
		Rectangle bounds = getBounds();
		if(bounds.x!=-1 && bounds.y!=-1 && bounds.width!=0 && bounds.height!=0)
			return new Point(bounds.x+(bounds.width/2), bounds.y+(bounds.height/2));
		return new Point(-1, -1);
	}
}
