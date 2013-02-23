package dragonkk.rs2rsps.model.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.model.Animation;
import dragonkk.rs2rsps.model.Entity;
import dragonkk.rs2rsps.model.Graphics;
import dragonkk.rs2rsps.model.Heal;
import dragonkk.rs2rsps.model.Hits;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.Hits.Hit;
import dragonkk.rs2rsps.model.Hits.HitType;
import dragonkk.rs2rsps.model.player.clan.ClanChat;
import dragonkk.rs2rsps.net.Frames;
import dragonkk.rs2rsps.net.codec.ConnectionHandler;
//import dragonkk.rs2rsps.skills.construction.Construction;
import dragonkk.rs2rsps.skills.prayer.Prayer;
import dragonkk.rs2rsps.skills.summoning.SummoningCharacter;
//import dragonkk.rs2rsps.skills.woodcutting.WoodCutting;
import dragonkk.rs2rsps.util.Misc;
import dragonkk.rs2rsps.util.RSTile;
import dragonkk.rs2rsps.util.Serializer;

public class Player extends Entity implements Serializable {

	/**
	 * One day = 86400000 ms or 144000 server cycles. Nevermind. This is too
	 * damn laggy. Was planning on setting an event event on login and then
	 * schedualing it to unmute the player after X amount of time
	 */
	public boolean isDicer = false; 
	public boolean summoningReset = false;
	public GameLogicTask unmutetask = null;
	public boolean korasiSpec = false;
	public boolean timedMuteSet = false;
  public boolean resting = false;
	public int autocast = 0;
	public int timedMuteDay = -1;
	public int timedMuteHr = -1;
	public int timedMuteMin = -1;
	public int timedMuteTime = -1;
	public int posionTimer = 20;
	public int posionStage = 6;
	public int posionStage2 = 3;
	public boolean playerPosioned;
	public int posionImmunity = 0;
	public boolean in2Delay = false;
  public int isMember, isExtremeMember;
	
	public void posionPlayer() {
		if(posionImmunity > 0)
			return;
		posionTimer = 20;
		posionStage = 6;
		posionStage2 = 3;
		playerPosioned = true;
	}

	public void applyPosion() {
		if (playerPosioned) {
			if (posionTimer > 0)
				posionTimer--;
			if (posionTimer < 1) {
				if (posionStage2 == 0) {
					posionStage--;
					posionStage2 = 3;
				}
				if (posionStage2 > 0)
					posionStage2--;
				if (posionStage == 0) {
					posionStage = 6;
					posionStage2 = 3;
					playerPosioned = false;
					return;
				}
				applyDamagePlayerPosion(posionStage * 10);
				posionTimer = 20;
			}
		}
	}

	public String setClan = "";
	public int yellDelay = 0;
	public String lastYell = "";
	public int potionTimer, foodTimer;
	public double epAmount = 50.6;
	public int epTimer = 0;
	private static final long serialVersionUID = -393308022192269041L;
	public boolean playerMuted = false;
	public boolean hasReset = false;
	public boolean isDonator = false;
	public boolean extremeDonator = false;
	public int saveTimer = 0;
	public int xpGained = 0;
	public String skulledOn = "";
	public int skullTimer = 0;
	public boolean wasReset = false;
	public int dangerousKillCount = 0;
	public int dangerousDeathCount = 0;
	public int safeKillCount = 0;
	public int safeDeathCount = 0;
	public String tag = "";
	public transient SummoningCharacter currentlySummoned = null;
	public ClanChat myOwnClanChat = null;
	public ClanChat currentlyTalkingIn = null;
	private TradeSession currentTradeSession;
	private Player tradePartner;
	public boolean tokenRing = false;
	public int currentTab = 0;
	public byte baTitle = 0;
	public int bookId = 192;

	public void setTitle(byte set) {
		baTitle = set;
		this.getMask().setApperanceUpdate(true);
	}

