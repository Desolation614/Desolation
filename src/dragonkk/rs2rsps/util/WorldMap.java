package dragonkk.rs2rsps.util;

import dragonkk.rs2rsps.model.player.Player;

public class WorldMap {

	public static void appendMap(Player p) {
		p.getFrames().sendWindowsPane((short)755, (byte)0);
	}

}
