package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i750 extends interfaceScript {

	@Override
	public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
		switch (buttonId) {
		case 1:
		}
			switch (packetId) {
			case 79:
			p.getWalk().setRunToggled(!p.getWalk().isRunToggled());
			break;
		case 24:
			if(p.resting == false) {
				p.animate(5713);
				p.resting = true;
				p.getFrames().sendConfig(1433, 1);
				p.getFrames().sendConfig(1189, 3833973);
				p.getFrames().sendBConfig(119, 3);
			} else if(p.resting == true) {
				p.getFrames().sendConfig(1433, 0);
				p.getFrames().sendConfig(1189, 3833973);
				p.getFrames().sendBConfig(119, 0);
				p.animate(11788);
				p.resting = false;
			}
			break;
		}
	}
}