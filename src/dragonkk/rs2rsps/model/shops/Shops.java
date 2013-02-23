package dragonkk.rs2rsps.model.shops;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hadyn Fitzgerald
 * @version 1.0
 */
public class Shops {

    public ArrayList<Shop> shops = new ArrayList<Shop>(10);

    public Shops() {
        setShops();
    }

    public void setShops() {
        shops.add(new Shop(0, Shop0Items(), Shop0Items().size()));
    }


    public Shop getShop(int shopid) {
        switch(shopid) {
            case 0:
                Shop shop = shops.get(0);
                return shop;
        }
        return null;
    }

    public static List<int[]> Shop0Items() {
        List<int[]> items = new ArrayList<int[]>(40);
        //item amount price?
        //vesta
        items.add(new int[]{13887, 1000, 5000000});
        items.add(new int[]{13893, 1000, 5000000});
        items.add(new int[]{13899, 1000, 7000000});
        //stat
        items.add(new int[]{13884, 1000, 500000});
        items.add(new int[]{13890, 1000, 5000000});
        items.add(new int[]{13896, 1000, 7000000});
        //morrigans
        items.add(new int[]{13870, 1000, 2500000});
        items.add(new int[]{13873, 1000, 2500000});
        items.add(new int[]{13876, 1000, 3000000});
        //zuriels
        items.add(new int[]{13858, 1000, 2500000});
        items.add(new int[]{13861, 1000, 2500000});
        items.add(new int[]{13864, 1000, 4000000});
        items.add(new int[]{13867, 1000, 6000000});
        //spirit shields
        items.add(new int[]{13734, 1000, 2000000});
        items.add(new int[]{13736, 1000, 32000000});
        items.add(new int[]{13738, 1000, 62000000});
        items.add(new int[]{13740, 1000, 200000000});
        items.add(new int[]{13742, 1000, 100000000});
        //items.add(new int[]{13374, 1000, 750});
        return items;
    }
}
