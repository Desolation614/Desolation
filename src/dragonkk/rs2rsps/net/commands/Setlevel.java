package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

public class Setlevel implements Command {

	@Override
	public void execute(String[] args, Player p) {
		if(!p.getCombat().isSafe(p)) {
			p.getFrames().sendChatMessage(0, "You cannot do this command in the wilderness!");
			return;
		}
		int level = Integer.parseInt(args[1]);
		int amount = Integer.parseInt(args[2]);
		if(p.getCombat().delay > 0) {
			p.getFrames().sendChatMessage(0, "You can't set stats while in combat.");
			return;
		}
		if(amount > 99) {
			amount = 99;
		}
		if(amount < 1) {
			amount = 1;
		}
		if(level == 23) {
			p.getFrames().sendChatMessage(0, "You cannot change this skill");
			return;
		}
		boolean canContinue = true;
		for(dragonkk.rs2rsps.model.Item i : p.getEquipment().getEquipment().getItems()) {
			if(i != null) {
				canContinue = false;
			}
		}
		if(!canContinue) {
			p.getFrames().sendChatMessage(0, "You cannot use this command wearing items!");
			return;
		}
		int xp = p.getSkills().getXPForLevel(amount);
		p.getSkills().set(level, amount);
		p.getSkills().setXp(level, xp);
		p.getMask().setApperanceUpdate(true);
	}

}
