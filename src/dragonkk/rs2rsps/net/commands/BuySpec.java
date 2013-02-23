package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

/**
 * BuySpec.java
 * Author Lay
 */
public class BuySpec implements Command {

	
	@Override
	public void execute(String[] args, Player p) {
		if(!p.isDonator) {
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>You need to be a premium member to use this command!");
			return;
		}
		if(p.dangerousKillCount < 50) {
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>You already know the god's secret. Why would you need to learn it again?!");
			return;
		}
		if(p.getInventory().contains(995, 5000000)) {
			p.korasiSpec = true;
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>The god's whisper a secret into your ear...you feel you've learned a new talent.");
			p.getInventory().deleteItem(995, 5000000);
			return;
		} else {
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>You need 5m coins to buy this talent.");
		}
	}

}
