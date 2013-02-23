package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class EdgeTele implements Command {

	@Override
	public void execute(String[] args, final Player p) {
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
		p.getCombatDefinitions().doEmote(8939, 1576, 4200);
		GameLogicTaskManager.schedule(new GameLogicTask() {
			int count = 0;
			@Override
			public void run() {
				if(!p.isOnline()) {
					this.stop();
					return;
				}
				if(count++ == 0)
					p.getMask().getRegion().teleport(3085, 3520, 0, 0);
				else {
					p.animate(8941);
					p.graphics(1577);
					this.stop();
				}
			}
		}, 3, 0, 0);
	}
}
