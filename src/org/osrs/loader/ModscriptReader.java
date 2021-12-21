package org.osrs.loader;

import java.io.File;
import java.io.FileInputStream;

import org.osrs.injection.ClassHook;
import org.osrs.injection.FieldHook;
import org.osrs.injection.MethodHook;

public class ModscriptReader {
	private byte[] dataBuffer = new byte[5000];
	private int bufferPosition=0;
	private FileInputStream stream;
	int fileSize;
	private Modscript modscript;
	public ModscriptReader(Modscript mod){
		modscript = mod;
	}
	public void loadModscript(){
		try{
			File file = new File("modscript.ms2");
			stream = new FileInputStream(file);
			int size = stream.available();
			System.out.println("Loading modscript... "+size+" bytes");
			dataBuffer = new byte[size];
			stream.read(dataBuffer);
			int revision = readShort();
			modscript.modscriptRevision = revision;
			System.out.println("Modscript revision : "+revision);
			ClassHook lastClass = null;
			int classes=0, fields=0, methods=0;
			while(bufferPosition<dataBuffer.length-1){
				int opcode = readByte();
				if(opcode==1){//Class
					String name = readString();
					String refactoredName = readString();
					int fieldCount = readShort();
					int methodCount = readShort();
					//System.out.println("Class Hook : "+name+"->"+refactoredName+" "+fieldCount+" fields, "+methodCount+" methods.");
					ClassHook classHook = new ClassHook(name, refactoredName);
					modscript.classHooks.add(classHook);
					classes++;
					lastClass = classHook;
				}
				else if(opcode==2){//Field
					String name = readString();
					String refactoredName = readString();
					String desc = readString();
					Object multiplier = 1;
					if(desc.equals("I"))
						multiplier = readInt();
					else if(desc.equals("J"))
						multiplier = readLong();
					//System.out.println("Field Hook : "+lastClass.obfuscatedName+"."+name+"->"+refactoredName+" "+desc+" "+multiplier);
					lastClass.fieldHooks.add(new FieldHook(lastClass.obfuscatedName, name, refactoredName, desc, multiplier));
					fields++;
				}
				else if(opcode==3){//Method
					String name = readString();
					String refactoredName = readString();
					String desc = readString();
					Object predicate=null;
					if(desc.contains("I)")){
						predicate = readInt();
					}
					else if(desc.contains("B)")){
						predicate = Byte.valueOf(""+readInt());
					}
					else if(desc.contains("S)")){
						predicate = Short.valueOf(""+readInt());
					}
					//System.out.println("Method Hook : "+lastClass.obfuscatedName+"."+name+desc+"->"+refactoredName+" "+predicate);
					lastClass.methodHooks.add(new MethodHook(lastClass.obfuscatedName, name, refactoredName, desc, predicate));
					methods++;
				}
				else if(opcode==4){//Static Field
					String owner = readString();
					String name = readString();
					String refactoredName = readString();
					String desc = readString();
					Object multiplier = 1;
					if(desc.equals("I"))
						multiplier = readInt();
					else if(desc.equals("J"))
						multiplier = readLong();
					//System.out.println("Static Field Hook : "+owner+"."+name+"->"+refactoredName+" "+desc+" "+multiplier);
					modscript.staticFields.add(new FieldHook(owner, name, refactoredName, desc, multiplier));
					fields++;
				}
				else if(opcode==5){//Static Method
					String owner = readString();
					String name = readString();
					String refactoredName = readString();
					String desc = readString();
					Object predicate=null;
					if(desc.contains("I)")){
						predicate = readInt();
					}
					else if(desc.contains("B)")){
						predicate = Byte.valueOf(""+readInt());
					}
					else if(desc.contains("S)")){
						predicate = Short.valueOf(""+readInt());
					}
					//System.out.println("Static Method Hook : "+owner+"."+name+desc+"->"+refactoredName+" "+predicate);
					modscript.staticMethods.add(new MethodHook(owner, name, refactoredName, desc, predicate));
					methods++;
				}
				else if(opcode==6){//Static opcode
					int staticFieldCount = readShort();
					int staticMethodCount = readShort();
					//System.out.println("Reading static hooks... "+staticFieldCount+" fields, "+staticMethodCount+" methods.");
				}
			}
			System.out.println("Classes : "+classes);
			System.out.println("Fields : "+fields);
			System.out.println("Methods : "+methods);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public byte readByte() {
		return dataBuffer[bufferPosition++];
	}
	public int readShort() {
		bufferPosition += 2;
		return ((dataBuffer[bufferPosition - 2] & 255) << 8) + (dataBuffer[bufferPosition - 1] & 255);
	}
	public int readInt() {
		bufferPosition += 4;
		return ((dataBuffer[bufferPosition - 4] & 255) << 24) + ((dataBuffer[bufferPosition - 3] & 255) << 16) + ((dataBuffer[bufferPosition - 2] & 255) << 8) + (dataBuffer[bufferPosition - 1] & 255);
	}
	public long readLong() {
		long var2 = readInt() & 4294967295L;
		long var4 = readInt() & 4294967295L;
		return (var2 << 32) + var4;
	}
	public String readString() {
		int startOffset = bufferPosition;
		while(dataBuffer[bufferPosition++] != 0);
		int length = bufferPosition - startOffset - 1;
		return 0 == length?"":decodeStringBytes(dataBuffer, startOffset, length);
	}
	private String decodeStringBytes(byte[] var0, int var1, int var2) {
		char[] var4 = new char[var2];
		int var5 = 0;
		for(int var6 = 0; var6 < var2; ++var6) {
			int var7 = var0[var6 + var1] & 255;
			if(var7 != 0) {
				var4[var5++] = (char)var7;
			}
		}
		return new String(var4, 0, var5);
	}
}
