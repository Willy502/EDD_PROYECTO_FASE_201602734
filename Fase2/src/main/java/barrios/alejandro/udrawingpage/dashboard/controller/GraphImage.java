package barrios.alejandro.udrawingpage.dashboard.controller;

import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyNode;
import barrios.alejandro.udrawingpage.structures.controller.BinarySearchTree;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyLinkedList;
import barrios.alejandro.udrawingpage.structures.controller.SparceMatrix;
import barrios.alejandro.udrawingpage.utils.CustomAlert;
import barrios.alejandro.udrawingpage.utils.TemporalInformation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.File;

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
