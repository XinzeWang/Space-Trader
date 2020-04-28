package pineapple;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import pineapple.model.Game;
import pineapple.model.Player;

import java.io.IOException;

public class Controller10GameOver {
    @FXML
    private Button restart;
    @FXML
    private Button exit;

    public void restart(ActionEvent actionEvent) throws IOException {
        ItemManager.init();
        MarketManager.init();
        RegionManager.init();
        ShipManager.init();
        Main.game = new Game(new Player());

        Stage stage = null;
        Parent myNewscene = null;

        stage = (Stage) restart.getScene().getWindow();
        myNewscene = FXMLLoader.load(getClass().getResource("Screen1Initconf.fxml"));

        Scene scene = new Scene(myNewscene, 800, 520);
        scene.getStylesheets().add(getClass().getResource("CSS/Screen1.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Initial configuration");
        stage.show();
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

}