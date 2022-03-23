package barrios.alejandro.udrawingpage.dashboard.controller;

import barrios.alejandro.udrawingpage.structures.controller.AvlTree;
import barrios.alejandro.udrawingpage.structures.controller.BinarySearchTree;
import barrios.alejandro.udrawingpage.structures.controller.DoublyCircularLinkedList;
import barrios.alejandro.udrawingpage.structures.controller.SparceMatrix;
import barrios.alejandro.udrawingpage.utils.TemporalInformation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StructuresReport {

    TemporalInformation temporalInformation;

    public StructuresReport() {
        temporalInformation = TemporalInformation.getInstance();
    }

    public void buildLayer(StackPane pane, int noLayer) {
        // Build Matrix
        SparceMatrix matrixToBuild = temporalInformation.getLoguedUser().getCapas().searchLayer(noLayer);
        matrixToBuild.graphMatrix();

        // Get matrix path
        File file = new File("out/capa_" + noLayer + ".png");
        if (file.exists()) {
            // Build image and imageview
            ImageView imageView = pane.getChildren().size() > 0 ? (ImageView) pane.getChildren().get(0) : new ImageView();
            Image image = new Image("file:out/capa_" + noLayer + ".png");

            imageView.setImage(image);
            imageView.setFitHeight(500);
            imageView.setPreserveRatio(true);
            if (pane.getChildren().size() == 0) pane.getChildren().add(imageView);

        }

    }

    public void buildAvlImages(StackPane pane, AvlTree images) {
        images.graphAvl();
        // Build image and imageview
        ImageView imageView = pane.getChildren().size() > 0 ? (ImageView) pane.getChildren().get(0) : new ImageView();
        Image image = new Image("file:out/AVL_IMAGENES.png");

        imageView.setImage(image);
        imageView.setFitHeight(500);
        imageView.setPreserveRatio(true);
        if (pane.getChildren().size() == 0) pane.getChildren().add(imageView);
    }

    public void buildBinaryLayers(StackPane pane, BinarySearchTree layers) {
        layers.graphBinary();
        // Build image and imageview
        ImageView imageView = pane.getChildren().size() > 0 ? (ImageView) pane.getChildren().get(0) : new ImageView();
        Image image = new Image("file:out/BINARY_LAYER.png");

        imageView.setImage(image);
        imageView.setFitHeight(500);
        imageView.setPreserveRatio(true);
        if (pane.getChildren().size() == 0) pane.getChildren().add(imageView);
    }

    public void buildCircularAlbums(StackPane pane, DoublyCircularLinkedList albums) {
        albums.graphCircular();
        // Build image and imageview
        ImageView imageView = pane.getChildren().size() > 0 ? (ImageView) pane.getChildren().get(0) : new ImageView();
        Image image = new Image("file:out/CIRCULAR_ALBUMS.png");

        imageView.setImage(image);
        imageView.setFitHeight(500);
        imageView.setPreserveRatio(true);
        if (pane.getChildren().size() == 0) pane.getChildren().add(imageView);
    }

}
