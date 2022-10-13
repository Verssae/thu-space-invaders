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

    public static ArrayList<Item> inventory;

    public static int getcurrentship()
    {
        for (Item item : inventory) {
            if(item.type==types.ship ||item.appliedp) return item.itemid; 
        }
        return 1;
    }

    boolean hasitem(Item itm)
    {
        for (Item item : inventory) {
            if(item==itm) return true;
        }
        return false;
    }
    //There was not enough time to analyze and use this code. I'm sorry.
    void additem(Item itm) {
        if(hasitem(itm)) return;        
        inventory.add(itm);
    }
}
