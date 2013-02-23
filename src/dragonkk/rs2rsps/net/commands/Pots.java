package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Pots implements Command {

	@Override
	public void execute(String[] args, Player p) {
		p.getInventory().addItem(2437, 2000);
		p.getInventory().addItem(2441, 2000);
		p.getInventory().addItem(6685, 2000);
		p.getInventory().addItem(3025, 2000);
	}
}