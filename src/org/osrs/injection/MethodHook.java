package org.osrs.injection;

public class MethodHook {
	public String owner = null;
	public String obfuscatedName = null;
	public String refactoredName = null;
	public String desc = null;
	public Object predicate = null;//dummy param
	public boolean hooked=false;
	public MethodHook(String owner, String name, String rename, String desc, Object predicate){
		this.owner=owner;
		this.obfuscatedName=name;
		this.refactoredName=rename;
		this.desc=desc;
		this.predicate=predicate;
	}
}
