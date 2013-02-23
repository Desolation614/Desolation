package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.net.Packets;
import dragonkk.rs2rsps.util.Misc;

public class Unmute implements Command {

	@Override
	public void execute(String[] args, Player p) {
		if(p.getRights() == 0 ) {
			p.getFrames().sendChatMessage(0, "You have to be a staff member to use this command.");
			return;
		}
		Player d = Packets.getPlayerByName(args[1]);
		String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
		if(d == null) {
			p.getFrames().sendChatMessage(0, "That player is offline.");
			return;
		}
		d.getFrames().sendChatMessage(0, "You have been unmuted by "+myName+".");
		d.timedMuteSet = false;
	}

}
