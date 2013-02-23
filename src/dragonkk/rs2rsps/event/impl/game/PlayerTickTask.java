package dragonkk.rs2rsps.event.impl.game;

import dragonkk.rs2rsps.event.ThreadTask;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;

public class PlayerTickTask implements ThreadTask {

	public Player p;

	public PlayerTickTask(Player p) {
		this.p = p;
	}

	@Override
	public void execute() {
		try {
			if(p == null)
				return;
			if (p.isOnline()) {
				p.getWalk().getNextEntityMovement();
			}
			if (p.isOnline()) {
				p.tick();
				p.getCombat().tick();
				p.processQueuedHits();
				World.playerUpdates[p.getIndex()] = p.getMask()
						.isUpdateNeeded();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
