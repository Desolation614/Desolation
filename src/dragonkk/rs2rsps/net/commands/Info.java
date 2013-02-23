package dragonkk.rs2rsps.net.commands;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.Command;
import dragonkk.rs2rsps.net.Packets;

public class Info implements Command {

//Author Lay
//RuneDesign 614

	@Override
	public void execute(String[] args, Player p) {
        int number = 0;
          for(int i = 0; i < 316; i++) {
        p.getFrames().sendString("",275,i);
     }
p.getFrames().sendString(" ", 275, 2);
p.getFrames().sendString(" ", 275,14);
p.getFrames().sendString(" ", 275, 16);
p.getFrames().sendString(" <col=244080><shad=000000> <img=3> Welcome to Dezolation614 <img=3> ", 275, 17);
p.getFrames().sendString(" <col=244080><shad=000000> <img=3> Owner is <img=1>Fagex<img=1> <img=3>", 275, 18);
p.getFrames().sendString(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ", 275, 19);
p.getFrames().sendString(" ", 275, 20);
p.getFrames().sendString(" <col=244080><shad=000000> <img=3>Stuff you should know:<img=3> ", 275, 21);
p.getFrames().sendString(" <col=244080><shad=000000><img=3>::commands for a list of commands! <img=3> ", 275, 22);
p.getFrames().sendString(" <col=244080><shad=000000><img=3>Forums is located at www.Dezolation614.com! <img=3> ", 275, 23);
p.getFrames().sendString(" <col=244080><shad=000000><img=3>Vote for 20m! <img=3> ", 275, 24);
p.getFrames().sendString(" <col=244080><shad=000000><img=3>Report Bugs on our Forum for rewards <img=3> ", 275, 25);
p.getFrames().sendString(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ", 275, 26);
p.getFrames().sendString(" <col=244080><shad=000000><img=3> There are 3 Ranks for which you can donate for<img=3>", 275, 27);
p.getFrames().sendString(" <col=244080><shad=000000><img=3> Premium Donator - 6M RSGP - $5<img=3>", 275, 28);
p.getFrames().sendString(" <col=244080><shad=000000><img=3> Super Donator - 12m RSGP - $15<img=3>", 275, 29);
p.getFrames().sendString(" <col=244080><shad=000000><img=3> Legit Dicer - 20m RSGP - $25<img=3>", 275, 30);
p.animate(1350);
p.getFrames().sendInterface(275);
	}
}
