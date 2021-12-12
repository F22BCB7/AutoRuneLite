package org.osrs.api.wrappers.proxies;

import org.osrs.api.methods.MethodContext;
import org.osrs.debug.InventoryDebug;
import org.osrs.debug.PathDebug;
import org.osrs.debug.TileDebug;
import org.osrs.debug.WidgetDebug;
import org.osrs.injection.MethodHook;
import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BVar;
import org.osrs.util.Data;

@BClass(name = "Client")
public class Client extends GameShell implements org.osrs.api.wrappers.Client{
	@BVar
	public MethodContext methodContext = null;
	@BFunction
	@Override
	public void setMethodContext(MethodContext context){
		methodContext = context;
	}
	@BFunction
	@Override
	public MethodContext getMethodContext(){
		return methodContext;
	}
	@BFunction
	@Override
	public Object getMethodPredicate(String owner, String name, String desc, boolean isStatic){
		return Data.clientModscript.getMethodPredicate(owner, name, desc, isStatic);
	}
	@BFunction
	@Override
	public int getIntGetterMultiplier(String owner, String name, boolean isStatic){
		Object multi = Data.clientModscript.getGetterMultiplier(owner, name, isStatic);
		if(multi instanceof Integer)
			return (int)multi;
		return 1;
	}
	@BFunction
	@Override
	public int getIntSetterMultiplier(String owner, String name, boolean isStatic){
		Object multi = Data.clientModscript.getSetterMultiplier(owner, name, isStatic);
		if(multi instanceof Integer)
			return (int)multi;
		return 1;
	}
	@BFunction
	@Override
	public long getLongGetterMultiplier(String owner, String name, boolean isStatic){
		Object multi = Data.clientModscript.getGetterMultiplier(owner, name, isStatic);
		if(multi instanceof Long)
			return (long)multi;
		return 1;
	}
	@BFunction
	@Override
	public long getLongSetterMultiplier(String owner, String name, boolean isStatic){
		Object multi = Data.clientModscript.getSetterMultiplier(owner, name, isStatic);
		if(multi instanceof Long)
			return (long)multi;
		return 1;
	}

	@BVar
	public WidgetDebug widgetDebug;
	@BFunction
	@Override
	public WidgetDebug getWidgetDebug(){
		if(widgetDebug==null)
			widgetDebug = new WidgetDebug(Client.clientInstance.getMethodContext());
		return widgetDebug;
	}
	@BVar
	public InventoryDebug inventoryDebug;
	@BFunction
	@Override
	public InventoryDebug getInventoryDebug(){
		if(inventoryDebug==null)
			inventoryDebug = new InventoryDebug(Client.clientInstance.getMethodContext());
		return inventoryDebug;
	}
	@BVar
	public PathDebug pathDebug;
	@BFunction
	@Override
	public PathDebug getPathDebug(){
		if(pathDebug==null)
			pathDebug = new PathDebug(Client.clientInstance.getMethodContext());
		return pathDebug;
	}
	@BVar
	public TileDebug tileDebug;
	@BFunction
	@Override
	public TileDebug getTileDebug(){
		if(tileDebug==null)
			tileDebug = new TileDebug(Client.clientInstance.getMethodContext());
		return tileDebug;
	}

	@BField
	public static org.osrs.api.wrappers.Client clientInstance;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Client clientInstance(){return clientInstance;}
	
	@BField
	public static org.osrs.api.wrappers.MouseListener mouseListener;
	@BGetter
	@Override
	public org.osrs.api.wrappers.MouseListener mouseListener(){return mouseListener;}
	@BField
	public static org.osrs.api.wrappers.KeyboardListener keyboardListener;
	@BGetter
	@Override
	public org.osrs.api.wrappers.KeyboardListener keyboardListener(){return keyboardListener;}
	
	@BField
	public static boolean resizeMode;
	@BGetter
	@Override
	public boolean resizeMode(){return resizeMode;}
	@BField
	public static int gameCycle;
	@BGetter
	@Override
	public int gameCycle(){return gameCycle;}

	@BField
	public static int mapBaseX;
	@BGetter
	@Override
	public int mapBaseX(){return mapBaseX;}
	@BField
	public static int mapBaseY;
	@BGetter
	@Override
	public int mapBaseY(){return mapBaseY;}
	
