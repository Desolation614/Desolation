package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.net.Packets;

/**
 * TeleToMe.java
 * Author Lay and JB
 */
public class TeleToMe implements Command {

	/* (non-Javadoc)
	 * @see dragonkk.rs2rsps.net.Command#execute(java.lang.String[], dragonkk.rs2rsps.model.player.Player)
	 */
	@Override
	public void execute(String[] args, Player p) {
		if(p.getRights() < 1) {
			p.getFrames().sendChatMessage(0, "You need to be a staff member to use this command.");
			return;
		}
		if(p.getCombat().delay > 0) {
			p.getFrames().sendChatMessage(0, "Don't try this in combat.");
			return;
		}
		Player to = Packets.getPlayerByName(args[1]);
		final int x = p.getLocation().getX();
		final int y = p.getLocation().getY();
		to.getMask().getRegion().teleport(x, y, 0, 0);

	}

}
