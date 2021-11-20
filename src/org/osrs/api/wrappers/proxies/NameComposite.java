package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="NameComposite")
public class NameComposite implements org.osrs.api.wrappers.NameComposite{

	@BField
	public String formattedName;
	@BGetter
	@Override
	public String formattedName(){return formattedName;}
	@BField
	public String name;
	@BGetter
	@Override
	public String name(){return name;}
}