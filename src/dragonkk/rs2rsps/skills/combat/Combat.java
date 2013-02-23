package dragonkk.rs2rsps.skills.combat;

import java.util.Random;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.Entity;
import dragonkk.rs2rsps.model.Hits.Hit;
import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.npc.Npc;
import dragonkk.rs2rsps.model.player.ChatMessage;
import dragonkk.rs2rsps.model.player.Equipment;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.player.Skills;
import dragonkk.rs2rsps.util.Misc;
import dragonkk.rs2rsps.util.ProjectileManager;

public class Combat {

	public boolean queuedSet;
	public int queuedSpellID;
	public int queuedSpellbookId;
	public int queuedPlayer;
	public int magicDelay;
	public int shieldDelay;
	public int[] dealtDamage = new int[2048];
	public double BGSDrain;
	public double SWHDrain;
	public long lastHit = 0;
	public int prayDisable = 0;

	public boolean boltEffect = false;
	public boolean bowSpec = false;

	public int calculateRange() {
		try {
			Player p = (Player) entity;
			@SuppressWarnings("static-access")
			double strength = p.getSkills().getLevel(p.getSkills().RANGE);
			double bonusMultiplier = (getRangedStrength(p) * 0.00175) + 0.1;
			double maxHit = (int) Math.floor(strength * bonusMultiplier) + 1.05;
			if (CombatManager.wearingVoid(p, 11664)) {
				maxHit *= 1.15;
			}
			return ((int) maxHit * 10);
		} catch (Exception e) {
		}
		return 1;
	}

	/**
	 * Leeches the victims stats
	 * 
	 * @param p
	 *            The attacker
	 * @param opp
	 *            The victim
	 */
	public void leechStats(final Player p, final Player opp) {
		if (p == null || p.getSkills().playerDead)
			return;
		if (opp == null || opp.getSkills().playerDead)
			return;
		boolean attack = p.getPrayer().usingPrayer(1, 10);
		boolean range = p.getPrayer().usingPrayer(1, 11);
		boolean magic = p.getPrayer().usingPrayer(1, 12);
		boolean def = p.getPrayer().usingPrayer(1, 13);
		boolean strength = p.getPrayer().usingPrayer(1, 14);
		if (attack)
			Combat.applyLeechStats(p, opp, LeechType.ATTACK);
		if (range)
			Combat.applyLeechStats(p, opp, LeechType.RANGE);
		if (magic)
			Combat.applyLeechStats(p, opp, LeechType.MAGIC);
		if (def)
			Combat.applyLeechStats(p, opp, LeechType.DEFENCE);
		if (strength)
			Combat.applyLeechStats(p, opp, LeechType.STRENGTH);
	}
	
	/**
	 * Variables for 'stat leeching'
	 */
	public static int LEECH_ATTACK = 0;
	public static int LEECH_RANGE = 2;
	public static int LEECH_MAGIC = 3;
	public static int LEECH_DEF = 4;
	public static int LEECH_STR = 5;

	/**
	 * An enum containing leech data Combat.java
	 * 
	 */
	public enum LeechType {

		ATTACK(2232, 2233, 2231, LEECH_ATTACK), RANGE(2238, 2237, 2236,
				LEECH_RANGE), MAGIC(2242, 2241, 2240, LEECH_MAGIC), DEFENCE(
				2246, 2245, 2244, LEECH_DEF), STRENGTH(2250, 2249, 2248,
				LEECH_STR);

		public int endGFX, returnGFX, projectileGFX, slotToLeech;

		LeechType(int... args) {
			endGFX = args[0];
			returnGFX = args[1];
			projectileGFX = args[2];
			slotToLeech = args[3];
		}
	}

	/**
	 * Applys the leeching of stats
	 * 
	 * @param attacker
	 *            The attacker
	 * @param victim
	 *            The victim
	 * @param leech
	 *            The leech
	 */
	public static void applyLeechStats(final Player attacker,
			final Player victim, final LeechType leech) {
		if (attacker.getCombat().shieldDelay < 1)
			attacker.animate(12575);
		ProjectileManager.sendGlobalProjectile(attacker, victim,
				leech.projectileGFX, 11, 11, 30, 20, 0);
		GameLogicTaskManager.schedule(new GameLogicTask() {
			@Override
			public void run() {
				victim.graphics(leech.endGFX);
			}
		}, 1, 0);

	}

	public int getRangedStrength(Player p) {
		try {
			p.getEquipment();
			short arrows = p.getEquipment().get(Equipment.SLOT_ARROWS).getId();
			short weapon = p.getEquipment().get(Equipment.SLOT_WEAPON).getId();
			/*
			 * if (weapon == 13879 || weapon == 13953) { return 145; } if
			 * (weapon == 13883 || weapon == 13957) { return 117; }
			 */
			switch (weapon) {
			case 13879:
			case 13953:
				return 145;
			case 13883:
			case 13957:
				return 117;
			case 4214:
				return 70;
			case 806:
				return 1;
			case 807:
				return 3;
			case 808:
				return 4;
			case 809:
				return 7;
			case 810:
				return 10;
			case 811:
				return 14;
			}
			switch (arrows) {
			case 15243:
				return 150;
			case 11212:
				return 60;
			case 9243:
				return 105;
			case 9244:
				return 117;
			case 9144:
				return 115;
			case 9143:
				return 100;
			case 9142:
				return 82;
			case 4740:
				return 55;
			case 882:
				return 7;
			case 884:
				return 10;
			case 886:
				return 16;
			case 888:
				return 22;
			case 890:
				return 31;
			case 892:
				return 49;
			}
		} catch (Exception e) {

		}
		return -1;
	}

	/*
	 * Returns the arrows allowed to be used by the bow
	 */
	public int[] arrowsAllowed(int bow) {
		switch (bow) {
		case 839:
		case 841:
			return new int[] { 882, 884 };
		case 843:
		case 845:
			return new int[] { 882, 884, 886 };
		case 847:
		case 849:
			return new int[] { 882, 884, 886, 888 };
		case 851:
		case 853:
			return new int[] { 882, 884, 886, 888, 890 };
		case 859:
		case 861:
			return new int[] { 882, 884, 886, 888, 890, 892 };
		case 18357:
		case 9185:
			return new int[] { 9243, 9244 };
		case 11235:
			return new int[] { 882, 884, 886, 888, 890, 892, 11212 };
		case 4734:
			return new int[] { 4740 };
		case 15241:
			return new int[] { 15243 };
		case 13879:
		}
		return null;
	}

	/*
	 * Checks to see if the player can use the arrows with the bow
	 */
	public boolean canUseArrows(Player p) {
		try {
			int wep = p.getEquipment().get(Equipment.SLOT_WEAPON).getId();
			int arrows = p.getEquipment().get(Equipment.SLOT_ARROWS).getId();
			int[] allowed = arrowsAllowed(wep);
			for (int i : allowed)
				if (i == arrows)
					return true;
		} catch (Exception e) {

		}
		return false;
	}

	public boolean checkArrows(Player p) {
		try {
			int wep = p.getEquipment().get(Equipment.SLOT_WEAPON).getId();
			int arrows = p.getEquipment().get(Equipment.SLOT_ARROWS).getId();
			if (wep >= 4210 && wep <= 4225) {
				return true;
			}
			if (arrows == -1) {
				p.getFrames().sendChatMessage(0,
						"You don't have any arrows equipt to use this weapon.");
				return false;
			}
			if (!canUseArrows(p)) {
				p.getFrames().sendChatMessage(0,
						"You can't use these arrows in your bow.");
				return false;
			}
		} catch (Exception e) {

		}
		return true;
	}

	public void skullPlayer(Player skulledOn) {
	}

	public void resetDamage() {
		for (int i = 0; i < dealtDamage.length; i++) {
			dealtDamage[i] = -1;
		}
	}

	public void addDamage(int damage, int slot) {
		dealtDamage[slot] += damage;
	}

	public boolean dangerousPVP(Player p) {
		int absX = p.getLocation().getX();
		int absY = p.getLocation().getY();
		return (absX >= 2919 && absX <= 3029 && absY >= 3307 && absY <= 3438)
				|| (absX >= 2940 && absX <= 3395 && absY >= 3520 && absY <= 4000);
	}

	public int getKiller() {
		int killer = -1;
		int killerDamage = -1;
		for (int i = 0; i < dealtDamage.length; i++) {
			if (dealtDamage[i] > killerDamage) {
				killer = i;
			}
		}
		return killer;
	}

	public boolean Multi(Player p) {
		int absX = p.getLocation().getX();
		int absY = p.getLocation().getY();
		return ((absX >= 1400 && absX <= 2300 && absY >= 5000 && absY <= 6000)
				|| (absX >= 2965 && absX <= 3050 && absY >= 3904 && absY <= 3959) || absX >= 3136
				&& absX <= 3327 && absY >= 3520 && absY <= 3607)
				|| (absX >= 3190 && absX <= 3327 && absY >= 3648 && absY <= 3839)
				|| (absX >= 2951 && absX <= 2976 && absY >= 3362 && absY <= 3391)
				|| (absX >= 3200 && absX <= 3390 && absY >= 3840 && absY <= 3967)
				|| (absX >= 2992 && absX <= 3007 && absY >= 3912 && absY <= 3967)
				|| (absX >= 2946 && absX <= 2959 && absY >= 3816 && absY <= 3831)
				|| (absX >= 3008 && absX <= 3199 && absY >= 3856 && absY <= 3903)
				|| (absX >= 3008 && absX <= 3071 && absY >= 3600 && absY <= 3711)
				|| (absX >= 3072 && absX <= 3327 && absY >= 3608 && absY <= 3647)
				|| (absX >= 3270 && absX <= 3346 && absY >= 3532 && absY <= 3625);
	}

