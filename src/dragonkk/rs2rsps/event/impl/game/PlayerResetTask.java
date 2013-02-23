package dragonkk.rs2rsps.event.impl.game;

import dragonkk.rs2rsps.event.ThreadTask;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;

public class PlayerResetTask implements ThreadTask {

	public Player p;

	public PlayerResetTask(Player p) {
		this.p = p;
	}

	@Override
	public void execute() {
		try {
			if(p == null)
				return;
			if (!World.playerUpdates[p.getIndex()]) {
				return;
			}
			World.playerUpdates[p.getIndex()] = false;
			if (p.isOnline()) {
				p.getMask().reset();
			}
		} catch (Exception e) {

		}
	}

}
