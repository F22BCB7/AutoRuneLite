package org.osrs.api.wrappers;

public interface Node{
	public Node previous();
	public Node next();
	public long uid();
}