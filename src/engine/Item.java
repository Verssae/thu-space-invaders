package engine;

import screen.ShopScreen;

import java.util.ArrayList;


public class Item {
    /** FileManager instance. */
    private static FileManager fileManager;

    public static ArrayList<Item> itemregistry;
    /** Item ID, must be associated manually! (to use with use)
    id is given at registeration time(at load, filemanager). */
    public int itemid;
    public String name;
    public int price;
    public boolean appliedp;

    public Item(int pitemid, String pName, int pPrice)
    {
        itemid = pitemid;
        name=pName;
        price = pPrice;
    }
    /** wanted to implement via higher-order function(lambda)
    but use switch-case for now. */
    public void use() {
        switch(itemid)
        {
            case 1000:
                /** Apply ship skin */
                fileManager = Core.getFileManager();
            case 2000:
                /** Apply Bgm */


            default:
            //You should not be here!!
        }
    }
}
