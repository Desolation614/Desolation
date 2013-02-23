package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class GetDrops implements Command {

	@Override
	public void execute(String[] args, Player p) {
		if(p.getSkills().drops.length < 1) {
			p.getFrames().sendChatMessage(0, "You don't have any drops to collect.");
			return;
		}
		if(p.getSkills().drops.length > p.getInventory().getFreeSlots()) {
			p.getFrames().sendChatMessage(0, "You don't have enough inventory space to claim your drops.");
			return;
		}
		for(int i = 0; i < p.getSkills().drops.length ; i++) {
			if(p.getSkills().drops[i] == -1)
				continue;
			p.getInventory().addItem(p.getSkills().drops[i], 1);
		}
		p.getFrames().sendChatMessage(0, "You have sucessfully claimed your drops.");
		for(int i = 0; i < p.getSkills().drops.length ; i++) {
			p.getSkills().drops[i] = -1;
		}
	}

}
