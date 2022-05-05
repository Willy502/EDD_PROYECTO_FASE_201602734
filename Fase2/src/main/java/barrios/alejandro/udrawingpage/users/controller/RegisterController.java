package barrios.alejandro.udrawingpage.users.controller;

import barrios.alejandro.udrawingpage.place.model.Town;
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
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

public class RegisterController {

    @FXML
    protected TextField txtName, txtUsername, txtEmail, txtPhone, txtAddress;
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
            User user;

            try {
                user = temporalInformation.getUsersTree().searchUserBy(Long.parseLong(txtDpi.getText()), txtEmail.getText(), txtUsername.getText());

                if (txtName.getText() == "" || txtPassword.getText() == "" || txtConfirmPassword.getText() == "")
                    throw new NullPointerException();

            } catch (Exception e) {
                new CustomAlert("Error", "Faltan campos");
                return;
            }

            String hashed = BCrypt.hashpw(txtPassword.getText(), BCrypt.gensalt(12));
            if (user == null) new UserController().createClient(
                    txtName.getText(),
                    Long.parseLong(txtDpi.getText()),
                    hashed,
                    txtUsername.getText(),
                    txtEmail.getText(),
                    txtPhone.getText(),
                    txtAddress.getText(),
                    new Town(1, "Default", "Default", false) // TODO: AGREGAR BUSQUEDA DE MUNICIPIO
            );
            else new CustomAlert("Usuario existente", "Este DPI ya ha sido tomado por otro usuario");
            clearFields();
        } else {
            new CustomAlert("Error", "Las contraseñas no son iguales");
        }
    }

    private void clearFields() {
        txtPassword.clear();
        txtConfirmPassword.clear();
        txtName.clear();
        txtDpi.clear();
    }

}
