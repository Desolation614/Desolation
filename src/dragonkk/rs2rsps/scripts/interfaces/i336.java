package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i336 extends interfaceScript {

	@Override
	public void actionButton(final Player p, final int packetId,
			final int buttonId, final int slot, final int buttonId3) {
	try {
		if (p.getTradeSession() != null) {
			switch (packetId) {
			case 79:
				p.getTradeSession().offerItem(p, slot, 1);
				break;
			case 24:
				p.getTradeSession().offerItem(p, slot, 5);
				break;
			case 48:
				p.getTradeSession().offerItem(p, slot, 10);
				break;
			case 40:
				break;
			// case 13:
			// InputHandler.requestIntegerInput(p, 2,
			// "Please enter an amount:");
			// p.setAttribute("slotId", slot);
			// break;
			case 55:
				p.getFrames().sendChatMessage(
						0,
						"Its a "
								+ p.getTradePartner().getTradeSession()
										.getPlayerItemsOffered(p).get(slot)
										.getDefinition().name);

				// ActionSender.sendMessage(p,
				// p.getTradeSession().getPlayerItemsOffered(p).get(slot).getDefinition().getName()
				// + " is valued at " +
				// p.getTradeSession().getPlayerItemsOffered(p).get(slot).getDefinition().getExchangePrice());
				break;
			}
		} else if (p.getTradePartner() != null) {
			switch (packetId) {
			case 79:
				p.getTradePartner().getTradeSession().offerItem(p, slot, 1);
				break;
			case 24:
				p.getTradePartner().getTradeSession().offerItem(p, slot, 5);
				break;
			case 48:
				p.getTradePartner().getTradeSession().offerItem(p, slot, 10);
				break;
			case 40:
				p.getTradePartner()
						.getTradeSession()
						.offerItem(
								p,
								slot,
								p.getInventory().numberOf(
										p.getInventory().getContainer()
												.get(slot).getId()));
				break;
			// case 13:
			// InputHandler.requestIntegerInput(p, 2,
			// "Please enter an amount:");
			// p.setAttribute("slotId", slot);
			// break;
			case 55:
				p.getFrames().sendChatMessage(
						0,
						"Its a "
								+ p.getTradePartner().getTradeSession()
										.getPlayerItemsOffered(p).get(slot)
										.getDefinition().name);
				// ActionSender.sendMessage(p,
				// p.getTradePartner().getTradeSession().getPlayerItemsOffered(p).get(slot).getDefinition().getName()
				// + " is valued at " +
				// p.getTradePartner().getTradeSession().getPlayerItemsOffered(p).get(slot).getDefinition().getExchangePrice());
				break;
			default:
			}
		}
	} catch(Exception e){
		
	}
	}

}
