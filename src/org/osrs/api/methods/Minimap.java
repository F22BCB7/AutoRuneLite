package org.osrs.api.methods;

import java.awt.Point;
import java.awt.Rectangle;

import org.osrs.api.objects.RSInterface;
import org.osrs.api.objects.RSTile;
import org.osrs.api.objects.RSWidget;

public class Minimap extends MethodDefinition{
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
		for(RSInterface iface : methods.widgets.getAll()){
			if(iface!=null){
				for(RSWidget w : iface.getChildren()){
					if(w!=null && w.isDisplayed() && w.contentType()==1338){
						return w.getBounds();
					}
				}
			}
		}
		return new Rectangle(0, 0, 0, 0);
	}
}
