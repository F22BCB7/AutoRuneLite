package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@BClass(name="NodeList")
public class NodeList implements org.osrs.api.wrappers.NodeList{
	@BField
	public Node current;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Node current(){return current;}
	@BField
	public Node head;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Node head(){return head;}
}