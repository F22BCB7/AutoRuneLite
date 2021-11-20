package org.osrs.api.wrappers;

public interface NPCDefinition extends EntityNode{
	public int widthScale();
	public String name();
	public int heightScale();
	public boolean isVisible();
	public int ambient();
	public int contrast();
	public int headIcon();
	public int rotation();
	public short[] recolorToFind();
	public short[] recolorToReplace();
	public short[] textureToFind();
	public short[] textureToReplace();
	public boolean isClickable();
	public int size();
	public int standingAnimation();
	public boolean isMoving();
	public int standingTurnedAnimation();
	public boolean boundaryDimension();
	public int runAnimation();
	public int walkingAnimation();
	public int rotate180Animation();
	public int rotate90RightAnimation();
	public int rotate90LeftAnimation();
	public int varpID();
	public int configID();
	public FixedSizeDeque params();
	public boolean isMinimapVisible();
	public int combatLevel();
	public int[] childrenIDs();
	public int[] npcModels();
	public int id();
	public String[] actions();
	public int[] additionalModels();
}