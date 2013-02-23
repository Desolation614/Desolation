package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i762 extends interfaceScript {
	@Override
	public void actionButton(Player p, int packetId, int buttonId,
			int buttonId2, int buttonId3) {
		if (buttonId >= 45 && buttonId <= 61) {
			p.setCurrentTab(p.getBank().getArrayIndex(buttonId));
		}
		switch (buttonId) {
		case 33:
			try {
				Item currentItem = null;
				for (int i = 0; i < p.getInventory().getContainer().getSize(); i++) {
					if ((currentItem = p.getInventory().getContainer().get(i)) != null) {
						p.getBank().addItemFromInventoryBankAll(i,
								currentItem.getId(), currentItem.getAmount());
						p.getInventory().getContainer().set(i, null);
					}
				}
				p.getInventory().refresh();
			} catch (Exception e) {
				p.getInventory().refresh();
			}

			break;
		case 19: // Note
			if (p.getBank().withdrawNote == false) {
				p.getBank().withdrawNote = true;
			} else {
				p.getBank().withdrawNote = false;
			}
			break;
		case 15: // Insert
			if (p.getBank().inserting == false) {
				p.getBank().inserting = true;
			} else {
				p.getBank().inserting = false;
			}
			break;
		case 45: // Current tab crap
			p.setCurrentTab(p.getBank().getArrayIndex(buttonId));
			break;
		}
		switch (packetId) {
		case 79: // Withdraw-1
			if (buttonId == 93) {
				p.getBank().deleteItemToInventory(buttonId2, buttonId3, 1,
						false);
			}
			break;
		case 24: // Withdraw-5
			p.getBank().deleteItemToInventory(buttonId2, buttonId3, 5, false);
			break;
		case 48: // Withdraw-10
			p.getBank().deleteItemToInventory(buttonId2, buttonId3, 10, false);
			break;
		case 13: // Withdraw-X
			// TODO
			break;
		case 55: // Withdraw-All
			p.getBank().deleteItemToInventory(buttonId2, buttonId3, 2147483647,
					false);
			break;
		case 0: // Withdraw-All but one
			p.getBank().deleteItemToInventory(buttonId2, buttonId3, 2147483647,
					true);
			break;
		}
	}
}