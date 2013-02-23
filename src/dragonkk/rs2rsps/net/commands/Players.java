package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Players implements Command {

	@Override
	public void execute(String[] args, Player p) {
		p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>There are currently "+World.getPlayers().size()+" players online!");
	}

}
