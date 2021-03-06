package dragonkk.rs2rsps.scripts.objects;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.Task;
import dragonkk.rs2rsps.model.Hits.HitType;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.objectScript;
import dragonkk.rs2rsps.util.RSTile;

public class o42936 extends objectScript {

	@Override
	public void examine(Player p) {
		
	}

	@Override
	public void option1(final Player p, final int coordX, final int coordY, final int height) {
		if(p.getWalk().getWalkDir() != -1 || p.getWalk().getRunDir() != -1)
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
		sendDeath(p);
	}

	private void sendDeath(final Player p) {
		Server.getEntityExecutor().schedule(new Task() {
			public void run() {
		p.getCombatDefinitions().doEmote(-1, 2652, 5000);
		p.heal(150, 0, 1);
		Server.getEntityExecutor().schedule(new Task() {
			@Override
			public void run() {
				p.hit(p.getSkills().getHitPoints(), HitType.DUNGEON_DAMAGE);
			}
			
		}, 5000);
			}
		}, 600);
	}
	@Override
	public void option2(Player p, int coordX, int coordY, int height) {
		
	}

}
