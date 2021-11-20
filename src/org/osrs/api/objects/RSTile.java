package org.osrs.api.objects;
import org.osrs.api.wrappers.Client;
import org.osrs.util.Data;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Comparator;

public class RSTile extends Interactable implements Comparable<RSTile> {
	public int x;
	public int y;
	public int plane;

	public RSTile(int x, int y) {
		methods = ((Client)Data.clientInstance).getMethodContext();
		this.x = x;
		this.y = y;
		this.plane = 0;
	}
	public RSTile(int x, int y, int plane) {
		methods = ((Client)Data.clientInstance).getMethodContext();
		this.x = x;
		this.y = y;
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

	public int getLocalX(){
		if(Data.clientInstance!=null){
			return this.x - ((Client)Data.clientInstance).mapBaseX();
		}
		return -1;
	}
	public int getLocalY(){
		if(Data.clientInstance!=null){
			return this.x - ((Client)Data.clientInstance).mapBaseX();
		}
		return -1;
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
	public Polygon getPolygon(){
		Polygon p = new Polygon();
		Point center = methods.calculations.locationToScreen(x, y, plane);
		Point n = methods.calculations.locationToScreen(x, y+1, plane);
		Point s = methods.calculations.locationToScreen(x, y-1, plane);
		Point e = methods.calculations.locationToScreen(x+1, y, plane);
		Point w = methods.calculations.locationToScreen(x-1, y, plane);
		p.addPoint((center.x+n.x+w.x)/3, (center.y+n.y+w.y)/3);
		p.addPoint((center.x+s.x+w.x)/3, (center.y+s.y+w.y)/3);
		p.addPoint((center.x+s.x+e.x)/3, (center.y+s.y+e.y)/3);
		p.addPoint((center.x+n.x+e.x)/3, (center.y+n.y+e.y)/3);
		return p;
	}
	@Override
	public Point getCenterPoint() {
		return methods.calculations.locationToScreen(x, y, plane);
	}
	@Override
	public Point getRandomPoint() {
		Polygon pl = getPolygon();
		Rectangle r = pl.getBounds();
		if(r.width<1 || r.height<1)
			return new Point(r.x, r.y);
		return new Point(r.x+(methods.calculations.random(r.width)), r.y+(methods.calculations.random(r.height)));
	}
	@Override
	public boolean isHovering() {
		Polygon pl = getPolygon();
		if(pl!=null){
			return pl.contains(methods.mouse.getLocation());
		}
		return false;
	}
}