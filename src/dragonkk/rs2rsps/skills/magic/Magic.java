package dragonkk.rs2rsps.skills.magic;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.player.Skills;

public class Magic {

	
	public static void spellBookTeleport(final Player p, int spellBook, int buttonId) {
		if(System.currentTimeMillis() < p.getCombatDefinitions().getLastEmote())
			return;
		if(p.getSkills().playerDead)
			return;
		if(p.getSkills().tbTimer > 0) {
			p.getFrames().sendChatMessage(0, "A teleblock spell blocks your teleport!");
			return;
		}
		final int coordX;
		final int coordY;
		final int magicLvl = p.getSkills().getLevel(Skills.MAGIC);
	    int neededLvl = 0;
		switch(spellBook) {
		case 192:
		switch(buttonId) {
			case 43:
				if(magicLvl < 31)
					neededLvl = 31;
				coordX = 3221;
				coordY = 3219;
			break;
			case 24:
				coordX = 3087;
				coordY = 3496;
			break;
			case 40:
				if(magicLvl < 25)
					neededLvl = 25;
				coordX = 3212;
				coordY = 3428;
			break;
			case 46:
				if(magicLvl < 37)
					neededLvl = 37;
				coordX = 2964;
				coordY = 3380;
			break;
			case 51:
				if(magicLvl < 45)
					neededLvl = 45;
				coordX = 2757;
				coordY = 3478;
			break;
			case 57:
				if(magicLvl < 58)
					neededLvl = 58;
				coordX = 2662;
				coordY = 3308;
			break;
			case 72:
				if(magicLvl < 64)
					neededLvl = 64;
				coordX = 2755;
				coordY = 2784;
			break;
			default:
				return;
			}
		break;
		default:
			System.out.println("bookId: "+spellBook);
			return;
		}
		if(neededLvl != 0) {
			p.getFrames().sendChatMessage(0, "You need level "+neededLvl+" magic to use this spell.");
			return;
		}
		p.getCombatDefinitions().doEmote(8939, 1576, 4200);
		GameLogicTaskManager.schedule(new GameLogicTask() {
			int count = 0;
			@Override
			public void run() {
				if(!p.isOnline()) {
					this.stop();
					return;
				}
				if(count++ == 0)
					p.getMask().getRegion().teleport(coordX, coordY, 0, 0);
				else {
					p.animate(8941);
					p.graphics(1577);
					this.stop();
				}
			}
			
		}, 3, 0, 0);
	}
}
