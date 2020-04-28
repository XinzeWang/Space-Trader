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

public class Controller9PoliceEncounter {
    @FXML
    private Label itemInfo;
    @FXML
    private Label playerCredit;
    @FXML
    private Label negotiatedPrice;
    @FXML
    private Label originalPrice;
    @FXML
    private Button Forfeit;
    @FXML
    private Button Flee;
    @FXML
    private Button Fight;

    private Game game;
    private Player player;
    String playerCredits;
    private Police police;
    private Ship ship;
    HashMap<Integer, Integer> shipInventory;
    private HashMap<Integer, Integer> stolenItems;

    public void initialize() throws IOException{

        game = Main.game;
        player = game.getPlayer();
        playerCredits = String.valueOf(player.getCredit());
        police = new Police();
        ship = player.getMyShip();
        police.assignStolen(ship);
        shipInventory = ship.getInventory();
        stolenItems = police.getStolenItems();

        playerCredit.setText("player has credit: " + playerCredits);
        itemInfo.setText("The police believe you stole: " + police.getStolenItemsAndPrice());
        itemInfo.setWrapText(true);
    }

    public void handleForfeit() throws IOException {
        police.handleForfeit(player);
        Stage stage0 = (Stage) Forfeit.getScene().getWindow();
        stage0.close();
        enterRegion(game.getNextReg());
    }

    public void handleFleeBack() throws IOException {
        int res = police.handleFleeBack(player);
        if (res == 1) {
            enterRegion(game.getCurReg());
        } else if (res == 0) {
            enterRegion(game.getNextReg());
        } else {
            gameOver();
        }
        Stage stage0 = (Stage) Flee.getScene().getWindow();
        stage0.close();
    }

    public void handleFight() throws IOException {
        int res = police.handleFight(player);
        Stage stage0 = (Stage) Fight.getScene().getWindow();
        stage0.close();
        if (res == 1 || res == 0) {
            enterRegion(game.getNextReg());
        } else {
            gameOver();
        }
    }

    public void enterRegion(Region region) throws IOException {
        if (region == null) {
            Stage stage = new Stage();
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
            Stage stage = new Stage();
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

    public void gameOver() throws IOException {
        Parent myNewscene = FXMLLoader.load(getClass().getResource("Screen10GameOver.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Game Over");
        Scene scene = new Scene(myNewscene, 800, 520);
        stage.setScene(scene);
        Stage oldStage = (Stage) Flee.getScene().getWindow();
        oldStage.close();
        stage.show();
    }
}

