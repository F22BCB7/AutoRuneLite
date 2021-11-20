package org.osrs.loader;

import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.osrs.api.wrappers.proxies.GameShell;
import org.osrs.injection.ClassHook;
import org.osrs.injection.ClassResolver;
import org.osrs.injection.FieldHook;
import org.osrs.injection.MethodHook;

public class Modscript {
	public int modscriptRevision=-1;
	public ClassResolver resolver = null;
	public boolean exists=false;
	public ArrayList<ClassHook> classHooks = new ArrayList<ClassHook>();
	public ArrayList<FieldHook> staticFields = new ArrayList<FieldHook>();
	public ArrayList<MethodHook> staticMethods = new ArrayList<MethodHook>();
	public HashMap<String, String[]> packetMethodOrder = new HashMap<String, String[]>();
	public int injectedClasses = 0;
	public int injectedGetters = 0;
	public Modscript(){
		File file = new File("modscript.ms2");
		if(file.exists() && !file.isDirectory()){
			//check hashcode in-case of needed update
			//if all uptodate
			exists=true;
		}
		else{
			System.out.println("Need to download modscript from server!");

			//download from web server

			//check if new download file exists locally
			//if(newFileExists)exists=true;

			System.exit(0);//remove when server is usable
		}
	}
	public void loadModscript(){
		long start = System.currentTimeMillis();
		if(!exists){
			System.out.println("No modscript data found! Unable to load.");
			System.exit(0);
		}
		new ModscriptReader(this).loadModscript();
		System.out.println("Loaded modscript r"+modscriptRevision+" in "+(System.currentTimeMillis()-start)+"ms");
		resolver = new ClassResolver(this);
	}
	public void runInjection(ArrayList<ClassNode> classNodes){
		try{
			//Prepping MouseListener and Canvas
			String keyboardName = resolver.getObfuscatedClassName("KeyboardListener");
			String mouseName = resolver.getObfuscatedClassName("MouseListener");
			String mouseWheelName = resolver.getObfuscatedClassName("MouseWheelListener");
			String canvasName = resolver.getObfuscatedClassName("Canvas");
			for(ClassNode node : classNodes){
				if(node.name.equals(mouseName)){
					ArrayList<MethodNode> rename = new ArrayList<MethodNode>();
					for(MethodNode mn : node.methods){
						if(mn.name.startsWith("mouse"))
							rename.add(mn);
					}
					for(MethodNode mn : rename){
						mn.name=mn.name+"0";
					}
				}
				else if(node.name.equals(mouseWheelName)){
					ArrayList<MethodNode> rename = new ArrayList<MethodNode>();
					for(MethodNode mn : node.methods){
						if(mn.name.startsWith("mouse"))
							rename.add(mn);
					}
					for(MethodNode mn : rename){
						mn.name=mn.name+"0";
					}
				}
				else if(node.name.equals(keyboardName)){
					ArrayList<MethodNode> rename = new ArrayList<MethodNode>();
					for(MethodNode mn : node.methods){
						if(mn.name.startsWith("key"))
							rename.add(mn);
					}
					for(MethodNode mn : rename){
						mn.name=mn.name+"0";
					}
				}
				else if(node.name.equals(canvasName)){
					ArrayList<MethodNode> rename = new ArrayList<MethodNode>();
					for(MethodNode mn : node.methods){
						if(mn.name.equals("paint"))
							rename.add(mn);
					}
					for(MethodNode mn : rename){
						mn.name=mn.name+"0";
					}
				}
			}
			//Hotfix hooks
			for(ClassHook ch : classHooks){
				/** DO NOT REMOVE OR MODIFY - part of MouseListener setup**/
				if(ch.refactoredName.equals("MouseListener")){
					ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mouseDragged0", "mouseDragged", "(Ljava/awt/event/MouseEvent;)V", -1));
					ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mouseEntered0", "mouseEntered", "(Ljava/awt/event/MouseEvent;)V", -1));
					ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mouseExited0", "mouseExited", "(Ljava/awt/event/MouseEvent;)V", -1));
					ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mousePressed0", "mousePressed", "(Ljava/awt/event/MouseEvent;)V", -1));
					ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mouseReleased0", "mouseReleased", "(Ljava/awt/event/MouseEvent;)V", -1));
					ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mouseClicked0", "mouseClicked", "(Ljava/awt/event/MouseEvent;)V", -1));
					ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mouseMoved0", "mouseMoved", "(Ljava/awt/event/MouseEvent;)V", -1));
				}
				/** DO NOT REMOVE OR MODIFY - part of MouseWheelListener setup**/
				if(ch.refactoredName.equals("MouseWheelListener")){
					ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mouseWheelMoved0", "mouseWheelMoved", "(Ljava/awt/event/MouseWheelEvent;)V", -1));
				}
				/** DO NOT REMOVE OR MODIFY - part of KeyboardListener setup**/
				else if(ch.refactoredName.equals("KeyboardListener")){
					ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "keyPressed0", "keyPressed", "(Ljava/awt/event/KeyEvent;)V", -1));
					ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "keyReleased0", "keyReleased", "(Ljava/awt/event/KeyEvent;)V", -1));
					ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "keyTyped0", "keyTyped", "(Ljava/awt/event/KeyEvent;)V", -1));
				}
				/** DO NOT REMOVE OR MODIFY - part of Canvas setup**/
				else if(ch.refactoredName.equals("Canvas")){
					ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "paint0", "paint", "(Ljava/awt/Graphics;)V", -1));
				}
			}
			System.out.println("Compiling mods...");
			Package pack = GameShell.class.getPackage();
			URL root = GameShell.class.getClassLoader().getResource(pack.getName().replace(".", "/"));
			
			//Load proxy classes to be injected into client
			ArrayList<ClassNode> proxyClasses = new ArrayList<ClassNode>();
			
			if(root.toString().startsWith("jar:file:")){//Loading via jar
				JarFile jar = new JarFile(root.toString().substring(10, root.toString().indexOf("!")));
				Enumeration<?> en = jar.entries();
				while (en.hasMoreElements()) {
					JarEntry entry = (JarEntry) en.nextElement();
					if(!entry.getName().endsWith(".class"))
						continue;
					if(!entry.getName().startsWith("org/osrs/api/wrappers/proxies"))
						continue;
					byte[] buffer = new byte[1024];
					int read;
					InputStream is = jar.getInputStream(entry);
					byte[] allByteData = new byte[0];
					while ((read = is.read(buffer)) != -1){
						byte[] tempBuff = new byte[read+allByteData.length];
						for(int i=0;i<allByteData.length;++i)
							tempBuff[i]=allByteData[i];
						for(int i=0;i<read;++i)
							tempBuff[i+allByteData.length]=buffer[i];
						allByteData=tempBuff;
					}
					ClassReader cr = new ClassReader(allByteData);
					ClassNode cn = new ClassNode();
					cr.accept(cn, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
					proxyClasses.add(cn);
				}
			}
			else{//Loading via IDE
				// Filter .class files.
				File[] files = new File(root.toURI()).listFiles(new FilenameFilter() {
				    public boolean accept(File dir, String name) {
				        return name.endsWith(".class");
				    }
				});
				System.out.println("Loaded "+files.length+" file mods...");
				for (File file : new File(root.getFile()).listFiles(new FilenameFilter(){public boolean accept(File dir, String name){return name.endsWith(".class");}})) {
					try{
					    InputStream in = new FileInputStream(file);
					    ClassNode cn = new ClassNode();
					    ClassReader reader = new ClassReader(in);
					    reader.accept(cn, ClassReader.EXPAND_FRAMES);
					    proxyClasses.add(cn);
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			System.out.println("Loaded "+proxyClasses.size()+" proxy classes...");
			ArrayList<String> injected = new ArrayList<String>();
			int resolvedInsn=0; 
			int lastPercentage=0;
			
			HashMap<String, ClassHook> classMapping = new HashMap<String, ClassHook>();
			HashMap<String, FieldHook> fieldMapping = new HashMap<String, FieldHook>();
			HashMap<String, MethodHook> methodMapping = new HashMap<String, MethodHook>();
			ArrayList<ClassNode> customClasses = new ArrayList<ClassNode>();
			HashMap<FieldNode, String> customFields = new HashMap<FieldNode, String>();
			HashMap<MethodNode, String> customMethods = new HashMap<MethodNode, String>();
			HashMap<MethodNode, FieldHook> fieldGetters = new HashMap<MethodNode, FieldHook>();

			HashMap<String, MethodNode> methodDetours = new HashMap<String, MethodNode>();
			HashMap<String, MethodHook> methodDetourHooks = new HashMap<String, MethodHook>();
			HashMap<String, MethodNode> fieldGetterDetours = new HashMap<String, MethodNode>();
			HashMap<String, String> targetGetterDetours = new HashMap<String, String>();
			HashMap<String, MethodNode> fieldSetterDetours = new HashMap<String, MethodNode>();
			HashMap<String, String> targetSetterDetours = new HashMap<String, String>();
			
			for(ClassNode proxyClass : proxyClasses){
				String sourceName = proxyClass.name;
				if(hasAnnotation(proxyClass, "Lorg/osrs/injection/bytescript/BClass;")){
					//is class mapping to hook
					sourceName = getAnnotation(proxyClass);
					ClassHook ch = resolver.getClassHook(sourceName);
					if(ch!=null){
						classMapping.put(sourceName, ch);
					}
					else{
						//System.out.println("Failed to find class hook for class mapping : "+sourceName);
					}
				}
				else if(hasAnnotation(proxyClass, "Lorg/osrs/injection/bytescript/BCustomClass;")){
					//is custom class to be added
					customClasses.add(proxyClass);
				}
				
				for(FieldNode proxyField : proxyClass.fields){
					if(hasAnnotation(proxyField, "Lorg/osrs/injection/bytescript/BField;")){
						//is field mapping to hook
						boolean isStatic = (proxyField.access & Opcodes.ACC_STATIC) != 0;
						String owner = sourceName;
						String name = proxyField.name;
						String desc = proxyField.desc;
						FieldHook fh = resolver.getFieldHook(owner, name, isStatic);
						if(fh!=null){
							fieldMapping.put(owner+"."+name, fh);
						}
						else{
							//System.out.println("Failed to load BField : "+owner+"."+name+" "+desc +" "+isStatic);
						}
					}
					else if(hasAnnotation(proxyField, "Lorg/osrs/injection/bytescript/BVar;")){
						//is custom field to be added
						customFields.put(proxyField, sourceName);
					}
				}
				
				for(MethodNode proxyMethod : proxyClass.methods){
					if(hasAnnotation(proxyMethod, "Lorg/osrs/injection/bytescript/BMethod;")){
						//is method mapping to hook
						boolean isStatic = proxyMethod.isStatic();
						String owner = sourceName;
						String name = getAnnotation(proxyMethod);
						String desc = proxyMethod.desc;
						MethodHook mh = resolver.getMethodHook(owner, name, desc, isStatic);
						if(mh!=null){
							methodMapping.put(owner+"."+name+desc, mh);
						}
						else{
							//System.out.println("Failed to load BMethod : "+owner+"."+name+desc+" "+isStatic);
						}
					}
					else if(hasAnnotation(proxyMethod, "Lorg/osrs/injection/bytescript/BFunction;")){
						//is custom method to be added
						customMethods.put(proxyMethod, sourceName);
					}
					else if(hasAnnotation(proxyMethod, "Lorg/osrs/injection/bytescript/BDetour;")){
						//is detour method to be added
						boolean isStatic = proxyMethod.isStatic();
						String owner = sourceName;
						String name = proxyMethod.name;
						String desc = proxyMethod.desc;
						String obfDesc = resolver.getObfuscatedDesc(desc);
						MethodHook mh = resolver.getMethodHook(owner, name, desc, isStatic);
						if(mh!=null && mh.desc.equals(obfDesc)){//prevent duplicates from different predicates
							methodDetours.put(mh.owner+"."+mh.obfuscatedName+mh.desc, proxyMethod);
							methodDetourHooks.put(mh.owner+"."+mh.obfuscatedName+mh.desc, mh);
						}
						else{
							//System.out.println("Failed to load BMethodDetour : "+owner+"."+name+desc+" "+obfDesc+" "+isStatic);
						}
					}
					else if(hasAnnotation(proxyMethod, "Lorg/osrs/injection/bytescript/BGetter;")){
						//is getter method to be added
						boolean isStatic = proxyMethod.isStatic();
						//Check for isStatic, as BGetters are wrapper accessors, so accessed like 
						//clientInstance.BGetterName();
						for(AbstractInsnNode insn : proxyMethod.instructions.toArray()){
							if(insn instanceof FieldInsnNode){
								isStatic=insn.getOpcode()==Opcodes.GETSTATIC;
								break;
							}
						}
						String owner = sourceName;
						String name = proxyMethod.name;
						String desc = proxyMethod.desc.replace("()", "");//trim to get field desc
						FieldHook fh = resolver.getFieldHook(owner, name, isStatic);
						if(fh!=null){
							fieldGetters.put(proxyMethod, fh);
						}
						else{
							//System.out.println("Failed to load BGetter : "+owner+"."+name+" "+desc+" "+isStatic);
						}
					}
					else if(hasAnnotation(proxyMethod, "Lorg/osrs/injection/bytescript/BGetterDetour;")){
						//is getter detour method to be added
						boolean isStatic = proxyMethod.isStatic();
						String owner = sourceName;
						String name = proxyMethod.name.replace("get_", "");//trim to get field name
						String desc = proxyMethod.desc.replace("()", "");//trim to get field desc
						FieldHook fh = resolver.getFieldHook(owner, name, isStatic);
						if(fh!=null){
							fieldGetterDetours.put(owner+"."+name, proxyMethod);
						}
						else{
							//System.out.println("Failed to load BGetterDetour : "+owner+"."+name+" "+desc+" "+isStatic);
						}
					}
					else if(hasAnnotation(proxyMethod, "Lorg/osrs/injection/bytescript/BSetterDetour;")){
						//is setter detour method to be added
						boolean isStatic = proxyMethod.isStatic();
						String owner = sourceName;
						String name = proxyMethod.name.replace("set_", "");//trim to get field name
						String desc = proxyMethod.desc.replace("(", "").replace(")V", "");//trim to get field desc
						FieldHook fh = resolver.getFieldHook(owner, name, isStatic);
						if(fh!=null){
							fieldSetterDetours.put(owner+"."+name, proxyMethod);
						}
						else{
							//System.out.println("Failed to load BSetterDetour : "+owner+"."+name+" "+desc+" "+isStatic);
						}
					}
				}
			}
			System.out.println("Loaded all mods...");
			System.out.println("Class Mappings:"+classMapping.size()+" Field Mappings:"+fieldMapping.size()+" Method Mappings:"+methodMapping.size());
			System.out.println("Custom Classes:"+customClasses.size()+" Custom Fields:"+customFields.size()+" Custom Methods:"+customMethods.size());
			System.out.println("Method Detours:"+methodDetours.size()+" Field Get Detours:"+fieldGetterDetours.size()+" Field Set Detours:"+fieldSetterDetours.size());
			System.out.println("Field Getters:"+fieldGetters.size());
			int addedClasses=0;
			for(ClassNode customClass : customClasses){
				classNodes.add(customClass);
				addedClasses++;
			}
			int addedFields=0;
			for(FieldNode customField : customFields.keySet()){
				String targetClass = customFields.get(customField);
				String obfuscatedTargetName = resolver.getObfuscatedClassName(targetClass);
				for(ClassNode cn : classNodes){
					if(cn.name.equals(obfuscatedTargetName)){
						cn.fields.add(customField);
						addedFields++;
						break;
					}
				}
			}
			int addedMethods=0;
			for(MethodNode customMethod : customMethods.keySet()){
				String targetClass = customMethods.get(customMethod);
				String obfuscatedTargetName = resolver.getObfuscatedClassName(targetClass);
				for(AbstractInsnNode insn : customMethod.instructions.toArray()){
					if(insn instanceof FieldInsnNode){
						FieldInsnNode fin = (FieldInsnNode)insn;
						FieldInsnNode clone = (FieldInsnNode)fin.clone(null);
						String oldOwner = fin.owner.replace("org/osrs/api/wrappers/proxies/", "");
						String oldName = fin.name;
						String newOwner = resolver.getObfuscatedFieldOwner(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
						if(newOwner!=null && !newOwner.equals("null"))
							clone.owner = newOwner;
						else{
							newOwner = resolver.getObfuscatedClassName(oldOwner);
							if(newOwner!=null && !newOwner.equals("null"))
								clone.owner = newOwner;
							else
								clone.owner = fin.owner;
						}
						String newName = resolver.getObfuscatedFieldName(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
						if(newName!=null && !newName.equals("null"))
							clone.name = newName;
						else
							clone.name = oldName;
						String oldDesc = fin.desc.replace("org/osrs/api/wrappers/proxies/", "");
						String newDesc = oldDesc.startsWith("L")?resolver.getObfuscatedType(oldDesc):oldDesc;
						if(newDesc!=null && !newDesc.equals("null"))
							clone.desc = newDesc;
						else
							clone.desc = oldDesc;
						FieldHook fh = resolver.getFieldHook(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
						if(fh!=null){
							clone.owner=fh.owner;
							clone.name=fh.obfuscatedName;
							clone.desc=fh.dataType;
						}
						customMethod.instructions.set(insn, clone);
						resolvedInsn++;
						//System.out.println("Resolved FieldInsn : "+oldOwner+"."+oldName+" "+oldDesc+" : "+clone.owner+"."+clone.name+" "+clone.desc);
					}
					else if(insn instanceof MethodInsnNode){
						MethodInsnNode min = (MethodInsnNode)insn;
						MethodInsnNode clone = (MethodInsnNode)min.clone(null);
						String oldOwner = min.owner.replace("org/osrs/api/wrappers/proxies/", "");
						String oldName = min.name;
						String oldDesc = min.desc;
						String newOwner = resolver.getObfuscatedMethodOwner(oldOwner, oldName.replace("_", ""), oldDesc, (insn.getOpcode()==Opcodes.INVOKESTATIC));
						if(newOwner!=null && !newOwner.equals("null"))
							clone.owner = newOwner;
						else{
							newOwner = resolver.getObfuscatedClassName(oldOwner);
							if(newOwner!=null && !newOwner.equals("null"))
								clone.owner = newOwner;
							else
								clone.owner = min.owner;
						}
						String newDesc = resolver.getObfuscatedDesc(oldDesc);
						if(newDesc!=null && !newDesc.equals("null"))
							clone.desc = newDesc;
						else
							clone.desc = oldDesc;
						String newName = resolver.getObfuscatedMethodName(oldOwner, oldName.replace("_", ""), oldDesc, (insn.getOpcode()==Opcodes.INVOKESTATIC));
						if(newName==null && oldOwner.equals("Client"))//static hooks
							newName = resolver.getObfuscatedMethodName(oldOwner, oldName.replace("_", ""), oldDesc, true);
						if(newName!=null && !newName.equals("null"))
							clone.name = newName;
						else
							clone.name = oldName;
						customMethod.instructions.set(insn, clone);
						resolvedInsn++;
						//System.out.println("Resolved MethodInsn : "+oldOwner+"."+oldName+" "+oldDesc+" : "+clone.owner+"."+clone.name+" "+clone.desc);
					}
				}
				for(ClassNode cn : classNodes){
					if((customMethod.isStatic() && cn.name.equals("client")) || 
							(!customMethod.isStatic() && cn.name.equals(obfuscatedTargetName))){
						cn.methods.add(customMethod);
						addedMethods++;
						break;
					}
				}
			}
			int addedGetters=0;
			for(MethodNode fieldGetter : fieldGetters.keySet()){
				FieldHook hook = fieldGetters.get(fieldGetter);
				for(AbstractInsnNode insn : fieldGetter.instructions.toArray()){
					if(insn instanceof FieldInsnNode){
						FieldInsnNode fin = (FieldInsnNode)insn;
						FieldInsnNode clone = (FieldInsnNode)fin.clone(null);
						String oldOwner = fin.owner.replace("org/osrs/api/wrappers/proxies/", "");
						String oldName = fin.name;
						FieldHook fh = resolver.getFieldHook(oldOwner, oldName, insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC);
						if(fh==null){
							System.out.println("Failed to find field hook : "+oldOwner+"."+oldName);
						}
						else{
							fh.hooked=true;
						}
						String oldDesc = fin.desc.replace("org/osrs/api/wrappers/", "");
						String newOwner = resolver.getObfuscatedFieldOwner(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
						if(newOwner!=null && !newOwner.equals("null")){
							clone.owner = newOwner;
						}
						else{
							newOwner = resolver.getObfuscatedClassName(oldOwner);
							if(newOwner!=null && !newOwner.equals("null"))
								clone.owner = newOwner;
							else
								clone.owner = fin.owner;
						}
						String newName = resolver.getObfuscatedFieldName(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
						clone.name = oldName;
						if(newName!=null)
							clone.name = newName;
						String newDesc = resolver.getObfuscatedFieldDesc(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
						clone.desc = oldDesc;
						if(newDesc!=null)
							clone.desc = newDesc;
						fieldGetter.instructions.set(insn, clone);
						resolvedInsn++;
						Object multiplier = resolver.getFieldMultiplier(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
						if(multiplier!=null){
							if(oldDesc.equals("I")){
								if(((Integer)multiplier)!=0){
									LdcInsnNode ldc = new LdcInsnNode(multiplier);
									fieldGetter.instructions.insert(clone, ldc);
									fieldGetter.instructions.insert(ldc, new InsnNode(Opcodes.IMUL));
									//System.out.println("Inserted multiplier : I-"+multiplier);
								}
							}
							if(oldDesc.equals("J")){
								if(((Long)multiplier)!=0){
									LdcInsnNode ldc = new LdcInsnNode(multiplier);
									fieldGetter.instructions.insert(clone, ldc);
									fieldGetter.instructions.insert(ldc, new InsnNode(Opcodes.LMUL));
									//System.out.println("Inserted multiplier : L-"+multiplier);
								}
							}
						}
						//System.out.println("Resolved FieldInsn : "+oldOwner+"."+oldName+" "+oldDesc+" : "+newOwner+"."+newName+" "+newDesc);
					}
				}
				boolean isStatic = fieldGetter.isStatic();
				for(AbstractInsnNode insn : fieldGetter.instructions.toArray()){
					if(insn instanceof FieldInsnNode){
						isStatic=insn.getOpcode()==Opcodes.GETSTATIC;
						break;
					}
				}
				for(ClassNode cn : classNodes){
					if((isStatic && cn.name.equals("client")) ||
							(!isStatic && cn.name.equals(hook.owner))){
						cn.methods.add(fieldGetter);
						//if(isStatic)
						//System.out.println("Injected getter : "+cn.name+"."+fieldGetter.name+fieldGetter.desc+" : "+hook.refactoredName);
						addedGetters++;
						break;
					}
				}
			}
			int addedDetours=0;
			for(String target : methodDetours.keySet()){
				String targetOwner = target.substring(0, target.indexOf("."));
				String targetName = target.substring(target.indexOf(".")+1, target.indexOf("("));
				String targetDesc = target.substring(target.indexOf("("), target.length());
				MethodNode methodDetour = methodDetours.get(target);
				for(AbstractInsnNode insn : methodDetour.instructions.toArray()){
					if(insn instanceof FieldInsnNode){
						FieldInsnNode fin = (FieldInsnNode)insn;
						FieldInsnNode clone = (FieldInsnNode)fin.clone(null);
						String oldOwner = fin.owner.replace("org/osrs/api/wrappers/proxies/", "");
						String oldName = fin.name;
						FieldHook fh = resolver.getFieldHook(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
						String newOwner = resolver.getObfuscatedFieldOwner(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
						if(newOwner!=null && !newOwner.equals("null"))
							clone.owner = newOwner;
						else{
							newOwner = resolver.getObfuscatedClassName(oldOwner);
							if(newOwner!=null && !newOwner.equals("null"))
								clone.owner = newOwner;
							else
								clone.owner = fin.owner;
						}
						String newName = resolver.getObfuscatedFieldName(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
						if(newName!=null && !newName.equals("null"))
							clone.name = newName;
						else
							clone.name = oldName;
						String oldDesc = fin.desc.replace("org/osrs/api/wrappers/proxies/", "");
						String newDesc = null;
						if(fh!=null)
							newDesc = fh.dataType;
						else{
							newDesc = oldDesc.startsWith("L")?resolver.getObfuscatedType(oldDesc):oldDesc;
						}
						if(newDesc!=null && !newDesc.equals("null"))
							clone.desc = newDesc;
						else
							clone.desc = oldDesc;
						methodDetour.instructions.set(insn, clone);
						resolvedInsn++;
						//System.out.println("Resolved FieldInsn : "+oldOwner+"."+oldName+" "+oldDesc+" : "+clone.owner+"."+clone.name+" "+clone.desc);
					}
					else if(insn instanceof MethodInsnNode){
						MethodInsnNode min = (MethodInsnNode)insn;
						MethodInsnNode clone = (MethodInsnNode)min.clone(null);
						String oldOwner = min.owner.replace("org/osrs/api/wrappers/proxies/", "");
						String oldName = min.name;
						String oldDesc = min.desc;
						String newOwner = resolver.getObfuscatedMethodOwner(oldOwner, oldName.replace("_", ""), oldDesc, (insn.getOpcode()==Opcodes.INVOKESTATIC));
						if(newOwner!=null && !newOwner.equals("null"))
							clone.owner = newOwner;
						else{
							newOwner = resolver.getObfuscatedClassName(oldOwner);
							if(newOwner!=null && !newOwner.equals("null"))
								clone.owner = newOwner;
							else
								clone.owner = min.owner;
						}
						String newDesc = resolver.getObfuscatedMethodDesc(oldOwner, oldName.replace("_", ""), oldDesc, (insn.getOpcode()==Opcodes.INVOKESTATIC));
						if(newDesc!=null && !newDesc.equals("null"))
							clone.desc = newDesc;
						else{
							clone.desc = oldDesc;
						}
						String newName = resolver.getObfuscatedMethodName(oldOwner, oldName.replace("_", ""), oldDesc, (insn.getOpcode()==Opcodes.INVOKESTATIC));
						if(newName==null && oldOwner.equals("Client"))//static hooks
							newName = resolver.getObfuscatedMethodName(oldOwner, oldName.replace("_", ""), oldDesc, true);
						if(newName!=null && !newName.equals("null"))
							clone.name = newName;
						else
							clone.name = oldName;
						methodDetour.instructions.set(insn, clone);
						resolvedInsn++;
						//System.out.println("Resolved MethodInsn : "+oldOwner+"."+oldName+" "+oldDesc+" : "+clone.owner+"."+clone.name+" "+clone.desc);
					}
				}
				for(ClassNode cn : classNodes){
					if((methodDetour.isStatic() && cn.name.equals("client")) || 
							(!methodDetour.isStatic() && cn.name.equals(targetOwner))){
						cn.methods.add(methodDetour);
						addedDetours++;
					}
				}
			}
			int addedGetterDetours = 0;
			for(String target : fieldGetterDetours.keySet()){
				String oldOwner = target.substring(0, target.indexOf("."));
				String oldName = target.substring(target.indexOf(".")+1, target.length());
				MethodNode detour = fieldGetterDetours.get(target);
				FieldHook hook = resolver.getFieldHook(oldOwner, oldName, detour.isStatic());
				String targetOwner = hook.owner;
				String targetName = hook.obfuscatedName;
				String newOwner = detour.isStatic()?"client":targetOwner;
				String newName = targetName;
				for(AbstractInsnNode insn : detour.instructions.toArray()){
					if(insn instanceof FieldInsnNode){
						FieldInsnNode fin = (FieldInsnNode)insn;
						FieldInsnNode clone = (FieldInsnNode)fin.clone(null);
						String oldDesc = fin.desc.replace("org/osrs/api/wrappers/", "");
						if(newOwner!=null && !newOwner.equals("null")){
							clone.owner = newOwner;
						}
						else{
							newOwner = resolver.getObfuscatedClassName(oldOwner);
							if(newOwner!=null && !newOwner.equals("null"))
								clone.owner = newOwner;
							else
								clone.owner = fin.owner;
						}
						clone.name = oldName;
						if(newName!=null)
							clone.name = newName;
						String newDesc = resolver.getObfuscatedFieldDesc(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
						clone.desc = oldDesc;
						if(newDesc!=null)
							clone.desc = newDesc;
						detour.instructions.set(insn, clone);
						resolvedInsn++;
						Object multiplier = getGetterMultiplier(newOwner, newName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
						if(multiplier!=null){
							if(oldDesc.equals("I")){
								if(((Integer)multiplier)!=0){
									LdcInsnNode ldc = new LdcInsnNode(multiplier);
									detour.instructions.insert(clone, ldc);
									detour.instructions.insert(ldc, new InsnNode(Opcodes.IMUL));
									//System.out.println("Inserted multiplier : I-"+multiplier);
								}
							}
							if(oldDesc.equals("J")){
								if(((Long)multiplier)!=0){
									LdcInsnNode ldc = new LdcInsnNode(multiplier);
									detour.instructions.insert(clone, ldc);
									detour.instructions.insert(ldc, new InsnNode(Opcodes.LMUL));
									//System.out.println("Inserted multiplier : L-"+multiplier);
								}
							}
						}
						//System.out.println("Resolved FieldInsn : "+oldOwner+"."+oldName+" "+oldDesc+" : "+newOwner+"."+newName+" "+newDesc);
					}
				}
				for(ClassNode cn : classNodes){
					if((detour.isStatic() && cn.name.equals("client")) || 
							(!detour.isStatic() && cn.name.equals(targetOwner))){
						cn.methods.add(detour);
						addedGetterDetours++;
						targetGetterDetours.put(targetOwner+"."+targetName, newOwner+"."+detour.name+detour.desc);
						//System.out.println("Injected getter detour : "+targetOwner+" "+targetName+" -> "+newOwner+"."+detour.name+" "+detour.desc);
					}
				}
			}

			int addedSetterDetours = 0;
			for(String target : fieldSetterDetours.keySet()){
				String oldOwner = target.substring(0, target.indexOf("."));
				String oldName = target.substring(target.indexOf(".")+1, target.length());
				MethodNode detour = fieldSetterDetours.get(target);
				FieldHook hook = resolver.getFieldHook(oldOwner, oldName, detour.isStatic());
				String targetOwner = hook.owner;
				String targetName = hook.obfuscatedName;
				String newOwner = detour.isStatic()?"client":targetOwner;
				String newName = detour.name;
				for(AbstractInsnNode insn : detour.instructions.toArray()){
					if(insn instanceof FieldInsnNode){
						FieldInsnNode fin = (FieldInsnNode)insn;
						FieldInsnNode clone = (FieldInsnNode)fin.clone(null);
						oldOwner = fin.owner.replace("org/osrs/api/wrappers/proxies/", "");
						oldName = fin.name;
						String oldDesc = fin.desc.replace("org/osrs/api/wrappers/", "");
						newOwner = resolver.getObfuscatedFieldOwner(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
						if(newOwner!=null && !newOwner.equals("null")){
							clone.owner = newOwner;
						}
						else{
							newOwner = resolver.getObfuscatedClassName(oldOwner);
							if(newOwner!=null && !newOwner.equals("null"))
								clone.owner = newOwner;
							else
								clone.owner = fin.owner;
						}
						newName = resolver.getObfuscatedFieldName(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
						clone.name = oldName;
						if(newName!=null)
							clone.name = newName;
						String newDesc = resolver.getObfuscatedFieldDesc(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
						clone.desc = oldDesc;
						if(newDesc!=null)
							clone.desc = newDesc;
						detour.instructions.set(insn, clone);
						resolvedInsn++;
						Object multiplier = resolver.getFieldMultiplier(newOwner, newName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
						if(multiplier!=null){
							if(oldDesc.equals("I")){
								if(((Integer)multiplier)!=0){
									LdcInsnNode ldc = new LdcInsnNode(multiplier);
									detour.instructions.insertBefore(clone, ldc);
									detour.instructions.insertBefore(clone, new InsnNode(Opcodes.IMUL));
									//System.out.println("Inserted multiplier : I-"+multiplier);
								}
							}
							if(oldDesc.equals("J")){
								if(((Long)multiplier)!=0){
									LdcInsnNode ldc = new LdcInsnNode(multiplier);
									detour.instructions.insertBefore(clone, ldc);
									detour.instructions.insertBefore(clone, new InsnNode(Opcodes.LMUL));
									//System.out.println("Inserted multiplier : L-"+multiplier);
								}
							}
						}
						//System.out.println("Resolved FieldInsn : "+oldOwner+"."+oldName+" "+oldDesc+" : "+newOwner+"."+newName+" "+newDesc);
					}
				}
				newOwner = detour.isStatic()?"client":targetOwner;
				newName = detour.name;
				for(ClassNode cn : classNodes){
					if((detour.isStatic() && cn.name.equals("client")) || 
							(!detour.isStatic() && cn.name.equals(targetOwner))){
						cn.methods.add(detour);
						addedSetterDetours++;
						targetSetterDetours.put(targetOwner+"."+targetName, newOwner+"."+newName+detour.desc);
						//System.out.println("Injected setter detour : "+targetOwner+" "+targetName+" -> "+newOwner+"."+newName+" "+detour.desc);
					}
				}
			}
			
			System.out.println("Modifying client bytecode... please wait...");
			int subclassed=0;
			int currClass=0;
			int lastPercent=-1;
			int currPercent=0;
			for(ClassNode cn : classNodes){
				currClass++;
				currPercent=(int)(100*((currClass*1.0)/classNodes.size()));
				if(currClass==0 || currPercent!=lastPercent){
					if(currPercent>10 && currPercent%10==1)
						System.out.print("\n");
					System.out.print("%"+currPercent+" ");
					lastPercent=currPercent;
					if(currPercent==100)
						System.out.print("\n");
				}
				String refactoredName = resolver.getRefactoredClassName(cn.name);
				ClassHook hook = classMapping.get(refactoredName);
				if(hook!=null){
					cn.interfaces.add("org/osrs/api/wrappers/" + hook.refactoredName);
					subclassed++;
					//System.out.println("Added interface : "+cn.name+" : "+Arrays.toString(cn.interfaces.toArray()));
				}
				for(MethodNode mn : cn.methods){
					if(isBytescript(mn) || mn.name.equals("<init>") || mn.name.equals("<clinit>"))
						continue;
					for(AbstractInsnNode insn : mn.instructions.toArray()){
						if(insn instanceof MethodInsnNode){
							MethodInsnNode min = (MethodInsnNode)insn;
							MethodInsnNode clone = (MethodInsnNode)min.clone(null);
							String owner = min.owner;
							String name = min.name;
							String desc = min.desc;
							if(owner.startsWith("org/bouncycastle/") || owner.startsWith("java/") || owner.startsWith("javax/") || owner.startsWith("netscape/"))
								continue;
							MethodNode detour = methodDetours.get(min.owner+"."+min.name+min.desc);
							MethodHook detourHook = methodDetourHooks.get(min.owner+"."+min.name+min.desc);
							
							String newOwner = owner;
							String newName = name;
							String newDesc = desc;
							if(detour!=null && detourHook!=null){
								if(min.getOpcode()==Opcodes.INVOKESTATIC)
									newOwner = "client";
								newName = detour.name;
								newDesc = detour.desc;
							}
							else{
								ClassNode currOwner = null;
								for(ClassNode clazz : classNodes){
									if(clazz.name.equals(owner)){
										currOwner = clazz;
										break;
									}
								}
								if(currOwner!=null){
									for(ClassNode clazz : classNodes){
										if(currOwner.superName.equals(clazz.name)){
											currOwner = clazz;//Found superclass
											break;
										}
									}
								}
								detour = methodDetours.get(currOwner.name+"."+min.name+min.desc);
								detourHook = methodDetourHooks.get(currOwner.name+"."+min.name+min.desc);
								if(detour!=null && detourHook!=null){
									newOwner = detourHook.owner;
									newName = detour.name;
									newDesc = detour.desc;
									//System.out.println("FOUND SUPERCLASS/SUBCLASS : "+owner+"."+name+desc+" -> "+newOwner+"."+newName+newDesc);
								}
							}
							if(detour!=null && detourHook!=null){
								clone.owner = newOwner;
								clone.name = newName;
								clone.desc = newDesc;
								mn.instructions.set(insn, clone);
								resolvedInsn++;
								//System.out.println("Resolved : "+min.owner+"."+min.name+min.desc+" -> "+newOwner+"."+newName+newDesc);
							}
						}
						else if(insn instanceof FieldInsnNode){
							FieldInsnNode fin = (FieldInsnNode)insn;
							String oldOwner = fin.owner;
							String oldName = fin.name;
							if(fin.getOpcode()==Opcodes.GETSTATIC || fin.getOpcode()==Opcodes.GETFIELD){
								String newInfo = targetGetterDetours.get(oldOwner+"."+oldName);
								if(newInfo==null){//check superclass
									String temp = null;
									ClassNode currOwner = null;
									for(ClassNode clazz : classNodes){
										if(clazz.name.equals(oldOwner)){
											currOwner = clazz;
											break;
										}
									}
									if(currOwner!=null){
										for(ClassNode clazz : classNodes){
											if(currOwner.superName.equals(clazz.name)){
												temp = clazz.name;//Found superclass
												break;
											}
										}
									}
									if(temp!=null){
										oldOwner=temp;
										newInfo = targetGetterDetours.get(oldOwner+"."+oldName);
									}
								}
								if(newInfo!=null){
									String newOwner = newInfo.substring(0, newInfo.indexOf("."));
									String newName = newInfo.substring(newInfo.indexOf(".")+1, newInfo.indexOf("("));
									String desc = newInfo.substring(newInfo.indexOf("("), newInfo.length());
									//System.out.println("Resolved insn : "+oldOwner+"."+oldName+" -> "+newInfo + " "+(fin.getOpcode()==Opcodes.GETSTATIC)+" "+desc);
									mn.instructions.set(insn, new MethodInsnNode(fin.getOpcode()==Opcodes.GETSTATIC?Opcodes.INVOKESTATIC:Opcodes.INVOKEVIRTUAL, newOwner, newName, desc, (fin.getOpcode()==Opcodes.GETSTATIC)));
									resolvedInsn++;
								}
							}
							else if(fin.getOpcode()==Opcodes.PUTFIELD || fin.getOpcode()==Opcodes.PUTSTATIC){
								String newInfo = targetSetterDetours.get(oldOwner+"."+oldName);
								if(newInfo==null){//check superclass
									String temp = null;
									ClassNode currOwner = null;
									for(ClassNode clazz : classNodes){
										if(clazz.name.equals(oldOwner)){
											currOwner = clazz;
											break;
										}
									}
									if(currOwner!=null){
										for(ClassNode clazz : classNodes){
											if(currOwner.superName.equals(clazz.name)){
												temp = clazz.name;//Found superclass
												break;
											}
										}
									}
									if(temp!=null){
										oldOwner=temp;
										newInfo = targetSetterDetours.get(oldOwner+"."+oldName);
									}
								}
								if(newInfo!=null){
									String newOwner = newInfo.substring(0, newInfo.indexOf("."));
									String newName = newInfo.substring(newInfo.indexOf(".")+1, newInfo.indexOf("("));
									String desc = newInfo.substring(newInfo.indexOf("("), newInfo.length());
									//System.out.println("Resolved insn : "+oldOwner+"."+oldName+" -> "+newInfo + " "+(fin.getOpcode()==Opcodes.GETSTATIC)+" "+desc);
									mn.instructions.set(insn, new MethodInsnNode(fin.getOpcode()==Opcodes.PUTSTATIC?Opcodes.INVOKESTATIC:Opcodes.INVOKEVIRTUAL, newOwner, newName, desc, (fin.getOpcode()==Opcodes.GETSTATIC)));
									resolvedInsn++;
								}
							}
						}
					}
				}
			}
			System.out.println("Done injecting...");
			System.out.println("Subclassed "+subclassed+"/"+classMapping.size()+" classes.");
			System.out.println("Injected "+addedClasses+"/"+customClasses.size()+" custom classes.");
			System.out.println("Injected "+addedFields+"/"+customFields.size()+" custom fields.");
			System.out.println("Injected "+addedMethods+"/"+customMethods.size()+" custom methods.");
			System.out.println("Injected "+addedGetters+"/"+fieldGetters.size()+" field getters.");
			System.out.println("Injected "+addedGetterDetours+"/"+fieldGetterDetours.size()+" field get detours.");
			System.out.println("Injected "+addedSetterDetours+"/"+fieldSetterDetours.size()+" field set detours.");
			System.out.println("Injected "+addedDetours+"/"+methodDetours.size()+" method detours.");
			System.out.println("Resolved "+resolvedInsn+" instructions.");
			
			//TODO remove for release
			dumpClasses("injected.jar", classNodes.toArray(new ClassNode[]{}));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public boolean containsAnnotation(MethodNode method, String annotation){
		if(method!=null){
			if(method.visibleAnnotations==null)
				return false;
			for(AnnotationNode an : method.visibleAnnotations){
				if(an.desc.contains(annotation))
					return true;
			}
		}
		return false;
	}
	public String getAnnotation(ClassNode clazz){
		if(clazz!=null && clazz.visibleAnnotations!=null){
			for(AnnotationNode an : clazz.visibleAnnotations){
				if(an.values.get(0).equals("name"))
					//System.out.println(clazz.name+":"+an.values.get(1).toString());
					return an.values.get(1).toString();
			}
		}
		return null;
	}
	public String getAnnotation(MethodNode method){
		if(method!=null && method.visibleAnnotations!=null){
			for(AnnotationNode an : method.visibleAnnotations){
				if(an.values.get(0).equals("name"))
					//System.out.println(clazz.name+":"+an.values.get(1).toString());
					return an.values.get(1).toString();
			}
		}
		return null;
	}
	public boolean hasAnnotation(FieldNode field, String annotation){
		if(field!=null){
			if(field.visibleAnnotations==null)
				return false;
			for(AnnotationNode an : field.visibleAnnotations){
				if(an.desc.equals(annotation))
					return true;
			}
		}
		return false;
	}
	public boolean hasAnnotation(ClassNode clazz, String annotation){
		if(clazz!=null){
			if(clazz.visibleAnnotations==null)
				return false;
			for(AnnotationNode an : clazz.visibleAnnotations){
				if(an.desc.equals(annotation))
					return true;
			}
		}
		return false;
	}
	public boolean hasAnnotation(MethodNode method, String annotation){
		if(method!=null){
			if(method.visibleAnnotations==null)
				return false;
			for(AnnotationNode an : method.visibleAnnotations){
				if(an.desc.equals(annotation))
					return true;
			}
		}
		return false;
	}
	public boolean isBytescript(ClassNode clazz){
		if(hasAnnotation(clazz, "Lorg/osrs/injection/bytescript/BCustomClass;"))
			return true;
		return false;
	}
	public boolean isBytescript(MethodNode method){
		if(hasAnnotation(method, "Lorg/osrs/injection/bytescript/BMethod;") ||
				hasAnnotation(method, "Lorg/osrs/injection/bytescript/BDetour;") ||
				hasAnnotation(method, "Lorg/osrs/injection/bytescript/BFunction;") ||
				hasAnnotation(method, "Lorg/osrs/injection/bytescript/BGetter;") ||
				hasAnnotation(method, "Lorg/osrs/injection/bytescript/BGetterDetour;") ||
				hasAnnotation(method, "Lorg/osrs/injection/bytescript/BSetterDetour;"))
			return true;
		return false;
	}
	public boolean isBytescript(FieldNode field){
		if(hasAnnotation(field, "Lorg/osrs/injection/bytescript/BVar;") ||
				hasAnnotation(field, "Lorg/osrs/injection/bytescript/BField;"))
			return true;
		return false;
	}
	protected String[] parseArguments(String desc){
		ArrayList<String> args = new ArrayList<String>();
		desc = desc.substring(desc.indexOf("(")+1, desc.indexOf(")"));
		while(desc!=null && !desc.equals("") && desc.length()>0){
			if(desc.charAt(0)=='B' || desc.charAt(0)=='C' || desc.charAt(0)=='D' || desc.charAt(0)=='F' || desc.charAt(0)=='I' || desc.charAt(0)=='J' || desc.charAt(0)=='S' || desc.charAt(0)=='Z'){
				args.add(desc.charAt(0)+"");
				desc=desc.substring(1, desc.length());
			}
			else if(desc.startsWith("[")){
				String arg = "[";
				desc=desc.substring(1, desc.length());
				while(desc.startsWith("[")){
					arg+="[";
					desc=desc.substring(1, desc.length());
				}
				if(desc.charAt(0)=='B' || desc.charAt(0)=='C' || desc.charAt(0)=='D' || desc.charAt(0)=='F' || desc.charAt(0)=='I' || desc.charAt(0)=='J' || desc.charAt(0)=='S' || desc.charAt(0)=='Z'){
					args.add(arg+desc.charAt(0)+"");
					desc=desc.substring(1, desc.length());
				}
				else{
					args.add(arg+desc.substring(0, desc.indexOf(";")+1));
					desc=desc.substring(desc.indexOf(";")+1, desc.length());
				}
			}
			else{
				args.add(desc.substring(0, desc.indexOf(";")+1));
				desc=desc.substring(desc.indexOf(";")+1, desc.length());
			}
		}
		return args.toArray(new String[]{});
	}
	private static void dumpClasses(final String jarName, final ClassNode[] classes) {
		try {
			final File file = new File(jarName);
			final JarOutputStream out = new JarOutputStream(new FileOutputStream(file));
			for (final ClassNode node : classes) {
				final JarEntry entry = new JarEntry(node.name + ".class");
				out.putNextEntry(entry);
				final ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
				//System.out.println(node.name);
				node.accept(writer);
				out.write(writer.toByteArray());
			}
			out.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public Object getMethodPredicate(String owner, String name, String wildcardDesc, boolean isStatic){
		MethodHook mh = resolver.getMethodHook(owner, name, wildcardDesc, isStatic);
		Object predicate = -1;
		if(mh!=null){
			if(mh.desc.contains("I)")){
				predicate = (int)mh.predicate;
			}
			else if(mh.desc.contains("B)")){
				predicate = (byte)mh.predicate;
			}
			else if(mh.desc.contains("S)")){
				predicate = (short)mh.predicate;
			}
		}
		return predicate;
	}
	public Object getGetterMultiplier(String owner, String name, boolean isStatic){
		FieldHook fh = resolver.getFieldHook(owner, name, isStatic);
		if(fh!=null)
			return fh.dataType.equals("I")?(int)fh.multiplier:(long)fh.multiplier;
		return 1;
	}
	public Object getSetterMultiplier(String owner, String name, boolean isStatic){
		FieldHook fh = resolver.getFieldHook(owner, name, isStatic);
		if(fh!=null){
			if(fh.dataType.equals("I")){
				int odd = (int)fh.multiplier;
				int i = (3 * odd) ^ 2;
			    i *= 2 - odd * i;
			    i *= 2 - odd * i;
			    return (i * (2 - odd * i));
			}
			else if(fh.dataType.equals("J")){
				long odd = (long)fh.multiplier;
			    long l = (3L * odd) ^ 2L;
			    l *= 2L - odd * l;
			    l *= 2L - odd * l;
			    l *= 2L - odd * l;
			    return (l * (2L - odd * l));
			}
		}
		return 1;
	}
}
