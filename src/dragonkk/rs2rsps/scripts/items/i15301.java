package dragonkk.rs2rsps.scripts.items;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.player.Skills;
import dragonkk.rs2rsps.scripts.itemScript;
import dragonkk.rs2rsps.util.RSTile;

public class i15301 extends itemScript {

    @Override
    public void option1(Player p, int itemId, int interfaceId, int slot) {
	if (p.getInventory().getContainer().get(slot) == null)
	    return;
	if (p.getInventory().getContainer().get(slot).getId() != itemId)
	    return;
	if (interfaceId != 149)
	    return;
	if (System.currentTimeMillis() - p.getCombatDefinitions().getLastPot() < 0)
	    return;
/*	if (p.getRights() < 2) {
	    p.getFrames().sendChatMessage(0, "Sorry, these are admin only atm.");
	    return;
	}*/
/*	if (RSTile.wildernessLevel(p.getLocation()) != 0) {
	    p.getFrames().sendChatMessage(0, "You can not use this potion in the wild.");
	    return;
	}*/
	 if(p.getCombatDefinitions().getSpecpercentage() == 100) {
	     p.getFrames().sendChatMessage(0, "you cant use this pot atm.");
	     }
	if (p.specPot > System.currentTimeMillis()) {
	    p.getFrames().sendChatMessage(0, "You can only use this potion every 30 seconds.");
	    return;
	}
	p.getInventory().deleteItem(15301, 1);
	p.getInventory().addItem(15302, 1);
	p.specPot = System.currentTimeMillis() + 30000;
	if (p.getCombatDefinitions().getSpecpercentage() > 75)
	    p.getCombatDefinitions().specpercentage = 100;
	else
	    p.getCombatDefinitions().specpercentage += 25;
	p.getCombatDefinitions().setSpecialOff();
	p.getCombatDefinitions().refreshSpecial();
	p.animate(829);
	p.getFrames().sendChatMessage(0, "You drink some of your Recover Special Potion.");
	p.getFrames().sendChatMessage(0, "It restores 25% of your special attack.");
	p.getFrames().sendChatMessage(0, "You have 2 doses of potion left.");
	p.getCombatDefinitions().setLastPot(System.currentTimeMillis() + 1800);
	p.getCombatDefinitions().setLastFood(System.currentTimeMillis() + 1800);
	p.getCombat().delay += 3;
    }
}
