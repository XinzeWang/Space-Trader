package pineapple.model;

import java.util.HashMap;

public class Ship {
    private int shipId;
    private String shipName;
    private int cargoCapacity;
    private int fuelCapacity;
    private int healthCapacity;
    private int currHealth;
    private int currLoad;
    private int currFuel;
    private HashMap<Integer, Integer> inventory;

    public Ship(int shipId, String shipName, int cargoCapacity,
                int fuelCapacity, int healthCapacity) {
        this.shipId = shipId;
        this.shipName = shipName;
        this.cargoCapacity = cargoCapacity;
        this.fuelCapacity = fuelCapacity;
        this.currFuel = fuelCapacity;
        this.healthCapacity = healthCapacity;
        this.currHealth = healthCapacity;
        this.inventory = new HashMap<Integer, Integer>();
    }

    public int getShipId() {
        return shipId;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public String getShipName() {
        return shipName;
    }

    public int getCargoCapacity() {
        return cargoCapacity;
    }

    public int getFuelCapacity() {
        return fuelCapacity;
    }

    public int getHealthCapacity() {
        return healthCapacity;
    }

    public void setHealth(int health) {
        this.currHealth = health;
    }

    public HashMap<Integer, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(HashMap<Integer, Integer> inventory) {
        this.inventory = inventory;
    }

    public void putInventory(int itemId, int quantity) {
        this.inventory.put(itemId, quantity);
    }

    public int getCurrLoad() {
        currLoad = 0;
        for (int itemId : inventory.keySet()) {
            currLoad += inventory.get(itemId);
        }

        return currLoad;
    }

    public boolean isFull() {
        return this.getCurrLoad() >= cargoCapacity;
    }

    public boolean isEmpty() {
        return getCurrLoad() == 0;
    }

    public int getCurrFuel() {
        return currFuel;
    }

    public boolean comsumeFuel(int delta) {
        System.out.println("fuel consumption: " + delta);
        if (delta > this.currFuel) {
            return false;
        } else {
            this.currFuel -= delta;
            System.out.println("current fuel: " + currFuel);
            return true;
        }
    }

    public int getCurrHealth() {
        return currHealth;
    }

    //fill up directly
    public boolean refuel() {
        if (this.currFuel == this.fuelCapacity) {
            return false;
        } else {
            this.currFuel = this.fuelCapacity;
            return true;
        }
    }
    //get damaged
    public boolean getDamage(int delta) {
        System.out.println("Your ship is attacked for " + delta + " health");
        if (delta > this.currHealth) {
            System.out.println("Your ship is destroyed, game over");
            return false;
        } else {
            this.currHealth -= delta;
            System.out.println("Your current ship health is " + this.currHealth + " health");
            return true;
        }
    }
    // repair ship
    public boolean repair() {
        if (this.currHealth == this.healthCapacity) {
            return false;
        } else {
            this.currHealth = this.healthCapacity;
            return true;
        }

    }

}
