package org.osrs.debug;

import java.awt.Color;
import java.awt.Graphics;
import org.osrs.api.methods.MethodContext;
import org.osrs.api.objects.RSNpc;
import org.osrs.api.objects.RSPlayer;
import org.osrs.api.objects.RSTile;
import org.osrs.util.Data;

public class PathDebug {
	private MethodContext methods = null;
	public PathDebug(MethodContext methods){
		this.methods=methods;
	}
	public Graphics paint(Graphics g){
		if(Data.clientFrame.pathDebugOption.getState()){
			for(RSPlayer p : methods.players.getAllPlayers()){
				if(p.getLocation().getPlane()!=methods.game.currentPlane())
					continue;
				if(p!=null){
					g.setColor(Color.ORANGE);
					for(RSTile t : p.getPath()){
						g.drawPolygon(t.getBounds());
					}
					g.setColor(Color.RED);
					g.drawPolygon(p.getLocation().getBounds());
				}
			}
			for(RSNpc n : methods.npcs.getAll()){
				if(n.getLocation().getPlane()!=methods.game.currentPlane())
					continue;
				if(n!=null){
					g.setColor(Color.ORANGE);
					for(RSTile t : n.getPath()){
						g.drawPolygon(t.getBounds());
					}
					g.setColor(Color.RED);
					g.drawPolygon(n.getLocation().getBounds());
				}
			}
		}
		return g;
	}
}