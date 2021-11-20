import java.applet.Applet;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.SwingUtilities;

import org.objectweb.asm.Assembly;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Mask;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.osrs.api.methods.MethodContext;
import org.osrs.api.wrappers.Client;
import org.osrs.input.InputManager;
import org.osrs.loader.AppletFrame;
import org.osrs.loader.JarDownloader;
import org.osrs.loader.Modscript;
import org.osrs.loader.PageParser;
import org.osrs.loader.RSClassLoader;
import org.osrs.util.Data;

public class Boot {
	public static void main(String[] args) {
		System.out.println("Booting AutoRuneLite...");
		try{
			int revision=-1;
			ArrayList<ClassNode> classNodes = new ArrayList<ClassNode>();
			PageParser parser = new PageParser();
			JarDownloader downloader = new JarDownloader(parser.jarLocation);
			if(downloader.downloaded){
				File file = new File("runescape.jar");
				JarFile jar = new JarFile(file);
				Enumeration<?> enumeration = jar.entries();
				System.out.println("Loading jar file entries : "+jar.size());
				while (enumeration.hasMoreElements()) {
					JarEntry entry = (JarEntry) enumeration.nextElement();
					if (entry.getName().endsWith(".class")) {
						ClassReader classReader = new ClassReader(jar.getInputStream(entry));
						ClassNode classNode = new ClassNode();
						classReader.accept(classNode, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
						classNodes.add(classNode);
						if(classNode.name.equals("client")){
							System.out.println("GameShell : "+classNode.superName);
							for (MethodNode mn : classNode.methods) {
								if (mn.name.equals("init")) {
									List<AbstractInsnNode> pattern = Assembly.find(mn,
										Mask.SIPUSH.operand(765),//Applet width
										Mask.SIPUSH.operand(503),//Applet height
										//Client version
										//Dummy parameter
										Mask.INVOKEVIRTUAL.distance(3)//initializeApplet
									);
									if (pattern != null) {
										IntInsnNode appHeight = (IntInsnNode)pattern.get(1);
										AbstractInsnNode clientVersion = appHeight.getNext();
										if(clientVersion instanceof IntInsnNode){
											revision = ((IntInsnNode)clientVersion).operand;
											break;
										}
									}
									break;
								}
							}
						}
					}
				}
				jar.close();
				if(revision!=-1){
					System.out.println("Loaded client r"+revision+"; "+classNodes.size()+" classes.");
					Modscript modscript = new Modscript();
					modscript.loadModscript();
					Data.clientModscript = modscript;
					System.out.println("Revisions : "+revision+":"+modscript.modscriptRevision);
					if(modscript.modscriptRevision==revision){
						//Run injection here; on-the-fly
						//Doesn't modify the File on Disk
						//But modifies the classes before loaded by the ClassLoader
						long start=System.currentTimeMillis();
						modscript.runInjection(classNodes);
						long end = System.currentTimeMillis();
						System.out.println("Injection ran for "+(end-start)+"ms");
						
						RSClassLoader classLoader = new RSClassLoader(file, classNodes, parser.parameters.get("codebase"));
						Class<?> client = classLoader.loadClass("client");
						if(client!=null){
							System.out.println("Loaded client class!");
							Object instance = client.newInstance();
							Data.clientInstance = instance;
							MethodContext context = new MethodContext(instance);
							Client clientInstance =  ((org.osrs.api.wrappers.Client)instance);
							clientInstance.setMethodContext(context);
							SwingUtilities.invokeLater(() -> {
								AppletFrame frame = new AppletFrame((Applet)instance, parser);
								frame.setVisible(true);
							});
							Data.inputManager = new InputManager(clientInstance);
						}
					}
					else{
						System.out.println("Modscript out of date! Please update!");
					}
				}
				else{
					System.err.println("Failed to determine build revision!");
				}
			}
		}
		catch(Exception e){
			e.toString();
		}
	}
}
