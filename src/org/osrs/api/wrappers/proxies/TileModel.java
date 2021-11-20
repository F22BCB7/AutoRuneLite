package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="TileModel")
public class TileModel implements org.osrs.api.wrappers.TileModel{

	@BField
	public boolean flat;
	@BGetter
	@Override
	public boolean flat(){return flat;}
	@BField
	public int shape;
	@BGetter
	@Override
	public int shape(){return shape;}
	@BField
	public int rotation;
	@BGetter
	@Override
	public int rotation(){return rotation;}
	@BField
	public int underlayID;
	@BGetter
	@Override
	public int underlayID(){return underlayID;}
	@BField
	public int overlayID;
	@BGetter
	@Override
	public int overlayID(){return overlayID;}
	@BField
	public int[] verticesX;
	@BGetter
	@Override
	public int[] verticesX(){return verticesX;}
	@BField
	public int[] verticesY;
	@BGetter
	@Override
	public int[] verticesY(){return verticesY;}
	@BField
	public int[] verticesZ;
	@BGetter
	@Override
	public int[] verticesZ(){return verticesZ;}
	@BField
	public int[] triangleIndicesX;
	@BGetter
	@Override
	public int[] triangleIndicesX(){return triangleIndicesX;}
	@BField
	public int[] triangleIndicesY;
	@BGetter
	@Override
	public int[] triangleIndicesY(){return triangleIndicesY;}
	@BField
	public int[] triangleIndicesZ;
	@BGetter
	@Override
	public int[] triangleIndicesZ(){return triangleIndicesZ;}
	@BField
	public int[] triangleColorA;
	@BGetter
	@Override
	public int[] triangleColorA(){return triangleColorA;}
	@BField
	public int[] triangleColorB;
	@BGetter
	@Override
	public int[] triangleColorB(){return triangleColorB;}
	@BField
	public int[] triangleColorC;
	@BGetter
	@Override
	public int[] triangleColorC(){return triangleColorC;}
	@BField
	public int[] triangleTextureIDs;
	@BGetter
	@Override
	public int[] triangleTextureIDs(){return triangleTextureIDs;}
}