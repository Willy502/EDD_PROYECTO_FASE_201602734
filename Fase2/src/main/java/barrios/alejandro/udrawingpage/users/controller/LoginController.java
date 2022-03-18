package barrios.alejandro.udrawingpage.users.controller;

import barrios.alejandro.udrawingpage.structures.controller.SparceMatrix;
import barrios.alejandro.udrawingpage.users.model.User;
import barrios.alejandro.udrawingpage.utils.CustomAlert;
import barrios.alejandro.udrawingpage.utils.TemporalInformation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController  {

    @FXML
    protected TextField txtDpi;
    @FXML
    protected PasswordField txtPassword;

    @FXML
    protected void goToRegister(ActionEvent event) throws IOException {
        String pack = "/barrios/alejandro/udrawingpage/users/";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(pack + "register-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }

    @FXML
    protected void loginUser(ActionEvent event) {
        TemporalInformation temporalInformation = TemporalInformation.getInstance();
        User user = temporalInformation.getUsersTree().searchUserByDpiAndPassword(Integer.parseInt(txtDpi.getText()), txtPassword.getText());
        if (user != null) {
            clearFields();
            temporalInformation.setLoguedUser(user);
            goToDashboard(event);
        } else {
            new CustomAlert("Registro", "Credenciales inválidas");
            SparceMatrix sparceMatrix = new SparceMatrix(1, 5, 5);
            sparceMatrix.saveCell(5, 5, "#000");
            sparceMatrix.saveCell(1, 1, "#000");
            sparceMatrix.saveCell(3, 4, "#000");
            sparceMatrix.saveCell(4, 2, "#000");
            sparceMatrix.saveCell(2, 3, "#000");
        }
    }

    private void goToDashboard(ActionEvent event) {
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

    private void clearFields() {
        txtPassword.clear();
        txtDpi.clear();
    }
}
