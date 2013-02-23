package dragonkk.rs2rsps.scripts.objects;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.objectScript;

public class o4415 extends objectScript {

	@Override
	public void option1(Player p, int coordX, int coordY, int height) {
		if(p.getLocation().getLocalX() == 2416 && p.getLocation().getLocalY() == 3075) {
			p.getMask().getRegion().teleport(2417, 3078, 0, 0);
		}
		if(p.getLocation().getLocalX() == 2417 && p.getLocation().getLocalY() == 3078) {
			p.getMask().getRegion().teleport(2416, 3075, 0, 0);
		}
		
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
