package org.osrs.api.methods;

import org.osrs.api.wrappers.FixedSizeDeque;
import org.osrs.api.wrappers.Node;

public class Nodes extends MethodDefinition{
	public Nodes(MethodContext context){
		super(context);
	}
	/**
	 * Finds a Node object within a given HashTable
	 * with the given UID.
	 * @param hashtable
	 * @param uid
	 * @return
	 */
	public Node lookup(FixedSizeDeque nc, long id) {
		try {
			if (nc == null || nc.buckets() == null || id < 0) {
				return null;
			}
			for(Node node : nc.buckets()){
				for(Node in = node.next();in!=null && !in.equals(node);in=in.next()){
					if(in.uid()==id)
						return in;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}