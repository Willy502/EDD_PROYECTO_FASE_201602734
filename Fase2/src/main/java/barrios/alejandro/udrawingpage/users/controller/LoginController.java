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
        TemporalInformation temporalInformation = TemporalInformation.getInstance();
        User user = temporalInformation.getUsersTree().searchUserByDpiAndPassword(Integer.parseInt(txtDpi.getText()), txtPassword.getText());
        if (user != null) {
            clearFields();
            System.out.println(user.getName());
        } else {
            new CustomAlert("Registro", "Credenciales inválidas");
        }
    }

    private void clearFields() {
        txtPassword.clear();
        txtDpi.clear();
    }
}
