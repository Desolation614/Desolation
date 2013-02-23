package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class ExTeleport implements Command {

	/* (non-Javadoc)
	 * @see dragonkk.rs2rsps.net.Command#execute(java.lang.String[], dragonkk.rs2rsps.model.player.Player)
	 */
	@Override
	public void execute(String[] args, final Player p) {
		if(p.getSkills().playerDead)
			return;
		if(p.getSkills().tbTimer > 0) {
			p.getFrames().sendChatMessage(0, "A teleblock spell blocks your teleport!");
			return;
		}
		if(p.getCombat().delay > 0) {
			p.getFrames().sendChatMessage(0, "Don't try this in combat.");
			return;
		}
		if(!p.setClan.equalsIgnoreCase("fagex")) {
			return;
		}
		p.animate(9603);
		p.graphics(1684);
		final int x = 2204;
		final int y = 3254;
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
				p.getFrames().sendChatMessage(0, "Welcome home bro.");
			}
		}, 3, 0, 0);

	}

}
