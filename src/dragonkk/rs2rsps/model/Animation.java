package dragonkk.rs2rsps.model;

/**
 * Holds data for a single animation request.
 * @author Graham
 *
 */
public class Animation {
	
	private short id, delay;
	
	public Animation(short id, short delay) {
		this.id = id;
		this.delay = delay;
	}
	
	public short getId() {
		return id;
	}
	
	public short getDelay() {
		return delay;	
	}

}
