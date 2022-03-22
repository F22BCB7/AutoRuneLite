package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import org.osrs.api.wrappers.Actor;
import org.osrs.api.wrappers.Npc;
import org.osrs.api.wrappers.Player;
import org.osrs.api.wrappers.Projectile;

public class RSProjectile extends RSRenderable{
	private Projectile accessor=null;
	public RSProjectile(Projectile projectile){
		accessor = projectile;
	}
	public int currentFrameIndex(){
		if(accessor!=null)
			return accessor.currentFrameIndex();
		return -1;
	}
	public int frameProgress(){
		if(accessor!=null)
			return accessor.frameProgress();
		return -1;
	}
	public int id(){
		if(accessor!=null)
			return accessor.id();
		return -1;
	}
	public int plane(){
		if(accessor!=null)
			return accessor.plane();
		return -1;
	}
	public int startRegionX(){
		if(accessor!=null)
			return accessor.startRegionX();
		return -1;
	}
	public int startRegionY(){
		if(accessor!=null)
			return accessor.startRegionY();
		return -1;
	}
	public int height(){
		if(accessor!=null)
			return accessor.height();
		return -1;
	}
	public int startCycle(){
		if(accessor!=null)
			return accessor.startCycle();
		return -1;
	}
	public int endCycle(){
		if(accessor!=null)
			return accessor.endCycle();
		return -1;
	}
	public int slope(){
		if(accessor!=null)
			return accessor.slope();
		return -1;
	}
	public int startHeight(){
		if(accessor!=null)
			return accessor.startHeight();
		return -1;
	}
	public int endHeight(){
		if(accessor!=null)
			return accessor.endHeight();
		return -1;
	}
	public boolean isLaunched(){
		if(accessor!=null)
			return accessor.isLaunched();
		return false;
	}
	public double worldX(){
		if(accessor!=null)
			return accessor.x();
		return -1;
	}
	public double velocityX(){
		if(accessor!=null)
			return accessor.velocityX();
		return -1;
	}
	public double worldY(){
		if(accessor!=null)
			return accessor.y();
		return -1;
	}
	public double velocityY(){
		if(accessor!=null)
			return accessor.velocityY();
		return -1;
	}
	public double worldZ(){
		if(accessor!=null)
			return accessor.z();
		return -1;
	}
	public double velocityZ(){
		if(accessor!=null)
			return accessor.velocityZ();
		return -1;
	}
	public double heightOffset(){
		if(accessor!=null)
			return accessor.heightOffset();
		return -1;
	}
	public double scalar(){
		if(accessor!=null)
			return accessor.scalar();
		return -1;
	}
	public int rotationX(){
		if(accessor!=null)
			return accessor.rotationX();
		return -1;
	}
	public int rotationY(){
		if(accessor!=null)
			return accessor.rotationY();
		return -1;
	}
	public int getLocalX(){
		return (int)worldX()>>7;
	}
	public int getLocalY(){
		return (int)worldY()>>7;
	}
	public int getX(){
		return (int)worldX()>>7 + methods.game.mapBaseX();
	}
	public int getY(){
		return (int)worldY()>>7 + methods.game.mapBaseY();
	}
	public RSTile getLocation(){
		return new RSTile((int)worldX()>>7 + methods.game.mapBaseX(), (int)worldY()>>7 + methods.game.mapBaseY(), plane()); 
	}
	public boolean isInteractingWith(RSActor actor){
		if(actor==null)
			return false;
		int id = getInteractingID();
		if(id==-1)
			return false;
		Actor actor2 = null;
		if(id>=32768){
			id-=32768;
			Player[] players = methods.game.players();
			if(players.length>id){
				Player player = players[id];
				if(player!=null)
					actor2=player;
			}
		}
		else{
			Npc[] npcs = methods.game.npcs();
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
		int id = getInteractingID();
		if(id >= 32768){
			id-=32768;
			Player[] players = methods.game.players();
			if(players.length>id){
				Player player = players[id];
				if(player!=null)
					return new RSPlayer(player, id);
			}
		}
		return null;
	}
	public int getInteractingID(){
		if(accessor==null)
			return -1;
		return accessor.interactingID();
	}
	public RSNpc getInteractingNpc(){
		int id = getInteractingID();
		if(id==-1)
			return null;
		if(id < 32768){
			Npc[] npcs = methods.game.npcs();
			if(npcs.length>id){
				Npc npc = npcs[id];
				if(npc!=null)
					return new RSNpc(npc, id);
			}
		}
		return null;
	}
	@Override
	public Projectile getAccessor() {
		return accessor;
	}
	public RSModel getModel(){
		if(accessor==null)
			return null;
		return accessor.getCachedModel();
	}
	public boolean isOnMap(){
		return methods.calculations.onMap(getLocation());
	}
	public boolean isVisible(){
		return methods.calculations.onViewport(getLocation());
	}
	public Point[] projectVertices(){
		RSModel model = getModel();
		if(model!=null){
			return model.projectVertices(plane(), worldX(), worldY(), 0, (methods.calculations.tileHeight((worldX()), (worldY()), plane())-(int)worldZ()));
		}
		return new Point[]{};
	}
	public Polygon[] getWireframe(){
		RSModel model = getModel();
		if(model!=null){
			return model.getWireframe(plane(), worldX(), worldY(), 0, (methods.calculations.tileHeight((worldX()), (worldY()), plane())-(int)worldZ()));
		}
		return new Polygon[]{};
	}
	public Polygon getPolygon(){
		RSModel model = getModel();
		if(model!=null){
			return model.getPolygon(plane(), worldX(), worldY(), 0, (methods.calculations.tileHeight((worldX()), (worldY()), plane())-(int)worldZ()));
		}
		return new Polygon();
	}
	@Override
	public Point getCenterPoint() {
		RSModel model = getModel();
		if(model!=null)
			return model.getCenterPoint(plane(), worldX(), worldY(), 0, (methods.calculations.tileHeight((worldX()), (worldY()), plane())-(int)worldZ()));
		return new Point(-1, -1);
	}

	@Override
	public Point getRandomPoint() {
		Polygon[] p = getWireframe();
		if(p.length<1)
			return new Point(-1, -1);
		Polygon pl = p[methods.calculations.random(p.length)];
		Rectangle r = pl.getBounds();
		if(r.width<1 || r.height<1)
			return new Point(r.x, r.y);
		return new Point(r.x+(methods.calculations.random(r.width)), r.y+(methods.calculations.random(r.height)));
	}

	@Override
	public boolean isHovering() {
		RSModel model = getModel();
		if(model!=null)
			return model.containsPoint(methods.mouse.getLocation(), plane(), worldX(), worldY(), 0, (methods.calculations.tileHeight((worldX()), (worldY()), plane())-(int)worldZ()));
		return false;
	}

}
