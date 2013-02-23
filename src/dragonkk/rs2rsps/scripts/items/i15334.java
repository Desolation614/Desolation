package dragonkk.rs2rsps.scripts.items;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.Task;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.player.Skills;
import dragonkk.rs2rsps.scripts.itemScript;

public class i15334 extends itemScript {

	@Override
	public void option1(final Player p, int itemId, int interfaceId, int slot) {
		if(p.getInventory().getContainer().get(slot) == null)
			return;
		if(p.getInventory().getContainer().get(slot).getId() != itemId)
			return;
		if(interfaceId != 149)
			return;
		if(System.currentTimeMillis()-p.getCombatDefinitions().getLastPot() < 0)
			return;
		p.getInventory().deleteItem(15334, 1);
		p.getInventory().addItem(15335, 1);
		    if (!p.extremeDonator) {
	                p.getFrames().sendChatMessage(0, "The God's of pvp choose not to aid you in battle.");
	                return;
		    }
		p.getSkills().set(Skills.STRENGTH, p.getSkills().getLevelForXp(Skills.STRENGTH)+5+ Math.round(p.getSkills().getLevelForXp(Skills.STRENGTH)*22/100));
		p.getSkills().set(Skills.DEFENCE, p.getSkills().getLevelForXp(Skills.DEFENCE)+5+ Math.round(p.getSkills().getLevelForXp(Skills.DEFENCE)*22/100));
		p.getSkills().set(Skills.ATTACK, p.getSkills().getLevelForXp(Skills.ATTACK)+5+ Math.round(p.getSkills().getLevelForXp(Skills.ATTACK)*22/100));
		p.getSkills().set(Skills.RANGE, p.getSkills().getLevelForXp(Skills.RANGE)+5+ Math.round(p.getSkills().getLevelForXp(Skills.RANGE)*22/100));
		p.getSkills().set(Skills.MAGIC, p.getSkills().getLevelForXp(Skills.MAGIC)+5+ Math.round(p.getSkills().getLevelForXp(Skills.MAGIC)*4/100));
		p.animate(829);
		p.getCombatDefinitions().setLastPot(System.currentTimeMillis()+1800);
		p.getCombatDefinitions().setLastFood(System.currentTimeMillis()+1800);
		p.getCombat().delay += 3;
	}
}