package org.osrs.api.methods;

import java.util.ArrayList;

import org.osrs.api.objects.PrayerAbility;
import org.osrs.api.objects.RSInterface;
import org.osrs.api.objects.RSWidget;

public class Prayer extends MethodDefinition{
	private RSInterface quickPrayerParent;
	private RSWidget quickPrayerWindow;
	private RSWidget quickPrayerDoneButton;
	
	public Prayer(MethodContext context){
		super(context);
	}
	public boolean prayerTabSelected(){
		return methods.tabs.prayer.isSelected();
	}
	public boolean closeQuickPrayerSelection(){
		if(quickPrayerSelectionOpen()){
			if(quickPrayerDoneButton!=null && quickPrayerDoneButton.isVisible()){
				if(quickPrayerDoneButton.click()){
					for(int i=0;i<20;++i){
						sleep(random(100, 200));
						if(!quickPrayerSelectionOpen())
							break;
					}
					return !quickPrayerSelectionOpen();
				}
			}
		}
		return false;
	}
	public boolean quickPrayerSelectionOpen(){
		return quickPrayerWindow!=null && quickPrayerWindow.isVisible();
	}
	public boolean quickPrayerActivated(){
		return methods.game.getVarbit(4103)==1;
	}
	public PrayerAbility[] getQuickPrayers(){
		ArrayList<PrayerAbility> prayers = new ArrayList<PrayerAbility>();
		int setting = methods.game.getVarbit(4102);
		for(PrayerAbility prayer : allPrayers){
			if((setting&(int)Math.pow(2, prayer.getIndex()))==((int)Math.pow(2, prayer.getIndex())))
				prayers.add(prayer);
		}
		return prayers.toArray(new PrayerAbility[]{});
	}
	public int getQuickPrayerCount(){
		int count = 0;
		int setting = methods.game.getVarbit(4102);
		for(PrayerAbility p : allPrayers)
			if((setting&(int)Math.pow(2, p.getIndex()))==((int)Math.pow(2, p.getIndex())))
				count++;
		return count;
	}
	public int getActivatedPrayerCount(){
		int count = 0;
		for(PrayerAbility p : allPrayers)
			if(p.isActivated())
				count++;
		return count;
	}
	/**
	 * More than 1 can be active at any time.
	 * @return
	 */
	public PrayerAbility[] getActivatedPrayers(){
		ArrayList<PrayerAbility> prayers = new ArrayList<PrayerAbility>();
		for(PrayerAbility prayer : allPrayers){
			if(prayer.isActivated())
				prayers.add(prayer);
		}
		return prayers.toArray(new PrayerAbility[]{});
	}
	public PrayerAbility THICK_SKIN = new PrayerAbility(methods, "Thick Skin", 4104, 1, 0);
	public PrayerAbility BURST_OF_STRENGTH = new PrayerAbility(methods, "Burst of Strength", 4105, 4, 1);
	public PrayerAbility CLARITY_OF_THOUGHT = new PrayerAbility(methods, "Clarity of Thought", 4106, 7, 2);
	public PrayerAbility ROCK_SKIN = new PrayerAbility(methods, "Rock Skin", 4107, 10, 3);
	public PrayerAbility SUPERHUMAN_STRENGTH = new PrayerAbility(methods, "Superhuman Strength", 4108, 13, 4);
	public PrayerAbility IMPROVED_REFLEXES = new PrayerAbility(methods, "Improved Reflexes", 4109, 16, 5);
	public PrayerAbility RAPID_RESTORE = new PrayerAbility(methods, "Rapid Restore", 4110, 19, 6);
	public PrayerAbility RAPID_HEAL = new PrayerAbility(methods, "Rapid Heal", 4111, 22, 7);
	public PrayerAbility PROTECT_ITEM = new PrayerAbility(methods, "Protect Item", 4112, 25, 8);
	public PrayerAbility STEEL_SKIN = new PrayerAbility(methods, "Steel Skin", 4113, 28, 9);
	public PrayerAbility ULTIMATE_STRENGTH = new PrayerAbility(methods, "Ultimate Strength", 4114, 31, 10);
	public PrayerAbility INCREDIBLE_REFLEXES = new PrayerAbility(methods, "Incredible Reflexes", 4115, 34, 11);
	public PrayerAbility PROTECT_FROM_MAGIC = new PrayerAbility(methods, "Protect from Magic", 4116, 37, 12);
	public PrayerAbility PROTECT_FROM_MISSILES = new PrayerAbility(methods, "Protect from Missiles", 4117, 40, 13);
	public PrayerAbility PROTECT_FROM_MELEE = new PrayerAbility(methods, "Protect from Melee", 4118, 43, 14);
	public PrayerAbility RETRIBUTION = new PrayerAbility(methods, "Retribution", 4119, 46, 15);
	public PrayerAbility REDEMPTION = new PrayerAbility(methods, "Redemption", 4120, 49, 16);
	public PrayerAbility SMITE = new PrayerAbility(methods, "Smite", 4121, 52, 17);
	public PrayerAbility SHARP_EYE = new PrayerAbility(methods, "Sharp Eye", 4122, 8, 18);
	public PrayerAbility MYSTIC_WILL = new PrayerAbility(methods, "Mystic Will", 4123, 9, 19);
	public PrayerAbility HAWK_EYE = new PrayerAbility(methods, "Hawk Eye", 4124, 26, 20);
	public PrayerAbility MYSTIC_LORE = new PrayerAbility(methods, "Mystic Lore", 4125, 27, 21);
	public PrayerAbility EAGLE_EYE = new PrayerAbility(methods, "Eagle Eye", 4126, 44, 22);
	public PrayerAbility MYSTIC_MIGHT = new PrayerAbility(methods, "Mystic Might", 4127, 45, 23);
	public PrayerAbility RIGOUR = new PrayerAbility(methods, "Rigour", 5464, 74, 24);
	public PrayerAbility CHIVALRY = new PrayerAbility(methods, "Chivalry", 4128, 55, 25);
	public PrayerAbility PIETY = new PrayerAbility(methods, "Piety", 4129, 60, 26);
	public PrayerAbility AUGURY = new PrayerAbility(methods, "Augury", 5465, 77, 27);
	public PrayerAbility PRESERVE = new PrayerAbility(methods, "Preserve", 5466, 70, 28);
	public PrayerAbility[] allPrayers = new PrayerAbility[]{
			THICK_SKIN, BURST_OF_STRENGTH, CLARITY_OF_THOUGHT, 
			ROCK_SKIN, SUPERHUMAN_STRENGTH, IMPROVED_REFLEXES,
			RAPID_RESTORE, RAPID_HEAL, PROTECT_ITEM,
			STEEL_SKIN, ULTIMATE_STRENGTH, INCREDIBLE_REFLEXES,
			PROTECT_FROM_MAGIC, PROTECT_FROM_MISSILES, PROTECT_FROM_MELEE,
			RETRIBUTION, REDEMPTION, SMITE, 
			SHARP_EYE, MYSTIC_WILL, 
			HAWK_EYE, MYSTIC_LORE,
			EAGLE_EYE, MYSTIC_MIGHT, 
			RIGOUR, CHIVALRY, PIETY, 
			AUGURY, PRESERVE};
	public void updatePrayerWidgets(RSInterface qpParent, RSWidget qpWindow){
		quickPrayerParent = qpParent;
		quickPrayerWindow = qpWindow;
		if(quickPrayerParent!=null){
			for(RSWidget w : quickPrayerParent.getChildren()){
				if(w!=null){
					if(w.isVisible()){
						if(w.containsAction("Done"))
							quickPrayerDoneButton = w;
					}
				}
			}
		}
	}
}
