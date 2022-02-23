package org.osrs.api.wrappers.proxies;

import org.osrs.api.objects.RSModel;
import org.osrs.api.wrappers.proxies.Item;
import org.osrs.api.wrappers.ItemDefinition;
import org.osrs.api.wrappers.proxies.RenderableNode;
import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BGetter;

@BClass(name="ItemLayer")
public class ItemLayer implements org.osrs.api.wrappers.ItemLayer{

	@BField
	public RenderableNode bottom;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RenderableNode bottom(){return bottom;}
	@BField
	public int x;
	@BGetter
	@Override
	public int x(){return x;}
	@BField
	public int y;
	@BGetter
	@Override
	public int y(){return y;}
	@BField
	public int tileHeight;
	@BGetter
	@Override
	public int tileHeight(){return tileHeight;}
	@BField
	public long hash;
	@BGetter
	@Override
	public long hash(){return hash;}
	@BField
	public RenderableNode middle;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RenderableNode middle(){return middle;}
	@BField
	public RenderableNode top;
	@BGetter
	@Override
	public org.osrs.api.wrappers.RenderableNode top(){return top;}
	@BField
	public int height;
	@BGetter
	@Override
	public int height(){return height;}
	
	@BFunction
	@Override
	public RSModel getModel(){
		RSModel model = null;
		if(bottom!=null){
			Item item = (Item)bottom;
			ItemDefinition modelDef = Client.clientInstance.invoke_getItemDefinition(item.id());
			if(modelDef!=null){
				model = modelDef.getCachedModel();
			}
		}
		if(middle!=null){
			Item item = (Item)middle;
			ItemDefinition modelDef = Client.clientInstance.invoke_getItemDefinition(item.id());
			if(modelDef!=null){
				if(model==null)
					model = modelDef.getCachedModel();
				else
					model.appendModel(modelDef.getCachedModel());
			}
		}
		if(top!=null){
			Item item = (Item)top;
			ItemDefinition modelDef = Client.clientInstance.invoke_getItemDefinition(item.id());
			if(modelDef!=null){
				if(model==null)
					model = modelDef.getCachedModel();
				else
					model.appendModel(modelDef.getCachedModel());
			}
		}
		return model;
	}
}