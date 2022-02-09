package org.osrs.api.methods;

import org.osrs.api.objects.CombatType;
import org.osrs.api.objects.CombatXPType;
import org.osrs.api.objects.MagicSpell;
import org.osrs.api.objects.PrayerAbility;
import org.osrs.api.objects.RSInterface;
import org.osrs.api.objects.RSNpc;
import org.osrs.api.objects.RSPlayer;
import org.osrs.api.objects.RSWidget;
import org.osrs.api.objects.WeaponType;

public class Combat extends MethodDefinition{
	//TODO Prayers, Players (PVP)
	
	private RSInterface autocastSelectionParent;
	private RSWidget autocastSelectionWindow;
	private RSWidget autocastSelectionCancelButton;
	public Combat(MethodContext context){
		super(context);
	}
	public int getCombatLevel(){
		RSPlayer p = methods.players.getLocalPlayer();
		if(p!=null)
			return p.getCombatLevel();
		return -1;
	}
	public int getMagicLevel(){
		return methods.magic.getLevel();
	}
	public int getRangedLevel(){
		return methods.skills.getSkillLevel(Skills.RANGE_INDEX);
	}
	public int getAttackLevel(){
		return methods.skills.getSkillLevel(Skills.ATTACK_INDEX);
	}
	public int getStrengthLevel(){
		return methods.skills.getSkillLevel(Skills.STRENGTH_INDEX);
	}
	public int getDefenseLevel(){
		return methods.skills.getSkillLevel(Skills.DEFENSE_INDEX);
	}
	public int getConstitutionLevel(){
		return methods.skills.getSkillLevel(Skills.CONSTITUTION_INDEX);
	}
	public int getPrayerLevel(){
		return methods.skills.getSkillLevel(Skills.PRAYER_INDEX);
	}
	public boolean isAutoRetaliateOn(){
		return methods.varps.get(172)==0;
	}
	public boolean combatTabSelected(){
		return methods.tabs.combat.isSelected();
	}
	public WeaponType getEquippedWeaponType(){
		int val = methods.varps.get(843);
		for(WeaponType type : WeaponType.allWeaponTypes){
			if(type.getID()==val)
				return type;
		}
		return WeaponType.UNARMED;
	}
	public CombatType getCurrentCombatType(){
		CombatType type = getEquippedWeaponType().getCombatType();
		if(type.equals(CombatType.MAGIC)){
			if(!isAutocastSet())
				return CombatType.MELEE;
		}
		return type;
	}
	public boolean isDefensiveAutocastSet(){
		return methods.varps.get(439)==256;
	}
	public boolean isAutocastSet(){
		return methods.game.client().invoke_getVarp(275)==1;
	}
	/**
	 * A spell needs to be selected before autocast starts
	 * using that spell if returns true. 
	 * If a spell was previously set, then that spell is used 
	 * until another is selected if this method returns true.
	 * @return true if the selection window was opened
	 * and a spell is needing selected. This can return true even 
	 * if its not displayed (e.g. another tab is selected).
	 */
	public boolean isAutocastSelectionNeeded(){
		return autocastSelectionWindow!=null && autocastSelectionWindow.displayCycle()!=-1;
	}
	public boolean isAutocastSelectionOpen(){
		return autocastSelectionWindow!=null && autocastSelectionWindow.isDisplayed();
	}
	public boolean cancelAutocastSelection(){
		if(isAutocastSelectionNeeded()){
			if(!isAutocastSelectionOpen()){
				if(methods.tabs.combat.click()){
					for(int i=0;i<20;++i){
						sleep(random(100, 200));
						if(isAutocastSelectionOpen())
							break;
					}
				}
				else
					return false;
			}
			if(isAutocastSelectionOpen()){
				if(autocastSelectionCancelButton.click()){
					for(int i=0;i<20;++i){
						sleep(random(100, 200));
						if(!isAutocastSelectionNeeded())
							break;
					}
				}
			}
		}
		return !isAutocastSelectionNeeded();
	}
	public int getAutocastSpellID(){
		return methods.game.client().invoke_getVarp(276);
	}
	public MagicSpell getAutocastSpell(){
		int id = getAutocastSpellID();
		for(MagicSpell spell : methods.magic.allSpells){
			if(spell.getVarbitID()==id)
				return spell;
		}
		return null;
	}
	public int getXPTypeID(){
		return methods.varps.get(46);
	}
	public CombatXPType getCurrentXPType(){
		int id = getXPTypeID();
		if(id==1){
			WeaponType weapon = getEquippedWeaponType();
			if(weapon.getCombatType().equals(CombatType.MAGIC)){
				if(isDefensiveAutocastSet())
					return CombatXPType.DEFENSIVE_MAGIC;
				if(isAutocastSet())
					return CombatXPType.MAGIC;
				return CombatXPType.ATTACK;
			}
			else if(weapon.getCombatType().equals(CombatType.RANGED)){
				if(methods.varps.get(43)==3)
					return CombatXPType.DEFENSIVE_RANGED;
				return CombatXPType.RANGED;
			}
			else if(weapon.getCombatType().equals(CombatType.MELEE)){
				return CombatXPType.ATTACK;
			}
		}
		for(CombatXPType type : CombatXPType.allXPTypes){
			if(type.getID()==id)
				return type;
		}
		return CombatXPType.UNKNOWN;
	}
	public RSNpc[] getAllNPCsNotInCombat(){
		return methods.npcs.getAllNotInCombat();
	}
	public RSNpc[] getAllNotInCombatByID(int id){
		return methods.npcs.getAllNotInCombatByID(id);
	}
	public RSNpc[] getAllNotInCombatByID(int...ids){
		return methods.npcs.getAllNotInCombatByID(ids);
	}
	public RSNpc[] getAllNotInCombatByName(String name){
		return methods.npcs.getAllNotInCombatByName(name);
	}
	public RSNpc[] getAllNotInCombatByName(String...names){
		return methods.npcs.getAllNotInCombatByName(names);
	}
	public RSNpc getNearestNotInCombatByID(int id){
		return methods.npcs.getNearestNotInCombatByID(id);
	}
	public RSNpc getNearestNotInCombatByID(int...ids){
		return methods.npcs.getNearestNotInCombatByID(ids);
	}
	public RSNpc getNearestNotInCombatByName(String name){
		return methods.npcs.getNearestNotInCombatByName(name);
	}
	public RSNpc getNearestNotInCombatByName(String...names){
		return methods.npcs.getNearestNotInCombatByName(names);
	}
	public RSNpc[] getNPCsTargetingLocalPlayer() {
		return methods.npcs.getNPCsTargetingLocalPlayer();
	}
	public RSNpc[] getNPCsTargetingLocalPlayerByID(int id) {
		return methods.npcs.getNPCsTargetingLocalPlayerByID(id);
	}
	public RSNpc[] getNPCsTargetingLocalPlayerByID(int...ids) {
		return methods.npcs.getNPCsTargetingLocalPlayerByID(ids);
	}
	public RSNpc[] getNPCsTargetingLocalPlayerByName(String name) {
		return methods.npcs.getNPCsTargetingLocalPlayerByName(name);
	}
	public RSNpc[] getNPCsTargetingLocalPlayerByName(String...names) {
		return methods.npcs.getNPCsTargetingLocalPlayerByName(names);
	}
	public RSNpc getNearestNPCTargetingLocalPlayer() {
		return methods.npcs.getNearestNPCTargetingLocalPlayer();
	}
	public RSNpc getNearestNPCTargetingLocalPlayerByID(int id) {
		return methods.npcs.getNearestNPCTargetingLocalPlayerByID(id);
	}
	public RSNpc getNearestNPCTargetingLocalPlayerByID(int...ids) {
		return methods.npcs.getNearestNPCTargetingLocalPlayerByID(ids);
	}
	public RSNpc getNearestNPCTargetingLocalPlayerByName(String name) {
		return methods.npcs.getNearestNPCTargetingLocalPlayerByName(name);
	}
	public RSNpc getNearestNPCTargetingLocalPlayerByName(String...names) {
		return methods.npcs.getNearestNPCTargetingLocalPlayerByName(names);
	}
	public int getActivatedPrayerCount(){
		return methods.prayer.getActivatedPrayerCount();
	}
	public PrayerAbility[] getActivatedPrayers(){
		return methods.prayer.getActivatedPrayers();
	}
	public void updateCombatWidgets(RSInterface parent, RSWidget window){
		autocastSelectionParent = parent;
		autocastSelectionWindow = window;
		if(autocastSelectionParent!=null){
			for(RSWidget w : autocastSelectionParent.getChildren()){
				if(w!=null){
					if(w.isDisplayed()){
					}
					RSWidget[] children = w.getChildren();
					if(children==null)
						continue;
					for(RSWidget child : children){
						if(child!=null){
							if(child.isDisplayed()){
								if(child.disabledText().equals("Cancel"))
									autocastSelectionCancelButton = child;
							}
						}
					}
				}
			}
		}
	}
}
