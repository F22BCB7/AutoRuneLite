package org.osrs.api.methods;

import java.util.ArrayList;

import org.osrs.api.objects.RSNpc;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.NPCDefinition;
import org.osrs.api.wrappers.Npc;

public class NPCs extends MethodDefinition{
	public NPCs(MethodContext context){
		super(context);
	}
	/**
	 * Grab an NPC from the loaded array by ID(s)
	 * @param id
	 * @return npc
	 */
	public RSNpc get(int id) {
		Npc[] npcs = ((Client)methods.botInstance).npcs();
		for(int i=0;i<npcs.length;++i) {
			Npc n = npcs[i];
			if(n != null){
				NPCDefinition def = n.definition();
				if(def!=null && def.id()==id)
					return new RSNpc(n, i);
			}
		}
		return null;
	}
	
	/**
	 * Grab an NPC from the loaded array by Name
	 * @param name
	 * @return npc
	 */
	public RSNpc get(String name) {
		Npc[] npcs = ((Client)methods.botInstance).npcs();
		for(int i=0;i<npcs.length;++i) {
			Npc n = npcs[i];
			if(n != null){
				NPCDefinition def = n.definition();
				if(def!=null && def.name().equals(name))
					return new RSNpc(n, i);
			}
		}
		return null;
	}
	/**
	 * Grabs a loaded NPC by a given id(s).
	 * This is only first in the array, not
	 * by any other means.
	 * @param id(s)
	 * @return npc
	 */
	public RSNpc get(int...ids) {
		Npc[] npcs = ((Client)methods.botInstance).npcs();
		for(int i=0;i<npcs.length;++i) {
			Npc n = npcs[i];
			if(n != null){
				NPCDefinition def = n.definition();
				if(def!=null){
					for(int id : ids) {
						if(def.id()==id)
							return new RSNpc(n, i);
					}
				}
			}
		}
		return null;
	}
	/**
	 * Grabs a loaded NPC by a given name(s)
	 * This is only first in the array, not
	 * by any other means.
	 * @param name(s)
	 * @return npc
	 */
	public RSNpc get(String...names) {
		Npc[] npcs = ((Client)methods.botInstance).npcs();
		for(int i=0;i<npcs.length;++i) {
			Npc n = npcs[i];
			if(n != null){
				NPCDefinition def = n.definition();
				if(def!=null){
					for(String name : names) {
						if(def.name().equals(name))
							return new RSNpc(n, i);
					}
				}
			}
		}
		return null;
	}
	/**
	 * Gets all loaded NPCs
	 * @return NPC Array
	 */
	public RSNpc[] getAll() {
		ArrayList<RSNpc> npc_list = new ArrayList<RSNpc>();
		Npc[] npcs = ((Client)methods.botInstance).npcs();
		for(int i=0;i<npcs.length;++i) {
			Npc n = npcs[i];
			if(n != null)
				npc_list.add(new RSNpc(n, i));
		}
		return npc_list.toArray(new RSNpc[]{});
	}
	/**
	 * Grabs all loaded NPCs by a given id
	 * @param id
	 * @return NPC[]
	 */
	public RSNpc[] getAllByID(int id) {
		ArrayList<RSNpc> npc_list = new ArrayList<RSNpc>();
		Npc[] npcs = ((Client)methods.botInstance).npcs();
		for(int i=0;i<npcs.length;++i) {
			Npc n = npcs[i];
			if(n != null){
				NPCDefinition def = n.definition();
				if(def!=null){
					if(def.id()==id){
						npc_list.add(new RSNpc(n, i));
					}
				}
			}
		}
		return npc_list.toArray(new RSNpc[]{});
	}
	/**
	 * Grabs all loaded NPCs by a given id(s)
	 * @param id(s)
	 * @return NPC[]
	 */
	public RSNpc[] getAllByID(int...ids) {
		ArrayList<RSNpc> npc_list = new ArrayList<RSNpc>();
		Npc[] npcs = ((Client)methods.botInstance).npcs();
		for(int i=0;i<npcs.length;++i) {
			Npc n = npcs[i];
			if(n != null){
				NPCDefinition def = n.definition();
				if(def!=null){
					for(int id : ids) {
						if(def.id()==id){
							npc_list.add(new RSNpc(n, i));
							break;
						}
					}
				}
			}
		}
		return npc_list.toArray(new RSNpc[]{});
	}
	/**
	 * Grabs all loaded NPCs by a given name
	 * @param name
	 * @return NPC[]
	 */
	public RSNpc[] getAllByName(String name) {
		ArrayList<RSNpc> npc_list = new ArrayList<RSNpc>();
		Npc[] npcs = ((Client)methods.botInstance).npcs();
		for(int i=0;i<npcs.length;++i) {
			Npc n = npcs[i];
			if(n != null){
				NPCDefinition def = n.definition();
				if(def!=null){
					if(def.name().equals(name)){
						npc_list.add(new RSNpc(n, i));
					}
				}
			}
		}
		return npc_list.toArray(new RSNpc[]{});
	}
	/**
	 * Grabs all loaded NPCs by a given name(s)
	 * @param name(s)
	 * @return NPC[]
	 */
	public RSNpc[] getAllByName(String...names) {
		ArrayList<RSNpc> npc_list = new ArrayList<RSNpc>();
		Npc[] npcs = ((Client)methods.botInstance).npcs();
		for(int i=0;i<npcs.length;++i) {
			Npc n = npcs[i];
			if(n != null){
				NPCDefinition def = n.definition();
				if(def!=null){
					for(String name : names) {
						if(def.name().equals(name)){
							npc_list.add(new RSNpc(n, i));
							break;
						}
					}
				}
			}
		}
		return npc_list.toArray(new RSNpc[]{});
	}
	/**
	 * Grabs the closest NPC with a given id
	 * @param id
	 * @return closest npc
	 */
	public RSNpc getNearestByID(int id) {
		RSNpc temp = null;
		double dist = Double.MAX_VALUE;
		for (RSNpc npc : getAllByID(id)) {
			if(npc==null)
				continue;
			double distance = methods.calculations.distanceTo(npc.getLocation());
			if (distance < dist) {
				dist = distance;
				temp = npc;
			}
		}
		return temp;
	}
	public RSNpc getNextNearestByID(int id) {
		RSNpc temp = null;
		double dist1 = Double.MAX_VALUE;
		double dist2 = Double.MAX_VALUE;
		for (RSNpc npc : getAllByID(id)) {
			if(npc==null)
				continue;
			double distance = methods.calculations.distanceTo(npc.getLocation());
			if (distance < dist1) {
				dist1 = distance;
			}
			else if(distance < dist2){
				dist2=distance;
				temp=npc;
			}
		}
		return temp;
	}
	/**
	 * Grabs the closest NPC with a given id(s)
	 * @param id(s)
	 * @return closest npc
	 */
	public RSNpc getNearestByID(int...ids) {
		RSNpc temp = null;
		double dist = Double.MAX_VALUE;
		for (RSNpc npc : getAllByID(ids)) {
			if(npc==null)
				continue;
			double distance = methods.calculations.distanceTo(npc.getLocation());
			if (distance < dist) {
				dist = distance;
				temp = npc;
			}
		}
		return temp;
	}
	public RSNpc getNextNearestByID(int...ids) {
		RSNpc temp = null;
		double dist1 = Double.MAX_VALUE;
		double dist2 = Double.MAX_VALUE;
		for (RSNpc npc : getAllByID(ids)) {
			if(npc==null)
				continue;
			double distance = methods.calculations.distanceTo(npc.getLocation());
			if (distance < dist1) {
				dist1 = distance;
			}
			else if(distance < dist2){
				dist2=distance;
				temp=npc;
			}
		}
		return temp;
	}
	/**
	 * Grabs the closest NPC with a given name
	 * @param name
	 * @return closest npc
	 */
	public RSNpc getNearestByName(String name) {
		RSNpc temp = null;
		double dist = Double.MAX_VALUE;
		for (RSNpc npc : getAllByName(name)) {
			if(npc==null)
				continue;
			double distance = methods.calculations.distanceTo(npc.getLocation());
			if (distance < dist) {
				dist = distance;
				temp = npc;
			}
		}
		return temp;
	}
	/**
	 * Grabs the closest NPC with a given name(s)
	 * @param name(s)
	 * @return closest npc
	 */
	public RSNpc getNearestByName(String...names) {
		RSNpc temp = null;
		double dist = Double.MAX_VALUE;
		for (RSNpc npc : getAllByName(names)) {
			if(npc==null)
				continue;
			double distance = methods.calculations.distanceTo(npc.getLocation());
			if (distance < dist) {
				dist = distance;
				temp = npc;
			}
		}
		return temp;
	}
	/**
	 * Gets all loaded NPCs not in combat
	 * @return NPC Array
	 */
	public RSNpc[] getAllNotInCombat() {
		ArrayList<RSNpc> npc_list = new ArrayList<RSNpc>();
		Npc[] npcs = ((Client)methods.botInstance).npcs();
		for(int i=0;i<npcs.length;++i) {
			Npc n = npcs[i];
			if(n != null){
				if(n.interactingID()!=-1)
					continue;
				RSNpc npc = new RSNpc(n, i);
				if(npc.getHPPercent()<100)
					continue;
				npc_list.add(npc);
			}
		}
		return npc_list.toArray(new RSNpc[]{});
	}
	/**
	 * Grabs all loaded NPCs not in combat by a given id
	 * @param id
	 * @return NPC[]
	 */
	public RSNpc[] getAllNotInCombatByID(int id) {
		ArrayList<RSNpc> npc_list = new ArrayList<RSNpc>();
		for(RSNpc n : getAllByID(id)) {
			if(n != null){
				NPCDefinition def = n.getDefinition();
				if(def==null)
					continue;
				if(n.getInteractingID()!=-1)
					continue;
				if(n.getHPPercent()<100)
					continue;
				npc_list.add(n);
			}
		}
		return npc_list.toArray(new RSNpc[]{});
	}
	/**
	 * Grabs all loaded NPCs not in combat by a given id(s)
	 * @param id(s)
	 * @return NPC[]
	 */
	public RSNpc[] getAllNotInCombatByID(int...ids) {
		ArrayList<RSNpc> npc_list = new ArrayList<RSNpc>();
		for(RSNpc n : getAllByID(ids)) {
			if(n != null){
				NPCDefinition def = n.getDefinition();
				if(def==null)
					continue;
				if(n.getInteractingID()!=-1)
					continue;
				if(n.getHPPercent()<100)
					continue;
				npc_list.add(n);
			}
		}
		return npc_list.toArray(new RSNpc[]{});
	}
	/**
	 * Grabs all loaded NPCs by a given name
	 * @param name
	 * @return NPC[]
	 */
	public RSNpc[] getAllNotInCombatByName(String name) {
		ArrayList<RSNpc> npc_list = new ArrayList<RSNpc>();
		for(RSNpc n : getAllByName(name)) {
			if(n != null){
				NPCDefinition def = n.getDefinition();
				if(def==null)
					continue;
				if(n.getInteractingID()!=-1)
					continue;
				if(n.getHPPercent()<100)
					continue;
				npc_list.add(n);
			}
		}
		return npc_list.toArray(new RSNpc[]{});
	}
	/**
	 * Grabs all loaded NPCs by a given name(s)
	 * @param name(s)
	 * @return NPC[]
	 */
	public RSNpc[] getAllNotInCombatByName(String...names) {
		ArrayList<RSNpc> npc_list = new ArrayList<RSNpc>();
		for(RSNpc n : getAllByName(names)) {
			if(n != null){
				NPCDefinition def = n.getDefinition();
				if(def==null)
					continue;
				if(n.getInteractingID()!=-1)
					continue;
				if(n.getHPPercent()<100)
					continue;
				npc_list.add(n);
			}
		}
		return npc_list.toArray(new RSNpc[]{});
	}
	/**
	 * Grabs the closest NPC not in combat with a given id
	 * @param id
	 * @return closest npc
	 */
	public RSNpc getNearestNotInCombatByID(int id) {
		RSNpc temp = null;
		double dist = Double.MAX_VALUE;
		for (RSNpc npc : getAllNotInCombatByID(id)) {
			if(npc==null)
				continue;
			double distance = methods.calculations.distanceTo(npc.getLocation());
			if (distance < dist) {
				dist = distance;
				temp = npc;
			}
		}
		if(temp != null)
			return temp;
		return null;
	}
	/**
	 * Grabs the closest NPC not in combat with a given id(s)
	 * @param id(s)
	 * @return closest npc
	 */
	public RSNpc getNearestNotInCombatByID(int...ids) {
		RSNpc temp = null;
		double dist = Double.MAX_VALUE;
		for (RSNpc npc : getAllNotInCombatByID(ids)) {
			if(npc==null)
				continue;
			double distance = methods.calculations.distanceTo(npc.getLocation());
			if (distance < dist) {
				dist = distance;
				temp = npc;
			}
		}
		if(temp != null)
			return temp;
		return null;
	}
	/**
	 * Grabs the closest NPC with a given name
	 * @param name
	 * @return closest npc
	 */
	public RSNpc getNearestNotInCombatByName(String name) {
		RSNpc temp = null;
		double dist = Double.MAX_VALUE;
		for (RSNpc npc : getAllNotInCombatByName(name)) {
			if(npc==null)
				continue;
			double distance = methods.calculations.distanceTo(npc.getLocation());
			if (distance < dist) {
				dist = distance;
				temp = npc;
			}
		}
		if(temp != null)
			return temp;
		return null;
	}
	/**
	 * Grabs the closest NPC with a given name(s)
	 * @param name(s)
	 * @return closest npc
	 */
	public RSNpc getNearestNotInCombatByName(String...names) {
		RSNpc temp = null;
		double dist = Double.MAX_VALUE;
		for (RSNpc npc : getAllNotInCombatByName(names)) {
			if(npc==null)
				continue;
			double distance = methods.calculations.distanceTo(npc.getLocation());
			if (distance < dist) {
				dist = distance;
				temp = npc;
			}
		}
		if(temp != null)
			return temp;
		return null;
	}
}
