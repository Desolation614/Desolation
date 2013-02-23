package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.Server;

public class CloseLottery implements Command {

	@Override
	public void execute(String[] args, Player p) {
		
		if (p.getRights() > 1) {
			if (Server.lotteryStatus == true) {
				Server.lotteryStatus = false;
				p.getFrames().sendChatMessage(0, "The lottery system is now offline.");
			} else {
				Server.lotteryStatus = true;
				p.getFrames().sendChatMessage(0, "The lottery system is now online.");
			}
		}
	}

}
