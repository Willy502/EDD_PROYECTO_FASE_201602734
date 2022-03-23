package barrios.alejandro.udrawingpage.dashboard.controller;

import barrios.alejandro.udrawingpage.structures.controller.BinarySearchTree;
import barrios.alejandro.udrawingpage.structures.controller.SinglyLinkedList;
import barrios.alejandro.udrawingpage.structures.controller.SparceMatrix;
import barrios.alejandro.udrawingpage.utils.TemporalInformation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class GraphImage {

    TemporalInformation temporalInformation;

    public GraphImage() {
        temporalInformation = TemporalInformation.getInstance();
    }

    public void buildImage(StackPane pane, BinarySearchTree image, String order) throws InterruptedException {
        if (pane.getChildren().size() > 0) {
            pane.getChildren().clear();
        }

        image.orderLayers(order);
        SinglyLinkedList<SparceMatrix> layers = image.getOrderedLayers();
        for (SinglyLinkedList.Node<SparceMatrix> layer = layers.getHead(); layer != null; layer = layer.next) {
            layer.data.graphMatrix();
            TimeUnit.SECONDS.sleep(2);

            File file = new File("out/capa_" + layer.data.id + ".png");
            if (file.exists()) {
                // Build image and imageview
                ImageView imageView = pane.getChildren().size() > 0 ? (ImageView) pane.getChildren().get(0) : new ImageView();
                Image img = new Image("file:out/capa_" + layer.data.id + ".png");

                imageView.setImage(img);
                imageView.setFitHeight(500);
                imageView.setPreserveRatio(true);
                pane.getChildren().add(imageView);

            }

        }

    }
}
