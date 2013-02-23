package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.net.Packets;

public class Teleto implements Command {

	@Override
	public void execute(String[] args, final Player p) {
		if(p.getRights() < 1) {
			p.getFrames().sendChatMessage(0, "You need to be a staff member to use this command.");
			return;
		}
		if(p.getCombat().delay > 0) {
			p.getFrames().sendChatMessage(0, "Don't try this in combat.");
			return;
		}
		Player to = Packets.getPlayerByName(args[1]);
		final int x = to.getLocation().getX();
		final int y = to.getLocation().getY();
		p.animate(9603);
		p.graphics(1684);
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
		}, 3, 0, 0);
	}

}
