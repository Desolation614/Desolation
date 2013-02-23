package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Food implements Command {

	@Override
	public void execute(String[] args, Player p) {
		p.getInventory().addItem(15273, 20000);
	}
}