package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BGetter;

@BClass(name="IntegerNode")
public class IntegerNode extends Node implements org.osrs.api.wrappers.IntegerNode{

	@BField
	public int value;
	@BGetter
	@Override
	public int value(){return value;}
}
