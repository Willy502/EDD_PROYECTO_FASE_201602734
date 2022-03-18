package barrios.alejandro.udrawingpage.users.controller;

import barrios.alejandro.udrawingpage.utils.CustomAlert;
import barrios.alejandro.udrawingpage.utils.TemporalInformation;
import barrios.alejandro.udrawingpage.users.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    @FXML
    protected TextField txtName;
    @FXML
    protected TextField txtDpi;
    @FXML
    protected PasswordField txtPassword;
    @FXML
    protected PasswordField txtConfirmPassword;

    @FXML
    protected void backToLogIn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }

    @FXML
    protected void registerUser() {
        int id = TemporalInformation.getInstance().getRegisteredUsers();
        if (txtPassword.getText().equals(txtConfirmPassword.getText())) {
            User user = new User(id + 1, txtName.getText(), txtDpi.getText(), txtPassword.getText());
            TemporalInformation.getInstance().setRegisteredUsers();
            TemporalInformation.getInstance().getUsersTree().insert(user);
            clearFields();
            new CustomAlert("Registro", "Usuario creado exitosamente");
        }

    }

    private void clearFields() {
        txtPassword.clear();
        txtConfirmPassword.clear();
        txtName.clear();
        txtDpi.clear();
    }

}
