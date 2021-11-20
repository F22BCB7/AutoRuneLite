package org.osrs.api.wrappers;

public interface TileModel{
	public boolean flat();
	public int shape();
	public int rotation();
	public int underlayID();
	public int overlayID();
	public int[] verticesX();
	public int[] verticesY();
	public int[] verticesZ();
	public int[] triangleIndicesX();
	public int[] triangleIndicesY();
	public int[] triangleIndicesZ();
	public int[] triangleColorA();
	public int[] triangleColorB();
	public int[] triangleColorC();
	public int[] triangleTextureIDs();
}