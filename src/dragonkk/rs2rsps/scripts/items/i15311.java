package dragonkk.rs2rsps.scripts.items;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.player.Skills;
import dragonkk.rs2rsps.scripts.itemScript;

public class i15311 extends itemScript {

	@Override
	public void option1(Player p, int itemId, int interfaceId, int slot) {
		if(p.getInventory().getContainer().get(slot) == null)
			return;
		if(p.getInventory().getContainer().get(slot).getId() != itemId)
			return;
		if(interfaceId != 149)
			return;
		if(System.currentTimeMillis()-p.getCombatDefinitions().getLastPot() < 0)
			return;
		p.getInventory().deleteItem(15311, 1);
		p.getInventory().addItem(229, 1);
		    if (!p.isDonator) {
	                p.getFrames().sendChatMessage(0, "You notice no increase in accuracy drinking this.");
	                return;
		    }
				p.getSkills().set(Skills.ATTACK, p.getSkills().getLevelForXp(Skills.ATTACK)+5+ Math.round(p.getSkills().getLevelForXp(Skills.ATTACK)*22/100));
		p.animate(829);
                p.getFrames().sendChatMessage(0, "You drink some of your extreme attack potion.");
                p.getFrames().sendChatMessage(0, "You have finished your potion.");
		p.getCombatDefinitions().setLastPot(System.currentTimeMillis()+1800);
		p.getCombatDefinitions().setLastFood(System.currentTimeMillis()+1800);
		p.getCombat().delay += 3;
	}
}
