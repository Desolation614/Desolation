package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i182 extends interfaceScript {

	@Override
	public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
		if(p.getSkills().playerDead)
			return;
		if(p.getCombat().combatWithDelay > 0)
			return;
		if(p.getCombat().delay > 0)
			return;
		if(System.currentTimeMillis() - p.getCombat().lastHit <= 10000) {
			p.getFrames().sendChatMessage(0, "You must wait atleast 10 seconds to logout from combat.");
			return;
		}
		switch (buttonId) {
		case 7:
			p.getFrames().sendLogout();
			break;
		case 9:
			p.getFrames().sendLogout();
			break;
		}
	}

}
