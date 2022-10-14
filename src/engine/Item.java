package engine;

import screen.ShopScreen;

import java.util.ArrayList;

import engine.DrawManager.SpriteType;


public class Item {
    /** FileManager instance. */
    private static FileManager fileManager;

    public static ArrayList<Item> itemregistry_ship;
    public static ArrayList<Item> itemregistry_bgm;
    /** Item ID, must be associated manually! (to use with use)
    id is given at registeration time(at load, filemanager). */
    public enum types{
        ship, bgm
    }
    public types type;
    public int itemid;
    public String name;
    public String description;
    public SpriteType assocsprite;
    public int price;
    public boolean appliedp;

    public Item(int pitemid, String pName, int pPrice,boolean applied)
    {
        itemid = pitemid;
        name=pName;
        price = pPrice;
        appliedp = applied;
    }
    public Item(int pitemid, String pName, int pPrice)
    {
        itemid = pitemid;
        name=pName;
        price = pPrice;
    }
    public static Item getItembyID(int itemid)
    {
        for (Item ite : itemregistry_ship) {
            if(ite.itemid==itemid) return ite;
        }
        return null;
    }
}
