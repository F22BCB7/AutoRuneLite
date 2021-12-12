package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.script.events.VarcEvent;
import org.osrs.util.Data;
import org.osrs.injection.bytescript.BGetter;

@BClass(name="RuneScriptVM")
public class RuneScriptVM implements org.osrs.api.wrappers.RuneScriptVM{
	@BField
	public String[] strings;
	@BGetter
	@Override
	public String[] strings(){return strings;}
	@BField
	public java.util.Map varcMap;
	@BGetter
	@Override
	public java.util.Map varcMap(){return varcMap;}
	@BField
	public boolean changed;
	@BGetter
	@Override
	public boolean changed(){return changed;}
	@BField
	public boolean[] varcStringSerials;
	@BGetter
	@Override
	public boolean[] varcStringSerials(){return varcStringSerials;}
	@BField
	public long cycle;
	@BGetter
	@Override
	public long cycle(){return cycle;}

	@BMethod(name="getVarcString")
	public String _getVarcString(int var1, int var2){return null;}
	@BMethod(name="getVarcString")
	public String _getVarcString(int var1, byte var2){return null;}
	@BMethod(name="getVarcString")
	public String _getVarcString(int var1, short var2){return null;}
	@BDetour
	public String getVarcString(int var1, int var2){
		return invoke_getVarcString(var1);
	}
	@BDetour
	public String getVarcString(int var1, byte var2){
		return invoke_getVarcString(var1);
	}
	@BDetour
	public String getVarcString(int var1, short var2){
		return invoke_getVarcString(var1);
	}
	@BFunction
	@Override
	public String invoke_getVarcString(int a){
		Object predicate = Client.clientInstance.getMethodPredicate("RuneScriptVM", "getVarcString", "(I?)Ljava/lang/String;", false);
		String val = null;
		if(predicate instanceof Integer)
			val = _getVarcString(a, (int)predicate);
		if(predicate instanceof Byte)
			val = _getVarcString(a, (byte)predicate);
		if(predicate instanceof Short)
			val = _getVarcString(a, (short)predicate);
		//System.out.println("[getVarcString] "+a+" : "+val);
		sendEvent(a, val, VarcEvent.GET_STRING_EVENT_TYPE);
		return val;
	}

	@BMethod(name="putVarcString")
	public void _putVarcString(int var1, String var2, int var3){}
	@BMethod(name="putVarcString")
	public void _putVarcString(int var1, String var2, byte var3){}
	@BMethod(name="putVarcString")
	public void _putVarcString(int var1, String var2, short var3){}
	@BDetour
	public void putVarcString(int var1, String var2, int var3){
		invoke_putVarcString(var1, var2);
	}
	@BDetour
	public void putVarcString(int var1, String var2, byte var3){
		invoke_putVarcString(var1, var2);
	}
	@BDetour
	public void putVarcString(int var1, String var2, short var3){
		invoke_putVarcString(var1, var2);
	}
	@BFunction
	@Override
	public void invoke_putVarcString(int var1, String var2){
		Object predicate = Client.clientInstance.getMethodPredicate("RuneScriptVM", "putVarcString", "(ILjava/lang/String;?)V", false);
		if(predicate instanceof Integer)
			_putVarcString(var1, var2, (int)predicate);
		if(predicate instanceof Byte)
			_putVarcString(var1, var2, (byte)predicate);
		if(predicate instanceof Short)
			_putVarcString(var1, var2, (short)predicate);
		//System.out.println("[putVarcString] "+var1+" : "+var2);
		sendEvent(var1, var2, VarcEvent.PUT_STRING_EVENT_TYPE);
	}

	@BMethod(name="getStringAtIndex")
	public String _getStringAtIndex(int var1, int var2){return null;}
	@BMethod(name="getStringAtIndex")
	public String _getStringAtIndex(int var1, byte var2){return null;}
	@BMethod(name="getStringAtIndex")
	public String _getStringAtIndex(int var1, short var2){return null;}
	@BDetour
	public String getStringAtIndex(int var1, int var2){
		return invoke_getStringAtIndex(var1);
	}
	@BDetour
	public String getStringAtIndex(int var1, byte var2){
		return invoke_getStringAtIndex(var1);
	}
	@BDetour
	public String getStringAtIndex(int var1, short var2){
		return invoke_getStringAtIndex(var1);
	}
	@BFunction
	@Override
	public String invoke_getStringAtIndex(int a){
		Object predicate = Client.clientInstance.getMethodPredicate("RuneScriptVM", "getStringAtIndex", "(I?)Ljava/lang/String;", false);
		String val = null;
		if(predicate instanceof Integer)
			val = _getStringAtIndex(a, (int)predicate);
		if(predicate instanceof Byte)
			val = _getStringAtIndex(a, (byte)predicate);
		if(predicate instanceof Short)
			val = _getStringAtIndex(a, (short)predicate);
		//System.out.println("[getStringAtIndex] "+a+" : "+val);
		return val;
	}
	
