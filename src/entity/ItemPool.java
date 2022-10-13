package entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Implements a pool of recyclable items.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public final class ItemPool {

    /** Set of already created items. */
    private static Set<Item> pool = new HashSet<Item>();

    /**
     * Constructor, not called.
     */
    private ItemPool() {

    }

    /**
     * Returns a item from the pool if one is available, a new one if there
     * isn't.
     *
     * @param positionX
     *            Requested position of the item in the X axis.
     * @param positionY
     *            Requested position of the item in the Y axis.
     * @param speed
     *            Requested speed of the item, positive or negative depending
     *            on direction - positive is down.
     * @return Requested item.
     */
    public static Item getItem(final int positionX,
                               final int positionY, final int speed) {
        Item item;
        if (!pool.isEmpty()) {
            item = pool.iterator().next();
            pool.remove(item);
            item.setPositionX(positionX - item.getWidth() / 2);
            item.setPositionY(positionY);
            item.setSpeed(speed);
            item.setSprite();
        } else {
            item = new Item(positionX, positionY, speed);
            item.setPositionX(positionX - item.getWidth() / 2);
        }
        return item;
    }

    /**
     * Adds one or more items to the list of available ones.
     *
     * @param item
     *            Items to recycle.
     */
    public static void recycle(final Set<Item> item) {
        pool.addAll(item);
    }
}