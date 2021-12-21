package org.osrs.loader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
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
		Compiler compiler = new Compiler(classNodes, this);
		compiler.prepInputListeners();
		compiler.loadModFiles();
		compiler.parseModClasses();
		compiler.remapInstructions();

		dumpClasses("injected.jar", classNodes);
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
	public void dumpClasses(final String jarName, ArrayList<ClassNode> classes) {
		try {
			//First output the bytecode to a jar, so that the ASM ClassLoader properly 
			//will load Class<?> instances when computing frames.
			final File file = new File(jarName);
			final JarOutputStream out = new JarOutputStream(new FileOutputStream(file));
			for (int i=0;i<classes.size();i++){
				ClassNode node = classes.get(i);
				if(node==null)
					continue;
				final JarEntry entry = new JarEntry(node.name + ".class");
				out.putNextEntry(entry);
				final ClassRebuilder writer = new ClassRebuilder(ClassWriter.COMPUTE_FRAMES);
				writer.setClient(classes.toArray(new ClassNode[]{}));
				node.accept(writer);
				byte[] bytes = writer.toByteArray();
				out.write(bytes);

				ClassNode newNode = new ClassNode();
				ClassReader reader = new ClassReader(bytes);
				reader.accept(newNode, ClassReader.EXPAND_FRAMES);
				classes.set(i, newNode);
			}
			out.close();

		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This is part of a fix for those pesky little stackmap frame verify errors.
	 * Rebuilding the frames requires ASM to load the classes, but we
	 * dont want that, we want our modified classes to be loaded if possible.
	 * @author Marneus901
	 */
	private class ClassRebuilder extends ClassWriter{
		private ClassNode[] classes=null;
		public ClassRebuilder(int flags) {
			super(flags);
		}
		public ClassRebuilder(final ClassReader classReader, final int flags) {
			super(classReader, flags);
		}
		public void setClient(ClassNode[] nodes){
			classes = nodes;
		}
		public ClassNode getClassNode(String name){
			if(classes!=null){
				for(ClassNode cn : classes){
					if(cn.name.equals(name))
						return cn;
				}
			}
			return null;
		}
		@Override
		protected String getCommonSuperClass(String type1, String type2) {
			//System.out.println("Finding common super for "+type1+":"+type2);
			ClassNode type1Node = getClassNode(type1);
			ClassNode type2Node = getClassNode(type2);
			ClassNode lastType1Super = null;
			ClassNode lastType2Super = null;
			if(type1Node!=null || type2Node!=null){
				ArrayList<String> type1Supers = new ArrayList<String>();
				ClassNode superNode = null;
				if(type1Node!=null){
					superNode = getClassNode(type1Node.name);
					while(superNode!=null){
						type1Supers.add(superNode.name);
						ClassNode parent = getClassNode(superNode.superName);
						if(parent==null){
							lastType1Super = superNode;
							break;
						}
						superNode = parent;
					}
				}
				if(type2Node!=null){
					superNode = getClassNode(type2Node.name);
					while(superNode!=null){
						if(type1Supers.contains(superNode.name))
							return superNode.name;
						ClassNode parent = getClassNode(superNode.superName);
						if(parent==null){
							lastType2Super = superNode;
							break;
						}
						superNode = parent;
					}
				}
				String str1 = lastType1Super!=null?lastType1Super.superName:type1;
				String str2 = lastType2Super!=null?lastType2Super.superName:type2;
				if(str1.equals(str2))
					return str1;
				if(str1.equals("java/lang/Object") || str2.equals("java/lang/Object"))
					return "java/lang/Object";
				type1=str1;
				type2=str2;
				//System.out.println("Checking : "+type1+":"+type2+" -> "+(lastType1Super!=null?lastType1Super.superName:type1)+":"+(lastType2Super!=null?lastType2Super.superName:type2));
			}
			return super.getCommonSuperClass(type1, type2);
		}
	}
	public Object getMethodPredicate(String owner, String name, String wildcardDesc, boolean isStatic){
		MethodHook mh = resolver.getMethodHook(owner, name, wildcardDesc, isStatic);
		Object predicate = -1;
		if(mh!=null){
			predicate = mh.predicate;
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
