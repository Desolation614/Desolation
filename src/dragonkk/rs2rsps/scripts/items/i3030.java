package dragonkk.rs2rsps.scripts.items;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.player.Skills;
import dragonkk.rs2rsps.scripts.itemScript;

public class i3030 extends itemScript {
	
	public void option1(Player p, int itemId, int interfaceId, int slot) {
		if(p.getInventory().getContainer().get(slot) == null)
			return;
		if(p.getInventory().getContainer().get(slot).getId() != itemId)
			return;
		if(interfaceId != 149)
			return;
		if(System.currentTimeMillis()-p.getCombatDefinitions().getLastPot() < 0)
			return;
		p.getInventory().deleteItem(3030, 1);
		p.getInventory().addItem(229, 1);
		for(int i = 0; i < Skills.SKILL_NAME.length; i++) {
			if(p.getSkills().getLevel(i) >= p.getSkills().getLevelForXp(i)) {
				continue;
			}
			p.getSkills().set(i, p.getSkills().getLevelForXp(i));
		}
		p.animate(829);
		p.getCombatDefinitions().setLastPot(System.currentTimeMillis()+1800);
		p.getCombat().delay += 3;
	}
}