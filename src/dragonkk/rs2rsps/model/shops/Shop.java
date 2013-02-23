package dragonkk.rs2rsps.model.shops;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hadyn Fitzgerald
 * @version 1.0
 */
public class Shop {

    public int id;
    public int maxslot;
    public List<int[]> items = new ArrayList<int[]>(40);

    public Shop(int id, List itemlist, int maxslot) {
        this.items = itemlist;
        this.maxslot = maxslot;
    }
    
    public void buyItem(int slot, int amount) {
       int itemsamount = items.get(slot)[1];
       System.out.println("ItemAmount: "+itemsamount);
       itemsamount = itemsamount - amount;
       System.out.println("ItemAmount: "+itemsamount);
       items.get(slot)[1] = itemsamount;
    }

    public void sellItem(int slot, int amount) {
       int itemsamount = items.get(slot)[1];
      System.out.println("ItemAmount: "+itemsamount);
       itemsamount = itemsamount + amount;
       System.out.println("ItemAmount: "+itemsamount);
       items.get(slot)[1] = itemsamount;
    }

    public boolean sellsItem(int item) {
        for(int i = 0; i < maxslot; i++) {
            if(items.get(i) == null)
                continue;
            int[] array = items.get(i);
            if(array[0] == item) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

    public int getSlotId(int item) {
        for(int i = 0; i < maxslot; i++) {
            int[] array = items.get(i);
            if(array[0] == item) {
                System.out.println(i);
                return i;
            } else {
                continue;
            }
        }
        return 0;
    }
}
