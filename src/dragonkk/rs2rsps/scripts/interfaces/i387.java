package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.Scripts;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i387 extends interfaceScript {

	@Override
	public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
		int slots[] = {0, 1, 2, 3, 4, 5, 7, 9, 10, 12, 13};
        int buttonIds[] = {8, 11, 14, 17, 20, 23, 26, 29, 32, 35, 38};
        for(int i = 0; i < buttonIds.length; i++) {
            if(buttonId == buttonIds[i]) {
                Scripts.invokeItemScript((short) buttonId3).option1(p, buttonId3, 387, slots[i]);
            }
        }
		switch (buttonId) {
		case 39:
			p.getFrames().scriptRequest(
					new String[] { "", "", "", "", "Wear<col=ff9040>" },
					-1, 0, 7, 4, 93, 43909120, 149);
			p.getFrames().sendAMask(1026, 670, 0, 0, 27);
			p.getFrames().sendAMask(1086, 667, 15, 0, 13);
			p.getFrames().sendString(
					"Stab: " + p.getCombatDefinitions().getBonus()[0], 667,
					37);
			p.getFrames().sendString(
					"Slash: " + p.getCombatDefinitions().getBonus()[1],
					667, 38);
			p.getFrames().sendString(
					"Crush: " + p.getCombatDefinitions().getBonus()[2],
					667, 39);
			p.getFrames().sendString(
					"Magic: " + p.getCombatDefinitions().getBonus()[3],
					667, 40);
			p.getFrames().sendString(
					"Ranged: " + p.getCombatDefinitions().getBonus()[4],
					667, 41);
			p.getFrames().sendString(
					"Stab: " + p.getCombatDefinitions().getBonus()[5], 667,
					42);
			p.getFrames().sendString(
					"Slash: " + p.getCombatDefinitions().getBonus()[6],
					667, 43);
			p.getFrames().sendString(
					"Crush: " + p.getCombatDefinitions().getBonus()[7],
					667, 44);
			p.getFrames().sendString(
					"Ranged: " + p.getCombatDefinitions().getBonus()[8],
					667, 46);
			p.getFrames().sendString(
					"Summoning: " + p.getCombatDefinitions().getBonus()[9],
					667, 47);
			p.getFrames().sendString(
					"Magic: " + p.getCombatDefinitions().getBonus()[10],
					667, 45);
			p.getFrames().sendString(
					"Strength: " + p.getCombatDefinitions().getBonus()[11],
					667, 49);
			p.getFrames().sendString(
					"Ranged Strength: "
							+ p.getCombatDefinitions().getBonus()[12], 667,
					50);
			p.getFrames().sendString(
					"Prayer: " + p.getCombatDefinitions().getBonus()[13],
					667, 51);
			p.getFrames().sendString("Magic Damage: 0%", 667, 52);
			p.getFrames().sendString("80 kg", 667, 33);
			p.getFrames().sendInterface(667);
			p.getFrames().sendInventoryInterface(670);
			break;
		}
	}

}
