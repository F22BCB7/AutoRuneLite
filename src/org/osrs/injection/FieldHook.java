package org.osrs.injection;

public class FieldHook {
	public String owner = null;
	public String obfuscatedName = null;
	public String refactoredName = null;
	public String dataType = null;
	public Object multiplier = null;//INT and LONG only
	public boolean hooked=false;
	public FieldHook(String ownerClass, String obf, String name, String desc, Object multi){
		owner=ownerClass;
		obfuscatedName = obf;
		refactoredName = name;
		dataType=desc;
		multiplier = multi;
	}
}
