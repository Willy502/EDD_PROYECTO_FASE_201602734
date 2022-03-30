package barrios.alejandro.udrawingpage.dashboard.controller;

import barrios.alejandro.udrawingpage.structures.controller.*;
import barrios.alejandro.udrawingpage.users.model.Rol;
import barrios.alejandro.udrawingpage.users.model.User;
import barrios.alejandro.udrawingpage.utils.CustomAlert;
import barrios.alejandro.udrawingpage.utils.TemporalInformation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class ReportsController {

    @FXML
    protected VBox vboxAdmin, vboxClient, main;
    @FXML
    protected TextField txtDpi;

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
        SinglyLinkedList<BinarySearchTree> images;
        try {
            images = temporalInformation.getLoguedUser().getImages().getImages();
        } catch (Exception e) {
            new CustomAlert("Error", "No se ha cargado información previa");
            return;
        }
         
        if (images == null) {
            new CustomAlert("Error", "No se ha cargado información previa");
            return;
        }

        SinglyLinkedList<HashMap<String, Integer>> toBeOrdered = new SinglyLinkedList<>();

        for (SinglyLinkedList.Node<BinarySearchTree> image = images.getHead(); image != null; image = image.next) {
            SinglyLinkedList.Node<BinarySearchTree> finalImage = image;
            image.data.orderLayers("PREORDER");
            SinglyLinkedList<SparceMatrix> layer = finalImage.data.getOrderedLayers();
            HashMap<String, Integer> info = new HashMap<>(){{
                put("id", finalImage.data.id);
                put("layers", layer.size());
            }};
            toBeOrdered.addToList(info);
        }
        SinglyLinkedList<HashMap<String, Integer>> ordered = sortImages(toBeOrdered);
        new CustomAlert("Procesando...");
        StringBuilder content = new StringBuilder();

        int j = 0;
        for (SinglyLinkedList.Node<HashMap<String, Integer>> i = ordered.getHead(); i != null; i = i.next) {
            content.append("Image No. ").append(i.data.get("id")).append(" -> ").append(i.data.get("layers")).append(" capas\n");
            j++;
            if (j == 5) break;
        }
        new CustomAlert("Imágenes con más capas", content.toString());
    }

    private SinglyLinkedList<HashMap<String, Integer>> sortImages(SinglyLinkedList<HashMap<String, Integer>> toOrdered) {

        if (toOrdered.size() > 1) {
            boolean swapped = true;
            while (swapped) {

                SinglyLinkedList.Node<HashMap<String, Integer>> previous = null;
                SinglyLinkedList.Node<HashMap<String, Integer>> current = toOrdered.getHead();
                swapped = false;

                for (SinglyLinkedList.Node<HashMap<String, Integer>> next = current.next; next != null; next = current.next) {

                    if (current.data.get("layers") < next.data.get("layers")) {
                        if (previous != null) {
                            previous.next = next;
                        } else {
                            toOrdered.setFirstNode(next);
                        }
                        current.next = next.next;
                        next.next = current;
                        previous = next;
                        swapped = true;
                    } else {
                        previous = current;
                        current = next;
                    }
                }
            }
        }
        return toOrdered;
    }

    @FXML
    protected void leafLayers() {
        BinarySearchTree searchTree = temporalInformation.getLoguedUser().getCapas();

        if (searchTree == null) {
            new CustomAlert("Error", "No se ha cargado información previa");
            return;
        }

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

        if (searchTree == null) {
            new CustomAlert("Error", "No se ha cargado información previa");
            return;
        }

        new CustomAlert("Procesando...");
        String content = "La profundidad del árbol de capas es de: " + searchTree.getRoot().height;

        new CustomAlert("Profundidad del árbol de capas", content);
    }

    @FXML
    protected void listLayers() {
        BinarySearchTree searchTree = temporalInformation.getLoguedUser().getCapas();

        if (searchTree == null) {
            new CustomAlert("Error", "No se ha cargado información previa");
            return;
        }

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

    @FXML
    protected void searchUser() {
        String dpi = txtDpi.getText();
        long dpiToSearch;
        try {
            dpiToSearch = Long.parseLong(dpi);
        } catch (Exception e) {
            new CustomAlert("Error", "El campo esta vacio");
            return;
        }
        User user = temporalInformation.getUsersTree().searchUserByDpi(dpiToSearch);
        if (user != null) {
            StringBuilder content = new StringBuilder("Nombre: " + user.getName() + "\n" +
                    "DPI: " + user.dpi + "\n" +
                    "Contraseña: " + user.getPassword() + "\n");

            content.append("Cantidad de albumes: ").append(user.getAlbumes().size()).append("\n");
            DoublyCircularLinkedList.Node node = user.getAlbumes().getHead();

            do {
                if (node == null) break;
                content.append(node.album.name).append(": ").append(node.album.images.size()).append(" imágenes \n");
                node = node.next;
            } while (!node.imHead);

            content.append("Cantidad de imágenes totales: ").append(user.getImages().getImages().size()).append("\n");
            BinarySearchTree capas = user.getCapas();
            capas.orderLayers("PREORDER");

            content.append("Cantidad de capas totales: ").append(capas.getOrderedLayers().size()).append("\n");

            new CustomAlert("Reporte de usuario", content.toString());

        } else {
            new CustomAlert("Usuario no encontrado", "El usuario ingresado no ha podido ser encontrado");
        }
    }

    @FXML
    protected void searchByLevels() {
        SinglyLinkedList<User> users = temporalInformation.getUsersTree().searchByLevels();
        StringBuilder content = new StringBuilder();
        for (SinglyLinkedList.Node<User> user = users.getHead(); user != null; user = user.next) {
            content.append(user.data.dpi).append(" - ").append(user.data.getName()).append("\n");
        }

        new CustomAlert("Usuario recorriendo por niveles", content.toString());
    }
}
