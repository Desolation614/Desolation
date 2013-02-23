package dragonkk.rs2rsps.skills.combat.spells;

import java.util.ArrayList;
import java.util.List;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.skills.combat.CombatManager;
import dragonkk.rs2rsps.skills.combat.MagicInterface;
import dragonkk.rs2rsps.skills.combat.RuneRequirements;
import dragonkk.rs2rsps.util.Misc;

public class IceBarrage implements MagicInterface {

	@Override
	public void execute(final Player p, final Player opp) {
		double maxHit = 300;
		double bonus = 1.0;
		if (p.getSkills().getLevel(6) < 94) {
			p
					.getFrames()
					.sendChatMessage(0,
							"You don't have the required magic level to cast this spell.");
			return;
		}
		if (!RuneRequirements.hasRunes(p, "ICE_BARRAGE")) {
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
		if (!opp.getCombat().Multi(opp)) {
			opp.graphics(-1);
			p.graphics(-1);
			p.graphics2(-1);
			boolean orbs = false;
			p.getSkills().sendCounter(hit, 52, true);
			if (hit != 0) {
				if (opp.getCombat().immuneDelay > 0) {
					orbs = true;
				} else {
					opp.getCombat().removeTarget();
					opp.getWalk().reset();
					opp.getCombat().freezeDelay = 30;
					opp.getCombat().immuneDelay = 38;
					opp.getFrames().sendChatMessage(0, "You have been frozen!");
				}
			}
			final boolean hasOrb = orbs;
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
						}, 0, 0);
						if (hasOrb) {
							opp.graphics2(1677);
						} else {
							opp.graphics(369);
						}
					} else {
						opp.graphics2(85);
					}
					this.stop();
				}
			}, 2, 0);
		} else {
			p.graphics(-1);
			p.graphics2(-1);
			List<Player> players = new ArrayList<Player>(9);
			double distance;
			players.add(opp);
			for(Player d : World.getPlayers()) {
				if(players.size() == 9)
					break;
				if(d == null)
					continue;
				distance = Math.ceil(d.getLocation().getDistance(opp.getLocation()));
				if(distance > 2)
					continue;
				if(d.getCombat().isSafe(d))
					continue;
				if(d.getUsername().equalsIgnoreCase(opp.getUsername())) {
					continue;
				}
				if(!d.getLocation().checkWildRange(p, d))
					continue;
				if(d.getDisplayName().equalsIgnoreCase(p.getDisplayName()))
					continue;
				players.add(d);
			}
			int hit3 = 0;
			try{
			for(final Player d : players) {
				if(d == null)
					continue;
				
				if(d.isDead())
					continue;
				hit3 = p.getCombat().getMagicHit(p, d,
						Misc.random((int) maxHit));
				boolean orbs = false;
				d.graphics(-1);
				d.graphics2(-1);
				if (hit3 != 0) {
					if (d.getCombat().immuneDelay > 0) {
						orbs = true;
					} else {
						d.getCombat().removeTarget();
						d.getWalk().reset();
						d.getCombat().freezeDelay = 30;
						d.getCombat().immuneDelay = 38;
						d.getFrames().sendChatMessage(0, "You have been frozen!");
					}
				}
				final boolean hasOrb = orbs;
				final boolean sucessfulCast = hit3 != 0;
				final int hit2 = hit3;
				p.getSkills().sendCounter(hit2, 52, true);
				GameLogicTaskManager.schedule(new GameLogicTask() {
					@Override
					public void run() {
						d.animate(CombatManager.getDefenceEmote(d));
						if (sucessfulCast) {
							GameLogicTaskManager.schedule(new GameLogicTask() {
								@Override
								public void run() {
									d.hit(hit2, p);
									if (opp.getCombat().vengeance) {
										if (hit2 > 0) {
											opp.getCombat().applyVengeance(
													Math.floor(hit2 * 0.75), p, d);
										}
									}
								}
							}, 0, 0);
							if (hasOrb) {
								d.graphics2(1677);
							} else {
								d.graphics(369);
							}
						} else {
							d.graphics2(85);
						}
						this.stop();
					}
				}, 2, 0);
			}
			}catch(Exception e) {
				
			}
			players.clear();
		}
	}

}