	public boolean isSafe(Player p) {
		int absX = p.getLocation().getX();
		int absY = p.getLocation().getY();
		return ((absX >= 2183 && absX <= 2218 && absY >= 3242 && absY <= 3265)
				|| (absX >= 3264 && absY >= 3672 && absX <= 3279 && absY <= 3695) || absX >= 3201
				&& absX <= 3227 && absY >= 2301 && absY <= 3235)
				|| (absX >= 3147 && absX <= 3182 && absY >= 3473 && absY <= 3505)
				|| (absX >= 2943 && absX <= 2950 && absY >= 3368 && absY <= 3373)
				|| (absX >= 3064 && absX <= 3129 && absY >= 3464 && absY <= 3520)
				|| (absX >= 2606 && absX <= 2616 && absY >= 3088 && absY <= 3097)
				|| (absX >= 3489 && absX <= 3504 && absY >= 3469 && absY <= 3479)
				|| (absX >= 1998 && absX <= 2017 && absY >= 4430 && absY <= 4449)
				|| (absX >= 3277 && absX <= 3285 && absY >= 2764 && absY <= 2777)
				|| (absX >= 3312 && absX <= 3327 && absY >= 3225 && absY <= 3248);
				

	}

	public boolean inWild(Player p) {
		int absX = p.getLocation().getX();
		int absY = p.getLocation().getY();
	return (absX >= 2940 && absX <= 3395 && absY >= 3520 && absY <= 4000)
		|| (absX >= 2946 && absX <= 3316 && absY >= 3523 && absY <= 4004);
	}

	public Player killer = null;

	public void setQueueMagic(int opp, int book, int id) {
		this.queuedPlayer = opp;
		this.queuedSpellID = id;
		this.queuedSpellbookId = book;
		this.queuedSet = true;
	}

	public int combatWith;
	public int combatWithDelay;
	public int leechDelay = 0;
	public int newDelay = 0;
	public int immuneDelay2;
	public int MiasmicEffect;

	public void tick() {
		if (prayDisable > 0) {
			prayDisable--;
		}
		if (MiasmicEffect > 0) {
			MiasmicEffect--;
		}
		if (immuneDelay2 > 0) {
			immuneDelay2--;
		}
		if (combatWithDelay > 0) {
			combatWithDelay--;
		}
		if (newDelay < 12) {
			newDelay++;
			if (newDelay >= 12) {
				this.leechStats(getPlayer(), (Player) target);
				newDelay = 0;
			}
		}
		if (combatWithDelay == 0) {
			combatWith = -1;
		}
		if (vengDelay > 0) {
			vengDelay--;
		}
		if (shieldDelay > 0) {
			shieldDelay--;
		}
		if (freezeDelay > 0) {
			freezeDelay--;
		}
		if (immuneDelay > 0) {
			immuneDelay--;
		}
		if (delay > 0) {
			delay--;
		}
		if (magicDelay > 0) {
			magicDelay--;
		}
		if (queuedSet) {
			attemptCastSpell();
		}
	}

	public int isAttackingWho() {
		// TODO: FINISH THIS TOMMORORW
		return -1;
	}

	public void attemptCastSpell() {
		try {
			Player opp = World.getPlayers().get(queuedPlayer);
			if (getPlayer().getCombat().delay > 0
					|| getPlayer().getCombat().magicDelay > 0) {
				return;
			}
			if (getPlayer() == null || opp == null) {
				queuedSet = false;
				return;
			}
			if (!getPlayer().getLocation()
					.withinDistance(opp.getLocation(), 12)) {

				return;
			}
			if (isSafe(getPlayer()) || isSafe(opp)) {
				getPlayer()
						.getFrames()
						.sendChatMessage(0,
								"You need to be in either a PvP zone or in the wilderness to attack them.");
				this.removeTarget();
				queuedSet = false;
				return;
			}
			if (!getPlayer().getLocation().checkWildRange(getPlayer(), opp)) {
				getPlayer()
						.getFrames()
						.sendChatMessage(0,
								"You need to travel deeper into the wilderness to attack this player.");
				removeTarget();
				queuedSet = false;
				return;
			}
			if (getPlayer().getSkills().isDead() || opp.getSkills().isDead()) {
				queuedSet = false;
				return;
			}
			if (!Multi(getPlayer()) || !Multi(opp)) {
				if (combatWith != opp.getClientIndex() && combatWith > 0) {
					getPlayer().getFrames().sendChatMessage(0,
							"I'm already under attack.");
					this.removeTarget();
					queuedSet = false;
					return;
				}
				if (opp.getCombat().combatWith != getPlayer().getClientIndex()
						&& opp.getCombat().combatWith > 0) {
					getPlayer().getFrames().sendChatMessage(0,
							"This player is already in combat.");
					this.removeTarget();
					queuedSet = false;
					return;
				}
			}
			if ((opp.getLocation().getX() == getPlayer().getLocation().getX())
					&& (opp.getLocation().getY() == getPlayer().getLocation()
							.getY())) {
				queuedSet = false;
				return;
			}
			this.target = (Entity) opp;
			opp.getCombat().combatWith = getPlayer().getClientIndex();
			opp.getCombat().combatWithDelay = 12;
			castSpell();
			this.lastHit = System.currentTimeMillis();
			opp.getCombat().lastHit = System.currentTimeMillis();
			this.queuedSet = false;
			getPlayer().getCombat().delay = 4;
			magicDelay = 6;
		} catch (Exception e) {

		}
	}

	public Player getPlayer() {
		return (Player) entity;
	}

	public void castSpell() {
		getPlayer().turnTemporarilyTo(
				(Entity) World.getPlayers().get(queuedPlayer));
		Player opp = World.getPlayers().get(queuedPlayer);
		switch (queuedSpellbookId) {
		case 193: // Ancients
			switch (queuedSpellID) {
			case 21:
				MagicManager.executeSpell(getPlayer(), opp, "IceBlitz");
				break;
			case 23:
				MagicManager.executeSpell(getPlayer(), opp, "IceBarrage");
				break;
			case 27:
				MagicManager.executeSpell(getPlayer(), opp, "BloodBarrage");
				break;
			case 39:
				MagicManager.executeSpell(getPlayer(), opp, "MiasmicBarrage");
				break;

			}
		case 192:
			switch (queuedSpellID) {
			case 86:
				MagicManager.executeSpell(getPlayer(), opp, "TeleBlock");
				break;
			}

		}

	}

	public int freezeDelay;
	public int immuneDelay;
	private Entity entity;
	private Entity target;
	public byte delay;
	public boolean vengeance;
	public int vengDelay;
	private byte prayerBeforeHitDelay;
	private byte hitDelay;
	private byte prayerAfterHitDelay;
	private long lastAttackedTime;
	private boolean isProcessing;
	private CombatHitDefinitions combatHitDefinitions;

	/* Note for me */
	public double getMagicAccuracy(Player p, Player opp) {
		// final double A = 0.705;
		final double A = 0.756;
		double atkBonus = p.getCombatDefinitions().bonus[3];
		double defBonus = opp.getCombatDefinitions().bonus[10] * 1.20;
		if (atkBonus < 1) {
			atkBonus = (atkBonus - 1) * 5;
		}
		if (defBonus < 1) {
			defBonus = 1;
		}
		double atk = (atkBonus * p.getSkills().level[6]);
		double def = (defBonus * opp.getSkills().level[1]);
		def += 1;
		if (opp.getPrayer().usingPrayer(0, 0)) {
			def *= 1.05;
		}
		if (opp.getPrayer().usingPrayer(0, 5)) {
			def *= 1.10;
		}
		if (opp.getPrayer().usingPrayer(0, 13)) {
			def *= 1.15;
		}
		if (opp.getPrayer().usingPrayer(0, 25)) {
			def *= 1.20;
		}
		if (opp.getPrayer().usingPrayer(0, 27)) {
			def *= 1.25;
		}
		if (CombatManager.wearingVoid(p, 11663)) {
			atk *= 1.15;
		}
		double OUTCOME = A * (atk / def);
		// double OUTCOME = (atk / def);
		return OUTCOME;
	}

	public int getMagicHit(Player p, Player opp, int hit) {
		double accuracy = getMagicAccuracy(p, opp);
		Random random = new Random();
		int hitBefore = hit;
		if (opp.getPrayer().usingPrayer(1, 7)) {
			hitBefore = (int) (hitBefore * 0.4);
			if (opp.getCombat().shieldDelay == 0)
				opp.animate(12573);
			opp.graphics(2228);
			p.hit(Misc.random(100));
		}
		hit = hitBefore;
		if (accuracy > 1.0) {
			accuracy = 1;
		}
		if (accuracy < random.nextDouble()) {
			return 0;
		} else {
			return hit;
		}
	}

	public Combat(Entity entity) {
		this.entity = entity;
		prayerBeforeHitDelay = -1;
		hitDelay = -1;
		prayerAfterHitDelay = -1;
	}

	public void removeTarget() {
		target = null;
		entity.resetTurnTo();
	}

	public void clear() {
		prayerBeforeHitDelay = -1;
		hitDelay = -1;
		prayerAfterHitDelay = -1;
	}

	public boolean hasTarget() {
		return target != null;
	}

	public void processDelays() {
		if (hitDelay > 0) {
			hitDelay--;
		}
		if (entity instanceof Player) {
			if (prayerBeforeHitDelay > 0)
				prayerBeforeHitDelay--;
			if (prayerAfterHitDelay > 0)
				prayerAfterHitDelay--;
		}
	}

	public void attack(Entity t) {
		if (entity.isDead())
			return;
		this.target = t;
		this.entity.turnTo(this.target);
		if (!isProcessing) {
			isProcessing = true;
			GameLogicTaskManager.schedule(new GameLogicTask() {
				@SuppressWarnings("unused")
				long time;

				@Override
				public void run() {
					processAttack();
					if (delay == 0 && hitDelay == -1
							&& prayerBeforeHitDelay == -1
							&& prayerAfterHitDelay == -1 && target == null) {
						this.stop();
						isProcessing = false;
					}
					time = System.currentTimeMillis();
				}

			}, 0, 0, 0);
		}
	}

	public int totalSoul;
	public boolean soulSplitting = false;

