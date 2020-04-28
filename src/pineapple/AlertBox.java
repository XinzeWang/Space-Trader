package pineapple;

import javafx.scene.control.Alert;

public class AlertBox {
    private Alert alert;
    public AlertBox(String type, String title, String info) {
        alert = new Alert(Alert.AlertType.NONE);
        switch (type) {
        case "CONFIRMATION":
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            break;
        case "WARNING":
            alert.setAlertType(Alert.AlertType.WARNING);
            break;
        case "INFORMATION":
            alert.setAlertType(Alert.AlertType.INFORMATION);
            break;
        default:
        }
        alert.setTitle(title);
        alert.setHeaderText(info);
    }
    public void showAlert() {
        alert.showAndWait();
    }
}
