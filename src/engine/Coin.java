package engine;

public class Coin {
    public static int balance;

    public static int spend(int amount) {
        if (balance < amount)
            return -1;
        balance -= amount;
        return balance;
    }

    public static int earn(int amount){
        return (balance+=amount);
    }
}
