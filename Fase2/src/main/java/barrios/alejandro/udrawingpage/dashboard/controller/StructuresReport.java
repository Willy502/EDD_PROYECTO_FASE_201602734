package barrios.alejandro.udrawingpage.dashboard.controller;

import barrios.alejandro.udrawingpage.graph.Graph;
import barrios.alejandro.udrawingpage.place.model.Routing;
import barrios.alejandro.udrawingpage.structures.Adjacency.AdjacencyList;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyLinkedList;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyNode;
import barrios.alejandro.udrawingpage.structures.controller.*;
import barrios.alejandro.udrawingpage.structures.hash.Entry;
import barrios.alejandro.udrawingpage.structures.hash.HashTable;
import barrios.alejandro.udrawingpage.users.model.Courier;
import barrios.alejandro.udrawingpage.utils.CustomAlert;
import barrios.alejandro.udrawingpage.utils.TemporalInformation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.File;

public class StructuresReport {

    TemporalInformation temporalInformation;

    public StructuresReport() {
        temporalInformation = TemporalInformation.getInstance();
    }

    public void buildLayer(StackPane pane, int noLayer) {
        if (pane.getChildren().size() > 0) {
            pane.getChildren().clear();
        }
        // Build Matrix
        SparceMatrix matrixToBuild = temporalInformation.getLoguedUser().getCapas().searchLayer(noLayer);
        matrixToBuild.graphMatrix(true);

        new CustomAlert("Construyendo capa...");
        // Get matrix path
        File file = new File("out/capa_" + noLayer + ".png");
        if (file.exists()) {
            // Build image and imageview
            ImageView imageView = new ImageView();
            Image image = new Image("file:out/capa_" + noLayer + ".png");

            imageView.setImage(image);
            imageView.setFitHeight(500);
            imageView.setPreserveRatio(true);
            pane.getChildren().add(imageView);

        }

    }

    public void buildAvlImages(StackPane pane, AvlTree images) {
        if (pane.getChildren().size() > 0) {
            pane.getChildren().clear();
        }
        images.graphAvl();
        // Build image and imageview
        new CustomAlert("Construyendo árbol avl de imágenes...");
        ImageView imageView = new ImageView();
        Image image = new Image("file:out/AVL_IMAGENES.png");

        imageView.setImage(image);
        imageView.setFitHeight(500);
        imageView.setPreserveRatio(true);
        pane.getChildren().add(imageView);
    }

    public void buildBinaryLayers(StackPane pane, BinarySearchTree layers) {
        if (pane.getChildren().size() > 0) {
            pane.getChildren().clear();
        }
        layers.graphBinary();
        // Build image and imageview
        new CustomAlert("Construyendo árbol binario de capas...");
        ImageView imageView = new ImageView();
        Image image = new Image("file:out/BINARY_LAYER.png");

        imageView.setImage(image);
        imageView.setFitHeight(500);
        imageView.setPreserveRatio(true);
        pane.getChildren().add(imageView);
    }

    public void buildCircularAlbums(StackPane pane, DoublyCircularLinkedList albums) {
        if (pane.getChildren().size() > 0) {
            pane.getChildren().clear();
        }
        albums.graphCircular();
        // Build image and imageview
        new CustomAlert("Construyendo lista doble circular de álbums...");
        ImageView imageView = new ImageView();
        Image image = new Image("file:out/CIRCULAR_ALBUMS.png");

        imageView.setImage(image);
        imageView.setFitHeight(500);
        imageView.setPreserveRatio(true);
        pane.getChildren().add(imageView);
    }

    public void buildImageAndLayers(StackPane pane, BinarySearchTree layers) {
        if (pane.getChildren().size() > 0) {
            pane.getChildren().clear();
        }
        layers.graphBinaryWithImage(Integer.toString(layers.id));
        // Build image and imageview
        new CustomAlert("Construyendo árbol binario de imágenes y capa...");
        ImageView imageView = new ImageView();
        Image image = new Image("file:out/BINARY_LAYER.png");

        imageView.setImage(image);
        imageView.setFitHeight(500);
        imageView.setPreserveRatio(true);
        pane.getChildren().add(imageView);
    }

    public void buildBTree(StackPane pane, BTreeV2 clients) {
        if (pane.getChildren().size() > 0) {
            pane.getChildren().clear();
        }
        clients.graphBTree();
        // Build image and imageview
        new CustomAlert("Construyendo arbol B de clientes...");
        ImageView imageView = new ImageView();
        Image image = new Image("file:out/BTree.png");

        imageView.setImage(image);
        imageView.setFitHeight(500);
        imageView.setPreserveRatio(true);
        pane.getChildren().add(imageView);
    }

    public void buildHashTable(StackPane pane, HashTable<Courier> courierHashTable) {
        if (pane.getChildren().size() > 0) pane.getChildren().clear();

        courierHashTable.graphHashTable();
        new CustomAlert("Construyendo HashTable de mensajeros...");
        ImageView imageView = new ImageView();
        Image image = new Image("file:out/COURIER_HASH.png");

        imageView.setImage(image);
        imageView.setFitHeight(900);
        imageView.setPreserveRatio(true);
        pane.getChildren().add(imageView);
    }

    public void buildRoutesGraph(StackPane pane, SinglyLinkedList<Routing> routingSinglyLinkedList) {
        if (pane.getChildren().size() > 0) pane.getChildren().clear();

        String result = "";

        result = "digraph G {\n";
        result += "node[shape=circle];\n";

        for (SinglyNode<Routing> node = routingSinglyLinkedList.getHead(); node != null; node = node.next) {

            result += "a" + node.data.getInicio().getId() + "[label=\"" + node.data.getInicio() + "\"];\n";
            result += "a" + node.data.getFinale().getId() + "[label=\"" + node.data.getFinale() + "\"];\n";
            result += "a" + node.data.getInicio().getId() + " -> " + "a" + node.data.getFinale().getId() + "[label=" + node.data.getWeight() + "arrowhead=none];\n";

        }

        result += "}\n";
        Graph.GenerarImagen("ROUTES_GRAPH", result);

        new CustomAlert("Construyendo grafo de rutas...");
        ImageView imageView = new ImageView();
        Image image = new Image("file:out/ROUTES_GRAPH.png");

        imageView.setImage(image);
        imageView.setFitHeight(500);
        imageView.setPreserveRatio(true);
        pane.getChildren().add(imageView);

    }

    public void buildAdjacencyList(StackPane pane, AdjacencyList adjacencyList) {
        if (pane.getChildren().size() > 0) pane.getChildren().clear();

        adjacencyList.buildGraph();
        new CustomAlert("Construyendo lista de adyacencia de mensajeros...");
        ImageView imageView = new ImageView();
        Image image = new Image("file:out/ADJACENCY_LIST.png");

        imageView.setImage(image);
        imageView.setFitHeight(600);
        imageView.setPreserveRatio(true);
        pane.getChildren().add(imageView);
    }

}
