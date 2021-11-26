package org.osrs.api.wrappers.proxies;

import java.applet.Applet;
import java.awt.Canvas;
import java.util.HashMap;

import org.osrs.api.wrappers.MouseWheelListener;
import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BMethod;
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
	@BMethod(name="processEngine")
	public void _processEngine(byte a){}
	@BMethod(name="processEngine")
	public void _processEngine(short a){}
	@BDetour
	public void processEngine(int a){invoke_processEngine();}
	@BDetour
	public void processEngine(byte a){invoke_processEngine();}
	@BDetour
	public void processEngine(short a){invoke_processEngine();}
	@BFunction
	public void invoke_processEngine(){
		HashMap<Integer, Integer> widgetVisibleTicks = new HashMap<Integer, Integer>();
		org.osrs.api.wrappers.Widget[][] allWidgets = Client.clientInstance.widgets();
		if(allWidgets!=null){
			for(org.osrs.api.wrappers.Widget[] widgets : allWidgets){
				if(widgets!=null){
					for(org.osrs.api.wrappers.Widget widget : widgets){
						if(widget!=null){
							widgetVisibleTicks.put(widget.hashCode(), widget.visibleCycle());
							org.osrs.api.wrappers.Widget[] children = widget.children();
							if(children!=null){
								for(org.osrs.api.wrappers.Widget child : children){
									if(child!=null){
										widgetVisibleTicks.put(child.hashCode(), child.visibleCycle());
									}
								}
							}
						}
					}
				}
			}
		}
		
		Object predicate = Client.clientInstance.getMethodPredicate("GameShell", "processEngine", "(?)V", false);
		if(predicate instanceof Integer)
			_processEngine((int)predicate);
		if(predicate instanceof Byte)
			_processEngine((byte)predicate);
		if(predicate instanceof Short)
			_processEngine((short)predicate);

		if(allWidgets!=null){
			for(org.osrs.api.wrappers.Widget[] widgets : allWidgets){
				if(widgets!=null){
					for(org.osrs.api.wrappers.Widget widget : widgets){
						if(widget!=null){
							Object oldTick = widgetVisibleTicks.get(widget.hashCode());
							widget.setVisible(oldTick==null || (int)oldTick!=widget.visibleCycle());
							org.osrs.api.wrappers.Widget[] children = widget.children();
							if(children!=null){
								for(org.osrs.api.wrappers.Widget child : children){
									if(child!=null){
										oldTick = widgetVisibleTicks.get(child.hashCode());
										child.setVisible(oldTick==null || (int)oldTick!=child.visibleCycle());
									}
								}
							}
						}
					}
				}
			}
		}
		Client.clientInstance.getMethodContext().inventory.updateInventoryItems();
	
		if(Data.currentScript!=null){
			if(Data.currentScript instanceof org.osrs.script.listeners.CycleListener){
				((org.osrs.script.listeners.CycleListener)Data.currentScript).gameCycle();
			}
		}
	}
}