	@BMethod(name="putStringAtIndex")
	public void _putStringAtIndex(int var1, String var2, int var3){}
	@BMethod(name="putStringAtIndex")
	public void _putStringAtIndex(int var1, String var2, byte var3){}
	@BMethod(name="putStringAtIndex")
	public void _putStringAtIndex(int var1, String var2, short var3){}
	@BDetour
	public void putStringAtIndex(int var1, String var2, int var3){
		invoke_putStringAtIndex(var1, var2);
	}
	@BDetour
	public void putStringAtIndex(int var1, String var2, byte var3){
		invoke_putStringAtIndex(var1, var2);
	}
	@BDetour
	public void putStringAtIndex(int var1, String var2, short var3){
		invoke_putStringAtIndex(var1, var2);
	}
	@BFunction
	@Override
	public void invoke_putStringAtIndex(int var1, String var2){
		Object predicate = Client.clientInstance.getMethodPredicate("RuneScriptVM", "putStringAtIndex", "(ILjava/lang/String;?)V", false);
		if(predicate instanceof Integer)
			_putStringAtIndex(var1, var2, (int)predicate);
		if(predicate instanceof Byte)
			_putStringAtIndex(var1, var2, (byte)predicate);
		if(predicate instanceof Short)
			_putStringAtIndex(var1, var2, (short)predicate);
		//System.out.println("[putStringAtIndex] "+var1+" : "+var2);
	}

	@BMethod(name="getVarcInt")
	public int _getVarcInt(int var1, int var3){return -1;}
	@BMethod(name="getVarcInt")
	public int _getVarcInt(int var1, byte var3){return -1;}
	@BMethod(name="getVarcInt")
	public int _getVarcInt(int var1, short var3){return -1;}
	@BDetour
	public int getVarcInt(int var1, int var3){
		return invoke_getVarcInt(var1);
	}
	@BDetour
	public int getVarcInt(int var1, byte var3){
		return invoke_getVarcInt(var1);
	}
	@BDetour
	public int getVarcInt(int var1, short var3){
		return invoke_getVarcInt(var1);
	}
	@BFunction
	@Override
	public int invoke_getVarcInt(int var1){
		int val = -1;
		Object predicate = Client.clientInstance.getMethodPredicate("RuneScriptVM", "getVarcInt", "(I?)I", false);
		if(predicate instanceof Integer)
			val = _getVarcInt(var1, (int)predicate);
		if(predicate instanceof Byte)
			val = _getVarcInt(var1, (byte)predicate);
		if(predicate instanceof Short)
			val = _getVarcInt(var1, (short)predicate);
		//System.out.println("[getVarcInt] "+var1+" : "+val);
		sendEvent(var1, val, VarcEvent.GET_INT_EVENT_TYPE);
		return val;
	}

	@BMethod(name="putVarcInt")
	public void _putVarcInt(int var1, int var2, int var3){}
	@BMethod(name="putVarcInt")
	public void _putVarcInt(int var1, int var2, byte var3){}
	@BMethod(name="putVarcInt")
	public void _putVarcInt(int var1, int var2, short var3){}
	@BDetour
	public void putVarcInt(int var1, int var2, int var3){
		invoke_putVarcInt(var1, var2);
	}
	@BDetour
	public void putVarcInt(int var1, int var2, byte var3){
		invoke_putVarcInt(var1, var2);
	}
	@BDetour
	public void putVarcInt(int var1, int var2, short var3){
		invoke_putVarcInt(var1, var2);
	}
	@BFunction
	@Override
	public void invoke_putVarcInt(int var1, int var2){
		Object predicate = Client.clientInstance.getMethodPredicate("RuneScriptVM", "putVarcInt", "(II?)V", false);
		if(predicate instanceof Integer)
			_putVarcInt(var1, var2, (int)predicate);
		if(predicate instanceof Byte)
			_putVarcInt(var1, var2, (byte)predicate);
		if(predicate instanceof Short)
			_putVarcInt(var1, var2, (short)predicate);
		//System.out.println("[putVarcInt] "+var1+" : "+var2);
		sendEvent(var1, var2, VarcEvent.PUT_INT_EVENT_TYPE);
	}
	
	@BFunction
	private void sendEvent(int key, Object value, int type){
		if(Data.currentScript!=null){
			if(Data.currentScript instanceof org.osrs.script.listeners.VarcListener){
				org.osrs.script.events.VarcEvent event = new org.osrs.script.events.VarcEvent(key, value, type);
				((org.osrs.script.listeners.VarcListener)Data.currentScript).varcEvent(event);
			}
		}
	}
}