package dragonkk.rs2rsps.model;

import java.util.HashSet;
import java.util.Set;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.util.RSTile;

/**
 * Manages a list of global ground items, NOTE: THIS IS UNTESTED.
 * @author Advocatus
 */
public class GroundItemMap {

    public static Set<GroundItem> GlobalItemList = new HashSet<GroundItem>(10000);

    public GroundItemMap() {
    }

    public static GroundItem getDropItem(int id, int amount, int x, int y, int z) {
	if (GlobalItemList.isEmpty())
	    return null;
	GroundItem dropitem = new GroundItem((short) id, amount, (int) x, (int) y, (byte) z);
	if (GlobalItemList.contains(dropitem))
	    return dropitem;
	else
	    return null;
    }

    public static boolean isValid(int id, int amount, int x, int y, int z) {
	if (GlobalItemList.isEmpty())
	    return false;
	GroundItem dropitem = new GroundItem((short) id, amount, (int) x, (int) y, (byte) z);
	if (GlobalItemList.contains(dropitem))
	    return true;
	else
	    return false;
    }

    public static void dropItem(Player p, RSTile tile, Item item, boolean uniqueDrop) {
	GlobalItemList.add(new GroundItem((short) item.getId(), item.getAmount(), (int) tile.getX(), (int) tile.getY(), (byte) tile.getZ()));
	p.getFrames().sendGroundItem(tile, item, uniqueDrop);
    }

    public static void deleteGlobalDropItem(GroundItem dropitem) {
	if (dropitem == null)
	    return;
	GlobalItemList.remove(dropitem);
    }
}
