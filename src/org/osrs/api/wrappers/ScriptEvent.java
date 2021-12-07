package org.osrs.api.wrappers;

public interface ScriptEvent extends Node{
	public int eventIndex();
	public int mouseY();
	public int typedKeyChar();
	public int type();
	public int op();
	public Widget target();
	public int mouseX();
	public java.lang.Object[] args();
	public String opbase();
	public Widget source();
	public int typedKeyCode();
	public boolean consumable();
}