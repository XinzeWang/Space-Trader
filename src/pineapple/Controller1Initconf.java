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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pineapple.model.Game;
import pineapple.model.Player;

import java.io.IOException;

public class Controller1Initconf {
    private Game game;
    private Player player;
    private Integer skillPoints;
    private Integer initPoints;
    @FXML
    private Label remainPoints;
    @FXML
    private Button confirm;
    @FXML
    private Button reset;
    @FXML
    private ChoiceBox difficultyBox;
    @FXML
    private TextField characterName;

    //optional @XYZ
    @FXML
    private Label pilotPoints;
    @FXML
    private Label fighterPoints;
    @FXML
    private Label merchantPoints;
    @FXML
    private Label engineerPoints;

    public void initialize() {
        //optional @XYZ
        this.game = Main.game;
        this.player = game.getPlayer();
        pilotPoints.setText(" ");
        fighterPoints.setText(" ");
        merchantPoints.setText(" ");
        engineerPoints.setText(" ");
        //

        difficultyBox.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String>
                                                observableValue, String s, String t1) {
                        try {
                            switch (t1) {
                                case "Easy":
                                    initPoints = 10;
                                    break;
                                case "Normal":
                                    initPoints = 8;
                                    break;
                                case "Difficult":
                                    initPoints = 6;
                                    break;
                                case "Hell":
                                    initPoints = 4;
                                    break;
                                default:
                            }
                            skillPoints = initPoints;
                            remainPoints.setText(initPoints.toString());
                        } catch (Exception e) {
                            System.out.println("something unknown happen");
                        }
                    }
                });

    }

    public void characterSheet(ActionEvent e) throws IOException {
        String cname = characterName.getText();
        if (cname.equals("") || cname == null) {
            AlertBox a = new AlertBox("WARNING", "WARNING", "Character name can not be null!");
            a.showAlert();
        } else {
            try {
                String dif = difficultyBox.getValue().toString();
                this.player.setCharacterName(cname);
                this.player.setDifficulty(dif);

                Stage stage = null;
                Parent myNewscene = null;
                stage = (Stage) confirm.getScene().getWindow();
                myNewscene = FXMLLoader.load(getClass().getResource("Screen2CharacterSheet.fxml"));
                Scene scene = new Scene(myNewscene, 800, 520);
                scene.getStylesheets().add(getClass().getResource("CSS/Screen2.css")
                        .toExternalForm());
                stage.setScene(scene);
                stage.setTitle("Character Sheet");
                stage.show();
            } catch (Exception ex) {
                AlertBox a = new AlertBox("WARNING", "WARNING", "Please choose a difficulty!");
                a.showAlert();
            }
        }
    }

    public void resetPoints(ActionEvent e) throws IOException {
        if (difficultyBox.getValue() == null) {
            AlertBox a = new AlertBox("WARNING", "WARNING",
                    "Please chhose a difficulty before reset!");
            a.showAlert();
        } else {
            skillPoints = initPoints;
            remainPoints.setText(initPoints.toString());
            pilotPoints.setText(" ");
            fighterPoints.setText(" ");
            merchantPoints.setText(" ");
            engineerPoints.setText(" ");
            player.resetAllPoints();
        }
    }

    public void skillPilot(ActionEvent e) throws IOException {
        if (difficultyBox.getValue() == null) {
            AlertBox a = new AlertBox("WARNING", "WARNING",
                    "Please chhose a difficulty before reset!");
            a.showAlert();
            return;
        }
        if (skillPoints > 0) {
            //@XYZ
            Integer pp = this.player.getPilotPoints();
            this.player.setPilotPoints(++pp);
            this.player.setBasicPilotPoints(pp);
            this.pilotPoints.setText(pp.toString());
            //
            skillPoints--;
            remainPoints.setText(skillPoints.toString());
        } else {
            AlertBox a = new AlertBox("WARNING", "WARNING",
                    "You have run out of your skill points!");
            a.showAlert();
        }
    }

    public void skillFighter(ActionEvent e) throws IOException {
        if (difficultyBox.getValue() == null) {
            AlertBox a = new AlertBox("WARNING", "WARNING",
                    "Please chhose a difficulty before reset!");
            a.showAlert();
            return;
        }
        if (skillPoints > 0) {
            //@XYZ
            Integer fp = this.player.getFighterPoints();
            this.player.setFighterPoints(++fp);
            this.player.setBasicFighterPoints(fp);
            this.fighterPoints.setText(fp.toString());
            //
            skillPoints--;
            remainPoints.setText(skillPoints.toString());
        } else {
            AlertBox a = new AlertBox("WARNING", "WARNING",
                    "You have run out of your skill points!");
            a.showAlert();
        }
    }

    public void skillMerchant(ActionEvent e) throws IOException {
        if (difficultyBox.getValue() == null) {
            AlertBox a = new AlertBox("WARNING", "WARNING",
                    "Please chhose a difficulty before reset!");
            a.showAlert();
            return;
        }
        if (skillPoints > 0) {
            //@XYZ
            Integer mp = this.player.getMerchantPoints();
            this.player.setMerchantPoints(++mp);
            this.player.setBasicMerchantPoints(mp);
            this.merchantPoints.setText(mp.toString());
            //
            skillPoints--;
            remainPoints.setText(skillPoints.toString());
        } else {
            AlertBox a = new AlertBox("WARNING", "WARNING",
                    "You have run out of your skill points!");
            a.showAlert();
        }
    }

    public void skillEngineer(ActionEvent e) throws IOException {
        if (difficultyBox.getValue() == null) {
            AlertBox a = new AlertBox("WARNING", "WARNING",
                    "Please chhose a difficulty before reset!");
            a.showAlert();
            return;
        }
        if (skillPoints > 0) {

            //@XYZ
            Integer ep = this.player.getEngineerPoints();
            this.player.setEngineerPoints(++ep);
            this.player.setBasicEngineerPoints(ep);
            this.engineerPoints.setText(ep.toString());
            //
            skillPoints--;
            remainPoints.setText(skillPoints.toString());
        } else {
            AlertBox a = new AlertBox("WARNING", "WARNING",
                    "You have run out of your skill points!");
            a.showAlert();
        }
    }
}
