package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.util.Serializer;

public class ChangePass implements Command {

	@Override
	public void execute(String[] args, Player p) {
		/*String pass = args[1];
		p.setPassword(pass);
		p.getFrames().sendChatMessage(0, "You have changed your password to: "+pass);
		Serializer.SaveAccount(p);*/
		p.getFrames().sendChatMessage(0, "Please change your password on the forums.");
	}

}
