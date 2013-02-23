package dragonkk.rs2rsps.skills.prayer;

import java.io.Serializable;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.events.Task;
import dragonkk.rs2rsps.model.player.Player;

public class Prayer implements Serializable {

	private static final long serialVersionUID = -3850249845394162505L;

	private transient Player player;
	private transient boolean[][] onPrayers;
	private transient boolean usingQuickPrayer;
	private boolean[][] quickPrayers = { new boolean[29], new boolean[20] };
	private transient boolean[] boostingPray;
	private boolean ancientcurses;
	private transient boolean drainingprayer;

	public boolean usingPrayer(int book, int prayerId) {
		return this.onPrayers[book][prayerId];
	}
	
	public boolean usingBoost(int prayer) {
		return this.boostingPray[prayer];
	}
	
	public boolean setBoost(int prayer, boolean boost) {
		return this.boostingPray[prayer] = boost;
	}

	private final static int[][] prayerLvls = {
	// normal prayer book
			{ 1, 4, 7, 8, 9, 10, 13, 16, 19, 22, 25, 26, 27, 28, 31, 34, 35,
					37, 40, 43, 44, 45, 46, 49, 52, 60, 65, 70, 77 },
			// ancient prayer book
			{ 50, 50, 52, 54, 56, 59, 62, 65, 68, 71, 74, 76, 78, 80, 82,
					84, 86, 89, 92, 95 } };

	private final static int[][][] closePrayers = { { // normal prayer book
			{ 0, 5, 13 }, // Skin prayers 0
					{ 1, 6, 14 }, // Strength prayers 1
					{ 2, 7, 15 }, // Attack prayers 2
					{ 3, 11, 20 }, // Range prayers 3
					{ 4, 12, 21, 28 }, // Magic prayers 4
					{ 8, 9, 26 }, // Restore prayers 5
					{ 10 }, // Protect item prayers 6
					{ 17, 18, 19 }, // Protect prayers 7
					{ 16 }, // Other protect prayers 8
					{ 22, 23, 24 }, // Other special prayers 9
					{ 25, 27 } // Other prayers 10
			}, { // ancient prayer book
			{ 0 }, // Protect item prayers 0
					{ 1, 2, 3, 4 }, // sap prayers 1
					{ 5 }, // other prayers 2
					{ 7, 8, 9, 17, 18 }, // protect prayers 3
					{ 6 }, // other protect prayers 4
					{ 10, 11, 12, 13, 14, 15, 16 }, // leech prayers 5
					{ 19 }, // other prayers
			} };

	public void startDrain() {
		if (drainingprayer)
			return;
		this.drainingprayer = true;
		final int drainrate = this.getDrainRate();
		/*
		Server.getEntityExecutor().schedule(new Task() {

			@Override
			public void run() {
				if (player == null || !player.isOnline()) {
					stop();
					return;
				}
				if (!checkPrayer()) {
					closeAllPrayers();
					drainingprayer = false;
					stop();
					return;
				}
				int newrate = getDrainRate();
				if (newrate == -1) {
					drainingprayer = false;
					stop();
					return;
				}
				if (newrate != drainrate) {
					drainingprayer = false;
					stop();
					if (hasPrayersOn())
						player.getSkills().drainPray(1);
					if (!checkPrayer()) {
						closeAllPrayers();
						drainingprayer = false;
						return;
					}
					startDrain();
					return;
				}
				if (!checkPrayer()) {
					closeAllPrayers();
					drainingprayer = false;
					stop();
					return;
				}
				player.getSkills().drainPray(1);
				
			}
			
		}, 2400 , 2400);
		GameLogicTaskManager.schedule(new GameLogicTask() {
			@Override
			public void run() {
				
			}

		},10, 1);
		*/
		
	}

	public int getDrainRate() {
		int rate = 0;
		int index = 0;
		int numberofprays = 0;
		for (boolean prayer : this.onPrayers[this.getPrayerBook()]) {
			if (prayer) {
				rate += drainRate(index);
				numberofprays++;
			}
			index++;
		}
		int bonushere = 0;
		if (rate == 0)
			return -1;
		rate = (int) (rate * (0.0035 * bonushere + 1) * 1000) / numberofprays
				- ((numberofprays - 1) * 600);
		return rate;
	}

