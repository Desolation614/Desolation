package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class GetTokens implements Command {

	@Override
	public void execute(String[] args, Player p) {
		double amount = 1;
		
		if (p.extremeDonator)
			amount = amount * 2;
		else if (p.isDonator)
			amount = amount * 1.5;
		if (p.hasTokenRing() && p.getSkills().isWearingTokenRing(p))
			amount = amount * 2;
		if (p.getFrames().isWeekend())
			amount = amount * 2;
		
		p.getFrames().sendChatMessage(0, "Your token multiplier is currently: x" + amount);
	}

}
