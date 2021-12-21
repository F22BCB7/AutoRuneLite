package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="EntityNode")
public class EntityNode extends Node implements org.osrs.api.wrappers.EntityNode{

	@BField
	public long entityUID;
	@BGetter
	@Override
	public long entityUID(){return entityUID;}
	@BField
	public EntityNode next;
	@BGetter
	@Override
	public org.osrs.api.wrappers.EntityNode next(){return next;}
	@BField
	public EntityNode previous;
	@BGetter
	@Override
	public org.osrs.api.wrappers.EntityNode previous(){return previous;}
}