	public int[] resetItems = {};
	public int[] donators = { 13887, 13888, 13893, 13894, 13899, 13900, 13905,
			13906, 13912, 13913, 13917, 13918, 13923, 13924, 13929, 13930,
			13736, 13737, 13738, 13739, 13740, 13741, 13742, 13743, 13744,
			13745 };
	public int[] playerHits = new int[10];
	public int[] hitsDelay = new int[10];
	public int[] dealtDamage = new int[10];
	public long lastResponce;
	public int pickupDelay;
	public boolean founder, admin, developer, jrAdmin, jrMod;
	public boolean lolTest = false;
	public boolean didRequestTrade = false;
	public transient long specPot;
	private transient ShopHandler shophandler;
	public final int[][] ITEM_PRICES = { { 11849, 11300000 }, // Barrows -
																// Dharok's Set
			{ 4178, 782700 }, // Dharok's Greataxe
			{ 4716, 732700 }, // Dharok's Helm
			{ 4720, 1100000 }, // Dharok's Platebody
			{ 4722, 1600000 }, // Dharok's Platelegs
			{ 11846, 6600000 }, // Barrows - Ahrim's Set
			{ 4708, 92400 }, // Ahrim's Hood
			{ 4710, 105200 }, // Ahrim's Staff
			{ 4712, 1700000 }, // Ahrim's Robetop
			{ 4714, 4700000 }, // Ahrim's Robeskirt
			{ 11850, 5500000 }, // Barrows - Guthan's Set
			{ 4724, 2500000 }, // Guthan's Helm
			{ 4726, 1600000 }, // Guthan's Warspear
			{ 4728, 607500 }, // Guthan's Platebody
			{ 4730, 744700 }, // Guthan's Chainskirt
			{ 11852, 6100000 }, // Barrows - Karil's Set
			{ 4732, 101700 }, // Karil's Coif
			{ 4734, 532400 }, // Karil's Crossbow
			{ 4736, 4900000 }, // Karil's Top
			{ 4738, 628000 }, // Karil's Skirt
			{ 4740, 313 }, // Bolt Rack
			{ 11854, 3200000 }, // Barrows - Torag's Set
			{ 4745, 866200 }, // Torag's Helm
			{ 4747, 105500 }, // Torag's Hammers
			{ 4749, 672400 }, // Torag's Platebody
			{ 4751, 1500000 }, // Torag's Platelegs
			{ 11856, 6300000 }, // Barrows - Verac's Set
			{ 4753, 3800000 }, // Verac's Helm
			{ 4755, 256300 }, // Verac's Flail
			{ 4757, 435100 }, // Verac's Brassard
			{ 4759, 1800000 }, // Verac's Plateskirt
			{ 11938, 4700000 }, // Gilded Armour Set (lg)
			{ 11940, 3300000 }, // Gilded Armour Set (sk)
			{ 3481, 1800000 }, // Gilded Platebody
			{ 3483, 1700000 }, // Gilded Platelegs
			{ 3485, 335500 }, // Gilded Plateskirt
			{ 3486, 649200 }, // Gilded Full Helm
			{ 3488, 515800 }, // Gilded Kiteshield
			{ 11860, 103500000 }, // Third-age Ranger Set
			{ 13544, 44900000 }, // Third-age Range Top
			{ 13545, 34000000 }, // Third-age Range Legs
			{ 13546, 12200000 }, // Third-age Range Coif
			{ 13547, 12400000 }, // Third-age Vambraces
			{ 11862, 140500000 }, // Third-age Mage Set
			{ 13548, 60400000 }, // Third-age Robe Top
			{ 13549, 22500000 }, // Third-age Robe
			{ 13550, 34400000 }, // Third-age Mage Hat
			{ 13551, 23100000 }, // Third-age Amulet
			{ 11858, 352900000 }, // Third-age Melee Set
			{ 13552, 127100000 }, // Third-age Platelegs
			{ 13553, 116300000 }, // Third-age Platebody
			{ 13554, 36000000 }, // Third-age Full Helmet
			{ 13555, 73500000 }, // Third-age Kiteshield
			{ 19580, 240800000 }, // Third-age Prayer Set
			{ 19308, 16900000 }, // Third-age druidic Staff
			{ 19311, 52300000 }, // Third-age Druidic Cloak
			{ 19314, 47100000 }, // Third-age Druidic Wreath
			{ 19317, 94200000 }, // Third-age Druidic Robe Top
			{ 19320, 47100000 }, // Third-age Druidic Robe
			{ 6916, 1700000 }, // Infinity Top
			{ 6918, 2300000 }, // Infinity Hat
			{ 6920, 935200 }, // Infinity Boots
			{ 6922, 1500000 }, // Infinity Gloves
			{ 6924, 2700000 }, // Infinity Bottoms
			{ 11874, 9200000 }, // Infinity Robes Set
			{ 11838, 183500 }, // Rune Armour Set (lg)
			{ 1079, 48400 }, // Rune Platelegs
			{ 1127, 67400 }, // Rune Platebody
			{ 1163, 21000 }, // Rune Full Helm
			{ 1201, 46700 }, // Rune Kiteshield
			{ 11840, 178300 }, // Rune Armour Set (sk)
			{ 1093, 43200 }, // Rune Plateskirt
			{ 1127, 67400 }, // Rune Platebody
			{ 1163, 21000 }, // Rune Full Helm
			{ 1201, 46700 }, // Rune Kiteshield
			{ 14484, 31600000 }, // Dragon Claws
			{ 13450, 77600000 }, // Armadyl Godsword
			{ 13451, 20100000 }, // Bandos Godsword
			{ 13452, 54700000 }, // Saradomin Godsword
			{ 13461, 8500000 }, // Saradomin Sword
			{ 13453, 21900000 }, // Zamorak Godsword
			{ 13454, 5300000 }, // Zamorakian Spear
			{ 13455, 2900000 }, // Armadyl Helmet
			{ 13456, 15500000 }, // Armadyl Chestplate
			{ 13457, 15400000 }, // Armadyl Plateskirt
			{ 13458, 22300000 }, // Bandos Chestplate
			{ 13459, 25000000 }, // Bandos Tassets
			{ 13460, 765300 }, // Bandos Boots
			{ 13462, 464400 }, // Dragon Boots
			{ 13531, 446300000 }, // Red Partyhat
			{ 13532, 429400000 }, // Yellow Partyhat
			{ 13533, 570000000 }, // Blue Partyhat
			{ 13534, 431800000 }, // Green Partyhat
			{ 13535, 428100000 }, // Purple Partyhat
			{ 13536, 477300000 }, // White Partyhat
			{ 13537, 94000000 }, // Santa Hat
			{ 13538, 88800000 }, // Green H'ween Mask
			{ 13539, 104800000 }, // Blue H'ween Mask
			{ 13540, 135600000 }, // Red H'ween Mask
			{ 13529, 61600 }, // Seercull
			{ 13736, 3900000 }, // Blessed Spirit Shield
			{ 13738, 63600000 }, // Arcane Spirit Shield
			{ 13740, 533900000 }, // Divine Spirit Shield
			{ 13742, 529900000 }, // Elysian Spirit Shield
			{ 13744, 62600000 }, // Spectral Spirit Shield
			{ 4151, 3400000 }, // Abyssal Whip
			{ 4152, 3400000 }, // Abyssal whip
			{ 4153, 237700 }, // Granite Maul
			{ 6739, 1700000 }, // Dragon Hatchet
			{ 11335, 33300000 }, // Dragon Full Helm
			{ 1149, 60300 }, // Dragon Med Helm
			{ 14479, 11200000 }, // Dragon Platebody
			{ 2513, 6300000 }, // Dragon Chainbody
			{ 4087, 668600 }, // Dragon Platelegs
			{ 4585, 197600 }, // Dragon Plateskirt
			{ 13405, 985400 } // Dark Bow
	};

