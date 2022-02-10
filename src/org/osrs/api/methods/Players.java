package org.osrs.api.methods;

import java.util.ArrayList;

import org.osrs.api.constants.SkullIcon;
import org.osrs.api.objects.RSPlayer;
import org.osrs.api.wrappers.Player;
import org.osrs.api.wrappers.NameComposite;

public class Players extends MethodDefinition{
	public Players(MethodContext context){
		super(context);
	}
	public RSPlayer[] getAllPlayers(){
		Player[] ps =  methods.game.players();
		if(ps!=null){
			ArrayList<RSPlayer> ret = new ArrayList<RSPlayer>();
			for(int i=0;i<ps.length;++i){
				Player p = ps[i];
				if(p!=null)
					ret.add(new RSPlayer(p, i));
			}
			return ret.toArray(new RSPlayer[]{});
		}
		return new RSPlayer[]{};
	}
	public RSPlayer[] getSkulledPlayers(){
		Player[] ps =  methods.game.players();
		if(ps!=null){
			ArrayList<RSPlayer> ret = new ArrayList<RSPlayer>();
			for(int i=0;i<ps.length;++i){
				Player p = ps[i];
				if(p!=null && p.skullIcon()==SkullIcon.SKULL)
					ret.add(new RSPlayer(p, i));
			}
			return ret.toArray(new RSPlayer[]{});
		}
		return new RSPlayer[]{};
	}
	public RSPlayer getLocalPlayer(){
		Player p =  methods.game.localPlayer();
		if(p!=null){
			return new RSPlayer(p, p.playerID());
		}
		return null;
	}
	public RSPlayer get(String name) {
		Player[] players = methods.game.players();
		for(int i=0;i<players.length;++i) {
			Player p = players[i];
			if(p != null){
				NameComposite nc = p.nameComposite();
				if(nc!=null && nc.formattedName().equals(name))
					return new RSPlayer(p, i);
			}
		}
		return null;
	}
}
