package org.osrs.api.wrappers;

public interface Model extends RenderableNode{
	public int[][] animationIndiceGroups();
	public byte[] texturedTriangleAlphas();
	public int[] indicesY();
	public int verticesCount();
	public int radius();
	public int[] verticesY();
	public int[] verticesZ();
	public int texturedIndiceCount();
	public int extremeZ();
	public int[] indicesZ();
	public int[] verticesX();
	public int[] indicesX();
	public byte[] triangleAlphas();
	public byte[] trianglePriorities();
	public int[] triangleHSLA();
	public short[] triangleTextures();
	public byte priority();
	public int indicesCount();
	public int[] texturedIndicesX();
	public int[] texturedIndicesY();
	public int[] texturedIndicesZ();
	public int[][] triangleAlphaIndiceGroups();
	public boolean hasMinimumBounds();
	public int boundsType();
	public int bottomY();
	public int diameter();
	public int centerX();
	public int centerY();
	public int centerZ();
	public int extremeX();
	public int extremeY();
	public int XYZMag();
	public int[] triangleHSLB();
	public int[] triangleHSLC();
}