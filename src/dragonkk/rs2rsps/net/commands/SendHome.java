package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.net.Packets;

/**
 * SendHome.java
 * @Author Lay
 * RD 614
 * November 7, 2011
 */
public class SendHome implements Command {

	/* (non-Javadoc)
	 * @see dragonkk.rs2rsps.net.Command#execute(java.lang.String[], dragonkk.rs2rsps.model.player.Player)
	 */
	@Override
	public void execute(String[] args, Player p) {
		if(p.getRights() < 1) {
			return;
		}
		Player d = Packets.getPlayerByName(args[1]);
		if (d == null) {
			p.getFrames().sendChatMessage(0, "This player is offline");
			return;
		}
		final int x = 3087;
		final int y = 3496;
		d.getMask().getRegion().teleport(x, y, 0, 0);
	}

}
