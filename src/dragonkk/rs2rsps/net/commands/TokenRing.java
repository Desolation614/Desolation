package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class TokenRing implements Command {

	@Override
	public void execute(String[] args, Player p) {
		
		if (p.hasTokenRing()) {
			p.getInventory().addItem(6575, 1);
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>Thank you for donating. Enjoy your ring of 2x tokens.");
		} else {
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>You must donate for this item. Check the forums.");
		}
	}

}
