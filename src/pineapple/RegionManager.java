package pineapple;

import pineapple.model.MarketPlace;
import pineapple.model.Region;

import java.util.HashMap;
import java.util.Random;

public class RegionManager {
    //@XYZ
    private static HashMap<String, Region> regionMap = new HashMap<>();

    public static void init() {
        Region region1 = new Region(false, "Goliath", "Low", "A region "
                + "full of giants, where science and technology " + "\n"
                + "are highly developed and abundant in resources");
        Region region2 = new Region(false, "David", "Low", "A region "
                + "full of goblins, where science and technology " + "\n"
                + "are highly developed,but lack of resources");
        Region region3 = new Region(false, "Eve", "Low", "A region "
                + "full of challenges but opportunities " + "\n"
                + "always come with challenges, isn't it?");
        Region region4 = new Region(false, "Villanelle", "Low", "A region "
                + "full of precious deposits but only the top " + "\n"
                + "talented players are able to find them");

        Region region5 = new Region(false, "Miller", "Medium", "A region "
                + "contains a substantial amount of water at its surface" + "\n"
                + "with limited resources");

        Region region6 = new Region(false, "Mann", "Medium", " A region "
                + "has a nearby black hole so time is severely dilated" + "\n"
                + "in this region");

        Region region7 = new Region(false, "Fire", "Medium", " A region "
                + "is so hot that only limited number of animals" + "\n"
                + "can live in this region");

        Region region8 = new Region(false, "Gold", "High", " A region "
                + "very rich that attract all the travellers" + "\n"
                + "in the universe");

        Region region9 = new Region(false, "Edward", "High", "A region "
                + "full of trading opportunities " + "\n"
                + "trading wisely will give you great profit");

        Region region10 = new Region(false, "Crystal", "High", "A region "
                + "full of unique offers and missions " + "\n"
                + "smart player may earn rewards from some of missions");
        //@XYZ

        HashMap<String, MarketPlace> marketMap = MarketManager.getMarketMap();
        region1.setMarketPlace(marketMap.get("market1"));
        region2.setMarketPlace(marketMap.get("market2"));
        region3.setMarketPlace(marketMap.get("market3"));
        region4.setMarketPlace(marketMap.get("market4"));
        region5.setMarketPlace(marketMap.get("market5"));
        region6.setMarketPlace(marketMap.get("market6"));
        region7.setMarketPlace(marketMap.get("market7"));
        region8.setMarketPlace(marketMap.get("market8"));
        region9.setMarketPlace(marketMap.get("market9"));
        region10.setMarketPlace(marketMap.get("market10"));

        regionMap.put("region1", region1);
        regionMap.put("region2", region2);
        regionMap.put("region3", region3);
        regionMap.put("region4", region4);
        regionMap.put("region5", region5);
        regionMap.put("region6", region6);
        regionMap.put("region7", region7);
        regionMap.put("region8", region8);
        regionMap.put("region9", region9);
        regionMap.put("region10", region10);

        //change the priceMultiplier for each region
        for (Region r : regionMap.values()) {
            // String myTechLevel = r.getTechLevel();
            MarketPlace myMarket = r.getMarketPlace();
            if (r.getTechLevelForPrice() == "Medium") {
                myMarket.setPriceMultiplier(1.2);
                r.setMarketPlace(myMarket);
            } else if (r.getTechLevelForPrice() == "High") {
                myMarket.setPriceMultiplier(1.5);
                r.setMarketPlace(myMarket);
            }
            System.out.println(myMarket.getPriceMultiplier());
        }
    }

    public static Region getRegionByID(String regID) {
        return regionMap.get(regID);
    }

    public static HashMap getRegionMap() {
        return regionMap;
    }


}
