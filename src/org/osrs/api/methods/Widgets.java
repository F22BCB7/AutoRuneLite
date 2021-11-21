package org.osrs.api.methods;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.osrs.api.objects.RSInterface;
import org.osrs.api.objects.RSWidget;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.Widget;
import org.osrs.util.Data;

public class Widgets extends MethodDefinition{
	public Widgets(MethodContext context){
		super(context);
	}
	private RSInterface[] mainCache = new RSInterface[0];
	private final Map<Integer, RSInterface> sparseMap = new HashMap<Integer, RSInterface>();
	public synchronized RSInterface[] getAll() {
		if(Data.clientInstance!=null){
			enlargeCache();
			Widget[][] inters = ((Client)Data.clientInstance).widgets();
			if (inters == null) {
				return new RSInterface[0];
			}
			ArrayList<RSInterface> out = new ArrayList<RSInterface>();
			for (int i = 0; i < inters.length; i++) {
				if (inters[i] != null) {
					RSInterface in = get(i);
					if (in!=null) {
						out.add(in);
					}
				}
			}
			return out.toArray(new RSInterface[out.size()]);
		}
		return new RSInterface[]{};
	}
	public synchronized RSInterface get(int index) {
		RSInterface inter;
		final int cacheLen = mainCache.length;
		if (index < cacheLen) {
			inter = mainCache[index];
			if (inter == null) {
				inter = new RSInterface(methods, index);
				mainCache[index] = inter;
			}
		} else {
			inter = sparseMap.get(index);
			if (inter == null) {
				enlargeCache();
				if (index < cacheLen) {
					inter = mainCache[index];
					if (inter == null) {
						inter = new RSInterface(methods, index);
						mainCache[index] = inter;
					}
				} else {
					inter = new RSInterface(methods, index);
					sparseMap.put(index, inter);
				}
			}
		}
		return inter;
	}
	public RSWidget getChild(int index, int childIndex) {
		try{
			return get(index).getChild(childIndex);
		}catch(Exception e){			
		}
		return null;
	}
	public RSWidget getChild(int index1, int index2, int index3){
		try{
			return get(index1).getChild(index2).getChild(index3);
		}catch(Exception e){			
		}
		return null;
	}
	public RSWidget getChild(int id) {
		final int x = id >> 16;
		final int y = id & 0xFFFF;
		return get(x).getChild(y);
	}
	public synchronized int getMaxCacheSize() {
		enlargeCache();
		return mainCache.length;
	}
	private synchronized void enlargeCache() {
		if(Data.clientInstance!=null){
			Widget[][] inters = ((Client)Data.clientInstance).widgets();
			if (inters != null && mainCache.length < inters.length) { // enlarge
				// cache
				mainCache = Arrays.copyOf(mainCache, inters.length);
				for (int i = mainCache.length; i < mainCache.length; i++) {
					final RSInterface tmp = sparseMap.get(i);
					if (tmp != null) {
						sparseMap.remove(i);
						mainCache[i] = tmp;
					}
				}
			}
		}
	}
	public boolean scrollTo(RSWidget scrollableArea, RSWidget scrollTo, RSWidget scrollBar) {
		if(scrollBar == null)
			return false;
		RSWidget[] scrollbarChildren = scrollBar.getChildren();
		if (scrollableArea==null || scrollTo == null || scrollbarChildren.length<6) {
			return false;
		}
		Point scrollToCenter = scrollTo.getCenterPoint();
		int areaY = scrollbarChildren[4].getAbsoluteY();//Top scroll button
		RSWidget scrollBarArea = scrollbarChildren[0];
		int areaHeight = scrollBarArea.height();
		int contentHeight = scrollableArea.getInternal().scrollHeight();
		int pos = (int) ((float) scrollBarArea.getInternal().scrollHeight() / contentHeight * (scrollTo.relativeY()));
		if (pos < 0){
			pos = 0;
		} 
		else if (pos >= scrollBarArea.getInternal().scrollHeight()) {
			pos = scrollBarArea.getInternal().scrollHeight() - 1;
		}		
		if(scrollToCenter.y >= areaY-15 && scrollToCenter.y <= areaY + areaHeight)
			return true;
		final boolean scrollUp = scrollToCenter.y < areaY;
		RSWidget arrowScroll = scrollbarChildren[scrollUp ? 4 : 5];
		
		Point p = arrowScroll.getCenterPoint();//TODO  change to random
		methods.mouse.move(p);
		try {
			Thread.sleep(new Random().nextInt(500)+500);
		} catch (InterruptedException e) {
		}
		if(arrowScroll.getBounds().contains(p)){
			methods.mouse.press(p.x, p.y, Mouse.LEFT_BUTTON);
			for(int i=0;i<20;++i){
				if(!scrollToCenter.equals(scrollTo.getCenterPoint()))
					i=0;
				scrollToCenter = scrollTo.getCenterPoint();
				if(scrollToCenter.y >= areaY && scrollToCenter.y <= areaY + areaHeight)
					break;
				try {
					Thread.sleep(new Random().nextInt(100)+100);
				} catch (InterruptedException e) {
				}
			}
			methods.mouse.release(p.x, p.y, Mouse.LEFT_BUTTON);
		}
		return (scrollToCenter.y >= areaY && scrollToCenter.y <= areaY + areaHeight);
	}
}
