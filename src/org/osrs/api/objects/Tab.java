package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Rectangle;

import org.osrs.util.Data;
import org.osrs.api.wrappers.Client;

public class Tab extends Interactable{
	public String name=null;
	public int index=-1;
	private int[] resizeModeWidgetIndexs = new int[]{-1, -1};
	private int[] fixedModeWidgetIndexs = new int[]{-1, -1};
	
	public Tab(int resize_interfaceIndex, int resize_childIndex, int fixed_interfaceIndex, int fixed_childIndex, String tabName, int index){
		methods = ((Client)Data.clientInstance).getMethodContext();
		resizeModeWidgetIndexs[0]=resize_interfaceIndex;
		resizeModeWidgetIndexs[1]=resize_childIndex;
		fixedModeWidgetIndexs[0]=fixed_interfaceIndex;
		fixedModeWidgetIndexs[1]=fixed_childIndex;
		this.name=tabName;
		this.index=index;
	}
	
	public boolean isSelected(){
		RSWidget widget = getWidget();
		if(widget!=null){
			if(widget.getInternal().spriteID()==1181)//find your selected tab
				return true;//and return it
		}
		return false;
	}
	public String getName(){
		return name;
	}
	public int getIndex(){
		return index;
	}
	public RSWidget getWidget(){
		RSWidget widget = null;
		if(Data.clientInstance!=null){
			if(((Client)Data.clientInstance).resizeMode()){
				widget = methods.widgets.getChild(resizeModeWidgetIndexs[0], resizeModeWidgetIndexs[1]);
			}
			else{
				widget = methods.widgets.getChild(fixedModeWidgetIndexs[0], fixedModeWidgetIndexs[1]);
			}
		}
		return widget;
	}
	public int getWidgetID(){
		RSWidget widget = getWidget();
		if(widget!=null)
			return widget.id();
		return -1;
	}
	public Rectangle getBounds(){
		RSWidget widget = getWidget();
		if(widget!=null){
			return new Rectangle(widget.getAbsoluteX(), widget.getAbsoluteY(), widget.width(), widget.height());
		}
		return new Rectangle(-1, -1, 0, 0);
	}
	public Point getScreenLocation(){
		return getBounds().getLocation();
	}
	@Override
	public boolean equals(Object o){
		return o instanceof Tab && ((Tab)o).index==index;
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
	public boolean isHovering(){
		RSWidget widget = getWidget();
		if(widget!=null){
			return widget.isHovering();
		}
		return false;
	}
}
