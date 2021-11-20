package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="PlayerDefinition")
public class PlayerDefinition implements org.osrs.api.wrappers.PlayerDefinition{

	@BField
	public int[] bodyPartColors;
	@BGetter
	@Override
	public int[] bodyPartColors(){return bodyPartColors;}
	@BField
	public boolean female;
	@BGetter
	@Override
	public boolean female(){return female;}
	@BField
	public int transformedNpcID;
	@BGetter
	@Override
	public int transformedNpcID(){return transformedNpcID;}
	@BField
	public long baseHashID;
	@BGetter
	@Override
	public long baseHashID(){return baseHashID;}
	@BField
	public long uniqueHashID;
	@BGetter
	@Override
	public long uniqueHashID(){return uniqueHashID;}
	@BField
	public int[] equipmentIDs;
	@BGetter
	@Override
	public int[] equipmentIDs(){return equipmentIDs;}
}