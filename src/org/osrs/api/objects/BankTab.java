package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Rectangle;

import org.osrs.api.wrappers.Client;
import org.osrs.util.Data;

public class BankTab extends Interactable{
	private RSWidget tabWidget;
	private int index;
	public BankTab(RSWidget widget, int i){
		methods = ((Client)Data.clientInstance).getMethodContext();
		tabWidget = widget;
		index = i;
	}
	public int getIndex(){
		return index;
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
