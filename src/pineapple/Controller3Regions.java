package pineapple;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pineapple.model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Controller3Regions {
    @FXML
    private Button visit;
    @FXML
    private Button universeMap;
    @FXML
    private Label name;
    @FXML
    private Label tech;
    @FXML
    private Label description;
    @FXML
    private Button confirmId;
    @FXML
    private Button sellThisItem;
    @FXML
    private TextField chosenItemId;
    @FXML
    private TextField itemToSell;

    @FXML
    private Button toNearby;
    @FXML
    private Button toPrevious;
    @FXML
    private Button button1;
    //@ZKQ
    @FXML
    private Canvas marketCanvas;
    //@XYZ
    @FXML
    private GridPane marketplace;
    @FXML
    private VBox chosenItemV;
    @FXML
    private HBox chooseIdH;
    @FXML
    private GraphicsContext gc;

    private Game game;
    private Player player;
    private Region r;
    private Region prevR;
    private MarketPlace market;
    private Ship myCurrShip;
    private HashMap<Integer, Integer> marketItems;
    private HashMap<Integer, Integer> inventory;
    private Stack<Region> visitedRegs;
    private HashMap<Integer, Item> itemMap;
    private HashMap<String, Region> regionMap;

    public Controller3Regions(Region r) {
        this.game = Main.game;
        this.player = game.getPlayer();
        this.r = r;
        market = r.getMarketPlace();
        myCurrShip = player.getMyShip();
        marketItems = market.getMarketItems();
        inventory = myCurrShip.getInventory();
        visitedRegs = game.getVisitedRegs();
        itemMap = ItemManager.getItemMap();
        regionMap = RegionManager.getRegionMap();
    }

    public void initialize() throws IOException {
        String rName = r.getName();
        String tLevel = r.getTechLevel();
        String de = r.getDescription();
        name.setText(rName);
        tech.setText(tLevel);
        description.setText(de);
        description.setWrapText(true);
    }

    // this is the key function for market place; it is dynamic
    public void showMarketPlace(Region r) throws FileNotFoundException {

        // set a grid pane for the marketplace
        marketplace.getChildren().clear();
        marketplace.setPadding(new Insets(25, 25, 25, 25));
        Text head1 = new Text("Items");
        Text head2 = new Text("Id");
        Text head3 = new Text("Base Price");
        Text head4 = new Text("Buy Price");
        Text head5 = new Text("Sell Price");
        Text head6 = new Text("Market has");
        Text head7 = new Text("You have");
        marketplace.add(head1, 0, 0);
        marketplace.add(head2, 1, 0);
        marketplace.add(head3, 2, 0);
        marketplace.add(head4, 3, 0);
        marketplace.add(head5, 4, 0);
        marketplace.add(head6, 5, 0);
        marketplace.add(head7, 6, 0);

        // @XZW initial version, could be improved
        for (Integer key : marketItems.keySet()) {
            addItem(key);
        }
    }

    public void showChooseIdH() {
        // make a HBox to choose itemId; not dynamic
        chooseIdH.getChildren().removeAll(chooseIdH.getChildren());
        chooseIdH.setSpacing(10);
        Text enterIdText = new Text("ItemId: ");
        enterIdText.prefHeight(50);
        enterIdText.prefWidth(50);
        TextField enterIdTF = new TextField();
        enterIdTF.setPromptText("Enter ItemId");
        enterIdTF.setPrefHeight(30);
        enterIdTF.setPrefWidth(70);
        Button confirmBut = new Button();
        confirmBut.setText("Confirm");
        confirmBut.prefHeight(30);
        confirmBut.prefWidth(50);
        confirmBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String cId = enterIdTF.getText();
                if (cId.equals("") || cId == null) {
                    AlertBox a = new AlertBox("WARNING", "WARNING", "ItemId can not be null!");
                    a.showAlert();
                } else {
                    try {
                        Integer.parseInt(cId);
                    } catch (NumberFormatException nfe) {
                        AlertBox a = new AlertBox("WARNING", "WARNING", "Please input an integer!");
                        a.showAlert();
                    }
                    int chosenId = Integer.parseInt(cId);
                    if (!marketItems.containsKey(chosenId)) {
                        AlertBox a = new AlertBox("WARNING", "WARNING",
                                "Please choose itemId the market has!");
                        a.showAlert();
                    } else {
                        System.out.println(chosenId);
                        showChosenItem(chosenId);
                    }
                }
            }
        });
        chooseIdH.getChildren().add(enterIdText);
        chooseIdH.getChildren().add(enterIdTF);
        chooseIdH.getChildren().add(confirmBut);
    }

    public void addItem(Integer itemId) {
        Item item = itemMap.get(itemId);

        String itemName = item.getItemName();
        double basePrice = item.getItemPrice();
        double buyPrice = market.getBuyPrice(item);
        double sellPrice = market.getSellPrice(item);
        Integer numItemMarket = market.getMarketItems().get(itemId);
        Integer numItemPlayer = player.getNumForItem(itemId);

        Button itemButton = new Button();
        itemButton.setText(itemName);

        Text text1 = new Text(itemName);
        Text text2 = new Text(Integer.toString(item.getItemId()));
        Text text3 = new Text(Double.toString(basePrice));
        Text text4 = new Text(String.format("%.2f", buyPrice));
        Text text5 = new Text(String.format("%.2f", sellPrice));
        Text text6 = new Text(Integer.toString(numItemMarket));
        Text text7 = new Text(Integer.toString(numItemPlayer));

        marketplace.add(text1, 0, itemId);
        marketplace.add(text2, 1, itemId);
        marketplace.add(text3, 2, itemId);
        marketplace.add(text4, 3, itemId);
        marketplace.add(text5, 4, itemId);
        marketplace.add(text6, 5, itemId);
        marketplace.add(text7, 6, itemId);
    }

    // this part is dynamic
    public void showChosenItem(int chosenId) {
        chosenItemV.getChildren().removeAll(chosenItemV.getChildren());
        Item item = itemMap.get(chosenId);

        String itemName = item.getItemName();
        double buyPrice = market.getBuyPrice(item);
        double sellPrice = market.getSellPrice(item);
        double adjustedBuyPrice = player.getAdjustedBuyPrice(buyPrice);
        double adjustedSellPrice = player.getAdjustedSellPrice(sellPrice);
        double playerCredits = player.getCredit();

        Text chosenItemName = new Text("Item Name: " + itemName);
        Text chosenItemBuyPrice = new Text("buyPrice: "
                + String.format("%.2f", buyPrice));
        Text adjustedBuy = new Text("Adjusted buyPrice: "
                + String.format("%.2f", adjustedBuyPrice));
        Text chosenItemSellPrice = new Text("sellPrice: "
                + String.format("%.2f", sellPrice));
        Text adjustedSell = new Text("Adjusted buyPrice: "
                + String.format("%.2f", adjustedSellPrice));
        Text playerCredit = new Text("Player Credits: "
                + String.format("%.2f", playerCredits));
        chosenItemV.setSpacing(5);
        chosenItemV.getChildren().add(chosenItemName);
        chosenItemV.getChildren().add(chosenItemBuyPrice);
        chosenItemV.getChildren().add(adjustedBuy);
        chosenItemV.getChildren().add(chosenItemSellPrice);
        chosenItemV.getChildren().add(adjustedSell);
        chosenItemV.getChildren().add(playerCredit);

        HBox hbox = new HBox();
        hbox.setSpacing(10);

        Button buyBut = addBuyButton(chosenId);
        hbox.getChildren().add(buyBut);
        Button sellBut = addSellButton(chosenId);
        hbox.getChildren().add(sellBut);
        chosenItemV.getChildren().add(hbox);
    }

    public Button addBuyButton(int itemId) {
        Button buyButton = new Button();
        buyButton.setText("Buy");
        buyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    buy(itemId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return buyButton;
    }

    public void buy(Integer itemId) throws IOException {

        Item item = itemMap.get(itemId);
        if (myCurrShip.isFull()) {
            AlertBox a = new AlertBox("WARNING", "WARNING", "Ship is full");
            a.showAlert();
        } else if (market.getMarketItems().get(itemId) < 1) {
            AlertBox a = new AlertBox("WARNING", "WARNING", "No more items in the market!");
            a.showAlert();
        } else {
            double sellPrice = market.getBuyPrice(item);
            double buyPrice = player.getAdjustedBuyPrice(sellPrice);

            if (player.getCredit() >= buyPrice) {
                player.setCredit(player.getCredit() - buyPrice);
                myCurrShip.putInventory(itemId, player.getNumForItem(itemId) + 1);
                market.removeItem(itemId);

                AlertBox a = new AlertBox("WARNING", "WARNING", "Transaction successful");
                a.showAlert();

                if (itemId == 8) {
                    gameOver();
                }
            } else {
                AlertBox a = new AlertBox("WARNING", "WARNING", "Credit not enough");
                a.showAlert();
            }


        }
        showChosenItem(itemId); // will make a new
        showMarketPlace(r);
    }

    public Button addSellButton(int itemId) {
        Button sellButton = new Button();
        sellButton.setText("Sell");
        sellButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    sell(itemId);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        return sellButton;
    }

    public void sell(int itemId) throws FileNotFoundException {

        HashMap<Integer, Integer> myGoods = myCurrShip.getInventory();
        Item item = itemMap.get(itemId);
        double buyPrice = market.getBuyPrice(item);
        double sellPrice = player.getAdjustedSellPrice(buyPrice);

        if (myGoods.get(itemId) > 0) {
            player.setCredit(player.getCredit() + sellPrice);
            myCurrShip.putInventory(itemId, player.getNumForItem(itemId) - 1);
            market.addItem(itemId);
            AlertBox a = new AlertBox("WARNING", "WARNING", "Transaction successful");
            a.showAlert();
        } else {
            AlertBox a = new AlertBox("WARNING", "WARNING", "You don't have the item!");
            a.showAlert();
        }

        chosenItemV.getChildren().removeAll(chosenItemV.getChildren());
        showChosenItem(itemId); // will make a new
        showMarketPlace(r);
    }

    public void visitRegion(ActionEvent actionEvent) throws IOException {

        this.r.setVisited(true);
        if (visitedRegs.isEmpty() || !visitedRegs.peek().equals(this.r)) {
            game.pushVisitedRegs(this.r);
        }
        initialize();
        showMarketPlace(r);
        showChooseIdH();
    }

    //initial version: @XYZ
    //Added fuel consumption: @CZ
    public void toNearby(ActionEvent actionEvent) throws IOException {
        List<String> keysArray = new ArrayList<>(regionMap.keySet());
        double minDist = Integer.MAX_VALUE;
        double curDist = 0;
        Region nearByReg = this.r;
        Region otherReg;
        for (String regionID : keysArray) {
            if (regionMap.get(regionID).equals(this.r)) {
                continue;
            }
            otherReg = (Region) regionMap.get(regionID);
            curDist = this.getDistance(this.r, otherReg);
            if (minDist > curDist) {
                minDist = curDist;
                nearByReg = otherReg;
            }
        }

        //Before going to and setting nearByReg, check remaining fuel
        int fuelConsumption = (int) (getDistance(r, nearByReg)
                * Math.exp(player.getPilotPoints() * -0.01));
        if (!myCurrShip.comsumeFuel(fuelConsumption)) {
            // fuel is not enough for this travel
            AlertBox a = new AlertBox("WARNING", "Lack of fuel!",
                    "Surplus fuel can not support this fight");
            a.showAlert();
            return;
        }

        game.setCurReg(r);
        game.setNextReg(nearByReg);
        //encounter different NPC for different range
        //feel free to change

        encounterNPC(nearByReg);

    }

    public static double getDistance(Region a, Region b) {
        return Math.sqrt((a.getLocation()[0] - b.getLocation()[0])
                * (a.getLocation()[0] - b.getLocation()[0])
                + (a.getLocation()[1] - b.getLocation()[1])
                * (a.getLocation()[1] - b.getLocation()[1]));
    }

    //first version: @XYZ
    //Added fuel consumption: @CZ
    public void toPrevious(ActionEvent actionEvent) throws IOException {

        if (visitedRegs.isEmpty()) {
            AlertBox a = new AlertBox("INFORMATION", "No Previous Region",
                    "No previous region found!");
            a.showAlert();
            return;
        }

        this.prevR = visitedRegs.isEmpty() ? null : visitedRegs.peek();

        if (visitedRegs.peek().equals(this.r)) {
            if (visitedRegs.size() == 1) {
                AlertBox a = new AlertBox("INFORMATION", "No Previous Region",
                        "No previous region found!");
                a.showAlert();
                return;
            } else {
                game.popVisitedRegs();
                this.prevR = game.peekVisitedRegs();
                System.out.println("not current region");
            }
        }

        Region curP = this.prevR;
        this.prevR = game.popVisitedRegs();

        //Before going to and setting previous region, check remaining fuel
        int fuelConsumption = (int) (getDistance(r, prevR)
                * Math.exp(player.getPilotPoints() * -0.01));
        if (!myCurrShip.comsumeFuel(fuelConsumption)) {
            // fuel is not enough for this travel
            AlertBox a = new AlertBox("WARNING", "Lack of fuel!",
                    "Surplus fuel can not support this fight");
            a.showAlert();
            return;
        }
        game.setCurReg(r);
        game.setNextReg(prevR);
        //@QZK add encounter NPC
        encounterNPC(curP);
    }

    public void goEquipment(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) universeMap.getScene().getWindow();
        Parent myNewscene = FXMLLoader.load(getClass().getResource("Screen6Equipment.fxml"));
        Scene scene = new Scene(myNewscene, 800, 520);
        stage.setScene(scene);
        stage.setTitle("Equipment");
        stage.show();
    }

    public void goMap(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) universeMap.getScene().getWindow();
        Parent myNewscene = FXMLLoader.load(getClass().getResource("Screen4UniverseMap.fxml"));
        Scene scene = new Scene(myNewscene, 800, 520);
        stage.setScene(scene);
        stage.setTitle("Universe Map");
        //@CZ apply css configuration to Screen 4
        scene.getStylesheets()
                .add(getClass()
                        .getResource("Screen4UniverseMap.css")
                        .toExternalForm());
        stage.show();
    }

