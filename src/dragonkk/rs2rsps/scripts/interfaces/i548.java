package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i548 extends interfaceScript {

	/* (non-Javadoc)
	 * @see dragonkk.rs2rsps.scripts.interfaceScript#actionButton(dragonkk.rs2rsps.model.player.Player, int, int, int, int)
	 */
	@Override
	public void actionButton(Player p, int packetId, int buttonId,
			int buttonId2, int buttonId3) {
		switch(packetId) {
		case 52:
			if(buttonId == 185) {
				p.xpGained = 0;
				p.getSkills().sendSkillLevels();
			}
		break;
		}

	}

}
