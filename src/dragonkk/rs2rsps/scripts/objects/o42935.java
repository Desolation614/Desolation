package dragonkk.rs2rsps.scripts.objects;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.Task;
import dragonkk.rs2rsps.model.Hits.HitType;
import dragonkk.rs2rsps.model.player.ChatMessage;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.objectScript;
import dragonkk.rs2rsps.util.Misc;
import dragonkk.rs2rsps.util.RSTile;

public class o42935 extends objectScript {

	@Override
	public void examine(Player p) {
		
	}

	@Override
	public void option1(final Player p, final int coordX, final int coordY, final int height) {
		if(p.getWalk().getWalkDir() != -1 || p.getWalk().getRunDir() != -1 )
			return;
		if(Math.round(p.getLocation().getDistance(RSTile.createRSTile(coordX, coordY, height))) > 1) 
			return;
		int firstX = coordX - (p.getLocation().getRegionX() - 6) * 8;
		int firstY = coordY - (p.getLocation().getRegionY() - 6) * 8;
		p.getWalk().reset();
		p.getWalk().addStepToWalkingQueue(firstX, firstY);
		if (p.getIntermanager().containsTab(16))
			p.getFrames().closeInterface(16);
		if (!p.getIntermanager().containsInterface(8, 137))
			p.getDialogue().finishDialogue();
		if (p.getCombat().hasTarget())
			p.getCombat().removeTarget();
		if (!p.getMask().isTurnToReset())
			p.getMask().setTurnToReset(true);
		double time = Misc.random(10)*1000;
		if(time < 3000)
			time = 3000;
		Server.getEntityExecutor().schedule(new Task() {
			@Override
			public void run() {
				if(p.getLocation().getX() == coordX && p.getLocation().getY() == coordY && p.getLocation().getZ() == height) {
					sendDeath(p);
				}
			}
		}, (long) time);
	}

	private void sendDeath(final Player p) {
		p.getCombatDefinitions().doEmote(-1, 2315, 600);
		Server.getEntityExecutor().schedule(new Task() {
			@Override
			public void run() {
				p.hit(p.getSkills().getHitPoints(), HitType.DUNGEON_DAMAGE);
			}
			
		}, 600);
	}
	@Override
	public void option2(Player p, int coordX, int coordY, int height) {
		
	}

}
