package dragonkk.rs2rsps.model;

import java.io.Serializable;

/**
 * Container class.
 *
 * @author Graham
 * @param <T>
 */
public class Container<T extends Item> implements Serializable {

    private static final long serialVersionUID = 1099313426737026107L;

    private Item[] data;
    private boolean alwaysStackable = false;

    public Container(int size, boolean alwaysStackable) {
        data = new Item[size];
        if(size == 9244) {
        	alwaysStackable = true;
        }
        this.alwaysStackable = alwaysStackable;
    }

    public void shift() {
        Item[] oldData = data;
        data = new Item[oldData.length];
        int ptr = 0;
        for (int i = 0; i < data.length; i++) {
            if (oldData[i] != null) {
                data[ptr++] = oldData[i];
            }
        }
    }

    @SuppressWarnings("unchecked")
    public T get(int slot) {
        if (slot < 0 || slot >= data.length) {
            return null;
        }
        return (T) data[slot];
    }

    public void set(int slot, T item) {
            if (slot < 0 || slot >= data.length) {
                return;
            }
            data[slot] = item;
    }

    public void set2(int slot, Item item) {
            if (slot < 0 || slot >= data.length) {
                return;
            }
            data[slot] = item;
    }

    public boolean forceAdd(T item) {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null) {
                data[i] = item;
                return true;
            }
        }
        return false;
    }

    public boolean add(T item) {
        if (alwaysStackable || item.getDefinition().isStackable() || item.getDefinition().isNoted()) {
            for (int i = 0; i < data.length; i++) {
                if (data[i] != null) {
                    if (data[i].getId() == item.getId()) {
                        data[i] = new Item(data[i].getDefinition().getId(), data[i].getAmount() + item.getAmount());
                        return true;
                    }
                }
            }
        } else {
            if (item.getAmount() > 1) {
                if (freeSlots() >= item.getAmount()) {
                    for (int i = 0; i < item.getAmount(); i++) {
                        int index = freeSlot();
                        data[index] = new Item(item.getId(), 1);
                    }
                    return true;
                } else {
                    return false;
                }
            }
        }
        int index = freeSlot();
        if (index == -1) {
            return false;
        }
        data[index] = item;
        return true;
    }

    public boolean addToSlot(T item, int slot) {
        if (alwaysStackable || item.getDefinition().isStackable() || item.getDefinition().isNoted()) {
            for (int i = 0; i < data.length; i++) {
                if (data[i] != null) {
                    if (data[i].getId() == item.getId()) {
                        data[i] = new Item(data[i].getDefinition().getId(), data[i].getAmount() + item.getAmount());
                        return true;
                    }
                }
            }
        } else {
            if (item.getAmount() > 1) {
                if (freeSlots() >= item.getAmount()) {
                    for (int i = 0; i < item.getAmount(); i++) {
                        //int index = freeSlot();
                        data[slot] = new Item(item.getId(), 1);
                    }
                    return true;
                } else {
                    return false;
                }
            }
        }
        //int index = freeSlot();
        if (slot == -1) {
            return false;
        }
        data[slot] = item;
        return true;
    }
    
    public int freeSlots() {
            int j = 0;
            for (Item aData : data) {
                if (aData == null) {
                    j++;
                }
            }
            return j;
    }

    public void remove(T item) {
            int removed = 0, toRemove = item.getAmount();
            for (int i = 0; i < data.length; i++) {
                if (data[i] != null) {
                    if (data[i].getId() == item.getId()) {
                        int amt = data[i].getAmount();
                        if (amt > toRemove) {
                            removed += toRemove;
                            amt -= toRemove;
                            toRemove = 0;
                            data[i] = new Item(data[i].getDefinition().getId(), amt);
                            return;
                        } else {
                            removed += amt;
                            toRemove -= amt;
                            data[i] = null;
                        }
                    }
                }
            }
    }

    public void removeAll(T item) {
            for (int i = 0; i < data.length; i++) {
                if (data[i] != null) {
                    if (data[i].getId() == item.getId()) {
                        data[i] = null;
                    }
                }
            }
    }

    public boolean containsOne(T item) {
        for (Item aData : data) {
            if (aData != null) {
                if (aData.getId() == item.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean contains(T item) {
        int amtOf = 0;
        for (Item aData : data) {
            if (aData != null) {
                if (aData.getId() == item.getId()) {
                    amtOf += aData.getAmount();
                }
            }
        }
        return amtOf >= item.getAmount();
    }

    public int freeSlot() {
            for (int i = 0; i < data.length; i++) {
                if (data[i] == null) {
                    return i;
                }
            }
            return -1;
    }

    public void clear() {
            for (int i = 0; i < data.length; i++) {
                data[i] = null;
            }
    }

    public int getSize() {
            return data.length;
    }

    public int getFreeSlots() {
            int s = 0;
            for (Item aData : data) {
                if (aData == null) {
                    s++;
                }
            }
            return s;
    }

    public int getNumberOf(Item item) {
            int count = 0;
            for (Item aData : data) {
                if (aData != null) {
                    if (aData.getId() == item.getId()) {
                        count += aData.getAmount();
                    }
                }
            }
            return count;
    }

    public int getNumberOff(int item) {
            int count = 0;
            for (Item aData : data) {
                if (aData != null) {
                    if (aData.getId() == item) {
                        count += aData.getAmount();
                    }
                }
            }
            return count;
    }

    public Item[] getItems() {
        return data;
    }

    public Container<Item> asItemContainer() {
        Container<Item> c = new Container<Item>(data.length, this.alwaysStackable);
        System.arraycopy(data, 0, c.data, 0, data.length);
        return c;
    }

    public int getFreeSlot() {
            for (int i = 0; i < data.length; i++) {
                if (data[i] == null) {
                    return i;
                }
            }
            return 0;
    }

    public int getThisItemSlot(T item) {
            for (int i = 0; i < data.length; i++) {
                if (data[i] != null) {
                    if (data[i].getId() == item.getId()) {
                        return i;
                    }
                }
            }
            return getFreeSlot();
    }

    public Item lookup(int id) {
        for (Item aData : data) {
            if (aData == null) {
                continue;
            }
            if (aData.getId() == id) {
                return aData;
            }
        }
        return null;
    }

    public int lookupSlot(int id) {
            for (int i = 0; i < data.length; i++) {
                if (data[i] == null) {
                    continue;
                }
                if (data[i].getId() == id) {
                    return i;
                }
            }
            return -1;
    }

    public void reset() {
            data = new Item[data.length];
    }

    public void remove(int preferredSlot, Item item) {
            int removed = 0, toRemove = item.getAmount();
            if (data[preferredSlot] != null) {
                if (data[preferredSlot].getId() == item.getId()) {
                    int amt = data[preferredSlot].getAmount();
                    if (amt > toRemove) {
                        removed += toRemove;
                        amt -= toRemove;
                        toRemove = 0;
                        //data[preferredSlot] = new Item(data[preferredSlot].getDefinition().getId(), amt);
                        set2(preferredSlot, new Item(data[preferredSlot].getDefinition().getId(), amt));
                        return;
                    } else {
                        removed += amt;
                        toRemove -= amt;
                        //data[preferredSlot] = null;
                        set(preferredSlot, null);
                    }
                }
            }
            for (int i = 0; i < data.length; i++) {
                if (data[i] != null) {
                    if (data[i].getId() == item.getId()) {
                        int amt = data[i].getAmount();
                        if (amt > toRemove) {
                            removed += toRemove;
                            amt -= toRemove;
                            toRemove = 0;
                            //data[i] = new Item(data[i].getDefinition().getId(), amt);
                            set2(i, new Item(data[preferredSlot].getDefinition().getId(), amt));
                            return;
                        } else {
                            removed += amt;
                            toRemove -= amt;
                            //data[i] = null;
                            set(i, null);
                        }
                    }
                }
            }
    }

    public void addAll(Container<T> container) {
            for (int i = 0; i < container.getSize(); i++) {
                T item = container.get(i);
                if (item != null) {
                    this.add(item);
                }
            }
    }

    public boolean hasSpaceFor(Container<T> container) {
        for (int i = 0; i < container.getSize(); i++) {
            T item = container.get(i);
            if (item != null) {
                if (!this.hasSpaceForItem(item)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hasSpaceForItem(T item) {
        if (alwaysStackable || item.getDefinition().isStackable() || item.getDefinition().isNoted()) {
            for (Item aData : data) {
                if (aData != null) {
                    if (aData.getId() == item.getId()) {
                        return true;
                    }
                }
            }
        } else {
            if (item.getAmount() > 1) {
                return freeSlots() >= item.getAmount();
            }
        }
        int index = freeSlot();
        return index != -1;
    }

}
