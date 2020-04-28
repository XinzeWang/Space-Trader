package pineapple;

import pineapple.model.Ship;

import java.util.HashMap;

public class ShipManager {
    // @ZKQ add ship
    private static HashMap<Integer, Ship> shipMap = new HashMap<>();

    private static Ship ship0 = new Ship(0, "BasicShip",
            100, 10000, 1231);

    //@ZKQ @XZW
    //    private static Item stone = new Item("stone", 1, "Low");
    //    private static Item water = new Item("water", 2, "Low");
    //    private static Item gold = new Item("gold", 100, "High");
    //    private static Item iron = new Item("iron", 10, "Medium");
    //    private static Item gem = new Item("gem", 300, "High");

    public static void init() {
        ship0 = new Ship(0, "BasicShip",
                100, 10000, 1);
        ship0.putInventory(1, 10);
        ship0.putInventory(2, 10);
        ship0.putInventory(3, 3);
        ship0.putInventory(4, 2);
        ship0.putInventory(5, 1);

        shipMap.put(0, ship0);
    }

    public static HashMap getShipMap() {
        return shipMap;
    }

    public static Ship getShipByID(int id) {
        return shipMap.get(id);
    }
}
