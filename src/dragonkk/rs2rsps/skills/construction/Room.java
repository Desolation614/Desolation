package dragonkk.rs2rsps.skills.construction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6763659361853802300L;
	
	private byte[] room;
	private byte[] place;
	private List<ConstructionObject> objects;
	
	public Room(byte[] room, byte[] place) {
		this.setRoom(room);
		this.setPlace(place);
		this.setObjects(new ArrayList<ConstructionObject>());
	}

	public boolean objectExists(int clickX, int clickY, int objectId) {
		for(ConstructionObject object : objects) {
			if(object.getClickX() == (byte)clickX || object.getClickY() == (byte)clickY)
				return true;
		}
		return false;
	}
	
	public void setRoom(byte[] room) {
		this.room = room;
	}

	public byte[] getRoom() {
		return room;
	}

	public void setPlace(byte[] place) {
		this.place = place;
	}

	public byte[] getPlace() {
		return place;
	}

	public void setObjects(List<ConstructionObject> objects) {
		this.objects = objects;
	}

	public List<ConstructionObject> getObjects() {
		return objects;
	}
}
