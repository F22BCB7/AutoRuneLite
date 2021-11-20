package org.osrs.api.wrappers.proxies;

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.event.FocusEvent;

import org.osrs.api.wrappers.MouseWheelListener;
import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BGetterDetour;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BSetterDetour;
import org.osrs.util.Data;

@BClass(name = "GameShell")
public class GameShell extends Applet implements org.osrs.api.wrappers.GameShell{	
	
	@BField
	public Canvas canvas;
	@BGetter
	@Override
	public Canvas canvas() {
		return canvas;
	}
	@BField
	public MouseWheelListener mouseWheelListener;
	@BGetter
	@Override
	public MouseWheelListener mouseWheelListener() {
		return mouseWheelListener;
	}

	@BMethod(name="processEngine")
	public void _processEngine(int a){}
	@BDetour
	public void processEngine(int a){
		_processEngine(a);
		if(Data.currentScript!=null){
			if(Data.currentScript instanceof org.osrs.script.listeners.CycleListener){
				((org.osrs.script.listeners.CycleListener)Data.currentScript).gameCycle();
			}
		}
	}
	@BMethod(name="processEngine")
	public void _processEngine(byte a){}
	@BDetour
	public void processEngine(byte a){
		_processEngine(a);
	}
	@BMethod(name="processEngine")
	public void _processEngine(short a){}
	@BDetour
	public void processEngine(short a){
		_processEngine(a);
	}
	

	@BMethod(name="processGraphics")
	public void _processGraphics(int a){}
	@BDetour
	public void processGraphics(int a){
		_processGraphics(a);
	}
	@BMethod(name="processGraphics")
	public void _processGraphics(byte a){}
	@BDetour
	public void processGraphics(byte a){
		_processGraphics(a);
	}
	@BMethod(name="processGraphics")
	public void _processGraphics(short a){}
	@BDetour
	public void processGraphics(short a){
		_processGraphics(a);
	}
}
