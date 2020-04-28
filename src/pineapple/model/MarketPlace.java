package pineapple.model;

import java.util.HashMap;

public class MarketPlace {
    private Double priceMultiplier; // could be relevant to tech_level
    private HashMap<Integer, Integer> marketItems; // <itemId, count>
    private boolean containWinningItem; // whether contain  winning item


    public MarketPlace(HashMap<Integer, Integer> marketItems) {
        this.priceMultiplier = 1.0;
        this.marketItems = marketItems;
    }

    public MarketPlace() {
        this.priceMultiplier = 1.0;
        HashMap<Integer, Integer> defaultItems = new HashMap<>();
        defaultItems.put(1, 10); // we have 10 stones as default
        this.marketItems = defaultItems;
    }

    public Double getPriceMultiplier() {
        return priceMultiplier;
    }

    public void setPriceMultiplier(Double priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }

    public HashMap<Integer, Integer> getMarketItems() {
        return marketItems;
    }

    public void setMarketItems(HashMap<Integer, Integer> marketItems) {
        this.marketItems = marketItems;
    }

    public Integer getItemCount(int itemId) {
        return marketItems.getOrDefault(itemId, 0);
    }

    // for player sell Items to market
    public void addItem(int itemId) {
        marketItems.put(itemId, marketItems.getOrDefault(itemId, 0) + 1);
    }

    // for player buy Items from market
    public void removeItem(int itemId) {
        assert marketItems.get(itemId) > 0;
        marketItems.put(itemId, marketItems.get(itemId) - 1);
    }

    // @XZW initial version
    public Double getBuyPrice(Item item) {
        // int randomNum = (int) (Math.random() * 5);
        return priceMultiplier * item.getItemPrice();
    }

    public Double getSellPrice(Item item) {
        //int randomNum = (int) (Math.random() * 5);
        //int sellPrice = (int) ((priceMultiplier * item.getItemPrice() + randomNum) * 0.5);
        return priceMultiplier * item.getItemPrice() * 0.8;
    }
}

