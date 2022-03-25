package barrios.alejandro.udrawingpage.utils;

import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;
import org.controlsfx.dialog.ProgressDialog;

public class CustomAlert {

    public CustomAlert(String message){
        Task<?> copyWorker = createWorker(message);
        ProgressDialog dialog = new ProgressDialog(copyWorker);
        dialog.initStyle(StageStyle.TRANSPARENT);

        dialog.setGraphic(null);
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.initStyle(StageStyle.UTILITY);
        new Thread(copyWorker).start();
        dialog.showAndWait();
    }

    public CustomAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Task<?> createWorker(String message) {
        return new Task<>() {
            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(100);
                    updateMessage(message);
                    updateProgress(i + 1, 10);
                }
                return true;
            }
        };
    }

}
