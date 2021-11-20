package org.osrs.api.wrappers;

public interface Cache{
	public EntityNode cacheableNode();
	public int size();
	public int remaining();
	public FixedSizeDeque table();
	public DoublyNode nodes();
}