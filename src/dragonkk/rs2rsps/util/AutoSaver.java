package dragonkk.rs2rsps.util;

import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;

public class AutoSaver implements Runnable {

	@Override
	public void run() {
		Logger.log(this, "Autosaver started!");
		int saver = 0;
		while(true) {
			if(saver == 0) {
				saver++;
				continue;
			}
			try {
				for(Player p :World.getPlayers()) {
					if(p == null)
						continue;
					Serializer.SaveAccount(p);
				}
				Logger.log(this, "Saved games for "+World.getPlayers().size()+" players!");
				Thread.sleep(30000);
			} catch(Exception e) {
			}
		}

	}

}
