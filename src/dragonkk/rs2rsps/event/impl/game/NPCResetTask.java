package dragonkk.rs2rsps.event.impl.game;

import dragonkk.rs2rsps.event.ThreadTask;
import dragonkk.rs2rsps.model.npc.Npc;

public class NPCResetTask implements ThreadTask {


	/**
	 * Constructor
	 * @param n NPC instance to process
	 */
	public NPCResetTask(Npc n) {
		this.n = n;
	}
	
	/**
	 * NPc instance
	 */
	public Npc n;
	
	/**
	 * Executes the npc update task in the multi threaded complex
	 */
	@Override
	public void execute() {
		n.getMask().reset();
	}

}
