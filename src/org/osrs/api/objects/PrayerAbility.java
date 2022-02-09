package org.osrs.api.objects;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.methods.Skills;

public class PrayerAbility {
	private MethodContext methods;
	private String name;
	private int varbitID;
	private RSWidget widget;
	private int level;
	public PrayerAbility(MethodContext context, String n, int id, int minlvl){
		methods = context;
		name = n;
		varbitID = id;
		level = minlvl;
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
					}
				}
			}
		}
		return null;
	}
}
