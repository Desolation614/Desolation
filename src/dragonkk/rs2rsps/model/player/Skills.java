package dragonkk.rs2rsps.model.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.events.Task;
import dragonkk.rs2rsps.model.GlobalDropManager;
import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.skills.summoning.impl.PackYak;
import dragonkk.rs2rsps.util.Misc;
import dragonkk.rs2rsps.util.RSTile;

/**
 * Manages the player's skills.
 * 
 * @author Graham
 * 
 */
public class Skills implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5844927980818218635L;

	
	public double determineCombatXp(int[] xp, boolean magic) {
		double gained = 0;
		/**
		 * 0 = hit
		 * 1 = mage damage
		 */
		gained += xp[0] / 7.5;
		if(magic) {
			player.xpGained += xp[1] * 1 + (xp[0] / 5);
			addXp(6, (xp[1] * 1 + (xp[0] / 5)));
		} else {
			player.xpGained += xp[0] / 2.55;
			addXp(6, xp[0] / 2.55);
		}
		return gained;
	}
	
	public void sendCounter(int hit, int mageXP, boolean mage) {
		double gained = 0;
		if(hit == -1)
			hit = 0;
		if(!mage)
			gained += hit / 2.55;
		else 
			gained = mageXP * 1 + (hit / 5);
		double hp = hit / 7.5;
		player.xpGained += gained + hp;
		addXp(mage? 6 : 0, gained + hp);
	}

	public static final int SKILL_COUNT = 25;
	
	public static boolean playerTeleing = false;

	private transient Player player;
	
	public static boolean playerDieing = false;
	
	public int tbTimer = -1;

	public static int[] drops = new int[20];

	public static Player killer;

	public static final double MAXIMUM_EXP = 200000000;
	
	public void summonMonster(String name) {
		if(name.equals("PackYak")) {
			player.currentlySummoned = new PackYak(player);
		}
	}

	public short level[] = new short[SKILL_COUNT];
	private double xp[] = new double[SKILL_COUNT];
	private short HitPoints;
	private transient boolean goingUp;
	public int fishID;
	public int emoteTick;

	public static List<Integer> ITEMS_TO_DROP = new ArrayList<Integer>();
	
	public static List<Item> deathItems = new ArrayList<Item>();

	@SuppressWarnings("unchecked")
	public void dropItems(Player opp) {
		try {
			if(opp.getRights() > 1) {
				return;
			}
			
			if (player.getInventory().contains(995))
			    player.getInventory().deleteAll(995);
			
			deathItems.addAll((Collection<? extends Item>) player.getEquipment().getEquipment());
			deathItems.addAll((Collection<? extends Item>) player.getInventory().getContainer());
			
			player.getEquipment().getEquipment().clear();
			player.getInventory().getContainer().clear();
			
			for (Item i : deathItems) {
				GlobalDropManager.dropItem(opp, player.getLocation(), i, false);
			}
			
			deathItems.clear();
		} catch (Exception e) {
			
		}
	}

	public void startBoostingSkill() {
		if (goingUp)
			return;
		goingUp = true;
		Server.getEntityExecutor().schedule(new Task() {
			@Override
			public void run() {
				boolean canContinue = false;
				if (!player.isOnline() || isDead()) {
					goingUp = false;
					stop();
					return;
				}
				for (int skillId = 0; skillId < SKILL_COUNT; skillId++) {
					int lvlForXp = getLevelForXp(skillId);
					if (skillId != 5 && level[skillId] != lvlForXp) {
						if (level[skillId] < lvlForXp)
							level[skillId] = (short) (level[skillId] + 1);
						else
							level[skillId] = (short) (level[skillId] - 1);
						canContinue = true;
						player.getFrames().sendSkillLevel(skillId);
					}

				}
				if (!canContinue) {
					goingUp = false;
					stop();
					return;
				}
			}

		}, 60000, 60000);
	}

	public static final String[] SKILL_NAME = { "Attack", "Defence",
			"Strength", "Hitpoints", "Range", "Prayer", "Magic", "Cooking",
			"Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting",
			"Smithing", "Mining", "Herblore", "Agility", "Thieving", "Slayer",
			"Farming", "Runecrafting", "Construction", "Hunter", "Summoning",
			"Dungeoneering" };

	public static final int ATTACK = 0, DEFENCE = 1, STRENGTH = 2,
			HITPOINTS = 3, RANGE = 4, PRAYER = 5, MAGIC = 6, COOKING = 7,
			WOODCUTTING = 8, FLETCHING = 9, FISHING = 10, FIREMAKING = 11,
			CRAFTING = 12, SMITHING = 13, MINING = 14, HERBLORE = 15,
			AGILITY = 16, THIEVING = 17, SLAYER = 18, FARMING = 19,
			RUNECRAFTING = 20, CONSTRUCTION = 21, HUNTER = 22, SUMMONING = 23,
			DUNGEONEERING = 23;

	public Skills() {
		for (int i = 0; i < SKILL_COUNT; i++) {
			level[i] = 1;
			xp[i] = 0;
		}
		level[3] = 10;
		xp[3] = 1184;
		HitPoints = 100;
	}

	public static int[] PVP_DROP = { 4131, 1187, 1215, 1231, 1305, 1377, 1434, 1645, 4087, 4586, 5680, 5681, 5698, 4587, 7158, 11732, 1725, 1704, 6585, 4724, 4726, 4728, 4730, 4753, 4755, 4759, 4757, 4745, 4747, 4749, 4751, 4732, 4734, 4736, 4738, 4708, 4710, 4712, 4714, 4716, 4718, 4720, 4722, 2497, 2491, 2503, 2587, 2583, 2510, 1401, 1403, 1405, 1407, 3054, 4091, 4093, 4095, 4097, 3839, 3840, 3844, 4089, 7403, 7402, 7401, 7400, 10380, 10382, 10442, 10448, 10454, 10462, 10466, 10472, 10788, 10384, 10386, 10388, 10390, 10440, 10446, 10458, 10459, 10470, 10471, 11730, 1033, 1035, 2655, 10372, 10374, 10444, 10450, 10460, 10468, 10786, 11235, 13734, 13736, 3122, 4153, 6809, 6705, 15272, 15266, 2440, 2436, 3024, 2444, 6685
	};

	public static final int PVPDROP() {
		return PVP_DROP[(int) (Math.random() * PVP_DROP.length)];
	}

	public void hit(int hitDiff) {
		if (hitDiff > HitPoints)
			hitDiff = HitPoints;
		HitPoints -= hitDiff;
		if (HitPoints == 0)
			sendDead();
		player.getFrames().sendConfig(1240, HitPoints * 2);
		player.getCombatDefinitions().startHealing();
	}

	public String killerName;

	public void setKiller(String name) {
		killerName = name;
	}

	public Player getPlayer() {
		for (Player p : World.getPlayers()) {
			if (p.getUsername().equalsIgnoreCase(killerName)) {
				return p;
			}
		}
		return null;
	}
	
	public boolean isWearingTokenRing(Player p) {
		if (p.getEquipment().contains(6575) && p.hasTokenRing())
			return true;
		else
			return false;
	}

	public void deathMessage(int x, int y) {
		Player p = getPlayer();
		if (p == null)
			return;
		int tokenAmount = player.getCombat().dangerousPVP(player) ? 3 : 6;
		int finalAmount = 0;
		if (p.extremeDonator) {
			finalAmount = p.extremeDonator ? tokenAmount * 10 : tokenAmount;
		} else if (p.isDonator) {
			finalAmount = (int) (p.isDonator ? tokenAmount * 10 : tokenAmount);
		} else {
			finalAmount = tokenAmount;
		}
		if (isWearingTokenRing(p))
			finalAmount = finalAmount * 6;
		
		p.getFrames().sendChatMessage(0,"You have defeated " + player.getUsername() + ".");
		p.getFrames().sendChatMessage(0, "You are rewarded "+tokenAmount+" PvP Tokens for your efforts!");
		p.getFrames().sendChatMessage(0, "Do ::stats to view your KDR.");
		/*if (p.getCombat().dangerousPVP(p) && player.getCombat().dangerousPVP(player)) {
			player.deathCount++;
			p.killCount++;
		}*/
		p.getInventory().addItem(995, finalAmount);
		RSTile tile = new RSTile((short) x, (short) y, (byte) 0, 0);
		Item item;
		double baseDrop = 0.10 + p.epAmount * 1.75;
		double ratio = Misc.random(251213) / 1457000;
		double overall = baseDrop / ratio;
		if(overall > 1.0) {
			overall = 1.0;
		}
		if(!player.getCombat().dangerousPVP(player)) {
			player.safeDeathCount++;
			p.safeKillCount++;
			int amountToDrop = Misc.random(8);
			for(int i = 0; i < amountToDrop; i++) {
				item = new Item(PVP_DROP[(int) Math.floor(Math.random()
						* PVP_DROP.length)]);
					GlobalDropManager.dropItem(p, tile, item, false);
					//p.getFrames().sendGroundItem(tile, item, false);
				
			}
			//p.getFrames().sendChatMessage(0, "Your drop potenital is now; "+p.epAmount+"%");
		} else {
			player.dangerousDeathCount++;
			p.dangerousKillCount++;
			if(player.getRights() > 1 || p.getRights() > 1)
				return;
			if (player.getInventory().contains(995))
			    player.getInventory().deleteAll(995);
			for(int i = 0; i < player.getInventory().getContainer().getSize(); i++) {
				item = player.getInventory().getContainer().get(i);
				if(item == null)
					continue;
				player.getInventory().deleteItem(item.getId(), item.getAmount());
				//p.getFrames().sendGroundItem(tile, item, false);
				GlobalDropManager.dropItem(p, tile, item, false);
			}
			for(int i = 0; i < player.getEquipment().getEquipment().getSize(); i++) {
				item = player.getEquipment().getEquipment().get(i);
				if(item == null)
					continue;
				player.getEquipment().set(i, null);
				//p.getFrames().sendGroundItem(tile, item, false);
				GlobalDropManager.dropItem(p, tile, item, false);
			}
		}
		item = null;
		tile = null;
	}

	public boolean isDead() {
		return HitPoints == 0 ? true : false;
	}

	public boolean isFishing = false;
	public int fishTimer = 0;
	public boolean playerDead;
	public transient boolean xLogProtection = false;
	

	public void sendDead() {
		if(xLogProtection)
			return;
		xLogProtection = true;
		player.getCombat().removeTarget();
		player.getWalk().reset();
		playerDead = true;
		GameLogicTaskManager.schedule(new GameLogicTask() {
			@Override
			public void run() {
				player.animate(9055);
				this.stop();
				GameLogicTaskManager.schedule(new GameLogicTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						final int x = player.getLocation().getX();
						final int y = player.getLocation().getY();
						player.getCombatDefinitions().refreshSpecial();
						deathMessage(x, y);
						player.getMask().getRegion().teleport(3087, 3496, 0, 0);
						HitPoints = (short) (getLevelForXp(3) * 10);
						player.getFrames().sendConfig(1240, HitPoints * 2);
						player.getCombat().freezeDelay = 0;
						player.getCombat().immuneDelay = 0;
						player.getSkills().tbTimer = -1;
						player.getCombat().delay = 0;
						player.getCombat().vengDelay = 0;
						player.getCombat().vengeance = false;
						playerDieing = false;
						for (int i = 0; i < SKILL_COUNT; i++) {
							set(i, getLevelForXp(i));
						}
						player.getPrayer().closeAllPrayers();
						player.getCombatDefinitions().setSpecpercentage(
								(byte) 100);
						player.getCombatDefinitions().refreshSpecial();
						player.getCombat().removeTarget();
						player.getCombat().clear();
						playerDead = false;
						xLogProtection = false;
						this.stop();
					}
				}, 3, 0);
			}
		}, 1 + player.potionTimer + player.foodTimer + player.getCombat().shieldDelay, 0);
	}
	
	public int determineExtraDeathTime() {
		long now = System.currentTimeMillis();
		long then = player.getCombatDefinitions().getLastFood();
		int time = player.getCombat().shieldDelay;
		if(now - then < 1800) {
			time++;
		}
		then = player.getCombatDefinitions().getLastPot();
		if(now - then < 1800) {
			 time++;
		}
		return time;
	}
	
	public void heal(int hitDiff) {
		if (isDead())
			return;
		HitPoints += hitDiff;
		short max = (short) (getLevel(3) * 10);
		if (HitPoints > max) {
			HitPoints = max;
		}
		player.getFrames().sendConfig(1240, HitPoints * 2);
	}

	public void healBrew(int hitDiff) {
		if (isDead())
			return;
		HitPoints += hitDiff;
		short max = (short) ((getLevel(3) * 10) * 1.15);
		if (HitPoints > max) {
			HitPoints = max;
		}
		player.getFrames().sendConfig(1240, HitPoints * 2);
	}

	public void heal(int hitDiff, short max) {
		HitPoints += hitDiff;
		if (HitPoints > max) {
			HitPoints = max;
		}
		player.getFrames().sendConfig(1240, HitPoints * 2);
	}

	public void RestorePray(int hitDiff) {
		level[5] += hitDiff;
		short max = (short) getLevelForXp(5);
		if (level[5] > max) {
			level[5] = max;
		}
		player.getFrames().sendSkillLevel(5);
	}

	public void drainPray(int drain) {
		level[5] -= drain;
		if (level[5] < 0) {
			level[5] = 0;
		}
		player.getFrames().sendSkillLevel(5);
	}

	public void drain(int skill, int drain) {
		level[skill] -= drain;
		if (level[skill] < 0) {
			level[skill] = 0;
		}
		player.getFrames().sendSkillLevel(skill);
		startBoostingSkill();
	}

	public void Reset() {
		for (int i = 0; i < SKILL_COUNT; i++) {
			level[i] = 1;
			xp[i] = 0;
		}
		refresh();
	}
	
	public void increaseLevelToMaximumModification(int skill, int modification) {
		if(isLevelBelowOriginalModification(skill, modification)) {
			set(skill, level[skill] + modification >= (getLevelForXp(skill) + modification) ? (getLevelForXp(skill) + modification) : level[skill] + modification);
		}
	}
	
	public boolean isLevelBelowOriginalModification(int skill, int modification) {
		return level[skill] < (getLevelForXp(skill) + modification);
	}
	public void increaseLevelToMaximum(int skill, int modification) {
		if(isLevelBelowOriginal(skill)) {
			set(skill, level[skill] + modification >= getLevelForXp(skill) ? getLevelForXp(skill) : level[skill] + modification);
		}
	}
	
	public boolean isLevelBelowOriginal(int skill) {
		return level[skill] < getLevelForXp(skill);
	}

	public int getCombatLevel() {
		int attack = getLevelForXp(0);
		int defence = getLevelForXp(1);
		int strength = getLevelForXp(2);
		int hp = getLevelForXp(3);
		int prayer = getLevelForXp(5);
		int ranged = getLevelForXp(4);
		int magic = getLevelForXp(6);
		int combatLevel = 3;
		combatLevel = (int) ((defence + hp + Math.floor(prayer / 2)) * 0.25) + 1;
		double melee = (attack + strength) * 0.325;
		double ranger = Math.floor(ranged * 1.5) * 0.325;
		double mage = Math.floor(magic * 1.5) * 0.325;
		if (melee >= ranger && melee >= mage) {
			combatLevel += melee;
		} else if (ranger >= melee && ranger >= mage) {
			combatLevel += ranger;
		} else if (mage >= melee && mage >= ranger) {
			combatLevel += mage;
		}
		int summoning = getLevelForXp(Skills.SUMMONING);
		summoning /= 8;
		return combatLevel + summoning;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getLevel(int skill) {
		return level[skill];
	}

	public double getXp(int skill) {
		return xp[skill];
	}

	public int getXPForLevel(int level) {
		int points = 0;
		int output = 0;
		for (int lvl = 1; lvl <= level; lvl++) {
			points += Math.floor((double) lvl + 300.0
					* Math.pow(2.0, (double) lvl / 7.0));
			if (lvl >= level) {
				return output;
			}
			output = (int) Math.floor(points / 4);
		}
		return 0;
	}

	public int getLevelForXp(int skill) {
		double exp = xp[skill];
		int points = 0;
		int output = 0;
		for (int lvl = 1; lvl < (skill == 24 ? 121 : 100); lvl++) {
			points += Math.floor((double) lvl + 300.0
					* Math.pow(2.0, (double) lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if ((output - 1) >= exp) {
				return lvl;
			}
		}
		return player.getUsername().equals("SorenMC") ? 255
				: (skill == 24 ? 120 : 99);
	}

	public void setXp(int skill, double exp) {
		xp[skill] = exp;
		player.getFrames().sendSkillLevel(skill);
	}

	public void addXp(int skill, double exp) {
		int oldLevel = getLevelForXp(skill);
		xp[skill] += exp;
		if (xp[skill] > MAXIMUM_EXP) {
			xp[skill] = MAXIMUM_EXP;
		}
		int newLevel = getLevelForXp(skill);
		int levelDiff = newLevel - oldLevel;
		if (newLevel > oldLevel) {
			level[skill] += levelDiff;
			if (skill == 3)
				heal(100 * levelDiff);
			// LevelUp.levelUp(player, skill);
			player.getMask().setApperanceUpdate(true);
		}
		player.getFrames().sendSkillLevel(skill);
	}
	public void addXp(int skill, double exp, boolean no) {
		int oldLevel = getLevelForXp(skill);
		xp[skill] += exp;
		if (xp[skill] > MAXIMUM_EXP) {
			xp[skill] = MAXIMUM_EXP;
		}
		int newLevel = getLevelForXp(skill);
		int levelDiff = newLevel - oldLevel;
		if (newLevel > oldLevel) {
			level[skill] += levelDiff;
			if (skill == 3)
				heal(100 * levelDiff);
			// LevelUp.levelUp(player, skill);
			player.getMask().setApperanceUpdate(true);
		}
	}

	public void set(int skill, int val) {
		level[skill] = (short) val;
		player.getFrames().sendSkillLevel(skill);
		startBoostingSkill();
	}

	public void sendSkillLevels() {
		for (int i = 0; i < Skills.SKILL_COUNT; i++)
			player.getFrames().sendSkillLevel(i);
	}

	public void refresh() {
		sendSkillLevels();
		player.getFrames().sendConfig(1240, HitPoints * 2);
		this.player.getMask().setApperanceUpdate(true);
	}

	public void setHitPoints(short hitPoints) {
		HitPoints = hitPoints;
	}

	public short getHitPoints() {
		return HitPoints;
	}
	public void decreaseLevelOnce(int skill, int amount) {
		if(level[skill] > (getLevelForXp(skill) - amount)) { 
			if(level[skill] - amount <= (getLevelForXp(skill) - amount)) {
				level[skill] = (short) (getLevelForXp(skill) - amount);
			} else {
				level[skill] -= amount;			
			}
			getPlayer().getFrames().sendSkillLevel(skill);
		}
	}

}
