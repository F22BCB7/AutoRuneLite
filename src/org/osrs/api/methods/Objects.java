package org.osrs.api.methods;

import org.osrs.api.objects.GameObject;
import org.osrs.api.objects.RSTile;
import org.osrs.api.wrappers.Actor;
import org.osrs.api.wrappers.AnimableObject;
import org.osrs.api.wrappers.BoundaryObject;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.Deque;
import org.osrs.api.wrappers.FloorDecoration;
import org.osrs.api.wrappers.InteractableObject;
import org.osrs.api.wrappers.Node;
import org.osrs.api.wrappers.WallDecoration;
import org.osrs.api.wrappers.Region;
import org.osrs.api.wrappers.Tile;

import java.util.ArrayList;

public class Objects extends MethodDefinition{
	public Objects(MethodContext context){
		super(context);
	}
	public GameObject[] getAllObjects(){
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		int plane=0, x=0, y=0;
		Region region = methods.game.region();
		for(Tile[][] map : region.tiles()){
			for(Tile[] row : map){
				for(Tile tile : row){
					if(tile!=null){
						for(InteractableObject io : tile.objects()){
							if(io==null)
								continue;
							if(x==(io.x()/128) && y==(io.y()/128)){
								objects.add(new GameObject(io, new RSTile(x, y, plane)));
							}
						}
						BoundaryObject bo = tile.boundary();
						if(bo!=null)
							objects.add(new GameObject(bo, new RSTile(x, y, plane)));
						FloorDecoration fo = tile.floor();
						if(fo!=null)
							objects.add(new GameObject(fo, new RSTile(x, y, plane)));
						WallDecoration wo = tile.wall();
						if(wo!=null)
							objects.add(new GameObject(wo, new RSTile(x, y, plane)));
					}
					y++;
				}
				y=0;
				x++;
			}
			x=0;
			plane++;
		}
		Deque animableObjects = methods.game.animableObjectDeque();
		if(animableObjects!=null){
			Node head = animableObjects.head();
			for(Node node=head.previous();node!=null && node.hashCode()!=head.hashCode();node=node.previous()){
				if(node instanceof AnimableObject){
					AnimableObject obj = (AnimableObject)node;
					objects.add(new GameObject(obj, new RSTile((obj.x()>>7)+methods.game.mapBaseX(), (obj.y()>>7)+methods.game.mapBaseY(), obj.plane())));
				}
			}
		}
		return objects.toArray(new GameObject[]{});
	}
	public GameObject[] getAllAt(RSTile t){
		return getAllAt(t.getX(), t.getY(), t.getPlane());
	}
	public GameObject[] getAllAt(int x, int y){
		return getAllAt(x, y, methods.game.currentPlane());
	}
	public GameObject[] getAllAt(int x, int y, int plane){
        ArrayList<GameObject> objects = new ArrayList<GameObject>();
    	int localX = x -methods.game.mapBaseX();
    	int localY = y -methods.game.mapBaseY();
    	if(localX<0 || localX>=104 || localY<0 || localY>=104)
    		return new GameObject[]{};
    	Region region =methods.game.region();
    	if(region!=null){
    		Tile tile = region.tiles()[plane][localX][localY];
    		if(tile!=null){
    			try {
	    			for(InteractableObject io : tile.objects()){
						if(io==null)
							continue;
	    				objects.add(new GameObject(io, new RSTile(x, y, plane)));
	    			}
	    			BoundaryObject bo = tile.boundary();
	    			if(bo!=null)
	    				objects.add(new GameObject(bo, new RSTile(x, y, plane)));
	    			FloorDecoration fo = tile.floor();
	    			if(fo!=null)
	    				objects.add(new GameObject(fo, new RSTile(x, y, plane)));
	    			WallDecoration wo = tile.wall();
	    			if(wo!=null)
	    				objects.add(new GameObject(wo, new RSTile(x, y, plane)));
    			}
    			catch(Exception e) {
    				e.printStackTrace();
    			}
    		}
    	}
		Deque animableObjects = methods.game.animableObjectDeque();
		if(animableObjects!=null){
			Node head = animableObjects.head();
			for(Node node=head.previous();node!=null && node.hashCode()!=head.hashCode();node=node.previous()){
				if(node instanceof AnimableObject){
					AnimableObject obj = (AnimableObject)node;
					if(obj.x()>>7==localX && obj.y()>>7==localY && obj.plane()==plane)
						objects.add(new GameObject(obj, new RSTile((obj.x()>>7)+methods.game.mapBaseX(), (obj.y()>>7)+methods.game.mapBaseY(), obj.plane())));
				}
			}
		}
        return objects.toArray(new GameObject[]{});
	}

