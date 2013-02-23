package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

/**
 * @Author Lay
 * RD 614
 * November 2, 2011
 */
public class StaffZone implements Command {

	/* (non-Javadoc)
	 * @see dragonkk.rs2rsps.net.Command#execute(java.lang.String[], dragonkk.rs2rsps.model.player.Player)
	 */
	@Override
	public void execute(String[] args, final Player p) {
			if(p.getRights() == 0 ) {
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>You have to be a staff member to use this command.");
			return;
		}
		if(p.getSkills().playerDead)
			return;
		if(p.getSkills().tbTimer > 0) {
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>A teleblock spell blocks your teleport!");
			return;
		}
		if(p.getCombat().delay > 0) {
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>Don't try this in combat.");
			return;
		}
		p.animate(9603);
		p.graphics(1684);
		final int x = 3281;
		final int y = 2770;
		GameLogicTaskManager.schedule(new GameLogicTask() {
			int count = 0;
			@Override
			public void run() {
				if(!p.isOnline()) {
					this.stop();
					return;
				}
				if(count++ == 0)
					p.getMask().getRegion().teleport(x, y, 0, 0);
				else {
					//p.animate(8941);
					//p.graphics(1577);
					this.stop();
				}
			}
		}, 2, 0, 0);
  }
}
