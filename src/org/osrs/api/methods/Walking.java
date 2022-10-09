package org.osrs.api.methods;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

import org.osrs.api.objects.RSInterface;
import org.osrs.api.objects.RSPlayer;
import org.osrs.api.objects.RSTile;
import org.osrs.api.objects.RSWidget;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.Widget;

public class Walking extends MethodDefinition{
	public Walker walker = null;
	public Walking(MethodContext context){
		super(context);
	}
	/**
	 * Gets the current destination tile.
	 * @return currentDestination
	 */
	public RSTile getDestination(){
		if(((Client)methods.botInstance).destinationX()<=0 || ((Client)methods.botInstance).destinationX()<=0)
			return new RSTile(-1, -1, -1);
		return new RSTile(((Client)methods.botInstance).destinationX()+((Client)methods.botInstance).mapBaseX(), ((Client)methods.botInstance).destinationY()+((Client)methods.botInstance).mapBaseY(), ((Client)methods.botInstance).currentPlane());
	}
	/**
	 * Gets the current run energy
	 * @return currentEnergy
	 */
	public int getEnergy(){
		return ((Client)methods.botInstance).runEnergy();
	}
	public RSWidget getToggleRunButton(){
		for(RSInterface iface : methods.widgets.getAll()){
			if(iface!=null){
				RSWidget[] childs = iface.getChildren();
				if(childs!=null && childs.length>0){
					for(RSWidget child : childs){
						if(child!=null){
							Widget widget = child.getInternal();
							if(widget!=null && widget.isDisplayed()){
								String[] actions = widget.actions();
								if(actions!=null && actions.length>0 && actions[0].equals("Toggle Run"))
									return child;
							}
						}
					}
				}
			}
		}
		return null;
	}
	/**
	 * Checks to see if there is a destination flag currently
	 * @return true if its up
	 */
	public boolean hasDestination(){
		return !(getDestination().equals(new RSTile(-1, -1, -1)));
	}
	/**
	 * Reverses the order of a given RSTile path.
	 * @param path
	 * @return reversedPath
	 */
	public RSTile[] invertPath(RSTile[] path) {
		RSTile[] invertedPath = new RSTile[path.length];
		for (int i = 0; i < path.length; i++) {
			invertedPath[i] = path[path.length - 1 - i];
		}
		return invertedPath;
	}
	/**
	 * Checks to see if a path is local. This means
	 * if any one RSTile is visible on the minimap, it is 
	 * local.
	 * @param path
	 * @return true if path is local
	 */
	public boolean isPathLocal(RSTile[] path){
		for(RSTile tile : path){
			if(methods.calculations.onMap(tile))
				return true;
		}
		return false;
	}
	/**
	 * Checks to see if running is enabled.
	 * @return true if enabled
	 */
	public boolean isRunEnabled(){
		int[] settings = ((Client)methods.botInstance).varps();
		if(settings.length==0)
			return false;
		return settings[173]==1;
	}
	/**
	 * Checks to see if the local player is walking/running.
	 * @return true if moving
	 */
	public boolean isWalking(){
		RSPlayer p = methods.players.getLocalPlayer();
		if(p!=null)
			return p.getAccessor().currentPathIndex()>0;
		return false;
	}
	/**
	 * Turns running on if param is true, off if false
	 * @param on(true)/off(false)
	 */
	public void setRun(boolean input){
		if(isRunEnabled()==input)
			return;
		RSWidget w = getToggleRunButton();
		if(w!=null){
			if(((Client)methods.botInstance).resizeMode()){
				Rectangle r =  w.getBounds();
				r.y+=5;
				Point pt = new Point(r.x+new Random().nextInt(r.width), r.y+new Random().nextInt(r.height));
				methods.mouse.move(pt);
				try {
					Thread.sleep(new Random().nextInt(50)+50);
				} catch (@SuppressWarnings("unused") Exception e) {
				}
				if(r.contains(methods.mouse.getLocation()))
					methods.mouse.click();
			}
			else
				w.click();
			for(int i=0;i<20;++i){
				try {
					Thread.sleep(new Random().nextInt(40)+10);
				} catch (@SuppressWarnings("unused") Exception e) {
				}
				if(isRunEnabled())
					break;
			}
		}
	}
	/**
	 * Starts a walking thread that attempts to traverse
	 * a path of RSTiles.
	 * @param path
	 * @return true if destination reached
	 */
	public boolean walkPath(RSTile[] path) {
		if (!isWalking() || methods.calculations.distanceTo(getDestination()) <= 5){
			if(walker!=null && walker.isAlive())
				walker.stop();
			walker = new Walker();
			return walker.walkTo(path, true);
		}
		return false;
	}
	/**
	 * Attempts to walk to a tile; if the tile is visible within
	 * the viewport, it will interact there; if not visible in the viewport
	 * but on the minimap, will interact there. 
	 * NOTE : This will NOT generate a path of RSTiles to traverse
	 * @param tile
	 * @return true if we walked to the tile
	 */
	public boolean walkTile(RSTile tile){
		RSPlayer localPlayer = methods.players.getLocalPlayer();
		if(localPlayer!=null){
			if(methods.calculations.onViewport(tile)){
				return tile.click("Walk here");
			}
			if(methods.calculations.onMap(tile)){
				methods.region.clickMap(tile);
				return true;
			}
		}
		return false;
	}
	/**
	 * Specifies to walk to a tile via the minimap
	 * if it is visible there.
	 * @param tile
	 * @return true if we walked to the tile
	 */
	public boolean walkTileOnMap(RSTile tile){
		if(methods.calculations.onMap(tile)){
			methods.region.clickMap(tile);
			for(int i=0;i<20;++i){
				try {
					Thread.sleep(new Random().nextInt(40)+10);
				} catch (@SuppressWarnings("unused") InterruptedException e) {
				}
				if(isWalking())
					break;
			}
			return true;
		}
		return false;
	}
	/**
	 * Grabs the next visible tile we should walk to
	 * within a path.
	 * @param path
	 * @return nextTile
	 */
	public RSTile nextTile(RSTile[] path) {
		for (int i = path.length - 1; i >= 0; i--) {
			if (methods.calculations.onMap(path[i])) {
				return path[i];
			}
		}
		return new RSTile(-1,-1);
	}

