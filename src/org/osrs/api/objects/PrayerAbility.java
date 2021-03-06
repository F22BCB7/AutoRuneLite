package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Rectangle;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.methods.Skills;
import org.osrs.api.objects.type.Graphical;

public class PrayerAbility extends Interactable implements Graphical{
	private MethodContext methods;
	private String name;
	private int varbitID;
	private RSWidget widget;
	private int level;
	private int index;
	public PrayerAbility(MethodContext context, String n, int varbit, int minlvl, int idx){
		methods = context;
		name = n;
		varbitID = varbit;
		level = minlvl;
		index = idx;
	}
	public int getID(){
		return varbitID;
	}
	public String getName(){
		return name;
	}
	public int getLevel(){
		return level;
	}
	public int getIndex(){
		return index;
	}
	public boolean hasLevel(){
		return methods.skills.getSkillLevel(Skills.PRAYER_INDEX)>=level;
	}
	public boolean isActivated(){
		return methods.game.getVarbit(varbitID)==1;
	}
	@Override
	public boolean equals(Object o){
		if(o instanceof PrayerAbility)
			return this.varbitID==((PrayerAbility)o).varbitID;
		return false;
	}
	@Override
	public String toString(){
		return name;
	}
	public boolean isDisplayed(){
		RSWidget w = getWidget();
		if(w!=null)
			return w.isDisplayed();
		return false;
	}
	public boolean isVisible(){
		RSWidget w = getWidget();
		if(w!=null)
			return w.isVisible();
		return false;
	}
	public boolean isHovering(){
		RSWidget w = getWidget();
		if(w!=null)
			return w.isHovering();
		return false;
	}
	public boolean click(){
		RSWidget w = getWidget();
		if(w!=null)
			return w.click();
		return false;
	}
	public boolean click(String action){
		RSWidget w = getWidget();
		if(w!=null)
			return w.click(action);
		return false;
	}
	public boolean click(String action, String option){
		RSWidget w = getWidget();
		if(w!=null)
			return w.click(action, option);
		return false;
	}
	public boolean hover(){
		RSWidget w = getWidget();
		if(w!=null)
			return w.hover();
		return false;
	}
	public boolean hover(String action){
		RSWidget w = getWidget();
		if(w!=null)
			return w.hover(action);
		return false;
	}
	public boolean hover(String action, String option){
		RSWidget w = getWidget();
		if(w!=null)
			return w.hover(action, option);
		return false;
	}
	public RSWidget getWidget(){
		if(widget!=null){
			if(widget.isVisible())
				return widget;
		}
		for(RSInterface iface : methods.widgets.getAll()){
			if(iface!=null){
				for(RSWidget w : iface.getChildren()){
					if(w!=null){
						if(w.isVisible()){
							if(w.opbase().equals("<col=ff9040>"+name+"</col>")){
								widget = w; 
								return widget;
							}
						}
						RSWidget[] children = w.getChildren();
						if(children!=null){
							for(RSWidget w2 : children){
								if(w2.isVisible()){
									if(w2.opbase().equals("<col=ff9040>"+name)){
										widget = w; 
										return widget;
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
	public Rectangle getBounds(){
		RSWidget widget = getWidget();
		if(widget!=null){
			return new Rectangle(widget.getAbsoluteX(), widget.getAbsoluteY(), widget.width(), widget.height());
		}
		return new Rectangle(-1, -1, 0, 0);
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
}
