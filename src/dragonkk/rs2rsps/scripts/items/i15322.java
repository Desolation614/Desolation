package dragonkk.rs2rsps.scripts.items;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.player.Skills;
import dragonkk.rs2rsps.scripts.itemScript;

public class i15322 extends itemScript {

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
		p.getInventory().deleteItem(15322, 1);
		p.getInventory().addItem(15323, 1);
		    if (!p.isDonator) {
	                p.getFrames().sendChatMessage(0, "You are no longer able to be graced with wisdom.");
	                return;
		    }
				p.getSkills().set(Skills.MAGIC, p.getSkills().getLevelForXp(Skills.MAGIC)+5+ Math.round(p.getSkills().getLevelForXp(Skills.MAGIC)*4/100));
		p.animate(829);
                p.getFrames().sendChatMessage(0, "You drink some of your extreme magic potion.");
                p.getFrames().sendChatMessage(0, "You have 1 doses of potion left.");
		p.getCombatDefinitions().setLastPot(System.currentTimeMillis()+1800);
		p.getCombatDefinitions().setLastFood(System.currentTimeMillis()+1800);
		p.getCombat().delay += 3;
	}
}
