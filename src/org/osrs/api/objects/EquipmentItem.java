package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Rectangle;

import org.osrs.api.objects.type.Graphical;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.ItemDefinition;
import org.osrs.util.Data;

public class EquipmentItem extends Interactable implements Graphical{
	private int id;
	private int equipmentIndex;
	private int stacksize;
	private ItemDefinition definition;
	private RSWidget widget;
	public EquipmentItem(int id, int stacksize, int index) {
		methods = ((Client)Data.clientInstance).getMethodContext();
		this.id = id;
		this.stacksize = stacksize;	
		this.equipmentIndex = index;
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
	public void updateWidget(RSWidget w){
		widget = w;
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
		return equipmentIndex;
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
		if(widget!=null && widget.isDisplayed())
			return widget.getBounds();
		return new Rectangle(-1, -1, -1, -1);
	}
	@Override
	public boolean click() {
		if(!methods.tabs.inventory.isSelected()){
			methods.tabs.inventory.click();
		}
		return super.click();
	}
	@Override
	public boolean click(String action) {
		if(!methods.tabs.inventory.isSelected()){
			methods.tabs.inventory.click();
		}
		return super.click(action);
	}
	@Override
	public boolean click(String action, String option) {
		if(!methods.tabs.inventory.isSelected()){
			methods.tabs.inventory.click();
		}
		return super.click(action, option);
	}
	@Override
	public boolean hover() {
		if(!methods.tabs.inventory.isSelected()){
			methods.tabs.inventory.click();
		}
		return super.hover();
	}
	@Override
	public boolean hover(String action) {
		if(!methods.tabs.inventory.isSelected()){
			methods.tabs.inventory.click();
		}
		return super.hover(action);
	}
	@Override
	public boolean hover(String action, String option) {
		if(!methods.tabs.inventory.isSelected()){
			methods.tabs.inventory.click();
		}
		return super.hover(action, option);
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
