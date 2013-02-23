package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;

//Author Lay

public class Commands implements Command {

	@Override
	public void execute(String[] args, Player p) {
		p.getFrames().sendChatMessage(0, " <col=244080><shad=000000>Commands are as followed; ::info ::safepk ::pvp (dangerous), ::setlevel id lvl");
		p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>::moderns, ::ancients ::lunars ::curses (true/false) ::item id amount");
		p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>::master ::bank ::yell msg ::players ::mb ::stats");
		p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>::empty ::edge ::dicezone ::dicebag ::runes ::food ::pots ::hybridset");
		p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>::settag tag ::removetag");
		p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>Enjoy your Experience!");
	}
}
