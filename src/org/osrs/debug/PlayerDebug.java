package org.osrs.debug;

import java.awt.Graphics;
import java.awt.Point;
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
		if(Data.clientFrame.playerDebugOption.getState()){
			for(RSPlayer pl : methods.players.getAllPlayers()){
				if(pl.getLocation().getPlane()!=methods.game.currentPlane())
					continue;
				if(pl.isHovering()){
					for(Polygon p : pl.getWireframe()){
						g.drawPolygon(p);
					}
					Point pt = pl.getScreenLocation();
					g.drawString("Player Name : "+pl.getName(), pt.x+15, pt.y);
					pt.y+=15;
					g.drawString("Combat level : "+pl.getCombatLevel(), pt.x+15, pt.y);
					pt.y+=15;
					g.drawString("Location : "+pl.getLocation(), pt.x+15, pt.y);
					pt.y+=15;
					g.drawString("Animation ID : "+pl.getAnimationID(), pt.x+15, pt.y);
					pt.y+=15;
					g.drawString("Height : "+pl.getHeight(), pt.x+15, pt.y);
					pt.y+=15;
					g.drawString("Interacting ID : "+pl.getInteractingID(), pt.x+15, pt.y);
					pt.y+=15;
					g.drawString("HP Percent : "+pl.getHPPercent(), pt.x+15, pt.y);
					pt.y+=15;
					g.drawString("Orientation : "+pl.getOrientation(), pt.x+15, pt.y);
				}
			}
		}
		return g;
	}
}