	@SuppressWarnings("static-access")
	public void soulSplit(final Player p, final Player opp, final int hit) {
		if (hit < 1) {
			return;
		}
		p.getSkills().killer = opp;
		if (!p.getPrayer().usingPrayer(1, 18)) {
			return;
		}
		p.getSkills().heal(hit / 5);
		opp.getSkills().drainPray((hit / 10) / 5);
		soulSplitting = true;
		try {
			ProjectileManager.sendGlobalProjectile(((Entity) p), (Entity) opp,
					2263, 11, 11, 30, 20, 0);
			GameLogicTaskManager.schedule(new GameLogicTask() {
				@Override
				public void run() {
					opp.graphics(2264);
					ProjectileManager.sendGlobalProjectile(((Entity)opp), (Entity) p,
							2263, 11, 11, 30, 20, 0);
					this.stop();
					
				}
			}, 1, 1);
		} catch (Exception e) {

		}

	}

	private void processAttack() {
		if (target == null)
			return;
		Player ptarget = null;
		@SuppressWarnings("unused")
		Npc ntarget = null;
		if (target instanceof Player)
			ptarget = ((Player) target);
		else
			ntarget = ((Npc) target);
		/*
		 * if (ptarget.getRights() > 1) {
		 * this.getPlayer().getFrames().sendChatMessage(0,
		 * "You cannot attack an administrator."); removeTarget(); return; } if
		 * (this.getPlayer().getRights() > 1) {
		 * this.getPlayer().getFrames().sendChatMessage(0,
		 * "Administrators may not attack players."); removeTarget(); return; }
		 */
		if ((ptarget != null && (!ptarget.isOnline()) || target.isDead())) {
			removeTarget();
			return;
		}
		if (!entity.getLocation().withinDistance(target.getLocation(), 25)) {
			removeTarget();
			return;
		}
		if (ptarget != null
				&& (ptarget.isDead() || System.currentTimeMillis() < (ptarget
						.getCombatDefinitions().getLastEmote() - 600))) {
			removeTarget();
			return;
		}
		/* Note 1, changes round to ceil to stop diagonal attacking */
		int distance = (int) Math.round(entity.getLocation().getDistance(
				target.getLocation()));
		// int distance = (int)
		// Math.ceil(entity.getLocation().getDistance(target.getLocation()));
		int entityDistance = ptarget == null ? (byte) ((Npc) target)
				.getNpcdefinition().size : 1;

		if (entity instanceof Player) {
			final Player p = (Player) entity;
			if (p.isDead()
					|| System.currentTimeMillis() < (p.getCombatDefinitions()
							.getLastEmote() - 600)) {
				removeTarget();
				return;
			}
			// if (p.getSkills().getCombatLevel()
			int weaponId = p.getEquipment().get(3) == null ? -1 : p
					.getEquipment().get(3).getId();
			if (weaponId > -1)
				entityDistance += CombatManager.distForWeap(weaponId);
		}

		try {
			if ((distance == 0 || distance > entityDistance
					&& !rangedWeapon(((Player) entity)))
					|| (distance == 0 || distance > 8
							&& rangedWeapon((Player) entity))) {
				if (this.freezeDelay > 0) {
					return;
				}
				entity.getWalk().reset();
				entity.getWalk().addToWalkingQueueFollow(
						target.getLocation().getX()
								- (entity.getLocation().getRegionX() - 6) * 8,
						target.getLocation().getY()
								- (entity.getLocation().getRegionY() - 6) * 8);
				// removeTarget();
				if ((entity.getWalk().getRunDir() == -1 || distance > 2)
						&& (entity.getWalk().getWalkDir() == -1 || distance > 1))
					return;
			}
		} catch (Exception e) {

		}
		if (delay > 0)
			return;

		if (entity instanceof Player)
			processPlayer();
		else
			processNpc();
	}

	public final int[] RANGED_WEAPONS = { 861, 9185, 18357, 15241, 11235,
			13879, 13953, 13957, 13883, 4734 };
	public final int[] NO_ARROWS = { 13879, 13953, 13957, 13883 };

	public boolean noArrowRange(Player p) {
		for (int i = 0; i < NO_ARROWS.length; i++) {
			if (p.getEquipment().contains(NO_ARROWS[i])) {
				return true;
			}
		}
		return false;
	}

	public boolean rangedWeapon(Player p) {
		for (int i = 0; i < RANGED_WEAPONS.length; i++) {
			if (p.getEquipment().contains(RANGED_WEAPONS[i])) {
				return true;
			}
		}
		return false;
	}

	public boolean canSpec(Player p, int weapon) {
		int specNeeded = 0;
		int specAmount = p.getCombatDefinitions().getSpecpercentage();
		Item ring = p.getEquipment().get(Equipment.SLOT_RING);
		switch (weapon) {
		case 11696:
			specNeeded = 100;
			break;
		case 18786:
		case 4587:
		case 11235:
		case 11700:
			specNeeded = 60;
			break;
		case 14484:
		case 15241:
		case 13902:
		case 11694:
		case 11698:
		case 4153:
		case 13879:
			specNeeded = 50;
			break;
		case 13899:
		case 1215:
		case 1231:
		case 5698:
		case 1434:
		case 1305:
			specNeeded = 25;
			break;
		}
		double tenPercentOfSpecialNeeded = specNeeded * 0.85;
		if (ring != null) {
			// Vigour Ring
			if (ring.getId() == 19669) {
				specNeeded = (int) tenPercentOfSpecialNeeded;
			}
		}

		if (specNeeded <= specAmount) {
			p.getCombatDefinitions().specpercentage -= specNeeded;
			p.getCombatDefinitions().setSpecialOff();
			p.getCombatDefinitions().refreshSpecial();
			return true;
		}
		return false;
	}

	public void specialAttack(Player p, Player opp) {
		int weaponID = p.getEquipment().get(3).getId();
		if (rangedWeapon(p)) {
			if (!this.checkArrows(p)) {
				p.getCombatDefinitions().setSpecialOff();
				p.getCombatDefinitions().refreshSpecial();
				this.removeTarget();
				return;
			}
		}
		if (p.getEquipment().get(3) != null) {
			if (weaponID == 18786) { // KORASI
				if (!p.isDonator) {
					p.getFrames()
							.sendChatMessage(0,
									"<img=1> <col=244080><shad=000000> You need to be a premium member to use this special attack.");
					p.getCombatDefinitions().setSpecialOff();
					p.getCombatDefinitions().refreshSpecial();
					removeTarget();
					return;
				}
				if (!p.korasiSpec) {
					p.getFrames()
							.sendChatMessage(
									0,
									"You try to use the swords special attack but seem unable to figure out what it does.");
					p.getCombatDefinitions().setSpecialOff();
					p.getCombatDefinitions().refreshSpecial();
					removeTarget();
					return;
				}
			}
		}
		if (!canSpec(p, weaponID)) {
			p.getCombatDefinitions().setSpecialOff();
			p.getFrames().sendChatMessage(0,
					"You don't have enough power left.");
			return;
		}
		// 18786

		int maxHit = getPlayerMaxHit(0, weaponID,
				CombatManager.isRangingWeapon(weaponID), false);
		int hit = Misc.random(450);
		int distanceDelay = getDelayDistance(p, (Player) opp);
		switch (weaponID) {
		case 15241: // 2143 projectile handcannon
			hit = Misc.random(this.calculateRange());
			p.animate(12175);
			p.graphics(2138);
			ProjectileManager.sendGlobalProjectile(entity, target, 2143, 31,
					35, 45, 23, 0);
			appendRangeDamage(p, opp, hit, distanceDelay);
			this.delay = 2;
			break;
		case 13879: // 2143 projectile handcannon
			hit = Misc.random(this.calculateRange());
			p.animate(10501);
			p.graphics(1836);
			ProjectileManager.sendGlobalProjectile(entity, target, 2143, 31,
					35, 45, 23, 0);
			appendRangeDamage(p, opp, hit, distanceDelay);
			this.delay = 2;
			break;
		case 18786:
			if (opp.getPrayer().usingPrayer(1, 7)) {
				maxHit = (int) (maxHit * 1.5);
			}
			if (opp.getCombat().Multi(opp)) {
				maxHit = maxHit / 2;
			}
			p.graphics2(1224);
			p.animate(1872);
			double minKorasi = maxHit * 0.8;
			double maxKorasi = maxHit * 1.5;
			int korasiHit = Misc.random((int) minKorasi, (int) maxKorasi);
			appendMeleeDamageNoDefPray(p, opp, korasiHit, true);
			break;
		case 1434:
			maxHit *= 1.25;
			p.animate(1060);
			p.graphics2(251);
			this.appendMeleeDamage(p, opp, Misc.random(maxHit), true);
			break;
		case 11235:
			p.animate(426);
			p.graphics2(1111);
			ProjectileManager.sendGlobalProjectile(entity, target, 1099, 42,
					35, 41, 15, 0);
			ProjectileManager.sendGlobalProjectile(entity, target, 1099, 42,
					35, 35, 13, 0);
			int max = this.calculateRange();
			int afterBoost = (int) ((int) max + (max * 0.6) + 80);
			hit = Misc.random(afterBoost);
			bowSpec = true;
			this.appendDBowSpec(p, opp, hit, distanceDelay, 1100);
			hit = Misc.random(afterBoost);
			bowSpec = true;
			appendDBowSpec(p, opp, hit, distanceDelay + 1, 1100);
			break;
		case 14484:
			maxHit *= 1.05;
			p.animate(10961);
			p.graphics(1950);
			int calcedHit = getMeleeHit(p, opp, Misc.random(maxHit), true);
			int hit2 = calcedHit / 2;
			int hit3 = hit2 / 2;
			int hit4 = hit3 + 10;
			boolean failed = false;
			if (calcedHit == 0) {
				hit2 = getMeleeHit(p, opp, Misc.random(maxHit), true);
				hit3 = hit2 / 2;
				hit4 = hit3 + 10;
			}
			if (hit2 == 0) {
				hit3 = getMeleeHit(p, opp, Misc.random(maxHit), true);
				hit4 = hit3 + 10;
			}
			if (hit3 == 0) {
				hit4 = getMeleeHit(p, opp, Misc.random(maxHit), true);
			}
			if (hit4 == 0) {
				failed = true;
			}
			if (failed) {
				appendMeleeDamageNoDef(p, opp, 0, true);
				appendMeleeDamageNoDefPray(p, opp, 1, true);
			} else {
				appendMeleeDamageNoDef(p, opp, calcedHit, true);
				appendMeleeDamageNoDef(p, opp, hit2, true);
				appendMeleeDamageNoDef(p, opp, hit3, true);
				appendMeleeDamageNoDef(p, opp, hit4, true);
			}
			break;
		case 13899:
			p.animate(10502);
			maxHit *= 1.20;
			appendMeleeDamage(p, opp, Misc.random(maxHit), true);
			break;
		case 11694:
			p.animate(7074);
			p.graphics(1222);
			maxHit *= 1.30;
			appendMeleeDamage(p, opp, Misc.random(maxHit), true);
			break;
		case 11698:
			p.animate(7071);
			p.graphics(1220);
			p.getSkills().heal(Misc.random(maxHit) / 2);
			p.getSkills().RestorePray(Misc.random(maxHit) / 4);
			appendMeleeDamage(p, opp, Misc.random(maxHit), true);
			break;
		case 1305:
			p.animate(1058);
			p.graphics2(248);
			maxHit *= 1.25;
			this.appendMeleeDamage(p, opp, Misc.random(maxHit), true);
			break;
		case 13902:
			maxHit *= 1.25;
			SWHDrain = Misc.random(maxHit) * 0.03;
			p.animate(10505);
			p.graphics(1840);
			appendMeleeDamage(p, opp, Misc.random(maxHit), true);
			if (SWHDrain < opp.getSkills().getLevel(Skills.DEFENCE)) {
				opp.getSkills()
						.set(Skills.DEFENCE,
								(int) (opp.getSkills().getLevel(Skills.DEFENCE) - SWHDrain));
			} else {
				opp.getSkills().set(Skills.DEFENCE, 0);
			}
			break;
		case 4587:
			int dragonScimSpec = getMeleeHit(p, opp, Misc.random(maxHit), true);
			p.animate(1872);
			p.graphics2(347);
			appendMeleeDamageNoDef(p, opp, dragonScimSpec, true);
			if (dragonScimSpec > 0) {
				opp.getCombat().prayDisable = 8;
				opp.getPrayer().removeAllProtectionPrayers();
				p.getFrames().sendChatMessage(0,
						"You slash your enemy's protection prayers!");
				opp.getFrames().sendChatMessage(0,
						"Your protection prayers have been slashed!");
				opp.getMask().setApperanceUpdate(true);
				opp.getPrayer().recalculatePrayer();
			} else {
				p.getFrames()
						.sendChatMessage(0,
								"You fail to slice through your enemy's protection prayers!");
			}
			break;
		case 11696:
			maxHit *= 1.10;
			p.animate(7073);
			p.graphics(1223);
			BGSDrain = Misc.random(maxHit) * 0.01;
			appendMeleeDamage(p, opp, Misc.random(maxHit), true);
			if (BGSDrain < opp.getSkills().getLevel(Skills.DEFENCE)) {
				opp.getSkills()
						.set(Skills.DEFENCE,
								(int) (opp.getSkills().getLevel(Skills.DEFENCE) - BGSDrain));
			} else {
				BGSDrain = BGSDrain - opp.getSkills().getLevel(Skills.DEFENCE);
				opp.getSkills().set(Skills.DEFENCE, 0);
				opp.getSkills()
						.set(Skills.STRENGTH,
								(int) (opp.getSkills()
										.getLevel(Skills.STRENGTH) - BGSDrain));
			}
			break;
		case 11700:
			p.animate(7070);
			opp.graphics(2111);
			opp.getCombat().freezeDelay = 40;
			appendMeleeDamage(p, opp, Misc.random(maxHit), true);
			break;
		case 1215:
		case 1231:
		case 5698:
			p.animate(0x426);
			p.graphics2(252);
			maxHit *= 1.235;
			appendMeleeDamage(p, opp, Misc.random(maxHit), true);

			appendMeleeDamage(p, opp, Misc.random(maxHit), true);
			break;
		}
	}

