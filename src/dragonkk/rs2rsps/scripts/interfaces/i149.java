package dragonkk.rs2rsps.scripts.interfaces;
import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.player.ConsumableManager;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.Scripts;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i149 extends interfaceScript {

	public void actionButton(final Player p, final int packetId, final int buttonId, final int buttonId2, final int buttonId3) {
		//System.out.println("packet: "+packetId);
		if(packetId == 24 ) {
			GameLogicTaskManager.schedule(new GameLogicTask() {

				@Override
				public void run() {
					Scripts.invokeItemScript((short) buttonId3).option1(p, buttonId3, 149, buttonId2);					
					p.getCombat().queuedSet = false;
				}
				
			}, 0, 0);
					}
		if(packetId == 79) {
			p.getCombat().queuedSet = false;
			ConsumableManager.handleConsumable(p, buttonId2, buttonId3);
		}
		else if(packetId == 52) 
			Scripts.invokeItemScript((short) buttonId3).drop(p, buttonId3, 149, buttonId2);
	}

}