	public int getItemPrice(int itemId) {
		for (int[] data : ITEM_PRICES) {
			if (itemId == data[0] || itemId + 1 == data[0]) {
				return data[1];
			}
		}
		return 1;
	}

	public final int[] RANGED_WEAPONS = { 861, 9185 };

	public boolean rangedWeapon() {
		for (int i = 0; i < RANGED_WEAPONS.length; i++) {
			if (getEquipment().contains(RANGED_WEAPONS[i])) {
				return true;
			}
		}
		return false;
	}

	public void setHit(int hit, int delay, Entity opp) {
		for (int i = 0; i < playerHits.length; i++) {
			if (playerHits[i] != -1) {
				playerHits[i] = hit;
				hitsDelay[i] = delay;
				dealtDamage[i] = opp.getIndex();
				break;
			}
		}
	}

	public int specTimer = 0;

	public void tick() {
		this.applyPosion();
		if (this.yellDelay > 0) {
			yellDelay--;
		}
		if (this.potionTimer > 0) {
			potionTimer--;
		}
		if (this.foodTimer > 0) {
			foodTimer--;
		}
		saveTimer++;
		if (saveTimer > 300) {
			Serializer.SaveAccount(this);
			saveTimer = 0;
		}
		if (getSkills().tbTimer >= 0) {
			getSkills().tbTimer--;
			if (getSkills().tbTimer == 0) {
				getSkills().tbTimer = -1;
				getFrames().sendChatMessage(0,
						"The teleblock spell effect has worn off.");
			}
		}
		if (pickupDelay > 0) {
			pickupDelay--;
		}
		if (this.getCombatDefinitions().specpercentage < 100) {
			specTimer++;
			if (specTimer == 120) {
				this.getCombatDefinitions().specpercentage += 25;
				this.getCombatDefinitions().refreshSpecial();
				specTimer = 0;
			}
		}
	}

	public void tickHits() {
		for (int i = 0; i < playerHits.length; i++) {
			if (playerHits[i] != -1) {
				if (hitsDelay[i] > 0) {
					hitsDelay[i]--;
				}
				if (hitsDelay[i] == 0) {
					applyDamagePlayer(playerHits[i]);
					resetHit(i);
				}
			}
		}
	}

	public HitType getDamageType(int hit) {
		if (hit == 0) {
			return HitType.NO_DAMAGE;
		}
		if (hit < 100) {
			return HitType.NORMAL_DAMAGE;
		}
		return HitType.NORMAL_BIG_DAMAGE;
	}

