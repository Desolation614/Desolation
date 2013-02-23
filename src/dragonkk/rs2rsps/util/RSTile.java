package dragonkk.rs2rsps.util;

import java.io.Serializable;

import dragonkk.rs2rsps.model.player.Player;

/**
 * Represents a location in the world.
 * <p/>
 * Immutable.
 *
 * @author Graham
 */
public class RSTile implements Cloneable, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5261865636768751008L;
    private short x, y;
    private byte z;
    private int staticlocation;
    
    public RSTile(short x, short y, byte z, int staticlocation) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.staticlocation = staticlocation;
    }

    public int getStaticLocation() {
    	return staticlocation;
    }
    
    public short getX() {
        return x;
    }

    public void setX(short x) {
        this.x = x;
    }

    public short getY() {
        return y;
    }

    public void setY(short y) {
        this.y = y;
    }

    public void setZ(byte z) {
        this.z = z;
    }

    public void set(short x, short y, byte z, int staticlocation) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.staticlocation = staticlocation;
    }

    
    
    public void set(int x, int y, int z) {
        this.x = (short) x;
        this.y = (short) y;
        this.z = (byte) z;
    }

    public void set(int x, int y) {
        this.x = (short) x;
        this.y = (short) y;
    }

    public byte getZ() {
        return z;
    }

    public byte getLocalX() {
        return (byte) (x - 8 * (getRegionX() - 6));
    }

    public byte getLocalY() {
        return (byte) (y - 8 * (getRegionY() - 6));
    }

    public byte getLocalX(RSTile loc) {
        return (byte) (x - 8 * (loc.getRegionX() - 6));
    }

    public byte getLocalY(RSTile loc) {
        return (byte) (y - 8 * (loc.getRegionY() - 6));
    }
    
    public short getRegionX() {
        return (short) (x >> 3);
    }

    public short getRegionY() {
        return (short) (y >> 3);
    }

    public int get12BitsHash() {
		return (0x1f & getLocalY()) | (getZ() << 10) | (0x3e5 & ((getLocalX() << 5)));
    }
    
    public int get18BitsHash() {
    	int thisregionId = ((getRegionX() / 8) << 8) + (getRegionY() / 8);
		return (((thisregionId & 0xff) * 64) >> 6) | (getZ() << 16) | ((((thisregionId >> 8) * 64) >> 6) << 8);
    }
    
    public int get30BitsHash() {
		return getY()| ((getZ() << 28) | (getX() << 14));
    }
    
    public static RSTile createRSTile(int x, int y, int z) {
        return new RSTile((short)x, (short)y, (byte)z, 0);
    }

    public static RSTile createRSTile(short x, short y, byte z, int staticlocation) {
        return new RSTile(x, y, z, staticlocation);
    }
    
    @Override
    public int hashCode() {
        return z << 30 | x << 15 | y;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof RSTile)) {
            return false;
        }
        RSTile loc = (RSTile) other;
        return loc.x == x && loc.y == y && loc.z == z;
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "," + z + "]";
    }

    public boolean withinDistance(RSTile other, int dist) {
        if (other.z != z) {
            return false;
        }
        int deltaX = other.x - x, deltaY = other.y - y;
        return (deltaX <= (dist) && deltaX >= (0 - dist - 1) && deltaY <= (dist) && deltaY >= (0 - dist - 1));
    }

    public boolean withinDistance(RSTile other) {
        if (other.z != z) {
            return false;
        }
        if (other.staticlocation != staticlocation) {
            return false;
        }
        int deltaX = other.x - x, deltaY = other.y - y;
        return deltaX <= 14 && deltaX >= -15 && deltaY <= 14 && deltaY >= -15;
    }

    public boolean withinInteractionDistance(RSTile l) {
        return withinDistance(l, 3);
    }

    public static int wildernessLevel(RSTile l) {
        int y = l.getY();
        if (y > 3520 && y < 4000) {
            return (((int) (Math.ceil((double) (y) - 3520D) / 8D) + 1));
        }
        return 0;
    }

	public boolean checkWildRange(Player attacker, Player other) { 
		int oCombat = other.getSkills().getCombatLevel();
		int pCombat = attacker.getSkills().getCombatLevel();
		int wildLvl = (int) (Math.round((pCombat + oCombat) *.05) + 5) + wildernessLevel(attacker.getLocation());
		return (oCombat >= pCombat && pCombat + wildLvl >= oCombat
				|| oCombat < pCombat && pCombat - wildLvl <= oCombat || oCombat == pCombat);

	}
	
    public double getDistance(RSTile other) {
        int xdiff = this.getX() - other.getX();
        int ydiff = this.getY() - other.getY();
        return Math.sqrt(xdiff * xdiff + ydiff * ydiff);
    }
    
    public double getDistance(int x, int y) {
        int xdiff = this.getX() - x;
        int ydiff = this.getY() - y;
        return Math.sqrt(xdiff * xdiff + ydiff * ydiff);
    }
}
