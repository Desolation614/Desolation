package dragonkk.rs2rsps.model.player;

import java.io.Serializable;

import dragonkk.rs2rsps.model.Container;
import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.rscache.Cache;
import dragonkk.rs2rsps.rscache.ItemDefinitions;

/**
 * Manages the player inventory.
 *
 * @author Graham
 */
public class Inventory implements Serializable {


    private static final long serialVersionUID = 5229803962314209861L;

    public static final byte SIZE = 28;

    private Container<Item> inventory = new Container<Item>(SIZE, false);

    private transient Player player;

    public void setPlayer(Player player) {
        this.player = player;
    	for(Item item : inventory.getItems())
    	if(item != null)
        item.setDefinition(ItemDefinitions.forID(item.getId()));
    }
    
    public void reset() {
		inventory = new Container<Item>(SIZE, false);
		refresh();
	}

    public boolean addItem(int item, int amount) {
        if (item < 0 || item > Cache.getAmountOfItems()) { //tmp fix for items
            return false;
        }
        boolean b = inventory.add(new Item(item, amount));
        if (!b) {
            player.getFrames().sendChatMessage(0, "Not enough space in your inventory.");
            refresh();
            return false;
        }
        refresh();
        return true;
    }

    public boolean addSlotItem(int item, int amount, int slot) {
        if (item < 0 || item > Cache.getAmountOfItems()) { //tmp fix for items
            return false;
        }
        boolean b = inventory.addToSlot(new Item(item, amount), slot);
        if (!b) {
            player.getFrames().sendChatMessage(0, "Not enough space in your inventory.");
            refresh();
            return false;
        }
        refresh();
        return true;
    }
    
    public boolean contains(int item, int amount) {
        return inventory.contains(new Item(item, amount));
    }

    public boolean contains(int item) {
        return inventory.containsOne(new Item(item));
    }

    public void deleteItem(int item, int amount) {
            inventory.remove(new Item(item, amount));
            refresh();
    }

    public void deleteAll(int item) {
            inventory.removeAll(new Item(item));
            refresh();
    }

    public void refresh() {
    	player.getFrames().sendItems(93, inventory, false);
    }

    public Container<Item> getContainer() {
        return inventory;
    }

    public int getFreeSlots() {
            return inventory.getFreeSlots();
    }

    public boolean hasRoomFor(int id, int itemAmount) {
        if (ItemDefinitions.forID(id).isStackable()) {
            return getFreeSlots() >= 1 || contains(id);
        } else {
            return getFreeSlots() >= itemAmount;
        }
    }

    public int numberOf(int id) {
            return inventory.getNumberOf(new Item(id, 1));
    }

    public Item lookup(int id) {
        return inventory.lookup(id);
    }

    public int lookupSlot(int id) {
            return inventory.lookupSlot(id);
    }

}
