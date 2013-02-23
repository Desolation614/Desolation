package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class SetGender implements Command {

	@Override
	public void execute(String[] args, Player p) {
		int gender = Integer.parseInt(args[1]);
		if(gender > 1) {
			gender = 0;
		}
		if(gender < 0) {
			gender = 0;
		}
		p.getAppearence().setGender((byte)gender);
		p.getMask().setApperanceUpdate(true);
	}

}
