package engine;

import java.util.ArrayList;
import java.util.function.Predicate;

public class Inventory {
    public static class InventoryEntry {
        public Item item;
        public int quantaty;

        InventoryEntry(Item pItem, int qty) {
            item = pItem;
            quantaty = qty;
        }
    }

    public static ArrayList<InventoryEntry> inventory;

    void additem(Item itm, int qty) {
        boolean inp = false;
        for (InventoryEntry i : inventory) {
            if (i.item.itemid == itm.itemid) {
                i.quantaty += qty;
                inp = true;
                break;
                
            }
        }
        if (!inp) {
            inventory.add(new InventoryEntry(itm, qty));
        }
    }

    void reduceitem() {

    }
}
