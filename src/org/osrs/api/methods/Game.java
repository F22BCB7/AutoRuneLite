package org.osrs.api.methods;

import java.util.Arrays;

import org.osrs.api.wrappers.*;
import org.osrs.api.wrappers.Region;

public class Game extends MethodDefinition{
	private Client client;
	public Game(MethodContext context){
		super(context);
		client = (Client)methods.botInstance;
	}
	public Client client(){
		return client;
	}
	public MouseListener mouseListener(){
		return client.mouseListener();
	}
	public KeyboardListener keyboardListener(){
		return client.keyboardListener();
	}
	public boolean resizeMode(){
		return client.resizeMode();
	}
	public int gameCycle(){
		return client.gameCycle();
	}
	
	public int mapBaseX(){
		return client.mapBaseX();
	}
	public int mapBaseY(){
		return client.mapBaseY();
	}
	
	public Widget[][] widgets(){
		return client.widgets();
	}
	public int[] widgetPositionsX(){
		return client.widgetPositionsX();
	}
	public int[] widgetPositionsY(){
		return client.widgetPositionsY();
	}
	public int[] widgetWidths(){
		return client.widgetWidths();
	}
	public int[] widgetHeights(){
		return client.widgetHeights();
	}
	public HashTable componentTable(){
		return client.componentTable();
	}
	public int widgetVisibleCycle(){
		return client.widgetVisibleCycle();
	}
	public HashTable itemContainers(){
		return client.itemContainers();
	}
	public ItemStorage[] getItemStorages(){
		ItemStorage[] storages = new ItemStorage[32];
		HashTable table = itemContainers();
		int index=0;
		for(Node node : table.buckets()){
			if(node!=null){
				if(!node.next().equals(node) && node.next() instanceof ItemStorage){
					storages[index] = (ItemStorage)node.next();
				}
			}
			index++;
		}
		return storages;
	}
	
	public int cameraX(){
		return client.cameraX();
	}
	public int cameraZ(){
		return client.cameraZ();
	}
	public int cameraY(){
		return client.cameraY();
	}
	public int cameraPitch(){
		return client.cameraPitch();
	}
	public int cameraYaw(){
		return client.cameraYaw();
	}
	public int viewportScale(){
		return client.viewportScale();
	}
	public int viewportHeight(){
		return client.viewportHeight();
	}
	public int viewportWidth(){
		return client.viewportWidth();
	}
	
	public int[][][] tileHeights(){
		return client.tileHeights();
	}
	public byte[][][] sceneRenderRules(){
		return client.sceneRenderRules();
	}
	public int mapRotation(){
		return client.mapRotation();
	}
	
	public Player[] players(){
		return client.players();
	}
	public Player localPlayer(){
		return client.localPlayer();
	}
	public Npc[] npcs(){
		return client.npcs();
	}
	public int currentPlane(){
		return client.currentPlane();
	}
	
	public int[] currentSkillLevels(){
		return client.currentSkillLevels();
	}
	public int[] skillExperiences(){
		return client.skillExperiences();
	}
	public int[] absoluteSkillLevels(){
		return client.absoluteSkillLevels();
	}
	
	public int lastSelectedItemIndex(){
		return client.lastSelectedItemIndex();
	}
	public int itemSelectionState(){
		return client.itemSelectionState();
	}
	public Cache itemModelCache(){
		return client.itemModelCache();
	}
	
	public boolean menuOpen(){
		return client.menuOpen();
	}
	public String[] menuTargets(){
		return client.menuTargets();
	}
	public String[] menuActions(){
		return client.menuActions();
	}
	public int menuItemCount(){
		return client.menuItemCount();
	}
	public int menuHeight(){
		return client.menuHeight();
	}
	public int menuWidth(){
		return client.menuWidth();
	}
	public int menuY(){
		return client.menuY();
	}
	public int menuX(){
		return client.menuX();
	}
	
	public int[] varps(){
		return client.varps();
	}
	
	public Region region(){
		return client.region();
	}
	
	public int destinationX(){
		return client.destinationX();
	}
	public int destinationY(){
		return client.destinationY();
	}
	public int runEnergy(){
		return client.runEnergy();
	}
	public Deque drawnTileDeque(){
		return client.drawnTileDeque();
	}
	public RuneScriptVM runescriptVM(){
		return client.runescriptVM();
	}
	public int mouseCrosshairState(){
		return client.mouseCrosshairState();
	}
	public CollisionMap[] collisionMaps(){
		return client.collisionMaps();
	}
	public boolean spellSelected(){
		return client.spellSelected();
	}
	public int mouseIdleTicks(){
		return client.mouseIdleTicks();
	}
	public void preventIdleMouse(boolean val){
		client.setPreventIdleMouse(val);
	}
	
	public ItemDefinition getItemDefinition(int id){
		return client.invoke_getItemDefinition(id);
	}
	public ObjectDefinition getObjectDefinition(int id){
		return client.invoke_getObjectDefinition(id);
	}
	public void addGameMessage(String message){
		addGameMessage(108, message);
	}
	public void addGameMessage(int type, String message){
		addGameMessage(type, message, null, "");
	}
	public void addGameMessage(int type, String message, String sender, String name){
		if(client!=null){
			client.invoke_addChatMessage(type, name, message, sender);
		}
	}
	public void runScriptEvent(Object[] args){
		client.runScriptEvent(args);
	}
}
