package org.osrs.api.wrappers;

public interface InteractableObject{
	public int cycle();
	public int priority();
	public long hash();
	public int renderInfo();
	public int plane();
	public int x();
	public int y();
	public int height();
	public RenderableNode model();
	public int orientation();
	public int relativeX();
	public int relativeY();
	public int offsetX();
	public int offsetY();
}