package pineapple;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import pineapple.model.Game;
import pineapple.model.Player;

import java.net.URL;

public class Main extends Application {
    static Game game;

    @Override
    public void start(Stage primaryStage) throws Exception {
        ItemManager.init();
        MarketManager.init();
        RegionManager.init();
        ShipManager.init();
        game = new Game(new Player());


        final URL resource = getClass().getResource("resource/music.mp3");
        final Media media = new Media(resource.toString());

        Parent root = FXMLLoader.load(getClass().getResource("Screen0Welcome.fxml"));
        primaryStage.setTitle("Team49_SpaceTrader");
        //@XZW
        Scene scene = new Scene(root, 500, 475);
        scene.getStylesheets().add(getClass().getResource("CSS/Screen0.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
