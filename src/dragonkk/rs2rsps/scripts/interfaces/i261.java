package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i261 extends interfaceScript {

	@Override
	public void actionButton(Player p, int packetId, int buttonId,
			int buttonId2, int buttonId3) {
		//Logger.log(this, "Button on equipment: "+buttonId+", "+buttonId2+", "+buttonId3);
		switch (buttonId) {
		case 16:
			p.getFrames().sendInterface(742);
			break;
		}
	}
}