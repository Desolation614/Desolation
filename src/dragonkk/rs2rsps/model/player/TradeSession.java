package dragonkk.rs2rsps.model.player;

import java.io.Serializable;
import java.text.NumberFormat;

import dragonkk.rs2rsps.model.Container;
import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.util.Misc;
import dragonkk.rs2rsps.util.Serializer;

public class TradeSession implements Serializable {

	private static final long serialVersionUID = -9176611950582062556L;
	public static final int ALL_ITEMS = -5;
	private final Player trader, partner;
	private TradeState currentState = TradeState.STATE_ONE;
	@SuppressWarnings("rawtypes")
	private final Container traderItemsOffered = new Container(28, true),
			partnerItemsOffered = new Container(28, true);
	private boolean traderDidAccept, partnerDidAccept;

	public TradeSession(Player trader, Player partner) {
		this.trader = trader;
		this.partner = partner;
		try {
			trader.didRequestTrade = Boolean.FALSE;
			partner.didRequestTrade = Boolean.FALSE;
			openFirstTradeScreen(trader);
			openFirstTradeScreen(partner);
		} catch (Exception e) {

		}

	}

	public Player getPartner() {
		return partner;
	}

	@SuppressWarnings("unchecked")
	public void openFirstTradeScreen(Player p) {
		try {
			p.getFrames().sendTradeOptions();
			p.getFrames().sendInterface(335);
			p.getFrames().sendInventoryInterface(336);
			p.getFrames().sendItems(90, traderItemsOffered, false);
			p.getFrames().sendItems(90, partnerItemsOffered, true);
			p.getFrames().sendString("", 335, 38);
			p.getFrames().sendString(
					"Trading with: "
							+ Misc
									.formatPlayerNameForDisplay(p
											.equals(trader) ? partner
											.getUsername() : trader
											.getUsername()), 335, 15);
			refreshScreen();
		} catch (Exception e) {
      // guts bo bro
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public String getItemList(Container list1) {
		String list = "";
		for (Item item : list1.getItems()) {
			if (item != null) {
				list = list + "<col=FF9040>" + item.getDefinition().name;
				if (item.getAmount() > 1) {
					list = list + "<col=FFFFFF> x <col=FFFFFF>" + item.getAmount() + "<br>";
				} else {
					list = list + "<br>";
				}
			}
		}
		if (list == "") {
			list = "<col=FFFFFF>Absolutely nothing!";
		}
		return list;
	}

	public void openSecondTradeScreen(Player p) {
		try {
			currentState = TradeState.STATE_TWO;
			partnerDidAccept = false;
			traderDidAccept = false;
			p.getFrames().sendInterface(334);
			p.getFrames().sendString("<col=00FFFF>Trading with:<br><col=00FFFF>"+ Misc.formatPlayerNameForDisplay(p
					.equals(trader) ? partner
					.getUsername() : trader
					.getUsername()), 334, 45);
			p.getFrames().sendInterfaceConfig(334, 37, false);
			p.getFrames().sendInterfaceConfig(334, 41, false);
			p.getFrames().sendInterfaceConfig(334, 45, false);
			p.getFrames().sendInterfaceConfig(334, 46, false);
			p.getFrames().sendString("", 334, 33);
			if(p.getUsername().equalsIgnoreCase(trader.getUsername())) {
				p.getFrames().sendString(getItemList(this.traderItemsOffered),  334, 37);
				p.getFrames().sendString(getItemList(this.partnerItemsOffered),  334, 41);
			} else {
				p.getFrames().sendString(getItemList(this.partnerItemsOffered),  334, 37);
				p.getFrames().sendString(getItemList(this.traderItemsOffered),  334, 41);

			}
		} catch (Exception e) {
			// IG
			e.printStackTrace();
		}
	}

	public void offerItem(Player pl, int slot, int amt) {
		try {
			if (slot < 0 || slot > 28)
				return;
			if (pl.equals(trader)) {
				Item i2 = pl.getInventory().getContainer().get(slot);
				if (i2 == null)
					return;
				if ((i2.getDefinition().lendTemplateID != -1))
					return;
				Item item = new Item(i2.getId(), pl.getInventory()
						.getContainer().getNumberOff(
								pl.getInventory().getContainer().get(slot)
										.getId()));
				if (item != null) {
					if (item.getAmount() > amt) {
						item.setAmount(amt);
					}
					if (traderItemsOffered.getFreeSlots() < item.getAmount()
							&& !pl.getInventory().getContainer().get(slot)
									.getDefinition().isNoted()
							&& !pl.getInventory().getContainer().get(slot)
									.getDefinition().isStackable()) {
						item.setAmount(traderItemsOffered.getFreeSlots());
					}
					traderItemsOffered.add(item);
					pl.getInventory().getContainer().remove(item);
					pl.getInventory().refresh();
				}
			} else if (pl.equals(partner)) {
				Item d = pl.getInventory().getContainer().get(slot);
				if (d == null)
					return;
				if ((d.getDefinition().lendTemplateID != -1))
					return;
				Item item = new Item(d.getId(), pl.getInventory()
						.getContainer().getNumberOff(
								pl.getInventory().getContainer().get(slot)
										.getId()));
				if (item != null) {
					if (item.getAmount() > amt) {
						item.setAmount(amt);
					}
					if (partnerItemsOffered.getFreeSlots() < item.getAmount()
							&& !pl.getInventory().getContainer().get(slot)
									.getDefinition().isNoted()
							&& !pl.getInventory().getContainer().get(slot)
									.getDefinition().isStackable()) {
						item.setAmount(partnerItemsOffered.getFreeSlots());
					}
					partnerItemsOffered.add(item);
					pl.getInventory().getContainer().remove(item);
					pl.getInventory().refresh();
				}
			}
			refreshScreen();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeItem(Player pl, int slot, int amt) {
		try {
			if (pl.equals(trader)) {
				Item i2 = traderItemsOffered.get(slot);
				if(i2 == null)
					return;
				Item item = new Item(i2.getId(),
						traderItemsOffered.getNumberOff(traderItemsOffered.get(
								slot).getId()));
				if (item != null) {
					if (item.getAmount() > amt) {
						item.setAmount(amt);
					}
					if (pl.getInventory().getFreeSlots() < item.getAmount()
							&& !traderItemsOffered.get(slot).getDefinition()
									.isNoted()
							&& !traderItemsOffered.get(slot).getDefinition()
									.isStackable()) {
						item.setAmount(pl.getInventory().getFreeSlots());
					}
					trader.getInventory().getContainer().add(item);
					trader.getInventory().refresh();
					traderItemsOffered.remove(item);
				}
			} else if (pl.equals(partner)) {
				Item i3 = partnerItemsOffered.get(slot);
				if(i3 == null)
					return;
				Item item = new Item(i3.getId(),
						partnerItemsOffered.getNumberOff(partnerItemsOffered
								.get(slot).getId()));
				if (item != null) {
					if (item.getAmount() > amt) {
						item.setAmount(amt);
					}
					if (pl.getInventory().getFreeSlots() < item.getAmount()
							&& !partnerItemsOffered.get(slot).getDefinition()
									.isNoted()
							&& !partnerItemsOffered.get(slot).getDefinition()
									.isStackable()) {
						item.setAmount(pl.getInventory().getFreeSlots());
					}
					partner.getInventory().getContainer().add(item);
					partner.getInventory().refresh();
					partnerItemsOffered.remove(item);
				}
			}
			refreshScreen();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void refreshScreen() {
		try {
			trader.getFrames().sendItems(90, traderItemsOffered, false);
			partner.getFrames().sendItems(90, partnerItemsOffered, false);
			trader.getFrames().sendItems(90, partnerItemsOffered, true);
			partner.getFrames().sendItems(90, traderItemsOffered, true);
			trader.getFrames().sendString(
					Misc.formatPlayerNameForDisplay(partner.getUsername()),
					335, 22);
			trader.getFrames().sendString(
					" has " + partner.getInventory().getFreeSlots()
							+ " free inventory slots.", 335, 21);
			partner.getFrames().sendString(
					Misc.formatPlayerNameForDisplay(trader.getUsername()), 335,
					22);
			partner.getFrames().sendString(
					" has " + trader.getInventory().getFreeSlots()
							+ " free inventory slots.", 335, 21);
			partner.getFrames().sendString(
					"Value: " + formatPrice(getPartnersItemsValue()), 335, 43);
			trader.getFrames().sendString(
					"Value: " + formatPrice(getTradersItemsValue()), 335, 43);
			trader.getFrames().sendString(
					"Value: " + formatPrice(getPartnersItemsValue()), 335, 45);
			partner.getFrames().sendString(
					"Value: " + formatPrice(getTradersItemsValue()), 335, 45);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private long getTradersItemsValue() {
		long initialPrice = 0;
		// for (Item item : traderItemsOffered.getItems()) {
		// if (item != null) {
		// initialPrice += item.getDefinition().getExchangePrice();
		// }
		// }
		return initialPrice;
	}

	private long getPartnersItemsValue() {
		long initialPrice = 0;
		// for (Item item : partnerItemsOffered.getItems()) {
		// if (item != null) {
		// initialPrice += item.getDefinition().getExchangePrice();
		// }
		// }
		return initialPrice;
	}

	@SuppressWarnings("unused")
	private void flashSlot(Player player, int slot) {
		// player.getFrames().scriptRequest( 143, new Object[] { slot, 7, 4,
		// player.equals(trader) ? 21954591 : 21954593 }, "Iiii"); //Guess this
		// wouldn't work for both screens.
	}

	public void acceptPressed(Player pl) {
		try {
			if (!traderDidAccept && pl.equals(trader)) {
				traderDidAccept = true;
			} else if (!partnerDidAccept && pl.equals(partner)) {
				partnerDidAccept = true;
			}
			switch (currentState) {
			case STATE_ONE:
				if (pl.equals(trader)) {
					if (partnerDidAccept && traderDidAccept) {
						openSecondTradeScreen(trader);
						openSecondTradeScreen(partner);
					} else {
						trader.getFrames().sendString(
								"Waiting for other player...", 335, 38);
						partner.getFrames().sendString(
								"The other player has accepted", 335, 38);
					}
				} else if (pl.equals(partner)) {
					if (partnerDidAccept && traderDidAccept) {
						openSecondTradeScreen(trader);
						openSecondTradeScreen(partner);
					} else {
						partner.getFrames().sendString(
								"Waiting for other player...", 335, 38);
						trader.getFrames().sendString(
								"The other player has accepted", 335, 38);
					}
				}
				break;
			case STATE_TWO:
				if (pl.equals(trader)) {
					if (partnerDidAccept && traderDidAccept) {
						giveItems();
					} else {
						trader.getFrames().sendString(
								"Waiting for other player...", 334, 33);
						partner.getFrames().sendString(
								"The other player has accepted", 334, 33);
					}
				} else if (pl.equals(partner)) {
					if (partnerDidAccept && traderDidAccept) {
						giveItems();
					} else {
						partner.getFrames().sendString(
								"Waiting for other player...", 334, 33);
						trader.getFrames().sendString(
								"The other player has accepted", 334, 33);
					}
				}
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void tradeFailed() {
		try {
			for (Item itemAtIndex : traderItemsOffered.getItems()) {
				if (itemAtIndex != null) {
					trader.getInventory().getContainer().add(itemAtIndex);
				}
			}
			for (Item itemAtIndex : partnerItemsOffered.getItems()) {
				if (itemAtIndex != null) {
					partner.getInventory().getContainer().add(itemAtIndex);
				}
			}
			endSession();
			trader.getInventory().refresh();
			partner.getInventory().refresh();
			Serializer.SaveAccount(trader);
			Serializer.SaveAccount(partner);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void endSession() {
		try {
			trader.setTradeSession(null);
			partner.setTradePartner(null);
			trader.getFrames().closeInter();
			partner.getFrames().closeInter();
			trader.getFrames().closeInventoryInterface();
			partner.getFrames().closeInventoryInterface();
			trader.getFrames().sendLoginInterfaces();
			trader.getFrames().sendLoginConfigurations();
			partner.getFrames().sendLoginInterfaces();
			partner.getFrames().sendLoginConfigurations();
			Serializer.SaveAccount(trader);
			Serializer.SaveAccount(partner);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void giveItems() {
		try {
			if (!trader.getInventory().getContainer().hasSpaceFor(
					partnerItemsOffered)) {
				partner
						.getFrames()
						.sendChatMessage(0,
								"The other player does not have enough space in their inventory.");
				trader.getFrames().sendChatMessage(0,
						"You do not have enough space in your inventory.");
				tradeFailed();
				return;
			} else if (!partner.getInventory().getContainer().hasSpaceFor(
					traderItemsOffered)) {
				trader
						.getFrames()
						.sendChatMessage(0,
								"The other player does not have enough space in their inventory.");
				partner.getFrames().sendChatMessage(0,
						"You do not have enough space in your inventory.");
				tradeFailed();
				return;
			}
			for (Item itemAtIndex : traderItemsOffered.getItems()) {
				if (itemAtIndex != null) {
					partner.getInventory().getContainer().add(itemAtIndex);
				}
			}
			for (Item itemAtIndex : partnerItemsOffered.getItems()) {
				if (itemAtIndex != null) {
					trader.getInventory().getContainer().add(itemAtIndex);
				}
			}
			endSession();
			partner.getInventory().refresh();
			trader.getInventory().refresh();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Container getPlayerItemsOffered(Player p) {
		return (p.equals(trader) ? traderItemsOffered : partnerItemsOffered);
	}

	public String formatPrice(long price) {
		return NumberFormat.getInstance().format(price);
	}

	public enum TradeState {
		STATE_ONE, STATE_TWO;
	}
}
