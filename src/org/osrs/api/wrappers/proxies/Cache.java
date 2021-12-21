package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="Cache")
public class Cache implements org.osrs.api.wrappers.Cache{

	@BField
	public EntityNode cacheableNode;
	@BGetter
	@Override
	public org.osrs.api.wrappers.EntityNode cacheableNode(){return cacheableNode;}
	@BField
	public int size;
	@BGetter
	@Override
	public int size(){return size;}
	@BField
	public int remaining;
	@BGetter
	@Override
	public int remaining(){return remaining;}
	@BField
	public FixedSizeDeque table;
	@BGetter
	@Override
	public org.osrs.api.wrappers.FixedSizeDeque table(){return table;}
	@BField
	public DoublyNode nodes;
	@BGetter
	@Override
	public org.osrs.api.wrappers.DoublyNode nodes(){return nodes;}
}