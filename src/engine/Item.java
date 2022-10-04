package engine;

import java.util.ArrayList;

public class Item {
    public static ArrayList<Item> itemregistry;
    // Item ID, must be associated manually! (to use with use)
    //id is given at registeration time(at load, filemanager).
    public int itemid;
    public String name;
    public int price;
    public boolean appliedp;

    Item()
    {

    }

    // wanted to implement via higher-order function(lambda)
    // but use switch-case for now.
    public void use() {
        switch(itemid)
        {
            default:
            //You should not be here!!
        }
    }
}
