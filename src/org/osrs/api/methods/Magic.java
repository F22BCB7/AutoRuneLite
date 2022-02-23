package org.osrs.api.methods;

import java.util.ArrayList;
import org.osrs.api.objects.MagicSpell;

public class Magic extends MethodDefinition{
	public MagicSpell[] allSpells;
	public Magic(MethodContext context){
		super(context);
		createSpellObjects();
	}
	public int getLevel(){
		return methods.skills.getSkillLevel(Skills.MAGIC_INDEX);
	}
	public boolean isDefensiveAutocastSet(){
		return methods.combat.isDefensiveAutocastSet();
	}
	public boolean isAutocastSet(){
		return methods.combat.isAutocastSet();
	}
	public int getAutocastSpellID(){
		return methods.combat.getAutocastSpellID();
	}
	public MagicSpell getAutocastSpell(){
		return methods.combat.getAutocastSpell();
	}
	public boolean isMagicBookOpen(){
		return methods.tabs.magic.isSelected();
	}
	public boolean isSpellSelected(){
		return methods.game.spellSelected();
	}
	public MagicSpell getSelectedSpell(){
		if(!isSpellSelected())
			return null;
		for(MagicSpell spell : allSpells){
			if(spell.isSelected())
				return spell;
		}
		return null;
	}
	public MagicSpell[] getDisplayedSpells(){
		ArrayList<MagicSpell> spells = new ArrayList<MagicSpell>();
		for(MagicSpell spell : allSpells){
			if(spell.isDisplayed())
				spells.add(spell);
		}
		return spells.toArray(new MagicSpell[]{});
	}
	public MagicSpell[] getVisibleSpells(){
		ArrayList<MagicSpell> spells = new ArrayList<MagicSpell>();
		for(MagicSpell spell : allSpells){
			if(spell.isVisible())
				spells.add(spell);
		}
		return spells.toArray(new MagicSpell[]{});
	}
	public MagicSpell getSpell(String name){
		for(MagicSpell spell : allSpells){
			if(spell.name.equals(name))
				return spell;
		}
		return null;
	}
	public boolean magicTabSelected(){
		return methods.tabs.magic.isSelected();
	}
	private void createSpellObjects(){
		allSpells = new MagicSpell[]{
				//Standard spell book
					new MagicSpell(methods, "Lumbridge Home Teleport", 0, 0, -1),
					new MagicSpell(methods, "Wind Strike", 1, 5.5, 1),
					new MagicSpell(methods, "Confuse", 3, 13, -1),
					new MagicSpell(methods, "Enchant Crossbow Bolt", 4, 9, -1),
					new MagicSpell(methods, "Water Strike", 5, 7.5, 2),
					new MagicSpell(methods, "Lvl-1 Enchant", 5, 17.5, -1),
					new MagicSpell(methods, "Earth Strike", 9, 9.5, 3),
					new MagicSpell(methods, "Weaken", 11, 21, -1),
					new MagicSpell(methods, "Fire Strike", 13, 11.5, 4),
					new MagicSpell(methods, "Bones to Bananas", 15, 25, -1),
					new MagicSpell(methods, "Wind Bolt", 17, 13.5, 5),
					new MagicSpell(methods, "Curse", 19, 29, -1),
					new MagicSpell(methods, "Bind", 20, 30, -1),
					new MagicSpell(methods, "Low Level Alchemy", 21, 31, -1),
					new MagicSpell(methods, "Water Bolt", 23, 16.5, 6),
					new MagicSpell(methods, "Varrock Teleport", 25, 35, -1),
					new MagicSpell(methods, "Lvl-2 Enchant", 27, 37, -1),
					new MagicSpell(methods, "Earth Bolt", 29, 19.5, 7),
					new MagicSpell(methods, "Lumbridge Teleport", 31, 41, -1),
					new MagicSpell(methods, "Telekinetic Grab", 33, 43, -1),
					new MagicSpell(methods, "Fire Bolt", 35, 22.5, 8),
					new MagicSpell(methods, "Falador Teleport", 37, 47, -1),
					new MagicSpell(methods, "Crumble Undead", 39, 24.5, 17),
					new MagicSpell(methods, "Teleport to House", 40, 30, -1),
					new MagicSpell(methods, "Wind Blast", 41, 25.5, 9),
					new MagicSpell(methods, "Superheat Item", 43, 53, -1),
					new MagicSpell(methods, "Camelot Teleport", 45, 34, -1),
					new MagicSpell(methods, "Water Blast", 47, 28.5, 10),
					new MagicSpell(methods, "Lvl-3 Enchant", 49, 59, -1),
					new MagicSpell(methods, "Iban Blast", 50, 61, 47),
					new MagicSpell(methods, "Snare", 50, 60, -1),
					new MagicSpell(methods, "Magic Dart", 50, 61, 18),
					new MagicSpell(methods, "Ardougne Teleport", 51, 61, -1),
					new MagicSpell(methods, "Earth Blast", 53, 31.5, 11),
					new MagicSpell(methods, "High Level Alchemy", 55, 65, -1),
					new MagicSpell(methods, "Charge Water Orb", 56, 56, -1),
					new MagicSpell(methods, "Lvl-4 Enchant", 57, 67, -1),
					new MagicSpell(methods, "Watchtower Teleport", 58, 68, -1),
					new MagicSpell(methods, "Fire Blast", 59, 34.5, 12),
					new MagicSpell(methods, "Charge Earth Orb", 60, 70, -1),
					new MagicSpell(methods, "Bones to Peaches", 60, 65, -1),
					new MagicSpell(methods, "Saradomin Strike", 60, 61, 52),
					new MagicSpell(methods, "Claws of Guthix", 60, 61, 19),
					new MagicSpell(methods, "Flames of Zamorak", 60, 61, 20),
					new MagicSpell(methods, "Trollheim Teleport", 61, 68, -1),
					new MagicSpell(methods, "Wind Wave", 62, 36, 13),
					new MagicSpell(methods, "Charge Fire Orb", 63, 73, -1),
					new MagicSpell(methods, "Ape Atoll Teleport", 64, 74, -1),
					new MagicSpell(methods, "Water Wave", 65, 37.5, 14),
					new MagicSpell(methods, "Charge Air Orb", 66, 76, -1),
					new MagicSpell(methods, "Vulnerability", 66, 76, -1),
					new MagicSpell(methods, "Lvl-5 Enchant", 68, 78, -1),
					new MagicSpell(methods, "Kourend Castle Teleport", 69, 82, -1),
					new MagicSpell(methods, "Earth Wave", 70, 40, 15),
					new MagicSpell(methods, "Enfeeble", 73, 83, -1),
					new MagicSpell(methods, "Teleother Lumbridge", 74, 84, -1),
					new MagicSpell(methods, "Fire Wave", 75, 42.5, 16),
					new MagicSpell(methods, "Entangle", 79, 89, -1),
					new MagicSpell(methods, "Stun", 80, 90, -1),
					new MagicSpell(methods, "Charge", 80, 180, -1),
					new MagicSpell(methods, "Wind Surge", 81, 75, 48),
					new MagicSpell(methods, "Teleother Falador", 82, 92, -1),
					new MagicSpell(methods, "Water Surge", 81, 75, 49),
					new MagicSpell(methods, "Tele Block", 85, 80, -1),
					new MagicSpell(methods, "Teleport to Target", 85, 45, -1),
					new MagicSpell(methods, "Lvl-6 Enchant", 87, 97, -1),
					new MagicSpell(methods, "Teleother Camelot", 90, 100, -1),
					new MagicSpell(methods, "Earth Surge", 90, 85, 50),
					new MagicSpell(methods, "Lvl-7 Enchant", 93, 110, -1),
					new MagicSpell(methods, "Fire Surge", 95, 90, 51)
			};
			/*	val=1,wind_strike_3273
				val=2,water_strike_3275
				val=3,earth_strike_3277
				val=4,fire_strike_3279
				val=5,wind_bolt_3281
				val=6,water_bolt_3285
				val=7,earth_bolt_3288
				val=8,fire_bolt_3291
				val=9,wind_blast_3294
				val=10,water_blast_3297
				val=11,earth_blast_3302
				val=12,fire_blast_3307
				val=13,wind_wave_3313
				val=14,water_wave_3315
				val=15,earth_wave_3319
				val=16,fire_wave_3321
				val=17,crumble_undead_3293
				val=18,magic_dart_4176
				val=47,iban_blast_3299
				val=19,claws_of_guthix_3309
				val=20,flames_of_zamorak_3310
				val=52,saradomin_strike_3311
				val=48,wind_surge_21876
				val=49,water_surge_21877
				val=50,earth_surge_21878
				val=51,fire_surge_21879
				
				val=31,smoke_rush_4629
				val=32,shadow_rush_4630
				val=33,blood_rush_4632
				val=34,ice_rush_4633
				val=35,smoke_burst_4635
				val=36,shadow_burst_4636
				val=37,blood_burst_4638
				val=38,ice_burst_4639
				val=39,smoke_blitz_4641
				val=40,shadow_blitz_4642
				val=41,blood_blitz_4644
				val=42,ice_blitz_4645
				val=43,smoke_barrage_4647
				val=44,shadow_barrage_4648
				val=45,blood_barrage_4650
				val=46,ice_barrage_4651
				
				val=53,inferior_demonbane_20398
				val=54,superior_demonbane_20399
				val=55,dark_demonbane_20400
				val=56,ghostly_grasp_21826
				val=57,skeletal_grasp_21829
				val=58,undead_grasp_21832*/

	}
}
