package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Master implements Command {

	@Override
	public void execute(String[] args, Player p) {
		if (p.getCombat().hasTarget()) {
			p.getFrames().sendChatMessage(0, "Don't even try this in the wild.");
			return;
		}
		for (int i = 0; i < 25; i++) {
			if(i == 23) 
				continue;
			p.getSkills().addXp(i, 13100000);
		}

	}

}
