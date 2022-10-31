package org.osrs.debug;

import java.awt.Graphics;
import java.awt.Polygon;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.objects.GameObject;
import org.osrs.api.wrappers.BoundaryObject;
import org.osrs.api.wrappers.InteractableObject;
import org.osrs.util.Data;

public class InteractableObjDebug {
	private MethodContext methods = null;
	public InteractableObjDebug(MethodContext methods){
		this.methods=methods;
	}
	public Graphics paint(Graphics g){
		int x = 30;
		int y = 60;
		if(Data.clientFrame.interactableObjDebugOption.getState()){
			g.drawString("ID : NAME : ORIENTATION : LOCATION", x, y);
			y+=15;
			for(GameObject go : methods.objects.getAllObjects()){
				if(go.getAccessor() instanceof InteractableObject){
					if(!go.isVisible())
						continue;
					if(!go.isHovering())
						continue;
					for(Polygon p : go.getWireframe()){
						g.drawPolygon(p);
					}
					g.drawString(""+go.getID()+" : "+go.getName()+" : "+go.getOrientation()+" : "+go.getLocation(), x, y);
					y+=15;
				}
			}
		}
		return g;
	}
}
