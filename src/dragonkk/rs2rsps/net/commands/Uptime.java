package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Uptime implements Command {

// Author Lay

	@Override
	public void execute(String[] args, Player p) {
		String upTime = "";
		String hours = "";
		String mins = "";
		String secs = "";
		if(World.seconds < 10) {
			secs = "0"+World.seconds;
		} else {
			secs = ""+World.seconds;
		}
		if(World.mins < 10) {
			mins = "0"+World.mins;
		} else {
			mins = ""+World.mins;
		}
		if(World.hours < 10) {
			hours = "0"+World.hours;
		} else {
			hours = ""+World.hours;
		}
		upTime = ""+hours+":"+mins+":"+secs;
		p.getFrames().sendChatMessage(0, "Uptime: "+upTime);

	}

}
