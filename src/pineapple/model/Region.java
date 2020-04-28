package pineapple.model;

import java.util.concurrent.ThreadLocalRandom;

public class Region {
    private boolean visited = false;
    private String name;
    private String techLevel;
    private String description;
    private double[] location = new double[2];
    private MarketPlace marketPlace;
    //private NPC npc;

    // @XZW new constructor with default marketPlace
    public Region(boolean visited, String name, String techLevel,
                  String description) {
        this.visited = visited;
        this.name = name;
        this.techLevel = techLevel;
        this.description = description;
        double randomX = ThreadLocalRandom.current().nextDouble(80, 720);
        double randomY = ThreadLocalRandom.current().nextDouble(40, 460);
        this.location = new double[]{randomX, randomY};
        this.marketPlace = new MarketPlace();
        System.out.println("Name: " + this.name + "/nLocation: "
                + this.location[0] + " " + this.location[1] + " ");
    }

    /* Utils */
    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String getName() {
        if (visited) {
            return name;
        } else {
            return "unknown";
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTechLevelForPrice() {
        return techLevel;
    }

    public String getTechLevel() {
        if (visited) {
            return techLevel;
        } else {
            return "unknown";
        }
    }

    public void setTechLevel(String techLevel) {
        this.techLevel = techLevel;
    }

    public String getDescription() {
        if (visited) {
            return description;
        } else {
            return "unknown";
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MarketPlace getMarketPlace() {
        return marketPlace;
    }

    public void setMarketPlace(MarketPlace marketPlace) {
        this.marketPlace = marketPlace;
    }

}