	private static int drainRate(int Prayer) {
		switch (Prayer) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 22:
			return 12;
		case 5:
		case 6:
		case 7:
		case 11:
		case 12:
		case 23:
			return 6;
		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
		case 18:
		case 19:
		case 20:
		case 21:
			return 3;
		case 8:
			return 26;
		case 9:
		case 10:
		case 26:
			return 18;
		case 24:
			return 2;
		case 25:
		case 27:
		case 28:
		default:
			return 1;
		}
	}

	public Prayer() {

	}

	public int getHeadIcon() {
		int value = -1;
		if (this.usingPrayer(0, 16))
			value += 8;
		if (this.usingPrayer(0, 17))
			value += 3;
		else if (this.usingPrayer(0, 18))
			value += 2;
		else if (this.usingPrayer(0, 19))
			value += 1;
		else if (this.usingPrayer(0, 22))
			value += 4;
		else if (this.usingPrayer(0, 23))
			value += 6;
		else if (this.usingPrayer(0, 24))
			value += 5;
		else if (this.usingPrayer(1, 6)) {
			value += 16;
			if (this.usingPrayer(1, 8))
				value += 2;
			else if (this.usingPrayer(1, 7))
				value += 3;
			else if (this.usingPrayer(1, 9))
				value += 1;
		}else if (this.usingPrayer(1, 7))
			value += 14;
		else if (this.usingPrayer(1, 8))
			value += 15;
		else if (this.usingPrayer(1, 9))
			value += 13;
		else if (this.usingPrayer(1, 17))
			value += 20;
		else if (this.usingPrayer(1, 18))
			value += 21;
		return value;
	}

	public void switchSettingQuickPrayer() {
		this.usingQuickPrayer = !this.usingQuickPrayer;
		this.player.getFrames().sendBConfig(181, this.usingQuickPrayer ? 1 : 0);
		this.player.getFrames().sendAMask(2, 271,
				this.usingQuickPrayer ? 7 : 6, 0, 27);
		if (this.usingQuickPrayer)
			this.player.getFrames().sendBConfig(168, 6);
	}

	public void switchQuickPrayers() {
		if (!checkPrayer())
			return;
		if (this.hasPrayersOn())
			this.closeAllPrayers();
		else {
			this.player.getFrames().sendBConfig(182, 1);
			int index = 0;
			for (boolean prayer : this.quickPrayers[this.getPrayerBook()]) {
				if (prayer)
					this.usePrayer(index);
				index++;
			}
			this.recalculatePrayer();
		}
	}

	public void switchPrayer(int prayerId) {
		if (!this.usingQuickPrayer)
			if (!this.checkPrayer())
				return;
		if(player.getCombat().prayDisable > 0) {
			if(ancientcurses) {
				if(prayerId == 7 || prayerId == 8  || prayerId == 9) {
					player.getFrames().sendChatMessage(0, "You cannot use this prayer at the momment.");
					return;
				}
			} else {
				if(prayerId >= 16 && prayerId <= 19) {
					player.getFrames().sendChatMessage(0, "You cannot use this prayer at the momment.");
					return;
				}
			}
		}
		this.usePrayer(prayerId);
		this.recalculatePrayer();
	}

	public void closeAllPrayers() {
		boolean[][] onPrayers = { new boolean[29], new boolean[20] };
		this.boostingPray = new boolean[9];
		this.onPrayers = onPrayers;
		this.player.getFrames().sendBConfig(182, 0);
		this.player.getFrames().sendConfig(ancientcurses ? 1582 : 1395, 0);
		this.player.getMask().setApperanceUpdate(true);
	}

	private boolean hasPrayersOn() {
		for (boolean prayer : this.onPrayers[this.getPrayerBook()])
			if (prayer == true)
				return true;
		return false;
	}
	
	public void removeAllProtectionPrayers() {
		int[] ancient = new int[] {7, 8, 9};
		int[] normal = new int[] {16, 17, 18, 19};
		if(this.isAncientCurses()) {
			for(int i : ancient) {
				this.onPrayers[1][i] = false;
			}
		} else {
			for(int i : normal) {
				this.onPrayers[0][i] = false;
			}
		}
	}
	
	public void turnPrayOff(int id) {
		int book = this.getPrayerBook();
		this.closePrayers(closePrayers[book][id]);
	}

	private boolean checkPrayer() {
		if (this.player.getSkills().getLevel(5) == 0) {
			this.player.getFrames().sendChatMessage((byte) 0,
					"Please recharge your prayer at the Lumbridge Church.");
			return false;
		}
		return true;
	}

	private boolean usePrayer(int prayerId) {
		if (prayerId < 0 || prayerId >= prayerLvls[this.getPrayerBook()].length)
			return false;
		if (this.player.getSkills().getLevelForXp(5) < prayerLvls[this
				.getPrayerBook()][prayerId]) {
			this.player.getFrames().sendChatMessage(
					(byte) 0,
					"You need " + prayerLvls[this.getPrayerBook()][prayerId]
							+ " Prayer to activate this.");
			return false;
		}
		if (!this.usingQuickPrayer) {
			if (this.onPrayers[this.getPrayerBook()][prayerId]) {
				this.onPrayers[this.getPrayerBook()][prayerId] = false;
				this.player.getMask().setApperanceUpdate(true);
				return true;
			}
		} else {
			if (this.quickPrayers[this.getPrayerBook()][prayerId]) {
				this.quickPrayers[this.getPrayerBook()][prayerId] = false;
				return true;
			}
		}
		if (this.getPrayerBook() == 0) {
			switch (prayerId) {
			case 0:
			case 5:
			case 13:
				this.closePrayers(closePrayers[this.getPrayerBook()][0],
						closePrayers[this.getPrayerBook()][10]);
				break;
			case 1:
			case 6:
			case 14:
				this.closePrayers(closePrayers[this.getPrayerBook()][1],
						closePrayers[this.getPrayerBook()][3],
						closePrayers[this.getPrayerBook()][4],
						closePrayers[this.getPrayerBook()][10]);
				break;
			case 2:
			case 7:
			case 15:
				this.closePrayers(closePrayers[this.getPrayerBook()][2],
						closePrayers[this.getPrayerBook()][3],
						closePrayers[this.getPrayerBook()][4],
						closePrayers[this.getPrayerBook()][10]);
				break;
			case 3:
			case 11:
			case 20:
				this.closePrayers(closePrayers[this.getPrayerBook()][1],
						closePrayers[this.getPrayerBook()][2],
						closePrayers[this.getPrayerBook()][3],
						closePrayers[this.getPrayerBook()][10]);
				break;
			case 4:
			case 12:
			case 21:
				this.closePrayers(closePrayers[this.getPrayerBook()][1],
						closePrayers[this.getPrayerBook()][2],
						closePrayers[this.getPrayerBook()][4],
						closePrayers[this.getPrayerBook()][10]);
				break;
			case 8:
			case 9:
			case 26:
				this.closePrayers(closePrayers[this.getPrayerBook()][5]);
				break;
			case 10:
				this.closePrayers(closePrayers[this.getPrayerBook()][6]);
				break;
			case 17:
			case 18:
			case 19:
				this.closePrayers(closePrayers[this.getPrayerBook()][7],
						closePrayers[this.getPrayerBook()][9]);
				this.player.getMask().setApperanceUpdate(true);
				break;
			case 16:
				this.closePrayers(closePrayers[this.getPrayerBook()][8],
						closePrayers[this.getPrayerBook()][9]);
				this.player.getMask().setApperanceUpdate(true);
				break;
			case 22:
			case 23:
			case 24:
				this.closePrayers(closePrayers[this.getPrayerBook()][7],
						closePrayers[this.getPrayerBook()][8],
						closePrayers[this.getPrayerBook()][9]);
				this.player.getMask().setApperanceUpdate(true);
				break;
			case 25:
			case 27:
				this.closePrayers(closePrayers[this.getPrayerBook()][0],
						closePrayers[this.getPrayerBook()][1],
						closePrayers[this.getPrayerBook()][2],
						closePrayers[this.getPrayerBook()][3],
						closePrayers[this.getPrayerBook()][4],
						closePrayers[this.getPrayerBook()][10]);
				break;
			case 28:
				this.closePrayers(closePrayers[this.getPrayerBook()][0],
						closePrayers[this.getPrayerBook()][1],
						closePrayers[this.getPrayerBook()][2],
						closePrayers[this.getPrayerBook()][4],
						closePrayers[this.getPrayerBook()][10]);
				break;
			default:
				return false;
			}
		} else {
			switch (prayerId) {
			case 0:
				player.animate(12567);
				player.graphics(2213);
				this.closePrayers(closePrayers[this.getPrayerBook()][0]);
				break;
			case 1:
				this.boostingPray[0] = false;
			case 2:
				this.boostingPray[1] = false;
			case 3:
				this.boostingPray[2] = false;
			case 4:
				this.closePrayers(closePrayers[this.getPrayerBook()][5],
						closePrayers[this.getPrayerBook()][6]);
				break;
			case 5:
				player.animate(12589);
				player.graphics(2266);
				this.closePrayers(closePrayers[this.getPrayerBook()][2]);
				break;
			case 7:
			case 8:
			case 9:
			case 17:
			case 18:
				this.closePrayers(closePrayers[this.getPrayerBook()][3]);
				this.player.getMask().setApperanceUpdate(true);
				break;
			case 6:
				this.closePrayers(closePrayers[this.getPrayerBook()][4]);
				this.player.getMask().setApperanceUpdate(true);
				break;
			case 10:
				this.boostingPray[3] = false;
			case 11:
				this.boostingPray[4] = false;
			case 12:
				this.boostingPray[5] = false;
			case 13:
				this.boostingPray[6] = false;
			case 14:
				this.boostingPray[7] = false;
			case 15:
			case 16:
				this.closePrayers(closePrayers[this.getPrayerBook()][1],
						closePrayers[this.getPrayerBook()][6]);
				break;
			case 19:
				this.boostingPray[8] = false;
				player.animate(12565);
				player.graphics(2226);
				this.closePrayers(closePrayers[this.getPrayerBook()][5],
						closePrayers[this.getPrayerBook()][6]);
				this.player.getMask().setApperanceUpdate(true);
			break;
			default:
				return false;
			}
		}
		if (!this.usingQuickPrayer) {
			this.onPrayers[this.getPrayerBook()][prayerId] = true;
			this.startDrain();
		} else
			this.quickPrayers[this.getPrayerBook()][prayerId] = true;
		return true;
	}

	private void closePrayers(int[]... prayers) {
		for (int[] prayer : prayers)
			for (int prayerId : prayer)
				if (usingQuickPrayer)
					this.quickPrayers[this.getPrayerBook()][prayerId] = false;
				else
					this.onPrayers[this.getPrayerBook()][prayerId] = false;
	}

	private final static int[] prayerSlotValues = { 1, 2, 4, 262144, 524288, 8,
			16, 32, 64, 128, 256, 1048576, 2097152, 512, 1024, 2048, 16777216,
			4096, 8192, 16384, 4194304, 8388608, 32768, 65536, 131072,
			33554432, 134217728, 67108864, 268435456 };

	public void recalculatePrayer() {
		int value = 0;
		int index = 0;
		for (boolean prayer : (!usingQuickPrayer ? this.onPrayers[this
				.getPrayerBook()] : this.quickPrayers[this.getPrayerBook()])) {
			if (prayer)
				value += ancientcurses ? Math.pow(2, index) : prayerSlotValues[index];
			index++;
		}
		this.player.getFrames().sendConfig(ancientcurses ? (usingQuickPrayer ? 1584 : 1582) : (usingQuickPrayer ? 1397 : 1395),
				value);
	}

	private int getPrayerBook() {
		return ancientcurses == false ? 0 : 1;
	}

	public void setPlayer(Player player) {
		this.player = player;
		boolean[][] onPrayers = { new boolean[29], new boolean[20] };
		boostingPray = new boolean[9];
		this.onPrayers = onPrayers;
	}

	public boolean isAncientCurses() {
		return this.ancientcurses;
	}

	public void switchPrayBook(boolean book) {
		this.ancientcurses = book;
		this.player.getFrames().sendConfig(1584, book ? 1 : 0);
		this.player.getFrames().sendAMask(0, 27, 271, 6, 0, 2);
		this.drainingprayer = false;
	}
}
