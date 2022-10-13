package engine;

import screen.ShopScreen;

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

    public static ArrayList<Item> inventory;


    /** There was not enough time to analyze and use this code. I'm sorry.
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

    } */
}
