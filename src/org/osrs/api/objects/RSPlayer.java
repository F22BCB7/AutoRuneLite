package org.osrs.api.objects;

import java.lang.ref.SoftReference;

import org.osrs.api.constants.HeadIcon;
import org.osrs.api.constants.SkullIcon;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.Player;
import org.osrs.api.wrappers.PlayerDefinition;
import org.osrs.util.Data;

public class RSPlayer extends RSActor{
	public SoftReference<Player> accessor;
	public int indice=-1;
	public RSPlayer(Player p, int index){
		accessor = new SoftReference<Player>(p);
		indice=index;
	}
	public Player getAccessor(){
		return accessor.get();
	}
	public PlayerDefinition getDefinition(){
		Player p = getAccessor();
		if(p!=null)
			return p.definition();
		return null;
	}
	public String getName(){
		return getAccessor().nameComposite().name();
	}
	@Override
	public int getCombatLevel(){
		return getAccessor().combatLevel();
	}
	public int getPlane(){
		return getAccessor().plane();
	}
	public int getSkullIcon(){
		return getAccessor().skullIcon();
	}
	/**
	 * Wilderness skulled (PK'ing)
	 * @return
	 */
	public boolean isSkulled(){
		return getSkullIcon()==SkullIcon.SKULL;
	}
	/**
	 * See org.osrs.api.constants.HeadIcon
	 * @return
	 */
	public int getOverheadIcon(){
		return getAccessor().overheadIcon();
	}
	public boolean isProtectMelee(){
		return getOverheadIcon()==HeadIcon.PROTECT_FROM_MELEE;
	}
	public boolean isProtectMagic(){
		return getOverheadIcon()==HeadIcon.PROTECT_FROM_MAGIC;
	}
	public boolean isProtectRange(){
		return getOverheadIcon()==HeadIcon.PROTECT_FROM_RANGE;
	}
	@Override
	public RSTile getLocation(){
		Player p = accessor.get();
		if(p!=null){
			return new RSTile((((Client)Data.clientInstance).mapBaseX()+(p.x()>>7)), (((Client)Data.clientInstance).mapBaseY()+(p.y()>>7)), p.plane());
		}
		return new RSTile(-1, -1, -1);
	}
	public long calculateMenuUID() {
		return (0 & 127) << 0 | (0 & 127) << 7 | (0 & 3) << 14 | (indice & 4294967295L) << 17;
	}
}
