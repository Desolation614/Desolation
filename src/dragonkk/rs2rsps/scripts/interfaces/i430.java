package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i430 extends interfaceScript {

	@Override
	public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
		switch(buttonId) {
		case 36:
			if(p.getSkills().level[6] < 94) {
				p.getFrames().sendChatMessage(0, "You need a magic level of 94 to cast vengeance");
				return;
			}
			if(p.getCombat().vengeance) {
				p.getFrames().sendChatMessage(0, "You already have vengeance casted.");
				return;
			}
			if(p.getCombat().vengDelay > 0) {
				p.getFrames().sendChatMessage(0, "You need to wait 30 seconds before you can cast vengeance again.");
				return;
			}
			p.animate(4410);
			p.graphics2(726);
			p.getCombat().vengeance = true;
			p.getCombat().vengDelay = 60;
			break;
		}
	}

}
