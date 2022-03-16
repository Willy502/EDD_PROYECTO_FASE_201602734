package barrios.alejandro.udrawingpage.users.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController  {

    @FXML
    protected void goToRegister(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("register-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }
}
