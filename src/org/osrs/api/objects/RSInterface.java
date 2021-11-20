package org.osrs.api.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.methods.MethodDefinition;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.Widget;
import org.osrs.util.Data;

public class RSInterface{
	private MethodContext methods;
	public int index;
	private RSWidget[] cachedChildren = new RSWidget[]{};
	private final HashMap<Integer, RSWidget> sparseMap = new HashMap<Integer, RSWidget>();
	public RSInterface(MethodContext context, int i){
		methods = ((Client)Data.clientInstance).getMethodContext();
		index=i;
	}
	public RSWidget getChild(int ind){
		Widget[] childs = ((Client)Data.clientInstance).widgets()[index];
		if(childs==null || childs.length<=ind)
			return null;
		RSWidget temp=null;
		int cacheLength = cachedChildren.length;
		if(ind<cacheLength){
			temp = cachedChildren[ind];
			if(temp==null){
				temp = new RSWidget(this, ind);
				cachedChildren[ind] = temp;
			}
		}
		else{
			temp = sparseMap.get(ind);
			if(temp==null){
				enlargeCache();
				if(ind<cacheLength){
					temp = cachedChildren[ind];
					if(temp==null){
						temp = new RSWidget(this, ind);
						cachedChildren[ind]=temp;
					}
				}
				else{
					temp = new RSWidget(this, ind);
					sparseMap.put(ind, temp);
				}
			}
		}
		return temp;
	}
	public synchronized int getChildCount(){
		enlargeCache();
		return cachedChildren.length;
	}
	public RSWidget[] getChildren(){
		enlargeCache();
		Widget[] children = getChildrenInternal();
		if(children==null || children.length==0)
			return new RSWidget[]{};
		ArrayList<RSWidget> out = new ArrayList<RSWidget>();
		for(int i=0;i<children.length;++i){
			RSWidget tmp = getChild(i);
			if(tmp!=null)
				out.add(tmp);
		}
		return out.toArray(new RSWidget[]{});
	}	
	public Widget[] getChildrenInternal(){
		Widget[][] interfaces = ((Client)Data.clientInstance).widgets();
		if(interfaces!=null && interfaces.length>index)
			return interfaces[index];
		return null;
	}
	private synchronized void enlargeCache() {
		Widget[] childs = getChildrenInternal();
		if(childs!=null && cachedChildren.length<childs.length){
			cachedChildren = Arrays.copyOf(cachedChildren, childs.length);
			for(int i=0;i<cachedChildren.length;++i){
				RSWidget tmp = sparseMap.get(i);
				if(tmp!=null){
					sparseMap.remove(i);
					cachedChildren[i]=tmp;
				}
			}
		}
	}
}
