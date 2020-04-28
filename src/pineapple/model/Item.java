package pineapple.model;

public class Item {

    //public static HashMap<Integer, Item> itemMap = new HashMap<>();

    private int itemId;
    private String itemName;
    private int basePrice;
    private String minTechReq;

    public Item(int itemId, String itemName, int basePrice, String minTechReq) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.basePrice = basePrice;
        this.minTechReq = minTechReq;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return basePrice;
    }

    public void setItemValue(int basePrice) {
        this.basePrice = basePrice;
    }

    public String getMinTechReq() {
        return minTechReq;
    }

    public void setMinTechReq(String minTechReq) {
        this.minTechReq = minTechReq;
    }


}
