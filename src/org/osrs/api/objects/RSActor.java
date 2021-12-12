package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import org.osrs.api.methods.Skills;
import org.osrs.api.wrappers.Actor;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.CombatBar;
import org.osrs.api.wrappers.CombatBarData;
import org.osrs.api.wrappers.Node;
import org.osrs.api.wrappers.NodeList;
import org.osrs.api.wrappers.Npc;
import org.osrs.api.wrappers.Player;
import org.osrs.util.Data;

public abstract class RSActor extends RSRenderable{
	public abstract Actor getAccessor();
	public RSActor(){
	}
	public int getAnimationID(){
		return getAccessor().animationID();
	}
	public boolean isMoving(){
		Actor a = getAccessor();
		if(a!=null){
			return a.currentPathIndex()>0;
		}
		return false;
	}
	public boolean isIdle(){
		return !isMoving() && getAnimationID()==-1;
	}
	public CombatBarData getCombatData(){
		if(Data.clientInstance!=null){
			int gameCycle = ((Client)Data.clientInstance).gameCycle();
			Actor a = getAccessor();
			if(a!=null){
				NodeList nl = a.combatInfoList();
				if(nl!=null){
					Node head = nl.head();
					if(head!=null){
						for(Node node=head.previous();node!=null && !node.equals(head);node=node.previous()){
							if(node instanceof CombatBar){
								CombatBar bar = (CombatBar)node;
								NodeList data = bar.combatDataList();
								if(data!=null){
									Node node2=data.head();
									if(node2!=null){
										for(Node node3=node2.previous();node3!=null && !node3.equals(node2);node3=node3.previous()){
											if(node3 instanceof CombatBarData){
												CombatBarData barData = (CombatBarData)node3;
												if(barData.cycle()<=gameCycle){
													return barData;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
	public int getHPPercent(){
		Actor p = getAccessor();
		if(p!=null){
			if(p instanceof Player){
				try{//Attempts to get realtime HP for local player
					//To ignore the hitbar update lag
					RSPlayer local = methods.players.getLocalPlayer();
					if(local!=null){
						if(local.getAccessor().equals(p)){//its local player
							int curr = methods.skills.getSkillLevel(Skills.CONSTITUTION_INDEX);
							int max = methods.skills.getSkillMaxLevel(Skills.CONSTITUTION_INDEX);
							if(max>=10 && curr>=0){
								DecimalFormat df = new DecimalFormat("#.##");
								double d = Double.parseDouble(df.format(((curr*1.0)/(max*1.0))));
								return (int)(d*100);
							}
						}
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			CombatBarData data = getCombatData();
			if(data!=null){
				return (int)(data.health()*(10.0/3));
			}
			return 100;
		}
		return -1;
	}
	public boolean isInteractingWith(RSActor actor){
		if(actor==null)
			return false;
		int id = getAccessor().interactingID();
		if(id==-1)
			return false;
		Actor actor2 = null;
		if(id>=32768){
			id-=32768;
			Player[] players = ((Client)Data.clientInstance).players();
			if(players.length>id){
				Player player = players[id];
				if(player!=null)
					actor2=player;
			}
		}
		else{
			Npc[] npcs = ((Client)Data.clientInstance).npcs();
			if(npcs.length>id){
				Npc npc = npcs[id];
				if(npc!=null)
					actor2=npc;
			}
		}
		if(actor2!=null){
			return actor2.equals(actor.getAccessor());
		}
		return false;
	}
	public RSPlayer getInteractingPlayer(){
		int id = getAccessor().interactingID();
		if(id >= 32768){
			id-=32768;
			Player[] players = ((Client)Data.clientInstance).players();
			if(players.length>id){
				Player player = players[id];
				if(player!=null)
					return new RSPlayer(player, id);
			}
		}
		return null;
	}
	public int getInteractingID(){
		return getAccessor().interactingID();
	}
	public RSNpc getInteractingNpc(){
		int id = getAccessor().interactingID();
		if(id==-1)
			return null;
		if(id < 32768){
			Npc[] npcs = ((Client)Data.clientInstance).npcs();
			if(npcs.length>id){
				Npc npc = npcs[id];
				if(npc!=null)
					return new RSNpc(npc, id);
			}
		}
		return null;
	}
	public RSTile getLocation(){
		Actor p = getAccessor();
		if(p!=null){
			return new RSTile((((Client)Data.clientInstance).mapBaseX()+(p.x()>>7)), (((Client)Data.clientInstance).mapBaseY()+(p.y()>>7)));
		}
		return new RSTile(-1, -1, -1);
	}
	public int getLocalX(){
		Actor p = getAccessor();
		if(p!=null){
			return p.x()>>7;
		}
		return -1;
	}
	public int getLocalY(){
		Actor p = getAccessor();
		if(p!=null){
			return p.y()>>7;
		}
		return -1;
	}
	public RSTile[] getPath(){
		Actor p = getAccessor();
		if(p!=null){
			int length = p.currentPathIndex();
			int[] xPath = p.pathX();
			int[] yPath = p.pathY();
			if(xPath.length<length || yPath.length<length)
				return new RSTile[]{};
			RSTile[] path = new RSTile[length];
			for(int i=0;i<length;++i){
				path[i]=new RSTile(xPath[i], yPath[i]);
			}
			return path;
		}
		return new RSTile[]{};
	}
	public RSModel getModel(){
		RSModel model = getAccessor().getCachedModel();
		if(model!=null)
			return model;
		return null;
	}
	public int getOrientation(){
		return (int)((getAccessor().orientation()/128)*22.5);
	}
	public Point getScreenLocation(){
		RSTile loc = getLocation();
		if(loc!=null){
			return loc.getCenterPoint();//TODO change to getCenterPoint on model once completed
		}
		return null;
	}
	public boolean isOnMap(){
		Point p = methods.calculations.locationToMinimap(getLocation());
		Rectangle bounds = methods.minimap.getMinimapBounds();
		if(!p.equals(new Point(-1, -1)) && (bounds.contains(p)))
			return true;
		return false;
	}
	public boolean isVisible(){
		Point pt = methods.calculations.locationToScreen(getLocation());
		return (pt.x!=-1 && pt.y!=-1);
	}
	public Point[] projectVertices(){
		Actor acc = getAccessor();
		if(acc!=null){
			RSModel model = getModel();
			if(model!=null){
				return model.projectVertices(getLocation().getPlane(), acc.x(), acc.y(), (int)((acc.orientation()/128)*22.5));
			}
		}
		return new Point[]{};
	}
	public Polygon[] getWireframe(){
		Actor acc = getAccessor();
		if(acc!=null){
			RSModel model = getModel();
			if(model!=null){
				return model.getWireframe(getLocation().getPlane(), acc.x(), acc.y(), (int)((acc.orientation()/128)*22.5));
			}
		}
		return new Polygon[]{};
	}
	@Override
	public Point getCenterPoint() {
		Actor acc = getAccessor();
		if(acc!=null){
			RSModel model = getModel();
			if(model!=null){
				Point center = model.getCenterPoint(getLocation());
				return center;
			}
		}
		return getScreenLocation();
	}
	@Override
	public Point getRandomPoint() {
		Polygon[] p = getWireframe();
		if(p.length<1)
			return new Point(-1, -1);
		Polygon pl = p[methods.calculations.random(p.length)];
		Rectangle r = pl.getBounds();
		return new Point(r.x+(methods.calculations.random(Math.abs(r.width)+1)), r.y+(methods.calculations.random(Math.abs(r.height)+1)));
	}
	@Override
	public boolean isHovering() {
		RSModel model = getModel();
		if(model!=null)
			return model.containsPoint(methods.mouse.getLocation(), getLocation().getPlane(), getAccessor().x(), getAccessor().y(), getOrientation());
		return false;
	}
	public abstract long calculateMenuUID();
	public int calculateMenuTag() {
		return (int)(calculateMenuUID() >>> 17 & 4294967295L);
	}
}
