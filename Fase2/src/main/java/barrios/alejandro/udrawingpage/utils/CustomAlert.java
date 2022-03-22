package barrios.alejandro.udrawingpage.utils;

import javafx.scene.control.Alert;

public class CustomAlert {

    public CustomAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
