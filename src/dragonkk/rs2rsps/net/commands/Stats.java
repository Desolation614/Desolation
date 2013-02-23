package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Stats implements Command {

	@Override
	public void execute(String[] args, Player p) {
		p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>Safe Kills: "+ p.getSafeKills() + " | Safe Deaths: " + p.getSafeDeaths());
		p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>Dangerous Kills: "+ p.getDangerousKills() + " | Dangerous Deaths: " + p.getDangerousDeaths());
	}

}
