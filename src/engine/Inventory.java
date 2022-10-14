package engine;

import screen.ShopScreen;

import java.util.ArrayList;
import java.util.function.Predicate;

import engine.Item.types;

public class Inventory {
    public static class InventoryEntry {
        public Item item;
        public int quantaty;

        InventoryEntry(Item pItem, int qty) {
            item = pItem;
            quantaty = qty;
        }
    }

    public static ArrayList<Item> inventory_ship;
    public static ArrayList<Item> inventory_bgm;

    public static int getcurrentship()
    {
        for (Item item : inventory_ship) {
            if(item.appliedp) return item.itemid;
        }
        return 1;
    }

    public static boolean hasitem(Item itm)
    {
        for (Item item : inventory_ship) {
            if(item==itm) return true;
        }
        for (Item item : inventory_bgm) {
            if(item==itm) return true;
        }
        return false;
    }
    /**
    void additem(Item itm) {
        if(hasitem(itm)) return;        
        inventory.add(itm);
    }*/
}
