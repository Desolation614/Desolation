package dragonkk.rs2rsps.skills.magic;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.skills.combat.RuneRequirements;

public class LunarMagics {
	
	public static void handleLunarSpell(int spell, final Player caster, final Player opp) {
		switch(spell) {
		case 41:
			if(opp.getCombat().vengeance) {
				caster.getFrames().sendChatMessage(0, "This player already has vengeance casted.");
				return;
			} else {
				if(caster.getCombat().vengDelay > 0) {
					caster.getFrames().sendChatMessage(0, "You cannot cast this spell at the moment.");
				} else {
					if(RuneRequirements.hasRunes(caster, "VENGEANCE_OTHER")) {
						caster.getCombat().vengDelay = 60;
						caster.animate(4411);
						GameLogicTaskManager.schedule(new GameLogicTask() {

							@Override
							public void run() {
								opp.getCombat().vengeance = true;
								opp.graphics2(725);
								opp.getFrames().sendChatMessage(0, "You have been given the power of vengeance!");
							}
							
						}, 1, 0);
					} else {
						caster.getFrames().sendChatMessage(0, "You don't have the required runes to cast this spell.");
						return;
					}
				}
			}
			break; 
		case 27: //ENERGY XFER
			break;
		case 51: //heal other
		}
	}

}
