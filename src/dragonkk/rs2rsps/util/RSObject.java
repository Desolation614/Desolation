package dragonkk.rs2rsps.util;


public class RSObject {

	private int id;
	private byte type, rotation;
	private RSTile location;

	public RSObject(int id, int x, int y, int z, int type, int rotation) {
		this.id = id;
		location = RSTile.createRSTile(x, y, z);
		this.type = (byte) type;
		this.rotation = (byte) rotation;
	}
	
	public RSObject(int id, RSTile location, int type, int rotation) {
		this.id = id;
		this.location = location;
		this.type = (byte) type;
		this.rotation = (byte) rotation;
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

	public byte getType() {
		return type;
	}

	public byte getRotation() {
			return rotation;
	}

	public void setRotation(byte rotation) {
		this.rotation = rotation;
}

	
	public void setLocation(RSTile location) {
		this.location = location;
	}

	public RSTile getLocation() {
		return location;
	}
}
