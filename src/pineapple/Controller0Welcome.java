package pineapple;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class Controller0Welcome {

    @FXML
    private Button startGame;

    public void startGame(ActionEvent e) throws IOException {

        Stage stage = null;
        Parent myNewscene = null;

        stage = (Stage) startGame.getScene().getWindow();
        myNewscene = FXMLLoader.load(getClass().getResource("Screen1Initconf.fxml"));

        Scene scene = new Scene(myNewscene, 800, 520);
        scene.getStylesheets().add(getClass().getResource("CSS/Screen1.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Initial configuration");
        stage.show();
    }
}
