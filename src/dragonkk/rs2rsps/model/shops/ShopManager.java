package dragonkk.rs2rsps.model.shops;

import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.model.player.Player;

/**
 * @author Hadyn Fitzgerald
 * @version 1.0
 */
public class ShopManager {

	public Shops shops;

	public ShopManager() {
		shops = new Shops();
	}

	public void initiateShop(Player player, int i) {
		player.getFrames()
				.sendItems(
						3,
						shops.getShop(player.getShophandler().getShopId()).items,
						false);
		player.getFrames().sendConfig(118, 3);
		player.getFrames().sendConfig(1496, 553);
		player.getFrames().sendConfig(532, 995);
		player.getFrames().sendBConfig(199, -1);
		player.getFrames().sendInterface(620);
		player.getFrames().sendInventoryInterface(621);
		for (int index = 0; index < 40; index++) {
			if (index == 0) {
				player.getFrames().sendBConfig(946 + index, 1000);
				continue;
			}
			player.getFrames().sendBConfig(946 + index, -1);
		}
		Object[] params = new Object[] { "Sell 50", "Sell 10", "Sell 5",
				"Sell 1", "Value", -1, 1, 7, 4, 93, 40697856 };
		player.getFrames().sendClientScript(149, params, "IviiiIsssss");
		player.getFrames().sendAMask(0, 27, 621, 0, 36, 1086);
		player.getFrames().sendAMask(0, 12, 620, 26, 0, 1150);
		player.getFrames().sendAMask(0, 240, 620, 25, 0, 1150);
	}

	public void handleSellButtons(Player p, int packetid, int slot, int itemid) {
		switch (packetid) {
		case 24:
			sellItem(p, slot, 1, itemid);
			break;

		case 48:
			sellItem(p, slot, 5, itemid);
			break;

		case 40:
			sellItem(p, slot, 10, itemid);
			break;

		case 13:
			sellItem(p, slot, 50, itemid);
			break;
		}
	}

	public void sellItem(Player p, int slot, int amount, int itemid) {
		Shop shop = shops.getShop(p.getShophandler().getShopId());
		if (!shop.sellsItem(itemid)) {
			p.getFrames().sendChatMessage(0,
					"You cannot sell this item to the shop.");
			return;
		}
		int shopslot = shop.getSlotId(itemid);
		int value = shop.items.get(shopslot)[2];
		if (!p.getInventory().hasRoomFor(995, value)) {
			p.getFrames().sendChatMessage(0,
					"You don't have room to sell this item.");
			return;
		}
		if (p.getInventory().numberOf(itemid) < amount) {
			shop.sellItem(shopslot, p.getInventory().numberOf(itemid));
			p.getInventory().addItem(995,
					value * p.getInventory().numberOf(itemid));
		} else {
			shop.sellItem(shopslot, amount);
			p.getInventory().addItem(995, value * amount);
		}
		p.getInventory().deleteItem(itemid, amount);
		p.getInventory().refresh();
		p.getFrames().sendItems(3,
				shops.getShop(p.getShophandler().getShopId()).items, false);
	}

	public void handleShopButtons(Player p, int buttonid, int packetid, int slot) {
		switch (buttonid) {
		case 25:
			switch (packetid) {
			case 24:
				buyItem(p, slot, 1);
				break;

			case 48:
				buyItem(p, slot, 1);
				break;

			case 40:
				buyItem(p, slot, 1);
				break;

			case 13:
				buyItem(p, slot, 1);
				break;

			case 55:
				buyItem(p, slot, 1);
				break;
			}
			break;
		}
	}

	public void buyItem(Player p, int slot, int amount) {
		try {
			int itemId = shops.getShop(p.getShophandler().getShopId()).items
					.get(slot)[0];
			Item item = new Item(itemId, amount);
			int itemworth = shops.getShop(p.getShophandler().getShopId()).items
					.get(slot)[2];
			if (!p.getInventory().hasRoomFor(itemId, amount)) {
				p.getFrames().sendChatMessage(0,
						"You don't have room to buy this item.");
				return;
			}
			if (shops.getShop(p.getShophandler().getShopId()).items.get(slot)[1]
					- amount < 0) {
				p.getFrames().sendChatMessage(0,
						"Not enough stock to sell this item.");
				return;
			}
			if (p.getInventory().numberOf(995)  >= itemworth * amount) {
				p.getInventory().addItem(itemId, amount);
				p.getInventory().deleteItem(995, amount * itemworth);
				shops.getShop(p.getShophandler().getShopId()).buyItem(slot,
						amount);
				p.getFrames().sendItems(3,
						shops.getShop(p.getShophandler().getShopId()).items,
						false);
			} else {
				p.getFrames().sendChatMessage(
						0,
						"You need " + amount * itemworth
								+ " tokens to buy this.");
			}
		} catch (Exception e) {

		}
	}
}