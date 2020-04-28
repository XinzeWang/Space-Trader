package pineapple;

import pineapple.model.MarketPlace;

import java.util.HashMap;
import java.util.Random;

public class MarketManager {
    //XYZ
    private static HashMap<String, MarketPlace> marketMap = new HashMap<>();

    // initialize the marketPlace for the region
    private static HashMap<Integer, Integer> marketItems1 = new HashMap<Integer, Integer>();

    private static HashMap<Integer, Integer> marketItems2 = new HashMap<Integer, Integer>();

    private static HashMap<Integer, Integer> marketItems3 = new HashMap<Integer, Integer>();

    private static HashMap<Integer, Integer> marketItems4 = new HashMap<Integer, Integer>();

    private static HashMap<Integer, Integer> marketItems5 = new HashMap<Integer, Integer>();

    private static HashMap<Integer, Integer> marketItems6 = new HashMap<Integer, Integer>();

    private static HashMap<Integer, Integer> marketItems7 = new HashMap<Integer, Integer>();

    private static HashMap<Integer, Integer> marketItems8 = new HashMap<Integer, Integer>();

    private static HashMap<Integer, Integer> marketItems9 = new HashMap<Integer, Integer>();

    private static HashMap<Integer, Integer> marketItems10 = new HashMap<Integer, Integer>();

    // construct marketPlace
    private static MarketPlace market1;
    private static MarketPlace market2;
    private static MarketPlace market3;
    private static MarketPlace market4;
    private static MarketPlace market5;
    private static MarketPlace market6;
    private static MarketPlace market7;
    private static MarketPlace market8;
    private static MarketPlace market9;
    private static MarketPlace market10;

    public static void init() {
        initMarketItems();
        initMarket();
        initMarketMap();
    }
    public static void initMarketItems() {
        Random rand = new Random();
        int winningMarketId = rand.nextInt((10 - 1) + 1) + 1;
        System.out.println(winningMarketId + "is the market containing winning item");

        marketItems1.put(1, 5);
        marketItems1.put(2, 10);
        marketItems1.put(3, 8);

        marketItems2.put(1, 3);
        marketItems2.put(3, 5);

        marketItems3.put(2,5);
        marketItems3.put(3, 10);

        marketItems4.put(2, 3);
        marketItems4.put(3, 7);

        marketItems5.put(1, 6);
        marketItems5.put(4, 5);
        marketItems5.put(5, 10);

        marketItems6.put(5, 5);
        marketItems6.put(3, 10);
        marketItems6.put(2, 9);

        marketItems7.put(3, 6);
        marketItems7.put(5, 8);

        marketItems8.put(1, 5);
        marketItems8.put(2, 10);
        marketItems8.put(3, 5);
        marketItems8.put(4, 10);
        marketItems8.put(5, 10);

        marketItems9.put(3, 8);
        marketItems9.put(5, 5);
        marketItems9.put(6, 1);

        marketItems10.put(1, 1);
        marketItems10.put(2, 1);
        marketItems10.put(3, 1);
        marketItems10.put(4, 1);
        marketItems10.put(5, 1);
        marketItems10.put(6, 1);

        switch (winningMarketId) {
        case 1:
            marketItems1.put(8, 1);
            break;
        case 2:
            marketItems2.put(8, 1);
            break;
        case 3:
            marketItems3.put(8, 1);
            break;
        case 4:
            marketItems4.put(8, 1);
            break;
        case 5:
            marketItems5.put(8, 1);
            break;
        case 6:
            marketItems6.put(8, 1);
            break;
        case 7:
            marketItems7.put(8, 1);
            break;
        case 8:
            marketItems8.put(8, 1);
            break;
        case 9:
            marketItems9.put(8, 1);
            break;
        case 10:
            marketItems10.put(8, 1);
            break;
        }
    }

    public static void initMarket() {
        market1 = new MarketPlace(marketItems1);
        market2 = new MarketPlace(marketItems2);
        market3 = new MarketPlace(marketItems3);
        market4 = new MarketPlace(marketItems4);
        market5 = new MarketPlace(marketItems5);
        market6 = new MarketPlace(marketItems6);
        market7 = new MarketPlace(marketItems7);
        market8 = new MarketPlace(marketItems8);
        market9 = new MarketPlace(marketItems9);
        market10 = new MarketPlace(marketItems10);
    }

    public static void initMarketMap() {
        marketMap.put("market1", market1);
        marketMap.put("market2", market2);
        marketMap.put("market3", market3);
        marketMap.put("market4", market4);
        marketMap.put("market5", market5);
        marketMap.put("market6", market6);
        marketMap.put("market7", market7);
        marketMap.put("market8", market8);
        marketMap.put("market9", market9);
        marketMap.put("market10", market10);
    }

    public static HashMap<String, MarketPlace> getMarketMap() {
        return marketMap;
    }

}
