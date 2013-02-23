package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i334 extends interfaceScript {

	@Override
	public void actionButton(final Player p, final int packetId,
			final int buttonId, final int buttonId2, final int buttonId3) {
		switch (buttonId) {
		case 20:
			try {
			if (p.getTradeSession() != null) {
				p.getTradeSession().acceptPressed(p);
			} else if (p.getTradePartner() != null) {
				p.getTradePartner().getTradeSession().acceptPressed(p);
			}
			} catch(Exception e) {
				
			}
			break;
		case 21:
		case 8:
			try {
			if (p.getTradeSession() != null) {
				p.getTradeSession().tradeFailed();
			} else if (p.getTradePartner() != null) {
				p.getTradePartner().getTradeSession().tradeFailed();
			}
			} catch(Exception e) {
				
			}
			break;
		}
	}
}
