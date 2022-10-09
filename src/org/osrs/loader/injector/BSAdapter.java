package org.osrs.loader.injector;

import java.util.ArrayList;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.commons.RemappingClassAdapter;
import org.objectweb.asm.commons.RemappingMethodAdapter;
import org.osrs.injection.FieldHook;
import org.osrs.injection.MethodHook;

public class BSAdapter extends RemappingClassAdapter{
	private org.osrs.loader.Compiler compiler;
	public BSAdapter(ClassVisitor cv, Remapper remapper){
		super(cv, remapper);
	}
	public void linkCompiler(org.osrs.loader.Compiler c){
		compiler = c;
	}
    @Override
    public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
		if(var3.contains("org/osrs/api/wrappers/proxies/")){
			var3 = remapper.mapType(var3.replace("org/osrs/api/wrappers/proxies/", ""));
		}
        return super.visitField(var1, var2, var3, var4, var5);
    }
    @Override
    public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
    	String desc = var3;
		String[] oldParams = parseArguments(desc);
		String[] newParams = new String[oldParams.length];
		for(int i=0;i<oldParams.length;++i){
			String s = oldParams[i];
			if(s.contains("org/osrs/api/wrappers/proxies/")){
				s = remapper.mapType(s.replace("org/osrs/api/wrappers/proxies/", ""));
			}
			newParams[i] = s;
		}
		String oldReturnType = desc.substring(desc.indexOf(")")+1, desc.length());
		String newReturnType = remapper.map(oldReturnType);
		String newDesc = "(";
		for(String s : newParams)
			newDesc+=s;
		newDesc+=")"+newReturnType;
		var3 = newDesc;
    	return super.visitMethod(var1, var2, var3, var4, var5);
    }
    @Override
    protected MethodVisitor createRemappingMethodAdapter(int access,
            String newDesc, MethodVisitor mv) {
    	return new BSMethodAdapter(access, newDesc, mv, remapper);
    }
    /**
     * E.G. (LNode;IIZ)Z -> {"LNode;", "I", "I", "Z"}
     * @param desc
     * @return params
     */
    public String[] parseArguments(String desc) {
        ArrayList<String> args = new ArrayList<String>();
        String signiture = desc;
        signiture = signiture.substring(signiture.indexOf("(") + 1, signiture.indexOf(")"));
        while (signiture != null && !signiture.equals("") && signiture.length() > 0) {
            if (signiture.charAt(0) == 'B' || signiture.charAt(0) == 'C' || signiture.charAt(0) == 'D' || signiture.charAt(0) == 'F' || signiture.charAt(0) == 'I' || signiture.charAt(0) == 'J' || signiture.charAt(0) == 'S' || signiture.charAt(0) == 'Z') {
                args.add(signiture.charAt(0) + "");
                signiture = signiture.substring(1, signiture.length());
            } else if (signiture.startsWith("[")) {
                String arg = "[";
                signiture = signiture.substring(1, signiture.length());
                while (signiture.startsWith("[")) {
                    arg += "[";
                    signiture = signiture.substring(1, signiture.length());
                }
                if (signiture.charAt(0) == 'B' || signiture.charAt(0) == 'C' || signiture.charAt(0) == 'D' || signiture.charAt(0) == 'F' || signiture.charAt(0) == 'I' || signiture.charAt(0) == 'J' || signiture.charAt(0) == 'S' || signiture.charAt(0) == 'Z') {
                    args.add(arg + signiture.charAt(0) + "");
                    signiture = signiture.substring(1, signiture.length());
                } else {
                    args.add(arg + signiture.substring(0, signiture.indexOf(";") + 1));
                    signiture = signiture.substring(signiture.indexOf(";") + 1, signiture.length());
                }
            } else {
                args.add(signiture.substring(0, signiture.indexOf(";") + 1));
                signiture = signiture.substring(signiture.indexOf(";") + 1, signiture.length());
            }
        }
        return args.toArray(new String[]{});
    }
    protected class BSMethodAdapter extends RemappingMethodAdapter{
		public BSMethodAdapter(int access, String desc, MethodVisitor mv, Remapper remapper) {
			super(access, desc, mv, remapper);
		}
	    @Override
	    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
	    	if(owner.startsWith("org/osrs/api/wrappers/proxies/")){
	    		FieldHook fh = compiler.modscriptData.resolver.getFieldHook(owner.replace("org/osrs/api/wrappers/proxies/", ""), name, (opcode==Opcodes.GETSTATIC || opcode==Opcodes.PUTSTATIC));
	    		if(fh!=null){
	    			owner = fh.owner;
	    			name = fh.obfuscatedName;
	    			desc = fh.dataType;
	    		}
	    		else{
	    			String temp = remapper.map(owner);
	    			if(temp!=null)
	    				owner = temp;
	    			else
	    				owner = owner.replace("org/osrs/api/wrappers/proxies/", "");
	    			if(desc.contains("org/osrs/api/wrappers/proxies/"))
	    				desc = remapper.mapType(desc);
	    		}
	    	}
	    	else{
	    		FieldHook fh = compiler.fieldGetterDetours.get(owner+"."+name);
	    		if(fh!=null){
		    		if(opcode==Opcodes.GETSTATIC || opcode==Opcodes.GETFIELD){//GETTER DETOURS
		    			owner = opcode==Opcodes.GETSTATIC?"client":owner;
		    			visitMethodInsn(opcode==Opcodes.GETSTATIC?Opcodes.INVOKESTATIC:Opcodes.INVOKEVIRTUAL, owner, "get_"+fh.refactoredName, "()"+desc);
		    			return;//dont create FieldInsn
			    	}
	    		}
	    		fh = compiler.fieldSetterDetours.get(owner+"."+name);
	    		if(fh!=null){
		    		if(opcode==Opcodes.PUTSTATIC || opcode==Opcodes.PUTFIELD){//SETTER DETOURS
		    			owner = opcode==Opcodes.PUTSTATIC?"client":owner;
		    			visitMethodInsn(opcode==Opcodes.PUTSTATIC?Opcodes.INVOKESTATIC:Opcodes.INVOKEVIRTUAL, owner, "set_"+fh.refactoredName, "("+desc+")V");
		    			return;//dont create FieldInsn
		    		}
	    		}
	    	}
	        super.visitFieldInsn(opcode, owner, name, desc);
	    }
	    @Override
	    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
	    	if(owner.startsWith("org/osrs/api/wrappers/proxies/")){
	    		String newOwner = remapper.map(owner);
		    	if(name.startsWith("_")){
		    		String newName = name.replace("_", "");
		    		String newDesc = remapper.mapMethodDesc(desc);
		    		MethodHook mh = compiler.methodMapping.get((owner.replace("org/osrs/api/wrappers/proxies/", ""))+"."+newName+newDesc);
		    		if(opcode==Opcodes.INVOKESTATIC){
		    			mh = compiler.methodMapping.get("Client."+newName+newDesc);
		    			if(mh!=null){
			    			newOwner = mh.owner;
			    			name = mh.obfuscatedName;
		    			}
		    		}
		    		else{
		    			if(mh!=null){
			    			name = mh.obfuscatedName;
		    			}
		    		}
		    	}
		    	//if(!owner.equals(newOwner))
		    		//System.out.println("Resolving MethodInsn owner : "+owner+" -> "+newOwner);
		    	owner = newOwner;
	    	}
	    	else{
	    		if(compiler.methodDetours.containsKey(owner+"."+name+desc)){
		    		MethodHook mh = compiler.modscriptData.resolver.getObfuscatedMethodHook(owner, name, desc, opcode==Opcodes.INVOKESTATIC);
		    		if(mh!=null){
		    			owner = opcode==Opcodes.INVOKESTATIC?"client":mh.owner;
		    			name = mh.refactoredName;
		    		}
	    		}
	    	}
	        super.visitMethodInsn(opcode, owner, name, desc);
	    }
    }
}
