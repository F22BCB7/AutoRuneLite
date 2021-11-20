package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="Node")
public class Node implements org.osrs.api.wrappers.Node{

	@BField
	public org.osrs.api.wrappers.Node previous;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Node previous(){return previous;}
	@BField
	public org.osrs.api.wrappers.Node next;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Node next(){return next;}
	@BField
	public long uid;
	@BGetter
	@Override
	public long uid(){return uid;}
}