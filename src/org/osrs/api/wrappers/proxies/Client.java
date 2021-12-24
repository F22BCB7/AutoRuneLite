package org.osrs.api.wrappers.proxies;

import org.osrs.api.methods.MethodContext;
import org.osrs.debug.BoundaryObjDebug;
import org.osrs.debug.CameraDebug;
import org.osrs.debug.FloorDecDebug;
import org.osrs.debug.GroundItemDebug;
import org.osrs.debug.InteractableObjDebug;
import org.osrs.debug.InventoryDebug;
import org.osrs.debug.LocationDebug;
import org.osrs.debug.MenuDebug;
import org.osrs.debug.MouseDebug;
import org.osrs.debug.NPCDebug;
import org.osrs.debug.PathDebug;
import org.osrs.debug.PlayerDebug;
import org.osrs.debug.TileDebug;
import org.osrs.debug.WallDecDebug;
import org.osrs.debug.WidgetDebug;
import org.osrs.injection.MethodHook;
import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BGetterDetour;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BSetterDetour;
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
	@BVar
	public CameraDebug cameraDebug;
	@BFunction
	@Override
	public CameraDebug getCameraDebug(){
		if(cameraDebug==null)
			cameraDebug = new CameraDebug(Client.clientInstance.getMethodContext());
		return cameraDebug;
	}
	@BVar
	public LocationDebug locationDebug;
	@BFunction
	@Override
	public LocationDebug getLocationDebug(){
		if(locationDebug==null)
			locationDebug = new LocationDebug(Client.clientInstance.getMethodContext());
		return locationDebug;
	}
	@BVar
	public MouseDebug mouseDebug;
	@BFunction
	@Override
	public MouseDebug getMouseDebug(){
		if(mouseDebug==null)
			mouseDebug = new MouseDebug(Client.clientInstance.getMethodContext());
		return mouseDebug;
	}
	@BVar
	public MenuDebug menuDebug;
	@BFunction
	@Override
	public MenuDebug getMenuDebug(){
		if(menuDebug==null)
			menuDebug = new MenuDebug(Client.clientInstance.getMethodContext());
		return menuDebug;
	}
	@BVar
	public NPCDebug npcDebug;
	@BFunction
	@Override
	public NPCDebug getNPCDebug(){
		if(npcDebug==null)
			npcDebug = new NPCDebug(Client.clientInstance.getMethodContext());
		return npcDebug;
	}
	@BVar
	public PlayerDebug playerDebug;
	@BFunction
	@Override
	public PlayerDebug getPlayerDebug(){
		if(playerDebug==null)
			playerDebug = new PlayerDebug(Client.clientInstance.getMethodContext());
		return playerDebug;
	}
	@BVar
	public BoundaryObjDebug boundaryObjDebug;
	@BFunction
	@Override
	public BoundaryObjDebug getBoundaryObjDebug(){
		if(boundaryObjDebug==null)
			boundaryObjDebug = new BoundaryObjDebug(Client.clientInstance.getMethodContext());
		return boundaryObjDebug;
	}
	@BVar
	public FloorDecDebug floorDecDebug;
	@BFunction
	@Override
	public FloorDecDebug getFloorDecDebug(){
		if(floorDecDebug==null)
			floorDecDebug = new FloorDecDebug(Client.clientInstance.getMethodContext());
		return floorDecDebug;
	}
	@BVar
	public InteractableObjDebug interactableObjDebug;
	@BFunction
	@Override
	public InteractableObjDebug getInteractableObjDebug(){
		if(interactableObjDebug==null)
			interactableObjDebug = new InteractableObjDebug(Client.clientInstance.getMethodContext());
		return interactableObjDebug;
	}
	@BVar
	public WallDecDebug wallDecDebug;
	@BFunction
	@Override
	public WallDecDebug getWallDecDebug(){
		if(wallDecDebug==null)
			wallDecDebug = new WallDecDebug(Client.clientInstance.getMethodContext());
		return wallDecDebug;
	}
	@BVar
	public GroundItemDebug groundItemDebug;
	@BFunction
	@Override
	public GroundItemDebug getGroundItemDebug(){
		if(groundItemDebug==null)
			groundItemDebug = new GroundItemDebug(Client.clientInstance.getMethodContext());
		return groundItemDebug;
	}

	@BField
	public static Client clientInstance;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Client clientInstance(){return clientInstance;}
	@BField
	public static MouseListener mouseListener;
	@BGetter
	@Override
	public org.osrs.api.wrappers.MouseListener mouseListener(){return mouseListener;}
	@BField
	public static KeyboardListener keyboardListener;
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
	public static Widget[][] widgets;
	@BVar
	public org.osrs.api.wrappers.Widget[][] widgetCache;
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
	public static HashTable componentTable;
	@BGetter
	@Override
	public org.osrs.api.wrappers.HashTable componentTable(){return componentTable;}
	@BField
	public static int widgetVisibleCycle;
	@BGetter
	@Override
	public int widgetVisibleCycle(){return widgetVisibleCycle;}
	@BField
	public static HashTable itemContainers;
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
	public static Player[] players;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Player[] players(){return players;}
	@BField
	public static Player localPlayer;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Player localPlayer(){return localPlayer;}
	@BField
	public static Npc[] npcs;
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
	public static Cache itemModelCache;
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
	public static Region region;
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
	public static Deque drawnTileDeque;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Deque drawnTileDeque(){return drawnTileDeque;}

	@BField
	public static RuneScriptVM runescriptVM;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RuneScriptVM runescriptVM(){return runescriptVM;}
	
	@BField
	public static int mouseCrosshairState;
	@BGetter
	@Override
	public int mouseCrosshairState(){return mouseCrosshairState;}
	@BVar
	public boolean preventIdleMouse;
	@BFunction
	@Override
	public void setPreventIdleMouse(boolean val){
		preventIdleMouse = val;
	}
	@BFunction
	@Override
	public boolean getPreventIdleMouse(){
		return preventIdleMouse;
	}
	@BField
	public static int mouseIdleTicks;
	@BGetter
	@Override
	public int mouseIdleTicks(){return mouseIdleTicks;}
	@BGetterDetour
	public static int get_mouseIdleTicks(){
		if(clientInstance.preventIdleMouse)
			return 0;
		return mouseIdleTicks;
	}
	
	@BMethod(name="getItemDefinition")
	public static ItemDefinition _getItemDefinition(int a, int b){return null;}
	@BMethod(name="getItemDefinition")
	public static ItemDefinition _getItemDefinition(int a, byte b){return null;}
	@BMethod(name="getItemDefinition")
	public static ItemDefinition _getItemDefinition(int a, short b){return null;}
	@BFunction
	@Override
	public org.osrs.api.wrappers.ItemDefinition invoke_getItemDefinition(int a){
		Object predicate = Client.clientInstance.getMethodPredicate("", "getItemDefinition", "(I?)L*;", true);
		if(predicate instanceof Integer)
			return _getItemDefinition(a, (Integer)predicate);
		if(predicate instanceof Byte)
			return _getItemDefinition(a, (Byte)predicate);
		if(predicate instanceof Short)
			return _getItemDefinition(a, (Short)predicate);
		return null;
	}
	
	@BMethod(name="getObjectDefinition")
	public static ObjectDefinition _getObjectDefinition(int a, int b){return null;}
	@BMethod(name="getObjectDefinition")
	public static ObjectDefinition _getObjectDefinition(int a, byte b){return null;}
	@BMethod(name="getObjectDefinition")
	public static ObjectDefinition _getObjectDefinition(int a, short b){return null;}
	@BFunction
	@Override
	public org.osrs.api.wrappers.ObjectDefinition invoke_getObjectDefinition(int a){
		Object predicate = Client.clientInstance.getMethodPredicate("", "getObjectDefinition", "(I?)L*;", true);
		if(predicate instanceof Integer)
			return _getObjectDefinition(a, (Integer)predicate);
		if(predicate instanceof Byte)
			return _getObjectDefinition(a, (Byte)predicate);
		if(predicate instanceof Short)
			return _getObjectDefinition(a, (Short)predicate);
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
			_addChatMessage(a, b, c, d, (Integer)predicate);
		if(predicate instanceof Byte)
			_addChatMessage(a, b, c, d, (Byte)predicate);
		if(predicate instanceof Short)
			_addChatMessage(a, b, c, d, (Short)predicate);
	}
	@BMethod(name="fireScriptEvent")
	public static void _fireScriptEvent(ScriptEvent a, int b){}
	@BMethod(name="fireScriptEvent")
	public static void _fireScriptEvent(ScriptEvent a, byte b){}
	@BMethod(name="fireScriptEvent")
	public static void _fireScriptEvent(ScriptEvent a, short b){}
	@BDetour
	public static void fireScriptEvent(ScriptEvent a, int b){
		clientInstance.invoke_fireScriptEvent(a);
		if(Data.currentScript!=null){
			if(Data.currentScript instanceof org.osrs.script.listeners.ScriptEventListener){
				((org.osrs.script.listeners.ScriptEventListener)Data.currentScript).scriptEventStarted(a);
			}
		}
	}
	@BDetour
	public static void fireScriptEvent(ScriptEvent a, byte b){
		clientInstance.invoke_fireScriptEvent(a);
		if(Data.currentScript!=null){
			if(Data.currentScript instanceof org.osrs.script.listeners.ScriptEventListener){
				((org.osrs.script.listeners.ScriptEventListener)Data.currentScript).scriptEventStarted(a);
			}
		}
	}
	@BDetour
	public static void fireScriptEvent(ScriptEvent a, short b){
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