package pineapple;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pineapple.model.Item;
import pineapple.model.Ship;

import java.io.IOException;
import java.util.HashMap;

import static pineapple.ShipManager.getShipByID;

public class Controller5ShipInfo {

    @FXML
    private Label shipName;
    @FXML
    private Label cargoCapacity;
    @FXML
    private Label fuelCapacity;
    @FXML
    private Label health;
    @FXML
    private Label items;
    @FXML
    private Button back;

    private HashMap<Integer, Item> itemMap = ItemManager.getItemMap();

    public void initialize() throws IOException {
        Ship ship = getShipByID(0);
        String sName = ship.getShipName();
        int cCap = ship.getCargoCapacity();
        int fCap = ship.getFuelCapacity();
        int h = ship.getCurrHealth();
        StringBuilder itemString = new StringBuilder();
        //@XZW
        for (Integer key : ship.getInventory().keySet()) {
            itemString.append(itemMap.get(key).getItemName());
            itemString.append(",  ");
            itemString.append(ship.getInventory().get(key));
            itemString.append(";  ");
        }
        shipName.setText(sName);
        cargoCapacity.setText(Integer.toString(cCap));
        fuelCapacity.setText(Integer.toString(fCap));
        health.setText(Integer.toString(h));
        items.setText(itemString.toString());
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        Stage stage = null;
        Parent myNewscene = null;
        stage = (Stage) back.getScene().getWindow();
        myNewscene = FXMLLoader.load(getClass().getResource("Screen2CharacterSheet.fxml"));
        Scene scene = new Scene(myNewscene, 800, 520);
        scene.getStylesheets().add(getClass().getResource("CSS/Screen2.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Character Sheet");
        stage.show();
    }
}
