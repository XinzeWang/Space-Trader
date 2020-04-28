package pineapple;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pineapple.model.*;

import java.io.IOException;
import java.util.HashMap;

import pineapple.model.Game;
import pineapple.model.Player;

public class Controller8TraderEncounter {
    @FXML
    private Label itemInfo;
    @FXML
    private Label playerCredit;
    @FXML
    private Label negotiatedPrice;
    @FXML
    private Label originalPrice;
    @FXML
    private Button Buy;
    @FXML
    private Button Ignore;
    @FXML
    private Button Rob;
    @FXML
    private Button Negotiate;

    private Game game;
    private Player player;
    String playerCredits;
    private Trader trader;
    private Ship ship;
    private HashMap<Integer, Integer> traderItems;

    public void initialize() {
        game = Main.game;
        player = game.getPlayer();
        playerCredits = String.valueOf(player.getCredit());
        trader = new Trader();
        ship = player.getMyShip();
        traderItems = trader.getTraderItems();

        playerCredit.setText("player has credit: " + playerCredits);
        itemInfo.setText(trader.getItemsAndPriceForTrade());
        itemInfo.setWrapText(true);
        originalPrice.setText("Original price: " + trader.getTotalPrice());
        negotiatedPrice.setText("Negotiated price: no negotiation yet");
    }

    public void handleBuy() throws IOException {
        //handle player buy items
        int res = trader.handleBuy(ship, player);
        if (res == 1) {
            playerCredit.setText(String.valueOf(player.getCredit()));
            Stage stage0 = (Stage) Buy.getScene().getWindow();
            stage0.close();
            enterRegion(game.getNextReg());
        }
    }

    public void handleIgnore() throws IOException {
        trader.handleIgnore();
        Stage stage0 = (Stage) Ignore.getScene().getWindow();
        stage0.close();
        enterRegion(game.getNextReg());
    }

    public void handleRob() throws IOException {
        trader.handleRob(ship, player);
        Stage stage0 = (Stage) Rob.getScene().getWindow();
        stage0.close();
        enterRegion(game.getNextReg());
    }

    public void handleNegotiate() {
        int res = trader.handleNegotiate(player);
        if (res == 1) {
            String priceAfterNegotiation = String.valueOf(trader.getNegogiatedPrice());
            negotiatedPrice.setText("negotiated price: " + priceAfterNegotiation);
        }
    }

    public void enterRegion(Region region) throws IOException {
        if (region == null) {
            Stage stage = (Stage) Buy.getScene().getWindow();
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
        } else {
            Stage stage = (Stage) Buy.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Screen3Region.fxml"));
            //enter a random region
            Controller3Regions controller = new Controller3Regions(region);

            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root, 800, 520);
            stage.setScene(scene);
            stage.setTitle("Region");
            stage.show();
        }

    }
}
