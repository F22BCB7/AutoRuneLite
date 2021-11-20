package org.osrs.injection;

import java.util.ArrayList;
import java.util.HashMap;

import org.objectweb.asm.Wildcard;
import org.osrs.loader.Modscript;

public class ClassResolver {
	private HashMap<String, String> old2new = new HashMap<String, String>();
	private HashMap<String, String> new2old = new HashMap<String, String>();
    private Modscript modscript = null;
    public ClassResolver(Modscript modscript){
    	this.modscript = modscript;
    }
    /**
     * E.G. (ILNode;I)LNode; -> (ILab;I)Lab;
     * @param refactoredDesc
     * @return obfuscatedDesc
     */
    public String getObfuscatedDesc(String refactoredDesc){
    	String obfuscatedDesc = new2old.get(refactoredDesc);
    	if(obfuscatedDesc!=null)
    		return obfuscatedDesc;
    	obfuscatedDesc = refactoredDesc;
    	String oldRetType = refactoredDesc.substring(refactoredDesc.indexOf(")")+1);
    	String retType = getObfuscatedType(oldRetType.replace("org/osrs/api/wrappers/", ""));
    	if(retType!=null)
    		obfuscatedDesc = obfuscatedDesc.replace(oldRetType, retType);
    	String[] params = parseArguments(refactoredDesc);
    	for(int i=0;i<params.length;++i){
    		if(params[i].startsWith("L") && params[i].endsWith(";")){
    			String s = getObfuscatedType(params[i].replace("org/osrs/api/wrappers/", ""));
    			if(s!=null){
    				obfuscatedDesc = obfuscatedDesc.replace(params[i], s);
    			}
    		}
    	}
    	new2old.put(refactoredDesc, obfuscatedDesc);
    	return obfuscatedDesc;
    }
    /**
     * E.G. (ILab;I)Lab; -> (ILNode;I)LNode;
     * @param obfuscatedDesc
     * @return refactoredDesc
     */
    public String getRefactoredDesc(String obfuscatedDesc){
    	String refactoredDesc = old2new.get(obfuscatedDesc);
    	if(refactoredDesc!=null)
    		return refactoredDesc;
    	refactoredDesc = obfuscatedDesc;
    	String oldRetType = obfuscatedDesc.substring(obfuscatedDesc.indexOf(")")+1);
    	String retType = getRefactoredType(oldRetType);
    	if(retType!=null)
    		refactoredDesc = refactoredDesc.replace(oldRetType, retType.replace("L", "Lorg/osrs/api/wrappers/"));
    	String[] params = parseArguments(obfuscatedDesc);
    	for(int i=0;i<params.length;++i){
    		if(params[i].startsWith("L") && params[i].endsWith(";")){
    			String s = getRefactoredType(params[i]);
    			if(s!=null){
    				refactoredDesc = refactoredDesc.replace(params[i], s.replace("L", "Lorg/osrs/api/wrappers/"));
    			}
    		}
    	}
    	old2new.put(obfuscatedDesc, refactoredDesc);
    	return refactoredDesc;
    }
    /**
     * E.G. LNode; -> Lab;
     * @param refactoredType
     * @return obfuscatedType
     */
    public String getObfuscatedType(String refactoredType){
    	//TODO determine array+dimension
    	String type = new2old.get(refactoredType);
    	if(type!=null)
    		return type;
    	if(refactoredType.startsWith("L") && refactoredType.endsWith(";")){
	    	type = refactoredType;
	    	String array = "";
	    	if(refactoredType.startsWith("[")){
	    		array = refactoredType.substring(refactoredType.indexOf("["), refactoredType.lastIndexOf("[")+1);
	    		type.replace(array, "");
	    	}
	    	type = type.replace("L", "");
	    	type = type.replace(";", "");
	    	type = getObfuscatedClassName(type);
	    	if(type!=null){
	    		type = array+"L"+type+";";
	    		new2old.put(refactoredType, type);
	    		return type;
	    	}
    	}
    	return null;
    }
    /**
     * E.G. Lab; -> LNode;
     * @param obfuscatedType
     * @return refactoredType
     */
    public String getRefactoredType(String obfuscatedType){
    	String type = old2new.get(obfuscatedType);
    	if(type!=null)
    		return type;
    	if(obfuscatedType.startsWith("L") && obfuscatedType.endsWith(";")){
	    	type = obfuscatedType;
	    	type = type.replace("L", "");
	    	type = type.replace(";", "");
	    	type = getRefactoredClassName(type);
	    	if(type!=null){
	    		old2new.put(obfuscatedType, "L"+type+";");
	    		return "L"+type+";";
	    	}
    	}
    	return null;
    }
    /**
     * E.G. Node -> ab
     * @param refactoredName
     * @return obfuscatedName
     */
    public String getObfuscatedClassName(String refactoredName){
    	String name = new2old.get(refactoredName);
    	if(name!=null)
    		return name;
    	for(ClassHook ch : modscript.classHooks){
    		if(ch.refactoredName.equals(refactoredName)){
    			new2old.put(refactoredName, ch.obfuscatedName);
    			return ch.obfuscatedName;
    		}
    	}
    	return null;
    }
    /**
     * E.G. ab -> Node
     * @param obfuscatedName
     * @return refactoredName
     */
    public String getRefactoredClassName(String obfuscatedName){
    	String name = old2new.get(obfuscatedName);
    	if(name!=null)
    		return name;
    	for(ClassHook ch : modscript.classHooks){
    		if(ch.obfuscatedName.equals(obfuscatedName)){
    			old2new.put(obfuscatedName, ch.refactoredName);
    			return ch.refactoredName;
    		}
    	}
    	return null;
    }
    public ClassHook getClassHook(String refactoredName){
    	for(ClassHook ch : modscript.classHooks){
    		if(ch.refactoredName.equals(refactoredName)){
    			return ch;
    		}
    	}
    	return null;
    }
    public String getObfuscatedMethodOwner(String refactoredOwner, String refactoredName, String wildcardDesc, boolean isStatic){
    	String name = new2old.get("OWNER"+refactoredOwner+"."+refactoredName+wildcardDesc);
    	if(name!=null)
    		return name;
    	if(isStatic){
    		for(MethodHook mh : modscript.staticMethods){
				if(mh.refactoredName.equals(refactoredName)){
					new2old.put(refactoredOwner+"."+refactoredName+wildcardDesc, mh.obfuscatedName);
					return mh.owner;
				}
			}
    	}
		for(ClassHook ch : modscript.classHooks){
    		if(!isStatic && ch.refactoredName.equals(refactoredOwner)){
    			for(MethodHook mh : ch.methodHooks){
    				if(mh.refactoredName.equals(refactoredName)){
    					new2old.put(refactoredOwner+"."+refactoredName+wildcardDesc, mh.obfuscatedName);
    					return mh.owner;
    				}
    			}
    		}
		}
    	return null;
    }
    public String getObfuscatedMethodDesc(String refactoredOwner, String refactoredName, String wildcardDesc, boolean isStatic){
    	if(isStatic){
    		for(MethodHook mh : modscript.staticMethods){
				if(mh.refactoredName.equals(refactoredName)){
					//new2old.put(refactoredOwner+"."+refactoredName+wildcardDesc, mh.obfuscatedName);
					return mh.desc;
				}
			}
    	}
		for(ClassHook ch : modscript.classHooks){
    		if(!isStatic && ch.refactoredName.equals(refactoredOwner)){
    			for(MethodHook mh : ch.methodHooks){
    				if(mh.refactoredName.equals(refactoredName)){
    					//new2old.put(refactoredOwner+"."+refactoredName+wildcardDesc, mh.obfuscatedName);
    					return mh.desc;
    				}
    			}
    		}
		}
    	return null;
    }
    public String getObfuscatedMethodName(String refactoredOwner, String refactoredName, String wildcardDesc, boolean isStatic){
    	String name = new2old.get(refactoredOwner+"."+refactoredName+wildcardDesc);
    	if(name!=null)
    		return name;
    	if(isStatic){
    		for(MethodHook mh : modscript.staticMethods){
				if(mh.refactoredName.equals(refactoredName)){
					new2old.put(refactoredOwner+"."+refactoredName+wildcardDesc, mh.obfuscatedName);
					return mh.obfuscatedName;
				}
			}
    	}
		for(ClassHook ch : modscript.classHooks){
    		if(!isStatic && ch.refactoredName.equals(refactoredOwner)){
    			for(MethodHook mh : ch.methodHooks){
    				if(mh.refactoredName.equals(refactoredName)){
    					new2old.put(refactoredOwner+"."+refactoredName+wildcardDesc, mh.obfuscatedName);
    					return mh.obfuscatedName;
    				}
    			}
    		}
		}
    	return null;
    }
    public String getRefactoredMethodName(String obfuscatedOwner, String obfuscatedName, String desc, boolean isStatic){
    	String name = old2new.get(obfuscatedOwner+"."+obfuscatedName+desc);
    	if(name!=null)
    		return name;
    	if(!isStatic){
    		for(ClassHook ch : modscript.classHooks){
	    		if(ch.obfuscatedName.equals(obfuscatedOwner)){
	    			for(MethodHook mh : ch.methodHooks){
	    				if(mh.obfuscatedName.equals(obfuscatedName) && desc.equals(mh.desc)){
	    					old2new.put(obfuscatedOwner+"."+obfuscatedName+desc, mh.refactoredName);
	    					return mh.refactoredName;
	    				}
	    			}
	    		}
    		}
    	}
    	else{
    		for(MethodHook mh : modscript.staticMethods){
    			if(mh.owner.equals(obfuscatedOwner) && mh.obfuscatedName.equals(obfuscatedName) && desc.equals(mh.desc)){
					old2new.put(obfuscatedOwner+"."+obfuscatedName+desc, mh.refactoredName);
					return mh.refactoredName;
				}
    		}
    	}
    	return null;
    }

