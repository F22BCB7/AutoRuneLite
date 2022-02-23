package org.osrs.api.objects.type;

import java.awt.Polygon;

import org.osrs.api.objects.RSModel;

public interface Modelled {
	//GameObjects, GroundItems, Actors, Tiles
	public RSModel getModel();
	public Polygon getPolygon();
	public Polygon[] getWireframe();
}
