package dragonkk.rs2rsps.scripts.items;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.player.Skills;
import dragonkk.rs2rsps.scripts.itemScript;

public class i15318 extends itemScript {

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
		p.getInventory().deleteItem(15318, 1);
		p.getInventory().addItem(15319, 1);
		    if (!p.isDonator) {
	                p.getFrames().sendChatMessage(0, "You have lost the ability to master the art of defense.");
	                return;
		    }
		p.getSkills().set(Skills.DEFENCE, p.getSkills().getLevelForXp(Skills.DEFENCE)+5+ Math.round(p.getSkills().getLevelForXp(Skills.DEFENCE)*22/100));
		p.animate(829);
                p.getFrames().sendChatMessage(0, "You drink some of your extreme defence potion.");
                p.getFrames().sendChatMessage(0, "You have 1 doses of potion left.");
		p.getCombatDefinitions().setLastPot(System.currentTimeMillis()+1800);
		p.getCombatDefinitions().setLastFood(System.currentTimeMillis()+1800);
		p.getCombat().delay += 3;
	}
}
