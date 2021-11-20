package org.osrs.api.methods;

import org.osrs.api.wrappers.Client;

public class Varps extends MethodDefinition{
	public Varps(MethodContext context){
		super(context);
	}
	/**
	 * Grabs a varp from a given index
	 * @param index
	 * @return setting
	 */
	public int get(int index){
		int[] all = getAll();
		if(all.length>index)
			return all[index];
		return -1;
	}
	/**
	 * Grabs a varp from a given index and applies
	 * a given bit-shift mask.
	 * @param index
	 * @param mask
	 * @return setting
	 */
	public int get(int index, int mask){
		return get(index) & mask;
	}
	/**
	 * Grabs a varp from a given index and applies
	 * a given bit-shift and a given bit-shift mask.
	 * @param index
	 * @param shift
	 * @param mask
	 * @return setting
	 */
	public int get(int index, int shift, int mask){
		return (get(index)>>shift)&mask;
	}
	/**
	 * Grabs all the client varps
	 * @return
	 */
	public int[] getAll(){
		return ((Client)methods.botInstance).varps();
	}
}
