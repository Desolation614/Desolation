package dragonkk.rs2rsps.scripts.items;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.Task;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.player.Skills;
import dragonkk.rs2rsps.scripts.itemScript;

public class i385 extends itemScript {

	@Override
	public void option1(final Player p, int itemId, int interfaceId, int slot) {
		if(p.getInventory().getContainer().get(slot) == null)
			return;
		if(p.getInventory().getContainer().get(slot).getId() != itemId)
			return;
		if(interfaceId != 149)
			return;
		if(System.currentTimeMillis()-p.getCombatDefinitions().getLastFood() < 0)
			return;
		p.animate(829);
		p.getCombatDefinitions().setLastFood(System.currentTimeMillis()+1800);
		p.getCombat().delay += 3;
		Server.getEntityExecutor().schedule(new Task()  {
			@Override
			public void run() {
				if(p.isDead())
					return;
				p.getInventory().deleteItem(385, 1);
				p.getSkills().heal(200);
			}
			
		}, 600);
	}
}