	@BField
	public static org.osrs.api.wrappers.Widget[][] widgets;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Widget[][] widgets(){return widgets;}
	@BField
	public static int[] widgetPositionsX;
	@BGetter
	@Override
	public int[] widgetPositionsX(){return widgetPositionsX;}
	@BField
	public static int[] widgetPositionsY;
	@BGetter
	@Override
	public int[] widgetPositionsY(){return widgetPositionsY;}
	@BField
	public static int[] widgetWidths;
	@BGetter
	@Override
	public int[] widgetWidths(){return widgetWidths;}
	@BField
	public static int[] widgetHeights;
	@BGetter
	@Override
	public int[] widgetHeights(){return widgetHeights;}
	@BField
	public static org.osrs.api.wrappers.HashTable componentTable;
	@BGetter
	@Override
	public org.osrs.api.wrappers.HashTable componentTable(){return componentTable;}
	@BField
	public static int widgetVisibleCycle;
	@BGetter
	@Override
	public int widgetVisibleCycle(){return widgetVisibleCycle;}
	@BField
	public static org.osrs.api.wrappers.HashTable itemContainers;
	@BGetter
	@Override
	public org.osrs.api.wrappers.HashTable itemContainers(){return itemContainers;}
	
	@BField
	public static int cameraX;
	@BGetter
	@Override
	public int cameraX(){return cameraX;}
	@BField
	public static int cameraZ;
	@BGetter
	@Override
	public int cameraZ(){return cameraZ;}
	@BField
	public static int cameraY;
	@BGetter
	@Override
	public int cameraY(){return cameraY;}
	@BField
	public static int cameraPitch;
	@BGetter
	@Override
	public int cameraPitch(){return cameraPitch;}
	@BField
	public static int cameraYaw;
	@BGetter
	@Override
	public int cameraYaw(){return cameraYaw;}
	@BField
	public static int viewportScale;
	@BGetter
	@Override
	public int viewportScale(){return viewportScale;}
	@BField
	public static int viewportHeight;
	@BGetter
	@Override
	public int viewportHeight(){return viewportHeight;}
	@BField
	public static int viewportWidth;
	@BGetter
	@Override
	public int viewportWidth(){return viewportWidth;}
	
	@BField
	public static int[][][] tileHeights;
	@BGetter
	@Override
	public int[][][] tileHeights(){return tileHeights;}
	@BField
	public static byte[][][] sceneRenderRules;
	@BGetter
	@Override
	public byte[][][] sceneRenderRules(){return sceneRenderRules;}
	@BField
	public static int mapRotation;
	@BGetter
	@Override
	public int mapRotation(){return mapRotation;}

	@BField
	public static org.osrs.api.wrappers.Player[] players;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Player[] players(){return players;}
	@BField
	public static org.osrs.api.wrappers.Player localPlayer;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Player localPlayer(){return localPlayer;}
	@BField
	public static org.osrs.api.wrappers.Npc[] npcs;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Npc[] npcs(){return npcs;}
	@BField
	public static int currentPlane;
	@BGetter
	@Override
	public int currentPlane(){return currentPlane;}
	
	@BField
	public static int[] currentSkillLevels;
	@BGetter
	@Override
	public int[] currentSkillLevels(){return currentSkillLevels;}
	@BField
	public static int[] skillExperiences;
	@BGetter
	@Override
	public int[] skillExperiences(){return skillExperiences;}
	@BField
	public static int[] absoluteSkillLevels;
	@BGetter
	@Override
	public int[] absoluteSkillLevels(){return absoluteSkillLevels;}
	
	@BField
	public static int lastSelectedItemIndex;
	@BGetter
	@Override
	public int lastSelectedItemIndex(){return lastSelectedItemIndex;}
	@BField
	public static int itemSelectionState;
	@BGetter
	@Override
	public int itemSelectionState(){return itemSelectionState;}
	@BField
	public static org.osrs.api.wrappers.Cache itemModelCache;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Cache itemModelCache(){return itemModelCache;}
	
