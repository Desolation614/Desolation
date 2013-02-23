package dragonkk.rs2rsps.scripts.items;

import dragonkk.rs2rsps.model.minigames.DiceGame;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.itemScript;

public class i15098 extends itemScript {
	
	public void option1(Player p, int itemId, int interfaceId, int slot) {
		DiceGame.rollDice(p);
	}
}