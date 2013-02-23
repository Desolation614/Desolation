package dragonkk.rs2rsps.skills.combat.spells;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.skills.combat.CombatManager;
import dragonkk.rs2rsps.skills.combat.MagicInterface;
import dragonkk.rs2rsps.skills.combat.RuneRequirements;
import dragonkk.rs2rsps.util.Misc;

public class BloodBarrage implements MagicInterface {

	@Override
	public void execute(final Player p, final Player opp) {
		double maxHit = 290;
		double bonus = 1.0;
		if(p.getSkills().getLevel(6) < 92) {
			p.getFrames().sendChatMessage(0, "You don't have the required magic level to cast this spell.");
			return;
		}
		if (!RuneRequirements.hasRunes(p, "BLOOD_BARRAGE")) {
			p.getFrames().sendChatMessage(0,
					"You don't have enough runes to cast this spell.");
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
		if (DIFFERENCE > 0) {
			bonus += DIFFERENCE * 0.03;
		}
		maxHit = maxHit * bonus;
		final int hit = p.getCombat().getMagicHit(p, opp,
				Misc.random((int) maxHit));
		p.getCombat().soulSplit(p, opp, hit);
		p.animate(1979);
		opp.graphics(-1);
		p.graphics(-1);
		p.graphics2(-1);
			p.getSkills().sendCounter(hit, 51, true);
		final boolean sucessfulCast = hit != 0;
		GameLogicTaskManager.schedule(new GameLogicTask() {
			@Override
			public void run() {
				opp.animate(CombatManager.getDefenceEmote(opp));
				if (sucessfulCast) {
					opp.hit(hit, p);
					p.getSkills().heal(hit / 4);
					if (opp.getCombat().vengeance) {
						if (hit > 0) {
							opp.getCombat().applyVengeance(
									Math.floor(hit * 0.75), p, opp);
						}
					}
					opp.graphics(377);
				} else {
					opp.graphics2(85);
				}
				this.stop();
			}
		}, 2, 0);

	}

}
