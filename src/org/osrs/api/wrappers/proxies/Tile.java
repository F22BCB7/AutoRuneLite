package org.osrs.api.wrappers.proxies;

import org.osrs.api.methods.Calculations;
import org.osrs.api.methods.MethodContext;
import org.osrs.api.objects.RSModel;
import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import org.osrs.util.Data;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

@BClass(name="Tile")
public class Tile extends Node implements org.osrs.api.wrappers.Tile{	
	@BVar
	private int tileModelSize;	
	@BVar
	private MethodContext methods;	
	@BVar
	public int[] verticesX;	
	@BVar
	public int[] verticesY;	
	@BVar
	public int[] verticesZ;	
	@BVar
	public int[] trianglePoints1;	
	@BVar
	public int[] trianglePoints2;	
	@BVar
	public int[] trianglePoints3;	
	@BVar
	public int[] boundsPoints;	
	@BVar
	public int height;
	@BVar
	public boolean boundsCreated;
	/*
	 * So we need 4 'corners', since 3D projection uses triangles,
	 * we can split the square into 4 triangles
	 * *Pt0 is center point 
	 *     4    Tri1    3    
	 *        --------
	 *       |\\    //|
	 * Tri2  |  \\//  | Tri0
	 *       |  //\\  |
	 *       |//    \\|
	 *        --------
	 *     2    Tri3    1      
	 */
	@BFunction
	@Override
	public void createBoundsHeights(){
		if(methods==null)
			methods = Client.clientInstance.getMethodContext();
		tileModelSize = 64;	
		verticesX = new int[]{0, tileModelSize, -tileModelSize, tileModelSize, -tileModelSize};
		verticesY = new int[]{0, 0, 0, 0, 0};	
		verticesZ = new int[]{0, -tileModelSize, -tileModelSize, tileModelSize, tileModelSize};
		trianglePoints1 = new int[]{1, 3, 4, 2};	
		trianglePoints2 = new int[]{3, 4, 2, 1};
		trianglePoints3 = new int[]{0, 0, 0, 0};
		boundsPoints = new int[]{4, 3, 1, 2, 4};

		height = methods.calculations.tileHeight(x(), y(), renderPlane());
		int nHeight = methods.calculations.tileHeight(x(), y()+1, renderPlane());
		int wHeight = methods.calculations.tileHeight(x()-1, y(), renderPlane());
		int sHeight = methods.calculations.tileHeight(x(), y()-1, renderPlane());
		int eHeight = methods.calculations.tileHeight(x()+1, y(), renderPlane());
		int nwHeight = methods.calculations.tileHeight(x()-1, y()+1, renderPlane());
		int neHeight = methods.calculations.tileHeight(x()+1, y()+1, renderPlane());
		int swHeight = methods.calculations.tileHeight(x()-1, y()-1, renderPlane());
		int seHeight = methods.calculations.tileHeight(x()+1, y()-1, renderPlane());
		{
			int average = (nHeight+wHeight+nwHeight+height)/4;
			verticesY[4]=average-height;
		}
		{
			int average = (sHeight+wHeight+swHeight+height)/4;
			verticesY[2]=average-height;
		}
		{
			int average = (sHeight+eHeight+seHeight+height)/4;
			verticesY[1]=average-height;
		}
		{
			int average = (nHeight+eHeight+neHeight+height)/4;
			verticesY[3]=average-height;
		}
		boundsCreated=true;
	}
	@BFunction
	@Override
	public Point getCenterPoint(){
		if(!boundsCreated)
			createBoundsHeights();
		if(!boundsCreated || x()<0 || x()>=104 || y()<0 || y()>=104)
			return new Point(-1, -1);
		double worldX = ((x()+0.5)*128.0);
		double worldY = ((y()+0.5)*128.0);
		double _x = (worldX)-methods.game.cameraX();
		double _z = (worldY)-methods.game.cameraY();
		int _y = height-methods.game.cameraZ();

		int curvexsin = Calculations.SIN_TABLE[methods.game.cameraYaw()];
		int curvexcos = Calculations.COS_TABLE[methods.game.cameraYaw()];
		int curveysin = Calculations.SIN_TABLE[methods.game.cameraPitch()];
		int curveycos = Calculations.COS_TABLE[methods.game.cameraPitch()];

		int calculation = curvexsin * (int) _z + (int) _x * curvexcos >> 16;
		_z = -(curvexsin * (int) _x) + (int) _z * curvexcos >> 16;
		_x = calculation;
		calculation = curveycos * _y - curveysin * (int) _z >> 16;
		_z = curveysin * _y + (int) _z * curveycos >> 16;
		_y = calculation;
		if(_z<50)
			return new Point(-1, -1);
		int screenX = ((int) _x * methods.game.viewportScale()) / (int) _z + (methods.game.viewportWidth()/2);
		int screenY = (_y * methods.game.viewportScale()) / (int) _z + (methods.game.viewportHeight()/2);
		return new Point(screenX, screenY);
	}
	@BFunction
	@Override
	public Polygon getBounds(){
		if(!boundsCreated)
			createBoundsHeights();
		Polygon p = new Polygon();
		if(!boundsCreated || x()<0 || x()>=104 || y()<0 || y()>=104){
			return p;
		}
		double worldX = ((x()+0.5)*128.0);
		double worldY = ((y()+0.5)*128.0);
		for(int i=0;i<boundsPoints.length;++i){
			int vertexX = verticesX[boundsPoints[i]];
			int vertexY = verticesY[boundsPoints[i]];
			int vertexZ = verticesZ[boundsPoints[i]];
			int temp = vertexX;
			vertexX = (int) (temp * RSModel.ORIENTATION_COS_TABLE[0] + vertexZ * RSModel.ORIENTATION_SIN_TABLE[0]);
			vertexZ = (int) (-temp * RSModel.ORIENTATION_SIN_TABLE[0] + vertexZ * RSModel.ORIENTATION_COS_TABLE[0]);
			double _x = (worldX)-methods.game.cameraX()+vertexX;
			double _z = (worldY)-methods.game.cameraY()+vertexZ;
			int _y = height-methods.game.cameraZ()+vertexY;

			int curvexsin = Calculations.SIN_TABLE[methods.game.cameraYaw()];
			int curvexcos = Calculations.COS_TABLE[methods.game.cameraYaw()];
			int curveysin = Calculations.SIN_TABLE[methods.game.cameraPitch()];
			int curveycos = Calculations.COS_TABLE[methods.game.cameraPitch()];

			int calculation = curvexsin * (int) _z + (int) _x * curvexcos >> 16;
			_z = -(curvexsin * (int) _x) + (int) _z * curvexcos >> 16;
			_x = calculation;
			calculation = curveycos * _y - curveysin * (int) _z >> 16;
			_z = curveysin * _y + (int) _z * curveycos >> 16;
			_y = calculation;
			if(_z<50)
				continue;
			int screenX = ((int) _x * methods.game.viewportScale()) / (int) _z + (methods.game.viewportWidth()/2);
			int screenY = (_y * methods.game.viewportScale()) / (int) _z + (methods.game.viewportHeight()/2);
			if(screenX<0 || screenY<0 || screenX>methods.game.viewportWidth() || screenY>methods.game.viewportHeight())
				return new Polygon();
			p.addPoint(screenX, screenY);
		}
		
		return p;
	}
	/**
	 * Tried to do it via RSModel, but was getting unintended output results in both
	 * height calculation and vertice layout (although RSModel may not be correct, or the hooks).
	 * @return vertices
	 */
	@BFunction
	@Override
	public Point[] projectVertices(){
		if(!boundsCreated)
			createBoundsHeights();
		if(!boundsCreated || x()<0 || x()>=104 || y()<0 || y()>=104)
			return new Point[]{};
		double worldX = ((x()+0.5)*128.0);
		double worldY = ((y()+0.5)*128.0);
		ArrayList<Point> pts = new ArrayList<Point>();
		for(int i=0;i<verticesX.length;++i){
			int vertexX = verticesX[i];
			int vertexY = verticesY[i];
			int vertexZ = verticesZ[i];
			int temp = vertexX;
			vertexX = (int) (temp * RSModel.ORIENTATION_COS_TABLE[0] + vertexZ * RSModel.ORIENTATION_SIN_TABLE[0]);
			vertexZ = (int) (-temp * RSModel.ORIENTATION_SIN_TABLE[0] + vertexZ * RSModel.ORIENTATION_COS_TABLE[0]);
			double _x = (worldX)-methods.game.cameraX()+vertexX;
			double _z = (worldY)-methods.game.cameraY()+vertexZ;
			int _y = height-methods.game.cameraZ()+vertexY;

			int curvexsin = Calculations.SIN_TABLE[methods.game.cameraYaw()];
			int curvexcos = Calculations.COS_TABLE[methods.game.cameraYaw()];
			int curveysin = Calculations.SIN_TABLE[methods.game.cameraPitch()];
			int curveycos = Calculations.COS_TABLE[methods.game.cameraPitch()];

			int calculation = curvexsin * (int) _z + (int) _x * curvexcos >> 16;
			_z = -(curvexsin * (int) _x) + (int) _z * curvexcos >> 16;
			_x = calculation;
			calculation = curveycos * _y - curveysin * (int) _z >> 16;
			_z = curveysin * _y + (int) _z * curveycos >> 16;
			_y = calculation;
			if(_z<50)
				continue;
			int screenX = ((int) _x * methods.game.viewportScale()) / (int) _z + (methods.game.viewportWidth()/2);
			int screenY = (_y * methods.game.viewportScale()) / (int) _z + (methods.game.viewportHeight()/2);
			Point pt = new Point(screenX, screenY);
			pts.add(pt);
		}
		return pts.toArray(new Point[]{});
	}
	/**
	 * Returns a wireframe of triangles used to build the tile.
	 * @return
	 */
	@BFunction
	@Override
	public Polygon[] getWireframe(){
		ArrayList<Polygon> polygons = new ArrayList<Polygon>();
		Point[] screenPoints = projectVertices();
		int numTriangles = Math.min(trianglePoints1.length, Math.min(trianglePoints2.length, trianglePoints3.length));
		for (int i = 0; i < numTriangles; i++) {
			int index1 = trianglePoints1[i];
			int index2 = trianglePoints2[i];
			int index3 = trianglePoints3[i];
			if(index1>=screenPoints.length || index2>=screenPoints.length || index3>=screenPoints.length)
				continue;
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
			int gameScreenX = methods.game.viewportWidth();
			int gameScreenY = methods.game.viewportHeight();
			if(point1X>gameScreenX || point1Y>gameScreenY ||
					point2X>gameScreenX || point2Y>gameScreenY ||
					point3X>gameScreenX || point3Y>gameScreenY)
				continue;
			Polygon p = new Polygon();
			p.addPoint(point1X, point1Y);
			p.addPoint(point2X, point2Y);
			p.addPoint(point3X, point3Y);
			polygons.add(p);
		}
		return polygons.toArray(new Polygon[]{});
	}
	@BFunction
	@Override
	public boolean isHovering(){
		Polygon bounds = getBounds();
		if(methods!=null && bounds!=null)
			return bounds.contains(methods.mouse.getLocation());
		return false;
	}
	
