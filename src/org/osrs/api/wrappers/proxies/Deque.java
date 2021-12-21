package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;

@BClass(name="Deque")
public class Deque implements org.osrs.api.wrappers.Deque{
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
	
	@BMethod(name="popFront")
	public Node _popFront(){return null;}
	@BDetour
	public Node popFront(){
		Node node = _popFront();
		if(node!=null && node instanceof Tile){
			Tile tile = (Tile)node;
			if(tile.isHovering()){
				Region region = Client.region;
				if(region!=null){
					region.setHoveringTile(tile);
				}
			}
		}
		return node;
	}
}