package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="CombatBar")
public class CombatBar extends Node implements org.osrs.api.wrappers.CombatBar{

	@BField
	public org.osrs.api.wrappers.NodeList combatDataList;
	@BGetter
	@Override
	public org.osrs.api.wrappers.NodeList combatDataList(){return combatDataList;}
	@BField
	public org.osrs.api.wrappers.CombatBarDefinition definition;
	@BGetter
	@Override
	public org.osrs.api.wrappers.CombatBarDefinition definition(){return definition;}
}