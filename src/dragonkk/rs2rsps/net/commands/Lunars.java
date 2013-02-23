package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Lunars implements Command {

	@Override
	public void execute(String[] args, Player p) {
		if(!p.getCombat().isSafe(p)){
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>You cannot use this command in the wilderness.");
			return;
		}
		p.getFrames().sendInterface(1, 548, 205, 430);
		p.getFrames().sendInterface(1, 746, 93, 430);
		p.bookId = 430;
		//p.getFrames().sendChatMessage(0, "If you are on resizable screen size. This won't work. Please use the fixed size thx.");
	}
	

}
