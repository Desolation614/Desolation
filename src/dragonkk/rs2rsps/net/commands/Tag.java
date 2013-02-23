package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.net.Packets;
import dragonkk.rs2rsps.util.Constants;

public class Tag implements Command {

	@Override
	public void execute(String[] args, Player p) {
		
		if(!p.extremeDonator) {
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>You must be an extreme member to change your tag.");
			return;
		}
		String newTag = args[1];
		if (newTag.length() > 15 && p.getRights() < 2) {
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>Your tag cannot be longer than 15 characters!");
			return;
		}
		
		if (Packets.checkArray(newTag, Constants.NAUGHTY_WORDS)) {
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>Please remove the innapropriate words from the tag and try again");
			return;
		}
		
		if (Packets.checkArray(newTag, Constants.BAD_TAG_WORDS)) {
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>Please remove the staff words from the tag and try again");
			return;
		}
		
		if (newTag.contains("<") || newTag.contains(">")) {
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>Nice try, remove the < or >");
			return;
		}
		
		p.setTag(newTag);
		p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>Your tag has been changed. It is now: " + newTag);
	}

}
