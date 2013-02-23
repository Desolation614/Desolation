package dragonkk.rs2rsps.skills.construction;

import java.io.Serializable;

public class ConstructionObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1396761527715763902L;
	
	private byte clickX;
	private byte clickY;
	private int objectId;
	private byte type;
	
	public ConstructionObject(int clickX, int ClickY, int objectId, int dir) {
		this.setClickX((byte) clickX);
		this.setClickY((byte) clickX);
		this.setObjectId(objectId);
		this.setType((byte) dir);
	}

	public void setClickX(byte clickX) {
		this.clickX = clickX;
	}

	public byte getClickX() {
		return clickX;
	}

	public void setClickY(byte clickY) {
		this.clickY = clickY;
	}

	public byte getClickY() {
		return clickY;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public int getObjectId() {
		return objectId;
	}

	public void setType(byte dir) {
		this.type = dir;
	}

	public int getType() {
		return type;
	}
	
}
