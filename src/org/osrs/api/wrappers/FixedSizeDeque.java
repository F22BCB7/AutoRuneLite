package org.osrs.api.wrappers;

public interface FixedSizeDeque{
	public int size();
	public Node[] buckets();
	public Node head();
	public Node tail();
	public int index();
}