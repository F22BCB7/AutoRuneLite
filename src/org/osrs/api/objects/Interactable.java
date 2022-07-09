package org.osrs.api.objects;

import java.awt.Point;

import org.osrs.api.methods.MethodContext;

public abstract class Interactable {
	public MethodContext methods;
	public abstract Point getCenterPoint();
	public abstract Point getRandomPoint();
	public abstract boolean isHovering();
	public boolean hover() {
		if(!isHovering()){
			Point pt = getRandomPoint();
			if(pt.x!=-1 && pt.y!=-1)
				methods.mouse.move(pt);
		}
		return isHovering();
	}
	public boolean hover(String action) {
		if(!isHovering() || !methods.menu.contains(action)){
			Point pt = getRandomPoint();
			if(pt.x!=-1 && pt.y!=-1)
				methods.mouse.move(pt);
		}
		return isHovering() && methods.menu.contains(action);
	}
	public boolean hover(String action, String option) {
		if(!isHovering() || !methods.menu.contains(action, option)){
			Point pt = getRandomPoint();
			if(pt.x!=-1 && pt.y!=-1)
				methods.mouse.move(pt);
		}
		return isHovering() && methods.menu.contains(action, option);
	}
	public boolean click() {
		if(!isHovering()){
			Point pt = getRandomPoint();
			if(pt.x!=-1 && pt.y!=-1){
				methods.mouse.move(pt);
				methods.sleep(methods.calculations.random(500));
			}
			else
				return false;
		}
		if(isHovering()){
			methods.mouse.click();
			return true;
		}
		return false;
	}
	public boolean click(String action) {
		if(!isHovering() || !methods.menu.contains(action)){
			Point pt = getRandomPoint();
			if(pt.x!=-1 && pt.y!=-1){
				methods.mouse.move(pt);
				methods.sleep(methods.calculations.random(500));
			}
		}
		if(isHovering() && methods.menu.contains(action)){
			return methods.menu.click(action);
		}
		return false;
	}
	public boolean click(String action, String option) {
		if(!isHovering() || !methods.menu.contains(action, option)){
			Point pt = getRandomPoint();
			if(pt.x!=-1 && pt.y!=-1){
				methods.mouse.move(pt);
				methods.sleep(methods.calculations.random(500));
			}
		}
		if(isHovering() && methods.menu.contains(action, option)){
			return methods.menu.click(action, option);
		}
		return false;
	}
}
