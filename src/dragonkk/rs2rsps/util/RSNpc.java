package dragonkk.rs2rsps.util;

public class RSNpc {

	private short id;
	private RSTile location;
	
	public RSNpc(int id, RSTile location) {
		this.setId(id);
		this.setLocation(location);
	}

	public void setId(int id) {
		this.id = (short) id;
	}

	public short getId() {
		return id;
	}

	public void setLocation(RSTile location) {
		this.location = location;
	}

	public RSTile getLocation() {
		return location;
	}
}
