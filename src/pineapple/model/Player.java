package pineapple.model;

import java.util.HashMap;

import static pineapple.ShipManager.getShipByID;


public class Player {

    private String characterName;
    private String difficulty;
    private Integer pilotPoints;
    private Integer fighterPoints;
    private Integer merchantPoints;
    private Integer engineerPoints;
    private Integer basicPilotPoints;
    private Integer basicFighterPoints;
    private Integer basicMerchantPoints;
    private Integer basicEngineerPoints;
    private Double credit;
    private Ship myShip;


    public Player() {
        this.pilotPoints = 0;
        this.fighterPoints = 0;
        this.merchantPoints = 0;
        this.engineerPoints = 0;
        this.credit = 0.0;
        this.basicEngineerPoints = 0;
        this.basicFighterPoints = 0;
        this.basicMerchantPoints = 0;
        this.basicPilotPoints = 0;
        myShip = getShipByID(0);
    }


    public Player(String characterName, String difficulty,
                  Integer pilotPoints, Integer fighterPoints,
                  Integer merchantPoints, Integer engineerPoints) {
        this.characterName = characterName;
        this.difficulty = difficulty;
        this.pilotPoints = pilotPoints;
        this.fighterPoints = fighterPoints;
        this.merchantPoints = merchantPoints;
        this.engineerPoints = engineerPoints;
        this.basicMerchantPoints = merchantPoints;
        this.basicPilotPoints = pilotPoints;
        this.basicFighterPoints = fighterPoints;
        this.basicEngineerPoints = engineerPoints;
        myShip = getShipByID(0);
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Double setCreditByDif(String dif) {
        switch (dif) {
        case "Easy":
            credit = 100.0;
            break;
        case "Normal":
            credit = 75.0;
            break;
        case "Difficult":
            credit = 50.0;
            break;
        case "Hell":
            credit = 25.0;
            break;
        default:
        }
        return credit;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getPilotPoints() {
        return pilotPoints;
    }

    public void setPilotPoints(Integer pilotPoints) {
        this.pilotPoints = pilotPoints;
    }

    public Integer getFighterPoints() {
        return fighterPoints;
    }

    public void setFighterPoints(Integer fighterPoints) {
        this.fighterPoints = fighterPoints;
    }

    public Integer getMerchantPoints() {
        return merchantPoints;
    }

    public void setMerchantPoints(Integer merchantPoints) {
        this.merchantPoints = merchantPoints;
    }

    public Integer getEngineerPoints() {
        return engineerPoints;
    }

    public void setEngineerPoints(Integer engineerPoints) {
        this.engineerPoints = engineerPoints;
    }

    public Integer getBasicPilotPoints() {
        return basicPilotPoints;
    }

    public Integer getBasicFighterPoints() {
        return basicFighterPoints;
    }

    public Integer getBasicMerchantPoints() {
        return basicMerchantPoints;
    }

    public Integer getBasicEngineerPoints() {
        return basicEngineerPoints;
    }

    public void setBasicPilotPoints(Integer basicPilotPoints) {
        this.basicPilotPoints = basicPilotPoints;
    }

    public void setBasicFighterPoints(Integer basicFighterPoints) {
        this.basicFighterPoints = basicFighterPoints;
    }

    public void setBasicMerchantPoints(Integer basicMerchantPoints) {
        this.basicMerchantPoints = basicMerchantPoints;
    }

    public void setBasicEngineerPoints(Integer basicEngineerPoints) {
        this.basicEngineerPoints = basicEngineerPoints;
    }

    public void setMyShip(Ship myShip) {
        this.myShip = myShip;
    }

    public Ship getMyShip() {
        return myShip;
    }

    public void resetAllPoints() {
        this.pilotPoints = 0;
        this.fighterPoints = 0;
        this.merchantPoints = 0;
        this.engineerPoints = 0;
        this.basicEngineerPoints = 0;
        this.basicFighterPoints = 0;
        this.basicPilotPoints = 0;
        this.basicMerchantPoints = 0;
    }

    //TODO need to move those methods to Game
    //get the num for a certain item
    public Integer getNumForItem(Integer itemId) {
        HashMap<Integer, Integer> myGoods = myShip.getInventory();
        return myGoods.getOrDefault(itemId, 0);
    }

    public Double getAdjustedBuyPrice(double price) {
        if (merchantPoints <= 3) {
            return price;
        } else if (merchantPoints <= 5) {
            return price * 0.9;
        } else {
            return price * 0.8;
        }
    }

    public Double getAdjustedSellPrice(double price) {
        if (merchantPoints <= 3) {
            return price;
        } else if (merchantPoints <= 5) {
            return price * 1.1;
        } else {
            return price * 1.2;
        }
    }

    public void reInitialize() {
        characterName = null;
        pilotPoints = 0;
        fighterPoints = 0;
        merchantPoints = 0;
        engineerPoints = 0;
        basicPilotPoints = 0;
        basicFighterPoints = 0;
        basicMerchantPoints = 0;
        basicEngineerPoints = 0;
        credit = 0.0;
    }
}
