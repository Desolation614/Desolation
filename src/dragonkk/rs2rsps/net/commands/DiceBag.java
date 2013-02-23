package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

// Author Lay

public class DiceBag implements Command {

	@Override
	public void execute(String[] args, Player p) {
		
		if(p.dangerousKillCount < 20) {
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>You need 20 kills and 50m to spawn this item!");
			return;
		}
		if(p.getInventory().contains(995, 50000000)) {
      p.getInventory().addItem(15098, 1);
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>Scamming will get you IP banned!");
			p.getInventory().deleteItem(995, 50000000);
			return;
		} else {
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>You need 50m and 20 kills to buy this item.");
		}
	}

}