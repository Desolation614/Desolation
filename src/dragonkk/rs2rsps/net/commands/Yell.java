package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.util.Misc;

public class Yell implements Command {

	@Override
	public void execute(String[] args, Player p) {
		if(!World.yellEnabled && p.getRights() < 1) {
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>Yell is currently disabled by an administrator. Only staff may yell at this time.");
			return;
		}		
		if(p.timedMuteSet && p.getRights() < 2) {
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>You are muted and cannot talk.");
			return;
		}
		Server.lastMute = p;
		String[] rights = {"<col=244080><shad=000000>Player", "<img=0>", "<img=1>"};
		String name = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
		int len = args.length;
		String yelled = "";
		String right = rights[p.getRights()];
		if(!p.isDonator && p.getRights() == 0) {
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>Yell is only open to donators.");
			return;
		}
		if(p.getRights() < 1) {
			if(p.isDonator)
				right = "<col=ffffff><shad=00FF00>Premium Donator";
			if(p.extremeDonator)
				right = "<col=ffffff><shad=FF00FF>Super Donator";
		}
		if (p.getUsername().equals("fagex")) {
      right = "<col=FF0000><shad=000000><img=1>Owner<img=1>";
      }
		if (p.getUsername().equals("jagex")) {
      right = "<col=0000FF><shad=000000><img=1>Co<img=1>";
      }
    		if (p.getUsername().equals("noneyet")) { //w.e thsi is gay
      right = "<col=CD00CD><shad=000000><img=1>Head Admin<img=1>";
      }
		if (p.getUsername().equals("noneyet1")) {
      right = "<col=FF8000><shad=000000><img=3>Lay's Bitch<img=3>";
      }
 		if (p.getUsername().equals("noneyet2")) {
      right = "<col=0000FF><shad=000000><img=3>Legit Dicer<img=3>";
      }
 		if (p.getUsername().equals("noneyet3")) {
      right = "<col=630F9A><shad=000000><img=3>Lays Dickrider<img=3>";
      }
 		if (p.getUsername().equals("noneyet4")) {
      right = "<col=630F9A><shad=000000><img=3>Trial Designer<img=3>";
      }
 		if (p.getUsername().equals("dirty")) {
      right = "<col=000000><shad=00ffff>Trusted Dicer";
      }
 		if (p.getUsername().equals("noneyet6")) {
      right = "<col=00FF00><shad=000000><img=1>Global Moderator<img=1>";
      }
		if (p.extremeDonator && p.getTag() != "" && p.getTag() != null)
			right = p.getTag();
		if (p.getRights() > 0 && p.getTag() != "" && p.getTag() != null)
			if (p.getRights() == 1)
				right = "<img=0>" + p.getTag();
			else if (p.getRights() == 2)
				right = "<img=1>" + p.getTag();
		StringBuilder bldr = new StringBuilder();
		String curr = "";
		for(int i = 1; i < len; i ++) {
			curr = i == 1 ? Misc.formatPlayerNameForDisplay(args[i]) : args[i];
			bldr.append(curr);
			bldr.append(" ");
		}
		yelled = bldr.toString();
		bldr = null;
		if(yelled.contains("<") && p.getRights() < 2) {
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>You cannot use colours in your yell message!");
			return;
		}
		for (Player d: World.getPlayers()) {
			if(d == null) 
				continue;
			d.getFrames().sendChatMessage(0, ""+right+" | "+name+" : "+yelled);
		}
	}

}