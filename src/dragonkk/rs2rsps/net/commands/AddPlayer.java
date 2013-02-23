package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.net.Packets;

public class AddPlayer implements Command {

	/*
	 * (non-Javadoc)
	 * 
	 * @see dragonkk.rs2rsps.net.Command#execute(java.lang.String[],
	 * dragonkk.rs2rsps.model.player.Player)
	 */
	@Override
	public void execute(String[] args, Player p) {
		if (p.getRights() < 1) {
			return;
		}
		Player d = Packets.getPlayerByName(args[1]);
		if (d == null) {
			p.getFrames().sendChatMessage(0, "This player is offline");
		}
		String name = d.getUsername().toLowerCase();
		Server.tornPlayers.add(name);
		p.getFrames().sendChatMessage(0,
				"You have added the player " + name + " to the PvP tourn list");
	}

}
