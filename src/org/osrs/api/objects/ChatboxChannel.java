package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Rectangle;

import org.osrs.api.methods.MethodContext;

public class ChatboxChannel extends Interactable{
	private MethodContext methods;
	private RSWidget widget;
	private RSWidget spriteWidget;
	public ChatboxChannel(MethodContext context){
		super();
		methods = context;
	}
	public void updateWidget(RSWidget mainWidget, RSWidget sprite){
		widget = mainWidget;
		spriteWidget = sprite;
	}
	public boolean isSelected() {
		if(spriteWidget!=null){
			if(spriteWidget.spriteID()==3053)
				return true;
			if(spriteWidget.spriteID()==3054)//is selected and hovering
				return true;
		}
		return false;
	}
	public Point getScreenLocation(){
		return getBounds().getLocation();
	}
	public Rectangle getBounds(){
		if(widget!=null && widget.isDisplayed())
			return widget.getBounds();
		return new Rectangle(-1, -1, -1, -1);
	}
	@Override
	public Point getCenterPoint() {
		Rectangle bounds = getBounds();
		if(bounds.x!=-1 && bounds.y!=-1 && bounds.width!=0 && bounds.height!=0)
			return new Point(bounds.x+(bounds.width/2), bounds.y+(bounds.height/2));
		return new Point(-1, -1);
	}
	@Override
	public Point getRandomPoint() {
		Rectangle bounds = getBounds();
		if(bounds.x!=-1 && bounds.y!=-1 && bounds.width!=0 && bounds.height!=0)
			return new Point(bounds.x+(bounds.width/2), bounds.y+(bounds.height/2));
		return new Point(-1, -1);
	}
	@Override
	public boolean isHovering() {
		if(spriteWidget!=null){
			if(spriteWidget.spriteID()==3052)
				return true;
			if(spriteWidget.spriteID()==3054)//is selected and hovering
				return true;
		}
		Rectangle bounds = getBounds();
		return bounds.contains(methods.mouse.getLocation());
	}
}
