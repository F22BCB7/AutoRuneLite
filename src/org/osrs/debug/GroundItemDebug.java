package org.osrs.debug;

import java.awt.Graphics;
import java.awt.Polygon;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.objects.GroundItem;
import org.osrs.util.Data;

public class GroundItemDebug {
	private MethodContext methods = null;
	public GroundItemDebug(MethodContext methods){
		this.methods=methods;
	}
	public Graphics paint(Graphics g){
		int x = 30;
		int y = 60;
		if(Data.clientFrame.groundItemDebugOption.getState()){
			g.drawString("ID : STACKSIZE : NAME", x, y);
			y+=15;
			//for(GroundItem gi : methods.groundItems.getAllItems()){
			//	if(!gi.isVisible())
			//		continue;
			//	if(!gi.isHovering())
			//		continue;
			for(GroundItem gi : methods.game.getHoveringGroundItems()){
				for(Polygon p : gi.getWireframe()){
					g.drawPolygon(p);
				}
				g.drawString(""+gi.getID()+" : "+gi.getStackSize()+" : "+gi.getName(), x, y);
				y+=15;
			}
		}
		return g;
	}
}
