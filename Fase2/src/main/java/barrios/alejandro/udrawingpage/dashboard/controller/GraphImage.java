package barrios.alejandro.udrawingpage.dashboard.controller;

import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyNode;
import barrios.alejandro.udrawingpage.structures.controller.BinarySearchTree;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyLinkedList;
import barrios.alejandro.udrawingpage.structures.controller.SparceMatrix;
import barrios.alejandro.udrawingpage.utils.CustomAlert;
import barrios.alejandro.udrawingpage.utils.TemporalInformation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class GraphImage {

    TemporalInformation temporalInformation;

    public GraphImage() {
        temporalInformation = TemporalInformation.getInstance();
    }

    public void buildImage(StackPane pane, BinarySearchTree image, String order) {
        if (pane.getChildren().size() > 0) {
            pane.getChildren().clear();
        }

        image.orderLayers(order);
        SinglyLinkedList<SparceMatrix> layers = image.getOrderedLayers();
        graphImage(pane, layers);
        try {
            launchPopUp(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildImage(StackPane pane, String layers) {
        if (pane.getChildren().size() > 0) {
            pane.getChildren().clear();
        }

        String[] dividedLayers = layers.split(",");
        SinglyLinkedList<SparceMatrix> sLayers = new SinglyLinkedList<>();
        for (String dividedLayer : dividedLayers) {
            SparceMatrix matrix = temporalInformation.getLoguedUser().getCapas().searchLayer(Integer.parseInt(dividedLayer));
            sLayers.addToList(matrix);
        }
        graphImage(pane, sLayers);
        try {
            launchPopUp(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void launchPopUp(StackPane pane) throws IOException {
        String pack = "/barrios/alejandro/udrawingpage/dashboard/";
        Parent parent = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource(pack + "popup-send.fxml")));
        Scene settingsScene = new Scene(parent);
        Stage popUp = new Stage();
        popUp.setScene(settingsScene);
        popUp.initModality(Modality.WINDOW_MODAL);
        popUp.initOwner(pane.getScene().getWindow());
        popUp.show();
    }


    private void graphImage(StackPane pane, SinglyLinkedList<SparceMatrix> layers) {
        for (SinglyNode<SparceMatrix> layer = layers.getHead(); layer != null; layer = layer.next) {
            layer.data.graphMatrix(false);
            new CustomAlert("Construyendo capa...");

            File file = new File("out/capa_" + layer.data.id + ".png");
            if (file.exists()) {
                // Build image and imageview
                ImageView imageView = new ImageView();
                Image img = new Image("file:out/capa_" + layer.data.id + ".png");

                imageView.setImage(img);
                imageView.setFitHeight(500);
                imageView.setPreserveRatio(true);
                pane.getChildren().add(imageView);

            }

        }
    }
}
