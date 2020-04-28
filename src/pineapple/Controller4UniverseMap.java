package pineapple;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import pineapple.model.Game;
import pineapple.model.Player;
import pineapple.model.Region;
import pineapple.model.Ship;

import java.io.IOException;
import java.util.Random;

import static pineapple.RegionManager.getRegionByID;

public class Controller4UniverseMap {

    @FXML
    private Label l0;
    @FXML
    private Button region1;
    @FXML
    private Button region2;
    @FXML
    private Button region3;
    @FXML
    private Button region4;
    @FXML
    private Button region5;
    @FXML
    private Button region6;
    @FXML
    private Button region7;
    @FXML
    private Button region8;
    @FXML
    private Button region9;
    @FXML
    private Button region10;

    private Game game;
    private Player player;
    private Ship myCurrShip;

    public void initialize() throws IOException {
        game = Main.game;
        player = game.getPlayer();
        myCurrShip = player.getMyShip();

        l0.setLayoutX(400);
        l0.setLayoutY(260);

        this.showRegion(region1);
        this.showRegion(region2);
        this.showRegion(region3);
        this.showRegion(region4);
        this.showRegion(region5);
        this.showRegion(region6);
        this.showRegion(region7);
        this.showRegion(region8);
        this.showRegion(region9);
        this.showRegion(region10);
    }

    private void showRegion(Button regionBut) {

        Region region = getRegionByID(regionBut.getId());
        String rName = region.getName();
        String tLevel = region.getTechLevel();
        String de = region.getDescription();
        String cox = Double.toString(region.getLocation()[0]);
        String coy = Double.toString(region.getLocation()[1]);
        String distance;
        if (game.getVisitedRegs().isEmpty()) {
            distance = new String("Do not have a current region");
        } else {
            Region currentRegion = game.peekVisitedRegs();
            distance = Double.toString(Math.round(
                    Controller3Regions.getDistance(currentRegion, region)
                            * 100.0) / 100.0);
        }

        regionBut.setText(rName);
        regionBut.setLayoutX(region.getLocation()[0]);
        regionBut.setLayoutY(region.getLocation()[1]);

        Tooltip tooltip = new Tooltip();
        tooltip.setText(region.isVisited()
                ? "Region Name: " + rName + "\nTechnology Level: " + tLevel + "\nDescription: "
                + de : "Region Not Visited" + "\nCoordinate: " + cox + ", "
                + coy + "\nDistance:" + distance);
        regionBut.setTooltip(tooltip);
    }

    public static double getDistance(Region a, Region b) {
        return Math.sqrt((a.getLocation()[0] - b.getLocation()[0])
                * (a.getLocation()[0] - b.getLocation()[0])
                + (a.getLocation()[1] - b.getLocation()[1])
                * (a.getLocation()[1] - b.getLocation()[1]));
    }

    //modified @XYZ
    public void goRegion(ActionEvent actionEvent) throws IOException {

        Button region = (Button) actionEvent.getSource();
        Region r = getRegionByID(region.getId());

        //Before going to and setting previous region, check remaining fuel
        if (game.getVisitedRegs().isEmpty()) {
            AlertBox a = new AlertBox("WARNING", "No current region",
                    "No current region found, please visit a region first");
            a.showAlert();
            return;
        } else {
            Region currentRegion = game.peekVisitedRegs();
            int fuelConsumption = (int) (getDistance(r, currentRegion)
                    * Math.exp(player.getPilotPoints() * -0.01));
            if (!myCurrShip.comsumeFuel(fuelConsumption)) {
                // fuel is not enough for this travel
                AlertBox a = new AlertBox("WARNING", "Lack of fuel!",
                        "Surplus fuel can not support this fight");
                a.showAlert();
                return;
            }
        }
        game.setCurReg(null);
        game.setNextReg(r);
        // second version @XZW
        encounterNPC(r);
    }

    // @XZW
    public void encounterNPC(Region r) throws IOException {
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

    // keepGoing could be replaced by enterRegion?
    public void keepGoing(Region r) throws IOException {
        Controller3Regions controller = new Controller3Regions(r);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Screen3Region.fxml"));
        Stage stage = new Stage();
        this.initialize();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root, 800, 520);
        Stage oldStage = (Stage) l0.getScene().getWindow();
        oldStage.close();
        stage.setScene(scene);
        stage.setTitle("Region");
        stage.show();
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
        Stage oldStage = (Stage) l0.getScene().getWindow();
        oldStage.close();
        stage.show();
    }
}
