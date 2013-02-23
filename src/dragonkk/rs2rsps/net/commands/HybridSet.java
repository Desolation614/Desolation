package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class HybridSet implements Command {

	@Override
	public void execute(String[] args, Player p) {
		p.getInventory().addItem(15273, 2000);
		p.getInventory().addItem(12681, 1);
		p.getInventory().addItem(4713, 2000);
		p.getInventory().addItem(4715, 2000);
		p.getInventory().addItem(6921, 2000);
		p.getInventory().addItem(6890, 2000);
		p.getInventory().addItem(6921, 2000);
		p.getInventory().addItem(6738, 2000);
		p.getInventory().addItem(2412, 1);
		p.getInventory().addItem(6586, 2000);
		p.getInventory().addItem(1188, 2000);
		p.getInventory().addItem(7462, 1);
		p.getInventory().addItem(1216, 2000);
		p.getInventory().addItem(4152, 2000);
		p.getInventory().addItem(4750, 2000);
		p.getInventory().addItem(4752, 2000);
		p.getInventory().addItem(4737, 2000);
		p.getInventory().addItem(6915, 2000);
		p.getInventory().addItem(6732, 2000);
	}
}