package org.osrs.loader;

import java.awt.AWTPermission;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilePermission;
import java.net.SocketPermission;
import java.net.URL;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.PropertyPermission;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

public class RSClassLoader extends ClassLoader{
	public Hashtable<String, Class<?>> classes = new Hashtable<String, Class<?>>();//public for reflection analyzer
	private HashMap<String, byte[]> classBytes = new HashMap<String, byte[]>();
	private ProtectionDomain domain;
    private String directoryPath = "";
	public RSClassLoader(File file, ArrayList<ClassNode> classes, String codebase){
		super(RSClassLoader.class.getClassLoader());
		System.out.println("Initialized ClassLoader!");
		directoryPath = file.getPath().substring(0, file.getPath().lastIndexOf("\\")+1);
		try {
			CodeSource codeSource = new CodeSource(new URL(codebase), (CodeSigner[]) null);
			domain = new ProtectionDomain(codeSource, getPermissions());
		} catch (@SuppressWarnings("unused") Exception e) {
			domain = new ProtectionDomain(null, getPermissions());
		}
		try {
			for(ClassNode cn : classes){
				ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
	    		cn.accept(writer);
	    		cn.visitEnd();
	    		byte[] data = writer.toByteArray();
	    		classBytes.put(cn.name+".class", data);
	    		//System.out.println("Loaded : "+cn.name+" "+data.length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private Permissions getPermissions() {
		final Permissions ps = new Permissions();
		ps.add(new AWTPermission("accessEventQueue"));
		ps.add(new PropertyPermission("user.home", "read"));
		ps.add(new PropertyPermission("java.vendor", "read"));
		ps.add(new PropertyPermission("java.version", "read"));
		ps.add(new PropertyPermission("os.name", "read"));
		ps.add(new PropertyPermission("os.arch", "read"));
		ps.add(new PropertyPermission("os.version", "read"));
		ps.add(new SocketPermission("*", "connect,resolve"));
		String uDir = System.getProperty("user.home");
		if (uDir != null) {
			uDir += "/";
		} else {
			uDir = "~/";
		}
		final String[] dirs = {"c:/rscache/", "/rscache/", "c:/windows/", "c:/winnt/", "c:/", uDir, "/tmp/", "."};
		final String[] rsDirs = {".jagex_cache_32", ".file_store_32"};
		for (String dir : dirs) {
			final File f = new File(dir);
			ps.add(new FilePermission(dir, "read"));
			if (!f.exists()) {
				continue;
			}
			dir = f.getPath();
			for (final String rsDir : rsDirs) {
				ps.add(new FilePermission(dir + File.separator + rsDir + File.separator + "-", "read"));
				ps.add(new FilePermission(dir + File.separator + rsDir + File.separator + "-", "write"));
			}
		}
		Calendar.getInstance();
		ps.setReadOnly();
		return ps;
	}
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		try{
			String entryName = name.replace('.', '/') + ".class";
			if(classes.containsKey(name))
				return classes.get(name);
			if(this.classBytes.containsKey(entryName)){
				byte[] buf = this.classBytes.get(entryName);
				Class<?> clazz = defineClass(name, buf, 0, buf.length, domain);
				if(clazz!=null){
					classes.put(name, clazz);
					return clazz;
				}
			}
	    	File temp = new File(directoryPath+name.replace(".", "\\")+".class");
	    	if(temp.exists()){
				FileInputStream stream = new FileInputStream(temp);
				byte[] bytes = new byte[stream.available()];
				stream.read(bytes);
				stream.close();
				classBytes.put(name, bytes);
				byte[] buf = this.classBytes.get(entryName);
				Class<?> clazz = defineClass(name, buf, 0, buf.length, null);
				if(clazz!=null){
					classes.put(entryName, clazz);
					return clazz;
				}
	    	}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		catch(Throwable t){
			t.printStackTrace();
		}
	    try
	    {
	    	return this.getClass().getClassLoader().loadClass(name);
	    }
	    catch (@SuppressWarnings("unused") Exception e) {
	    } catch (@SuppressWarnings("unused") Throwable t) {
	    }
		return super.loadClass(name);
	}
}
