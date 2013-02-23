package dragonkk.rs2rsps.scripts.objects;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.Task;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.objectScript;

public class o23271 extends objectScript {

	@Override
	public void examine(Player p) {
		
	}

	@Override
	public void option1(final Player p, final int coordX, final int coordY, final int height) {
		if(p.getWalk().getWalkDir() != -1 || p.getWalk().getRunDir() != -1)
			return;
		if(p.getLocation().getY() == 3520) {
			p.animate(6132);
			Server.getEntityExecutor().schedule(new Task() {
				@Override
				public void run() {
					p.getMask().getRegion().teleport(p.getLocation().getX(), 3523, 0, 0);
				}
			}, 900);
	} else if(p.getLocation().getY() == 3523) {
			p.animate(6132);
			Server.getEntityExecutor().schedule(new Task() {
				@Override
				public void run() {
				p.getMask().getRegion().teleport(p.getLocation().getX(), 3520, 0, 0);
			}
		}, 900);
	}
}
	@Override
	public void option2(Player p, int coordX, int coordY, int height) {
		// TODO Auto-generated method stub
	}
}