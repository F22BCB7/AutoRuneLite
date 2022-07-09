package org.osrs.api.wrappers.proxies;

import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BGetterDetour;
import org.osrs.injection.bytescript.BSetterDetour;

@BClass
public class ClientPreferences implements org.osrs.api.wrappers.ClientPreferences{
	@BField
	public boolean disableRoofs;
	@BGetter
	@Override
	public boolean disableRoofs(){return disableRoofs;}
	@BGetterDetour
	public boolean get_disableRoofs(){
		return true;//TODO implement ClientSettings (org.osrs.util.Settings)
	}
	
	@BField
	public boolean disableLoadingAudio;
	@BGetter
	@Override
	public boolean disableLoadingAudio(){return disableLoadingAudio;}
	@BGetterDetour
	public boolean get_disableLoadingAudio(){
		return true;//TODO implement ClientSettings (org.osrs.util.Settings)
	}
}
