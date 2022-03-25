package barrios.alejandro.udrawingpage.dashboard.controller;

import barrios.alejandro.udrawingpage.structures.controller.BinarySearchTree;
import barrios.alejandro.udrawingpage.structures.controller.SinglyLinkedList;
import barrios.alejandro.udrawingpage.structures.controller.SparceMatrix;
import barrios.alejandro.udrawingpage.users.model.Rol;
import barrios.alejandro.udrawingpage.utils.CustomAlert;
import barrios.alejandro.udrawingpage.utils.TemporalInformation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportsController {

    @FXML
    protected VBox vboxAdmin, vboxClient, main;

    private final TemporalInformation temporalInformation;

    public ReportsController() {
        temporalInformation = TemporalInformation.getInstance();
    }

    @FXML
    public void initialize() {
        if (temporalInformation.getLoguedUser().getRol() == Rol.ADMIN) {
            main.getChildren().remove(vboxClient);
        } else {
            main.getChildren().remove(vboxAdmin);
        }
    }

    @FXML
    protected void backToDashboard(ActionEvent event) {
        String pack = "/barrios/alejandro/udrawingpage/dashboard/";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(pack + "dashboard-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void top5ImgsLayers() {

    }

    @FXML
    protected void leafLayers() {
        BinarySearchTree searchTree = temporalInformation.getLoguedUser().getCapas();
        searchTree.orderLayers("PREORDER");
        new CustomAlert("Procesando...");
        SinglyLinkedList<SparceMatrix> layers = searchTree.getLeafLayers();
        StringBuilder content = new StringBuilder();

        for (SinglyLinkedList.Node<SparceMatrix> layer = layers.getHead(); layer != null; layer = layer.next) {
            content.append("Capa No. ").append(layer.data.id).append("\n");
        }

        new CustomAlert("Todas las capas que son hojas", content.toString());
    }

    @FXML
    protected void heightLayersTree() {
        BinarySearchTree searchTree = temporalInformation.getLoguedUser().getCapas();
        new CustomAlert("Procesando...");
        String content = "La profundidad del árbol de capas es de: " + searchTree.getRoot().height;

        new CustomAlert("Profundidad del árbol de capas", content);
    }

    @FXML
    protected void listLayers() {
        BinarySearchTree searchTree = temporalInformation.getLoguedUser().getCapas();
        StringBuilder content = new StringBuilder();

        SinglyLinkedList<String> orders = new SinglyLinkedList<>();
        orders.addToList("PREORDER");
        orders.addToList("INORDER");
        orders.addToList("POSTORDER");

        for (SinglyLinkedList.Node<String> order = orders.getHead(); order != null; order = order.next) {
            searchTree.orderLayers(order.data);
            new CustomAlert("Procesando...");
            SinglyLinkedList<SparceMatrix> layers = searchTree.getOrderedLayers();

            content.append(order.data).append(": ");
            for (SinglyLinkedList.Node<SparceMatrix> layer = layers.getHead(); layer != null; layer = layer.next) {
                content.append(layer.data.id).append(", ");
            }
            content.deleteCharAt(content.length() - 1);
            content.append("\n");
        }

        new CustomAlert("Capas listadas", content.toString());
    }
}
