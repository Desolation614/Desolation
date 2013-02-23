package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.player.clan.ClanChat;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.util.Misc;

public class ChangeClanName implements Command {

	/* (non-Javadoc)
	 * @see dragonkk.rs2rsps.net.Command#execute(java.lang.String[], dragonkk.rs2rsps.model.player.Player)
	 */
	@Override
	public void execute(String[] args, Player p) {
		int len = args.length;
		String yelled = "";
		String seperator = "";
		for(int i = 1; i < len; i ++) {
			seperator = i == 1? "" : " ";
			yelled = yelled+ seperator +args[i];
		}
		if(yelled.length() > 12) {
			p.getFrames().sendChatMessage(0, "The name you tried to set your clan too is too long. Please shortern it.");
			return;
		}
		ClanChat clan = World.clanManager.getRegisteredClans().get(p.getUsername().toLowerCase().replaceAll(" ", "_"));
		clan.setRoomName(Misc.formatPlayerNameForDisplay(yelled));
		clan.refreshClanAll();
	}

}
