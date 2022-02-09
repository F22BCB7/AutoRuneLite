package org.osrs.api.methods;

import java.util.Arrays;

import org.osrs.api.objects.RSInterface;
import org.osrs.api.objects.RSWidget;
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
	
	public int getVarbit(int id){
		return client.invoke_getVarp(id);
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
	
	public void updateWidgets(){
		RSInterface autocastSelectionParent=null;
		RSWidget autocastSelectionWindow=null;
		RSInterface depositBoxParent=null;
		RSWidget depositBoxWindow=null;
		RSInterface bankParent=null;
		RSWidget bankWindow=null;
		RSInterface bankTutorialParent=null;
		RSInterface chatboxParent=null;
		RSWidget chatboxWindow=null;
		RSInterface equipmentParent = null;
		RSWidget equipmentWindow = null;
		RSInterface equipmentStatsParent = null;
		RSWidget equipmentStatsWindow = null;
		RSInterface priceCheckerParent = null;
		RSWidget priceCheckerWindow = null;
		RSInterface itemsKeptOnDeathParent = null;
		RSWidget itemsKeptOnDeathWindow = null;
		
		//Find all parent interfaces, and cuts early once parent is found, 
		//so the entire parents children is not iterated through this.
		for(RSInterface i : methods.widgets.getAll()){
			if(i!=null){
				parentchilds:for(RSWidget w : i.getChildren()){
					if(w!=null){
						if(w.containsAction("View equipment stats")){//finds button
							equipmentParent = i;
							equipmentWindow = methods.widgets.getChild(w.getParentID());
							break parentchilds;
						}
						if(w.isDisplayed()){
							if(w.spriteID()==2524){
								bankTutorialParent = i;
								break parentchilds;
							}
							else if(w.disabledText().equals("The Bank of Gielinor")){
								bankParent = i;
								bankWindow = methods.widgets.getChild(w.getParentID());
								break parentchilds;
							}
							else if(w.clickMask()==0 && w.spriteID()==297 && w.alpha()==0 && w.boundsIndex()==1){
								equipmentStatsParent = i;
								equipmentStatsWindow = w;
								break parentchilds;
							}
						}
						RSWidget[] children = w.getChildren();
						if(children==null)
							continue;
						for(RSWidget child : children){
							if(child!=null){
								if(child.isDisplayed()){
									if(child.spriteID()==1017){
										chatboxParent = i;
										RSWidget parent1 = methods.widgets.getChild(w.getParentID());
										if(parent1!=null){
											RSWidget parent2 = methods.widgets.getChild(parent1.getParentID());
											if(parent2!=null && parent2.isVisible()){
												chatboxWindow = parent2;
												break parentchilds;
											}
										}
									}
									else if(child.disabledText().equals("Select a Combat Spell")){
										autocastSelectionWindow = child;
										autocastSelectionParent = i;
										break parentchilds;
									}
									else if(child.disabledText().equals("Grand Exchange guide prices")){
										priceCheckerParent = i;
										priceCheckerWindow = w;
										break parentchilds;
									}
									else if(child.disabledText().equals("Items Kept on Death")){
										itemsKeptOnDeathParent = i;
										itemsKeptOnDeathWindow = w;
										break parentchilds;
									}
									else if(child.disabledText().equals("The Bank of Gielinor")){
										depositBoxWindow = methods.widgets.getChild(w.getParentID());
										depositBoxParent = i;
										break parentchilds;
									}
								}
							}
						}
					}
				}
			}
		}
		
		//Then update the various method API.
		//Finds children specific to the parent interfaces.
		//Prevents false-positives/duplicates (like exit buttons).
		methods.bank.updateBankWidgets(bankParent, bankWindow, bankTutorialParent, depositBoxParent, depositBoxWindow);
		methods.combat.updateCombatWidgets(autocastSelectionParent, autocastSelectionWindow);
		methods.chatbox.updateChatboxWidgets(chatboxParent, chatboxWindow);
		methods.equipment.updateEquipmentWidgets(equipmentParent, equipmentWindow, equipmentStatsParent, equipmentStatsWindow, priceCheckerParent, priceCheckerWindow, itemsKeptOnDeathParent, itemsKeptOnDeathWindow);
		
	}
}
