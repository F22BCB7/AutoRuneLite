package org.osrs.api.wrappers;

public interface ObjectDefinition extends EntityNode{
	public int[] objectModels();
	public int[] objectTypes();
	public boolean clipped();
	public int modelSizeX();
	public String name();
	public int modelHeight();
	public int modelSizeY();
	public int mapSceneID();
	public int offsetX();
	public int offsetZ();
	public int offsetY();
	public boolean obstructsGround();
	public boolean isHollow();
	public int supportItems();
	public int width();
	public int ambientSoundID();
	public int length();
	public int[] soundEffectIDs();
	public int clipType();
	public boolean blocksProjectile();
	public int mapIconID();
	public int mapDoorFlag();
	public int contouredGround();
	public boolean nonFlatShading();
	public boolean modelClipped();
	public int animationID();
	public int decorDisplacement();
	public int varpID();
	public int configID();
	public int[] childrenIDs();
	public int ambient();
	public int contrast();
	public short[] textureToFind();
	public short[] textureToReplace();
	public FixedSizeDeque params();
	public boolean isRotated();
	public short[] recolorToReplace();
	public short[] recolorToFind();
	public String[] actions();
	public int id();
	
	public ObjectDefinition invoke_getChildDefinition();
}