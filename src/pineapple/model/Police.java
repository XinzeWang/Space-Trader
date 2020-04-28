package pineapple.model;

import pineapple.AlertBox;
import pineapple.ItemManager;

import java.util.HashMap;
import java.util.Random;

public class Police implements fightableNPC {
    private HashMap<Integer, Item> itemMap = ItemManager.getItemMap();
    private HashMap<Integer, Integer> stolenItems = new HashMap<>();
    private int npcFighter;
    private int npcPilot;

    public Police() {
        Random random = new Random();
        npcFighter = random.nextInt(10);
        npcPilot = random.nextInt(10);
    }

    public HashMap<Integer, Integer> getStolenItems() {
        return stolenItems;
    }

    public void assignStolen(Ship ship) {
        //TODO randomly assign items to be stolen
        HashMap<Integer, Integer> shipItems = ship.getInventory();
        for (Integer id : shipItems.keySet()) {
            if (shipItems.get(id) > 0) {
                int randNum = (int) (Math.random() * shipItems.get(id));
                if (randNum > 0) {
                    stolenItems.put(id, randNum);
                } else if (randNum == 0) {
                    shipItems.put(id, 1); // so that at least one is stolen
                }
            }
        }
    }

    public String getStolenItemsAndPrice() {
        StringBuilder sb = new StringBuilder();
        //sb.append("The police believe you stole: ");

        for (Integer id : stolenItems.keySet()) {
            sb.append(itemMap.get(id).getItemName());
            sb.append(": ");
            sb.append(stolenItems.get(id));
            sb.append("; ");
        }

        return sb.toString();
    }

    private void confiscate(Player player) {
        //TODO for every stolen item, corresponding #item - num;
        HashMap<Integer, Integer> shipItems = player.getMyShip().getInventory();
        for (Integer id : stolenItems.keySet()) {
            shipItems.put(id, shipItems.get(id) - stolenItems.get(id));
            if (shipItems.get(id) <= 0) {
                shipItems.remove(id);
            }
            player.getMyShip().setInventory(shipItems);
        }
    }

    public void handleForfeit(Player player) {
        confiscate(player);
        String info = "You forfeited" + getStolenItemsAndPrice();
        AlertBox a = new AlertBox("WARNING", "WARNING", info);
        a.showAlert();
    }

    @Override
    public int handleFleeBack(Player player) {
        if (player.getPilotPoints() > npcPilot) {
            //back to original
            // TODO lose fuel
            AlertBox a = new AlertBox("WARNING", "WARNING", "Lucky! Flee successfully");
            a.showAlert();
            return 1;
        } else {
            confiscate(player);
            forceFine(player, npcPilot);
            if (!player.getMyShip().getDamage(npcPilot)) {
                // game over
                return -1;
            } else {
                String info = String.format("Flee failed, submit %d credit fine,"
                        + "  ship has %d damage, return to previous region", npcPilot, npcPilot);
                AlertBox a = new AlertBox("WARNING", "WARNING", info);
                a.showAlert();
                return 0;
            }
        }
    }

    private void forceFine(Player player, int fine) {
        // a fine for evasion
        if (player.getCredit() < fine) {
            player.setCredit(0.0);
        } else {
            player.setCredit(player.getCredit() - fine);
        }
    }


    @Override
    public int handleFight(Player player) {
        if (player.getFighterPoints() > npcFighter) {
            player.setCredit(player.getCredit() + npcFighter);
            String info = String.format("You won, earn %d credi"
                    + "t fine, go to next region", npcFighter);
            AlertBox a = new AlertBox("WARNING", "WARNING", info);
            a.showAlert();
            return 1;
        } else {
            confiscate(player);
            forceFine(player, npcFighter);
            if (!player.getMyShip().getDamage(npcFighter)) {
                // game over
                return -1;
            } else {
                String info = String.format("You lose, submit %d credit fine,  "
                        + "ship has %d damage, return to previous region", npcFighter, npcFighter);
                AlertBox a = new AlertBox("WARNING", "WARNING", info);
                a.showAlert();
                return 0;
            }
        }
    }
}

