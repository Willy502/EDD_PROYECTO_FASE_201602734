package barrios.alejandro.udrawingpage.users.controller;

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
    protected TextField txtUsername;
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
        User user;

        try {
            user = temporalInformation.getUsersTree().loginByUsernameAndPassword(txtUsername.getText(), txtPassword.getText());
        } catch (Exception e) {
            new CustomAlert("Error", "Credenciales inválidas");
            return;
        }

        if (user != null) {
            clearFields();
            temporalInformation.setLoguedUser(user);
            goToDashboard(event);
        } else {
            new CustomAlert("Registro", "Credenciales inválidas");
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
        txtUsername.clear();
    }
}
