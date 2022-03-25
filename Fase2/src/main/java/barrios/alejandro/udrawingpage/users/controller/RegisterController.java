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

    TemporalInformation temporalInformation;

    public RegisterController() {
        temporalInformation = TemporalInformation.getInstance();
    }

    @FXML
    protected void backToLogIn(ActionEvent event) throws IOException {
        String pack = "/barrios/alejandro/udrawingpage/users/";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(pack + "login-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }

    @FXML
    protected void registerUser() {
        if (txtPassword.getText().equals(txtConfirmPassword.getText())) {
            User user = temporalInformation.getUsersTree().searchUserByDpi(Long.parseLong(txtDpi.getText()));
            if (user == null) new UserController().createClient(txtName.getText(), Long.parseLong(txtDpi.getText()), txtPassword.getText());
            else new CustomAlert("Usuario existente", "Este DPI ya ha sido tomado por otro usuario");
            clearFields();
        }
    }

    private void clearFields() {
        txtPassword.clear();
        txtConfirmPassword.clear();
        txtName.clear();
        txtDpi.clear();
    }

}
