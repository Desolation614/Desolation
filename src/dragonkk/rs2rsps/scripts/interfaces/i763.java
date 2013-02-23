package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i763 extends interfaceScript {

	@Override
	public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
		switch(packetId) {
		case 79: // Deposit-1
			p.getBank().addItemFromInventory(buttonId2, buttonId3, 1);
		break;
		case 24: // Deposit-5
			p.getBank().addItemFromInventory(buttonId2, buttonId3, 5);
		break;
		case 48: // Deposit-10
			p.getBank().addItemFromInventory(buttonId2, buttonId3, 10);
		break;
		case 13: // Deposit-X
			//TODO
		break;
		case 55: // Deposit-All
			p.getBank().addItemFromInventory(buttonId2, buttonId3, 2147483647);
		break;
		}
	}
}