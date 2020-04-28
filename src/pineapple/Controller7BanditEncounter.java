package pineapple;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import pineapple.model.*;

import java.io.IOException;

public class Controller7BanditEncounter {

    private Game game;
    private Player player;
    private Bandit bandit;
    private boolean selected;

    //    @FXML
    //    private Button back;
    @FXML
    private Button Flee;
    @FXML
    private Button Fight;
    @FXML
    private Button Pay;


    public void initialize() {
        this.game = Main.game;
        this.player = game.getPlayer();
        bandit = new Bandit();
        selected = false;
    }

    // TODO add credits the bandit ask for; current credit user has;
    //  current ship health; current items; fuel

    public void payCredit(ActionEvent actionEvent) throws IOException {
        if (!selected) {
            bandit.handlePay(player);
            selected = true;
            Stage stage0 = (Stage) Pay.getScene().getWindow();
            stage0.close();
            enterRegion(game.getNextReg());
        } else {
            AlertBox a = new AlertBox("WARNING", "WARNING",
                    "You already made choice, please click back!");
            a.showAlert();
        }

    }

    public void fleeBack(ActionEvent actionEvent) throws IOException {
        if (!selected) {
            Stage stage0 = (Stage) Flee.getScene().getWindow();
            stage0.close();
            int res = bandit.handleFleeBack(player);
            if (res == 1) {
                //todo : implement go back to prev
                enterRegion(game.getCurReg());
            } else if (res == 0) {
                enterRegion(game.getNextReg());
            } else {
                gameOver();
            }

            selected = true;
        } else {
            AlertBox a = new AlertBox("WARNING", "WARNING",
                    "You already made choice, please click back!");
            a.showAlert();
        }

    }

    public void fightAgainst(ActionEvent actionEvent) throws IOException {
        if (!selected) {
            Stage stage0 = (Stage) Fight.getScene().getWindow();
            stage0.close();
            int res = bandit.handleFight(player);
            if (res == 1 || res == 0) {
                enterRegion(game.getNextReg());
            } else {
                gameOver();
            }
            selected = true;

        } else {
            AlertBox a = new AlertBox("WARNING", "WARNING",
                    "You already made choice, please click back!");
            a.showAlert();
        }

    }

    public void enterRegion(Region region) throws IOException {
        if (region == null) {
            Stage stage = (Stage) Pay.getScene().getWindow();
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
            Stage stage = (Stage) Pay.getScene().getWindow();
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
