package dragonkk.rs2rsps.scripts.items;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.itemScript;
import dragonkk.rs2rsps.util.Misc;

public class i391 extends itemScript {
	
	@Override
	public void option1(Player p, int itemId, int interfaceId, int slot) {
		if(p.getInventory().getContainer().get(slot) == null)
			return;
		if(p.getInventory().getContainer().get(slot).getId() != itemId)
			return;
		if(interfaceId != 149)
			return;
		if(System.currentTimeMillis()-p.getCombatDefinitions().getLastFood() < 0)
			return;
		p.getInventory().deleteItem(391, 1);
		p.animate(829);
		p.getSkills().heal(220);
		p.getCombatDefinitions().setLastFood(System.currentTimeMillis()+1800);
		p.getCombat().delay += 3;
		p.getCombat().queuedSet = false;
		p.getCombat().removeTarget();
	}

}
