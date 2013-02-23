package dragonkk.rs2rsps.event.tickable;

import java.util.*;

public class TickableManager {
	
	/**
	 * The list of tickables.
	 */
	private List<Tickable> tickables = new LinkedList<Tickable>();
	
	/**
	 * @return The tickables.
	 */
	public List<Tickable> getTickables() {
		return tickables;
	}
	
	/**
	 * Submits a new tickable to the <code>GameEngine</code>.
	 * @param tickable The tickable to submit.
	 */
	public void submit(final Tickable tickable) {
		tickables.add(tickable);
	}	
	
}
