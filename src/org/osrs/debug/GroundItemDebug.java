package org.osrs.debug;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.objects.GameObject;
import org.osrs.api.objects.GroundItem;
import org.osrs.api.wrappers.FloorDecoration;
import org.osrs.util.Data;

public class GroundItemDebug {
	private MethodContext methods = null;
	public GroundItemDebug(MethodContext methods){
		this.methods=methods;
	}
	public Graphics paint(Graphics g){
		if(Data.clientFrame.groundItemDebugOption.getState()){
			for(GroundItem gi : methods.groundItems.getAllItems()){
				if(gi.getPlane()!=methods.game.currentPlane())
					continue;
				if(gi.isHovering()){
					for(Polygon p : gi.getWireframe()){
						g.drawPolygon(p);
					}
					Point pt = gi.getScreenLocation();
					g.drawString("ID : "+gi.getID(), pt.x+15, pt.y);
					pt.y+=15;
					g.drawString("Stack Size : "+gi.getStackSize(), pt.x+15, pt.y);
					pt.y+=15;
					g.drawString("Name : "+gi.getName(), pt.x+15, pt.y);
					pt.y+=15;
					g.drawString("Location : "+gi.getLocation(), pt.x+15, pt.y);
					pt.y+=15;
				}
			}
		}
		return g;
	}
}
