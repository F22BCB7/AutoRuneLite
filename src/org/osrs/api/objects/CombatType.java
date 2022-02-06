package org.osrs.api.objects;

public class CombatType {
	private String name;
	private int id;
	public CombatType(String s, int i){
		name = s;
		id = i;
	}
	public String getName(){
		return name;
	}
	public int getID(){
		return id;
	}
	@Override
	public boolean equals(Object o){
		if(o instanceof CombatType)
			return this.id==((CombatType)o).id;
		return false;
	}
	public static final CombatType UNKNOWN = new CombatType("Unknown", -1);
	public static final CombatType MELEE = new CombatType("Melee", 1);
	public static final CombatType MAGIC = new CombatType("Magic", 2);
	public static final CombatType RANGED = new CombatType("Ranged", 3);
}
