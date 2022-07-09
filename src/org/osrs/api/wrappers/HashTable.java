package org.osrs.api.wrappers;

import org.osrs.api.wrappers.Node;

public interface HashTable{
	public int size();
	public Node[] buckets();
	public Node head();
	public Node tail();
	public int index();
	public Node invoke_get(long a);
}