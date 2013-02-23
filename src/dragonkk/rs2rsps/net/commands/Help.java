package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Help implements Command {

	@Override
	public void execute(String[] args, Player p) {
		p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>Please pm a moderator for help. Tickets will be enabled soon.");
	}

}
