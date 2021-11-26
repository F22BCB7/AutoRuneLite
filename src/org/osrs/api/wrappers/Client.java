package org.osrs.api.wrappers;

import java.util.ArrayList;

import org.osrs.api.methods.MethodContext;
import org.osrs.debug.InventoryDebug;
import org.osrs.debug.WidgetDebug;

public interface Client extends GameShell{
	public void setMethodContext(MethodContext context);
	public MethodContext getMethodContext();
	public int getIntGetterMultiplier(String owner, String name, boolean isStatic);
	public int getIntSetterMultiplier(String owner, String name, boolean isStatic);
	public long getLongGetterMultiplier(String owner, String name, boolean isStatic);
	public long getLongSetterMultiplier(String owner, String name, boolean isStatic);
	public Object getMethodPredicate(String owner, String name, String desc, boolean isStatic);
	
	public WidgetDebug getWidgetDebug();
	public InventoryDebug getInventoryDebug();

	public Client clientInstance();
	
	public MouseListener mouseListener();
	public KeyboardListener keyboardListener();
	
	public boolean resizeMode();
	public int gameCycle();
	
	public int mapBaseX();
	public int mapBaseY();
	
	public Widget[][] widgets();
	public int[] widgetPositionsX();
	public int[] widgetPositionsY();
	public int[] widgetWidths();
	public int[] widgetHeights();
	public HashTable componentTable();
	public int widgetVisibleCycle();
	public HashTable itemContainers();
	
	public int cameraX();
	public int cameraZ();
	public int cameraY();
	public int cameraPitch();
	public int cameraYaw();
	public int viewportScale();
	public int viewportHeight();
	public int viewportWidth();
	
	public int[][][] tileHeights();
	public byte[][][] sceneRenderRules();
	public int mapRotation();
	
	public Player[] players();
	public Player localPlayer();
	public Npc[] npcs();
	public int currentPlane();
	
	public int[] currentSkillLevels();
	public int[] skillExperiences();
	public int[] absoluteSkillLevels();
	
	public int lastSelectedItemIndex();
	public int itemSelectionState();
	public Cache itemModelCache();
	
	public boolean menuOpen();
	public String[] menuTargets();
	public String[] menuActions();
	public int menuItemCount();
	public int menuHeight();
	public int menuWidth();
	public int menuY();
	public int menuX();
	
	public int[] varps();
	
	public Region region();
	
	public int destinationX();
	public int destinationY();
	public int runEnergy();
	
	public ItemDefinition invoke_getItemDefinition(int a);
	public ObjectDefinition invoke_getObjectDefinition(int a);
	public void invoke_addChatMessage(int a, String b, String c, String d);
	
}