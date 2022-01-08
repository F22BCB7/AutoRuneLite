package org.osrs.api.methods;

import java.util.ArrayList;
import java.util.HashMap;

import org.osrs.api.objects.MagicSpell;

public class Magic extends MethodDefinition{
	public Magic(MethodContext context){
		super(context);
		createSpellObjects();
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
	public MagicSpell[] allSpells;
	private void createSpellObjects(){
		allSpells = new MagicSpell[]{
				//Standard spell book
					new MagicSpell(methods, "Lumbridge Home Teleport", 0, 0),
					new MagicSpell(methods, "Wind Strike", 1, 5.5),
					new MagicSpell(methods, "Confuse", 3, 13),
					new MagicSpell(methods, "Enchant Crossbow Bolt", 4, 9),
					new MagicSpell(methods, "Water Strike", 5, 7.5),
					new MagicSpell(methods, "Lvl-1 Enchant", 5, 17.5),
					new MagicSpell(methods, "Earth Strike", 9, 9.5),
					new MagicSpell(methods, "Weaken", 11, 21),
					new MagicSpell(methods, "Fire Strike", 13, 11.5),
					new MagicSpell(methods, "Bones to Bananas", 15, 25),
					new MagicSpell(methods, "Wind Bolt", 17, 13.5),
					new MagicSpell(methods, "Curse", 19, 29),
					new MagicSpell(methods, "Bind", 20, 30),
					new MagicSpell(methods, "Low Level Alchemy", 21, 31),
					new MagicSpell(methods, "Water Bolt", 23, 16.5),
					new MagicSpell(methods, "Varrock Teleport", 25, 35),
					new MagicSpell(methods, "Lvl-2 Enchant", 27, 37),
					new MagicSpell(methods, "Earth Bolt", 29, 19.5),
					new MagicSpell(methods, "Lumbridge Teleport", 31, 41),
					new MagicSpell(methods, "Telekinetic Grab", 33, 43),
					new MagicSpell(methods, "Fire Bolt", 35, 22.5),
					new MagicSpell(methods, "Falador Teleport", 37, 47),
					new MagicSpell(methods, "Crumble Undead", 39, 24.5),
					new MagicSpell(methods, "Teleport to House", 40, 30),
					new MagicSpell(methods, "Wind Blast", 41, 25.5),
					new MagicSpell(methods, "Superheat Item", 43, 53),
					new MagicSpell(methods, "Camelot Teleport", 45, 34),
					new MagicSpell(methods, "Water Blast", 47, 28.5),
					new MagicSpell(methods, "Lvl-3 Enchant", 49, 59),
					new MagicSpell(methods, "Iban Blast", 50, 61),
					new MagicSpell(methods, "Snare", 50, 60),
					new MagicSpell(methods, "Magic Dart", 50, 61),
					new MagicSpell(methods, "Ardougne Teleport", 51, 61),
					new MagicSpell(methods, "Earth Blast", 53, 31.5),
					new MagicSpell(methods, "High Level Alchemy", 55, 65),
					new MagicSpell(methods, "Charge Water Orb", 56, 56),
					new MagicSpell(methods, "Lvl-4 Enchant", 57, 67),
					new MagicSpell(methods, "Watchtower Teleport", 58, 68),
					new MagicSpell(methods, "Fire Blast", 59, 34.5),
					new MagicSpell(methods, "Charge Earth Orb", 60, 70),
					new MagicSpell(methods, "Bones to Peaches", 60, 65),
					new MagicSpell(methods, "Saradomin Strike", 60, 61),
					new MagicSpell(methods, "Claws of Guthix", 60, 61),
					new MagicSpell(methods, "Flames of Zamorak", 60, 61),
					new MagicSpell(methods, "Trollheim Teleport", 61, 68),
					new MagicSpell(methods, "Wind Wave", 62, 36),
					new MagicSpell(methods, "Charge Fire Orb", 63, 73),
					new MagicSpell(methods, "Ape Atoll Teleport", 64, 74),
					new MagicSpell(methods, "Water Wave", 65, 37.5),
					new MagicSpell(methods, "Charge Air Orb", 66, 76),
					new MagicSpell(methods, "Vulnerability", 66, 76),
					new MagicSpell(methods, "Lvl-5 Enchant", 68, 78),
					new MagicSpell(methods, "Kourend Castle Teleport", 69, 82),
					new MagicSpell(methods, "Earth Wave", 70, 40),
					new MagicSpell(methods, "Enfeeble", 73, 83),
					new MagicSpell(methods, "Teleother Lumbridge", 74, 84),
					new MagicSpell(methods, "Fire Wave", 75, 42.5),
					new MagicSpell(methods, "Entangle", 79, 89),
					new MagicSpell(methods, "Stun", 80, 90),
					new MagicSpell(methods, "Charge", 80, 180),
					new MagicSpell(methods, "Wind Surge", 81, 75),
					new MagicSpell(methods, "Teleother Falador", 82, 92),
					new MagicSpell(methods, "Water Surge", 81, 75),
					new MagicSpell(methods, "Tele Block", 85, 80),
					new MagicSpell(methods, "Teleport to Target", 85, 45),
					new MagicSpell(methods, "Lvl-6 Enchant", 87, 97),
					new MagicSpell(methods, "Teleother Camelot", 90, 100),
					new MagicSpell(methods, "Earth Surge", 90, 85),
					new MagicSpell(methods, "Lvl-7 Enchant", 93, 110),
					new MagicSpell(methods, "Fire Surge", 95, 90)
			};
	}
}
