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

	@BMethod(name="requestShutdown")
	public void _requestShutdown(String s, int a){}
	@BMethod(name="requestShutdown")
	public void _requestShutdown(String s, byte a){}
	@BMethod(name="requestShutdown")
	public void _requestShutdown(String s, short a){}
	@BDetour
	public void requestShutdown(String s, int a){invoke_requestShutdown(s);}
	@BDetour
	public void requestShutdown(String s, byte a){invoke_requestShutdown(s);}
	@BDetour
	public void requestShutdown(String s, short a){invoke_requestShutdown(s);}
	@BFunction
	public void invoke_requestShutdown(String s){
		try{
			System.out.println("[GameShell.requestShutdown] Shutdown requested : "+s);
			Object predicate = Client.clientInstance.getMethodPredicate("GameShell", "requestShutdown", "(Ljava/lang/String;?)V", false);
			if(predicate instanceof Integer)
				_requestShutdown(s, (int)predicate);
			if(predicate instanceof Byte)
				_requestShutdown(s, (byte)predicate);
			if(predicate instanceof Short)
				_requestShutdown(s, (short)predicate);
		}
		catch(Exception e){
			e.printStackTrace();
		}
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
		try{
			HashMap<Integer, Integer> widgetVisibleTicks = new HashMap<Integer, Integer>();
			Widget[][] allWidgets = Client.widgets;
			if(allWidgets!=null){
				for(Widget[] widgets : allWidgets){
					if(widgets!=null){
						for(Widget widget : widgets){
							if(widget!=null){
								widgetVisibleTicks.put(widget.hashCode(), widget.visibleCycle());
								Widget[] children = widget.children;
								if(children!=null){
									for(Widget child : children){
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
				for(Widget[] widgets : allWidgets){
					if(widgets!=null){
						for(Widget widget : widgets){
							if(widget!=null){
								Object oldTick = widgetVisibleTicks.get(widget.hashCode());
								widget.setVisible(oldTick==null || (int)oldTick!=widget.visibleCycle());
								Widget[] children = widget.children;
								if(children!=null){
									for(Widget child : children){
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
				//Update API with widget info
				Client.clientInstance.getMethodContext().game.updateWidgets();
				
				//Doesn't use widgets for item info; uses ItemStorage.
				Client.clientInstance.getMethodContext().inventory.updateInventoryItems();
				Client.clientInstance.getMethodContext().equipment.updateEquipmentItems();
			}
		
			if(Data.currentScript!=null){
				if(Data.currentScript instanceof org.osrs.script.listeners.CycleListener){
					((org.osrs.script.listeners.CycleListener)Data.currentScript).gameCycle();
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	@BMethod(name="doCycle")
	public void _doCycle(int a){}
	@BMethod(name="doCycle")
	public void _doCycle(byte a){}
	@BMethod(name="doCycle")
	public void _doCycle(short a){}
	@BDetour
	public void doCycle(int a){invoke_doCycle();}
	@BDetour
	public void doCycle(byte a){invoke_doCycle();}
	@BDetour
	public void doCycle(short a){invoke_doCycle();}
	@BFunction
	public void invoke_doCycle(){
		try{
			Object predicate = Client.clientInstance.getMethodPredicate("GameShell", "doCycle", "(?)V", false);
			if(predicate instanceof Integer)
				_doCycle((int)predicate);
			if(predicate instanceof Byte)
				_doCycle((byte)predicate);
			if(predicate instanceof Short)
				_doCycle((short)predicate);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	@BMethod(name="addCanvas")
	public void _addCanvas(int a){}
	@BMethod(name="addCanvas")
	public void _addCanvas(byte a){}
	@BMethod(name="addCanvas")
	public void _addCanvas(short a){}
	@BDetour
	public void addCanvas(int a){invoke_addCanvas();}
	@BDetour
	public void addCanvas(byte a){invoke_addCanvas();}
	@BDetour
	public void addCanvas(short a){invoke_addCanvas();}
	@BFunction
	public void invoke_addCanvas(){
		try{
			System.out.println("[GameShell.addCanvas] We're swapping canvas instances!");
			Object predicate = Client.clientInstance.getMethodPredicate("GameShell", "addCanvas", "(?)V", false);
			if(predicate instanceof Integer)
				_addCanvas((int)predicate);
			if(predicate instanceof Byte)
				_addCanvas((byte)predicate);
			if(predicate instanceof Short)
				_addCanvas((short)predicate);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
