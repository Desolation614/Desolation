package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i335 extends interfaceScript {

	@Override
	public void actionButton(final Player p, final int packetId,
			final int buttonId, final int slot, final int buttonId3) {
		
		/**
		 * Trade
		 */
		// case 335:
		switch (buttonId) {
		/**
		 * Close button.
		 */
		case 18:
		case 12:
			try {
				if (p.getTradeSession() != null) {
					p.getTradeSession().tradeFailed();
				} else if (p.getTradePartner() != null) {
					p.getTradePartner().getTradeSession().tradeFailed();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 16:
			try {
			if (p.getTradeSession() != null) {
				p.getTradeSession().acceptPressed(p);
			} else if (p.getTradePartner() != null) {
				p.getTradePartner().getTradeSession().acceptPressed(p);
			}
			}catch(Exception e) {
				
			}
			break;
		case 31:
			if (p.getTradeSession() != null) {
				
				switch (packetId) {
				case 79:
					p.getTradeSession().removeItem(p, slot, 1);
					break;
				case 24:
					p.getTradeSession().removeItem(p, slot, 5);
					break;
				case 48:
					p.getTradeSession().removeItem(p, slot, 10);
					break;
				case 40:
					p.getTradeSession().removeItem(
							p,
							slot,
							p.getTradeSession()
									.getPlayerItemsOffered(p)
									.getNumberOf(
											p.getTradeSession()
													.getPlayerItemsOffered(p)
													.get(slot)));
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
					// p.getFrames().sendMessage(p.getTradeSession().getPlayerItemsOffered(p).get(slot).getDefinition().name
					// + " is valued at " +
					// p.getTradeSession().getPlayerItemsOffered(p).get(slot).getDefinition().getExchangePrice());
					break;
				default:
				}
			} else if (p.getTradePartner() != null) {
				switch (packetId) {
				case 79:
					p.getTradePartner().getTradeSession()
							.removeItem(p, slot, 1);
					break;
				case 24:
					p.getTradePartner().getTradeSession()
							.removeItem(p, slot, 5);
					break;
				case 48:
					p.getTradePartner().getTradeSession()
							.removeItem(p, slot, 10);
					break;
				case 40:
					p.getTradePartner()
							.getTradeSession()
							.removeItem(
									p,
									slot,
									p.getTradePartner()
											.getTradeSession()
											.getPlayerItemsOffered(p)
											.getNumberOf(
													p.getTradePartner()
															.getTradeSession()
															.getPlayerItemsOffered(
																	p)
															.get(slot)));
					break;
				// case 13:
				// InputHandler.requestIntegerInput(p, 1,
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
					// p.getFrames().sendMessage(p.getTradePartner().getTradeSession().getPlayerItemsOffered(p).get(slot).getDefinition().name
					// + " is valued at " +
					// p.getTradePartner().getTradeSession().getPlayerItemsOffered(p).get(slot).getDefinition().getExchangePrice());
					break;
				}
			}
		default:
		}
		// break;
	}
		
}
