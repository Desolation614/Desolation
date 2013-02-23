package dragonkk.rs2rsps.model;

import java.util.ArrayList;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.util.RSTile;

/**
 * @author Hadyn Fitzgerald
 * @version 1.0
 */
public class GlobalDropManager {

    public static ArrayList<GlobalDropItem> GlobalItemList = new ArrayList<GlobalDropItem>(10000);

    public GlobalDropManager() {       
    }

    public static GlobalDropItem getDropItem(int id, int x, int y, int z) {
        if(GlobalItemList.isEmpty())
            return null;
        for(int i = 0; i < GlobalItemList.size(); i++) {
            GlobalDropItem dropitem = GlobalItemList.get(i);
            if(dropitem == null) continue;
            if(dropitem.x == x & dropitem.y == y & dropitem.z == z & dropitem.id == id) {
                return dropitem;
            } else {
                continue;
            }
        }
        return null;
    }
    
    public static boolean isValid(int id, int x, int y, int z) {
        if(GlobalItemList.isEmpty())
            return false;
        for(int i = 0; i < GlobalItemList.size(); i++) {
            GlobalDropItem dropitem = GlobalItemList.get(i);
            if(dropitem == null) continue;
            if(dropitem.x == x && dropitem.y == y && dropitem.z == z && dropitem.id == id) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }
    
    public static void dropItem(Player p, RSTile tile, Item item, boolean uniqueDrop) {
        GlobalItemList.add(new GlobalDropItem((int) item.getId(), item.getAmount(), (int) tile.getX(), (int) tile.getY(), (int) tile.getZ()/*, 50*/));
        p.getFrames().sendGroundItem(tile, item, uniqueDrop);
    }

    public static void deleteGlobalDropItem(GlobalDropItem dropitem) {
    	GlobalItemList.remove(dropitem);
        int i = GlobalItemList.indexOf(dropitem);
        if(i == -1) return;
        GlobalItemList.remove(i);
    }
}