	/**
	 * Used to draw the current walking data to
	 * the minimap.
	 * @param graphics
	 */
	public void drawMap(Graphics g) {
		if (walker != null && walker.isAlive()) {
			g.setColor(Color.RED);
			RSTile loc = methods.players.getLocalPlayer().getLocation();
			Point myTile = methods.calculations.locationToMinimap(loc);
			Point center = new Point(myTile.x + 2, myTile.y + 2);
			g.drawOval(center.x - 70, center.y - 70, 140, 140);
			RSTile[] path = walker.path;
			if (path == null) return;
			for (int i = 0; i < path.length; i++) {
				RSTile tile = path[i];
				Point p = methods.calculations.locationToMinimap(tile);
				if (p.x != -1 && p.y != -1) {
					g.setColor(Color.BLACK);
					g.fillRect(p.x + 1, p.y + 1, 3, 3);
					if (i > 0) {
						final Point p1 = methods.calculations.locationToMinimap(path[i - 1]);
						g.setColor(Color.ORANGE);
						if (p1.x != -1 && p1.y != -1)
							g.drawLine(p.x + 2, p.y + 2, p1.x + 2, p1.y + 2);
					}
				}
			}
			RSTile next = nextTile(path);
			Point tile = methods.calculations.locationToMinimap(next);
			g.setColor(Color.RED);
			if (tile.x != -1 && tile.y != -1) {
				g.fillRect(tile.x + 1, tile.y + 1, 3, 3);
			}
			g.setColor(Color.BLACK);
		}
	}
	

	public class Walker extends Thread{
		public RSTile tile = null;
		public boolean done = false;
		public int movementTimer = 10000;
		public int distanceTo = 6;
		public void run() {
			done=false;
			System.out.println("Started walker thread!");
			long timer = System.currentTimeMillis();
			RSPlayer pl = methods.players.getLocalPlayer();
			RSTile lastTile = pl.getLocation();
			int randomReturn = new Random().nextInt(3)+5;
			int randomRun = new Random().nextInt(50)+50;
			this.tile = path[path.length - 1];
			while (methods.calculations.distanceTo(tile) > distanceTo && !done){
				if(!methods.walking.isRunEnabled() && methods.walking.getEnergy()>randomRun){
					System.out.println("[Walker] Turning on run...");
					methods.walking.setRun(true);
					for(int i=0;i<20;++i){
						if(methods.walking.isRunEnabled())
							break;
						methods.sleep(new Random().nextInt(50)+150);
					}
				}
				if (!pl.isMoving() || !hasDestination() || methods.calculations.distanceTo(methods.walking.getDestination()) < randomReturn) {
					RSTile nextTile = nextTile(path);
					if(nextTile.equals(new RSTile(-1, -1))) {
						System.out.println("[Walker] Invalid next tile...");
						continue;
					}
					if (hasDestination() && methods.calculations.distanceBetween(getDestination(), nextTile) <= distanceTo) {
						System.out.println("[Walker] hasDestination and distanceBetween(dest, next)<=distTo...");
						continue;
					}
					System.out.println("[Walker] Walking next tile...");
					walkTileOnMap(nextTile);
					if(nextTile.equals(tile)) {
						System.out.println("[Walker] Tile walked is final destination of path...");
						done = true;
					}
					if(!waitToMove(new Random().nextInt(400)+800)){
						System.out.println("[Walker] Failed to walk to tile "+nextTile.toString());
						return;
					}
					randomReturn = new Random().nextInt(3)+5;
				}
				RSTile myLoc = pl.getLocation();
				if(!myLoc.equals(lastTile)){
					timer = System.currentTimeMillis();
					lastTile = myLoc;
				}
				if (System.currentTimeMillis() - timer > movementTimer) {
					System.out.println("[Walker] Timeout exit.");
					return;
				}
				methods.sleep(new Random().nextInt(20)+20);
			}
			if (methods.calculations.distanceTo(tile) <= distanceTo) {
				System.out.println("[Walker] Finished walking...");
				done = true;
			}
		}
		public RSTile[] path = null;
		public boolean waitToMove(int timeout){
			long start = System.currentTimeMillis();
			RSPlayer myPlayer = methods.players.getLocalPlayer();
			while (System.currentTimeMillis() - start < timeout) {
				if (myPlayer.isMoving())
					return true;
				methods.sleep(15);
			}
			return false;
		}
		public boolean walkTo(RSTile[] path, boolean waitUntilDest){
			if(!methods.walking.isPathLocal(path)){
				System.out.println("[Walker] Path not local. Not walking.");
				return false;
			}
			
			this.tile = path[path.length - 1];
			this.path = path;
			this.start();
			waitToMove(new Random().nextInt(400)+800);
			if (waitUntilDest) {
				while (this.isAlive()) {
					methods.sleep(new Random().nextInt(300)+300);
				}
				this.stop();
				return done;
			}
			this.stop();
			return true;
		}
	
	}
}
