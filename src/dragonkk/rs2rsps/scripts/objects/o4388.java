package dragonkk.rs2rsps.scripts.objects;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.objectScript;

public class o4388 extends objectScript {

	@Override
	public void option1(Player p, int coordX, int coordY, int height) {
		Server.castleWars.enterZamorak(p);
		
	}

	@Override
	public void option2(Player p, int coordX, int coordY, int height) {
		
	}

	@Override
	public void examine(Player p) {
		
	}

}
