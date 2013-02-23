package dragonkk.rs2rsps.event.impl.game;

import dragonkk.rs2rsps.event.ThreadTask;
import dragonkk.rs2rsps.model.player.Player;

public class PlayerUpdateTask implements ThreadTask {
	
	public Player p;
	
	public PlayerUpdateTask(Player p) {
		this.p = p;
	}

	@Override
	public void execute() {
		try {
			if(p == null)
				return;
			p.getGpi().sendUpdate();
			p.getGni().sendUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
