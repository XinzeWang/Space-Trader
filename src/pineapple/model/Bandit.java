package pineapple.model;

import pineapple.AlertBox;
import java.util.HashMap;
import java.util.Random;

public class Bandit implements fightableNPC {

    //todo: Add specific numbers @XZW

    public Bandit() {
    }

    public void handlePay(Player player) {
        Random random = new Random();
        int needPay = random.nextInt(30);
        if (player.getCredit() > needPay) {
            player.setCredit(player.getCredit() - needPay);
            AlertBox a = new AlertBox("WARNING", "WARNING",
                    "You paid, please click OK!");
            a.showAlert();
        } else if (!player.getMyShip().getInventory().isEmpty()) {
            player.getMyShip().setInventory(new HashMap<Integer, Integer>());
            AlertBox a = new AlertBox("WARNING", "WARNING",
                    "Credit not enough, empty inventory, please click OK!");
            a.showAlert();
        } else {
            player.getMyShip().setHealth(player.getMyShip().getCurrHealth() - needPay);
            AlertBox a = new AlertBox("WARNING", "WARNING",
                    "Ship damaged, please click OK!");
            a.showAlert();
        }
    }

    @Override
    public int handleFleeBack(Player player) {
        Random random = new Random();
        int fleeThreshold = random.nextInt(10);
        if (player.getPilotPoints() > fleeThreshold) {
            //back to original
            AlertBox a = new AlertBox("WARNING", "WARNING",
                    "Flee successfully, please click OK!");
            a.showAlert();
            return 1;
        } else {
            player.setCredit(0.0);
            if (!player.getMyShip().getDamage(fleeThreshold)) {
                // game over
                // TODO implement game over screen
                return -1;
            } else {
                AlertBox a = new AlertBox("WARNING", "WARNING",
                        "Flee Failed,credit robbed, "
                                + "ship get damage, please click OK!");
                a.showAlert();
                return 0;
            }
        }
    }

    @Override
    public int handleFight(Player player) {
        Random random = new Random();
        int npcFighter = random.nextInt(10);
        if (player.getFighterPoints() > npcFighter) {
            player.setCredit(player.getCredit() + npcFighter);
            AlertBox a = new AlertBox("WARNING", "WARNING",
                    "You win, earn credits, please click OK!");
            a.showAlert();
            return 1;
        } else {
            player.setCredit(0.0);
            if (!player.getMyShip().getDamage(npcFighter)) {
                // game over
                return -1;
            } else {
                AlertBox a = new AlertBox("WARNING", "WARNING",
                        "Fight Failed, credit robbed, ship get damage, please click OK!");
                a.showAlert();
                return 0;
            }
        }
    }
}
