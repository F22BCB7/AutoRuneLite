package org.osrs.api.wrappers;

public interface PlayerDefinition{
	public int[] bodyPartColors();
	public boolean female();
	public int transformedNpcID();
	public long baseHashID();
	public long uniqueHashID();
	public int[] equipmentIDs();
}