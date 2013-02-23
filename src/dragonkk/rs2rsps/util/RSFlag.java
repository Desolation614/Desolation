package dragonkk.rs2rsps.util;


public class RSFlag {

	private int id, flag;
	private RSTile location;

	public RSFlag(int id, int x, int y, int flag) {
		this.id = id;
		this.flag = flag;
	}
	
	public RSFlag(int id, RSTile location, int flag) {
		this.id = id;
		this.location = location;
		this.flag = flag;
	}

	public int getId() {
		return id;
	}
		
	public int getX() {
		return location.getX();
	}
		
	public int getY() {
		return location.getY();
	}
		
	public int getZ() {
		return location.getZ();
	}

	public int getFlag() {
			return flag;
	}

	public RSTile getLocation() {
		return location;
	}
}