	public void applyDamagePlayer(int damage) {
		if (!this.getMask().isHitUpdate()) {
			this.hits.setHit1(new Hit(damage, getDamageType(damage)));
			this.getMask().setHitUpdate(true);
			this.getSkills().hit(damage);
		} else if (!this.getMask().isHit2Update()) {
			this.hits.setHit2(new Hit(damage, getDamageType(damage)));
			this.getMask().setHit2Update(true);
			this.getSkills().hit(damage);
		}
	}

	public void applyDamagePlayerPosion(int damage) {
		if (!this.getMask().isHitUpdate()) {
			this.hits.setHit1(new Hit(damage, HitType.POISON_DAMAGE));
			this.getMask().setHitUpdate(true);
			this.getSkills().hit(damage);
		} else if (!this.getMask().isHit2Update()) {
			this.hits.setHit2(new Hit(damage, HitType.POISON_DAMAGE));
			this.getMask().setHit2Update(true);
			this.getSkills().hit(damage);
		}
	}

	public void resetHit(int slot) {
		playerHits[slot] = -1;
		hitsDelay[slot] = -1;
		dealtDamage[slot] = -1;
	}

	// Main Information Start
	private int combatDelay;
	private boolean isAttacking;
	private Entity attackingEntity;
	private String Username;
	private String DisplayName;
	private String Password;
	private Calendar BirthDate;
	private Calendar RegistDate;
	private short Country;
	private String Email;
	private byte Settings;
	private boolean isBanned;
	private Date Membership;
	private List<String> friends;
	private transient List<String> ignores;
	private List<String> Messages;
	private int LastIp;
	private transient byte rights;
	// Saving classes here
	private Appearence appearence;
	private Inventory inventory;
	private Equipment equipment;
	private Skills skills;
	private Banking bank;
	private CombatDefinitions combatdefinitions;
	private Prayer prayer;
	private MusicManager musicmanager;
	private transient ConnectionHandler connection;
	private transient Frames frames;
	private transient Mask mask;
	private transient Gpi gpi;
	private transient Gni gni;
	private transient Queue<Hit> queuedHits;
	private transient Hits hits;
	private transient InterfaceManager intermanager;
	private transient HintIconManager hinticonmanager;
	private transient MinigameManager Minigamemanager;
	private transient Dialogue dialogue;
	// login stuff
	private transient boolean isOnline;
	private transient boolean inClient;

	public Player(String Username, String Password, Calendar Birth,
			Calendar ThisDate, short Country, String Email, byte Settings) {
		this.setUsername(Username);
		this.setDisplayName(Username);
		this.setPassword(Password);//lolol wutwut ho
		this.setBirthDate(BirthDate);
		this.setRegistDate(ThisDate);
		this.setCountry(Country);
		this.setEmail(Email);
		this.setSettings(Settings);
		this.setKills(0);
		this.setDeaths(0);
		this.setTag("");
		this.setTokenRing(false);
		this.setCurrentTab(10);
		this.setMuted(false);
		this.setBanned(false);
		this.setMembership(new Date());
		this.setFriends(new ArrayList<String>(200));
		// this.setIgnores(new ArrayList<String>(100));
		this.setMessages(new ArrayList<String>());
		this.setLocation(RSTile.createRSTile(3087, 3496, (byte) 0));
		this.setAppearence(new Appearence());
		this.setInventory(new Inventory());
		this.setEquipment(new Equipment());
		this.setSkills(new Skills());
		this.setCombatDefinitions(new CombatDefinitions());
		this.setPrayer(new Prayer());
		this.setBank(new Banking());
		this.setMusicmanager(new MusicManager());
	}

