package org.osrs.api.wrappers.proxies;

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
		int y=15;
		g.drawString("AutoRuneLite v0.1", 15, y);
		y+=15;
		
		if(Data.currentScript!=null){
			if(Data.currentScript instanceof org.osrs.script.listeners.PaintListener){
				((org.osrs.script.listeners.PaintListener)Data.currentScript).paint(g);
			}
		}
		WidgetDebug widgets = getWidgetDebugFrame();
		if(widgets!=null){
			if(widgets.debugger.isVisible())
				widgets.paint(g);
		}
		
		Client.clientInstance.mouseListener().paint(g);
		
		super.getGraphics().drawImage(gameImage, 0, 0, null);
		return g;
	}
	@BVar
	public WidgetDebug widgetDebug;
	@BFunction
	@Override
	public WidgetDebug getWidgetDebugFrame(){
		if(widgetDebug==null)
			widgetDebug = new WidgetDebug(Client.clientInstance.getMethodContext());
		return widgetDebug;
	}
}