package dragonkk.rs2rsps.scripts.items;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.player.Skills;
import dragonkk.rs2rsps.scripts.itemScript;

public class i6691 extends itemScript {
	
	public void option1(Player p, int itemId, int interfaceId, int slot) {
		if(p.getInventory().getContainer().get(slot) == null)
			return;
		if(p.getInventory().getContainer().get(slot).getId() != itemId)
			return;
		if(interfaceId != 149)
			return;
		if(System.currentTimeMillis()-p.getCombatDefinitions().getLastPot() < 0)
			return;
		p.getInventory().deleteItem(6691, 1);
		p.getInventory().addItem(229, 1);
		p.getSkills().healBrew(160);
		p.getSkills().set(Skills.DEFENCE, (int)(p.getSkills().getLevelForXp(Skills.DEFENCE)*1.25));
		p.getSkills().set(Skills.STRENGTH, (int)(p.getSkills().getLevel(2) * 0.9));
		p.getSkills().set(Skills.ATTACK, (int)(p.getSkills().getLevel(0) * 0.9));
		p.getSkills().set(Skills.RANGE, (int)(p.getSkills().getLevel(Skills.RANGE) * 0.9));
		p.getSkills().set(Skills.MAGIC, (int)(p.getSkills().getLevel(Skills.MAGIC) * 0.9));
		p.animate(829);
		p.getCombatDefinitions().setLastPot(System.currentTimeMillis()+1800);
		p.getCombat().delay += 3;
	}
}