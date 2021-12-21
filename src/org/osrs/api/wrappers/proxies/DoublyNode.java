package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="DoublyNode")
public class DoublyNode implements org.osrs.api.wrappers.DoublyNode{

	@BField
	public EntityNode head;
	@BGetter
	@Override
	public org.osrs.api.wrappers.EntityNode head(){return head;}
	@BField
	public EntityNode current;
	@BGetter
	@Override
	public org.osrs.api.wrappers.EntityNode current(){return current;}
}