	@SuppressWarnings("deprecation")
	public void LoadPlayer(ConnectionHandler connection) {
		try {
			this.setConnection(connection);
			this.setFrames(new Frames(this));
			this.setMask(new Mask(this));
			this.setGpi(new Gpi(this));
			this.setGni(new Gni(this));
			this.setQueuedHits(new LinkedList<Hit>());
			this.setHits(new Hits());
			this.setIntermanager(new InterfaceManager(this));
			this.setHinticonmanager(new HintIconManager(this));
			this.setMinigamemanager(new MinigameManager(this));
			this.setDialogue(new Dialogue(this));
			if (this.appearence == null)
				this.appearence = new Appearence();
			if (this.inventory == null)
				this.inventory = new Inventory();
			this.getInventory().setPlayer(this);
			if (this.equipment == null)
				this.equipment = new Equipment();
			this.getEquipment().setPlayer(this);
			if (this.skills == null)
				this.skills = new Skills();
			this.getSkills().setPlayer(this);
			if (this.combatdefinitions == null)
				this.combatdefinitions = new CombatDefinitions();
			this.getCombatDefinitions().setPlayer(this);
			if (this.prayer == null)
				this.prayer = new Prayer();
			this.getPrayer().setPlayer(this);
			if (this.musicmanager == null)
				this.musicmanager = new MusicManager();
			this.getMusicmanager().setPlayer(this);
			if (this.bank == null)
				this.bank = new Banking();
			this.getBank().setPlayer(this);
			this.setIgnores(new ArrayList<String>(100)); // Resets list for
															// now...
			this.EntityLoad();
			this.getFrames().loginResponce();
			this.getFrames().sendLoginInterfaces();
			this.getFrames().sendLoginConfigurations();
			this.getFrames().sendOtherLoginPackets();
			Calendar cal = Calendar.getInstance();
			int hours = cal.get(Calendar.HOUR_OF_DAY);
			int mins = cal.get(Calendar.MINUTE);
			int doy = cal.get(Calendar.DAY_OF_YEAR);
			if (this.timedMuteSet) {
				if (doy - this.timedMuteDay >= this.timedMuteTime) {
					if (hours >= this.timedMuteHr) {
						if (mins >= this.timedMuteMin) {
							this.timedMuteSet = false;
						}
					}
				}
			}
			if (timedMuteSet) {
				int daysLeft = (timedMuteDay + this.timedMuteTime) - doy;
				getFrames().sendChatMessage(
						0,
						"You have less than " + daysLeft
								+ " day(s) before your mute is lifted.");
			}
			this.LoadFriend_Ignore_Lists();
			this.reset();
			this.setOnline(true);
			for (Player p2 : World.getPlayers()) {
				if (p2 == null || p2 == this)
					continue;
				p2.getGpi().addPlayer(this);
			}
			this.setShophandler(new ShopHandler(this));
			this.getCombatDefinitions().startHealing();
			this.getSkills().startBoostingSkill();
			this.getCombatDefinitions().startGettingSpecialUp();
			if (this.isDead())
				this.getSkills().sendDead();
			currentlySummoned = null;
			Serializer.saveBackup(this);
		} catch (Exception e) {

		}
	}

	private void reset() {
		if (this.getConnection().getChannel() == null)
			return;
		if (this.LastIp == 0) {
			this.getFrames().sendChatMessage(0, "Enjoy your starter!");
			this.getInventory().addItem(995, wasReset ? 1000 : 50000000);
			int[] starter = { 995 };
			for (int i : starter) {
				this.getInventory().addItem(i, 1);
			}
		}
		String ip = "" + this.getConnection().getChannel().getLocalAddress();
		ip = ip.replaceAll("/", "");
		ip = ip.replaceAll(" ", "");
		ip = ip.substring(0, ip.indexOf(":"));
		this.setLastIp(Misc.IPAddressToNumber(ip));
		if (World.getIps().containsKey(this.LastIp))
			World.getIps().remove(this.LastIp);
		World.getIps().put(this.LastIp, System.currentTimeMillis());
	}

	private void LoadFriend_Ignore_Lists() {
		this.getFrames().sendUnlockIgnoreList();
		this.getFrames().sendUnlockFriendList();
		LoadIgnoreList();
		LoadFriendList();
	}

	private void LoadFriendList() {
		for (String Friend : getFriends()) {
			short WorldId = (short) (World.isOnline(Misc
					.formatPlayerNameForProtocol(Friend)) ? 1 : 0);// getWorld("Player");
			boolean isOnline = WorldId != 0;
			this.getFrames().sendFriend(Friend, Friend, WorldId, isOnline,
					false);
		}
	}

	private void LoadIgnoreList() {
		for (String Ignore : getIgnores()) {
			this.getFrames().sendIgnore(Ignore, Ignore);
		}
	}

	public void UpdateFriendStatus(String Friend, short worldId,
			boolean isOnline) {
		// this.getFrames().sendFriend(Friend, Friend, worldId, isOnline, true);
	}

	public void AddFriend(String Friend) {
		if ((getMembershipCredit() == 0 && getFriends().size() >= 100)
				|| getFriends().size() >= 200
				|| Friend == null
				|| Friend.equals("")
				|| getFriends().contains(Friend)
				|| getIgnores().contains(Friend)
				|| Friend.equals(Misc.formatPlayerNameForDisplay(this
						.getUsername())))
			return;
		getFriends().add(Friend);
		short WorldId = (short) (World.isOnline(Misc
				.formatPlayerNameForProtocol(Friend)) ? 1 : 0);// getWorld("Player");
		boolean isOnline = WorldId != 0;
		this.getFrames().sendFriend(Friend, Friend, WorldId, false, false);
		if (isOnline)
			UpdateFriendStatus(Friend, WorldId, isOnline);
	}

