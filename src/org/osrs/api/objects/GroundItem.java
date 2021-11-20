package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import org.osrs.api.wrappers.Cache;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.Item;
import org.osrs.api.wrappers.ItemDefinition;
import org.osrs.api.wrappers.Model;
import org.osrs.api.wrappers.Node;
import org.osrs.util.Data;

public class GroundItem extends Interactable{
	private RSTile location;
	private Item itemNode;
	private ItemDefinition definition;
	private RSModel model;
	public GroundItem(RSTile t, Item item){
		methods = ((Client)Data.clientInstance).getMethodContext();
		this.location=t;
		this.itemNode=item;
		definition = getDefinition();
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
		int id = getID();
		if(itemNode.quantity()>1){
			int stackIndex = -1;
			int[] amounts = definition.stackAmounts();
			if(amounts!=null){
				for(int amount : amounts){
					if(itemNode.quantity()>=amount)
						stackIndex++;
					else
						break;
				}
			}
			if(stackIndex!=-1){
				int[] stackIDs = definition.stackIDs();
				if(stackIDs!=null){
					if(stackIDs.length>stackIndex)
						id = stackIDs[stackIndex];
				}
			}
		}
		Cache cache = ((Client)Data.clientInstance).itemModelCache();
		if(cache!=null){
			Node node = methods.nodes.lookup(cache.table(), id);
			if(node!=null && node instanceof Model){
				model = new RSModel((Model)node);
			}
		}
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
		Point pt = methods.calculations.locationToScreen(location);
		return pt.x!=-1 && pt.y!=-1;
	}
	public Point[] projectVertices(){
		RSModel model = getModel();
		if(model!=null){
			return model.projectVertices(location);
		}
		return new Point[]{};
	}
	public Polygon[] getWireframe(){
		RSModel model = getModel();
		if(model!=null){
			return model.getWireframe(location);
		}
		return new Polygon[]{};
	}
	@Override
	public Point getCenterPoint() {
		if(model!=null)
			return model.getCenterPoint(location);
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
		if(model!=null)
			return model.containsPoint(methods.mouse.getLocation(), location);
		return false;
	}
}
