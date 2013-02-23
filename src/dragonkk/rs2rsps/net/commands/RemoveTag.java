package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;


//Author Lay


public class RemoveTag implements Command {

	@Override
	public void execute(String[] args, Player p) {
		if(!p.extremeDonator) {
			p.getFrames().sendChatMessage(0, "You must be an extreme member to change your tag.");
			return;
		}

		p.tag = "";
		p.getFrames().sendChatMessage(0, "Your tag has been removed.");
	}

}
