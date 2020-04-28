package pineapple;

import pineapple.model.Item;

import java.util.HashMap;

public class ItemManager {


    private static HashMap<Integer, Item> itemMap = new HashMap<>();

    public static void init() {
        //@XZW initialize the items for the game
        Item stone = new Item(1, "stone", 1, "Low");
        Item water = new Item(2, "water", 2, "Low");
        Item iron = new Item(3, "iron", 5, "Low");
        Item silver = new Item(4, "silver", 10, "Medium");
        Item gold = new Item(5, "gold", 20, "Medium");
        Item gem = new Item(6, "gem", 50, "High");
        Item uranium = new Item(7, "uranium", 100, "High");
        Item winningItem = new Item(8, "winning item", 1, "Low");
        itemMap.put(1, stone);
        itemMap.put(2, water);
        itemMap.put(3, iron);
        itemMap.put(4, silver);
        itemMap.put(5, gold);
        itemMap.put(6, gem);
        itemMap.put(7, uranium);
        itemMap.put(8, winningItem);
    }

    public static HashMap<Integer, Item> getItemMap() {
        return itemMap;
    }


}