	public void handleGraniteMaulSpecial() {
		Player enemy = (Player) target;
		Player opp = (Player) target;
		Item item = getPlayer().getEquipment().get(Equipment.SLOT_WEAPON);
		if (enemy == null)
			return;
		if (getPlayer().getSkills().playerDead)
			return;
		if (enemy.getSkills().playerDead)
			return;
		if (item != null && item.getId() != 4153)
			return;
		if (isSafe(getPlayer()) || isSafe(enemy)) {
			return;
		}
		if (!getPlayer().getLocation().withinDistance(opp.getLocation(), 8)) {
			queuedSet = false;
			this.removeTarget();
			return;
		}
		if (isSafe(getPlayer()) || isSafe(opp)) {
			getPlayer()
					.getFrames()
					.sendChatMessage(0,
							"You need to be in either a PvP zone or in the wilderness to attack them.");
			this.removeTarget();
			queuedSet = false;
			return;
		}
		if (!getPlayer().getLocation().checkWildRange(getPlayer(), opp)) {
			getPlayer()
					.getFrames()
					.sendChatMessage(0,
							"You need to travel deeper into the wilderness to attack this player.");
			removeTarget();
			queuedSet = false;
			return;
		}
		if (getPlayer().getSkills().isDead() || opp.getSkills().isDead()) {
			queuedSet = false;
			return;
		}
		if (!Multi(getPlayer()) || !Multi(opp)) {
			if (combatWith != opp.getClientIndex() && combatWith > 0) {
				getPlayer().getFrames().sendChatMessage(0,
						"I'm already under attack.");
				this.removeTarget();
				queuedSet = false;
				return;
			}
			if (opp.getCombat().combatWith != getPlayer().getClientIndex()
					&& opp.getCombat().combatWith > 0) {
				getPlayer().getFrames().sendChatMessage(0,
						"This player is already in combat.");
				this.removeTarget();
				queuedSet = false;
				return;
			}
		}
		int maxHit = (int) (getPlayerMaxHit(0, 4153, false, false) * 1.10);
		usingGraniteMaulSpec = true;
		if (getPlayer().getCombatDefinitions().isSpecialOn()) {
			if (getPlayer().getCombatDefinitions().specpercentage < 50) {
				getPlayer()
						.getFrames()
						.sendChatMessage(0,
								"You don't have enough special energy left to do this.");
				removeTarget();
				return;
			} else {
				if (getPlayer().getLocation().withinDistance(
						enemy.getLocation(), 1)) {
					getPlayer().getCombatDefinitions().specpercentage -= 50;
					getPlayer().getCombatDefinitions().setSpecialOff();
					getPlayer().getCombatDefinitions().refreshSpecial();
					getPlayer().animate(1667);
					getPlayer().graphics2(340);
					appendMeleeDamage(getPlayer(), enemy, Misc.random(maxHit),
							true);
					usingGraniteMaulSpec = false;
				}
			}
		}
	}

	public boolean usingGraniteMaulSpec = false;

