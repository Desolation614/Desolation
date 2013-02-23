package dragonkk.rs2rsps.event.managment;

import java.util.concurrent.TimeUnit;

import dragonkk.rs2rsps.Server;

public class EventManager {

	/**
	 * Submits a new event to the <code>GameEngine</code>.
	 * 
	 * @param event
	 *            The event to submit.
	 */
	public void submit(final Event event) {
		submit(event, event.getDelay());
	}

	/**
	 * Schedules an event to run after the specified delay.
	 * 
	 * @param event
	 *            The event.
	 * @param delay
	 *            The delay.
	 */
	private void submit(final Event event, final long delay) {
		Server.getEngine().scheduleLogic(new Runnable() {
			@Override
			public void run() {
				long start = System.currentTimeMillis();
				if (event.isRunning()) {
					event.execute();
				} else {
					return;
				}
				long elapsed = System.currentTimeMillis() - start;
				long remaining = event.getDelay() - elapsed;
				if (remaining <= 0) {
					remaining = 0;
				}
				submit(event, remaining);
			}
		}, delay, TimeUnit.MILLISECONDS);
	}

}
