package dragonkk.rs2rsps.scripts.objects;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.objectScript;

public class o4387 extends objectScript {

	@Override
	public void option1(Player p, int coordX, int coordY, int height) {
		Server.castleWars.enterSaradomin(p);
		
	}

	@Override
	public void option2(Player p, int coordX, int coordY, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void examine(Player p) {
		// TODO Auto-generated method stub
		
	}

}