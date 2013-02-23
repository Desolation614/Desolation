package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Empty implements Command {

	@Override
	public void execute(String[] args, Player p) {
		if(!p.getCombat().isSafe(p)){
			p.getFrames().sendChatMessage(0, "You cannot use this command in the wilderness.");
			return;
		}
		p.getInventory().reset();
	}

}
