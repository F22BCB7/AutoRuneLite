/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* Dynamac's Dynamic ClassLoader.                       *
* Will not cache loaded classes to the system.         *
********************************************************/
package org.osrs.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.osrs.util.Data;

public class ScriptLoader extends java.lang.ClassLoader{
	private HashMap<String, Class<?>> classes = new HashMap<String, Class<?>>();
    private HashMap<String, byte[]> classBytes = new HashMap<String, byte[]>();
    private String fileName = "";
    private String directoryPath = "";
    public ScriptLoader(File file) {
    	super(ScriptLoader.class.getClassLoader());
    	fileName = file.getName();
		directoryPath = file.getAbsolutePath().substring(0, file.getPath().lastIndexOf(File.separator)+1);
    	if(fileName.endsWith(".class")){
    		File directory = new File(directoryPath);
    		for(File f : directory.listFiles()){
    	    	if(f.getName().endsWith(".class")){
					try {
						FileInputStream stream = new FileInputStream(f);
						byte[] bytes = new byte[stream.available()];
						stream.read(bytes);
						stream.close();
						classBytes.put(f.getName(), bytes);
					} catch (Exception e) {
						e.printStackTrace();
					}
    	    	}
    		}
    	}
        if (fileName.endsWith(".jar")) {
            try {
                JarFile jar = new JarFile(file);
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().endsWith(".class")) {
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
        				classBytes.put(entry.getName(), allByteData);
                    }
                }
                jar.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
		String entry = name.replace('.', '/') + ".class";
	    try
	    {
	    	if(classes.containsKey(entry))
	    		return classes.get(entry);
	    	if(this.classBytes.containsKey(entry)){
				byte[] buf = this.classBytes.get(entry);
				Class<?> clazz = defineClass(name, buf, 0, buf.length, null);
				if(clazz!=null){
					classes.put(entry, clazz);
					return clazz;
				}
	    	}
	    	if(Data.scriptClassloader!=null){
	    		Class<?> cl = Data.scriptClassloader.loadClass(name);
	    		if(cl!=null)
	    			return cl;
	    	}
	    	File temp = new File(directoryPath+name.replace(".", "\\")+".class");
	    	if(temp.exists()){
				FileInputStream stream = new FileInputStream(temp);
				byte[] bytes = new byte[stream.available()];
				stream.read(bytes);
				stream.close();
				classBytes.put(name, bytes);
				byte[] buf = this.classBytes.get(entry);
				Class<?> clazz = defineClass(name, buf, 0, buf.length, null);
				if(clazz!=null){
					classes.put(entry, clazz);
					return clazz;
				}
	    	}
		    //if(Data.LOADERSCRIPT_CLASSLOADER!=null && Data.LOADERSCRIPT_CLASSLOADER!=this)
		    	//return Data.LOADERSCRIPT_CLASSLOADER.loadClass(name);
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    } catch (Throwable t) {
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
