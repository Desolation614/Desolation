package dragonkk.rs2rsps.model.player;

import java.io.Serializable;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.Task;
import dragonkk.rs2rsps.model.Item;

public class CombatDefinitions implements Serializable {

	private static final long serialVersionUID = 3485431224108912565L;

	private transient Player player;
	private transient boolean specialOn;
	private transient boolean gettingSpecialUp;
	private transient boolean autocasting;
	public transient short[] bonus;
	private transient boolean healing;
	private transient long lastEmote;
	private transient long lastFood;
	private transient long lastPot;
	private byte attackStyle;
	public byte specpercentage;
	public void switchSpecial() {
		specialOn = !specialOn;
		player.getFrames().sendConfig(301, specialOn ? 1 : 0);
		player.getCombat().handleGraniteMaulSpecial();
	}
	public void setSpecialOff() {
		specialOn = false;
		player.getFrames().sendConfig(301, specialOn ? 1 : 0);
	}
	
	public void refreshSpecial() {
		if(specpercentage > 100) {
			specpercentage = 100;
		}
		player.getFrames().sendConfig(300, specpercentage*10);
	}
	
	public void drainSpec(int amount) {
		if(specpercentage - amount < 0)
			specpercentage = 0;
		else
			specpercentage -= amount;
		refreshSpecial();
		startGettingSpecialUp();
	}
	public void startGettingSpecialUp() {
		if (gettingSpecialUp)
			return;
		gettingSpecialUp = true;
		Server.getEntityExecutor().schedule(new Task() {
			@Override
			public void run() {
				if (!player.isOnline() || specpercentage == 100) {
					gettingSpecialUp = false;
					stop();
					return;
				}
				specpercentage += (byte) ((100-specpercentage) > 9 ? 10 : 100-specpercentage);
				refreshSpecial();
				if (specpercentage == 100) {
					gettingSpecialUp = false;
					stop();
					return;
				}
			}
		}, 30000, 30000);
	}
	
	public void doEmote(int animId, int gfxId, int milliSecondDelay) {
		if (System.currentTimeMillis() < lastEmote) {
			player.getFrames().sendChatMessage(0,
					"You're already doing an emote!");
			return;
		}
		if (player.getCombat().hasTarget()) {
			player.getFrames().sendChatMessage(0,
					"You can't make an emote while attacking!");
			return;
		}
		if (animId > -1)
			player.animate(animId);
		if (gfxId > -1)
			player.graphics(gfxId);
		lastEmote = System.currentTimeMillis() + milliSecondDelay;
	}

	public void startHealing() {
		if (healing)
			return;
		healing = true;
		Server.getEntityExecutor().schedule(new Task() {
			@Override
			public void run() {
				if (!player.isOnline()
						|| player.getSkills().getHitPoints() <= 0
						|| player.getSkills().getHitPoints() >= (player
								.getSkills().getXPForLevel(3) * 10)) {
					healing = false;
					stop();
					return;
				}
				player.getSkills().heal(1);
				if (player.getSkills().getHitPoints() >= (player.getSkills()
						.getXPForLevel(3) * 10)) {
					healing = false;
					stop();
					return;
				}
			}
		}, 6000, 6000);
	}

	public CombatDefinitions() {
	}

	public void refreshBonuses() {
		short[] bonuses = new short[15];
		for (int i = 0; i < 14; i++) {
			Item item = player.getEquipment().get(i);
			if (item == null)
				continue;
			for (int j = 0; j < 15; j++) {
				bonuses[j] += item.getDefinition().bonus[j];
			}
		}
		bonus = bonuses;
		// TODO
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setSpecialOn(boolean specialOn) {
		this.specialOn = specialOn;
	}

	public boolean isSpecialOn() {
		return specialOn;
	}

	public void setAttackStyle(byte attackStyle) {
		this.attackStyle = attackStyle;
	}

	public byte getAttackStyle() {
		return attackStyle;
	}

	public void setBonus(short[] bonus) {
		this.bonus = bonus;
	}

	public short[] getBonus() {
		return bonus;
	}

	public void setAutocasting(boolean autocasting) {
		this.autocasting = autocasting;
	}

	public boolean isAutocasting() {
		return autocasting;
	}

	public void setSpecpercentage(byte specpercentage) {
		this.specpercentage = specpercentage;
	}

	public byte getSpecpercentage() {
		return specpercentage;
	}

	public void setHealing(boolean healing) {
		this.healing = healing;
	}

	public boolean isHealing() {
		return healing;
	}

	public void setLastEmote(long lastEmote) {
		this.lastEmote = lastEmote;
	}

	public long getLastEmote() {
		return lastEmote;
	}
	public void setLastFood(long lastFood) {
		this.lastFood = lastFood;
	}
	public long getLastFood() {
		return lastFood;
	}
	public void setLastPot(long lastPot) {
		this.lastPot = lastPot;
	}
	public long getLastPot() {
		return lastPot;
	}
}