	public void AddIgnore(String Ignore) {
		if (getIgnores().size() >= 100
				|| Ignore == null
				|| getFriends().contains(Ignore)
				|| getIgnores().contains(Ignore)
				|| getIgnores().equals(
						Misc.formatPlayerNameForDisplay(this.getUsername())))
			return;
		getIgnores().add(Ignore);
		this.getFrames().sendIgnore(Ignore, Ignore);
	}

	public void RemoveIgnore(String Ignore) {
		if (Ignore == null || !getIgnores().contains(Ignore))
			return;
		getIgnores().remove(Ignore);
	}

	public void RemoveFriend(String Friend) {
		if (Friend == null || !getFriends().contains(Friend))
			return;
		getFriends().remove(Friend);
	}

	@SuppressWarnings("deprecation")
	public void MakeMember(int numberofmonths) {
		if (getMembership().before(new Date()))
			setMembership(new Date());
		getMembership().setMonth(getMembership().getMonth() + numberofmonths);
	}

	public int getMembershipCredit() {
		Date today = new Date();
		if (getMembership().before(today))
			return 0;
		long MembershipTime = getMembership().getTime();
		long TodayTime = today.getTime();
		int DayOfFinish = (int) (MembershipTime / 1000 / 60 / 60 / 24);
		int DayOfToday = (int) (TodayTime / 1000 / 60 / 60 / 24);
		return DayOfFinish - DayOfToday;
	}

	public void setFrames(Frames frames) {
		this.frames = frames;
	}

	public Frames getFrames() {
		if (frames == null)
			frames = new Frames(this);
		return frames;
	}

	public void setConnection(ConnectionHandler connection) {
		this.connection = connection;
	}

	public ConnectionHandler getConnection() {
		return connection;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getUsername() {
		if (Username == null)
			Username = "";
		return Username;
	}

	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}

