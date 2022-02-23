package org.osrs.api.objects;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import org.osrs.api.methods.Calculations;
import org.osrs.api.methods.MethodContext;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.Model;
import org.osrs.util.Data;

public class RSModel{
	private MethodContext methods;
	public Model accessor=null;
	public int[] verticesX=new int[]{};
	public int[] verticesY=new int[]{};
	public int[] verticesZ=new int[]{};
	public int[] trianglesX=new int[]{};
	public int[] trianglesY=new int[]{};
	public int[] trianglesZ=new int[]{};
	public int[] centerPoint=new int[]{};
	public boolean containsMouse=false;
	public RSModel(Object object){
		methods = ((Client)Data.clientInstance).getMethodContext();
		if(object instanceof Model){
			Model model = (Model)object;
			accessor=model;
			if(model!=null){
				verticesX = model.verticesX().clone();
				verticesY = model.verticesY().clone();
				verticesZ = model.verticesZ().clone(); 
				trianglesX = model.indicesX().clone();
				trianglesY = model.indicesY().clone();
				trianglesZ = model.indicesZ().clone();

		        int vertexX = 0;
				int vertexY = 0;
				int vertexZ = 0;
				for(int i : verticesX){
					vertexX=(vertexX+i)/2;
				}
				for(int i : verticesY){
					vertexY=(vertexY+i)/2;
				}
				for(int i : verticesZ){
					vertexZ=(vertexZ+i)/2;
				}
		        centerPoint = new int[]{vertexX, vertexY, vertexZ};
			}
		}
	}
	public RSModel(int[] xVertices, int[] yVertices, int[] zVertices, 
			int[] xTriangles, int[] yTriangles, int[]zTriangles){
		methods = ((Client)Data.clientInstance).getMethodContext();
		accessor=null;
		verticesX = xVertices;
		verticesY = yVertices;
		verticesZ = zVertices; 
		trianglesX = xTriangles;
		trianglesY = yTriangles;
		trianglesZ = zTriangles;

        int vertexX = 0;
		int vertexY = 0;
		int vertexZ = 0;
		for(int i : verticesX){
			vertexX=(vertexX+i)/2;
		}
		for(int i : verticesY){
			vertexY=(vertexY+i)/2;
		}
		for(int i : verticesZ){
			vertexZ=(vertexZ+i)/2;
		}
        centerPoint = new int[]{vertexX, vertexY, vertexZ};
	}
	public void updateModel(Object object){
		if(object instanceof Model){
			Model model = (Model)object;
			accessor=model;
			if(model!=null){
				verticesX = model.verticesX().clone();
				verticesY = model.verticesY().clone();
				verticesZ = model.verticesZ().clone(); 
				trianglesX = model.indicesX().clone();
				trianglesY = model.indicesY().clone();
				trianglesZ = model.indicesZ().clone();
	
		        int vertexX = 0;
				int vertexY = 0;
				int vertexZ = 0;
				for(int i : verticesX){
					vertexX=(vertexX+i)/2;
				}
				for(int i : verticesY){
					vertexY=(vertexY+i)/2;
				}
				for(int i : verticesZ){
					vertexZ=(vertexZ+i)/2;
				}
		        centerPoint = new int[]{vertexX, vertexY, vertexZ};
			}
		}
	}
	public void appendModel(RSModel model){
		if(model==null)
			return;
		int oldCount = Math.min(verticesX.length, Math.min(verticesY.length, verticesZ.length));
		int newCount = oldCount + (Math.min(model.verticesX.length, Math.min(model.verticesY.length, model.verticesZ.length)));
		int[] vx=new int[newCount];
		int[] vy=new int[newCount];
		int[] vz=new int[newCount];
		for(int i=0;i<newCount;++i){
			if(i<oldCount){
				vx[i]=verticesX[i];
				vy[i]=verticesY[i];
				vz[i]=verticesZ[i];
			}
			else{
				vx[i]=model.verticesX[i-oldCount];
				vy[i]=model.verticesY[i-oldCount];
				vz[i]=model.verticesZ[i-oldCount];
			}
		}
        int vertexX = 0;
		int vertexY = 0;
		int vertexZ = 0;
		for(int i : vx){
			vertexX=(vertexX+i)/2;
		}
		for(int i : vy){
			vertexY=(vertexY+i)/2;
		}
		for(int i : vz){
			vertexZ=(vertexZ+i)/2;
		}
        centerPoint = new int[]{vertexX, vertexY, vertexZ};
        verticesX = vx;
        verticesY = vy;
        verticesZ = vz;
		int oldCount2 = Math.min(trianglesX.length, Math.min(trianglesY.length, trianglesZ.length));
		int newCount2 = oldCount2 + (Math.min(model.trianglesX.length, Math.min(model.trianglesY.length, model.trianglesZ.length)));
		int[] tx=new int[newCount2];
		int[] ty=new int[newCount2];
		int[] tz=new int[newCount2];
		for(int i=0;i<newCount2;++i){
			if(i<oldCount2){
				tx[i]=trianglesX[i];
				ty[i]=trianglesY[i];
				tz[i]=trianglesZ[i];
			}
			else{
				tx[i]=model.trianglesX[i-oldCount2]+oldCount;
				ty[i]=model.trianglesY[i-oldCount2]+oldCount;
				tz[i]=model.trianglesZ[i-oldCount2]+oldCount;
			}
		}
		trianglesX = tx;
		trianglesY = ty;
		trianglesZ = tz;
	}
	public Point getCenterPoint(RSTile location){
		return getCenterPoint(location, 0);
	}
	public Point getCenterPoint(RSTile location, int orientation){
		return getCenterPoint(location, orientation, 0);
	}
	public Point getCenterPoint(RSTile location, int orientation, int heightOffset){
		if(Data.clientInstance!=null){
			double localX = location.getX()-((Client)Data.clientInstance).mapBaseX();
			double localY = location.getY()-((Client)Data.clientInstance).mapBaseY();
			double worldX = ((localX+0.5)*128.0);
			double worldY = ((localY+0.5)*128.0);
			return getCenterPoint(location.getPlane(), worldX, worldY, orientation, heightOffset);
		}
		return new Point(-1, -1);
	}

