package dragonkk.rs2rsps.skills.combat.spells;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.skills.combat.CombatManager;
import dragonkk.rs2rsps.skills.combat.MagicInterface;
import dragonkk.rs2rsps.skills.combat.RuneRequirements;
import dragonkk.rs2rsps.util.Misc;

// Author Lay
// 11/27/2011 RuneDesign 614
// MiasmicBarrage.java

public class MiasmicBarrage implements MagicInterface {

	@Override
	public void execute(final Player p, final Player opp) {
		double maxHit = 320;
		double bonus = 1.0;
		if (!RuneRequirements.hasRunes(p, "MIASMIC_BARRAGE")) {
			p.getFrames().sendChatMessage(0,
					"You don't have enough runes to cast this spell.");
			return;
		}
		if (!p.getEquipment().contains(13867)) {
			p.getFrames().sendChatMessage(0,
					"You need to wield Zuriels staff to cast this spell.");
			return;
		}
		if (p.getEquipment().contains(13867)) {
			bonus += 0.10;
		}
		if (p.getEquipment().contains(18335)) {
			bonus += .20;
		}
		final int NO_BOOST = p.getSkills().getLevelForXp(6);
		final int DIFFERENCE = p.getSkills().getLevel(6) - NO_BOOST;
		if (DIFFERENCE > 0) {
			bonus += DIFFERENCE * 0.03;
		}
		maxHit = maxHit * bonus;
		final int hit = p.getCombat().getMagicHit(p, opp,
				Misc.random((int) maxHit));
		p.getCombat().soulSplit(p, opp, hit);
		p.graphics2(1853);
		p.animate(10518);
		if (hit != 0) {
			if (opp.getCombat().immuneDelay2 > 0) {
				opp.getCombat().MiasmicEffect = 96;
				opp.getCombat().immuneDelay2 = 115;
			}
		}
		final boolean sucessfulCast = hit != 0;
		p.getSkills().sendCounter(hit,55, true);
		GameLogicTaskManager.schedule(new GameLogicTask() {
			@Override
			public void run() {
				opp.animate(CombatManager.getDefenceEmote(opp));
				if (sucessfulCast) {
					opp.hit(hit, p);
					opp.graphics2(1854);
					if (opp.getCombat().vengeance) {
						if (hit > 0) {
							opp.getCombat().applyVengeance(
									Math.floor(hit * 0.75), p, opp);
						}
					}

				} else {
					opp.graphics2(85);
				}
				this.stop();
			}
		}, 2, 0);
	}

}
