package dragonkk.rs2rsps.model;

/**
 * Represents a single item in the game world.
 * @author Advocatus.
 */
public class GroundItem {

    public short id;
    public int amount;
    public int xCoord;
    public int yCoord;
    public byte zCoord;

    public GroundItem(short id, int amount, int x, int y, byte z) {
        this.id = id;
        this.amount = amount;
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
    }
}