package org.osrs.injection;

import java.util.ArrayList;

import org.objectweb.asm.tree.ClassNode;

public class ClassHook {
	public ArrayList<FieldHook> fieldHooks = new ArrayList<FieldHook>();
	public ArrayList<MethodHook> methodHooks = new ArrayList<MethodHook>();
	public String obfuscatedName = null;
	public String refactoredName = null;
	public ClassNode classNode = null;
	public boolean hooked=false;
	public ClassHook(String obf, String name){
		obfuscatedName = obf;
		refactoredName = name;
	}
	public void addField(String owner, String obf, String name, String desc, Object multi){
		fieldHooks.add(new FieldHook(owner, obf, name, desc, multi));
	}
	public void addMethod(String owner, String name, String rename, String desc, Object predicate){
		methodHooks.add(new MethodHook(owner, name, rename, desc, predicate));
	}
	public void setNode(ClassNode node){
		classNode=node;
	}
}