	@BField
	public static boolean menuOpen;
	@BGetter
	@Override
	public boolean menuOpen(){return menuOpen;}
	@BField
	public static String[] menuTargets;
	@BGetter
	@Override
	public String[] menuTargets(){return menuTargets;}
	@BField
	public static String[] menuActions;
	@BGetter
	@Override
	public String[] menuActions(){return menuActions;}
	@BField
	public static int menuItemCount;
	@BGetter
	@Override
	public int menuItemCount(){return menuItemCount;}
	@BField
	public static int menuHeight;
	@BGetter
	@Override
	public int menuHeight(){return menuHeight;}
	@BField
	public static int menuWidth;
	@BGetter
	@Override
	public int menuWidth(){return menuWidth;}
	@BField
	public static int menuY;
	@BGetter
	@Override
	public int menuY(){return menuY;}
	@BField
	public static int menuX;
	@BGetter
	@Override
	public int menuX(){return menuX;}
	

	@BField
	public static int[] varps;
	@BGetter
	@Override
	public int[] varps(){return varps;}
	
	@BField
	public static org.osrs.api.wrappers.Region region;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Region region(){return region;}

	@BField
	public static int destinationX;
	@BGetter
	@Override
	public int destinationX(){return destinationX;}
	@BField
	public static int destinationY;
	@BGetter
	@Override
	public int destinationY(){return destinationY;}
	@BField
	public static int runEnergy;
	@BGetter
	@Override
	public int runEnergy(){return runEnergy;}

	@BField
	public static org.osrs.api.wrappers.Deque drawnTileDeque;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Deque drawnTileDeque(){return drawnTileDeque;}

	@BField
	public static org.osrs.api.wrappers.RuneScriptVM runescriptVM;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RuneScriptVM runescriptVM(){return runescriptVM;}
	
	@BMethod(name="getItemDefinition")
	public static org.osrs.api.wrappers.ItemDefinition _getItemDefinition(int a, int b){return null;}
	@BMethod(name="getItemDefinition")
	public static org.osrs.api.wrappers.ItemDefinition _getItemDefinition(int a, byte b){return null;}
	@BMethod(name="getItemDefinition")
	public static org.osrs.api.wrappers.ItemDefinition _getItemDefinition(int a, short b){return null;}
	@BFunction
	@Override
	public org.osrs.api.wrappers.ItemDefinition invoke_getItemDefinition(int a){
		Object predicate = Client.clientInstance.getMethodPredicate("", "getItemDefinition", "(I?)L*;", true);
		if(predicate instanceof Integer)
			return _getItemDefinition(a, (int)predicate);
		if(predicate instanceof Byte)
			return _getItemDefinition(a, (byte)predicate);
		if(predicate instanceof Short)
			return _getItemDefinition(a, (short)predicate);
		return null;
	}
	
	@BMethod(name="getObjectDefinition")
	public static org.osrs.api.wrappers.ObjectDefinition _getObjectDefinition(int a, int b){return null;}
	@BMethod(name="getObjectDefinition")
	public static org.osrs.api.wrappers.ObjectDefinition _getObjectDefinition(int a, byte b){return null;}
	@BMethod(name="getObjectDefinition")
	public static org.osrs.api.wrappers.ObjectDefinition _getObjectDefinition(int a, short b){return null;}
	@BFunction
	@Override
	public org.osrs.api.wrappers.ObjectDefinition invoke_getObjectDefinition(int a){
		Object predicate = Client.clientInstance.getMethodPredicate("", "getObjectDefinition", "(I?)L*;", true);
		if(predicate instanceof Integer)
			return _getObjectDefinition(a, (int)predicate);
		if(predicate instanceof Byte)
			return _getObjectDefinition(a, (byte)predicate);
		if(predicate instanceof Short)
			return _getObjectDefinition(a, (short)predicate);
		return null;
	}

