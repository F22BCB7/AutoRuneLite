package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Rectangle;

import org.osrs.api.objects.type.Graphical;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.ItemDefinition;
import org.osrs.util.Data;

public class InventoryItem extends Interactable implements Graphical{
	private int id;
	private int inventoryIndex;
	private int stacksize;
	private ItemDefinition definition;
	public InventoryItem(int id, int stacksize, int index) {
		methods = ((Client)Data.clientInstance).getMethodContext();
		this.id = id;
		this.stacksize = stacksize;	
		this.inventoryIndex = index;
		this.definition = getDefinition();
	}
	public void updateInfo(int id, int stack){
		this.id=id;
		this.stacksize=stack;
		if(id==-1)
			definition=null;
		else
			definition=getDefinition();
	}
	/**
	 * Returns the Items definition
	 * @return itemDefinition
	 */
	public ItemDefinition getDefinition() {
		if(id==-1)
			return null;
		if(definition!=null)
			return definition;
		definition = ((Client)Data.clientInstance).invoke_getItemDefinition(id);
		return definition;
	}
	/**
	 * Returns the item ID
	 * @return id
	 */
	public int getID(){
		return id;
	}
	/**
	 * Returns the item name if definition is not null.
	 * Returns null_def if no definition is found.
	 * @return name
	 */
	public String getName(){
		ItemDefinition def = getDefinition();
		if(def!=null)
			return def.name();
		return "null_def";
	}
	/**
	 * Returns the item inventory index
	 * @return index
	 */
	public int getIndex(){
		return inventoryIndex;
	}
	/**
	 * Returns the items stack size
	 * @return
	 */
	public int getStackSize(){
		return stacksize;
	}
	public Point getScreenLocation(){
		return getBounds().getLocation();
	}
	public Rectangle getBounds(){
		if(methods.grandExchange.isGEInventoryOpen()){
			RSWidget parent = methods.grandExchange.getInventoryWidget();
			if(parent!=null){
				RSWidget[] children = parent.getChildren();
				if(children.length==28){
					RSWidget child = children[inventoryIndex];
					if(child!=null)
						return child.getBounds();
				}
			}
			return new Rectangle(-1, -1, -1, -1);
		}
		if(((Client)Data.clientInstance).resizeMode()){
			Rectangle r = methods.inventory.getBounds();
			int startX = r.x;
			int startY = r.y;
			int width = 36;
			int height = 32;
			int wPad = 6;
			int hPad = 4;
			int currentIndex=0;
			Rectangle r2 = new Rectangle();
			for(int y=0;y<7;++y){
				for(int x=0;x<4;++x){
					if(currentIndex==inventoryIndex){
						r2 = new Rectangle(startX+((width+wPad)*x), startY+((height+hPad)*y), width, height);
					}
					currentIndex++;
				}
			}
			//Trim the bounds....
			Rectangle r3 = new Rectangle(r2.x+3, r2.y+2, r2.width-6, r2.height-2);
			return r3;
		}
		int startX = 563;
		int startY = 213;
		int width = 36;
		int height = 32;
		int wPad = 6;
		int hPad = 4;
		int currentIndex=0;
		for(int y=0;y<7;++y){
			for(int x=0;x<4;++x){
				if(currentIndex==inventoryIndex){
					return new Rectangle(startX+((width+wPad)*x)+1, startY+((height+hPad)*y)+2, width-6, height-2);
				}
				currentIndex++;
			}
		}
		return new Rectangle(-1, -1, -1, -1);
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
	@Override
	public boolean isHovering() {
		Rectangle bounds = getBounds();
		return bounds.contains(methods.mouse.getLocation());
	}
}
