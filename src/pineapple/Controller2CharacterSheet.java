package pineapple;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pineapple.model.Player;
import pineapple.model.Region;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static pineapple.RegionManager.getRegionMap;

public class Controller2CharacterSheet {
    @FXML
    private Label pilotPoints;
    @FXML
    private Label fighterPoints;
    @FXML
    private Label merchantPoints;
    @FXML
    private Label engineerPoints;
    @FXML
    private Label characterName;
    @FXML
    private Label difficulty;
    @FXML
    private Label credit;
    @FXML
    private Button enter;
    @FXML
    private Button shipInfo;

    public void initialize() throws IOException {
        Player player = Main.game.getPlayer();
        String ch = player.getCharacterName();
        String di = player.getDifficulty();
        String p = player.getPilotPoints().toString();
        String f = player.getFighterPoints().toString();
        String m = player.getMerchantPoints().toString();
        String e = player.getEngineerPoints().toString();
        String c = player.setCreditByDif(di).toString();
        pilotPoints.setText(p);
        fighterPoints.setText(f);
        merchantPoints.setText(m);
        engineerPoints.setText(e);
        characterName.setText(ch);
        difficulty.setText(di);
        credit.setText(c);
    }

    public void enterRegion(ActionEvent e) throws IOException {
        Stage stage = (Stage) enter.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Screen3Region.fxml"));
        //enter a random region
        List<String> keysArray = new ArrayList<>(getRegionMap().keySet());
        Random random = new Random();
        int randId = random.nextInt(keysArray.size());
        Region randomReg = (Region) getRegionMap().get(keysArray.get(randId));
        Controller3Regions controller = new Controller3Regions(randomReg);

        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root, 800, 520);
        stage.setScene(scene);
        stage.setTitle("Region");
        stage.show();
    }

    //@ZKQ
    public void showShipInfo(ActionEvent e) throws IOException {
        Stage mapStage = (Stage) shipInfo.getScene().getWindow();
        Parent mapScene = FXMLLoader.load(getClass().getResource("Screen5ShipInfo.fxml"));
        Scene scene = new Scene(mapScene, 800, 520);
        mapStage.setScene(scene);
        mapStage.setTitle("Ship Info");
        mapStage.show();
    }

    //@XYZ
    public void enterMap(ActionEvent e) throws IOException {
        Stage mapStage = (Stage) enter.getScene().getWindow();
        Parent mapScene = FXMLLoader.load(getClass().getResource("Screen4UniverseMap.fxml"));
        Scene scene = new Scene(mapScene, 800, 800);
        mapStage.setScene(scene);
        mapStage.setTitle("Universe Map");
        mapStage.show();
    }
}
