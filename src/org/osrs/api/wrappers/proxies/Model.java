package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import java.util.HashMap;
import java.util.Map;

@BClass(name="Model")
public class Model extends RenderableNode implements org.osrs.api.wrappers.Model{

	@BField
	public int[][] animationIndiceGroups;
	@BGetter
	@Override
	public int[][] animationIndiceGroups(){return animationIndiceGroups;}
	@BField
	public byte[] texturedTriangleAlphas;
	@BGetter
	@Override
	public byte[] texturedTriangleAlphas(){return texturedTriangleAlphas;}
	@BField
	public int[] indicesY;
	@BGetter
	@Override
	public int[] indicesY(){return indicesY;}
	@BField
	public int verticesCount;
	@BGetter
	@Override
	public int verticesCount(){return verticesCount;}
	@BField
	public int radius;
	@BGetter
	@Override
	public int radius(){return radius;}
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
	public int texturedIndiceCount;
	@BGetter
	@Override
	public int texturedIndiceCount(){return texturedIndiceCount;}
	@BField
	public int extremeZ;
	@BGetter
	@Override
	public int extremeZ(){return extremeZ;}
	@BField
	public int[] indicesZ;
	@BGetter
	@Override
	public int[] indicesZ(){return indicesZ;}
	@BField
	public int[] verticesX;
	@BGetter
	@Override
	public int[] verticesX(){return verticesX;}
	@BField
	public int[] indicesX;
	@BGetter
	@Override
	public int[] indicesX(){return indicesX;}
	@BField
	public byte[] triangleAlphas;
	@BGetter
	@Override
	public byte[] triangleAlphas(){return triangleAlphas;}
	@BField
	public byte[] trianglePriorities;
	@BGetter
	@Override
	public byte[] trianglePriorities(){return trianglePriorities;}
	@BField
	public int[] triangleHSLA;
	@BGetter
	@Override
	public int[] triangleHSLA(){return triangleHSLA;}
	@BField
	public short[] triangleTextures;
	@BGetter
	@Override
	public short[] triangleTextures(){return triangleTextures;}
	@BField
	public byte priority;
	@BGetter
	@Override
	public byte priority(){return priority;}
	@BField
	public int indicesCount;
	@BGetter
	@Override
	public int indicesCount(){return indicesCount;}
	@BField
	public int[] texturedIndicesX;
	@BGetter
	@Override
	public int[] texturedIndicesX(){return texturedIndicesX;}
	@BField
	public int[] texturedIndicesY;
	@BGetter
	@Override
	public int[] texturedIndicesY(){return texturedIndicesY;}
	@BField
	public int[] texturedIndicesZ;
	@BGetter
	@Override
	public int[] texturedIndicesZ(){return texturedIndicesZ;}
	@BField
	public int[][] triangleAlphaIndiceGroups;
	@BGetter
	@Override
	public int[][] triangleAlphaIndiceGroups(){return triangleAlphaIndiceGroups;}
	@BField
	public boolean hasMinimumBounds;
	@BGetter
	@Override
	public boolean hasMinimumBounds(){return hasMinimumBounds;}
	@BField
	public int boundsType;
	@BGetter
	@Override
	public int boundsType(){return boundsType;}
	@BField
	public int bottomY;
	@BGetter
	@Override
	public int bottomY(){return bottomY;}
	@BField
	public int diameter;
	@BGetter
	@Override
	public int diameter(){return diameter;}
	@BField
	public int centerX;
	@BGetter
	@Override
	public int centerX(){return centerX;}
	@BField
	public int centerY;
	@BGetter
	@Override
	public int centerY(){return centerY;}
	@BField
	public int centerZ;
	@BGetter
	@Override
	public int centerZ(){return centerZ;}
	@BField
	public int extremeX;
	@BGetter
	@Override
	public int extremeX(){return extremeX;}
	@BField
	public int extremeY;
	@BGetter
	@Override
	public int extremeY(){return extremeY;}
	@BField
	public int XYZMag;
	@BGetter
	@Override
	public int XYZMag(){return XYZMag;}
	@BField
	public int[] triangleHSLB;
	@BGetter
	@Override
	public int[] triangleHSLB(){return triangleHSLB;}
	@BField
	public int[] triangleHSLC;
	@BGetter
	@Override
	public int[] triangleHSLC(){return triangleHSLC;}
}