package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BGetter;

@BClass(name="KeyInputHandler")
public class KeyInputHandler implements org.osrs.api.wrappers.KeyInputHandler{

	@BField
	public char[] unknownCharArray;
	@BGetter
	@Override
	public char[] unknownCharArray(){return unknownCharArray;}
	@BField
	public char unknownChar;
	@BGetter
	@Override
	public char unknownChar(){return unknownChar;}
	@BField
	public int[] unknownIntArray1;
	@BGetter
	@Override
	public int[] unknownIntArray1(){return unknownIntArray1;}
	@BField
	public int[] unknownIntArray2;
	@BGetter
	@Override
	public int[] unknownIntArray2(){return unknownIntArray2;}
	@BField
	public int[] unknownIntArray3;
	@BGetter
	@Override
	public int[] unknownIntArray3(){return unknownIntArray3;}
	@BField
	public boolean[] unknownBooleanArray1;
	@BGetter
	@Override
	public boolean[] unknownBooleanArray1(){return unknownBooleanArray1;}
	@BField
	public boolean[] unknownBooleanArray2;
	@BGetter
	@Override
	public boolean[] unknownBooleanArray2(){return unknownBooleanArray2;}
	@BField
	public boolean[] unknownBooleanArray3;
	@BGetter
	@Override
	public boolean[] unknownBooleanArray3(){return unknownBooleanArray3;}
	@BField
	public int unknownInt1;
	@BGetter
	@Override
	public int unknownInt1(){return unknownInt1;}
	@BField
	public int unknownInt2;
	@BGetter
	@Override
	public int unknownInt2(){return unknownInt2;}
	@BField
	public int unknownInt3;
	@BGetter
	@Override
	public int unknownInt3(){return unknownInt3;}
	@BField
	public int unknownInt4;
	@BGetter
	@Override
	public int unknownInt4(){return unknownInt4;}
	@BField
	public int unknownInt5;
	@BGetter
	@Override
	public int unknownInt5(){return unknownInt5;}
	@BField
	public int unknownInt6;
	@BGetter
	@Override
	public int unknownInt6(){return unknownInt6;}
}
