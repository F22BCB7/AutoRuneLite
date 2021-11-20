package org.osrs.api.wrappers;

public interface EntityNode extends Node{
	public long entityUID();
	public EntityNode next();
	public EntityNode previous();
}