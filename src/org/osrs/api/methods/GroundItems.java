package org.osrs.api.methods;

import java.util.ArrayList;

import org.osrs.api.objects.GroundItem;
import org.osrs.api.objects.RSPlayer;
import org.osrs.api.objects.RSTile;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.Item;
import org.osrs.api.wrappers.ItemLayer;
import org.osrs.api.wrappers.Region;
import org.osrs.api.wrappers.Tile;

public class GroundItems extends MethodDefinition{
	public GroundItems(MethodContext context){
		super(context);
	}
	/**
	 * Retrieves all loaded GroundItems.
	 * @return all GroundItems
	 */
    public GroundItem[] getAllItems() {
        ArrayList<GroundItem> items = new ArrayList<GroundItem>();
        RSPlayer p = methods.players.getLocalPlayer();
        if (p != null) {
            RSTile tile = p.getLocation();
            for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane()))
            	items.add(gi);
            int di = 1;
            int dj = 0;
            int segment_length = 1;
            int i = tile.getX();
            int j = tile.getY();
            int z = tile.getPlane();
            int segment_passed = 0;
            for (int k = 0; k < (20*20); ++k) {
                i += di;
                j += dj;
                if(i==-1 || j==-1)
                	break;
                ++segment_passed;
                tile = new RSTile(i, j, z);
                for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane()))
                	items.add(gi);
                if (segment_passed == segment_length) {
                    segment_passed = 0;
                    int buffer = di;
                    di = -dj;
                    dj = buffer;
                    if (dj == 0) {
                        ++segment_length;
                    }
                }
            }
        }
        return items.toArray(new GroundItem[]{});
    }
    /**
     * Retrieves the GroundItems from a given RSTile.
     * @param tile
     * @return all GroundItems at the tile.
     */
	public GroundItem[] getItemsAt(int x, int y, int plane){
		try{
			int localX = x-((Client)methods.botInstance).mapBaseX();
			int localY = y-((Client)methods.botInstance).mapBaseY();
			ArrayList<GroundItem> items = new ArrayList<GroundItem>();
			Region region = ((Client)methods.botInstance).region();
			Tile t = region.tiles()[plane][localX][localY];
			if(t==null)
				return new GroundItem[]{};
			ItemLayer layer = t.itemLayer();
			if(layer==null)
				return new GroundItem[]{};
			Item item = (Item) layer.top();
			if(item!=null){
				items.add(new GroundItem(new RSTile(x, y, plane), item, layer.height()));
			}
			item = (Item) layer.middle();
			if(item!=null){
				items.add(new GroundItem(new RSTile(x, y, plane), item, layer.height()));
			}
			item = (Item) layer.bottom();
			if(item!=null){
				items.add(new GroundItem(new RSTile(x, y, plane), item, layer.height()));
			}
			return items.toArray(new GroundItem[]{});
		}
		catch(@SuppressWarnings("unused") Exception e){
		}
		return new GroundItem[]{};
	}
    /**
     * Retrieves the GroundItems from the ItemLayer of a
     * given RSTiles' LOCAL coordinates from the region data.
     * @param tile.localx
     * @param tile.localy
     * @param tile.plane
     * @return all GroundItems at the given RSTile coordinates.
     */
    public GroundItem[] getItemsAt(RSTile t) {
    	return getItemsAt(t.getX(), t.getY(), t.getPlane());
    }
	/**
	 * Retrieves all loaded GroundItems with a specified id.
	 * @param id
	 * @return all GroundItems with given id.
	 */
	public GroundItem[] getItemsByID(int id) {
		ArrayList<GroundItem> items = new ArrayList<GroundItem>();
		for(GroundItem gi : getAllItems()){
			if(gi.getID()==id)
				items.add(gi);
		}
		return items.toArray(new GroundItem[]{});
	}
	/**
	 * Retrieves all loaded GroundItems with a specified id(s).
	 * @param ids
	 * @return all GroundItems with given id(s).
	 */
	public GroundItem[] getItemsByID(int...ids) {
		ArrayList<GroundItem> items = new ArrayList<GroundItem>();
		for(GroundItem gi : getAllItems()){
			for(int id : ids)
				if(gi.getID()==id)
					items.add(gi);
		}
		return items.toArray(new GroundItem[]{});
	}
	/**
	 * Retrieves all loaded GroundItems with a specified name.
	 * @param name
	 * @return all GroundItems with given name.
	 */
	public GroundItem[] getItemsByName(String name) {
		ArrayList<GroundItem> items = new ArrayList<GroundItem>();
		for(GroundItem gi : getAllItems()){
			if(gi.getDefinition()==null)continue;
			if(gi.getName().equals(name))
				items.add(gi);
		}
		return items.toArray(new GroundItem[]{});
	}
	/**
	 * Retrieves all loaded GroundItems with a specified name(s).
	 * @param name(s)
	 * @return all GroundItems with given name(s).
	 */
	public GroundItem[] getItemsByName(String...names) {
		ArrayList<GroundItem> items = new ArrayList<GroundItem>();
		for(GroundItem gi : getAllItems()){
			if(gi.getDefinition()==null)continue;
			for(String name : names)
				if(gi.getName().equals(name))
					items.add(gi);
		}
		return items.toArray(new GroundItem[]{});
	}
	/**
	 * Retrieves all loaded GroundItems with a specified id
	 * and distance. This is used to reduce overhead when parsing
	 * the regions tiles by reducing the amount of data to parse.
	 * @param distance
	 * @param id
	 * @return all GroundItems within the given distance with the specified id.
	 */
	public GroundItem[] getItemsWithinByID(int distance, int id) {
        ArrayList<GroundItem> items = new ArrayList<GroundItem>();
        RSPlayer p = methods.players.getLocalPlayer();
        if (p != null) {
            RSTile tile = p.getLocation();
            for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())) {
            	if(gi.getID()==id)
            		items.add(gi);
            }
            int di = 1;
            int dj = 0;
            int segment_length = 1;
            int i = tile.getX();
            int j = tile.getY();
            int z = tile.getPlane();
            int segment_passed = 0;
            for (int k = 0; k < ((distance*2)*(distance*2)); ++k) {
                i += di;
                j += dj;
                if(i==-1 || j==-1)
                	break;
                ++segment_passed;
                tile = new RSTile(i, j, z);
                for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())){
                	if(gi.getID()==id)
                		items.add(gi);
                }
                if (segment_passed == segment_length) {
                    segment_passed = 0;
                    int buffer = di;
                    di = -dj;
                    dj = buffer;
                    if (dj == 0) {
                        ++segment_length;
                    }
                }
            }
        }
        return items.toArray(new GroundItem[]{});
	}
	/**
	 * Retrieves all loaded GroundItems with a specified id(s)
	 * and distance. This is used to reduce overhead when parsing
	 * the regions tiles by reducing the amount of data to parse.
	 * @param distance
	 * @param ids
	 * @return all GroundItems within the given distance with the specified id(s).
	 */
	public GroundItem[] getItemsWithinByID(int distance, int...ids) {
        ArrayList<GroundItem> items = new ArrayList<GroundItem>();
        RSPlayer p = methods.players.getLocalPlayer();
        if (p != null) {
            RSTile tile = p.getLocation();
            for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())) {
            	for(int id : ids)
            		if(gi.getID()==id)
            			items.add(gi);
            }
            int di = 1;
            int dj = 0;
            int segment_length = 1;
            int i = tile.getX();
            int j = tile.getY();
            int z = tile.getPlane();
            int segment_passed = 0;
            for (int k = 0; k < ((distance*2)*(distance*2)); ++k) {
                i += di;
                j += dj;
                if(i==-1 || j==-1)
                	break;
                ++segment_passed;
                tile = new RSTile(i, j, z);
                for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())){
                	for(int id : ids)
                		if(gi.getID()==id)
                			items.add(gi);
                }
                if (segment_passed == segment_length) {
                    segment_passed = 0;
                    int buffer = di;
                    di = -dj;
                    dj = buffer;
                    if (dj == 0) {
                        ++segment_length;
                    }
                }
            }
        }
        return items.toArray(new GroundItem[]{});
	}
	/**
	 * Retrieves all loaded GroundItems with a specified name
	 * and distance. This is used to reduce overhead when parsing
	 * the regions tiles by reducing the amount of data to parse.
	 * @param distance
	 * @param name
	 * @return all GroundItems within the given distance with the specified name.
	 */
	public GroundItem[] getItemsWithinByName(int distance, String name) {
        ArrayList<GroundItem> items = new ArrayList<GroundItem>();
        RSPlayer p = methods.players.getLocalPlayer();
        if (p != null) {
            RSTile tile = p.getLocation();
            for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())) {
            	if(gi.getDefinition()==null)continue;
            	if(gi.getName().equals(name))
            		items.add(gi);
            }
            int di = 1;
            int dj = 0;
            int segment_length = 1;
            int i = tile.getX();
            int j = tile.getY();
            int z = tile.getPlane();
            int segment_passed = 0;
            for (int k = 0; k < ((distance*2)*(distance*2)); ++k) {
                i += di;
                j += dj;
                if(i==-1 || j==-1)
                	break;
                ++segment_passed;
                tile = new RSTile(i, j, z);
                for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())) {
                	if(gi.getDefinition()==null)continue;
                	if(gi.getName().equals(name))
                		items.add(gi);
                }
                if (segment_passed == segment_length) {
                    segment_passed = 0;
                    int buffer = di;
                    di = -dj;
                    dj = buffer;
                    if (dj == 0) {
                        ++segment_length;
                    }
                }
            }
        }
        return items.toArray(new GroundItem[]{});
	}
	/**
	 * Retrieves all loaded GroundItems with a specified name(s)
	 * and distance. This is used to reduce overhead when parsing
	 * the regions tiles by reducing the amount of data to parse.
	 * @param distance
	 * @param name(s)
	 * @return all GroundItems within the given distance with the specified name(s).
	 */
	public GroundItem[] getItemsWithinByName(int distance, String...names) {
        ArrayList<GroundItem> items = new ArrayList<GroundItem>();
        RSPlayer p = methods.players.getLocalPlayer();
        if (p != null) {
            RSTile tile = p.getLocation();
            for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())) {
            	if(gi.getDefinition()==null)continue;
            	for(String name : names)
            		if(gi.getName().equals(name))
            			items.add(gi);
            }
            int di = 1;
            int dj = 0;
            int segment_length = 1;
            int i = tile.getX();
            int j = tile.getY();
            int z = tile.getPlane();
            int segment_passed = 0;
            for (int k = 0; k < ((distance*2)*(distance*2)); ++k) {
                i += di;
                j += dj;
                if(i==-1 || j==-1)
                	break;
                ++segment_passed;
                tile = new RSTile(i, j, z);
                for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())) {
                	if(gi.getDefinition()==null)continue;
                	for(String name : names)
                		if(gi.getName().equals(name))
                			items.add(gi);
                }
                if (segment_passed == segment_length) {
                    segment_passed = 0;
                    int buffer = di;
                    di = -dj;
                    dj = buffer;
                    if (dj == 0) {
                        ++segment_length;
                    }
                }
            }
        }
        return items.toArray(new GroundItem[]{});
	}
	/**
	 * Retrieves all loaded GroundItems within a given RSTile distance.
	 * @param distance
	 * @returns loaded items
	 */
	public GroundItem[] getItemsWithin(int distance) {
        ArrayList<GroundItem> items = new ArrayList<GroundItem>();
        RSPlayer p = methods.players.getLocalPlayer();
        if (p != null) {
            RSTile tile = p.getLocation();
            for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane()))
            	items.add(gi);
            int di = 1;
            int dj = 0;
            int segment_length = 1;
            int i = tile.getX();
            int j = tile.getY();
            int z = tile.getPlane();
            int segment_passed = 0;
            for (int k = 0; k < ((distance*2)*(distance*2)); ++k) {
                i += di;
                j += dj;
                if(i==-1 || j==-1)
                	break;
                ++segment_passed;
                tile = new RSTile(i, j, z);
                for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane()))
                	items.add(gi);
                if (segment_passed == segment_length) {
                    segment_passed = 0;
                    int buffer = di;
                    di = -dj;
                    dj = buffer;
                    if (dj == 0) {
                        ++segment_length;
                    }
                }
            }
        }
        return items.toArray(new GroundItem[]{});
	}
	/**
	 * Grabs the nearest GroundItem with the specified id.
	 * NOTE : This is not perfectly accurate. This uses a spiral algorithm
	 * from the local players location to spiral out, therefore,
	 * an actual nearest in calculated distance is not considered.
	 * @param id
	 * @return the nearest GroundItem with a given id
	 */
    public GroundItem getNearestByID(int id) {
        RSPlayer p = methods.players.getLocalPlayer();
        if (p != null) {
            RSTile tile = p.getLocation();
            for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())) {
            	if(gi.getID()==id)
            		return gi;
            }
            int di = 1;
            int dj = 0;
            int segment_length = 1;
            int i = tile.getX();
            int j = tile.getY();
            int segment_passed = 0;
            for (int k = 0; k < (104*104); ++k) {
                i += di;
                j += dj;
                ++segment_passed;
                tile = new RSTile(i, j);
                for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())) {
                	if (gi.getID()==id)
                		return gi;
                }
                if (segment_passed == segment_length) {
                    segment_passed = 0;
                    int buffer = di;
                    di = -dj;
                    dj = buffer;
                    if (dj == 0) {
                        ++segment_length;
                    }
                }
            }
        }
        return null;
    }
	/**
	 * Grabs the nearest GroundItem with the specified id(s).
	 * NOTE : This is not perfectly accurate. This uses a spiral algorithm
	 * from the local players location to spiral out, therefore,
	 * an actual nearest in calculated distance is not considered.
	 * @param ids
	 * @return the nearest GroundItem with a given id(s)
	 */
    public GroundItem getNearestByID(int...ids) {
        RSPlayer p = methods.players.getLocalPlayer();
        if (p != null) {
            RSTile tile = p.getLocation();
            for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())) {
            	for(int id : ids)
            		if(gi.getID()==id)
            			return gi;
            }
            int di = 1;
            int dj = 0;
            int segment_length = 1;
            int i = tile.getX();
            int j = tile.getY();
            int segment_passed = 0;
            for (int k = 0; k < (104*104); ++k) {
                i += di;
                j += dj;
                ++segment_passed;
                tile = new RSTile(i, j);
                for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())) {
                	for (int id : ids)
                		if (gi.getID()==id)
                			return gi;
                }
                if (segment_passed == segment_length) {
                    segment_passed = 0;
                    int buffer = di;
                    di = -dj;
                    dj = buffer;
                    if (dj == 0) {
                        ++segment_length;
                    }
                }
            }
        }
        return null;
    }
	/**
	 * Grabs the next nearest GroundItem with the specified id,
	 * ignoring a given tile.
	 * NOTE : This is not perfectly accurate. This uses a spiral algorithm
	 * from the local players location to spiral out, therefore,
	 * an actual nearest in calculated distance is not considered.
	 * @param tileToIgnore
	 * @param id
	 * @return the nearest GroundItem with a given id
	 */
    public GroundItem getNextNearestByID(RSTile ignore, int id) {
        RSPlayer p = methods.players.getLocalPlayer();
        if (p != null) {
            RSTile tile = p.getLocation();
        	if(!(tile.getX()==ignore.getX() && tile.getY()==ignore.getY() && tile.getPlane()==ignore.getPlane())){
                for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())) {
                	if (gi.getID()==id)
                		return gi;
                }
        	}
            int di = 1;
            int dj = 0;
            int segment_length = 1;
            int i = tile.getX();
            int j = tile.getY();
            int segment_passed = 0;
            for (int k = 0; k < (104*104); ++k) {
                i += di;
                j += dj;
                ++segment_passed;
                tile = new RSTile(i, j);
            	if(!(tile.getX()==ignore.getX() && tile.getY()==ignore.getY() && tile.getPlane()==ignore.getPlane())){
	                for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())) {
	                	if (gi.getID()==id)
	                		return gi;
	                }
            	}
                if (segment_passed == segment_length) {
                    segment_passed = 0;
                    int buffer = di;
                    di = -dj;
                    dj = buffer;
                    if (dj == 0) {
                        ++segment_length;
                    }
                }
            }
        }
        return null;
    }
	/**
	 * Grabs the next nearest GroundItem with the specified id(s),
	 * ignoring a given tile.
	 * NOTE : This is not perfectly accurate. This uses a spiral algorithm
	 * from the local players location to spiral out, therefore,
	 * an actual nearest in calculated distance is not considered.
	 * @param tileToIgnore
	 * @param ids
	 * @return the nearest GroundItem with a given id(s)
	 */
    public GroundItem getNextNearestByID(RSTile ignore, int...ids) {
        RSPlayer p = methods.players.getLocalPlayer();
        if (p != null) {
            RSTile tile = p.getLocation();
        	if(!(tile.getX()==ignore.getX() && tile.getY()==ignore.getY() && tile.getPlane()==ignore.getPlane())){
                for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())) {
                	for (int id : ids)
                		if (gi.getID()==id)
                			return gi;
                }
        	}
            int di = 1;
            int dj = 0;
            int segment_length = 1;
            int i = tile.getX();
            int j = tile.getY();
            int segment_passed = 0;
            for (int k = 0; k < (104*104); ++k) {
                i += di;
                j += dj;
                ++segment_passed;
                tile = new RSTile(i, j);
            	if(!(tile.getX()==ignore.getX() && tile.getY()==ignore.getY() && tile.getPlane()==ignore.getPlane())){
	                for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())) {
	                	for (int id : ids)
	                		if (gi.getID()==id)
	                			return gi;
	                }
            	}
                if (segment_passed == segment_length) {
                    segment_passed = 0;
                    int buffer = di;
                    di = -dj;
                    dj = buffer;
                    if (dj == 0) {
                        ++segment_length;
                    }
                }
            }
        }
        return null;
    }
	/**
	 * Grabs the nearest GroundItem with the specified name.
	 * NOTE : This is not perfectly accurate. This uses a spiral algorithm
	 * from the local players location to spiral out, therefore,
	 * an actual nearest in calculated distance is not considered.
	 * @param name
	 * @return the nearest GroundItem with a given name
	 */
    public GroundItem getNearestByName(String name) {
        RSPlayer p = methods.players.getLocalPlayer();
        if (p != null) {
            RSTile tile = p.getLocation();
            for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())) {
            	if(gi.getDefinition()==null)continue;
            	if (gi.getName().equals(name))
            		return gi;
            }
            int di = 1;
            int dj = 0;
            int segment_length = 1;
            int i = tile.getX();
            int j = tile.getY();
            int segment_passed = 0;
            for (int k = 0; k < (104*104); ++k) {
                i += di;
                j += dj;
                ++segment_passed;
                tile = new RSTile(i, j);
                for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())) {
                	if(gi.getDefinition()==null)continue;
                	if (gi.getName().equals(name))
                		return gi;
                }
                if (segment_passed == segment_length) {
                    segment_passed = 0;
                    int buffer = di;
                    di = -dj;
                    dj = buffer;
                    if (dj == 0) {
                        ++segment_length;
                    }
                }
            }
        }
        return null;
    
    }
	/**
	 * Grabs the nearest GroundItem with the specified name(s).
	 * NOTE : This is not perfectly accurate. This uses a spiral algorithm
	 * from the local players location to spiral out, therefore,
	 * an actual nearest in calculated distance is not considered.
	 * @param names
	 * @return the nearest GroundItem with a given name(s)
	 */
    public GroundItem getNearestByName(String...names) {
        RSPlayer p = methods.players.getLocalPlayer();
        if (p != null) {
            RSTile tile = p.getLocation();
            for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())) {
            	if(gi.getDefinition()==null)continue;
            	for (String name : names)
            		if (gi.getName().equals(name))
            			return gi;
            }
            int di = 1;
            int dj = 0;
            int segment_length = 1;
            int i = tile.getX();
            int j = tile.getY();
            int segment_passed = 0;
            for (int k = 0; k < (104*104); ++k) {
                i += di;
                j += dj;
                ++segment_passed;
                tile = new RSTile(i, j);
                for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())) {
                	if(gi.getDefinition()==null)continue;
                	for (String name : names)
                		if (gi.getName().equals(name))
                			return gi;
                }
                if (segment_passed == segment_length) {
                    segment_passed = 0;
                    int buffer = di;
                    di = -dj;
                    dj = buffer;
                    if (dj == 0) {
                        ++segment_length;
                    }
                }
            }
        }
        return null;
    }
	/**
	 * Grabs the next nearest GroundItem with the specified name,
	 * ignoring a given tile.
	 * NOTE : This is not perfectly accurate. This uses a spiral algorithm
	 * from the local players location to spiral out, therefore,
	 * an actual nearest in calculated distance is not considered.
	 * @param tileToIgnore
	 * @param name
	 * @return the nearest GroundItem with a given name
	 */
    public GroundItem getNextNearestByName(RSTile ignore, String name) {
        RSPlayer p = methods.players.getLocalPlayer();
        if (p != null) {
            RSTile tile = p.getLocation();
        	if(!(tile.getX()==ignore.getX() && tile.getY()==ignore.getY() && tile.getPlane()==ignore.getPlane())){
                for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())) {
                	if(gi.getDefinition()==null)continue;
                	if (gi.getName().equals(name))
                		return gi;
                }
        	}
            int di = 1;
            int dj = 0;
            int segment_length = 1;
            int i = tile.getX();
            int j = tile.getY();
            int segment_passed = 0;
            for (int k = 0; k < (104*104); ++k) {
                i += di;
                j += dj;
                ++segment_passed;
                tile = new RSTile(i, j);
            	if(!(tile.getX()==ignore.getX() && tile.getY()==ignore.getY() && tile.getPlane()==ignore.getPlane())){
	                for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())) {
	                	if(gi.getDefinition()==null)continue;
	                	if (gi.getName().equals(name))
	                		return gi;
	                }
            	}
                if (segment_passed == segment_length) {
                    segment_passed = 0;
                    int buffer = di;
                    di = -dj;
                    dj = buffer;
                    if (dj == 0) {
                        ++segment_length;
                    }
                }
            }
        }
        return null;
    }
	/**
	 * Grabs the next nearest GroundItem with the specified name(s),
	 * ignoring a given tile.
	 * NOTE : This is not perfectly accurate. This uses a spiral algorithm
	 * from the local players location to spiral out, therefore,
	 * an actual nearest in calculated distance is not considered.
	 * @param tileToIgnore
	 * @param names
	 * @return the nearest GroundItem with a given name(s)
	 */
    public GroundItem getNextNearestByName(RSTile ignore, String...names) {
        RSPlayer p = methods.players.getLocalPlayer();
        if (p != null) {
            RSTile tile = p.getLocation();
        	if(!(tile.getX()==ignore.getX() && tile.getY()==ignore.getY() && tile.getPlane()==ignore.getPlane())){
                for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())) {
                	if(gi.getDefinition()==null)continue;
                	for (String name : names)
                		if (gi.getName().equals(name))
                			return gi;
                }
        	}
            int di = 1;
            int dj = 0;
            int segment_length = 1;
            int i = tile.getX();
            int j = tile.getY();
            int segment_passed = 0;
            for (int k = 0; k < (104*104); ++k) {
                i += di;
                j += dj;
                ++segment_passed;
                tile = new RSTile(i, j);
            	if(!(tile.getX()==ignore.getX() && tile.getY()==ignore.getY() && tile.getPlane()==ignore.getPlane())){
	                for (GroundItem gi : getItemsAt(tile.getX(), tile.getY(), tile.getPlane())) {
	                	if(gi.getDefinition()==null)continue;
	                	for (String name : names)
	                		if (gi.getName().equals(name))
	                			return gi;
	                }
            	}
                if (segment_passed == segment_length) {
                    segment_passed = 0;
                    int buffer = di;
                    di = -dj;
                    dj = buffer;
                    if (dj == 0) {
                        ++segment_length;
                    }
                }
            }
        }
        return null;
    }
    /**
     * Checks to see if a given RSTile contains any of the
     * specified id.
     * @param tile
     * @param id
     * @return true if one of the id was found at the RSTile
     */
    public boolean isItemAt(RSTile tile, int id){
    	for(GroundItem gi : getItemsAt(tile))
    		if(gi.getID()==id)
    			return true;
    	return false;
    }
    /**
     * Checks to see if a given RSTile contains any of the
     * specified id(s).
     * @param tile
     * @param ids
     * @return true if one of the id(s) was found at the RSTile
     */
    public boolean isItemAt(RSTile tile, int...ids){
    	for(GroundItem gi : getItemsAt(tile))
    		for(int id : ids)
    			if(gi.getID()==id)
    				return true;
    	return false;
    }
    /**
     * Checks to see if a given RSTile contains any of the
     * specified name.
     * @param tile
     * @param name
     * @return true if the name was found at the RSTile
     */
    public boolean isItemAt(RSTile tile, String name){
    	for(GroundItem gi : getItemsAt(tile)){
    		if(gi.getDefinition()==null)continue;
    		if(gi.getName().equals(name))
    			return true;
    	}
    	return false;
    }
    /**
     * Checks to see if a given RSTile contains any of the
     * specified name(s).
     * @param tile
     * @param names
     * @return true if one of the name(s) was found at the RSTile
     */
    public boolean isItemAt(RSTile tile, String...names){
    	for(GroundItem gi : getItemsAt(tile)){
    		if(gi.getDefinition()==null)continue;
    		for(String name : names)
    			if(gi.getName().equals(name))
    				return true;
    	}
    	return false;
    }
}
