package org.osrs.api.objects;

public class WeaponType {
	/**
	 * Just a string to hold a name for the
	 * weaponType.
	 */
	private String name;
	/**
	 * Value from equipped weaponType varp.
	 */
	private int id;
	/**
	 * 1 - melee
	 * 2 - magic
	 * 3 - range
	 */
	private CombatType combatType;
	/**
	 * @param s - name
	 * @param i - id
	 * @param type - combatType
	 */
	public WeaponType(String s, int i, CombatType type){
		name = s;
		id = i;
		combatType = type;
	}
	public String getName(){
		return name;
	}
	public int getID(){
		return id;
	}
	public CombatType getCombatType(){
		return combatType;
	}
	@Override
	public boolean equals(Object o){
		if(o instanceof WeaponType)
			return this.id==((WeaponType)o).id;
		return false;
	}
	@Override
	public String toString(){
		return name+"["+combatType.getName()+"]";
	}
	//TODO still need salamanders
	public static final WeaponType UNARMED = new WeaponType("Unarmed", 0, CombatType.MELEE);
	public static final WeaponType AXES = new WeaponType("Axes", 1, CombatType.MELEE);
	public static final WeaponType BLUNT = new WeaponType("Blunt", 2, CombatType.MELEE);
	public static final WeaponType BOWS = new WeaponType("Bows", 3, CombatType.RANGED);
	public static final WeaponType CLAWS = new WeaponType("Claws", 4, CombatType.MELEE);
	public static final WeaponType CROSSBOWS = new WeaponType("Crossbows", 5, CombatType.RANGED);
	public static final WeaponType BLASTER = new WeaponType("Blaster", 6, CombatType.RANGED);
	public static final WeaponType CHINCHOMPA = new WeaponType("Chinchompa", 7, CombatType.RANGED);
	public static final WeaponType GUN = new WeaponType("Gun", 8, CombatType.RANGED);
	public static final WeaponType SLASH = new WeaponType("Slash", 9, CombatType.MELEE);
	public static final WeaponType TWO_HAND = new WeaponType("Two hand", 10, CombatType.MELEE);
	public static final WeaponType PICKAXE = new WeaponType("Pickaxe", 11, CombatType.MELEE);
	public static final WeaponType POLEARM = new WeaponType("Polearm", 12, CombatType.MELEE);
	public static final WeaponType POLESTAFF = new WeaponType("Polestaff", 13, CombatType.MELEE);
	public static final WeaponType SCYTHE = new WeaponType("Scythe", 14, CombatType.MELEE);
	public static final WeaponType SPEAR = new WeaponType("Spear", 15, CombatType.MELEE);
	public static final WeaponType SPIKED = new WeaponType("Spiked", 16, CombatType.MELEE);
	public static final WeaponType STAB = new WeaponType("Stab", 17, CombatType.MELEE);
	public static final WeaponType STAFF = new WeaponType("Staff", 18, CombatType.MAGIC);
	public static final WeaponType THROWN = new WeaponType("Thrown", 19, CombatType.RANGED);
	public static final WeaponType WHIP = new WeaponType("Whip", 20, CombatType.MELEE);
	public static final WeaponType STAFF_BLADED = new WeaponType("Staff bladed", 21, CombatType.MAGIC);
	public static final WeaponType UNUSED_1 = new WeaponType("Unused 1", 22, CombatType.UNKNOWN);
	public static final WeaponType STAFF_POWERED = new WeaponType("Staff powered", 23, CombatType.MAGIC);
	public static final WeaponType BANNER = new WeaponType("Banner", 24, CombatType.MELEE);
	public static final WeaponType UNUSED_2 = new WeaponType("Unused 2", 25, CombatType.UNKNOWN);
	public static final WeaponType BLUDGEON = new WeaponType("Bludgeon", 26, CombatType.MELEE);
	public static final WeaponType BULWARK = new WeaponType("Bulwark", 27, CombatType.MELEE);
	public static final WeaponType[] allWeaponTypes = new WeaponType[]{
			UNARMED, AXES, BLUNT, BOWS, CLAWS, CROSSBOWS, BLASTER, CHINCHOMPA, GUN,
			SLASH, TWO_HAND, PICKAXE, POLEARM, POLESTAFF, SCYTHE, SPEAR, SPIKED,
			STAB, STAFF, THROWN, WHIP, STAFF_BLADED, UNUSED_1, STAFF_POWERED,
			BANNER, UNUSED_2, BLUDGEON, BULWARK};
}
