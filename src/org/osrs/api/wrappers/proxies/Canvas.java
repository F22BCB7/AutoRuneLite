package org.osrs.api.wrappers.proxies;

import org.osrs.debug.InventoryDebug;
import org.osrs.debug.TileDebug;
import org.osrs.debug.WidgetDebug;
import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import org.osrs.util.Data;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

@BClass(name="Canvas")
public class Canvas extends java.awt.Canvas implements org.osrs.api.wrappers.Canvas{
	@BField
	public java.awt.Component component;
	@BGetter
	@Override
	public java.awt.Component component(){return component;}
	
	@BVar
	public BufferedImage gameImage = new BufferedImage(1000, 700, BufferedImage.TYPE_INT_RGB);
	
	@BFunction
	@Override
	public void applySize(int w, int h){
		this.setSize(w, h);
		gameImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	}
	@BFunction
	@Override
	public Graphics getGraphics(){
		if(gameImage==null || gameImage.getWidth()!=getWidth() || gameImage.getHeight()!=getHeight())
			gameImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		
		Graphics g = gameImage.getGraphics();
		g.setColor(Color.ORANGE);
		g.drawString("AutoRuneLite v0.1", 15, 15);

		int gameCycle = Client.clientInstance.gameCycle();
		org.osrs.api.wrappers.Widget[][] allWidgets = Client.clientInstance.widgets();
		if(allWidgets!=null){
			for(org.osrs.api.wrappers.Widget[] widgets : allWidgets){
				if(widgets!=null){
					for(org.osrs.api.wrappers.Widget widget : widgets){
						if(widget!=null){
							widget.setDisplayed(widget.displayCycle()>=gameCycle);
							org.osrs.api.wrappers.Widget[] children = widget.children();
							if(children!=null){
								for(org.osrs.api.wrappers.Widget child : children){
									if(child!=null){
										child.setDisplayed(child.displayCycle()>=gameCycle);
									}
								}
							}
						}
					}
				}
			}
		}
		if(Client.clientInstance.getMethodContext()!=null){
			WidgetDebug widgets = Client.clientInstance.getWidgetDebug();
			if(widgets!=null){
				if(widgets.debugger.isVisible())
					widgets.paint(g);
			}

			g.setColor(Color.ORANGE);
			InventoryDebug inventory = Client.clientInstance.getInventoryDebug();
			if(inventory!=null){
				inventory.paint(g);
			}

			TileDebug tile = Client.clientInstance.getTileDebug();
			if(tile!=null){
				tile.paint(g);
			}
		}
		if(Data.currentScript!=null){
			if(Data.currentScript instanceof org.osrs.script.listeners.PaintListener){
				((org.osrs.script.listeners.PaintListener)Data.currentScript).paint(g);
			}
		}
		
		Client.clientInstance.mouseListener().paint(g);
		
		super.getGraphics().drawImage(gameImage, 0, 0, null);
		return g;
	}
}