package org.osrs.api.methods;

import java.awt.Point;
import java.awt.Rectangle;

import org.osrs.api.objects.RSInterface;
import org.osrs.api.objects.RSPlayer;
import org.osrs.api.objects.RSTile;
import org.osrs.api.objects.RSWidget;
import org.osrs.util.Data;

import org.osrs.api.wrappers.Client;

public class Calculations extends MethodDefinition{
	public static final int[] SIN_TABLE = new int[2048];
	public static final int[] COS_TABLE = new int[2048];
	public static Rectangle GAME_SCREEN_BOUNDS;
	private RSWidget resizeMode_HUD = null;
	private Rectangle fixedMode_Bounds = new Rectangle(4, 4, 512, 334);
	public Calculations(MethodContext context){
		super(context);
	}
	/**
	 * Calculates the distance between two given points.
	 * @param point1
	 * @param point2
	 * @return distance
	 */
	public double distanceBetween(Point point1, Point point2) {
		return distance(point1.x, point2.x, point1.y, point2.y);
	}
	/**
	 * Calculates the distance between two given Runescape Tiles.
	 * @param tile1
	 * @param tile2
	 * @return distance
	 */
	public double distanceBetween(RSTile tile1, RSTile tile2) {
		return distance(tile1.getX(), tile2.getX(), tile1.getY(), tile2.getY());
	}
	/**
	 * Calculates the distance between two 2D screen-space
	 * coordinates (X/Y axis).
	 * @param x1
	 * @param x2
	 * @param y1
	 * @param y2
	 * @return distance
	 */
	public double distance(double x1, double x2, double y1, double y2) {
		if(x1==-1 || x2==-1 || y1==-1 || y2==-1)
			return -1;
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}
	/**
	 * Calculates the distance to a given RSTile from the local players' location.
	 * @param tile
	 * @return distance
	 */
	public double distanceTo(RSTile tile) {
		return distanceTo(tile.getX(), tile.getY());
	}
	/**
	 * Calculates the distance to a given RSTile coordinate from the
	 * local players' location.
	 * @param tileX
	 * @param tileY
	 * @return distance
	 */
	public double distanceTo(double tileX, double tileY) {
		RSPlayer p = methods.players.getLocalPlayer();
		if (p != null) {
			RSTile tile = p.getLocation();
			double x2 = tile.getX();
			double y2 = tile.getY();
			return Math.sqrt((tileX - x2) * (tileX - x2) + (tileY - y2) * (tileY - y2));
		}
		return -1.0;
	}
	/**
	 * Calculates the minimap 2D screen-space location 
	 * to a given RSTile.
	 * @param tile
	 * @return screenLocation
	 */
	public Point locationToMinimap(RSTile tile) {
		RSPlayer p = methods.players.getLocalPlayer();
		if(p==null)return new Point(-1, -1);
		RSTile center = p.getLocation();
		double x = tile.getX() - ((Client)Data.clientInstance).mapBaseX();
		double y = tile.getY() - ((Client)Data.clientInstance).mapBaseY();
		int mmX = (int) (x * 4 + 2 - (((center.getX() - ((Client)Data.clientInstance).mapBaseX())+0.5D)*128) / 32);
		int mmY = (int) (y * 4 + 2 - (((center.getY() - ((Client)Data.clientInstance).mapBaseY())+0.5D)*128) / 32);
		return worldToMinimap(mmX, mmY);
	}
	/**
	 * Calculates the minimap 2D screen-space location 
	 * to a given RSTile.
	 * @param localX
	 * @param localY
	 * @return screenLocation
	 */
	public Point locationToMinimap(double localX, double localY) {
		RSPlayer p = methods.players.getLocalPlayer();
		if(p==null)return new Point(-1, -1);
		RSTile center = p.getLocation();
		int mmX = (int) (localX * 4 + 2 - (((center.getX() - ((Client)Data.clientInstance).mapBaseX())+0.5D)*128) / 32);
		int mmY = (int) (localY * 4 + 2 - (((center.getY() - ((Client)Data.clientInstance).mapBaseY())+0.5D)*128) / 32);
		return worldToMinimap(mmX, mmY);
	}
	/**
	 * Converts the given RSTile coordinates to a region offset and calculates a 
	 * 2D screen-space location.
	 * @param tileX
	 * @param tileY
	 * @param regionOffsetX
	 * @param regionOffsetY
	 * @param plane
	 * @return point
	 */
	public Point locationToScreen(double tileX, double tileY, double regionOffsetX, double regionOffsetY, int plane, int height) {
		return worldToScreen((tileX - ((Client)Data.clientInstance).mapBaseX() + regionOffsetX) * 128.0D, (tileY - ((Client)Data.clientInstance).mapBaseY() + regionOffsetY) * 128.0D, plane, height);
	}
	/**
	 * Converts the given RSTile to RSTile coordinates and calculates a 2D
	 * screen-space location. 
	 * @param Tile
	 * @param regionOffset
	 * @param regionOffset
	 * @param height
	 * @return point
	 */
	public Point locationToScreen(RSTile t, double dX, double dY, int height) {
		return locationToScreen(t.getX(), t.getY(), dX, dY, t.getPlane(), height);
	}
	/**
	 * Calculates a 2D screen-space location from given RSTile
	 * coordinates and height. Useful in situations such as an
	 * GroundItem on a table (an offset height in comparison
	 * to an GroundItem on the floor).
	 * @param tile.x
	 * @param tile.y
	 * @param height
	 * @return point
	 */
	public Point locationToScreen(double x, double y, int plane, int height) {
		return locationToScreen(x, y, 0.5D, 0.5D, plane, height);
	}
	/**
	 * Calculates a generic 2D screen-space location from given 
	 * RSTile coordinates.
	 * @param tile.x
	 * @param tile.y
	 * @return point
	 */
	public Point locationToScreen(int x, int y, int plane) {
		return locationToScreen(x, y, 0.5D, 0.5D, plane, 0);
	}
	/**
	 * Calculates a 2D screen-space location from a given
	 * RSTile and height. Useful in situations such as an
	 * GroundItem on a table (an offset height in comparison
	 * to an GroundItem on the floor).
	 * @param tile
	 * @param height
	 * @return point
	 */
	public Point locationToScreen(RSTile t, int h) {
		return locationToScreen(t.getX(), t.getY(), t.getPlane(), h);
	}
	/**
	 * Calculates a generic 2D screen-space location from a given RSTile.
	 * @param tile
	 * @return point
	 */
	public Point locationToScreen(RSTile t) {
		return locationToScreen(t.getX(), t.getY(), t.getPlane(), 0);
	}
	public Rectangle getGameScreenBounds(){
		return methods.game.client().canvas().getBounds();
	}
	public boolean onGameScreen(RSTile tile){
		Point center = methods.calculations.locationToScreen(tile);
		return onGameScreen(center);
	}
	/**
	 * Checks to see if a given point is within the game screen bounds.
	 * @param point
	 * @return boolean
	 */
	public boolean onGameScreen(Point p) {
		return methods.game.client().canvas().contains(p);
	}
	public boolean onMap(RSTile tile){
		if(distanceTo(tile)>20)
			return false;
		Point p = locationToMinimap(tile);
		Rectangle r = methods.minimap.getMinimapBounds();
		int baseX = (r.x+(r.width/2));
		int baseY = (r.y+(r.height/2));
		return !p.equals(new Point(-1, -1)) && distance(baseX, p.x, baseY, p.y)<75;
	}	
	public Rectangle getViewportBounds(){
		if(((Client)Data.clientInstance).resizeMode()){
			if(resizeMode_HUD==null){
				findHUDLoop:for(RSInterface i : methods.widgets.getAll()){
					if(i!=null){
						for(RSWidget ic : i.getChildren()){
							if(ic!=null){
								if(ic.boundsIndex()==4){
									resizeMode_HUD = ic;
									break findHUDLoop;
								}
							}
						}
					}
				}
			}
			if(resizeMode_HUD!=null)
				return resizeMode_HUD.getBounds();
		}
		return fixedMode_Bounds;
	}
	/**
	 * Checks to see if a given point is within the viewport bounds;
	 * the viewport is the window that displays the world/objects/players;
	 * so basically not the inventory/chatbox/minimap.
	 * @param point
	 * @return boolean
	 */
	public boolean onViewport(Point p){
		return getViewportBounds().contains(p);
	}
	public boolean onViewport(int screenX, int screenY){
		return onViewport(new Point(screenX, screenY));
	}
	public boolean onViewport(RSTile t){
		Point pt = this.locationToScreen(t);
		return onViewport(pt);
	}
	/**
	 * Converts a 3D region offset location (based on RSTile) to a
	 * 2D screen-space location (Point). 
	 * @param regionX
	 * @param regionY
	 * @param height
	 * @return point
	 */
	public Point worldToScreen(double X, double Y, int plane, int height) {
		try {
			if ((X < 128.0D) || (Y < 128.0D) || (X > 13056.0D) || (Y > 13056.0D)) {
				return new Point(-1, -1);
			}
			int Z = tileHeight(X, Y, plane) - height;
			X -= ((Client)Data.clientInstance).cameraX();
			Y -= ((Client)Data.clientInstance).cameraY();
			Z -= ((Client)Data.clientInstance).cameraZ();
			int curvexsin = Calculations.SIN_TABLE[((Client)Data.clientInstance).cameraYaw()];
			int curvexcos = Calculations.COS_TABLE[((Client)Data.clientInstance).cameraYaw()];
			int curveysin = Calculations.SIN_TABLE[((Client)Data.clientInstance).cameraPitch()];
			int curveycos = Calculations.COS_TABLE[((Client)Data.clientInstance).cameraPitch()];
			
			int calculation = curvexsin * (int) Y + (int) X * curvexcos >> 16;
			Y = -(curvexsin * (int) X) + (int) Y * curvexcos >> 16;
			X = calculation;
			calculation = curveycos * Z - curveysin * (int) Y >> 16;
			Y = curveysin * Z + (int) Y * curveycos >> 16;
			Z = calculation;
			if(Y==0)
				return new Point(-1, -1);
			int screenX = ((int) X * ((Client)Data.clientInstance).viewportScale()) / (int) Y + (((Client)Data.clientInstance).viewportWidth()/2);
			int screenY = (Z * ((Client)Data.clientInstance).viewportScale()) / (int) Y + (((Client)Data.clientInstance).viewportHeight()/2);
			Point p = new Point(screenX, screenY);
			return p;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Point(-1, -1);
	}
	/**
	 * Calculates an RSTiles' height.
	 * @param tile
	 * @return height
	 */
	public int tileHeight(RSTile t) {
		return tileHeight(t.getLocalX(), t.getLocalY(), t.getPlane());
	}
	public int tileHeight(int x, int y, int plane) {
		int[][][] heights = methods.game.tileHeights();
		if(heights!=null){
			if(x<0 || y<0 || x>=104 || y>=104)
				return 0;
			if(heights.length>=plane && heights[plane].length>=x && heights[plane][x].length>=y){
				int height = heights[plane][x][y];
				int z1 = height;
				int z2 = x + 1 < 104 ? heights[plane][x+1][y] : z1;
				int z3 = y + 1 < 104 ? heights[plane][x][y+1] : z1;
				int z4 = x + 1 < 104 && y + 1 < 104 ? heights[plane][x+1][y+1] : z1;
				return (z1+z2+z3+z4)/4;
			}
		}
		return 0;
	}
	/**
	 * Uses RSTile coordinates to pull an RSTiles' height
	 * from the clients stored data.
	 * @param tile.x
	 * @param tile.y
	 * @param plane
	 * @return height
	 */
	public int tileHeight(double x, double y, int plane) {
		return tileHeight(((int) x) >> 7, ((int) y) >> 7, plane);
	}
	/**
	 * Converts RSTile coordinates to a 2D screen-space location
	 * within the minimap.
	 * @param tile.x
	 * @param tile.y
	 * @return point
	 */
	public Point worldToMinimap(int regionOffsetX, int regionOffsetY) {
		int angle = ((Client)Data.clientInstance).mapRotation() & 0x7FF;
		int angleSine = Calculations.SIN_TABLE[angle];
		int angleCosine = Calculations.COS_TABLE[angle];
		int localPointX = (regionOffsetY * angleSine + regionOffsetX * angleCosine) >> 16;
		int localPointY = (regionOffsetY * angleCosine - regionOffsetX * angleSine) >> 16;
		Rectangle r = ((Client)Data.clientInstance).getMethodContext().minimap.getMinimapBounds();
		int baseX = (r.x+(r.width/2));
		int baseY = (r.y+(r.height/2));
		return new Point(baseX + localPointX, baseY - localPointY);
	}
	static {
		for (int i = 0; i < 2048; ++i) {
			SIN_TABLE[i] = (int) (65536.0D * Math.sin(i * 0.0030679615D));
			COS_TABLE[i] = (int) (65536.0D * Math.cos(i * 0.0030679615D));
		}
		GAME_SCREEN_BOUNDS = new Rectangle(0, 0, 756, 503);
	}
}