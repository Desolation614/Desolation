package dragonkk.rs2rsps.skills.construction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dragonkk.rs2rsps.model.player.Player;

public class Construction implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6401383632439859627L;

	//X, Y, Price, Level
    public static final int roomInfo[][] = {
        {1864, 5056, 0, 0}, //Unbuilded land
        {1856, 5112, 1000, 1}, //Parlour
        {1856, 5064, 1000, 1}, //Garden //1856, 5064
        {1872, 5112, 5000, 5}, //Kitchen
        {1890, 5112, 5000, 10}, //Dining room
        {1856, 5096, 10000, 15}, //Workshop
        {1904, 5112, 10000, 20}, //Bedroom
        {1880, 5104, 15000, 25}, //Skill hall
        {1896, 5088, 25000, 30}, //Games room
        {1880, 5088, 25000, 32}, //Combat room
        {1912, 5104, 25000, 35}, //Quest hall
        {1888, 5096, 50000, 40}, //Study
        {1904, 5064, 50000, 42}, //Costume room
        {1872, 5096, 50000, 45}, //Chapel
        {1864, 5088, 100000, 50}, //Portal chamber
        {1872, 5064, 75000, 55}, //Formal garden
        {1904, 5096, 150000, 60}, //Throne room
        {1904, 5080, 150000, 65}, //Oubliette
        {1888, 5080, 7500, 70}, //Dungeon - Corridor
        {1856, 5080, 7500, 70}, //Dungeon - Junction
        {1872, 5080, 7500, 70}, //Dungeon - Stairs
        {1912, 5088, 250000, 75} //Treasure room
    };
    
	//0 south
	//1 west
	//2 north
	//3 east
    public static final boolean AllowedRoomRott[][] = {
    	{false, false, false, false}, //Unbuilded land
    	{true, true, false, true}, //Parlour
    	{true, true, true, true}, //Garden
    	{true, true, false, false}, //Kitchen
    	{true, true, false, true}, //Dining room
    	{true, false, true, false}, //Workshop
    	{true, true, false, false}, //Dinning room
    	{true, true, true, true}, //Skill hall
    	{true, true, false, true}, //Games room
    	{true, true, false, false}, //Chapel
    	{true, false, false, false}, //Portal chamber
    	{true, true, true, true}, //Formal garden
    	{true, false, false, false}, //Throne room
    	{true, true, true, true}, //Oubliette
    	{true, false, true, false}, //Dungeon - Corridor
    	{true, true, true, true}, //Dungeon - Junction
    	{true, true, true, true}, //Dungeon - Stairs
    	{true, false, false, false} //Treasure room
    };
    
    public static final byte[] BasicGarden = {2, 0}; //type, rotation
    
    private List<Room> house;
	private byte houseplace = 0;
	private transient boolean buildMode;
	
	private transient Player player;
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
    public Construction() {
    	this.house = new ArrayList<Room>();
    	houseplace = 0; //=p best house type
		byte[] place = {6, 6, 0};
		makeRoom(BasicGarden, place);
    }
    
    public byte getPlayerRoom() {       
    byte roomX = (byte) (int) Math.floor((player.getLocation().getX() - 8 * ((4000 >> 3) - 6)) / 8);
    byte roomY = (byte) (int) Math.floor((player.getLocation().getY() - 8 * ((4000 >> 3) - 6)) / 8);
    	byte index = 0;
    	for(Room room : this.house) {
    		if(room.getPlace()[0] == roomX && room.getPlace()[1] == roomY && room.getPlace()[2] == player.getLocation().getZ())
    			return index;
    		index++;
    	}
    	return -1;
    }
    
    public boolean RoomExists(int roomX, int roomY, int height) {
    	for(Room room : this.house) {
    		if(room.getPlace()[0] == roomX && room.getPlace()[1] == roomY && room.getPlace()[2] == height)
    			return true;
    	}
    	return false;
    }
    public void clickObject(int objectId, int coordX, int coordY, int height) {
    	if(height != player.getLocation().getZ())
    		return;
    	
    	int playerRoom = 1;
    	if(playerRoom == -1)
    		return;
        int objectroomX = (int) Math.floor((player.getLocation().getX() - 8 * ((4000 >> 3) - 6)) / 8);
        int objectroomY = (int) Math.floor((player.getLocation().getY() - 8 * ((4000 >> 3) - 6)) / 8);
        
		int BaseCoordX = (player.getLocation().getX() >> 3) << 3;
		int BaseCoordY = (player.getLocation().getY() >> 3) << 3;
        
        System.out.println("playerroom: "+getPlayerRoom());
        System.out.println("oroomX: "+objectroomX +", oroomY: "+objectroomY);
        System.out.println("clickX: "+(coordX-BaseCoordX) +", clickY: "+(coordY-BaseCoordY));
        System.out.println("object: "+objectId);
        
        int clickX = coordX-BaseCoordX;
        int clickY = coordY-BaseCoordY;
        int roomtype = this.house.get(playerRoom).getRoom()[0];
        int rotation = this.house.get(playerRoom).getRoom()[1];
        this.buildMode = true;
        switch(roomtype) {
        case 2:
        if(buildMode) {
        	if (objectId == 15313 || objectId == 15314 || objectId == 15310 || objectId == 15309) {
        		byte newRoomX = 0;
        		byte newRoomY = 0;
        		byte newRoomDir = 0;
        		if(((clickX == 3 || clickX == 4) && clickY == 7)) { //north
        			newRoomX = (byte) objectroomX;
        			newRoomY = (byte) (objectroomY+1);
        			newRoomDir = 2;
        			if(RoomExists(newRoomX, newRoomY, height))
        				return;
        		} else if (clickX == 0 && (clickY == 3 || clickY == 4)) { //west
        			newRoomX = (byte) (objectroomX-1);
        			newRoomY = (byte) objectroomY;
        			newRoomDir = 1;
        			if(RoomExists(newRoomX, newRoomY, height))
        				return;
        		} else if (clickX == 7 && (clickY == 3 || clickY == 4)) {//east
        			newRoomX = (byte) (objectroomX+1);
        			newRoomY = (byte) objectroomY;
        			newRoomDir = 3;
        			if(RoomExists(newRoomX, newRoomY, height))
        				return;
        		} else if ((clickX == 3 || clickX == 4) && clickY == 0) {//south
        			newRoomX = (byte) objectroomX;
        			newRoomY = (byte) (objectroomY-1);
        			newRoomDir = 0;
        			if(RoomExists(newRoomX, newRoomY, height))
        				return;
        		} else {
        			return;
        		}
        		player.getFrames().sendInterface(402);
        		this.nextRoom = new byte[]{newRoomX, newRoomY, (byte) height, newRoomDir};
        		
        	}else if (objectId == 15313) {
        		if (clickX == 3 && clickY == 3) {
        			if(this.house.get(playerRoom).objectExists(clickX, clickY, objectId))
        				return;
        		}
        	}
        }
        break;
        default:
        break;
        }
    }
    
    private boolean[] getAllowedRottations(int roomType) {
    	boolean[] returnDirs = new boolean[4];
    	for(int actualdir = 0; actualdir < 4; actualdir++) {
			boolean []newAllowedDirs = new boolean[4];
			for(int i = 0; i < 4; i++) {
				int nextdir = actualdir+i;
    			if(nextdir > 3)
    				nextdir -= 3;
			newAllowedDirs[i] = AllowedRoomRott[roomType][nextdir];
			}
			if(newAllowedDirs[this.getNextRoom()[3]] == true)
				returnDirs[actualdir] = true;
    	}
		return returnDirs;	
    }
    
    
    private transient byte[] nextRoom;
    
    public void makeRoom(byte[] room, byte[] place) {
    	if(place == null || room == null || place.length < 3 || room.length < 2)
    		return;
    	this.house.add(new Room(room, place));
    }
    
    public void loadHouse() {
		int[][][][] palletes = new int[4][4][13][13];
		for(int x = 0; x < 13; x++) {
			for(int y = 0; y < 13; y++) {
				for(int z = 0; z < 4; z++) {
					if(z == 0) {
						palletes[0][z][x][y] = 1864 / 8;
						palletes[1][z][x][y] = 5056 / 8;
						palletes[2][z][x][y] = houseplace;        
					}else {
					palletes[0][z][x][y] = -1;
					palletes[1][z][x][y] = -1;
					palletes[2][z][x][y] = -1;
					}
				}
			}
		}
		for(Room room : this.house) {
			palletes[0][room.getPlace()[2]][room.getPlace()[0]][room.getPlace()[1]] = roomInfo[room.getRoom()[0]][0]/8;
			palletes[1][room.getPlace()[2]][room.getPlace()[0]][room.getPlace()[1]] = roomInfo[room.getRoom()[0]][1]/8;
			palletes[2][room.getPlace()[2]][room.getPlace()[0]][room.getPlace()[1]] = houseplace;
			palletes[3][room.getPlace()[2]][room.getPlace()[0]][room.getPlace()[1]] = room.getRoom()[1];
		}
		this.player.getMask().getRegion().teleport(4000, 4000, 0, player.getIndex(), palletes);
    }

	public void setNextRoom(byte[] nextRoom) {
		this.nextRoom = nextRoom;
	}

	public byte[] getNextRoom() {
		return nextRoom;
	}
    
    
}
