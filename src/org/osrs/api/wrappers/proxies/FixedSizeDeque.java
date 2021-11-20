package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="FixedSizeDeque")
public class FixedSizeDeque implements org.osrs.api.wrappers.FixedSizeDeque{

	@BField
	public int size;
	@BGetter
	@Override
	public int size(){return size;}
	@BField
	public org.osrs.api.wrappers.Node[] buckets;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Node[] buckets(){return buckets;}
	@BField
	public org.osrs.api.wrappers.Node head;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Node head(){return head;}
	@BField
	public org.osrs.api.wrappers.Node tail;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Node tail(){return tail;}
	@BField
	public int index;
	@BGetter
	@Override
	public int index(){return index;}
}