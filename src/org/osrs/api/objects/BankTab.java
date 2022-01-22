package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Rectangle;

import org.osrs.api.wrappers.Client;
import org.osrs.util.Data;

public class BankTab extends Interactable{
	private RSWidget tabWidget;
	private RSWidget backgroundTextureWidget;
	private int index;
	public BankTab(RSWidget widget, int i, RSWidget texture){
		methods = ((Client)Data.clientInstance).getMethodContext();
		tabWidget = widget;
		index = i;
		backgroundTextureWidget = texture;
	}
	public int getIndex(){
		return index;
	}
	public int getItemCount(){
		return methods.game.client().invoke_getVarp(4171+index);
	}
	public Rectangle getBounds(){
		return tabWidget.getBounds();
	}
	@Override
	public Point getCenterPoint() {
		return tabWidget.getCenterPoint();
	}
	@Override
	public Point getRandomPoint() {
		return tabWidget.getRandomPoint();
	}
	@Override
	public boolean isHovering() {
		return tabWidget.isHovering();
	}
	public boolean isDisplayed(){
		return tabWidget.isDisplayed();
	}
	public boolean isSelected(){
		if(backgroundTextureWidget!=null)
			return backgroundTextureWidget.spriteID()==1079;
		return false;
	}
	public boolean isVisible(){
		return tabWidget.isVisible();
	}
	public boolean isMainTab(){
		return index==-1;
	}
	public boolean isAddTab(){
		return index==-2;
	}
	public boolean isAdditionalTab(){
		return index!=-1 && index!=-2;
	}
	public RSWidget getWidget(){
		return tabWidget;
	}
}
