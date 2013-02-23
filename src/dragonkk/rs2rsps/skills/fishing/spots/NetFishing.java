package dragonkk.rs2rsps.skills.fishing.spots;

import java.util.Random;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.skills.fishing.FishingSpot;
import dragonkk.rs2rsps.util.Misc;

public class NetFishing implements FishingSpot {
	
	public final int[] LEVELS = {1, 10};
	public final int[] FISH = {317, 321};
	public final int[] EXP = {10, 40};
	public final int[] BONUS = {4, 7};
	public final int EQUIPMENT = 303;
	public final int EMOTE = 1;
	public final int EMOTE_TICK = 30;

	@Override
	public void execute(final Player p) {
		if(!p.getInventory().contains(EQUIPMENT)) {
			p.getFrames().sendChatMessage(0, "You don't have the correct equipment to fish here.");
			return;
		}
		if(p.getSkills().level[p.getSkills().FISHING] < LEVELS[0]) {
			p.getFrames().sendChatMessage(0, "Your fishing level isn't high enough to fish in this spot.");
			return;
		}
		if(p.getSkills().isFishing)
			return;
		p.getSkills().isFishing = true;
		p.getSkills().fishID = newFish(p);
		calcFish(p);
		GameLogicTaskManager.schedule(new GameLogicTask() {
			@Override
			public void run() {
				if(!p.getSkills().isFishing || p == null){ 
					this.stop();
					return;
				}
				p.getSkills().emoteTick++;
				if(p.getSkills().emoteTick == EMOTE_TICK) {
					p.animate(EMOTE);
					p.getSkills().emoteTick = 0;
				}
				if(p.getSkills().fishTimer > 0)
					p.getSkills().fishTimer--;
				if(p.getSkills().fishTimer == 0) {
					p.getInventory().addItem(FISH[p.getSkills().fishID], 1);
					p.getSkills().addXp(p.getSkills().FISHING, EXP[p.getSkills().fishID]);
					p.getFrames().sendChatMessage(0, "You catch a fish.");
					if(p.getInventory().getFreeSlots() < 1) {
						this.stop();
						p.getSkills().isFishing = false;
					} else {
						p.getSkills().fishID = newFish(p);
						calcFish(p);
					}
				}
			}
			
		}, 0, 0);
	}
	
	public double calculateFish(Player p) {
		double D = 0.865;
		double Chance = (p.getSkills().level[p.getSkills().FISHING]) * BONUS[p.getSkills().fishID];
		double level = LEVELS[p.getSkills().fishID] * D;
		return (Chance * level);
	}
	
	public int newFish(Player p) {
		Random r = new Random(LEVELS.length);
		int chosen = 0;
		chosen = r.nextInt();
		if(p.getSkills().level[p.getSkills().FISHING] < LEVELS[chosen]) 
			chosen = chosen - 1;
		return chosen;
	}
	
	public void calcFish(Player p) {
		double calculate = calculateFish(p);
		Random r = new Random();
		if(calculate > 1.0) {
			calculate = 1.0;
		}
		if(r.nextDouble() > calculate) {
			p.getSkills().fishTimer = ((int)Misc.random((int) r.nextDouble()));
		} else {
			p.getSkills().fishTimer = Misc.random((int)calculate);
		}
	}
}
