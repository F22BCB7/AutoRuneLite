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
			Menu menu = methods.menu;
			g.drawString("Is Open : "+menu.isOpen(), 30, 60);
			g.drawString("Position : "+menu.getX()+","+menu.getY(), 30, 75);
			g.drawString("Bounds : "+menu.getWidth()+":"+menu.getHeight(), 30, 90);
			g.drawString("Top Text : "+menu.getTopText(), 30, 105);
			g.drawString("Item Count : "+menu.getItemCount(), 30, 120);
			g.drawString("Hovering Index : "+menu.getHoveringIndex(), 30, 135);
			g.drawString("Actions : "+Arrays.toString(menu.getActions()), 30, 150);
			g.drawString("Options : "+Arrays.toString(menu.getOptions()), 30, 165);
		}
		return g;
	}
}
