package pineapple.model;

import pineapple.AlertBox;
import pineapple.ItemManager;

import java.util.HashMap;
import java.util.Random;

public class Trader {

    private HashMap<Integer, Item> itemMap = ItemManager.getItemMap();
    private HashMap<Integer, Integer> traderItems = new HashMap<>();
    private Boolean negotiated;
    private boolean negotiateResult;

    public Trader() {
        this.negotiated = false;
        this.negotiateResult = false;

        for (int i = 1; i <= 3; i++) {
            Integer randNum = (int) (Math.random() * 3);
            traderItems.put(i, randNum);
        }

        Integer randItemId = (int) (Math.random() * 4) + 3;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            traderItems.put(randItemId, 1);
        }
    }

    public String getItemsAndPriceForTrade() {
        StringBuilder itemForTrade = new StringBuilder();
        itemForTrade.append("This trader has: ");


        for (Integer id : traderItems.keySet()) {
            itemForTrade.append(itemMap.get(id).getItemName());
            itemForTrade.append(": ");
            itemForTrade.append(traderItems.get(id));
            itemForTrade.append("; ");
        }

        return itemForTrade.toString();
    }

    public Double getTotalPrice() {
        Double price = 0.0;
        for (Integer id : traderItems.keySet()) {
            price += itemMap.get(id).getItemPrice();
        }

        return price;
    }

    public Integer getTotalItemNum() {
        Integer totalNum = 0;

        for (Integer id : traderItems.keySet()) {
            totalNum += traderItems.get(id);
        }

        return totalNum;
    }

    public  Double getNegogiatedPrice() {
        if (!negotiated) {
            return this.getTotalPrice();
        } else {
            if (negotiateResult) {
                return this.getTotalPrice() * 0.5;
            } else {
                return this.getTotalPrice() * 1.5;
            }
        }
    }

    public HashMap<Integer, Integer> getTraderItems() {
        return traderItems;
    }

    public void setTraderItems(HashMap<Integer, Integer> traderItems) {
        this.traderItems = traderItems;
    }

    public void setNegotiated() {
        negotiated = true;
    }

    public boolean getNegotiated() {
        return negotiated;
    }

    public void setNegotiatedResult(boolean res) {
        negotiateResult = res;
    }

    public boolean getNegotiatedResult() {
        return negotiateResult;
    }


    public Integer handleBuy(Ship ship, Player player) {
        //handle player buy items
        int currLoad = ship.getCurrLoad();
        if (currLoad + this.getTotalItemNum() > ship.getCargoCapacity()) {
            AlertBox a = new AlertBox("WARNING", "WARNING", "CargoCap not enough! Please ignore");
            a.showAlert();
            return 0;
        } else {
            for (int id: traderItems.keySet()) {
                ship.putInventory(id, player.getNumForItem(id) + traderItems.get(id));
            }
            double finalPrice = this.getNegogiatedPrice();
            if (player.getCredit() >= finalPrice) {
                player.setCredit(player.getCredit() - this.getNegogiatedPrice());
                AlertBox a = new AlertBox("WARNING", "WARNING", "Items bought successfully");
                a.showAlert();

                return 1;
            } else {
                AlertBox a = new AlertBox("WARNING", "WARNING",
                        "Credits not enough and choose another option!");
                a.showAlert();
                return 0;
            }
        }
    }

    public void handleIgnore() {
        AlertBox a = new AlertBox("WARNING", "WARNING",
                "Ignored and will continue to destination");
        a.showAlert();
    }

    public void handleRob(Ship ship, Player player) {
        if (player.getFighterPoints() >= 2) {
            Random rand = new Random();
            for (int id: traderItems.keySet()) {
                if (traderItems.get(id) <= 0) {
                    continue;
                }
                int randForItem = rand.nextInt(traderItems.get(id));
                if (ship.getCurrLoad() + randForItem <= ship.getCargoCapacity()) {
                    ship.putInventory(id, player.getNumForItem(id) + randForItem);
                }
            }
            AlertBox a = new AlertBox("WARNING", "WARNING", "Robbed items successfully");
            a.showAlert();
        } else {
            ship.setHealth(ship.getCurrHealth() - 10);
            AlertBox a = new AlertBox("WARNING", "WARNING", "Rob failed; health damaged");
            a.showAlert();
        }
    }

    public Integer handleNegotiate(Player player) {
        if (this.getNegotiated()) {
            AlertBox a = new AlertBox("WARNING", "WARNING", "Can't negotiate any more");
            a.showAlert();
            return 0;
        } else {
            this.setNegotiated();
            if (player.getMerchantPoints() >= 2) {
                this.setNegotiatedResult(true);
                AlertBox a = new AlertBox("WARNING", "WARNING",
                        "Negotiation succuss! Price lowered");
                a.showAlert();
            } else {
                this.setNegotiatedResult(false);
                AlertBox a = new AlertBox("WARNING", "WARNING",
                        "Negotiation failure! Price increased");
                a.showAlert();
            }

            return 1;
        }
    }

}
