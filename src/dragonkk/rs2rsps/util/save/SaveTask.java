package dragonkk.rs2rsps.util.save;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.util.Serializer;

public class SaveTask {
	
	/**
	 * Constructor
	 */
	public SaveTask(Player player) {
		this.player = player;
	}
	
	/**
	 * Executes the save task
	 * @throws Exception
	 */
	public void executeTask() throws Exception {
		if(player == null){
			throw new Exception("Player instance was nulled.");
		}
		Serializer.SaveAccount1(player);
	}
	
	/**
	 * The player instance to save
	 */
	private Player player;

}