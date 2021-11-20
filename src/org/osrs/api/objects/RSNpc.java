package org.osrs.api.objects;

import java.lang.ref.SoftReference;

import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.NPCDefinition;
import org.osrs.api.wrappers.Npc;
import org.osrs.util.Data;

public class RSNpc extends RSActor{
	public SoftReference<Npc> accessor;
	public int indice=-1;
	public RSNpc(Npc p, int index){
		accessor = new SoftReference<Npc>(p);
		indice=index;
	}
	public Npc getAccessor(){
		return accessor.get();
	}
	public NPCDefinition getDefinition(){
		return getAccessor().definition();
	}
	public int getID(){
		Npc npc = getAccessor();
		if(npc!=null){
			NPCDefinition def = npc.definition();
			if(def!=null){
				return def.id();
			}
		}
		return -1;
	}
	public String getName(){
		Npc npc = getAccessor();
		if(npc!=null){
			NPCDefinition def = npc.definition();
			if(def!=null){
				return def.name();
			}
		}
		return "";
	}
	@Override
	public RSTile getLocation(){
		Npc p = accessor.get();
		if(p!=null){
			return new RSTile((((Client)Data.clientInstance).mapBaseX()+(p.x()>>7)), 
					(((Client)Data.clientInstance).mapBaseY()+(p.y()>>7)), 
					((Client)Data.clientInstance).currentPlane());
		}
		return new RSTile(-1, -1, -1);
	}
	public long calculateMenuUID() {
		return (0 & 127) << 0 | (0 & 127) << 7 | (1 & 3) << 14 | (indice & 4294967295L) << 17;
	}
}
