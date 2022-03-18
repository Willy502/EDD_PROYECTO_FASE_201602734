package barrios.alejandro.udrawingpage;

import barrios.alejandro.udrawingpage.utils.InitApp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    String pack = "/barrios/alejandro/udrawingpage/users/";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(pack + "login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 420, 340);
        stage.setTitle("UDrawingPage");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        new InitApp();
        launch();
    }
}