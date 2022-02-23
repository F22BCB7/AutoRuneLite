package org.osrs.debug;

import java.awt.Graphics;
import java.util.Arrays;

import org.osrs.api.methods.Menu;
import org.osrs.api.methods.MethodContext;
import org.osrs.util.Data;

public class MenuDebug {
	private MethodContext methods = null;
	public MenuDebug(MethodContext methods){
		this.methods=methods;
	}
	public Graphics paint(Graphics g){
		if(Data.clientFrame.menuDebugOption.getState()){
			int x = 30;
			int y = 60;
			Menu menu = methods.menu;
			g.drawString("Is Open : "+menu.isOpen(), x, y);
			y+=15;
			g.drawString("Position : "+menu.getX()+","+menu.getY(), x, y);
			y+=15;
			g.drawString("Bounds : "+menu.getWidth()+":"+menu.getHeight(), x, y);
			y+=15;
			g.drawString("Top Text : "+menu.getTopText(), x, y);
			y+=15;
			g.drawString("Item Count : "+menu.getItemCount(), x, y);
			y+=15;
			g.drawString("Hovering Index : "+menu.getHoveringIndex(), x, y);
			y+=15;
			g.drawString("Actions : "+Arrays.toString(menu.getActions()), x, y);
			y+=15;
			g.drawString("Options : "+Arrays.toString(menu.getOptions()), x, y);
			y+=15;
		}
		return g;
	}
}
