package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="Npc")
public class Npc extends Actor implements org.osrs.api.wrappers.Npc{
	@BField
	public NPCDefinition definition;
	@BGetter
	@Override
	public org.osrs.api.wrappers.NPCDefinition definition(){return definition;}
}