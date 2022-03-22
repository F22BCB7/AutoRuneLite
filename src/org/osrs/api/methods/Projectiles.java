package org.osrs.api.methods;

import java.util.ArrayList;

import org.osrs.api.objects.RSActor;
import org.osrs.api.objects.RSProjectile;
import org.osrs.api.wrappers.Deque;
import org.osrs.api.wrappers.Node;
import org.osrs.api.wrappers.Projectile;

public class Projectiles extends MethodDefinition{
	public Projectiles(MethodContext context){
		super(context);
	}
	public RSProjectile[] getAllProjectiles(){
		ArrayList<RSProjectile> projs = new ArrayList<RSProjectile>();
		Deque projectiles = methods.game.projectileDeque();
		if(projectiles!=null){
			Node head = projectiles.head();
			for(Node node=head.previous();node!=null && node.hashCode()!=head.hashCode();node=node.previous()){
				if(node instanceof Projectile){
					Projectile obj = (Projectile)node;
					projs.add(new RSProjectile(obj));
				}
			}
		}
		return projs.toArray(new RSProjectile[]{});
	}
	public RSProjectile[] getProjectilesTargeting(RSActor actor){
		ArrayList<RSProjectile> projs = new ArrayList<RSProjectile>();
		for(RSProjectile p : getAllProjectiles()){
			if(p.isInteractingWith(actor))
				projs.add(p);
		}
		return projs.toArray(new RSProjectile[]{});
	}
}
