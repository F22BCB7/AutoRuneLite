package org.osrs.debug;

import java.awt.Graphics;
import java.awt.Polygon;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.objects.RSPlayer;
import org.osrs.util.Data;

public class PlayerDebug {
	private MethodContext methods = null;
	public PlayerDebug(MethodContext methods){
		this.methods=methods;
	}
	public Graphics paint(Graphics g){
		int x = 30;
		int y = 60;
		if(Data.clientFrame.playerDebugOption.getState()){
			g.drawString("NAME : COMBAT LEVEL : LOCATION  : ANIMATION ID : ORIENTATION: INTERACTING ID : HP : HEIGHT", x, y);
			y+=15;
			for(RSPlayer pl : methods.players.getAllPlayers()){
				if(!pl.isVisible())
					continue;
				if(!pl.isHovering())
					continue;
				for(Polygon p : pl.getWireframe()){
					g.drawPolygon(p);
				}
				g.drawString(""+pl.getName()+" : "+pl.getCombatLevel()+" : "+pl.getLocation()+" : "+pl.getAnimationID()+" : "+pl.getOrientation()+" : "+pl.getInteractingID()+" : "+pl.getHPPercent()+" : "+pl.getHeight(), x, y);
				y+=15;
			}
		}
		return g;
	}
}