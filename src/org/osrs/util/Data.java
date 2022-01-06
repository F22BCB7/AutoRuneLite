package org.osrs.util;

import org.osrs.input.InputManager;
import org.osrs.loader.AppletFrame;
import org.osrs.loader.Modscript;
import org.osrs.script.ScriptDef;

public class Data {
	public static Modscript clientModscript;
	public static ClassLoader scriptClassloader;
	public static Object clientInstance;
	public static InputManager inputManager;
	public static ScriptDef currentScript;
	public static AppletFrame clientFrame;
}
