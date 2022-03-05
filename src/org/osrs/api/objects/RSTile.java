package org.osrs.api.objects;
import org.osrs.api.objects.type.Modelled;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.Tile;
import org.osrs.util.Data;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Comparator;

public class RSTile extends Interactable implements Comparable<RSTile>, Modelled {
	public int x;
	public int y;
	public int plane;
	public int height=-1;
	private Tile tile;
	public RSTile(int x, int y) {
		methods = ((Client)Data.clientInstance).getMethodContext();
		this.x = x;
		if(this.x<104 && this.x>=0)
			this.x+=methods.game.mapBaseX();
		this.y = y;
		if(this.y<104 && this.y>=0)
			this.y+=methods.game.mapBaseY();
		this.plane = 0;
	}
	public RSTile(int x, int y, int plane) {
		methods = ((Client)Data.clientInstance).getMethodContext();
		this.x = x;
		if(this.x<104 && this.x>=0)
			this.x+=methods.game.mapBaseX();
		this.y = y;
		if(this.y<104 && this.y>=0)
			this.y+=methods.game.mapBaseY();
		this.plane = plane;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getPlane() {
		return plane;
	}
	public int getHeight(){
		int[][][] heights = methods.game.tileHeights();
		if(heights!=null){
			int localX = getLocalX();
			int localY = getLocalY();
			if(localX<0 || localY<0)
				return 0;
			if(heights.length>=plane && heights[plane].length>=localX && heights[plane][localX].length>=localY){
				height = heights[plane][getLocalX()][getLocalY()];
				return height;
			}
		}
		return 0;
	}
	public int getLocalX(){
		if(Data.clientInstance!=null){
			return this.x - ((Client)Data.clientInstance).mapBaseX();
		}
		return -1;
	}
	public int getLocalY(){
		if(Data.clientInstance!=null){
			return this.y - ((Client)Data.clientInstance).mapBaseY();
		}
		return -1;
	}
	public Tile getInternal(){
		int localX = getLocalX();
		int localY = getLocalY();
		if(localX>=0 && localX<=104 &&
				localY>=0 && localY<=104){
			Tile[][][] tiles = methods.game.region().tiles();
			if(tiles!=null && tiles.length>plane){
				Tile[][] map = tiles[plane];
				if(map!=null && map.length>localX){
					Tile[] row = map[localX];
					if(row!=null && row.length>localY){
						tile = row[localY];
						return tile;
					}
				}
			}
		}
		tile = null;
		return null;
	}
	@Override
	public int hashCode() {
		return plane << 30 | y << 15 | x;
	}
	private final Comparator<RSTile> COMPARATOR = Comparator
			.comparingInt(RSTile::getPlane)
			.thenComparingInt(RSTile::getY)
			.thenComparingInt(RSTile::getX);
	@Override
	public int compareTo(RSTile obj) {
		return COMPARATOR.compare(this, obj);
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RSTile) {
			RSTile t = (RSTile) obj;
			return (t.x == this.x && t.y == this.y && t.plane == this.plane);
		}
		return false;
	}
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + plane + ")";
	}
	public Point getCenterPoint(){
		Tile internal = getInternal();
		if(internal!=null){
			return internal.getCenterPoint();
		}
		return new Point(-1, -1);
	}
	public Polygon getBounds(){
		Tile internal = getInternal();
		if(internal!=null){
			return internal.getBounds();
		}
		return new Polygon();
	}
	@Override
	public RSModel getModel(){
		return null;//Modelling is done custom in proxy wrapper.
	}
	@Override
	public Polygon getPolygon(){
		return getBounds();
	}
	public boolean isVisible(){
		return methods.calculations.onViewport(this);
	}
	public boolean isOnMap(){
		return methods.calculations.onMap(this);
	}
	public Point[] projectVertices(){
		Tile internal = getInternal();
		if(internal!=null){
			return internal.projectVertices();
		}
		return new Point[]{};
	}
	public Polygon[] getWireframe(){
		Tile internal = getInternal();
		if(internal!=null){
			return internal.getWireframe();
		}
		return new Polygon[]{};
	}
	@Override
	public boolean isHovering() {
		Tile internal = getInternal();
		if(internal!=null){
			return internal.isHovering();
		}
		return false;
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
}