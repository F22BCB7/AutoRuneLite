package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.Item;
import org.osrs.api.wrappers.ItemDefinition;
import org.osrs.util.Data;

public class GroundItem extends Interactable{
	private RSTile location;
	private int itemPileHeight;
	private Item itemNode;
	private ItemDefinition definition;
	private RSModel model;
	public GroundItem(RSTile t, Item item, int pileHeight){
		methods = ((Client)Data.clientInstance).getMethodContext();
		this.location=t;
		this.itemNode=item;
		definition = getDefinition();
		itemPileHeight = pileHeight;
	}
	public ItemDefinition getDefinition(){
		if(definition!=null)
			return definition;
		definition = ((Client)Data.clientInstance).invoke_getItemDefinition(itemNode.id());
		return definition;
	}
	public RSModel getModel(){
		if(model!=null)
			return model;
		definition = getDefinition();
		if(definition==null)
			return null;
		model = definition.getCachedModel();
		return model;
	}
	public int getID(){
		if(itemNode==null)
			return -1;
		return itemNode.id();
	}
	public Item getItemNode(){
		return itemNode;
	}
	public String getName(){
		if(definition==null)
			definition=getDefinition();
		if(definition!=null)
			return definition.name();
		return "null_definition";
	}
	public int getStackSize(){
		return itemNode.quantity();
	}
	public RSTile getLocation(){
		return location;
	}
	public double getLocationX(){
		return location.getX();
	}
	public int getLocalX(){
		return location.getX()-((Client)Data.clientInstance).mapBaseX();
	}
	public int getLocalY(){
		return location.getY()-((Client)Data.clientInstance).mapBaseY();
	}
	public double getLocationY(){
		return location.getY();
	}
	public int getPlane(){
		return location.getPlane();
	}
	public Point getScreenLocation(){
		return methods.calculations.locationToScreen(location);
	}
	public boolean isOnMap(){
		Point p = methods.calculations.locationToMinimap(location);
		Rectangle bounds = methods.minimap.getMinimapBounds();
		if(!p.equals(new Point(-1, -1)) && (bounds.contains(p)))
			return true;
		return false;
	}
	public boolean isVisible(){
		return methods.calculations.onViewport(getLocation());
	}
	public Point[] projectVertices(){
		RSModel model = getModel();
		if(model!=null){
			return model.projectVertices(location, 0, itemPileHeight);
		}
		return new Point[]{};
	}
	public Polygon[] getWireframe(){
		RSModel model = getModel();
		if(model!=null){
			return model.getWireframe(location, 0, itemPileHeight);
		}
		return new Polygon[]{};
	}
	@Override
	public Point getCenterPoint() {
		RSModel m = getModel();
		if(m!=null)
			return m.getCenterPoint(location, 0, itemPileHeight);
		return new Point(-1, -1);
	}
	@Override
	public Point getRandomPoint() {
		Polygon[] p = getWireframe();
		if(p.length<1)
			return new Point(-1, -1);
		Polygon pl = p[methods.calculations.random(p.length)];
		Rectangle r = pl.getBounds();
		if(r.width<1 || r.height<1)
			return new Point(r.x, r.y);
		return new Point(r.x+(methods.calculations.random(r.width)), r.y+(methods.calculations.random(r.height)));
	}
	@Override
	public boolean isHovering() {
		RSModel m = getModel();
		if(m!=null)
			return m.containsPoint(methods.mouse.getLocation(), location, 0, itemPileHeight);
		return false;
	}
}
