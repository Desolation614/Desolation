package dragonkk.rs2rsps.scripts;

import dragonkk.rs2rsps.model.player.Player;





/**
 * @author Alex
 * 
 */
public abstract class objectScript {

	public abstract void option1(Player p, int coordX, int coordY, int height);
	public abstract void option2(Player p, int coordX, int coordY, int height);
	public abstract void examine(Player p);
}