	public Point getCenterPoint(int plane, double worldX, double worldY){
		return getCenterPoint(plane, worldX, worldY, 0, 0);
	}
	public Point getCenterPoint(int plane, double worldX, double worldY, int orientation){
		return getCenterPoint(plane, worldX, worldY, orientation, 0);
	}
	public Point getCenterPoint(int plane, double worldX, double worldY, int orientation, int heightOffset){
		int vertexX = centerPoint[0];
		int vertexY = centerPoint[1];
		int vertexZ = centerPoint[2];
		if(orientation==256)
			orientation+=148;
		int temp = vertexX;
		vertexX = (int) (temp * RSModel.ORIENTATION_COS_TABLE[orientation] + vertexZ * RSModel.ORIENTATION_SIN_TABLE[orientation]);
		vertexZ = (int) (-temp * RSModel.ORIENTATION_SIN_TABLE[orientation] + vertexZ * RSModel.ORIENTATION_COS_TABLE[orientation]);
		double _x = (worldX)-((Client)Data.clientInstance).cameraX()+vertexX;
		double _z = (worldY)-((Client)Data.clientInstance).cameraY()+vertexZ;
		int _y = methods.calculations.tileHeight(worldX, worldY, plane)-((Client)Data.clientInstance).cameraZ()+vertexY-(heightOffset/2);

		int curvexsin = Calculations.SIN_TABLE[((Client)Data.clientInstance).cameraYaw()];
		int curvexcos = Calculations.COS_TABLE[((Client)Data.clientInstance).cameraYaw()];
		int curveysin = Calculations.SIN_TABLE[((Client)Data.clientInstance).cameraPitch()];
		int curveycos = Calculations.COS_TABLE[((Client)Data.clientInstance).cameraPitch()];

		int calculation = curvexsin * (int) _z + (int) _x * curvexcos >> 16;
		_z = -(curvexsin * (int) _x) + (int) _z * curvexcos >> 16;
		_x = calculation;
		calculation = curveycos * _y - curveysin * (int) _z >> 16;
		_z = curveysin * _y + (int) _z * curveycos >> 16;
		_y = calculation;
		
		int screenX = ((int) _x * ((Client)Data.clientInstance).viewportScale()) / (int) _z + (((Client)Data.clientInstance).viewportWidth()/2);
		int screenY = (_y * ((Client)Data.clientInstance).viewportScale()) / (int) _z + (((Client)Data.clientInstance).viewportHeight()/2);
		
		return new Point(screenX, screenY);
	
	}
	public int getVertexCount(){
		return Math.min(Math.min(verticesX.length, verticesY.length), verticesZ.length);
	}
	public Point[] projectVertices(RSTile location) {
		return projectVertices(location, 0);
	}  
	public Point[] projectVertices(RSTile location, int orientation) {
		return projectVertices(location, orientation, 0);
	}
	public Point[] projectVertices(RSTile location, int orientation, int heightOffset) {
		double localX = location.getX()-((Client)Data.clientInstance).mapBaseX();
		double localY = location.getY()-((Client)Data.clientInstance).mapBaseY();
		double _x = ((localX+0.5)*128.0);
		double _z = ((localY+0.5)*128.0);
		return projectVertices(location.getPlane(), _x, _z, orientation, heightOffset);
	}
	public Point[] projectVertices(int plane, double worldX, double worldY, int orientation){
		return projectVertices(plane, worldX, worldY, orientation, 0);
	}
	public Point[] projectVertices(int plane, double worldX, double worldY, int orientation, int heightOffset){
		ArrayList<Point> pts = new ArrayList<Point>();
		for(int i=0;i<verticesX.length;++i){
			int vertexX = verticesX[i];
			int vertexY = verticesY[i];
			int vertexZ = verticesZ[i];
			if(orientation==256)
				orientation+=148;
			int temp = vertexX;
			vertexX = (int) (temp * RSModel.ORIENTATION_COS_TABLE[orientation] + vertexZ * RSModel.ORIENTATION_SIN_TABLE[orientation]);
			vertexZ = (int) (-temp * RSModel.ORIENTATION_SIN_TABLE[orientation] + vertexZ * RSModel.ORIENTATION_COS_TABLE[orientation]);
			double _x = (worldX)-((Client)Data.clientInstance).cameraX()+vertexX;
			double _z = (worldY)-((Client)Data.clientInstance).cameraY()+vertexZ;
			int _y = methods.calculations.tileHeight((worldX), (worldY), plane)-((Client)Data.clientInstance).cameraZ()+vertexY-heightOffset;

			int curvexsin = Calculations.SIN_TABLE[((Client)Data.clientInstance).cameraYaw()];
			int curvexcos = Calculations.COS_TABLE[((Client)Data.clientInstance).cameraYaw()];
			int curveysin = Calculations.SIN_TABLE[((Client)Data.clientInstance).cameraPitch()];
			int curveycos = Calculations.COS_TABLE[((Client)Data.clientInstance).cameraPitch()];

			int calculation = curvexsin * (int) _z + (int) _x * curvexcos >> 16;
			_z = -(curvexsin * (int) _x) + (int) _z * curvexcos >> 16;
			_x = calculation;
			calculation = curveycos * _y - curveysin * (int) _z >> 16;
			_z = curveysin * _y + (int) _z * curveycos >> 16;
			_y = calculation;
			if(_z<50)
				continue;
			int screenX = ((int) _x * ((Client)Data.clientInstance).viewportScale()) / (int) _z + (((Client)Data.clientInstance).viewportWidth()/2);
			int screenY = (_y * ((Client)Data.clientInstance).viewportScale()) / (int) _z + (((Client)Data.clientInstance).viewportHeight()/2);
			//if(screenX<1 ||screenX>methods.game.getViewportWidth() ||
					//screenY<1 || screenY>methods.game.getViewportHeight())
				//continue;
			pts.add(new Point(screenX, screenY));
		}
		return pts.toArray(new Point[]{});
	}
	public boolean containsPoint(Point point, RSTile location){
		return containsPoint(point, location, 0, 0);
	}
	public boolean containsPoint(Point point, RSTile location, int orientation){
		return containsPoint(point, location, orientation, 0);
	}
	public boolean containsPoint(Point point, RSTile location, int orientation, int heightOffset){
		double localX = location.getX()-((Client)Data.clientInstance).mapBaseX();
		double localY = location.getY()-((Client)Data.clientInstance).mapBaseY();
		double _x = ((localX+0.5)*128.0);
		double _z = ((localY+0.5)*128.0);
		return containsPoint(point, location.getPlane(), _x, _z, orientation, heightOffset);
	}
	public boolean containsPoint(Point point, int plane, double worldX, double worldY){
		return containsPoint(point, plane, worldX, worldY, 0, 0);
	}
	public boolean containsPoint(Point point, int plane, double worldX, double worldY, int orientation){
		return containsPoint(point, plane, worldX, worldY, orientation, 0);
	}
	public boolean containsPoint(Point point, int plane, double worldX, double worldY, int orientation, int heightOffset){
		Point[] screenPoints = projectVertices(plane, worldX, worldY, orientation, heightOffset);
		int[] trix = trianglesX;
		int[] triy = trianglesY;
		int[] triz = trianglesZ;
		boolean ret = false;
		int numTriangles = Math.min(trix.length, Math.min(triy.length, triz.length));
		for (int i = 0; i < numTriangles; i++) {
			try{
				int index1 = trix[i];
				int index2 = triy[i];
				int index3 = triz[i];
				int point1X = screenPoints[index1].x;
				int point1Y = screenPoints[index1].y;
				int point2X = screenPoints[index2].x;
				int point2Y = screenPoints[index2].y;
				int point3X = screenPoints[index3].x;
				int point3Y = screenPoints[index3].y;
				if(isPointWithinTriangle(point.x, point.y, point1X, point1Y, point2X, point2Y, point3X, point3Y))
					ret=true;
			}
			catch(@SuppressWarnings("unused") Exception e){}
		}
		return ret;
	}
	public Polygon[] getWireframe(RSTile location){
		return getWireframe(location, 0);
	}
	public Polygon[] getWireframe(RSTile location, int orientation){
		return getWireframe(location, orientation, 0);
	}
	public Polygon[] getWireframe(RSTile location, int orientation, int heightOffset){
		double localX = location.getX()-((Client)Data.clientInstance).mapBaseX();
		double localY = location.getY()-((Client)Data.clientInstance).mapBaseY();
		double _x = ((localX+0.5)*128.0);
		double _z = ((localY+0.5)*128.0);
		return getWireframe(location.getPlane(), _x, _z, orientation, heightOffset);
	}
	public Polygon[] getWireframe(int plane, double worldX, double worldY, int orientation){
		return getWireframe(plane, worldX, worldY, orientation, 0);
	}
	public Polygon[] getWireframe(int plane, double worldX, double worldY, int orientation, int heightOffset){
		ArrayList<Polygon> polys = new ArrayList<Polygon>();
		Point[] screenPoints = projectVertices(plane, worldX, worldY, orientation, heightOffset);
		int[] trix = trianglesX;
		int[] triy = trianglesY;
		int[] triz = trianglesZ;
		boolean mouse=false;
		Point mouseLoc = methods.mouse.getLocation();
		int numTriangles = Math.min(trix.length, Math.min(triy.length, triz.length));
		for (int i = 0; i < numTriangles; i++) {
			try{
				int index1 = trix[i];
				int index2 = triy[i];
				int index3 = triz[i];
				int point1X = screenPoints[index1].x;
				int point1Y = screenPoints[index1].y;
				int point2X = screenPoints[index2].x;
				int point2Y = screenPoints[index2].y;
				int point3X = screenPoints[index3].x;
				int point3Y = screenPoints[index3].y;
				if(point1X<=-1 || point1Y<=-1 ||
						point2X<=-1 || point2Y<=-1 ||
						point3X<=-1 || point3Y<=-1)
					continue;
				int gameScreenX = ((Client)Data.clientInstance).viewportWidth();
				int gameScreenY = ((Client)Data.clientInstance).viewportHeight();
				if(point1X>gameScreenX || point1Y>gameScreenY ||
						point2X>gameScreenX || point2Y>gameScreenY ||
						point3X>gameScreenX || point3Y>gameScreenY)
					continue;
				if(isPointWithinTriangle(mouseLoc.x, mouseLoc.y, point1X, point1Y, point2X, point2Y, point3X, point3Y))
					mouse=true;
				Polygon p = new Polygon();
				p.addPoint(point1X, point1Y);
				p.addPoint(point2X, point2Y);
				p.addPoint(point3X, point3Y);
				polys.add(p);
			}
			catch(@SuppressWarnings("unused") Exception e){}
		}
		containsMouse=mouse;
		return polys.toArray(new Polygon[]{});
	}
    private final boolean isPointWithinTriangle(int pointX, int pointY, int x1, int y1, int x2, int y2, int x3, int y3){
        if(pointY < y1 && pointY < y2 && pointY < y3)
            return false;
        if(pointY > y1 && pointY > y2 && pointY > y3)
            return false;
        if(pointX < x1 && pointX < x2 && pointX < x3)
            return false;
        return pointX <= x1 || pointX <= x2 || pointX <= x3;
    }
	public Polygon getPolygon(RSTile location){
		return getPolygon(location, 0);
	}
	public Polygon getPolygon(RSTile location, int orientation){
		return getPolygon(location, orientation, 0);
	}
	public Polygon getPolygon(RSTile location, int orientation, int heightOffset){
		Point[] points = getPolygonPoints(projectVertices(location, orientation, heightOffset));
		Polygon p = new Polygon();
		for(int i = 0; i < points.length; i++) {
			p.addPoint(points[i].x, points[i].y);
		}
		return p;
	}
	/**
	 * Returns the convex hull of the points created from the list
	 * <code>points</code>. Note that the first and last point in the
	 * returned <code>List&lt;java.awt.Point&gt;</code> are the same
	 * point.
	 *
	 * @param points the list of points.
	 * @return the convex hull of the points created from the list
	 *         <code>points</code>.
	 * @throws IllegalArgumentException if all points are collinear or if there
	 *                                  are less than 3 unique points present.
	 */
	private static Point[] getPolygonPoints(Point[] points) throws IllegalArgumentException {

		Set<Point> unsorted = getSortedPointSet(points);

		if (unsorted == null) {
			return new Point[]{};
		}

		ArrayList<Point> sorted = new ArrayList<Point>(unsorted);

		if (sorted.size() < 3) {
			return new Point[]{};
		}

		if (areAllCollinear(sorted)) {
			return new Point[]{};
		}

		Stack<Point> stack = new Stack<Point>();
		stack.push(sorted.get(0));
		stack.push(sorted.get(1));

		for (int i = 2; i < sorted.size(); i++) {

			Point head = sorted.get(i);
			Point middle = stack.pop();
			Point tail = stack.peek();

			Turn turn = getTurn(tail, middle, head);

			switch (turn) {
			case COUNTER_CLOCKWISE:
				stack.push(middle);
				stack.push(head);
				break;
			case CLOCKWISE:
				i--;
				break;
			case COLLINEAR:
				stack.push(head);
				break;
			}
		}

		// close the hull
		stack.push(sorted.get(0));

		return stack.toArray(new Point[0]);
	}
	/**
	 * Returns a sorted set of points from the list <code>points</code>. The
	 * set of points are sorted in increasing order of the angle they and the
	 * lowest point <tt>P</tt> make with the x-axis. If tow (or more) points
	 * form the same angle towards <tt>P</tt>, the one closest to <tt>P</tt>
	 * comes first.
	 *
	 * @param points the list of points to sort.
	 * @return a sorted set of points from the list <code>points</code>.
	 */
	private static Set<Point> getSortedPointSet(Point[] points) {

		final Point lowest = getLowestPoint(points);

		if (lowest == null) {
			return null;
		}

		TreeSet<Point> set = new TreeSet<Point>(new Comparator<Point>() {
			public int compare(Point a, Point b) {

				if (a == b || a.equals(b)) {
					return 0;
				}

				// use longs to guard against int-underflow
				double thetaA = Math.atan2((long) a.y - lowest.y, (long) a.x - lowest.x);
				double thetaB = Math.atan2((long) b.y - lowest.y, (long) b.x - lowest.x);

				if (thetaA < thetaB) {
					return -1;
				} else if (thetaA > thetaB) {
					return 1;
				} else {
					// collinear with the 'lowest' point, let the point closest to it come first

					// use longs to guard against int-over/underflow
					double distanceA = Math.sqrt((((long) lowest.x - a.x) * ((long) lowest.x - a.x)) +
							(((long) lowest.y - a.y) * ((long) lowest.y - a.y)));
					double distanceB = Math.sqrt((((long) lowest.x - b.x) * ((long) lowest.x - b.x)) +
							(((long) lowest.y - b.y) * ((long) lowest.y - b.y)));

					if (distanceA < distanceB) {
						return -1;
					}
					return 1;
				}
			}
		});

		Collections.addAll(set, points);
		return set;
	}
	/**
	 * Returns the points with the lowest y coordinate. In case more than 1 such
	 * point exists, the one with the lowest x coordinate is returned.
	 *
	 * @param points the list of points to return the lowest point from.
	 * @return the points with the lowest y coordinate. In case more than
	 *         1 such point exists, the one with the lowest x coordinate
	 *         is returned.
	 */
	private static Point getLowestPoint(Point[] points) {

		if (points.length < 1) {
			return null;
		}

		Point lowest = points[0];

		for (Point temp : points) {

			if (temp.y < lowest.y || (temp.y == lowest.y && temp.x < lowest.x)) {
				lowest = temp;
			}
		}

		return lowest;
	}
	/**
	 * Returns true if all points in <code>points</code> are collinear.
	 * @param points
	 * @return true if all points in <code>points</code> are collinear.
	 */
	private static boolean areAllCollinear(java.util.List<Point> points) {

		if (points.size() < 2) {
			return true;
		}

		final Point a = points.get(0);
		final Point b = points.get(1);

		for (int i = 2; i < points.size(); i++) {

			Point c = points.get(i);

			if (getTurn(a, b, c) != Turn.COLLINEAR) {
				return false;
			}
		}

		return true;
	}
	/**
	 * Returns the GrahamScan#Turn formed by traversing through the
	 * ordered points <code>a</code>, <code>b</code> and <code>c</code>.
	 * More specifically, the cross product <tt>C</tt> between the
	 * 3 points (vectors) is calculated:
	 * <p/>
	 * <tt>(b.x-a.x * c.y-a.y) - (b.y-a.y * c.x-a.x)</tt>
	 * <p/>
	 * and if <tt>C</tt> is less than 0, the turn is CLOCKWISE, if
	 * <tt>C</tt> is more than 0, the turn is COUNTER_CLOCKWISE, else
	 * the three points are COLLINEAR.
	 *
	 * @param a the starting point.
	 * @param b the second point.
	 * @param c the end point.
	 * @return the GrahamScan#Turn formed by traversing through the
	 *         ordered points <code>a</code>, <code>b</code> and
	 *         <code>c</code>.
	 */
	private static Turn getTurn(Point a, Point b, Point c) {
		// use longs to guard against int-over/underflow
		long crossProduct = (((long) b.x - a.x) * ((long) c.y - a.y)) - (((long) b.y - a.y) * ((long) c.x - a.x));
		if (crossProduct > 0) {
			return Turn.COUNTER_CLOCKWISE;
		} else if (crossProduct < 0) {
			return Turn.CLOCKWISE;
		} else {
			return Turn.COLLINEAR;
		}
	}
	public static final double[] ORIENTATION_SIN_TABLE = new double[2048];
	public static final double[] ORIENTATION_COS_TABLE = new double[2048];    
	public static int[] ROTATION_2D_SINE = new int[2048];
	public static int[] ROTATION_2D_COSINE = new int[2048];
	/**
	 * An enum denoting a directional-turn between 3 points (vectors).
	 */
	private static enum Turn {
		CLOCKWISE, COUNTER_CLOCKWISE, COLLINEAR
	}
	static {
		final double d = 0.017453292519943295;
		for (int i = 0; i < 2048; i++) {
			ORIENTATION_SIN_TABLE[i] = Math.sin(i * d);
			ORIENTATION_COS_TABLE[i] = Math.cos(i * d);
		}	
		for(int i = 0; i < 2048; ++i) {
			ROTATION_2D_SINE[i] = (int)(65536.0D * Math.sin(i * 0.0030679615D));
			ROTATION_2D_COSINE[i] = (int)(65536.0D * Math.cos(i * 0.0030679615D));
		}
	}
}
