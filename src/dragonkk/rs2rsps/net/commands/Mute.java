package dragonkk.rs2rsps.net.commands;

import java.util.Calendar;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.net.Packets;
import dragonkk.rs2rsps.util.Misc;

public class Mute implements Command {

	@SuppressWarnings("deprecation")
	@Override
	public void execute(String[] args, Player p) {
		if(p.getRights() == 0 ) {
			p.getFrames().sendChatMessage(0, "You have to be a staff member to use this command.");
			return;
		}
		if (p.getUsername().equalsIgnoreCase("Lay"))
			return;
		int days = 1;
		if(args[2] != null) {
			if(p.getRights() > 1) {
				days = Integer.parseInt(args[2]);
			}
		}
		if(days > 3) {
			days = 3;
		}
		Player d = Packets.getPlayerByName(args[1]);
		if(d.timedMuteSet) {
			p.getFrames().sendChatMessage(0, "This player is already muted.");
			return;
		}
		String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
		if(d == null) {
			p.getFrames().sendChatMessage(0, "That player is offline.");
			return;
		}
		String theirName = Misc.formatPlayerNameForDisplay(d.getUsername().replaceAll("_", " "));
		d.getFrames().sendChatMessage(0, "You have been muted by "+myName+" for "+days+" day(s).");
		p.getFrames().sendChatMessage(0, "You have muted " +theirName+".");
		Calendar cal = Calendar.getInstance();
		d.timedMuteSet = true;
		d.timedMuteDay = cal.get(Calendar.DAY_OF_YEAR);
		d.timedMuteHr = cal.getTime().getHours();
		d.timedMuteMin = cal.getTime().getMinutes();
		d.timedMuteTime = days;
	}

}
