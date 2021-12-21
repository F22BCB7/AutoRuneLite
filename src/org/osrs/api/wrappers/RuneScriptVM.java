package org.osrs.api.wrappers;

import java.util.Map;

public interface RuneScriptVM{
	public String[] strings();
	public Map varcMap();
	public boolean changed();
	public boolean[] varcSerials();
	public long cycle();
	
	public String invoke_getVarcString(int a);
	public void invoke_putVarcString(int var1, String var2);
	public void invoke_putStringAtIndex(int var1, String var2);
	public String invoke_getStringAtIndex(int a);
	public int invoke_getVarcInt(int var1);
	public void invoke_putVarcInt(int var1, int var2);
}