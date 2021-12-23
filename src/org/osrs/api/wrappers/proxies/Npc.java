package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BGetter;

@BClass(name="Npc")
public class Npc extends Actor implements org.osrs.api.wrappers.Npc{
	@BField
	public NPCDefinition definition;
	@BGetter
	@Override
	public org.osrs.api.wrappers.NPCDefinition definition(){return definition;}
}