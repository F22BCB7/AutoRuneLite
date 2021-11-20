package org.osrs.api.methods;

import java.awt.Point;
import java.awt.Rectangle;

import org.osrs.api.objects.RSTile;
import org.osrs.api.objects.RSWidget;
import org.osrs.util.Data;
import org.osrs.api.wrappers.Client;

public class Minimap extends MethodDefinition{
	private final int RESIZE_MODE_MINIMAP_PARENT = 164;
	private final int RESIZE_MODE_MINIMAP_BOUNDS = 28;
	
	public Minimap(MethodContext context){
		super(context);
	}
	public boolean isOnMap(RSTile tile){
		return methods.calculations.onMap(tile);
	}
	public Point locationToMinimap(RSTile tile){
		return methods.calculations.locationToMinimap(tile);
	}
	public Rectangle getMinimapBounds(){
		if(Data.clientInstance!=null){
			if(((Client)Data.clientInstance).resizeMode()){//resize mode bounds
				RSWidget w = methods.widgets.getChild(RESIZE_MODE_MINIMAP_PARENT, RESIZE_MODE_MINIMAP_BOUNDS);
				if(w!=null){
					return w.getBounds();
				}
			}
			return new Rectangle(643, 84, 0, 0);//Fixed mode bounds
		}
		return new Rectangle(0, 0, 0, 0);
	}
}
