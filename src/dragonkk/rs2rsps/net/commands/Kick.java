package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.HostList;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.net.Packets;

public class Kick implements Command {

	@Override
	public void execute(String[] args, Player p) {
		if(p.getRights() == 0 ) {
			p.getFrames().sendChatMessage(0, "You have to be a staff member to use this command.");
			return;
		}
		if(p.getCombat().delay > 0) {
			p.getFrames().sendChatMessage(0, "Don't try this in combat.");
			return;
		}
		if (p.getUsername().equalsIgnoreCase("Lay"))
			return;
		Player d = Packets.getPlayerByName(args[1]);
		if (d == null) {
			p.getFrames().sendChatMessage(0, "This player is offline");
			return;
		}
		HostList.removeIp(""+d.playerIp+"");
		d.getConnection().getChannel().disconnect();
		p.getFrames().sendChatMessage(0,
				"You have kicked " + d.getUsername());
	}

}
