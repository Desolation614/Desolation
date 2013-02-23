package dragonkk.rs2rsps.util;

import dragonkk.rs2rsps.model.Entity;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;

public class ProjectileManager {

	public static void sendGlobalProjectile(Entity from, Entity to, int gfxId, int startHeight, int endHeight, int speed, int speed1, int speed2) {
		if(from == null || to == null) {
			return;
		}
		int distance = (int) Math.round(from.getLocation().getDistance(
				to.getLocation())) > 4 ? 75 : 60;
				
		for (Player p : World.getPlayers()) {
			if (p == null)
				continue;
			synchronized (p) {
				if(from == null || to == null) {
					return;
				}
				if (p.isOnline()
						&& (p.getLocation().withinDistance(from.getLocation()) || p.getLocation().withinDistance(to.getLocation()))) {
					p.getFrames().sendProjectile(from, to, gfxId, startHeight, endHeight, speed, distance, speed1, speed2);
				}
			}
		}
	}

}
