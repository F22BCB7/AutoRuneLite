package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;

@BClass(name="Deque")
public class Deque implements org.osrs.api.wrappers.Deque{
	@BField
	public org.osrs.api.wrappers.Node current;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Node current(){return current;}
	@BField
	public org.osrs.api.wrappers.Node head;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Node head(){return head;}
	
	@BMethod(name="popFront")
	public org.osrs.api.wrappers.Node _popFront(){return null;}
	@BDetour
	public org.osrs.api.wrappers.Node popFront(){
		org.osrs.api.wrappers.Node node = _popFront();
		if(node!=null && node instanceof org.osrs.api.wrappers.Tile){
			org.osrs.api.wrappers.Tile tile = (org.osrs.api.wrappers.Tile)node;
			if(tile.isHovering()){
				org.osrs.api.wrappers.Region region = Client.clientInstance.region();
				if(region!=null){
					region.setHoveringTile(tile);
				}
			}
		}
		return node;
	}
}