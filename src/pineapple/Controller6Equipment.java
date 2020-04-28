package pineapple;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pineapple.model.Game;
import pineapple.model.Player;
import pineapple.model.Region;
import pineapple.model.Ship;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static pineapple.RegionManager.getRegionMap;
import static pineapple.ShipManager.getShipByID;

public class Controller6Equipment {
    @FXML
    private Label pilotPoint;
    @FXML
    private Label fighterPoint;
    @FXML
    private Label merchantPoint;
    @FXML
    private Label engineerPoint;
    @FXML
    private ChoiceBox weaponBox;
    @FXML
    private ChoiceBox scrollBox;
    @FXML
    private Button back;
    @FXML
    private Button refuel;
    @FXML
    private Button repair;
    @FXML
    private Label fuel;
    @FXML
    private Label health;
    @FXML
    private Label credit;
    private Region prevR;


    private Game game;
    private Player player;
    private Ship ship;
    private Integer op;
    private Integer of;
    private Integer om;
    private Integer oe;
    private Integer p;
    private Integer f;
    private Integer m;
    private Integer e;
    private Integer remainingFuel;
    private Integer remainingHealth;
    private Double remainingCredit;


    public void initialize() throws IOException {
        this.game = Main.game;
        this.player = game.getPlayer();
        this.ship = getShipByID(0);
        op = player.getBasicPilotPoints();
        p = player.getPilotPoints();
        of = player.getBasicFighterPoints();
        f = player.getFighterPoints();
        om = player.getBasicMerchantPoints();
        m = player.getMerchantPoints();
        oe = player.getBasicEngineerPoints();
        e = player.getEngineerPoints();
        remainingFuel = ship.getCurrFuel();
        remainingHealth = ship.getCurrHealth();

        remainingCredit = player.getCredit();

        pilotPoint.setText(p.toString());
        fighterPoint.setText(f.toString());
        merchantPoint.setText(m.toString());
        engineerPoint.setText(e.toString());

        fuel.setText(remainingFuel.toString());
        health.setText(remainingHealth.toString());

        credit.setText(remainingCredit.toString());

        weaponBox.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String>
                                                observableValue, String s, String t1) {
                        try {
                            switch (t1) {
                                case "No Weapon":
                                    f = of;
                                    break;
                                case "Ray Gun":
                                    f = of + 5;
                                    break;
                                case "Thunder Gun":
                                    f = of + 10;
                                    break;
                                default:
                            }
                            player.setFighterPoints(f);
                            fighterPoint.setText(f.toString());
                        } catch (Exception e) {
                            System.out.println("something unknown happen");
                        }

                    }
                });

        scrollBox.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String>
                                                observableValue, String s, String t2) {
                        try {
                            switch (t2) {
                                case "No Scroll":
                                    e = oe;
                                    m = om;
                                    p = op;
                                    break;
                                case "Engineer Scroll":
                                    e = oe + 5;
                                    m = om;
                                    p = op;
                                    break;
                                case "Merchant Scroll":
                                    m = om + 5;
                                    p = op;
                                    e = oe;
                                    break;
                                case "Pilot Scroll":
                                    p = op + 5;
                                    m = om;
                                    e = oe;
                                    break;
                                default:
                            }
                            player.setMerchantPoints(m);
                            player.setPilotPoints(p);
                            player.setEngineerPoints(e);
                            engineerPoint.setText(e.toString());
                            pilotPoint.setText(p.toString());
                            merchantPoint.setText(m.toString());
                            

                        } catch (Exception e) {
                            System.out.println("something unknown happen");
                        }

                    }
                });
    }
    public void refuel(ActionEvent actionEvent) throws IOException {
        int fuelConsumption = ship.getFuelCapacity() - ship.getCurrFuel();
        double cost = fuelConsumption * 0.06;

        if (player.getCredit() < cost) {
            AlertBox a = new AlertBox("WARNING", "Warning",
                    "Your don't have enough money to refuel!");
            a.showAlert();
        } else if (!ship.refuel()) {
            AlertBox a = new AlertBox("WARNING", "Warning", "Your fuel is full!");
            a.showAlert();
        } else {
            player.setCredit(player.getCredit() - cost);
            remainingCredit = player.getCredit();
            credit.setText(remainingCredit.toString());
            remainingFuel = ship.getCurrFuel();
            fuel.setText(remainingFuel.toString());

            AlertBox a = new AlertBox("INFORMATION", "INFORMATION", "Your fuel is full now!");
            a.showAlert();
        }
    }


    public void repair(ActionEvent actionEvent) throws IOException {
        int healthDiminished = ship.getHealthCapacity() - ship.getCurrHealth();
        double cost = healthDiminished * 0.1 / (player.getEngineerPoints() / 2 + 1);
        System.out.println(cost);

        if (player.getCredit() < cost) {
            AlertBox a = new AlertBox("WARNING", "Warning",
                    "Your don't have enough money to repair!");
            a.showAlert();
        } else if (!ship.repair()) {
            AlertBox a = new AlertBox("WARNING", "Warning", "Your HP is full!");
            a.showAlert();
        } else {
            player.setCredit(player.getCredit() - cost);
            remainingCredit = player.getCredit();
            credit.setText(remainingCredit.toString());
            remainingHealth = ship.getCurrHealth();
            health.setText(remainingHealth.toString());
            AlertBox a = new AlertBox("INFORMATION", "INFORMATION", "Your HP is full now!");
            a.showAlert();
        }
    }

    public void back(ActionEvent actionEvent) throws IOException {
        if (game.getVisitedRegs().isEmpty()) {
            Stage stage = (Stage) back.getScene().getWindow();
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
        } else {
            this.prevR = game.peekVisitedRegs();
            Stage stage = (Stage) back.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Screen3Region.fxml"));
            Controller3Regions controller = new Controller3Regions(this.prevR);

            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root, 800, 520);
            stage.setScene(scene);
            stage.setTitle("Region");
            stage.show();
        }

    }

}
