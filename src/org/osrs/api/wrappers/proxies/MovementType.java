package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BGetter;

@BClass(name="MovementType")
public class MovementType implements org.osrs.api.wrappers.MovementType{
	@BField
	public int id;
	@BGetter
	@Override
	public int id(){return id;}
}
