package org.osrs.api.methods;

import org.osrs.util.Data;
import org.osrs.api.wrappers.Client;

public class Skills extends MethodDefinition{
	public Skills(MethodContext context){
		super(context);
	}
	public static int[] XP_TABLE = {0, 0, 83, 174, 276, 388, 512, 650, 801, 969, 1154, 1358, 1584, 1833, 2107,
		2411, 2746, 3115, 3523, 3973, 4470, 5018, 5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031, 13363, 14833,
		16456, 18247, 20224, 22406, 24815, 27473, 30408, 33648, 37224, 41171, 45529, 50339, 55649, 61512, 67983,
		75127, 83014, 91721, 101333, 111945, 123660, 136594, 150872, 166636, 184040, 203254, 224466, 247886, 273742,
		302288, 333804, 368599, 407015, 449428, 496254, 547953, 605032, 668051, 737627, 814445, 899257, 992895,
		1096278, 1210421, 1336443, 1475581, 1629200, 1798808, 1986068, 2192818, 2421087, 2673114, 2951373, 3258594,
		3597792, 3972294, 4385776, 4842295, 5346332, 5902831, 6517253, 7195629, 7944614, 8771558, 9684577, 10692629,
		11805606, 13034431, 14391160, 15889109, 17542976, 19368992, 21385073, 23611006, 26068632, 28782069,
		31777943, 35085654, 38737661, 42769801, 47221641, 52136869, 57563718, 63555443, 70170840, 77474828,
		85539082, 94442737, 104273167, 115126838, 127110260, 140341028, 154948977, 171077457, 188884740};
	public static String[] SKILL_NAMES = {"attack", "defence", "strength", "constitution", "range", "prayer",
		"magic", "cooking", "woodcutting", "fletching", "fishing", "firemaking", "crafting", "smithing", "mining",
		"herblore", "agility", "thieving", "slayer", "farming", "runecrafting", "hunter", "construction",
		"summoning", "dungeoneering"};
	public static int ATTACK_INDEX = 0;
	public static int DEFENSE_INDEX = 1;
	public static int STRENGTH_INDEX = 2;
	public static int CONSTITUTION_INDEX = 3;
	public static int RANGE_INDEX = 4;
	public static int PRAYER_INDEX = 5;
	public static int MAGIC_INDEX = 6;
	public static int COOKING_INDEX = 7;
	public static int WOODCUTTING_INDEX = 8;
	public static int FLETCHING_INDEX = 9;
	public static int FISHING_INDEX = 10;
	public static int FIREMAKING_INDEX = 11;
	public static int CRAFTING_INDEX = 12;
	public static int SMITHING_INDEX = 13;
	public static int MINING_INDEX = 14;
	public static int HERBLORE_INDEX = 15;
	public static int AGILITY_INDEX = 16;
	public static int THIEVING_INDEX = 17;
	public static int SLAYER_INDEX = 18;
	public static int FARMING_INDEX = 19;
	public static int RUNECRAFTING_INDEX = 20;
	public static int HUNTER_INDEX = 21;
	public static int CONSTRUCTION_INDEX = 22;
	public static int SUMMONING_INDEX = 23;
	public static int DUNGEONEERING_INDEX = 24;
	/**
	 * Gets the experience left required for a given level
	 * for a given stat.
	 * @param statIndex
	 * @param level
	 * @return experienceRequired
	 */
	public int getExpTillLevel(int index, int level){
		int real = getRealSkillLevel(index);
		if(real==-1)
			return -1;
		if (index == DUNGEONEERING_INDEX && (real == 120 || level > 120))
			return 0;
		else if (real == 99 || level > 99)
			return 0;
		return XP_TABLE[level+1] - getSkillExperience(index);
	}
	/**
	 * Gets the experience left required for a level-up.
	 * @param skillIndex
	 * @return experienceRequired
	 */
	public int getExpTillRealLevel(int index, int level){
		int real = getRealSkillLevel(index);
		if(real==-1)
			return -1;
		else if (real == 126 || level > 126)
			return 0;
		return XP_TABLE[level+1] - getSkillExperience(index);
	}
	public int getExpTillNextLevel(int index){
		return getExpTillLevel(index, getSkillLevel(index));
	}
	public int getExpTillNextRealLevel(int index){
		return getExpTillRealLevel(index, getRealSkillLevel(index));
	}
	/**
	 * Calculates level based off of experience.
	 * @param experience
	 * @return level
	 */
	public int getLevel(int exp) {
		for (int i = XP_TABLE.length-1; i>0; i--)
			if (exp>XP_TABLE[i])
				return i;
		return -1;
	}
	/**
	 * Gets the real skill level; this is the level
	 * ignoring level 99 cap (experience based calculation).
	 * @param skillIndex
	 * @return level
	 */
	public int getRealSkillLevel(int index){
		return getLevel(getSkillExperience(index));
	}
	/**
	 * Grabs the experience to a given skill
	 * @param skillIndex
	 * @return experience
	 */
	public int getSkillExperience(int index){
		int[] exps = ((Client)Data.clientInstance).skillExperiences();
		if(index<exps.length)
			return exps[index];
		return -1;
	}
	/**
	 * Gets the skill index based off of the name
	 * ignoring case.
	 * @param statName
	 * @return skillIndex
	 */
	public int getSkillIndex(String statName) {
		for (int i = 0; i < SKILL_NAMES.length; i++)
			if (SKILL_NAMES[i].equalsIgnoreCase(statName))
				return i;
		return -1;
	}
	/**
	 * Gets the current skill level to a given skill.
	 * This accounts for stat reducers/potions.
	 * @param index
	 * @return level
	 */
	public int getSkillLevel(int index){
		int[] lvls = ((Client)Data.clientInstance).currentSkillLevels();
		if(index<lvls.length)
			return lvls[index];
		return -1;
	}
	/**
	 * Gets the real skill level to a given skill.
	 * This ignores stat reducers/potions.
	 * @param index
	 * @return level
	 */
	public int getSkillMaxLevel(int index){
		int[] lvls = ((Client)Data.clientInstance).absoluteSkillLevels();
		if(index<lvls.length)
			return lvls[index];
		return -1;
	}
	/**
	 * Gets a skill name based off of index.
	 * @param skillIndex
	 * @return skillName
	 */
	public String getSkillName(int index) {
		if (index>=SKILL_NAMES.length) {
			return "invalid skill index";
		}
		return SKILL_NAMES[index];
	}
}
