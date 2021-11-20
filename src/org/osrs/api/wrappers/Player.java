package org.osrs.api.wrappers;

import org.osrs.api.wrappers.proxies.Model;

public interface Player extends Actor{
	public int minX();
	public PlayerDefinition definition();
	public int skullIcon();
	public int overheadIcon();
	public NameComposite nameComposite();
	public int team();
	public int totalLevel();
	public int maxY();
	public int animationCycleEnd();
	public boolean updateMovement();
	public int regionZ();
	public String[] actions();
	public org.osrs.api.wrappers.Model model();
	public int animationCycleStart();
	public int minY();
	public int maxX();
	public int regionY();
	public int tileHeight();
	public int tileX();
	public boolean hidden();
	public int plane();
	public int playerID();
	public int regionX();
	public int combatLevel();
	public boolean lowDetail();
	public int tileY();
}