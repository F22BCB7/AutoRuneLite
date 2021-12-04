package org.osrs.debug;

import java.awt.Color;
import java.awt.Graphics;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.wrappers.Region;
import org.osrs.util.Data;

public class TileDebug {
	private MethodContext methods = null;
	public TileDebug(MethodContext methods){
		this.methods=methods;
	}
	public Graphics paint(Graphics g){
		if(Data.clientFrame.tileDebugOption.getState()){
			Region region = methods.game.region();
			if(region!=null){
				org.osrs.api.wrappers.Tile hovering = region.getHoveringTile();
				if(hovering!=null){
					g.setColor(Color.CYAN);
					g.drawPolygon(hovering.getBounds());
				}
			}
		}
		return g;
	}
}
