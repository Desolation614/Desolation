package dragonkk.rs2rsps.event.impl.game;

import dragonkk.rs2rsps.event.ThreadTask;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.util.Serializer;

public class PlayerSaveTask implements ThreadTask {

	/**
	 * The player instance
	 */
	private Player p;
	
	/**
	 * Constructor
	 * @param p
	 */
	public PlayerSaveTask(Player p) {
		this.p = p;
	}
	
	/**
	 * Executed when the task is invoked
	 */
	@Override
	public void execute() {
		Serializer.save(p);
	}

}