package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.wrappers.BoundaryObject;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.FloorDecoration;
import org.osrs.api.wrappers.InteractableObject;
import org.osrs.api.wrappers.ObjectDefinition;
import org.osrs.api.wrappers.RenderableNode;
import org.osrs.api.wrappers.WallDecoration;
import org.osrs.util.Data;
import org.osrs.api.wrappers.Model;

public class GameObject extends Interactable{
	private Object reference = null;
	private RSModel model = null;
	private ObjectDefinition definition = null;
	private RSTile location = null;
	private int orientation = 0;
	private long id = -1;
	private long definitionHash = -1;
	/**
	 * ref must be a InteractableObject, FloorDecoration, WallDecoration, or BoundaryObject
	 * @param ref
	 * @param loc
	 */
	public GameObject(Object ref, RSTile loc) {
		methods = ((Client)Data.clientInstance).getMethodContext();
		reference = ref;
		location = loc;
		if (ref instanceof BoundaryObject) {
			RenderableNode tmp = ((BoundaryObject)ref).model();
			if(tmp!=null){
				if(tmp instanceof Model)
					model = new RSModel(tmp);
				else{
					try{
						model = tmp.getCachedModel();
					}
					catch(Exception e){}
				}
			}
			else{
				tmp = ((BoundaryObject)ref).secondaryModel();
				if(tmp!=null && tmp instanceof Model)
					model = new RSModel(tmp);
			}
			id = ((BoundaryObject) ref).id();
			orientation = ((BoundaryObject) ref).orientation();
		}
		if (ref instanceof FloorDecoration) {
			if(((FloorDecoration)ref).model() instanceof Model)
				model = new RSModel(((FloorDecoration)ref).model());
			else{
				RenderableNode obj = ((FloorDecoration)ref).model();
				try{
					model = obj.getCachedModel();
				}
				catch(Exception e){}
			}
			id = ((FloorDecoration) ref).hash();
			orientation = 0;
		}
		if (ref instanceof InteractableObject) {
			if(((InteractableObject)ref).model() instanceof Model)
				model = new RSModel(((InteractableObject)ref).model());
			else{
				RenderableNode obj = ((InteractableObject)ref).model();
				try{
					model = obj.getCachedModel();
				}
				catch(Exception e){}
			}
			id = ((InteractableObject) ref).hash();
			orientation = ((InteractableObject) ref).orientation();
		}
		if (ref instanceof WallDecoration) {
			if(((WallDecoration)ref).model() instanceof Model)
				model = new RSModel(((WallDecoration)ref).model());
			else{
				RenderableNode obj = ((WallDecoration)ref).model();
				try{
					model = obj.getCachedModel();
				}
				catch(Exception e){}
			}
			id = ((WallDecoration) ref).hash();
			orientation = ((WallDecoration) ref).orientation();
		}
		definitionHash = id >>> 17 & 0xFFFFFFFF;
		definition = getDefinition();
	}
	public Object getAccessor(){
		return reference;
	}
	/**
	 * Returns the Objects definition
	 * @return objectDefinition
	 */
	public ObjectDefinition getDefinition() {
		if(definition!=null)
			return definition;
		definition = ((Client)Data.clientInstance).invoke_getObjectDefinition((int)definitionHash);
		return definition;
	}
	/**
	 * Returns the object ID
	 * @return id
	 */
	public long getID() {
		return definitionHash;
	}
	/**
	 * Returns the objects location
	 * @return location
	 */
	public RSTile getLocation() {
		return location;
	}
	public int getLocalX(){
		if (reference instanceof InteractableObject)
			return (((InteractableObject)reference).relativeX());
		return location.getX()-((Client)Data.clientInstance).mapBaseX();
	}
	public int getLocalY(){
		if (reference instanceof InteractableObject)
			return (((InteractableObject)reference).relativeY());
		return location.getY()-((Client)Data.clientInstance).mapBaseY();
	}
	/**
	 * Returns the objects model
	 * @return model
	 */
	public RSModel getModel() {
		return model;
	}
	/**
	 * Returns the object name from its definition
	 * @return name
	 */
	public String getName(){
		ObjectDefinition def = getDefinition();
		if(def!=null){
			return def.name();
		}
		return "nulldef";
	}
	/**
	 * Returns the objects orientation (facing/angle)
	 * @return orientation
	 */
	public int getOrientation() {
		return orientation;
	}
	public Point getScreenLocation(){
		double _x = -1;
		double _y = -1;
		if (reference instanceof BoundaryObject) {
			_x = ((BoundaryObject)reference).x();
			_y = ((BoundaryObject)reference).y();
		}
		if (reference instanceof FloorDecoration) {
			_x = ((FloorDecoration)reference).x();
			_y = ((FloorDecoration)reference).y();
		}
		if (reference instanceof InteractableObject) {
			_x = ((InteractableObject)reference).x();
			_y = ((InteractableObject)reference).y();
		}
		if (reference instanceof WallDecoration) {
			_x = ((WallDecoration)reference).x();
			_y = ((WallDecoration)reference).y();
		}
		return methods.calculations.worldToScreen(_x, _y, location.getPlane(), 0);
	}
	public boolean isOnMap(){
		Point p = methods.calculations.locationToMinimap(getLocation());
		Rectangle bounds = methods.minimap.getMinimapBounds();
		if(!p.equals(new Point(-1, -1)) && (bounds.contains(p)))
			return true;
		return false;
	}
	public boolean isVisible(){
		return methods.calculations.onViewport(getLocation());
	}
	public Point[] projectVertices(){
		if(model!=null){
			double _x = -1;
			double _y = -1;
			if (reference instanceof BoundaryObject) {
				_x = ((BoundaryObject)reference).x();
				_y = ((BoundaryObject)reference).y();
			}
			if (reference instanceof FloorDecoration) {
				_x = ((FloorDecoration)reference).x();
				_y = ((FloorDecoration)reference).y();
			}
			if (reference instanceof InteractableObject) {
				_x = ((InteractableObject)reference).x();
				_y = ((InteractableObject)reference).y();
			}
			if (reference instanceof WallDecoration) {
				_x = ((WallDecoration)reference).x();
				_y = ((WallDecoration)reference).y();
			}
			return model.projectVertices(location.getPlane(), _x, _y, orientation);
		}
		return new Point[]{};
	}
	public Polygon[] getWireframe(){
		if(model!=null){
			double _x = -1;
			double _y = -1;
			if (reference instanceof BoundaryObject) {
				_x = ((BoundaryObject)reference).x();
				_y = ((BoundaryObject)reference).y();
			}
			if (reference instanceof FloorDecoration) {
				_x = ((FloorDecoration)reference).x();
				_y = ((FloorDecoration)reference).y();
			}
			if (reference instanceof InteractableObject) {
				_x = ((InteractableObject)reference).x();
				_y = ((InteractableObject)reference).y();
			}
			if (reference instanceof WallDecoration) {
				_x = ((WallDecoration)reference).x();
				_y = ((WallDecoration)reference).y();
			}
			return model.getWireframe(location.getPlane(), _x, _y, orientation);
		}
		return new Polygon[]{};
	}
	@Override
	public Point getCenterPoint() {
		if(model!=null)
			return model.getCenterPoint(location, orientation);
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
			return model.containsPoint(methods.mouse.getLocation(), location, orientation);
		return false;
	}
}