	private void processPlayer() {
		try {
			final Player p = (Player) entity;
			final Entity target = this.target;
			if (p.getUsername().equalsIgnoreCase(
					((Player) target).getUsername())) {
				p.getFrames()
						.sendChatMessage(0,
								"Patched already. Have a nice time playing legit. - Lay");
				return;
			}
			final byte attackStyle = p.getCombatDefinitions().getAttackStyle();
			final int weaponId = p.getEquipment().get(3) == null ? -1 : p
					.getEquipment().get(3).getId();
			final int ammoId = p.getEquipment().get(13) == null ? -1 : p
					.getEquipment().get(13).getId();
			int specAmt = CombatManager.getSpecAmt(weaponId);
			final boolean specialOn = p.getCombatDefinitions().isSpecialOn()
					&& p.getCombatDefinitions().getSpecpercentage() >= specAmt;
			int maxHit = getPlayerMaxHit(attackStyle, weaponId,
					CombatManager.isRangingWeapon(weaponId), specialOn);
			int speed = CombatManager.getSpeedForWeapon(weaponId);
			/* Changes round to ceil again note 1 */
			// int distance =
			// Math.ceil(entity.getLocation().getDistance(target.getLocation()))
			// > 3 ? 2 : 1;
			if (isSafe(p) || isSafe((Player) target)) {
				p.getFrames()
						.sendChatMessage(0,
								"You need to be in either a PvP zone or in the wilderness to attack them.");
				this.removeTarget();
				return;
			}
			if (!p.getLocation().checkWildRange(p, (Player) target)) {
				p.getFrames()
						.sendChatMessage(0,
								"You need to travel deeper into the wilderness to attack this player.");
				removeTarget();
				return;
			}
			if (!Multi(getPlayer()) || !Multi((Player) target)) {
				if (combatWith != target.getClientIndex() && combatWith > 0) {
					getPlayer().getFrames().sendChatMessage(0,
							"I'm already under attack.");
					this.removeTarget();
					queuedSet = false;
					return;
				}
				if (target.getCombat().combatWith != getPlayer()
						.getClientIndex() && target.getCombat().combatWith > 0) {
					getPlayer().getFrames().sendChatMessage(0,
							"This player is already in combat.");
					this.removeTarget();
					queuedSet = false;
					return;
				}
			}
			target.getCombat().combatWith = getPlayer().getClientIndex();
			target.getCombat().combatWithDelay = 12;
			this.lastHit = System.currentTimeMillis();
			((Player)target).getSkills().setKiller(getPlayer().getUsername());
			((Player) target).getCombat().lastHit = System.currentTimeMillis();

			this.delay += (byte) speed * (MiasmicEffect > 0 ? 2 : 1);
			this.queuedSet = false;
			if (p.getCombatDefinitions().isSpecialOn()) {
				if (weaponId == 4153) {
					if (this.usingGraniteMaulSpec) {
						return;
					} else {
						this.handleGraniteMaulSpecial();
						return;
					}
				} else {
					specialAttack(p, (Player) target);
					return;
				}
			}
			if (rangedWeapon(p)) {
				p.getWalk().reset();
				rangedAttack(p, target, specialOn);
				return;
			}
			sendPlayerAttackEmote(specialOn, weaponId);
			sendPlayerAttackGraphic(specialOn, weaponId, ammoId, target);
			appendMeleeDamage(p, (Player) target, Misc.random(maxHit), false);
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	public int getDrawbackID(Player p) {
		int arrowId = p.getEquipment().get(3).getId();
		switch (arrowId) {
		case 882:
		case 883:
			return 19;
		case 884:
		case 885:
			return 18;
		case 886:
		case 887:
			return 20;
		case 888:
		case 889:
			return 21;
		case 890:
		case 891:
			return 22;
		case 892:
		case 893:
			return 24;
		default:
			return 18;
		}
	}

	public int getProjectileID(Player p) {
		int arrowId = p.getEquipment().get(3).getId();
		switch (arrowId) {
		case 882:
		case 883:
			return 11;
		case 884:
		case 885:
			return 10;
		case 886:
		case 887:
			return 12;
		case 888:
		case 889:
			return 13;
		case 890:
		case 891:
			return 14;
		case 892:
		case 893:
			return 17;
		case 9051:
			return 17;
		default:
			return 18;
		}
	}

	public int getDelayDistance(Player p, Player opp) {
		int dist = Misc.getDistance(p.getLocation().getX(), p.getLocation()
				.getY(), opp.getLocation().getX(), opp.getLocation().getY());
		int delay = 2;
		if (dist > 2) {
			delay += 1;
		}
		return delay;
	}

	public void appendRangeDamage(final Player p, final Player opp,
			int hitBefore, int cycleDelay) {
		if (opp.getPrayer().usingPrayer(0, 18)) {
			hitBefore = (int) (hitBefore * 0.4);
		}
		if (this.boltEffectIncrease != 1.0) {
			hitBefore = (int) (hitBefore * boltEffectIncrease);
			boltEffectIncrease = 1.0;
		}
		p.getCombat().shieldDelay = 1;
		final int hit = getRangeHit(p, opp, hitBefore, false);
		if (hit > opp.getSkills().getHitPoints()) {
			opp.getCombat().removeTarget();
			opp.getCombat().delay = 15;
			opp.getCombat().shieldDelay = 0;
		}
		soulSplit(p, opp, hit);
		if (hit > 0)
			p.getSkills().sendCounter(hit, 0, false);
		GameLogicTaskManager.schedule(new GameLogicTask() {
			@Override
			public void run() {
				if (opp.getCombat().shieldDelay == 0) {
					opp.animate(CombatManager.getDefenceEmote(opp));
				}
				opp.hit(hit, p);
				if (opp.getCombat().vengeance) {
					if (hit > 0) {
						applyVengeance(
								p.getSkills().isDead() ? 0 : Math
										.floor(hit * 0.75), p, opp);
					}
				}
				this.stop();
			}
		}, cycleDelay - 1, 0);
	}

	public void appendDBowSpec(final Player p, final Player opp, int hitBefore,
			int cycleDelay, final int end) {
		if (opp.getPrayer().usingPrayer(0, 18)) {
			hitBefore = (int) (hitBefore * 0.4);
		}
		p.getCombat().shieldDelay = 1;
		int hit = getRangeHit(p, opp, hitBefore, false);
		if (hit < 80) {
			hit = 80;
		}
		final int hit2 = hit;
		soulSplit(p, opp, hit);
		if (hit > 0)
			p.getSkills().sendCounter(hit, 0, false);
		GameLogicTaskManager.schedule(new GameLogicTask() {
			@Override
			public void run() {
				if (opp.getCombat().shieldDelay == 0) {
					opp.animate(CombatManager.getDefenceEmote(opp));
				}
				opp.hit(hit2, p);
				opp.graphics2(end);
				if (opp.getCombat().vengeance) {
					if (hit2 > 0) {
						applyVengeance(
								p.getSkills().isDead() ? 0 : Math
										.floor(hit2 * 0.75), p, opp);
					}
				}
				this.stop();
			}
		}, cycleDelay - 1, 0);
	}

	public void applyVengeance(final double hit, final Player p,
			final Player opp) {
		opp.getMask().setLastChatMessage(
				new ChatMessage(0, 0, "Taste vengeance!"));
		opp.getMask().setChatUpdate(true);
		opp.getCombat().vengeance = false;
		if (hit > opp.getSkills().getHitPoints()) {
			opp.getCombat().removeTarget();
			opp.getCombat().shieldDelay = 0;
		}
		p.hit((int) hit, opp);
	}

	public void appendMeleeDamage(final Player p, final Player opp,
			int hitBefore, boolean spec) {
		if (opp.getPrayer().usingPrayer(0, 19)) {
			hitBefore = (int) (hitBefore * 0.4);
		} else if (opp.getPrayer().usingPrayer(1, 9)) {
			hitBefore = (int) (hitBefore * 0.4);
			if (opp.getCombat().shieldDelay == 0)
				opp.animate(12573);
			opp.graphics2(2230);
		}

		p.getCombat().shieldDelay = 1;
		final int hit = getMeleeHit(p, opp, hitBefore, spec);
		if (opp.getCombat().pendingDamage() + hit > opp.getSkills()
				.getHitPoints()) {
			opp.getCombat().removeTarget();
			opp.getCombat().shieldDelay = 0;
		}
		if (hit > 0)
			p.getSkills().sendCounter(hit, 0, false);
		soulSplit(p, opp, hit);
		if (opp.getCombat().shieldDelay == 0) {
			opp.animate(CombatManager.getDefenceEmote(opp));
		}
		GameLogicTaskManager.schedule(new GameLogicTask() {
			@Override
			public void run() {
				opp.hit(hit, p);
				if (opp.getCombat().vengeance) {
					if (hit > 0) {
						applyVengeance(
								p.getSkills().isDead() ? 0 : Math
										.floor(hit * 0.75), p, opp);
					}
				}
				this.stop();
			}
		}, 0, 0);
	}

	public int pendingDamage() {
		Player p = (Player) entity;
		int totalDamage = 0;
		for (Hit h : p.getQueuedHits()) {
			totalDamage += h.getDamage();
		}
		return totalDamage;
	}

	public void appendMeleeDamageNoDef(final Player p, final Player opp,
			int hitBefore, boolean spec) {
		if (opp.getPrayer().usingPrayer(0, 19)) {
			hitBefore = (int) (hitBefore * 0.4);
		} else if (opp.getPrayer().usingPrayer(1, 9)) {
			hitBefore = (int) (hitBefore * 0.4);
			if (opp.getCombat().shieldDelay == 0)
				opp.animate(12573);
			opp.graphics2(2230);
		}
		p.getCombat().shieldDelay = 1;
		final int hit = hitBefore;
		if (opp.getCombat().pendingDamage() + hit > opp.getSkills()
				.getHitPoints()) {
			opp.getCombat().removeTarget();
			opp.getCombat().shieldDelay = 0;
		}
		soulSplit(p, opp, hit);
		if (opp.getCombat().shieldDelay == 0) {
			opp.animate(CombatManager.getDefenceEmote(opp));
		}
		if (hit > 0)
			p.getSkills().sendCounter(hit, 0, false);
		GameLogicTaskManager.schedule(new GameLogicTask() {
			@Override
			public void run() {
				opp.hit(hit);
				if (opp.getCombat().vengeance) {
					if (hit > 0) {
						applyVengeance(
								p.getSkills().isDead() ? 0 : Math
										.floor(hit * 0.75), p, opp);
					}
				}
				this.stop();
			}
		}, 0, 0);
	}

	public void appendMeleeDamageNoDefPray(final Player p, final Player opp,
			int hitBefore, boolean spec) {
		p.getCombat().shieldDelay = 1;
		final int hit = hitBefore;
		if (opp.getCombat().pendingDamage() + hit > opp.getSkills()
				.getHitPoints()) {
			opp.getCombat().removeTarget();
			opp.getCombat().shieldDelay = 0;
		}
		soulSplit(p, opp, hit);
		if (opp.getCombat().shieldDelay == 0) {
			opp.animate(CombatManager.getDefenceEmote(opp));
		}
		if (hit > 0)
			p.getSkills().sendCounter(hit, 0, false);
		GameLogicTaskManager.schedule(new GameLogicTask() {
			@Override
			public void run() {
				opp.hit(hit);
				if (opp.getCombat().vengeance) {
					if (hit > 0) {
						applyVengeance(
								p.getSkills().isDead() ? 0 : Math
										.floor(hit * 0.75), p, opp);
					}
				}
				this.stop();
			}
		}, 0, 0);
	}

	public double boltEffectIncrease = 1.0;

	/* Note for me */
	public double getRangeAccuracy(Player p, Player opp, boolean spec) {
		final double A = 0.655;
		// final double A = 0.705;
		double atkBonus = p.getCombatDefinitions().bonus[4];
		double defBonus = opp.getCombatDefinitions().bonus[/* 4 + 5 */8];
		if (atkBonus < 1) {
			atkBonus = 0;
		}
		if (defBonus < 1) {
			defBonus = 0;
		}
		double atk = (atkBonus * p.getSkills().level[4]);
		double def = (defBonus * opp.getSkills().level[1]);
		if (CombatManager.wearingVoid(p, 11664)) {
			atk *= 1.25;
		}
		if (p.getPrayer().usingPrayer(0, 3)) {
			atk *= 1.05;
		}
		if (p.getPrayer().usingPrayer(0, 11)) {
			atk *= 1.10;
		}
		if (p.getPrayer().usingPrayer(0, 20)) {
			atk *= 1.15;
		}
		if (opp.getPrayer().usingPrayer(0, 0)) {
			def *= 1.05;
		}
		if (opp.getPrayer().usingPrayer(0, 5)) {
			def *= 1.10;
		}
		if (opp.getPrayer().usingPrayer(0, 13)) {
			def *= 1.15;
		}
		if (opp.getPrayer().usingPrayer(0, 25)) {
			def *= 1.20;
		}
		if (opp.getPrayer().usingPrayer(0, 27)) {
			def *= 1.25;
		}
		int boltChance = Misc.random(15);
		if (boltChance == 5) {
			Item arrows = p.getEquipment().get(Equipment.SLOT_ARROWS);
			Item wep = p.getEquipment().get(Equipment.SLOT_WEAPON);
			if (arrows != null) {
				if (wep != null) {
					if (wep.getId() == 9185 || wep.getId() == 18357) {
						switch (arrows.getId()) {
						case 9244:
							atk *= 1.30;
							opp.graphics(756);
							this.boltEffectIncrease = 1.80;
							break;
						case 9243:
							break;
						}
					}
				}
			}
		}
		if (bowSpec) {
			atk *= 1.10;
			bowSpec = false;
		}
		double OUTCOME = A * (atk / def);
		return OUTCOME;
	}

	public int getRangeHit(Player p, Player opp, int hit, boolean spec) {
		double accuracy = getRangeAccuracy(p, opp, spec);
		Random random = new Random();
		int hitBefore = hit;
		if (opp.getPrayer().usingPrayer(1, 8)) {
			hitBefore = (int) (hitBefore * 0.4);
			if (opp.getCombat().shieldDelay == 0)
				opp.animate(12573);
			opp.graphics(2229);
			p.hit(Misc.random(70));
		}
		hit = hitBefore;
		if (accuracy > 1.0) {
			accuracy = 1;
		}
		if (accuracy < random.nextDouble()) {
			return 0;
		} else {
			return hit;
		}
	}

	public int getMeleeHit(Player p, Player opp, int hit, boolean spec) {
		double accuracy = getMeleeAccuracy(p, opp, spec);
		Random random = new Random();
		if (accuracy > 1.0) {
			accuracy = 1;
		}
		if (accuracy < random.nextDouble()) {
			return 0;
		} else {
			return hit;
		}
	}

	/* Note for me */
	public double getMeleeAccuracy(Player p, Player opp, boolean spec) {
		try {
			final double A = 0.825;
			// final double A = 0.755;
			int bonus = 1;
			Item wep = p.getEquipment().get(Equipment.SLOT_WEAPON);
			if (wep != null) {
				if (wep.getDefinition().name.toLowerCase().contains("maul")) {
					bonus = 2;
				}
			}
			double atkBonus = p.getCombatDefinitions().bonus[bonus];
			double defBonus = opp.getCombatDefinitions().bonus[bonus + 5];
			if (atkBonus < 1) {
				atkBonus = 0;
			}
			if (defBonus < 1) {
				defBonus = 0;
			}
			double atk = (atkBonus * p.getSkills().level[0]);
			double def = (defBonus * opp.getSkills().level[1]);
			atk += 2;
			if (CombatManager.wearingVoid(p, 11665)) {
				atk *= 1.15;
			}

			if (spec) {
				switch (p.getEquipment().get(3).getId()) {
				case 4153:
					atk *= 1.05;
					break;
				case 4587:
				case 1305:
					atk *= 1.15;
					break;
				case 1434:
				case 11694:
					atk *= 1.17;
					break;
				case 14484:
					atk *= 1.05;
					break;
				case 1215:
				case 1231:
				case 5698:
					atk *= 1.45;
					break;
				case 13899:
					atk *= 1.25;
					break;
				}
			} else {
				switch (p.getEquipment().get(3).getId()) {
				case 18349:
					atk *= 1.35;
					break;
				}
			}
			if (p.getPrayer().usingPrayer(0, 2)) {
				atk *= 1.05;
			}
			if (p.getPrayer().usingPrayer(0, 7)) {
				atk *= 1.10;
			}
			if (opp.getPrayer().usingPrayer(0, 0)) {
				def *= 1.05;
			}
			if (opp.getPrayer().usingPrayer(0, 5)) {
				def *= 1.10;
			}
			if (opp.getPrayer().usingPrayer(0, 13)) {
				def *= 1.15;
			}
			if (opp.getPrayer().usingPrayer(0, 25)) {
				def *= 1.20;
			}
			if (opp.getPrayer().usingPrayer(0, 27)) {
				def *= 1.25;
			}

			final double OUTCOME = A * (atk / def);
			return OUTCOME;
		} catch (Exception e) {

		}
		return -1;
	}

	public void rangedAttack(Player p, Entity opp, boolean usingSpec) {
		int weaponID = p.getEquipment().get(3) == null ? -1 : p.getEquipment()
				.get(3).getId();
		Player opponant = (Player) opp;
		int hit = Misc.random(this.calculateRange());
		int distanceDelay = getDelayDistance(p, (Player) opp);
		if (!this.checkArrows(p) && !noArrowRange(p)) {
			return;
		}
		switch (weaponID) {
		case 839:
		case 841:
		case 843:
		case 845:
		case 847:
		case 849:
		case 851:
		case 853:
		case 859:
		case 861:
			p.animate(426);
			p.graphics2(getDrawbackID(p));
			ProjectileManager.sendGlobalProjectile(entity, target, 15, 42, 35,
					45, 23, 0);
			appendRangeDamage(p, opponant, hit, distanceDelay);
			break;

		case 11235:
			p.animate(426);
			p.graphics2(1111);
			ProjectileManager.sendGlobalProjectile(entity, target, 15, 42, 35,
					45, 23, 0);
			ProjectileManager.sendGlobalProjectile(entity, target, 15, 42, 35,
					20, 23, 0);
			appendRangeDamage(p, opponant, hit, distanceDelay);
			appendRangeDamage(p, opponant, Misc.random(this.calculateRange()),
					distanceDelay + 1);
			break;
		case 15241:
			p.animate(12174);
			p.graphics(2138);
			ProjectileManager.sendGlobalProjectile(entity, target, 2143, 31,
					35, 45, 23, 0);
			appendRangeDamage(p, opponant, hit, distanceDelay);
		case 13879:
			p.animate(10501);
			p.graphics(1836);
			ProjectileManager.sendGlobalProjectile(entity, target, 2143, 31,
					35, 45, 23, 0);
			appendRangeDamage(p, opponant, hit, distanceDelay);
			break;
		/*
		 * case 839: case 841: case 843: case 845: case 847: case 849: case 851:
		 * case 853: case 859: case 861: p.animate(426);
		 * p.graphics2(getDrawbackID(p));
		 * ProjectileManager.sendGlobalProjectile(entity, target, 15, 42, 35,
		 * 45, 23, 0); appendRangeDamage(p, opponant, hit, distanceDelay);
		 * break;
		 */
		case 13957:
		case 13883: // morrigan throwin axe
			p.graphics2(1838, 0);
			p.animate(10504, 0);
			ProjectileManager.sendGlobalProjectile(entity, target, 1839, 42,
					35, 45, 23, 0);
			appendRangeDamage(p, opponant, hit, distanceDelay);
			/*
			 * //p.getActionSender().slopedProjectile(p, casterY, casterX,
			 * offsetY, offsetX, 50, 70, 1839, 39, 39, opp.playerId,
			 * getSlope());
			 */
			break;
		case 13953:
		case 18357:
		case 9185:
			p.animate(4230);
			ProjectileManager.sendGlobalProjectile(entity, target, 27, 42, 35,
					48, 12/* 18 */, 0);
			appendRangeDamage(p, opponant, hit, distanceDelay);
			break;
		case 4734:
			p.animate(2075);
			ProjectileManager.sendGlobalProjectile(entity, target, 27, 42, 35,
					48, 12/* 18 */, 0);
			appendRangeDamage(p, opponant, hit, distanceDelay);
			break;
		}
	}

	public void processHit() {
		if (combatHitDefinitions == null)
			return;
		if (hitDelay != 0)
			return;
		Entity target = combatHitDefinitions.getTarget();
		if (target instanceof Player) {
			if (!((Player) target).isOnline() || target.isDead()) {
				if (prayerAfterHitDelay == -1)
					combatHitDefinitions = null;
				return;
			}
			((Player) target).getCombat().setLastAttackedTime(
					System.currentTimeMillis());
		}
		/*
		 * Prayer while hitting
		 */
		int hit1 = PlayerProbOfHiting(target,
				combatHitDefinitions.getMaxDamage(),
				combatHitDefinitions.getBonuses(),
				combatHitDefinitions.getWeaponId(),
				combatHitDefinitions.isSpecialOn()) ? combatHitDefinitions
				.getMaxDamage() : 0;
		if (combatHitDefinitions.isMeleeDeflectPray()
				|| combatHitDefinitions.isRangeDeflectPray()) {
			if (hit1 * 10 / 100 > 0)
				entity.hit(hit1 * 10 / 100);
			target.animate(12573);
			target.graphics(combatHitDefinitions.isMeleeDeflectPray() ? 2230
					: 2229);
		} else if (combatHitDefinitions.isSoulSplitPray())
			ProjectileManager.sendGlobalProjectile(entity, target, 2263, 11,
					11, 30, 20, 0);
		if (combatHitDefinitions.isLeechAttack()
				|| combatHitDefinitions.isLeechRanged()
				|| combatHitDefinitions.isLeechDefence()
				|| combatHitDefinitions.isLeechStrength())
			ProjectileManager
					// atk = 2231
					// range = 2236 defence 2244 mage = 2248
					.sendGlobalProjectile(
							target,
							entity,
							combatHitDefinitions.isLeechAttack() ? 2231
									: (combatHitDefinitions.isLeechRanged() ? 2236
											: (combatHitDefinitions
													.isLeechDefence() ? 2244
													: 2248)), 30, 30, 30, 0, 0);
		else if (combatHitDefinitions.isTurmoilPray())
			((Player) target).getPrayer().setBoost(8, true);

		/*
		 * Defence Animation
		 */
		if (!combatHitDefinitions.isMeleeDeflectPray()
				&& !combatHitDefinitions.isRangeDeflectPray())
			target.animate(CombatManager.getDefenceEmote(target));

		/*
		 * Hit
		 */
		if (combatHitDefinitions.isSpecialOn()
				&& combatHitDefinitions.getWeaponId() == 14484) {
			int totalDamage = 0;
			int hit2 = hit1 == 0 ? (PlayerProbOfHiting(target,
					combatHitDefinitions.getMaxDamage(),
					combatHitDefinitions.getBonuses(),
					combatHitDefinitions.getWeaponId(),
					combatHitDefinitions.isSpecialOn()) ? combatHitDefinitions
					.getMaxDamage() : 0) : hit1;
			if (hit1 == 0 && hit2 == 0) {
				int hit3 = PlayerProbOfHiting(target,
						combatHitDefinitions.getMaxDamage(),
						combatHitDefinitions.getBonuses(),
						combatHitDefinitions.getWeaponId(),
						combatHitDefinitions.isSpecialOn()) ? combatHitDefinitions
						.getMaxDamage() : 0;
				if (hit3 == 0) {
					int hit4 = PlayerProbOfHiting(target,
							combatHitDefinitions.getMaxDamage(),
							combatHitDefinitions.getBonuses(),
							combatHitDefinitions.getWeaponId(),
							combatHitDefinitions.isSpecialOn()) ? combatHitDefinitions
							.getMaxDamage() : 0;
					if (hit4 == 0) {
						target.hit(hit2);
						target.hit(hit1);
						target.hit(1);
						target.hit(hit3);
						totalDamage = 1;
					} else {
						target.hit(hit2);
						target.hit(hit1);
						target.hit((int) (hit4 * 1.5));
						target.hit(hit3);
						totalDamage = (int) (hit4 * 1.5);
					}
				} else {
					target.hit(hit2);
					target.hit(hit1);
					target.hit(hit3);
					target.hit(hit3);
					totalDamage = hit3 * 2;
				}
			} else {
				target.hit(hit1 == 0 ? hit2 : hit2 / 2);
				target.hit(hit1);
				target.hit(hit2 / 4);
				target.hit(hit1 == 0 ? hit2 / 2 : hit2 / 4);
				totalDamage = (hit1 == 0 ? hit2 : hit2 / 2) + hit1
						+ (hit1 == 0 ? hit2 / 2 : hit2 / 4) + (hit2 / 4);
			}
			combatHitDefinitions.setMaxDamage(totalDamage);
		} else {
			target.hit(hit1);
			combatHitDefinitions.setMaxDamage(hit1);
		}
		hitDelay = -1;
		if (prayerAfterHitDelay == -1)
			combatHitDefinitions = null;
	}

	private boolean PlayerProbOfHiting(Entity target, int attackStyle,
			short[] bonus, int weaponId, boolean specialOn) {
		Player p = (Player) entity;
		if (target instanceof Player) { // TODO
			Player enemy = (Player) target;
			double att = bonus[1] + p.getSkills().getLevel(Skills.ATTACK);
			if (specialOn) {
				double multiplier = 0.25;
				multiplier += CombatManager
						.getSpecDamageDoublePercentage(weaponId) / 2;
				att = att * multiplier;
			}
			double def = enemy.getCombatDefinitions().getBonus()[6]
					+ enemy.getSkills().getLevel(Skills.DEFENCE);
			double prob = att / def;
			if (prob > 0.80)
				prob = 0.80;
			return prob >= Math.random();
		}
		return true;
	}

	public void processPrayerBeforeHit() {
		if (combatHitDefinitions == null)
			return;
		if (prayerBeforeHitDelay != 0)
			return;
		Entity target = combatHitDefinitions.getTarget();
		if (target instanceof Player)
			if (!((Player) target).isOnline() || target.isDead())
				return;

		if (combatHitDefinitions.isSapWarrior())
			ProjectileManager.sendGlobalProjectile(entity, target, 2215, 35,
					35, 30, 0, 140);
		if (combatHitDefinitions.isSapRanger())
			ProjectileManager.sendGlobalProjectile(entity, target, 2218, 35,
					35, 30, 0, 140);
		if (combatHitDefinitions.isSapSpirit())
			ProjectileManager.sendGlobalProjectile(entity, target, 2224, 35,
					35, 30, 0, 140);
		prayerBeforeHitDelay = -1;
	}

	public void processPrayerAfterHit() {
		if (combatHitDefinitions == null)
			return;
		if (prayerAfterHitDelay != 0)
			return;
		Entity target = combatHitDefinitions.getTarget();
		if (target instanceof Player) {
			if (!((Player) target).isOnline() || target.isDead()) {
				combatHitDefinitions = null;
				return;
			}
		}

		if (combatHitDefinitions.isSoulSplitPray()) {
			int gain = combatHitDefinitions.getMaxDamage() / 50;
			((Player) entity).getSkills().heal(gain * 10);
			if (target instanceof Player)
				((Player) target).getSkills().drainPray(gain);
			target.graphics(2264);
			ProjectileManager.sendGlobalProjectile(target, entity, 2263, 11,
					11, 30, 0, 0);
		}
		if (combatHitDefinitions.isSapWarrior()) {
			boolean bost = ((Player) entity).getPrayer().usingBoost(0);
			((Player) target).getSkills().set(
					0,
					((Player) target).getSkills().getLevelForXp(0)
							* (bost ? 80 : 90) / 100);
			((Player) target).getSkills().set(
					2,
					((Player) target).getSkills().getLevelForXp(2)
							* (bost ? 80 : 90) / 100);
			((Player) target).getSkills().set(
					1,
					((Player) target).getSkills().getLevelForXp(1)
							* (bost ? 80 : 90) / 100);
			target.graphics(2216);
			((Player) entity).getPrayer().setBoost(0, true);
		}
		if (combatHitDefinitions.isSapRanger()) {
			boolean bost = ((Player) entity).getPrayer().usingBoost(1);
			((Player) target).getSkills().set(
					4,
					((Player) target).getSkills().getLevelForXp(4)
							* (bost ? 80 : 90) / 100);
			((Player) target).getSkills().set(
					1,
					((Player) target).getSkills().getLevelForXp(1)
							* (bost ? 80 : 90) / 100);
			target.graphics(2219);
			((Player) entity).getPrayer().setBoost(1, true);
		}
		if (combatHitDefinitions.isSapSpirit()) {
			target.graphics(2225);
		}
		if (combatHitDefinitions.isLeechAttack()) {
			boolean bost = ((Player) entity).getPrayer().usingBoost(3);
			((Player) target).getSkills().set(
					0,
					((Player) target).getSkills().getLevelForXp(0)
							* (bost ? 75 : 90) / 100);
			target.graphics(2232);
			((Player) entity).getPrayer().setBoost(3, true);
		}
		if (combatHitDefinitions.isLeechRanged()) {
			boolean bost = ((Player) entity).getPrayer().usingBoost(5);
			((Player) target).getSkills().set(
					4,
					((Player) target).getSkills().getLevelForXp(4)
							* (bost ? 75 : 90) / 100);
			target.graphics(2238);
			((Player) entity).getPrayer().setBoost(5, true);
		}
		if (combatHitDefinitions.isLeechDefence()) {
			boolean bost = ((Player) entity).getPrayer().usingBoost(6);
			((Player) target).getSkills().set(
					1,
					((Player) target).getSkills().getLevelForXp(1)
							* (bost ? 75 : 90) / 100);
			target.graphics(2246);
			((Player) entity).getPrayer().setBoost(6, true);
		}
		if (combatHitDefinitions.isLeechStrength()) {
			boolean bost = ((Player) entity).getPrayer().usingBoost(7);
			((Player) target).getSkills().set(
					2,
					((Player) target).getSkills().getLevelForXp(2)
							* (bost ? 75 : 90) / 100);
			target.graphics(2250);
			((Player) entity).getPrayer().setBoost(7, true);
		}

		prayerAfterHitDelay = -1;
		combatHitDefinitions = null;
	}

	@SuppressWarnings("unused")
	private void sendPlayerHit(boolean specialon, final int weaponId,
			final int maxHit, final int distance, final Entity target) {
		int randomDamage = Misc.random(maxHit);
		if (specialon) {
			int p1 = maxHit * 5 / 10;
			double m1 = 1.25 + CombatManager
					.getSpecDamageDoublePercentage(weaponId);
			if (p1 > randomDamage && m1 > Math.random()) {
				int d1 = maxHit - p1;
				randomDamage = p1 + Misc.random(d1);
			}
		}
		final boolean protectPray = randomDamage > 0
				&& (target instanceof Player && (CombatManager
						.isRangingWeapon(weaponId)
						&& (((Player) target).getPrayer().usingPrayer(0, 18)) || ((Player) target)
						.getPrayer().usingPrayer(0, 19)));
		final boolean sapWarrior = randomDamage > 0
				&& !CombatManager.isRangingWeapon(weaponId)
				&& (target instanceof Player && entity instanceof Player && ((Player) entity)
						.getPrayer().usingPrayer(1, 1)) && Misc.random(14) == 1;
		if (protectPray)
			randomDamage = target instanceof Player ? randomDamage * 60 / 100
					: 0;
		final boolean sapRanger = randomDamage > 0
				&& CombatManager.isRangingWeapon(weaponId)
				&& (target instanceof Player && entity instanceof Player && ((Player) entity)
						.getPrayer().usingPrayer(1, 2)) && Misc.random(14) == 1;
		final boolean sapSpirit = !sapRanger
				&& !sapWarrior
				&& randomDamage > 0
				&& (target instanceof Player && entity instanceof Player && ((Player) entity)
						.getPrayer().usingPrayer(1, 4)) && Misc.random(14) == 1;
		final boolean leechAttack = randomDamage > 0
				&& !CombatManager.isRangingWeapon(weaponId)
				&& (target instanceof Player && entity instanceof Player && ((Player) entity)
						.getPrayer().usingPrayer(1, 10))
				&& Misc.random(14) == 1;
		final boolean leechRanged = randomDamage > 0
				&& CombatManager.isRangingWeapon(weaponId)
				&& (target instanceof Player && entity instanceof Player && ((Player) entity)
						.getPrayer().usingPrayer(1, 11))
				&& Misc.random(14) == 1;
		final boolean leechDefence = !leechAttack
				&& !leechRanged
				&& randomDamage > 0
				&& (target instanceof Player && entity instanceof Player && ((Player) entity)
						.getPrayer().usingPrayer(1, 13))
				&& Misc.random(14) == 1;
		final boolean leechStrength = !leechAttack
				&& !leechDefence
				&& randomDamage > 0
				&& !CombatManager.isRangingWeapon(weaponId)
				&& (target instanceof Player && entity instanceof Player && ((Player) entity)
						.getPrayer().usingPrayer(1, 14))
				&& Misc.random(14) == 1;
		final boolean rangeDeflectPray = randomDamage > 0
				&& (target instanceof Player
						&& CombatManager.isRangingWeapon(weaponId) && ((Player) target)
						.getPrayer().usingPrayer(1, 8));
		final boolean meleeDeflectPray = randomDamage > 0
				&& (target instanceof Player
						&& !CombatManager.isRangingWeapon(weaponId) && ((Player) target)
						.getPrayer().usingPrayer(1, 9));
		if (rangeDeflectPray || meleeDeflectPray)
			randomDamage = target instanceof Player ? randomDamage * 60 / 100
					: 0;
		final boolean soulSplitPray = (entity instanceof Player && ((Player) entity)
				.getPrayer().usingPrayer(1, 18));
		final boolean turmoilPray = (target instanceof Player
				&& ((Player) target).getPrayer().usingPrayer(1, 19) && !((Player) target)
				.getPrayer().usingBoost(8));

		if (sapWarrior || sapRanger || sapSpirit) {
			if (sapWarrior) {
				entity.animate(12569);
				target.graphics(2214);
			}
			if (sapRanger) {
				entity.animate(12569);
				target.graphics(2217);
			}
			if (sapSpirit) {
				entity.animate(12569);
				target.graphics(2223);
			}
		}
		combatHitDefinitions = new CombatHitDefinitions(target, weaponId,
				randomDamage, specialon, ((Player) entity)
						.getCombatDefinitions().getBonus(), meleeDeflectPray,
				rangeDeflectPray, protectPray, soulSplitPray, sapWarrior,
				sapRanger, sapSpirit, leechAttack, leechRanged, leechDefence,
				leechStrength, turmoilPray);
		hitDelay = (weaponId == 4153 && specialon) ? 0 : (byte) distance;

	}

	private void processNpc() {

	}

	private int getPlayerMaxHit(int attackStyle, int weaponId,
			boolean isRanging, boolean specialOn) {
		Player p = (Player) entity;
		if (!isRanging) {
			int StrengthLvl = p.getSkills().getLevel(Skills.STRENGTH);
			double PrayerBonus = 1;
			if (p.getPrayer().usingPrayer(0, 1))
				PrayerBonus = 1.05;
			else if (p.getPrayer().usingPrayer(0, 6))
				PrayerBonus = 1.1;
			else if (p.getPrayer().usingPrayer(0, 14))
				PrayerBonus = 1.15;
			else if (p.getPrayer().usingPrayer(0, 25))
				PrayerBonus = 1.18;
			else if (p.getPrayer().usingPrayer(0, 26))
				PrayerBonus = 1.23;
			else if (p.getPrayer().usingPrayer(1, 19))
				PrayerBonus = (p.getPrayer().usingBoost(8) && target instanceof Player) ? 1.30 + (((Player) target)
						.getSkills().getLevelForXp(Skills.STRENGTH) / 1000)
						: 1.23;
			double OtherBonus = 1;
			int StyleBonus = 0;
			if (attackStyle == 0)
				StyleBonus = 0;
			else if (attackStyle == 2)
				StyleBonus = 1;
			else if (attackStyle == 3)
				StyleBonus = 3;
			double EffectiveStrenght = Math.round(StrengthLvl * PrayerBonus
					* OtherBonus)
					+ StyleBonus;
			int StrengthBonus = p.getCombatDefinitions().getBonus()[11];
			double BaseDamage = 15 + EffectiveStrenght + (StrengthBonus / 8)
					+ (EffectiveStrenght * StrengthBonus / 64);
			double finaldamage;
			if (specialOn)
				finaldamage = Math
						.floor(BaseDamage
								* CombatManager
										.getSpecDamageDoublePercentage(weaponId));
			else
				finaldamage = Math.round(BaseDamage);
			if (CombatManager.wearingDharok(p))
				finaldamage += ((p.getSkills().getLevelForXp(3) * 10) - p
						.getSkills().getHitPoints()) / 2;
			return (int) finaldamage;
		}
		return 0;
	}

	private void sendPlayerAttackEmote(boolean specialon, int weaponId) {
		Player p = (Player) entity;
		byte attackStyle = p.getCombatDefinitions().getAttackStyle();
		if (weaponId == -1) {
			this.entity.animate(attackStyle == 2 ? 422 : 423);
			return;
		}
		String itemName = new Item(weaponId).getDefinition().name;
		if (!specialon) {
			if (itemName.contains("scimitar")
					|| itemName.contains("Korasi's sword"))
				this.entity.animate(12029);
			else if (itemName.contains("Chaotic longsword")
					|| itemName.contains("Chaotic rapier"))
				this.entity.animate(12310);
			else if (itemName.contains("longsword"))
				this.entity.animate(12029);
			else if (itemName.contains("whip"))
				this.entity.animate(1658);
			else if (itemName.contains("claws"))
				this.entity.animate(393);
			else if (weaponId == 6528 || itemName.contains("Chaotic maul"))
				this.entity.animate(2661);
			else if (itemName.contains("pickaxe"))
				this.entity.animate(attackStyle == 2 ? 402 : 401);
			else if (itemName.contains("Dharok"))
				this.entity.animate(attackStyle == 2 ? 2067 : 2066);
			else if (itemName.contains("Verac"))
				this.entity.animate(2062);
			else if (itemName.contains("Granite maul"))
				this.entity.animate(1665);
			else if (itemName.contains("Guthan"))
				this.entity.animate(attackStyle == 2 ? 2081 : 2080);
			else if (itemName.contains("godsword")
					|| itemName.contains("2h sword")
					|| itemName.contains("Saradomin sword"))
				this.entity.animate(attackStyle == 2 ? 7048 : 7041);
			else if (itemName.contains("Keris") || itemName.contains("dagger"))
				this.entity.animate(attackStyle == 2 ? 401 : 402);
			else if (itemName.contains("Karil"))
				this.entity.animate(2075);
			else if (!itemName.contains("Karil")
					&& itemName.contains("crossbow"))
				this.entity.animate(4230);
			else if (itemName.contains("dart"))
				this.entity.animate(582);
			else if (itemName.contains("knife"))
				this.entity.animate(806);
			else if (itemName.contains("bow"))
				this.entity.animate(426);
			else
				this.entity.animate(451);
		} else {
			if (weaponId == 14484) {
				this.entity.animate(10961);
			} else if (weaponId == 11694) {
				this.entity.animate(7074);
			} else if (weaponId == 4153) {
				this.entity.animate(1667);
			} else if (weaponId == 13879) {
				this.entity.animate(10501);
			}
		}
	}

	private void sendPlayerAttackGraphic(boolean specialon, int weaponId,
			int ammoId, Entity target) {
		if (weaponId == -1)
			return;
		String itemName = new Item(weaponId).getDefinition().name;
		if (!specialon) {
			if (weaponId == 868) {
				entity.graphics(225, 100 << 16);
			}
			if (weaponId == 867) {
				entity.graphics(224, 100 << 16);
			} else if (weaponId == 866) {
				entity.graphics(223, 100 << 16);
			} else if (weaponId == 865) {
				entity.graphics(222, 100 << 16);
			} else if (weaponId == 864) {
				entity.graphics(221, 100 << 16);
			} else if (weaponId == 863) {
				entity.graphics(220, 100 << 16);
			} else if (weaponId == 4214) {
				entity.graphics(250, 100 << 16);
				ProjectileManager.sendGlobalProjectile(entity, target, 249, 34,
						36, 41, 15, 0);
			} else if (itemName.contains("bow")) {
				if (ammoId == 882) {
					entity.graphics(19, 100 << 16);
					ProjectileManager.sendGlobalProjectile(entity, target, 10,
							36, 40, 41, 15, 0);

				} else if (ammoId == 884) {
					entity.graphics(18, 100 << 16);
					ProjectileManager.sendGlobalProjectile(entity, target, 11,
							36, 40, 41, 15, 0);
				} else if (ammoId == 886) {
					entity.graphics(20, 100 << 16);
					ProjectileManager.sendGlobalProjectile(entity, target, 12,
							36, 40, 41, 15, 0);
				} else if (ammoId == 9706) {
					entity.graphics(25, 100 << 16);
					ProjectileManager.sendGlobalProjectile(entity, target, 25,
							36, 40, 41, 15, 0);
				} else if (ammoId == 888) {
					entity.graphics(21, 100 << 16);
					ProjectileManager.sendGlobalProjectile(entity, target, 13,
							36, 40, 41, 15, 0);
				} else if (ammoId == 890) {
					entity.graphics(22, 100 << 16);
					ProjectileManager.sendGlobalProjectile(entity, target, 14,
							36, 40, 41, 15, 0);
				} else if (ammoId == 892) {
					entity.graphics(24, 100 << 16);
					ProjectileManager.sendGlobalProjectile(entity, target, 15,
							36, 40, 41, 15, 0);
				}
			}
		} else {
			if (weaponId == 14484) {
				this.entity.graphics(1950);
			} else if (weaponId == 11694) {
				this.entity.graphics(1222);
			} else if (weaponId == 4153) {
				this.entity.graphics(340);
			} else if (weaponId == 13879) {
				this.entity.graphics(1836);
			}
		}
	}

	public void setLastAttackedTime(long lastAttackedTime) {
		this.lastAttackedTime = lastAttackedTime;
	}

	public long getLastAttackedTime() {
		return lastAttackedTime;
	}

}
