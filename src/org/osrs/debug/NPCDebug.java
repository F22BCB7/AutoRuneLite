package org.osrs.debug;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.objects.RSNpc;
import org.osrs.util.Data;

public class NPCDebug {
	private MethodContext methods = null;
	public NPCDebug(MethodContext methods){
		this.methods=methods;
	}
	public Graphics paint(Graphics g){
		if(Data.clientFrame.npcDebugOption.getState()){
			for(RSNpc npc : methods.npcs.getAll()){
				if(npc.getLocation().getPlane()!=methods.game.currentPlane())
					continue;
				if(npc.isHovering()){
					for(Polygon p : npc.getWireframe()){
						g.drawPolygon(p);
					}
					Point pt = npc.getScreenLocation();
					g.drawString("NPC ID : "+npc.getID(), pt.x+15, pt.y);
					pt.y+=15;
					g.drawString("NPC Name : "+npc.getName(), pt.x+15, pt.y);
					pt.y+=15;
					g.drawString("Combat Level : "+npc.getCombatLevel(), pt.x+15, pt.y);
					pt.y+=15;
					g.drawString("Animation ID : "+npc.getAnimationID(), pt.x+15, pt.y);
					pt.y+=15;
					g.drawString("Orientation : "+npc.getOrientation(), pt.x+15, pt.y);
					pt.y+=15;
					g.drawString("Location : "+npc.getLocation(), pt.x+15, pt.y);
					pt.y+=15;
					g.drawString("Interacting ID : "+npc.getInteractingID(), pt.x+15, pt.y);
					pt.y+=15;
					g.drawString("Height : "+npc.getHeight(), pt.x+15, pt.y);
					pt.y+=15;
					g.drawString("HP Percent : "+npc.getHPPercent(), pt.x+15, pt.y);
				}
			}
		}
		return g;
	}
}
