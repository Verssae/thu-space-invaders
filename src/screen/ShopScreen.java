package screen;

public class ShopScreen {

    public boolean purchase(engine.Item item, int qty) {
        return (engine.Coin.spend(item.price*qty) == -1) ? true : false;
    }
}
