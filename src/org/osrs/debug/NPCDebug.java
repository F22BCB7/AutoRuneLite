package org.osrs.debug;

import java.awt.Graphics;
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
		int x = 30;
		int y = 60;
		if(Data.clientFrame.npcDebugOption.getState()){
			g.drawString("ID : NAME : COMBAT LEVEL : ANIMATION ID : ORIENTATION : LOCATION : INTERACTING ID : HP : HEIGHT", x, y);
			y+=15;
			for(RSNpc npc : methods.npcs.getAll()){
				if(npc.getLocation().getPlane()!=methods.game.currentPlane())
					continue;
				if(npc.isHovering()){
					for(Polygon p : npc.getWireframe()){
						g.drawPolygon(p);
					}
					g.drawString(""+npc.getID()+" : "+npc.getName()+" : "+npc.getCombatLevel()+" : "+npc.getAnimationID()+" : "+npc.getOrientation()+" : "+npc.getLocation()+" : "+npc.getInteractingID()+" : "+npc.getHPPercent()+" : "+npc.getHeight(), x, y);
					y+=15;
				}
			}
		}
		return g;
	}
}
