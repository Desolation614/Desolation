package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Runes implements Command {

	@Override
	public void execute(String[] args, Player p) {
		p.getInventory().addItem(554, 20000);
		p.getInventory().addItem(555, 20000);
		p.getInventory().addItem(556, 20000);
		p.getInventory().addItem(557, 20000);
		p.getInventory().addItem(558, 20000);
		p.getInventory().addItem(559, 20000);
		p.getInventory().addItem(560, 20000);
		p.getInventory().addItem(561, 20000);
		p.getInventory().addItem(562, 20000);
		p.getInventory().addItem(563, 20000);
		p.getInventory().addItem(564, 20000);
		p.getInventory().addItem(565, 20000);
		p.getInventory().addItem(566, 20000);
		p.getInventory().addItem(9075, 20000);
	}
}