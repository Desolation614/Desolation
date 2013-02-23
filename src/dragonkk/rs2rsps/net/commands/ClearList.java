package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class ClearList implements Command {

	/* (non-Javadoc)
	 * @see dragonkk.rs2rsps.net.Command#execute(java.lang.String[], dragonkk.rs2rsps.model.player.Player)
	 */
	@Override
	public void execute(String[] args, Player p) {
		if(p.getRights() < 1) {
			return;
		}
		Server.tornPlayers.clear();
		p.getFrames().sendChatMessage(0,"You have cleared the PvP tourn list.");

	}

}
