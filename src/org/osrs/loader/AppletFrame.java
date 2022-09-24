package org.osrs.loader;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.CheckboxMenuItem;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.wrappers.Canvas;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.GameShell;
import org.osrs.debug.WidgetDebug;
import org.osrs.script.ScriptDef;
import org.osrs.util.Data;
import com.github.joonasvali.naturalmouse.api.MouseMotionFactory;
import com.github.joonasvali.naturalmouse.util.FactoryTemplates;

public class AppletFrame extends JFrame implements AppletStub, AppletContext, ComponentListener{
	private static final long serialVersionUID = 1L;
	private PageParser pageParser;

	private MenuBar menuBar;
	private Menu fileMenu;
	private MenuItem startScriptOption;
	private MenuItem pauseScriptOption;
	private Menu debugMenu;
	public CheckboxMenuItem bankDebugOption;
	public CheckboxMenuItem boundaryObjDebugOption;
	public CheckboxMenuItem cameraDebugOption;
	public CheckboxMenuItem equipmentDebugOption;
	public CheckboxMenuItem floorDecDebugOption;
	public CheckboxMenuItem groundItemDebugOption;
	public CheckboxMenuItem interactableObjDebugOption;
	public CheckboxMenuItem inventoryDebugOption;
	public CheckboxMenuItem locationDebugOption;
	public CheckboxMenuItem menuDebugOption;
	public CheckboxMenuItem npcDebugOption;
	public CheckboxMenuItem mouseDebugOption;
	public CheckboxMenuItem pathDebugOption;
	public CheckboxMenuItem playerDebugOption;
	public CheckboxMenuItem tileDebugOption;
	public CheckboxMenuItem wallDecDebugOption;
	public CheckboxMenuItem widgetDebugOption;
	public AppletFrame(Applet applet, PageParser parser){
		long start = System.currentTimeMillis();
		System.out.println("\n[ - Applet Loader - ]");
		pageParser = parser;
		this.setSize(1000, 700);
		this.setResizable(true);
		setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Old School Runescape Game");

		menuBar = new MenuBar();
		
		fileMenu = new Menu("File");
		startScriptOption = new MenuItem("Start Script");
		startScriptOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startScriptOptionActionPerformed(evt);
            }
        });
		fileMenu.add(startScriptOption);
		pauseScriptOption = new MenuItem("Pause Script");
		pauseScriptOption.setEnabled(false);
		pauseScriptOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseScriptOptionActionPerformed(evt);
            }
        });
		fileMenu.add(pauseScriptOption);
		menuBar.add(fileMenu);

		debugMenu = new Menu("Debug");
		bankDebugOption = new CheckboxMenuItem("Bank");
		debugMenu.add(bankDebugOption);
		boundaryObjDebugOption = new CheckboxMenuItem("Boundary Objects");
		debugMenu.add(boundaryObjDebugOption);
		cameraDebugOption = new CheckboxMenuItem("Camera");
		debugMenu.add(cameraDebugOption);
		equipmentDebugOption = new CheckboxMenuItem("Equipment");
		debugMenu.add(equipmentDebugOption);
		floorDecDebugOption = new CheckboxMenuItem("Floor Decorations");
		debugMenu.add(floorDecDebugOption);
		groundItemDebugOption = new CheckboxMenuItem("Ground Items");
		debugMenu.add(groundItemDebugOption);
		interactableObjDebugOption = new CheckboxMenuItem("Interactable Objects");
		debugMenu.add(interactableObjDebugOption);
		inventoryDebugOption = new CheckboxMenuItem("Inventory");
		debugMenu.add(inventoryDebugOption);
		locationDebugOption = new CheckboxMenuItem("Location");
		debugMenu.add(locationDebugOption);
		menuDebugOption = new CheckboxMenuItem("Menu");
		debugMenu.add(menuDebugOption);
		npcDebugOption = new CheckboxMenuItem("NPCs");
		debugMenu.add(npcDebugOption);
		mouseDebugOption = new CheckboxMenuItem("Mouse");
		debugMenu.add(mouseDebugOption);
		pathDebugOption = new CheckboxMenuItem("Paths");
		debugMenu.add(pathDebugOption);
		playerDebugOption = new CheckboxMenuItem("Players");
		debugMenu.add(playerDebugOption);
		tileDebugOption = new CheckboxMenuItem("Tiles");
		debugMenu.add(tileDebugOption);
		wallDecDebugOption = new CheckboxMenuItem("Wall Decorations");
		debugMenu.add(wallDecDebugOption);
		widgetDebugOption = new CheckboxMenuItem("Widgets");
		widgetDebugOption.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
            	WidgetDebug widgetDebugger = ((Client)Data.clientInstance).getWidgetDebug();
                if(widgetDebugOption.getState()){
                	if(widgetDebugger!=null && !widgetDebugger.debugger.isVisible())
                		widgetDebugger.debugger.setVisible(true);
                }
                else{
                	if(widgetDebugger!=null && widgetDebugger.debugger.isVisible())
                		widgetDebugger.debugger.setVisible(false);
                }
            }
        });
		debugMenu.add(widgetDebugOption);
		menuBar.add(debugMenu);
		
		setMenuBar(menuBar);
		
		add(applet, BorderLayout.CENTER);
		System.out.println("Starting applet...");
		applet.setStub(this);
		applet.setVisible(true);
		applet.setSize(new Dimension(765, 545));
		applet.init();
		applet.start();
		System.out.println("Succesfully started applet in : "+(System.currentTimeMillis()-start)+"ms");
        revalidate();
        appletResize(1000, 700);
	}
	public void pauseScriptOptionActionPerformed(java.awt.event.ActionEvent evt) {
		ScriptDef script = (ScriptDef)Data.currentScript;
		if(script!=null){
			if(pauseScriptOption.getLabel().contains("Pause")){
				pauseScriptOption.setLabel("Resume Script");
				script.pause();
			}
			else{
				pauseScriptOption.setLabel("Pause Script");
				script.unpause();
			}
		}
		else{
			startScriptOption.setLabel("Start Script");
			pauseScriptOption.setLabel("Pause Script");
			pauseScriptOption.setEnabled(false);
		}
	}
    @SuppressWarnings("deprecation")
	private void startScriptOptionActionPerformed(java.awt.event.ActionEvent evt) {
		ScriptDef script = (ScriptDef)Data.currentScript;
		if(script!=null){
    		script.stopScript();
    		Data.currentScript=null;
			startScriptOption.setLabel("Start Script");
			pauseScriptOption.setLabel("Pause Script");
			pauseScriptOption.setEnabled(false);
		}
		else{
			JFileChooser c = new JFileChooser();    
			c.setCurrentDirectory(new java.io.File(getLastScriptDirectory()));
			c.setDialogTitle("Select script...");
			c.setFileSelectionMode(JFileChooser.FILES_ONLY);
			c.setFileFilter(new FileFilter() {
		        public boolean accept(File f) {
		            return (f.getName().toLowerCase().endsWith(".class") && !f.getName().contains("$")) ||
		            f.getName().toLowerCase().endsWith(".jar")
		                || f.isDirectory();
		          }
	
		          public String getDescription() {
		            return "AutoRune Scripts";
		          }
		        });
			c.setAcceptAllFileFilterUsed(false);
			if (c.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
				writeToRegistry("LastScriptLocation", c.getSelectedFile().toString());
				try {
					ClassLoader loader = new ScriptLoader(c.getSelectedFile());
					if(loader!=null){
						Class<?> scriptClass = loader.loadClass(c.getSelectedFile().getName().substring(0, c.getSelectedFile().getName().indexOf(".")));
						Object scriptObject = scriptClass.newInstance();
						if(scriptObject instanceof ScriptDef){
							script = (ScriptDef)scriptObject;
							script.setContext(((Client)Data.clientInstance).getMethodContext());
							script.start();
							Data.currentScript = script;
							startScriptOption.setLabel("Stop Script");
							pauseScriptOption.setLabel("Pause Script");
							pauseScriptOption.setEnabled(true);
						}
						else{
							System.out.println("Selected file is not a valid script!");
						}
					} 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	public String getLastLoaderDirectory(){
		if(readRegistry("LastLoaderLocation").equals("null")){
			writeToRegistry("LastLoaderLocation", getDefaultDirectory());
		}
		return readRegistry("LastLoaderLocation");
	}
	public String getLastScriptDirectory(){
		if(readRegistry("LastScriptLocation").equals("null")){
			writeToRegistry("LastScriptLocation", getDefaultDirectory());
		}
		return readRegistry("LastScriptLocation");
	}
	public void writeToRegistry(String key, String value){
		Preferences userPref = Preferences.userRoot();
		userPref.put(key, value);
	}
	public String readRegistry(String key){
		Preferences userPref = Preferences.userRoot();
		String s = userPref.get(key, "null");
		return s;
	}
	public String getCurrentDirectory(){
		if(readRegistry("LastScriptLocation").equals("null")){
			writeToRegistry("LastScriptLocation", getDefaultDirectory());
		}
		return readRegistry("LastScriptLocation");
	}
	public String getDefaultDirectory(){
		try {
			return new File(".").getCanonicalPath();
		} catch (@SuppressWarnings("unused") Exception e) {
			return System.getProperty("user.dir");
		}
	}
	public void appletResize(int arg0, int arg1) {
		if(Data.clientInstance!=null) {
			  Canvas canvas = (Canvas) ((GameShell)Data.clientInstance).canvas();
			  if(canvas!=null) canvas.applySize(arg0, arg1);
		}
	}
	public static Applet getCurrentApplet(){
		if(Data.clientInstance!=null)
			return ((Applet)Data.clientInstance);
		return null;
	}
	public AppletContext getAppletContext() {
		return this;
	}
	public URL getCodeBase() {
		try {
			return new URL(pageParser.currentLink);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public URL getDocumentBase() {
		try {
			return new URL(pageParser.currentLink);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getParameter(String s){
		return pageParser.parameters.get(s);
	}
	@Override
	public void setSize(int x, int y){
		super.setSize(x, y);
		appletResize(x, y);
	}
	private final Map<URL, WeakReference<Image>> IMAGE_CACHE = new HashMap<URL, WeakReference<Image>>();
	private final Map<String, InputStream> INPUT_CACHE = Collections.synchronizedMap(new HashMap<String, InputStream>(2));
	
	public Applet getApplet(final String name) {
		return (Applet)Data.clientInstance;
	}
	public Enumeration<Applet> getApplets() {
		final Vector<Applet> apps = new Vector<Applet>();
		apps.add((Applet)Data.clientInstance);
		return apps.elements();
	}
	public AudioClip getAudioClip(final URL url) {
		throw new UnsupportedOperationException("NOT YET IMPLEMENTED getAudioClip=" + url);
	}
	public Image getImage(final URL url) {
		synchronized (IMAGE_CACHE) {
			WeakReference<Image> ref = IMAGE_CACHE.get(url);
			Image img;
			if (ref == null || (img = ref.get()) == null) {
				img = Toolkit.getDefaultToolkit().createImage(url);
				ref = new WeakReference<Image>(img);
				IMAGE_CACHE.put(url, ref);
			}
			return img;
		}
	}
	public InputStream getStream(final String key) {
		return INPUT_CACHE.get(key);
	}
	public Iterator<String> getStreamKeys() {
		return Collections.unmodifiableSet(INPUT_CACHE.keySet()).iterator();
	}
	public void setStream(final String key, final InputStream stream) throws IOException {
		INPUT_CACHE.put(key, stream);
	}
	public void showDocument(final URL url) {
		showDocument(url, "");
	}
	public void showDocument(final URL url, final String target) {
	}
	public void showStatus(final String status) {
	}
	public void componentHidden(ComponentEvent arg0) {
	}
	public void componentMoved(ComponentEvent arg0) {
	}
	public void componentResized(ComponentEvent arg0) {
		appletResize(arg0.getComponent().getWidth(), arg0.getComponent().getHeight());
	}
	public void componentShown(ComponentEvent arg0) {
	}
}