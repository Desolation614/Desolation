package dragonkk.rs2rsps.net;

import dragonkk.rs2rsps.model.player.Player;

public interface Command {
	
	public void execute(String[] args, Player p);

}
