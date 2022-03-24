package barrios.alejandro.udrawingpage.dashboard.controller;

import barrios.alejandro.udrawingpage.users.model.Rol;
import barrios.alejandro.udrawingpage.utils.TemporalInformation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportsController {

    @FXML
    protected VBox vboxAdmin, vboxClient, main;

    private final TemporalInformation temporalInformation;

    public ReportsController() {
        temporalInformation = TemporalInformation.getInstance();
    }

    @FXML
    public void initialize() {
        if (temporalInformation.getLoguedUser().getRol() == Rol.ADMIN) {
            main.getChildren().remove(vboxClient);
        } else {
            main.getChildren().remove(vboxAdmin);
        }
    }

    @FXML
    protected void backToDashboard(ActionEvent event) {
        String pack = "/barrios/alejandro/udrawingpage/dashboard/";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(pack + "dashboard-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
