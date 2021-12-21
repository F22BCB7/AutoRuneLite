package org.osrs.loader.injector;

import org.objectweb.asm.commons.Remapper;
import org.osrs.injection.ClassResolver;
import org.osrs.loader.Modscript;

public class BSRemapper extends Remapper{
	private ClassResolver resolver;
	private Modscript modscript;
	private org.osrs.loader.Compiler compiler;
	public BSRemapper(org.osrs.loader.Compiler c){
		compiler = c;
		modscript = compiler.modscriptData;
		resolver = modscript.resolver;
	}
	@Override
	public String map(String key){
		if(key.startsWith("org/osrs/api/wrappers/proxies/")){
			String newKey = resolver.getObfuscatedClassName(key.replace("org/osrs/api/wrappers/proxies/", ""));
			return newKey;
		}
		return key;
	}
	@Override
	public String mapType(String key){
		if(key.contains("org/osrs/api/wrappers/proxies/")){
			String newKey = resolver.getObfuscatedType(key.replace("org/osrs/api/wrappers/proxies/", ""));
			return newKey;
		}
		return super.mapType(key);
	}
	@Override
	public String mapMethodDesc(final String methodDescriptor){
		String desc = methodDescriptor;
		String[] oldParams = resolver.parseArguments(desc);
		String[] newParams = new String[oldParams.length];
		for(int i=0;i<oldParams.length;++i){
			String s = oldParams[i];
			if(s.startsWith("Lorg/osrs/api/wrappers/proxies/")){
				s = resolver.getObfuscatedType(s.replace("org/osrs/api/wrappers/proxies/", ""));
			}
			newParams[i] = s;
		}
		String oldReturnType = desc.substring(desc.indexOf(")")+1, desc.length());
		String newReturnType = resolver.getObfuscatedType(oldReturnType.replace("org/osrs/api/wrappers/proxies/", ""));
		String newDesc = "(";
		for(String s : newParams)
			newDesc+=s;
		newDesc+=")"+newReturnType;
		return newDesc;
	}
    @Override
    public final String mapMethodName(String owner, String name, String desc) {
    	return name;//dont remap; handled in BSAdapter.
    }
}