/*---------Utils-------*/

    // initial version: @XZW
    private void encounterNPC(Region r) throws IOException {
        int difficultyLevel = 0;
        switch (player.getDifficulty()) {
        case "Easy":
            difficultyLevel = 1;
            break;
        case "Normal":
            difficultyLevel = 2;
            break;
        case "Difficult":
            difficultyLevel = 3;
            break;
        case "Hell":
            difficultyLevel = 4;
            break;
        default:
        }
        Random random = new Random();
        int randomInt = random.nextInt(100);
        if (randomInt >= 0 && randomInt <= 10 && !myCurrShip.isEmpty()) { // must have items
            interactPolice();
        } else if (randomInt >= 10 && randomInt < 20) {
            interactTrader();
        } else if (randomInt >= 20 && randomInt < (20 + 5 * difficultyLevel)) {
            interactBandit();
        } else {
            keepGoing(r);
        }
    }

    private void interactBandit() throws IOException {
        interactNPC("Screen7BanditEncounter.fxml", "Bandit");
    }

    private void interactTrader() throws IOException {
        interactNPC("Screen8TraderEncounter.fxml", "Trader");
    }

    private void interactPolice() throws IOException {
        interactNPC("Screen9PoliceEncounter.fxml", "Police");
    }

    private void interactNPC(String screenName, String NPCType) throws IOException {
        Parent myNewscene = FXMLLoader.load(getClass().getResource(screenName));
        Stage stage = new Stage();
        stage.setTitle("You Encounter a " + NPCType);
        Scene scene = new Scene(myNewscene, 800, 520);
        stage.setScene(scene);
        Stage oldStage = (Stage) universeMap.getScene().getWindow();
        oldStage.close();
        stage.show();
    }

    private void keepGoing(Region r) throws IOException {
        Controller3Regions controller = new Controller3Regions(r);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Screen3Region.fxml"));
        Stage stage = new Stage();
        this.initialize();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root, 800, 520);
        Stage oldStage = (Stage) universeMap.getScene().getWindow();
        oldStage.close();
        stage.setScene(scene);
        stage.setTitle("Region");
        stage.show();
    }

    public void gameOver() throws IOException {
        Parent myNewscene = FXMLLoader.load(getClass().getResource("Screen10GameOver.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Game Over");
        Scene scene = new Scene(myNewscene, 800, 520);
        stage.setScene(scene);
        Stage oldStage = (Stage) toNearby.getScene().getWindow();
        oldStage.close();
        stage.show();
    }

}