	public String getDisplayName() {
		if (DisplayName == null)
			DisplayName = "";
		return DisplayName;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getPassword() {
		if (Password == null)
			Password = "";
		return Password;
	}

	public void setBirthDate(Calendar birthDate) {
		BirthDate = birthDate;
	}

	public Calendar getBirthDate() {
		if (BirthDate == null)
			BirthDate = new GregorianCalendar();
		return BirthDate;
	}

	public void setCountry(short country) {
		Country = country;
	}

	public short getCountry() {
		return Country;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getEmail() {
		if (Email == null)
			Email = "";
		return Email;
	}

	public void setSettings(byte settings) {
		Settings = settings;
	}

	public byte getSettings() {
		return Settings;
	}

	public void setRegistDate(Calendar registDate) {
		RegistDate = registDate;
	}

	public Calendar getRegistDate() {
		if (RegistDate == null)
			RegistDate = new GregorianCalendar();
		return RegistDate;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setMembership(Date membership) {
		Membership = membership;
	}

	public Date getMembership() {
		if (Membership == null)
			Membership = new Date();
		return Membership;
	}

	public void setFriends(List<String> friends) {
		this.friends = friends;
	}

	public List<String> getFriends() {
		if (friends == null)
			friends = new ArrayList<String>(200);
		return friends;
	}

	public void setIgnores(List<String> ignores) {
		this.ignores = ignores;
	}

	public List<String> getIgnores() {
		if (ignores == null)
			ignores = new ArrayList<String>(100);
		return ignores;
	}

	public void setMuted(boolean isMuted) {
		this.playerMuted = isMuted;
	}

	public void setCurrentTab(int tab) {
		this.currentTab = tab;
	}

	public int getCurrentTab() {
		return currentTab;
	}

	public void setTokenRing(Boolean hasRing) {
		this.tokenRing = hasRing;
	}

	public boolean hasTokenRing() {
		return tokenRing;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	public boolean isMuted() {
		return playerMuted;
	}

	public void setKills(int kills) {
		safeKillCount = kills;
		dangerousKillCount = kills;
	}

	public void setDeaths(int deaths) {
		safeDeathCount = deaths;
		dangerousDeathCount = deaths;
	}

	public int getSafeKills() {
		return safeKillCount;
	}

	public int getSafeDeaths() {
		return safeDeathCount;
	}

	public int getDangerousKills() {
		return dangerousKillCount;
	}

	public int getDangerousDeaths() {
		return dangerousDeathCount;
	}

	public void setBanned(boolean isBanned) {
		this.isBanned = isBanned;
	}

	public boolean isBanned() {
		return isBanned;
	}

	public void setMessages(List<String> messages) {
		Messages = messages;
	}

	public List<String> getMessages() {
		if (Messages == null)
			Messages = new ArrayList<String>();
		return Messages;
	}

	public void setLastIp(int lastIp) {
		LastIp = lastIp;
	}

	public int getLastIp() {
		return LastIp;
	}

	public void setRights(byte rights) {
		this.rights = rights;
	}

	public byte getRights() {
		return rights;
	}

	@Override
	public void animate(int id) {
		this.getMask().setLastAnimation(new Animation((short) id, (short) 0));
		this.getMask().setAnimationUpdate(true);
	}

	@Override
	public void animate(int id, int delay) {
		this.getMask().setLastAnimation(
				new Animation((short) id, (short) delay));
		this.getMask().setAnimationUpdate(true);
	}

	public void graphics(int... args) {
		switch (args.length) {
		case 1:
			mask.setLastGraphics(Graphics.create(args[0]));
			break;
		case 2:
			mask.setLastGraphics(Graphics.create(args[0], args[1]));
			break;
		case 3:
			mask.setLastGraphics(Graphics.create(args[0], args[1], args[2]));
			break;
		default:
			throw new IllegalArgumentException(
					"Graphic arguments can't be greater then 3");
		}
	}

	@Override
	public void heal(int amount) {
		// TODO Auto-generated method stub
	}

	public void heal(int healdelay, int bardelay, int healspeed) {
		getMask().setLastHeal(
				new Heal((short) healdelay, (byte) bardelay, (byte) healspeed));
		getMask().setHealUpdate(true);
	}

	public void processQueuedHits() {
		if (!this.getMask().isHitUpdate()) {
			if (queuedHits.size() > 0) {
				Hit h = queuedHits.poll();
				this.hit(h.getDamage(), h.getType());
			}
		}
		if (!this.getMask().isHit2Update()) {
			if (queuedHits.size() > 0) {
				Hit h = queuedHits.poll();
				this.hit(h.getDamage(), h.getType());
			}
		}
	}

	public void hit(int damage, Hits.HitType type) {
		if (System.currentTimeMillis() < this.getCombatDefinitions()
				.getLastEmote() - 600) {
			queuedHits.add(new Hit(damage, type));
		} else if (!this.getMask().isHitUpdate()) {
			this.hits.setHit1(new Hit(damage, type));
			this.getMask().setHitUpdate(true);
			this.getSkills().hit(damage);
		} else if (!this.getMask().isHit2Update()) {
			this.hits.setHit2(new Hit(damage, type));
			this.getMask().setHit2Update(true);
			this.getSkills().hit(damage);
		} else {
			if (this.skills.getHitPoints() <= 0) {
				return;
			}
			queuedHits.add(new Hit(damage, type));
		}
	}

	@Override
	public void hit(int damage) {
		if (damage > this.skills.getHitPoints())
			damage = this.skills.getHitPoints();
		if (damage == 0) {
			hit(damage, Hits.HitType.NO_DAMAGE);
		} else if (damage >= 100) {
			hit(damage, Hits.HitType.NORMAL_BIG_DAMAGE);
		} else {
			hit(damage, Hits.HitType.NORMAL_DAMAGE);
		}
	}

	public void hit(int damage, Player opp) {
		/*
		 * if(getEquipment().get(5).getDefinition().getId() == 13740) { int
		 * prayerLost = (int) Math.ceil(damage * 0.3 * .05);
		 * if(getSkills().level[5] >= prayerLost) { getSkills().level[5] -=
		 * prayerLost; damage *= 0.7; } else getSkills().level[5] = 0; }
		 */
		/*
		 * if(getEquipment().get(5).getDefinition().getId() == 13742) {
		 * if(Misc.random(9) <= 6) { damage *= 0.75; } }
		 */
		this.getSkills().killerName = opp.getUsername();
		if (damage > this.skills.getHitPoints())
			damage = this.skills.getHitPoints();
		if (damage == 0) {
			hit(damage, Hits.HitType.NO_DAMAGE);
		} else if (damage >= 100) {
			hit(damage, Hits.HitType.NORMAL_BIG_DAMAGE);
		} else {
			hit(damage, Hits.HitType.NORMAL_DAMAGE);
		}
	}

	public void hitType(int damage, HitType hitType) {
		if (damage > this.skills.getHitPoints())
			damage = this.skills.getHitPoints();
		hit(damage, hitType);
	}

	@Override
	public void resetTurnTo() {
		this.mask.setTurnToIndex(-1);
		this.mask.setTurnToReset(true);
		this.mask.setTurnToUpdate(true);
	}

	@Override
	public void turnTemporarilyTo(Entity entity) {
		// TODO Auto-generated method stub
		this.mask.setTurnToIndex(entity.getClientIndex());
		this.mask.setTurnToReset(true);
		this.mask.setTurnToUpdate(true);
	}

	public void turnTemporarilyTo(RSTile location) {
		this.mask.setTurnToLocation(location);
		this.mask.setTurnToUpdate1(true);
	}

	@Override
	public void turnTo(Entity entity) {
		this.mask.setTurnToIndex(entity.getClientIndex());
		this.mask.setTurnToReset(false);
		this.mask.setTurnToUpdate(true);
	}

	public void setMask(Mask mask) {
		this.mask = mask;
	}

	public Mask getMask() {
		return mask;
	}

	public void setAppearence(Appearence appearence) {
		this.appearence = appearence;
	}

	public Appearence getAppearence() {
		return appearence;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setSkills(Skills skills) {
		this.skills = skills;
	}

	public Skills getSkills() {
		return skills;
	}

	public void setIntermanager(InterfaceManager intermanager) {
		this.intermanager = intermanager;
	}

	public InterfaceManager getIntermanager() {
		return intermanager;
	}

	public void setInClient(boolean inClient) {
		this.inClient = inClient;
	}

	public boolean isInClient() {
		return inClient;
	}

	public void setCombatDefinitions(CombatDefinitions combat) {
		this.combatdefinitions = combat;
	}

	public CombatDefinitions getCombatDefinitions() {
		return combatdefinitions;
	}

	public void setDialogue(Dialogue dialogue) {
		this.dialogue = dialogue;
	}

	public Dialogue getDialogue() {
		return dialogue;
	}

	public void setPrayer(Prayer prayer) {
		this.prayer = prayer;
	}

	public Prayer getPrayer() {
		return prayer;
	}

	public TradeSession getTradeSession() {
		return this.currentTradeSession;
	}

	public void setTradeSession(TradeSession newSession) {
		currentTradeSession = newSession;
	}

	public void setTradePartner(Player tradePartner) {
		this.tradePartner = tradePartner;
	}

	public Player getTradePartner() {
		return tradePartner;
	}

	public void setQueuedHits(Queue<Hit> queuedHits) {
		this.queuedHits = queuedHits;
	}

	public Queue<Hit> getQueuedHits() {
		return queuedHits;
	}

	public void setHits(Hits hits) {
		this.hits = hits;
	}

	public Hits getHits() {
		return hits;
	}

	public void setGpi(Gpi gpi) {
		this.gpi = gpi;
	}

	public Gpi getGpi() {
		return gpi;
	}

	public void setMusicmanager(MusicManager musicmanager) {
		this.musicmanager = musicmanager;
	}

	public MusicManager getMusicmanager() {
		return musicmanager;
	}

	public void setBank(Banking bank) {
		this.bank = bank;
	}

	public Banking getBank() {
		return bank;
	}

	public void setHinticonmanager(HintIconManager hinticonmanager) {
		this.hinticonmanager = hinticonmanager;
	}

	public HintIconManager getHinticonmanager() {
		return hinticonmanager;
	}

	public void setMinigamemanager(MinigameManager minigamemanager) {
		Minigamemanager = minigamemanager;
	}

	public MinigameManager getMinigamemanager() {
		return Minigamemanager;
	}

	public void setGni(Gni gni) {
		this.gni = gni;
	}

	public Gni getGni() {
		return gni;
	}

	public void setCombatDelay(int combatDelay) {
		this.combatDelay = combatDelay;
	}

	public int getCombatDelay() {
		return combatDelay;
	}

	public void setAttacking(boolean isAttacking) {
		this.isAttacking = isAttacking;
	}

	public boolean isAttacking() {
		return isAttacking;
	}

	public void setAttackingEntity(Entity attackingEntity) {
		this.attackingEntity = attackingEntity;
	}

	public Entity getAttackingEntity() {
		return attackingEntity;
	}

	/**
	 * @param shophandler
	 *            the shophandler to set
	 */
	public void setShophandler(ShopHandler shophandler) {
		this.shophandler = shophandler;
	}

	/**
	 * @return the shophandler
	 */
	public ShopHandler getShophandler() {
		return shophandler;
	}

	/*
	 * @Override public void addToRegion(Region region) {
	 * region.addPlayer(this); }
	 * 
	 * @Override public void removeFromRegion(Region region) {
	 * region.removePlayer(this); }
	 */
	// public void RegIp(String string) {
	// playerIp = string;
	// }
	public String playerIp = "";

	@Override
	public void graphics(int id) {
		this.getMask().setLastGraphics(new Graphics((short) id, (short) 0));
		this.getMask().setGraphicUpdate(true);
	}

	public void graphics2(int id) {
		this.getMask().setLastGraphics2(new Graphics((short) id, (short) 0));
		this.getMask().setGraphic2Update(true);
	}

	public void graphics2(int id, int delay) {
		this.getMask()
				.setLastGraphics2(new Graphics((short) id, (short) delay));
		this.getMask().setGraphic2Update(true);
	}

	@Override
	public void graphics(int id, int delay) {
		this.getMask().setLastGraphics(new Graphics((short) id, (short) delay));
		this.getMask().setGraphicUpdate(true);
	}

}