	@BField
	public int physicalPlane;
	@BGetter
	@Override
	public int physicalPlane(){return physicalPlane;}
	@BField
	public int x;
	@BGetter
	@Override
	public int x(){return x;}
	@BField
	public int y;
	@BGetter
	@Override
	public int y(){return y;}
	@BField
	public int renderPlane;
	@BGetter
	@Override
	public int renderPlane(){return renderPlane;}
	@BField
	public TilePaint paint;
	@BGetter
	@Override
	public org.osrs.api.wrappers.TilePaint paint(){return paint;}
	@BField
	public ItemLayer itemLayer;
	@BGetter
	@Override
	public org.osrs.api.wrappers.ItemLayer itemLayer(){return itemLayer;}
	@BField
	public BoundaryObject boundary;
	@BGetter
	@Override
	public org.osrs.api.wrappers.BoundaryObject boundary(){return boundary;}
	@BField
	public WallDecoration wall;
	@BGetter
	@Override
	public org.osrs.api.wrappers.WallDecoration wall(){return wall;}
	@BField
	public boolean visible;
	@BGetter
	@Override
	public boolean visible(){return visible;}
	@BField
	public TileModel overlay;
	@BGetter
	@Override
	public org.osrs.api.wrappers.TileModel overlay(){return overlay;}
	@BField
	public int entityCount;
	@BGetter
	@Override
	public int entityCount(){return entityCount;}
	@BField
	public InteractableObject[] objects;
	@BGetter
	@Override
	public org.osrs.api.wrappers.InteractableObject[] objects(){
		if(objects!=null){
			int length = objects.length;
			org.osrs.api.wrappers.InteractableObject[] retObjs = new org.osrs.api.wrappers.InteractableObject[length];
			for(int i=0;i<length;++i){
				retObjs[i]=objects[i];
			}
			return retObjs;
		}
		return new org.osrs.api.wrappers.InteractableObject[]{};
	}
	@BField
	public int[] entityFlags;
	@BGetter
	@Override
	public int[] entityFlags(){return entityFlags;}
	@BField
	public int flags;
	@BGetter
	@Override
	public int flags(){return flags;}
	@BField
	public boolean draw;
	@BGetter
	@Override
	public boolean draw(){return draw;}
	@BField
	public boolean drawEntities;
	@BGetter
	@Override
	public boolean drawEntities(){return drawEntities;}
	@BField
	public int wallCullDirection;
	@BGetter
	@Override
	public int wallCullDirection(){return wallCullDirection;}
	@BField
	public int plane;
	@BGetter
	@Override
	public int plane(){return plane;}
	@BField
	public int wallDrawFlag;
	@BGetter
	@Override
	public int wallDrawFlag(){return wallDrawFlag;}
	@BField
	public int wallUncullDirection;
	@BGetter
	@Override
	public int wallUncullDirection(){return wallUncullDirection;}
	@BField
	public int wallCullOppositeDirection;
	@BGetter
	@Override
	public int wallCullOppositeDirection(){return wallCullOppositeDirection;}
	@BField
	public FloorDecoration floor;
	@BGetter
	@Override
	public org.osrs.api.wrappers.FloorDecoration floor(){return floor;}
	@BField
	public Tile bridge;
	@BGetter
	@Override
	public org.osrs.api.wrappers.Tile bridge(){return bridge;}
}