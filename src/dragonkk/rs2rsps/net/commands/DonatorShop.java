package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class DonatorShop implements Command {

	@Override
	public void execute(String[] args, Player p) {
		if(!p.isDonator) {
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>This command is only for donatons+ only.");
			return;
		}
		World.getShopmanager().initiateShop(p, 0);
	}

}
