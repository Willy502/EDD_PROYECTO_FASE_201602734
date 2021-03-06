package barrios.alejandro.udrawingpage.users.controller;

import barrios.alejandro.udrawingpage.place.model.Town;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyLinkedList;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyNode;
import barrios.alejandro.udrawingpage.utils.CustomAlert;
import barrios.alejandro.udrawingpage.utils.TemporalInformation;
import barrios.alejandro.udrawingpage.users.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
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
    @FXML
    protected ComboBox<Town> comboCity;

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
    public void initialize() {
        populateTowns();
    }

    private void populateTowns() {
        if (temporalInformation.getTownSinglyLinkedList() != null) {
            comboCity.getItems().clear();
            SinglyLinkedList<Town> towns = temporalInformation.getTownSinglyLinkedList();

            for (SinglyNode<Town> current = towns.getHead(); current != null; current = current.next) {
                comboCity.getItems().add(current.data);
            }
        }
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
                    comboCity.getValue()
            );
            else new CustomAlert("Usuario existente", "Estos datos ya han sido tomados por otro usuario");
            clearFields();
        } else {
            new CustomAlert("Error", "Las contrase??as no son iguales");
        }
    }

    private void clearFields() {
        txtPassword.clear();
        txtConfirmPassword.clear();
        txtName.clear();
        txtDpi.clear();
        txtUsername.clear();
        txtEmail.clear();
        txtPhone.clear();
        txtAddress.clear();
    }

}
