package dragonkk.rs2rsps.skills.combat.spells;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.skills.combat.CombatManager;
import dragonkk.rs2rsps.skills.combat.MagicInterface;
import dragonkk.rs2rsps.skills.combat.RuneRequirements;
import dragonkk.rs2rsps.util.Misc;
import dragonkk.rs2rsps.util.ProjectileManager;

public class Teleblock implements MagicInterface {

	@Override
	public void execute(final Player p, final Player opp) {
		if(opp == null || p == null) {
			return;
		}
		if(!RuneRequirements.hasRunes(p, "TELEPORT_BLOCK")) {
			p.getFrames().sendChatMessage(0, "You don't have enough runes to cast this spell.");
			return;
		}
		if(opp.getSkills().tbTimer > 0) {
			p.getFrames().sendChatMessage(0, "This player is already being affected by this spell.");
			return;
		}
		final int hit = p.getCombat().getMagicHit(p, opp, 20);
		final boolean sucessfulCast = hit != 0;
		final int timer = opp.getPrayer().usingPrayer(1, 7) ? 250 : 500;
		p.animate(10503);
		p.graphics(1841);
		ProjectileManager.sendGlobalProjectile(p, opp, 1842, 25,
				30, 41, 15, 0);
		GameLogicTaskManager.schedule(new GameLogicTask() {
			@Override
			public void run() {
				if(opp == null || p == null) {
					this.stop();
					return;
				}
				opp.animate(CombatManager.getDefenceEmote(opp));
				if (sucessfulCast) {
					opp.graphics(1843);
					opp.getFrames().sendChatMessage(0, "You have been teleblocked!");
					opp.getSkills().tbTimer = timer;
					GameLogicTaskManager.schedule(new GameLogicTask() {
						@Override
						public void run() {
							if(opp == null || p == null) {
								this.stop();
								return;
							}
							opp.hit(hit, p);
							if (opp.getCombat().vengeance) {
								if (hit > 0) {
									opp.getCombat().applyVengeance(
									Math.floor(hit * 0.75), p, opp);
								}
							}
						}
					}, 0, 0);
				} else {
					opp.graphics2(85);
				}
				this.stop();
			}
		}, 2, 0);

	}

}