	public GameObject getPrimaryAt(RSTile t) {
		int plane = t.getPlane();
		int localX = t.getLocalX();
		int localY = t.getLocalY();
    	Region region =methods.game.region();
		Tile tile = region.tiles()[plane][localX][localY];
		if (tile == null) return null;
		for (InteractableObject io : tile.objects()) {
			if (io != null && !(io.model() instanceof Actor)) {
				return new GameObject(io, t);
			}
		}
		return null;
	}
	/**
	 * Returns top object on tile
	 * InteractableObject > BoundaryObject > FloorDecoration > WallDecoration
	 * @param tile
	 * @return object
	 */
	public GameObject getObjectAt(RSTile t){
		return getObjectAt(t.getX(), t.getY(), t.getPlane());
	}
	public GameObject getObjectAt(int x, int y){
		return getObjectAt(x, y,methods.game.currentPlane());
	}
	public GameObject getObjectAt(int x, int y, int plane){
    	int localX = x -methods.game.mapBaseX();
    	int localY = y -methods.game.mapBaseY();
    	Region region =methods.game.region();
    	if(region!=null && localX<104 && localY<104 && localX>=0 && localY>=0){
    		Tile tile = region.tiles()[plane][localX][localY];
    		if(tile!=null){
    			for(InteractableObject io : tile.objects()){
    				if(io!=null)
    					return new GameObject(io, new RSTile(x, y, plane));
    			}
    			BoundaryObject bo = tile.boundary();
    			if(bo!=null)
    				return  new GameObject(bo, new RSTile(x, y, plane));
    			FloorDecoration fo = tile.floor();
    			if(fo!=null)
    				return new GameObject(fo, new RSTile(x, y, plane));
    			WallDecoration wo = tile.wall();
    			if(wo!=null)
    				return new GameObject(wo, new RSTile(x, y, plane));
    		}
    	}
		Deque animableObjects = methods.game.animableObjectDeque();
		if(animableObjects!=null){
			Node head = animableObjects.head();
			for(Node node=head.previous();node!=null && node.hashCode()!=head.hashCode();node=node.previous()){
				if(node instanceof AnimableObject){
					AnimableObject obj = (AnimableObject)node;
					if(obj.x()>>7==localX && obj.y()>>7==localY && obj.plane()==plane)
						return (new GameObject(obj, new RSTile((obj.x()>>7)+methods.game.mapBaseX(), (obj.y()>>7)+methods.game.mapBaseY(), obj.plane())));
				}
			}
		}
        return null;
	}
	public GameObject[] getObjectsByID(long id){
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		int plane=0, x=0, y=0;
		Region region = methods.game.region();
		for(Tile[][] map : region.tiles()){
			for(Tile[] row : map){
				for(Tile tile : row){
					if(tile!=null){
						for(InteractableObject io : tile.objects()){
							GameObject go = new GameObject(io, new RSTile(x+methods.game.mapBaseX(), y+methods.game.mapBaseY(), plane));
							if(id==go.getID())
								objects.add(go);
						}
						BoundaryObject bo = tile.boundary();
						if(bo!=null){
							GameObject go = new GameObject(bo, new RSTile(x+methods.game.mapBaseX(), y+methods.game.mapBaseY(), plane));
							if(id==go.getID())
								objects.add(go);
						}
						FloorDecoration fo = tile.floor();
						if(fo!=null){
							GameObject go = new GameObject(fo, new RSTile(x+methods.game.mapBaseX(), y+methods.game.mapBaseY(), plane));
							if(id==go.getID())
								objects.add(go);
						}
						WallDecoration wo = tile.wall();
						if(wo!=null){
							GameObject go = new GameObject(wo, new RSTile(x+methods.game.mapBaseX(), y+methods.game.mapBaseY(), plane));
							if(id==go.getID())
								objects.add(go);
						}
					}
					y++;
				}
				y=0;
				x++;
			}
			x=0;
			plane++;
		}
		Deque animableObjects = methods.game.animableObjectDeque();
		if(animableObjects!=null){
			Node head = animableObjects.head();
			for(Node node=head.previous();node!=null && node.hashCode()!=head.hashCode();node=node.previous()){
				if(node instanceof AnimableObject){
					AnimableObject obj = (AnimableObject)node;
					if(obj.id()==id)
						objects.add(new GameObject(obj, new RSTile((obj.x()>>7)+methods.game.mapBaseX(), (obj.y()>>7)+methods.game.mapBaseY(), obj.plane())));
				}
			}
		}
		return objects.toArray(new GameObject[]{});
	}
	public GameObject[] getObjectsByID(long...ids){
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		int plane=0, x=0, y=0;
		Region region = methods.game.region();
		for(Tile[][] map : region.tiles()){
			for(Tile[] row : map){
				for(Tile tile : row){
					if(tile!=null){
						for(InteractableObject io : tile.objects()){
							GameObject go = new GameObject(io, new RSTile(x+methods.game.mapBaseX(), y+methods.game.mapBaseY(), plane));
							for(long id : ids){
								if(id==go.getID()){
									objects.add(go);
									break;
								}
							}
						}
						BoundaryObject bo = tile.boundary();
						if(bo!=null){
							GameObject go = new GameObject(bo, new RSTile(x+methods.game.mapBaseX(), y+methods.game.mapBaseY(), plane));
							for(long id : ids){
								if(id==go.getID()){
									objects.add(go);
									break;
								}
							}
						}
						FloorDecoration fo = tile.floor();
						if(fo!=null){
							GameObject go = new GameObject(fo, new RSTile(x+methods.game.mapBaseX(), y+methods.game.mapBaseY(), plane));
							for(long id : ids){
								if(id==go.getID()){
									objects.add(go);
									break;
								}
							}
						}
						WallDecoration wo = tile.wall();
						if(wo!=null){
							GameObject go = new GameObject(wo, new RSTile(x+methods.game.mapBaseX(), y+methods.game.mapBaseY(), plane));
							for(long id : ids){
								if(id==go.getID()){
									objects.add(go);
									break;
								}
							}
						}
					}
					y++;
				}
				y=0;
				x++;
			}
			x=0;
			plane++;
		}
		Deque animableObjects = methods.game.animableObjectDeque();
		if(animableObjects!=null){
			Node head = animableObjects.head();
			for(Node node=head.previous();node!=null && node.hashCode()!=head.hashCode();node=node.previous()){
				if(node instanceof AnimableObject){
					AnimableObject obj = (AnimableObject)node;
					for(long id : ids){
						if(obj.id()==id){
							objects.add(new GameObject(obj, new RSTile((obj.x()>>7)+methods.game.mapBaseX(), (obj.y()>>7)+methods.game.mapBaseY(), obj.plane())));
							break;
						}
					}
				}
			}
		}
		return objects.toArray(new GameObject[]{});
	}
	public GameObject getNearestByID(long id){
		GameObject closestObj = null;
		double closestDistance = 999;
		RSTile closestTile = null;
		int baseX=methods.game.mapBaseX(), baseY=methods.game.mapBaseY();
		int plane =methods.game.currentPlane();
		for(int localX=0;localX<104;++localX){
			for(int localY=0;localY<104;++localY){
				int x = localX+baseX;
				int y = localY+baseY;
				RSTile tile = new RSTile(x, y, plane);
				double distance = methods.calculations.distanceTo(tile);
				if(closestTile!=null){
					if(distance>=closestDistance)
						continue;
				}
				for(GameObject go : getAllAt(tile)){
					if(go.getID()==id){
						closestObj=go;
						closestTile=tile;
						closestDistance = distance;
					}
				}
			}
		}
		Deque animableObjects = methods.game.animableObjectDeque();
		if(animableObjects!=null){
			Node head = animableObjects.head();
			for(Node node=head.previous();node!=null && node.hashCode()!=head.hashCode();node=node.previous()){
				if(node instanceof AnimableObject){
					AnimableObject obj = (AnimableObject)node;
					if(obj.id()==id){
						GameObject go = (new GameObject(obj, new RSTile((obj.x()>>7)+methods.game.mapBaseX(), (obj.y()>>7)+methods.game.mapBaseY(), obj.plane())));
						RSTile tile = go.getLocation();
						double distance = methods.calculations.distanceTo(tile);
						if(closestTile!=null){
							if(distance>=closestDistance)
								continue;
						}
						closestObj=go;
						closestTile=tile;
						closestDistance = distance;
					}
				}
			}
		}
		return closestObj;
	}
	public GameObject getNearestByID(long...ids){
		GameObject closestObj = null;
		double closestDistance = 999;
		RSTile closestTile = null;
		int baseX=methods.game.mapBaseX(), baseY=methods.game.mapBaseY();
		int plane =methods.game.currentPlane();
		for(int localX=0;localX<104;++localX){
			for(int localY=0;localY<104;++localY){
				int x = localX+baseX;
				int y = localY+baseY;
				RSTile tile = new RSTile(x, y, plane);
				double distance = methods.calculations.distanceTo(tile);
				if(closestTile!=null){
					if(distance>=closestDistance)
						continue;
				}
				for(GameObject go : getAllAt(tile)){
					for(long id : ids){
						if(go.getID()==id){
							closestObj=go;
							closestTile=tile;
							closestDistance = distance;
							break;
						}
					}
				}
			}
		}
		Deque animableObjects = methods.game.animableObjectDeque();
		if(animableObjects!=null){
			Node head = animableObjects.head();
			for(Node node=head.previous();node!=null && node.hashCode()!=head.hashCode();node=node.previous()){
				if(node instanceof AnimableObject){
					AnimableObject obj = (AnimableObject)node;
					for(long id : ids){
						if(obj.id()==id){
							GameObject go = (new GameObject(obj, new RSTile((obj.x()>>7)+methods.game.mapBaseX(), (obj.y()>>7)+methods.game.mapBaseY(), obj.plane())));
							RSTile tile = go.getLocation();
							double distance = methods.calculations.distanceTo(tile);
							if(closestTile!=null){
								if(distance>=closestDistance)
									break;
							}
							closestObj=go;
							closestTile=tile;
							closestDistance = distance;
							break;
						}
					}
				}
			}
		}
		return closestObj;
	}
	public GameObject getNextNearestByID(RSTile ignore, long id){
		GameObject closest = null;
		int plane=0, x=0, y=0;
		Region region = methods.game.region();
		for(Tile[][] map : region.tiles()){
			for(Tile[] row : map){
				for(Tile tile : row){
					if(tile!=null){
						RSTile rstile = new RSTile(x+methods.game.mapBaseX(), y+methods.game.mapBaseY(), plane);
						if(rstile.equals(ignore))
							continue;
						for(InteractableObject io : tile.objects()){
							if(io==null)
								continue;
							GameObject go = new GameObject(io, rstile);
							if(id==go.getID()){
								if(closest==null || methods.calculations.distanceTo(go.getLocation())<methods.calculations.distanceTo(closest.getLocation())){
									closest=go;
								}
							}
						}
						BoundaryObject bo = tile.boundary();
						if(bo!=null){
							GameObject go = new GameObject(bo, rstile);
							if(id==go.getID()){
								if(closest==null || methods.calculations.distanceTo(go.getLocation())<methods.calculations.distanceTo(closest.getLocation())){
									closest=go;
								}
							}
						}
						FloorDecoration fo = tile.floor();
						if(fo!=null){
							GameObject go = new GameObject(fo, rstile);
							if(id==go.getID()){
								if(closest==null || methods.calculations.distanceTo(go.getLocation())<methods.calculations.distanceTo(closest.getLocation())){
									closest=go;
								}
							}
						}
						WallDecoration wo = tile.wall();
						if(wo!=null){
							GameObject go = new GameObject(wo, rstile);
							if(id==go.getID()){
								if(closest==null || methods.calculations.distanceTo(go.getLocation())<methods.calculations.distanceTo(closest.getLocation())){
									closest=go;
								}
							}
						}
					}
					y++;
				}
				y=0;
				x++;
			}
			x=0;
			plane++;
		}
		Deque animableObjects = methods.game.animableObjectDeque();
		if(animableObjects!=null){
			Node head = animableObjects.head();
			for(Node node=head.previous();node!=null && node.hashCode()!=head.hashCode();node=node.previous()){
				if(node instanceof AnimableObject){
					AnimableObject obj = (AnimableObject)node;
					if(obj.id()==id){
						GameObject go = (new GameObject(obj, new RSTile((obj.x()>>7)+methods.game.mapBaseX(), (obj.y()>>7)+methods.game.mapBaseY(), obj.plane())));
						if(go.getLocation().equals(ignore))
							continue;
						if(closest==null || methods.calculations.distanceTo(go.getLocation())<methods.calculations.distanceTo(closest.getLocation())){
							closest=go;
						}
					}
				}
			}
		}
		return closest;
	}
	public GameObject getNextNearestByID(RSTile ignore, long...ids){
		GameObject closest = null;
		int plane=0, x=0, y=0;
		Region region = methods.game.region();
		for(Tile[][] map : region.tiles()){
			for(Tile[] row : map){
				for(Tile tile : row){
					if(tile!=null){
						RSTile rstile = new RSTile(x+methods.game.mapBaseX(), y+methods.game.mapBaseY(), plane);
						if(rstile.equals(ignore))
							continue;
						for(InteractableObject io : tile.objects()){
							if(io==null)
								continue;
							
							GameObject go = new GameObject(io, rstile);
							for(long id : ids){
								if(id==go.getID()){
									if(closest==null || methods.calculations.distanceTo(go.getLocation())<methods.calculations.distanceTo(closest.getLocation())){
										closest=go;
										break;
									}
								}
							}
						}
						BoundaryObject bo = tile.boundary();
						if(bo!=null){
							GameObject go = new GameObject(bo, rstile);
							for(long id : ids){
								if(id==go.getID()){
									if(closest==null || methods.calculations.distanceTo(go.getLocation())<methods.calculations.distanceTo(closest.getLocation())){
										closest=go;
										break;
									}
								}
							}
						}
						FloorDecoration fo = tile.floor();
						if(fo!=null){
							GameObject go = new GameObject(fo, rstile);
							for(long id : ids){
								if(id==go.getID()){
									if(closest==null || methods.calculations.distanceTo(go.getLocation())<methods.calculations.distanceTo(closest.getLocation())){
										closest=go;
										break;
									}
								}
							}
						}
						WallDecoration wo = tile.wall();
						if(wo!=null){
							GameObject go = new GameObject(wo, rstile);
							for(long id : ids){
								if(id==go.getID()){
									if(closest==null || methods.calculations.distanceTo(go.getLocation())<methods.calculations.distanceTo(closest.getLocation())){
										closest=go;
										break;
									}
								}
							}
						}
					}
					y++;
				}
				y=0;
				x++;
			}
			x=0;
			plane++;
		}
		Deque animableObjects = methods.game.animableObjectDeque();
		if(animableObjects!=null){
			Node head = animableObjects.head();
			for(Node node=head.previous();node!=null && node.hashCode()!=head.hashCode();node=node.previous()){
				if(node instanceof AnimableObject){
					AnimableObject obj = (AnimableObject)node;
					for(long id : ids){
						if(obj.id()==id){
							GameObject go = (new GameObject(obj, new RSTile((obj.x()>>7)+methods.game.mapBaseX(), (obj.y()>>7)+methods.game.mapBaseY(), obj.plane())));
							if(go.getLocation().equals(ignore))
								continue;
							if(closest==null || methods.calculations.distanceTo(go.getLocation())<methods.calculations.distanceTo(closest.getLocation())){
								closest=go;
							}
						}
					}
				}
			}
		}
		return closest;
	}
	public boolean isObjectAt(RSTile tile, long id){
    	int localX = tile.getLocalX();
    	int localY = tile.getLocalY();
    	if(localX<0 || localX>104 || localY<0 || localY>104)
    		return false;
    	Region region =methods.game.region();
    	if(region!=null){
    		Tile t = region.tiles()[tile.getPlane()][localX][localY];
    		if(t!=null){
    			for(InteractableObject io : t.objects()){
					if(io==null)
						continue;
    				GameObject go = new GameObject(io, tile);
    				if(id==go.getID())
    					return true;
    			}
    			BoundaryObject bo = t.boundary();
    			if(bo!=null){
    				GameObject go = new GameObject(bo, tile);
    				if(id==go.getID())
    					return true;
    			}
    			FloorDecoration fo = t.floor();
    			if(fo!=null){
    				GameObject go = new GameObject(fo, tile);
    				if(id==go.getID())
    					return true;
    			}
    			WallDecoration wo = t.wall();
    			if(wo!=null){
    				GameObject go = new GameObject(wo, tile);
    				if(id==go.getID())
    					return true;
    			}
    		}
    	}
		Deque animableObjects = methods.game.animableObjectDeque();
		if(animableObjects!=null){
			Node head = animableObjects.head();
			for(Node node=head.previous();node!=null && node.hashCode()!=head.hashCode();node=node.previous()){
				if(node instanceof AnimableObject){
					AnimableObject obj = (AnimableObject)node;
					if(obj.x()>>7==localX && obj.y()>>7==localY && obj.plane()==tile.plane && id==obj.id())
						return true;
				}
			}
		}
		return false;
	}
	public boolean isObjectAt(RSTile tile, long...ids){
    	int localX = tile.getLocalX();
    	int localY = tile.getLocalY();
    	if(localX<0 || localX>104 || localY<0 || localY>104)
    		return false;
    	Region region =methods.game.region();
    	if(region!=null){
    		Tile t = region.tiles()[tile.getPlane()][localX][localY];
    		if(t!=null){
    			for(InteractableObject io : t.objects()){
					if(io==null)
						continue;
    				GameObject go = new GameObject(io, tile);
    				for(long id : ids){
    					if(id==go.getID())
    						return true;
    				}
    			}
    			BoundaryObject bo = t.boundary();
    			if(bo!=null){
    				GameObject go = new GameObject(bo, tile);
    				for(long id : ids){
    					if(id==go.getID())
    						return true;
    				}
    			}
    			FloorDecoration fo = t.floor();
    			if(fo!=null){
    				GameObject go = new GameObject(fo, tile);
    				for(long id : ids){
    					if(id==go.getID())
    						return true;
    				}
    			}
    			WallDecoration wo = t.wall();
    			if(wo!=null){
    				GameObject go = new GameObject(wo, tile);
    				for(long id : ids){
    					if(id==go.getID())
    						return true;
    				}
    			}
    		}
    	}
		Deque animableObjects = methods.game.animableObjectDeque();
		if(animableObjects!=null){
			Node head = animableObjects.head();
			for(Node node=head.previous();node!=null && node.hashCode()!=head.hashCode();node=node.previous()){
				if(node instanceof AnimableObject){
					AnimableObject obj = (AnimableObject)node;
					if(obj.x()>>7==localX && obj.y()>>7==localY && obj.plane()==tile.plane){
						for(long id : ids){
							if(obj.id()==id)
								return true;
						}
					}
				}
			}
		}
		return false;
	}
	public GameObject[] getHoveringObjects(){
		return methods.game.getHoveringObjects();
	}
}