    public String getObfuscatedFieldDesc(String refactoredOwner, String refactoredName, boolean isStatic){
    	String name = new2old.get("DESC"+refactoredOwner+"."+refactoredName);
    	if(name!=null)
    		return name;
    	if(!isStatic){
	    	for(ClassHook ch : modscript.classHooks){
	    		if(ch.refactoredName.equals(refactoredOwner)){
	    			for(FieldHook fh : ch.fieldHooks){
	    				if(fh.refactoredName.equals(refactoredName)){
	    					new2old.put("DESC"+refactoredOwner+"."+refactoredName, fh.dataType);
	    					return fh.dataType;
	    				}
	    			}
	    		}
	    	}
    	}
    	else{
			for(FieldHook fh : modscript.staticFields){
				if(fh.refactoredName.equals(refactoredName)){
					new2old.put("DESC"+refactoredOwner+"."+refactoredName, fh.dataType);
					return fh.dataType;
				}
			}
    	}
    	return null;
    }
    public Object getFieldMultiplier(String refactoredOwner, String refactoredName, boolean isStatic){
    	if(!isStatic){
	    	for(ClassHook ch : modscript.classHooks){
	    		if(ch.refactoredName.equals(refactoredOwner)){
	    			for(FieldHook fh : ch.fieldHooks){
	    				if(fh.refactoredName.equals(refactoredName)){
	    					return fh.multiplier;
	    				}
	    			}
	    		}
	    	}
    	}
    	else{
			for(FieldHook fh : modscript.staticFields){
				if(fh.refactoredName.equals(refactoredName)){
					return fh.multiplier;
				}
			}
    	}
    	return null;
    }
    public String getObfuscatedFieldOwner(String refactoredOwner, String refactoredName, boolean isStatic){
    	String name = new2old.get("OWNER"+refactoredOwner+"."+refactoredName);
    	if(name!=null)
    		return name;
    	if(!isStatic){
	    	for(ClassHook ch : modscript.classHooks){
	    		if(ch.refactoredName.equals(refactoredOwner)){
	    			for(FieldHook fh : ch.fieldHooks){
	    				if(fh.refactoredName.equals(refactoredName)){
	    					new2old.put("OWNER"+refactoredOwner+"."+refactoredName, fh.owner);
	    					return fh.owner;
	    				}
	    			}
	    		}
	    	}
    	}
    	else{
			for(FieldHook fh : modscript.staticFields){
				if(fh.refactoredName.equals(refactoredName)){
					new2old.put("OWNER"+refactoredOwner+"."+refactoredName, fh.owner);
					return fh.owner;
				}
			}
    	}
    	return null;
    }
    public String getObfuscatedFieldName(String refactoredOwner, String refactoredName, boolean isStatic){
    	String name = new2old.get(refactoredOwner+"."+refactoredName);
    	if(name!=null)
    		return name;
    	if(!isStatic){
	    	for(ClassHook ch : modscript.classHooks){
	    		if(ch.refactoredName.equals(refactoredOwner)){
	    			for(FieldHook fh : ch.fieldHooks){
	    				if(fh.refactoredName.equals(refactoredName)){
	    					new2old.put(refactoredOwner+"."+refactoredName, fh.obfuscatedName);
	    					return fh.obfuscatedName;
	    				}
	    			}
	    		}
	    	}
    	}
    	else{
			for(FieldHook fh : modscript.staticFields){
				if(fh.refactoredName.equals(refactoredName)){
					new2old.put(refactoredOwner+"."+refactoredName, fh.obfuscatedName);
					return fh.obfuscatedName;
				}
			}
    	}
    	return null;
    }
    public String getRefactoredFieldName(String obfuscatedOwner, String obfuscatedName, boolean isStatic){
    	String name = old2new.get(obfuscatedOwner+"."+obfuscatedName);
    	if(name!=null)
    		return name;
    	if(!isStatic){
	    	for(ClassHook ch : modscript.classHooks){
	    		if(ch.obfuscatedName.equals(obfuscatedOwner)){
	    			for(FieldHook fh : ch.fieldHooks){
	    				if(fh.obfuscatedName.equals(obfuscatedName)){
	    					old2new.put(obfuscatedOwner+"."+obfuscatedName, fh.refactoredName);
	    					return fh.refactoredName;
	    				}
	    			}
	    		}
	    	}
    	}
    	else{
    		for(FieldHook fh : modscript.staticFields){
    			if(fh.owner.equals(obfuscatedOwner) && fh.obfuscatedName.equals(obfuscatedName)){
					old2new.put(obfuscatedOwner+"."+obfuscatedName, fh.refactoredName);
					return fh.refactoredName;
				}
    		}
    	}
    	return null;
    }
    public MethodHook getMethodHook(String refactoredOwner, String refactoredName, String wildcardDesc, boolean isStatic){
    	if(isStatic){
    		for(MethodHook mh : modscript.staticMethods){
				if(mh.refactoredName.equals(refactoredName)){
					new2old.put(refactoredOwner+"."+refactoredName+wildcardDesc, mh.obfuscatedName);
					return mh;
				}
			}
    	}
		for(ClassHook ch : modscript.classHooks){
    		if(!isStatic && ch.refactoredName.equals(refactoredOwner)){
    			for(MethodHook mh : ch.methodHooks){
    				if(mh.refactoredName.equals(refactoredName)){
    					new2old.put(refactoredOwner+"."+refactoredName+wildcardDesc, mh.obfuscatedName);
    					return mh;
    				}
    			}
    		}
		}
    	return null;
    }
    public FieldHook getFieldHook(String refactoredOwner, String refactoredName, boolean isStatic){
    	if(!isStatic){
	    	for(ClassHook ch : modscript.classHooks){
	    		if(ch.refactoredName.equals(refactoredOwner)){
	    			for(FieldHook fh : ch.fieldHooks){
	    				if(fh.refactoredName.equals(refactoredName)){
	    					return fh;
	    				}
	    			}
	    		}
	    	}
    	}
    	else{
			for(FieldHook fh : modscript.staticFields){
				if(fh.refactoredName.equals(refactoredName)){
					return fh;
				}
			}
    	}
    	return null;
    }
    /**
     * E.G. (LNode;IIZ)Z -> {"LNode;", "I", "I", "Z"}
     * @param desc
     * @return params
     */
    private String[] parseArguments(String desc) {
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
}