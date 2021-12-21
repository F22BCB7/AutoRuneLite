package org.osrs.debug;

import java.awt.Graphics;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.methods.Mouse;
import org.osrs.util.Data;

public class MouseDebug {
	private MethodContext methods = null;
	public MouseDebug(MethodContext methods){
		this.methods=methods;
	}
	public Graphics paint(Graphics g){
		if(Data.clientFrame.mouseDebugOption.getState()){
			Mouse mouse = methods.mouse;
			g.drawString("Real Location : "+mouse.getRealLocation(), 30, 60);
			g.drawString("Client Location : "+mouse.getLocation(), 30, 75);
			g.drawString("Crosshair State : "+methods.game.mouseCrosshairState(), 30, 90);
		}
		return g;
	}
}
