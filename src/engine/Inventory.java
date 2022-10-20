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

    public String dumpinven()
    {
        String ret=new String();
        for (Item item : inventory_ship) {
            ret+=Integer.toString(item.itemid)+" "+Boolean.toString(item.appliedp)+" ";
        }
        return ret;
    }

    public static void parseinven(String input)
    {
        var tok=new java.util.StringTokenizer(input, " ");
        while(tok.hasMoreTokens())
        {
            int itemid=Integer.parseInt(tok.nextToken());
            Boolean appp=Boolean.parseBoolean(tok.nextToken());
            inventory_ship.add(Item.getItembyID(itemid));
            inventory_ship.get(inventory_ship.size()).appliedp=appp;
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
