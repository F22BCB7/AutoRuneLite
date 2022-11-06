package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import org.osrs.api.objects.type.Modelled;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.Item;
import org.osrs.api.wrappers.ItemDefinition;
import org.osrs.api.wrappers.ItemLayer;
import org.osrs.api.wrappers.NPCDefinition;
import org.osrs.util.Data;

public class GroundItem extends Interactable implements Modelled{
	private RSTile location;
	private int itemPileHeight;
	private Item itemNode;
	private ItemDefinition definition;
	private ItemLayer layer;
	public GroundItem(RSTile t, Item item, ItemLayer pile){
		methods = ((Client)Data.clientInstance).getMethodContext();
		this.location=t;
		this.itemNode=item;
		layer = pile;
		itemPileHeight = pile.height();
	}
	public ItemDefinition getDefinition(){
		if(definition!=null)
			return definition;
		definition = methods.game.getItemDefinition(itemNode.id());
		return definition;
	}
	public RSModel getModel(){
		if(layer!=null){
			return layer.getModel();
		}
		return null;
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
		ItemDefinition def = getDefinition();
		if(def!=null)
			return def.name();
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
		return methods.calculations.onMap(getLocation());
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
	public Polygon getPolygon(){
		RSModel model = getModel();
		if(model!=null){
			return model.getPolygon(location, 0, itemPileHeight);
		}
		return new Polygon();
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
		long uid = calculateMenuUID();
		long[] uids = methods.game.onCursorUIDs();
		for(int i=0;i<methods.game.onCursorUIDCount();++i)
			if(uids[i]==uid)
				return true;
		RSModel model = getModel();
		if(model!=null)
			return model.containsPoint(methods.mouse.getLocation(), location);
		return false;
	}
	public long calculateMenuUID() {
		return (getLocalX() & 127) << 0 | (getLocalY() & 127) << 7 | (3 & 3) << 14 | (0 & 4294967295L) << 17;
	}
}
