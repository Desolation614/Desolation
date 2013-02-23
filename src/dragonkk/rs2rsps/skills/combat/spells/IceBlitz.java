package dragonkk.rs2rsps.skills.combat.spells;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.skills.combat.CombatManager;
import dragonkk.rs2rsps.skills.combat.MagicInterface;
import dragonkk.rs2rsps.skills.combat.RuneRequirements;
import dragonkk.rs2rsps.util.Misc;

public class IceBlitz implements MagicInterface {
	
	@Override
	public void execute(final Player p, final Player opp) {
		double maxHit = 260;
		double bonus = 1.0;
		
		if(!RuneRequirements.hasRunes(p, "ICE_BLITZ")) {
			p.getFrames().sendChatMessage(0, "You don't have enough runes to cast this spell.");
			return;
		}
		
		if(p.getSkills().getLevel(6) < 82) {
			p.getFrames().sendChatMessage(0, "You don't have the required magic level to cast this spell.");
			return;
		}
		if (p.getEquipment().contains(4675)) {
			bonus += 0.10;
		}
		if (p.getEquipment().contains(18335)) {
			bonus += .20;
		}
		if (p.getEquipment().contains(15486)) {
			bonus += .15;
		}	
		final int NO_BOOST = p.getSkills().getLevelForXp(6);
		final int DIFFERENCE = p.getSkills().getLevel(6) - NO_BOOST;
		if(DIFFERENCE > 0) { 
			bonus += DIFFERENCE * 0.03;
		}
		maxHit = maxHit * bonus;
		final int hit = p.getCombat().getMagicHit(p, opp, Misc.random((int)maxHit));
		p.getCombat().soulSplit(p, opp, hit);
		p.graphics(-1);
		p.graphics2(-1);
		p.animate(1978);
		p.graphics(366);
		if(opp.getCombat().immuneDelay == 0 & hit != 0) {
			opp.getCombat().removeTarget();
			opp.getWalk().reset();
			opp.getCombat().freezeDelay = 20;
			opp.getCombat().immuneDelay = 28;
			opp.getFrames().sendChatMessage(0, "You have been frozen!");
		}
		p.getSkills().sendCounter(hit, 46, true);
		final boolean sucessfulCast = hit != 0;
		GameLogicTaskManager.schedule(new GameLogicTask() {
			@Override
			public void run() {
				opp.animate(CombatManager.getDefenceEmote(opp));
				if (sucessfulCast) {
					GameLogicTaskManager.schedule(new GameLogicTask() {
						@Override
						public void run() {
							opp.hit(hit, p);
							if (opp.getCombat().vengeance) {
								if (hit > 0) {
									opp.getCombat().applyVengeance(
									Math.floor(hit * 0.75), p, opp);
								}
							}
						}
					}, 1, 0);
					opp.graphics(367);
				} else {
					opp.graphics2(85);
				}
				this.stop();
			}
		}, 2, 0);
	}


}
