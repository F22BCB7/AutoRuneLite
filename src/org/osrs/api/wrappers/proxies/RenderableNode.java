package org.osrs.api.wrappers.proxies;

import org.osrs.api.objects.RSModel;
import org.osrs.injection.MethodHook;
import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BVar;
import org.osrs.util.Data;

@BClass(name="RenderableNode")
public class RenderableNode extends EntityNode implements org.osrs.api.wrappers.RenderableNode{
	@BField
	public int height;
	@BGetter
	@Override
	public int height(){return height;}

	@BVar
	public org.osrs.api.objects.RSModel cachedModel;
	@BFunction
	@Override
	public org.osrs.api.objects.RSModel getCachedModel(){
		return cachedModel;
	}
	@BFunction
	public void setCachedModel(org.osrs.api.objects.RSModel model){
		cachedModel = model;
	}
	@BMethod(name="getModel")
	public Model _getModel(int a){return null;}
	@BMethod(name="getModel")
	public Model _getModel(byte a){return null;}
	@BMethod(name="getModel")
	public Model _getModel(short a){return null;}
	@BDetour
	public Model getModel(int a){return (Model)invoke_getModel();}
	@BDetour
	public Model getModel(byte a){return (Model)invoke_getModel();}
	@BDetour
	public Model getModel(short a){return (Model)invoke_getModel();}
	@BFunction
	public org.osrs.api.wrappers.Model invoke_getModel(){
		org.osrs.api.wrappers.Model model = null;
		MethodHook mh = Data.clientModscript.resolver.getMethodHook("RenderableNode", "getModel", "(?)L*;", false);
		if(mh!=null){
			Object predicate = mh.predicate;
			if(mh.desc.startsWith("(I)L"))
				model = _getModel((int)predicate);
			else if(mh.desc.startsWith("(B)L"))
				model = _getModel((byte)predicate);
			else if(mh.desc.startsWith("(S)L"))
				model = _getModel((short)predicate);
		}

		if(model!=null){
			if(cachedModel!=null)
				cachedModel.updateModel(model);
			else
				cachedModel = new RSModel(model);
		}
		else{
			this.cachedModel=null;
		}
		return model;
	}
}