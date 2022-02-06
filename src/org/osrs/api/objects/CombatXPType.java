package org.osrs.api.objects;

public class CombatXPType {
	private String name;
	private int varpValue;
	public CombatXPType(String n, int id){
		name = n;
		varpValue = id;
	}
	public int getID(){
		return varpValue;
	}
	public String getName(){
		return name;
	}
	@Override
	public boolean equals(Object o){
		if(o instanceof CombatXPType)
			return this.varpValue==((CombatXPType)o).varpValue;
		return false;
	}
	@Override
	public String toString(){
		return name;
	}
	public static CombatXPType UNKNOWN = new CombatXPType("Unknown", -1);
	public static CombatXPType CONTROLLED = new CombatXPType("Controlled", 0);
	public static CombatXPType ATTACK = new CombatXPType("Attack", 1);
	public static CombatXPType MAGIC = new CombatXPType("Magic", 1);
	public static CombatXPType DEFENSIVE_MAGIC = new CombatXPType("Defensive Magic", 1);
	public static CombatXPType RANGED = new CombatXPType("Ranged", 1);
	public static CombatXPType DEFENSIVE_RANGED = new CombatXPType("Defensive Ranged", 1);
	public static CombatXPType STRENGTH = new CombatXPType("Strength", 2);
	public static CombatXPType DEFENSE = new CombatXPType("Defense", 3);
	public static CombatXPType[] allXPTypes = new CombatXPType[]{
		UNKNOWN, CONTROLLED, ATTACK, MAGIC, DEFENSIVE_MAGIC, RANGED, STRENGTH, DEFENSE	
	};
}
