package org.osrs.util;

import org.osrs.input.InputManager;
import org.osrs.loader.Modscript;

public class Data {
	public static Modscript clientModscript;
	public static ClassLoader scriptClassloader;
	public static Object clientInstance;
	public static InputManager inputManager;
	public static Object currentScript;
}
