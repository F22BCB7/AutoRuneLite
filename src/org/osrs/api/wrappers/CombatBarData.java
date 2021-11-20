package org.osrs.api.wrappers;

public interface CombatBarData extends Node{
	public int cycle();
	public int healthRatio();
	public int health();
	public int cycleLength();
}