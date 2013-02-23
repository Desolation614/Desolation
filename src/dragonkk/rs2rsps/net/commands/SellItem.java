package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;


public class SellItem implements Command {

	@Override
	public void execute(String[] args, Player p) {
		int itemId = Integer.parseInt(args[1]);
		int price = p.getItemPrice(itemId);
		int amount = Integer.parseInt(args[2]);
		if (Integer.parseInt(args[1]) == 995 || item >= 13887
				&& item <= 13943 && !p.isDonator) {
			return;
		}
		if(p.getInventory().contains(itemId)) {
			p.getInventory().deleteItem(itemId, 1);
			p.getInventory().addItem(995, price);
		} else {
			p.getFrames().sendChatMessage(0, "You need to have the item in your inventory to sell it!");
		}
	}

}