	@BMethod(name="addChatMessage")
	public static void _addChatMessage(int a, String b, String c, String d, int e){}
	@BMethod(name="addChatMessage")
	public static void _addChatMessage(int a, String b, String c, String d, byte e){}
	@BMethod(name="addChatMessage")
	public static void _addChatMessage(int a, String b, String c, String d, short e){}
	@BDetour
	public static void addChatMessage(int a, String b, String c, String d, int e){
		clientInstance.invoke_addChatMessage(a, b, c, d);
		if(Data.currentScript!=null){
			if(Data.currentScript instanceof org.osrs.script.listeners.MessageListener){
				org.osrs.script.events.MessageEvent event = new org.osrs.script.events.MessageEvent(a, c, b, d);
				((org.osrs.script.listeners.MessageListener)Data.currentScript).messageReceived(event);
			}
		}
	}
	@BDetour
	public static void addChatMessage(int a, String b, String c, String d, byte e){
		clientInstance.invoke_addChatMessage(a, b, c, d);
		if(Data.currentScript!=null){
			if(Data.currentScript instanceof org.osrs.script.listeners.MessageListener){
				org.osrs.script.events.MessageEvent event = new org.osrs.script.events.MessageEvent(a, c, b, d);
				((org.osrs.script.listeners.MessageListener)Data.currentScript).messageReceived(event);
			}
		}
	}
	@BDetour
	public static void addChatMessage(int a, String b, String c, String d, short e){
		clientInstance.invoke_addChatMessage(a, b, c, d);
		if(Data.currentScript!=null){
			if(Data.currentScript instanceof org.osrs.script.listeners.MessageListener){
				org.osrs.script.events.MessageEvent event = new org.osrs.script.events.MessageEvent(a, c, b, d);
				((org.osrs.script.listeners.MessageListener)Data.currentScript).messageReceived(event);
			}
		}
	}
	@BFunction
	@Override
	public void invoke_addChatMessage(int a, String b, String c, String d){
		Object predicate = Client.clientInstance.getMethodPredicate("", "addChatMessage", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;?)V", true);
		if(predicate instanceof Integer)
			_addChatMessage(a, b, c, d, (int)predicate);
		if(predicate instanceof Byte)
			_addChatMessage(a, b, c, d, (byte)predicate);
		if(predicate instanceof Short)
			_addChatMessage(a, b, c, d, (short)predicate);
	}
	@BMethod(name="fireScriptEvent")
	public static void _fireScriptEvent(org.osrs.api.wrappers.ScriptEvent a, int b){}
	@BMethod(name="fireScriptEvent")
	public static void _fireScriptEvent(org.osrs.api.wrappers.ScriptEvent a, byte b){}
	@BMethod(name="fireScriptEvent")
	public static void _fireScriptEvent(org.osrs.api.wrappers.ScriptEvent a, short b){}
	@BDetour
	public static void fireScriptEvent(org.osrs.api.wrappers.ScriptEvent a, int b){
		clientInstance.invoke_fireScriptEvent(a);
		if(Data.currentScript!=null){
			if(Data.currentScript instanceof org.osrs.script.listeners.ScriptEventListener){
				((org.osrs.script.listeners.ScriptEventListener)Data.currentScript).scriptEventStarted(a);
			}
		}
	}
	@BDetour
	public static void fireScriptEvent(org.osrs.api.wrappers.ScriptEvent a, byte b){
		clientInstance.invoke_fireScriptEvent(a);
		if(Data.currentScript!=null){
			if(Data.currentScript instanceof org.osrs.script.listeners.ScriptEventListener){
				((org.osrs.script.listeners.ScriptEventListener)Data.currentScript).scriptEventStarted(a);
			}
		}
	}
	@BDetour
	public static void fireScriptEvent(org.osrs.api.wrappers.ScriptEvent a, short b){
		clientInstance.invoke_fireScriptEvent(a);
		if(Data.currentScript!=null){
			if(Data.currentScript instanceof org.osrs.script.listeners.ScriptEventListener){
				((org.osrs.script.listeners.ScriptEventListener)Data.currentScript).scriptEventStarted(a);
			}
		}
	}
	@BFunction
	@Override
	public void invoke_fireScriptEvent(org.osrs.api.wrappers.ScriptEvent a){
		Object predicate = Client.clientInstance.getMethodPredicate("", "fireScriptEvent", "(L*;?)V", true);
		if(predicate instanceof Integer)
			_fireScriptEvent((org.osrs.api.wrappers.proxies.ScriptEvent)a, (int)predicate);
		if(predicate instanceof Byte)
			_fireScriptEvent((org.osrs.api.wrappers.proxies.ScriptEvent)a, (byte)predicate);
		if(predicate instanceof Short)
			_fireScriptEvent((org.osrs.api.wrappers.proxies.ScriptEvent)a, (short)predicate);
	}
	@BFunction
	@Override
	public void runScriptEvent(Object[] args){
		ScriptEvent e = new ScriptEvent();
		e.args = args;
		invoke_fireScriptEvent(e);
	}
}