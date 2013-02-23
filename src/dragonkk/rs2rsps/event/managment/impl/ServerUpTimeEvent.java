package dragonkk.rs2rsps.event.managment.impl;

import dragonkk.rs2rsps.event.managment.Event;
import dragonkk.rs2rsps.model.World;

public class ServerUpTimeEvent extends Event {

	public ServerUpTimeEvent(long delay) {
		super(delay);
	}

	@Override
	public void execute() {
		World.processUpTime();
	}

}
