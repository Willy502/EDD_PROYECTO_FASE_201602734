package barrios.alejandro.udrawingpage.users.controller;

import barrios.alejandro.udrawingpage.utils.CustomAlert;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("register-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }

    @FXML
    protected void loginUser() {
        // Search by dpi and password
        clearFields();
        new CustomAlert("Registro", "Encontrao");
    }

    private void clearFields() {
        txtPassword.clear();
        txtDpi.clear();
    }
}
