package org.osrs.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.osrs.api.wrappers.proxies.GameShell;
import org.osrs.injection.ClassHook;
import org.osrs.injection.FieldHook;
import org.osrs.injection.MethodHook;
import org.osrs.loader.injector.BSAdapter;
import org.osrs.loader.injector.BSRemapper;

public class Compiler {
	private ArrayList<ClassNode> classNodes;
	private ArrayList<ClassNode> proxyClasses;
	public Modscript modscriptData;
	
	public HashMap<String, ClassHook> classMapping;
	public HashMap<String, FieldHook> fieldMapping;
	public HashMap<String, MethodHook> methodMapping;
	
	public HashMap<String, ClassNode> customClasses;
	public HashMap<String, FieldNode> customFields;
	public HashMap<String, MethodNode> customMethods;

	public HashMap<String, MethodNode> fieldGetters;
	public HashMap<String, FieldHook> fieldGetterDetours;
	public HashMap<String, FieldHook> fieldSetterDetours;
	public HashMap<String, MethodNode> methodDetours;
	public Compiler(ArrayList<ClassNode> client, Modscript modscript){
		classNodes = client;
		modscriptData = modscript;
		proxyClasses = new ArrayList<ClassNode>();
		classMapping = new HashMap<String, ClassHook>();
		fieldMapping = new HashMap<String, FieldHook>();
		methodMapping = new HashMap<String, MethodHook>();
		customClasses = new HashMap<String, ClassNode>();
		customFields = new HashMap<String, FieldNode>();
		customMethods = new HashMap<String, MethodNode>();
		fieldGetters = new HashMap<String, MethodNode>();
		fieldGetterDetours = new HashMap<String, FieldHook>();
		fieldSetterDetours = new HashMap<String, FieldHook>();
		methodDetours = new HashMap<String, MethodNode>();
	}
	public void remapInstructions(){
		try{
			BSRemapper remapper = new BSRemapper(this);
			ArrayList<ClassNode> newNodes = new ArrayList<ClassNode>();
			for(ClassNode cn : classNodes){
				try{
					ClassNode newClass = new ClassNode();
					BSAdapter adapter = new BSAdapter(newClass, remapper);
					adapter.linkCompiler(this);
					cn.accept(adapter);
					ClassHook hook = modscriptData.resolver.getObfusctedClassHook(cn.name);
					if(hook!=null && classMapping.containsKey(hook.refactoredName)){
						newClass.interfaces.add("org/osrs/api/wrappers/" + hook.refactoredName);
					}
					newNodes.add(newClass);
				}
				catch(Exception e){
					e.printStackTrace();
					System.out.println("Failed to compile class : "+cn.name);
				}
			}
			classNodes.clear();
			for(ClassNode cn : newNodes)
				classNodes.add(cn);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void prepInputListeners(){
		//Prepping MouseListener and Canvas
		String keyboardName = modscriptData.resolver.getObfuscatedClassName("KeyboardListener");
		String mouseName = modscriptData.resolver.getObfuscatedClassName("MouseListener");
		String mouseWheelName = modscriptData.resolver.getObfuscatedClassName("MouseWheelListener");
		String canvasName = modscriptData.resolver.getObfuscatedClassName("Canvas");
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
		for(ClassHook ch : modscriptData.classHooks){
			/** DO NOT REMOVE OR MODIFY - part of MouseListener setup**/
			if(ch.refactoredName.equals("MouseListener")){
				ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mouseDragged0", "mouseDragged0", "(Ljava/awt/event/MouseEvent;)V", -1));
				ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mouseEntered0", "mouseEntered0", "(Ljava/awt/event/MouseEvent;)V", -1));
				ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mouseExited0", "mouseExited0", "(Ljava/awt/event/MouseEvent;)V", -1));
				ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mousePressed0", "mousePressed0", "(Ljava/awt/event/MouseEvent;)V", -1));
				ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mouseReleased0", "mouseReleased0", "(Ljava/awt/event/MouseEvent;)V", -1));
				ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mouseClicked0", "mouseClicked0", "(Ljava/awt/event/MouseEvent;)V", -1));
				ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mouseMoved0", "mouseMoved0", "(Ljava/awt/event/MouseEvent;)V", -1));
			}
			/** DO NOT REMOVE OR MODIFY - part of MouseWheelListener setup**/
			if(ch.refactoredName.equals("MouseWheelListener")){
				ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "mouseWheelMoved0", "mouseWheelMoved0", "(Ljava/awt/event/MouseWheelEvent;)V", -1));
			}
			/** DO NOT REMOVE OR MODIFY - part of KeyboardListener setup**/
			else if(ch.refactoredName.equals("KeyboardListener")){
				ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "keyPressed0", "keyPressed0", "(Ljava/awt/event/KeyEvent;)V", -1));
				ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "keyReleased0", "keyReleased0", "(Ljava/awt/event/KeyEvent;)V", -1));
				ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "keyTyped0", "keyTyped0", "(Ljava/awt/event/KeyEvent;)V", -1));
			}
			/** DO NOT REMOVE OR MODIFY - part of Canvas setup**/
			else if(ch.refactoredName.equals("Canvas")){
				ch.methodHooks.add(new MethodHook(ch.obfuscatedName, "paint0", "paint0", "(Ljava/awt/Graphics;)V", -1));
			}
		}
	}
	public void parseModClasses(){
		System.out.println("Parsing "+proxyClasses.size()+" mod classes...");
		for(ClassNode cn : proxyClasses){
			String sourceName = cn.name;
			if(hasAnnotation(cn, "Lorg/osrs/injection/bytescript/BClass;")){
				sourceName = cn.name.replace("org/osrs/api/wrappers/proxies/", "");
				ClassHook ch = modscriptData.resolver.getClassHook(sourceName);
				if(ch!=null){
					classMapping.put(sourceName, ch);
					System.out.println("Loaded BClass : "+sourceName);
					ClassNode clientNode = getClientClassNode(ch.obfuscatedName);
					if(clientNode!=null){
						for(FieldNode fn : cn.fields){
							if(hasAnnotation(fn, "Lorg/osrs/injection/bytescript/BVar;")){
								if(fn.desc.contains("org/osrs/api/wrappers/proxies/"))
									fn.desc = modscriptData.resolver.getObfuscatedType(fn.desc.replace("org/osrs/api/wrappers/proxies/", ""));
								clientNode.fields.add(fn);
								customFields.put(clientNode.name+"."+fn.name, fn);
							}
							else if(hasAnnotation(fn, "Lorg/osrs/injection/bytescript/BField;")){
								FieldHook fh = modscriptData.resolver.getFieldHook(sourceName, fn.name, fn.isStatic());
								if(fh!=null){
									fieldMapping.put(ch.obfuscatedName+"."+fh.obfuscatedName, fh);
								}
								else{
									System.out.println("Failed to load FieldHook : "+sourceName+"."+fn.name+" : "+(fn.isStatic()?"static":"nonstatic"));
								}
							}
						}
						for(MethodNode mn : cn.methods){
							String desc = mn.desc;
							String[] oldParams = parseArguments(desc);
							String[] newParams = new String[oldParams.length];
							for(int i=0;i<oldParams.length;++i){
								String s = oldParams[i];
								if(s.startsWith("Lorg/osrs/api/wrappers/proxies/")){
									s = modscriptData.resolver.getObfuscatedType(s.replace("org/osrs/api/wrappers/proxies/", ""));
								}
								newParams[i] = s;
							}
							String oldReturnType = desc.substring(desc.indexOf(")")+1, desc.length());
							String newReturnType = modscriptData.resolver.getObfuscatedType(oldReturnType);
							String newDesc = "(";
							for(String s : newParams)
								newDesc+=s;
							newDesc+=")"+newReturnType;
							mn.desc = newDesc;
							
							if(hasAnnotation(mn, "Lorg/osrs/injection/bytescript/BFunction;")){
								clientNode.methods.add(mn);
								customMethods.put(sourceName+"."+mn.name+mn.desc, mn);
							}
							else if(hasAnnotation(mn, "Lorg/osrs/injection/bytescript/BMethod;")){
								MethodHook mh = modscriptData.resolver.getMethodHook(sourceName, mn.name.replace("_", ""), mn.desc, mn.isStatic());
								if(mh!=null && mh.desc.charAt(mh.desc.indexOf(")")-1)==mn.desc.charAt(mn.desc.indexOf(")")-1)){
									methodMapping.put(sourceName+"."+mh.refactoredName+mh.desc, mh);
								}
								else{
									System.out.println("Failed to load MethodHook : "+sourceName+"."+mn.name.replace("_", "")+mn.desc+" : "+(mn.isStatic()?"static":"nonstatic"));
								}
							}
							else if(hasAnnotation(mn, "Lorg/osrs/injection/bytescript/BDetour;")){
								MethodHook mh = modscriptData.resolver.getMethodHook(sourceName, mn.name, mn.desc, mn.isStatic());
								if(mh!=null && mh.desc.charAt(mh.desc.indexOf(")")-1)==mn.desc.charAt(mn.desc.indexOf(")")-1)){
									clientNode.methods.add(mn);
									methodDetours.put(mh.owner+"."+mh.obfuscatedName+mh.desc, mn);
								}
							}
							else if(hasAnnotation(mn, "Lorg/osrs/injection/bytescript/BGetter;")){
								boolean isStatic = false;
								for(AbstractInsnNode insn : mn.instructions.toArray()){
									if(insn.getOpcode()==Opcodes.GETSTATIC)
										isStatic=true;
								}
								FieldHook fh2 = modscriptData.resolver.getFieldHook(ch.refactoredName, mn.name, isStatic);
								if(fh2!=null){
									for(AbstractInsnNode insn : mn.instructions.toArray()){
										if(insn instanceof FieldInsnNode){
											FieldInsnNode fin = (FieldInsnNode)insn;
											FieldInsnNode clone = (FieldInsnNode)fin.clone(null);
											String oldOwner = fin.owner.replace("org/osrs/api/wrappers/proxies/", "");
											String oldName = fin.name;
											String oldDesc = fin.desc.replace("org/osrs/api/wrappers/proxies/", "");
											String newOwner = modscriptData.resolver.getObfuscatedFieldOwner(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
											if(newOwner!=null && !newOwner.equals("null")){
												clone.owner = newOwner;
											}
											else{
												newOwner = modscriptData.resolver.getObfuscatedClassName(oldOwner);
												if(newOwner!=null && !newOwner.equals("null"))
													clone.owner = newOwner;
												else
													clone.owner = fin.owner;
											}
											String newName = modscriptData.resolver.getObfuscatedFieldName(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
											clone.name = oldName;
											if(newName!=null)
												clone.name = newName;
											String newDesc2 = modscriptData.resolver.getObfuscatedFieldDesc(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
											clone.desc = oldDesc;
											if(newDesc2!=null)
												clone.desc = newDesc2;
											mn.instructions.set(insn, clone);
											Object multiplier = modscriptData.resolver.getFieldMultiplier(oldOwner, oldName, (insn.getOpcode()==Opcodes.GETSTATIC||insn.getOpcode()==Opcodes.PUTSTATIC));
											if(multiplier!=null){
												if(oldDesc.equals("I")){
													if(((Integer)multiplier)!=0){
														LdcInsnNode ldc = new LdcInsnNode(multiplier);
														mn.instructions.insert(clone, ldc);
														mn.instructions.insert(ldc, new InsnNode(Opcodes.IMUL));
													}
												}
												if(oldDesc.equals("J")){
													if(((Long)multiplier)!=0){
														LdcInsnNode ldc = new LdcInsnNode(multiplier);
														mn.instructions.insert(clone, ldc);
														mn.instructions.insert(ldc, new InsnNode(Opcodes.LMUL));
													}
												}
											}
										}
									}
									clientNode.methods.add(mn);
								}
							}
							else if(hasAnnotation(mn, "Lorg/osrs/injection/bytescript/BGetterDetour;")){
								FieldHook fh = modscriptData.resolver.getFieldHook(sourceName, mn.name.replace("get_", ""), mn.isStatic());
								if(fh!=null){
									mn.desc = "()"+fh.dataType;
									clientNode.methods.add(mn);
									fieldGetterDetours.put(fh.owner+"."+fh.obfuscatedName, fh);
								}
							}
							else if(hasAnnotation(mn, "Lorg/osrs/injection/bytescript/BSetterDetour;")){
								FieldHook fh = modscriptData.resolver.getFieldHook(sourceName, mn.name.replace("set_", ""), mn.isStatic());
								if(fh!=null){
									mn.desc = "("+fh.dataType+")V";
									clientNode.methods.add(mn);
									fieldSetterDetours.put(fh.owner+"."+fh.obfuscatedName, fh);
								}
							}
							
						}
					}
				}
				else{
					System.out.println("Failed to load ClassHook : "+sourceName);
				}
			}
			else if(hasAnnotation(cn, "Lorg/osrs/injection/bytescript/BCustomClass;")){
				sourceName = cn.name.replace("org/osrs/api/wrappers/proxies/", "");
				cn.name = sourceName;
				customClasses.put(sourceName, cn);
				classNodes.add(cn);//add to client
				for(FieldNode fn : cn.fields){
					customFields.put(sourceName+"."+fn.name, fn);
				}
				for(MethodNode mn : cn.methods){
					customMethods.put(sourceName+"."+mn.name+mn.desc, mn);
				}
			}
		}
		System.out.println("Loaded "+customClasses.size()+" custom classes and "+classMapping.size()+" class mappings.");
	}
	public void loadModFiles(){
		try{
			System.out.println("Compiling mods...");
			Package pack = GameShell.class.getPackage();
			URL root = GameShell.class.getClassLoader().getResource(pack.getName().replace(".", "/"));
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
					cr.accept(cn, ClassReader.EXPAND_FRAMES);
					proxyClasses.add(cn);
				}
				jar.close();
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
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private ClassNode getClientClassNode(String obfuscatedName){
		for(ClassNode cn : classNodes){
			if(cn.name.equals(obfuscatedName))
				return cn;
		}
		return null;
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
					return an.values.get(1).toString();
			}
		}
		return null;
	}
	public String getAnnotation(MethodNode method){
		if(method!=null && method.visibleAnnotations!=null){
			for(AnnotationNode an : method.visibleAnnotations){
				if(an.values.get(0).equals("name"))
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
}
