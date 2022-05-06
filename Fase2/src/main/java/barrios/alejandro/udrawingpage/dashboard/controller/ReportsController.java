package barrios.alejandro.udrawingpage.dashboard.controller;

import barrios.alejandro.udrawingpage.place.model.Order;
import barrios.alejandro.udrawingpage.place.model.Route;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyLinkedList;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyNode;
import barrios.alejandro.udrawingpage.structures.controller.*;
import barrios.alejandro.udrawingpage.users.model.Courier;
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

        for (SinglyNode<BinarySearchTree> image = images.getHead(); image != null; image = image.next) {
            SinglyNode<BinarySearchTree> finalImage = image;
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
        for (SinglyNode<HashMap<String, Integer>> i = ordered.getHead(); i != null; i = i.next) {
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

                SinglyNode<HashMap<String, Integer>> previous = null;
                SinglyNode<HashMap<String, Integer>> current = toOrdered.getHead();
                swapped = false;

                for (SinglyNode<HashMap<String, Integer>> next = current.next; next != null; next = current.next) {

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

        for (SinglyNode<SparceMatrix> layer = layers.getHead(); layer != null; layer = layer.next) {
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

        for (SinglyNode<String> order = orders.getHead(); order != null; order = order.next) {
            searchTree.orderLayers(order.data);
            new CustomAlert("Procesando...");
            SinglyLinkedList<SparceMatrix> layers = searchTree.getOrderedLayers();

            content.append(order.data).append(": ");
            for (SinglyNode<SparceMatrix> layer = layers.getHead(); layer != null; layer = layer.next) {
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
                    "DPI: " + user.getDpi() + "\n" +
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
        for (SinglyNode<User> user = users.getHead(); user != null; user = user.next) {
            content.append(user.data.getDpi()).append(" - ").append(user.data.getName()).append("\n");
        }

        new CustomAlert("Usuario recorriendo por niveles", content.toString());
    }

    @FXML
    protected void top10Long() {
        SinglyLinkedList<Order> orders = temporalInformation.getOrders();

        if (orders.size() > 1) {
            boolean swapped = true;
            while (swapped) {

                SinglyNode<Order> previous = null;
                SinglyNode<Order> current = orders.getHead();
                swapped = false;

                for (SinglyNode<Order> next = current.next; next != null; next = current.next) {

                    if (current.data.getTotalWeight() < next.data.getTotalWeight()) {
                        if (previous != null) {
                            previous.next = next;
                        } else {
                            orders.setFirstNode(next);
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

        int i = 0;
        StringBuilder route = new StringBuilder();
        for (SinglyNode<Order> order = orders.getHead(); order != null; order = order.next) {
            i++;
            if (i >= 10) break;


            route.append(i).append(". ");
            for (SinglyNode<Route> temp = order.data.getRoute().getHead(); temp != null; temp = temp.next) {
                route.append(" -> ").append(temp.data.getTown());
            }
            route.append("\n").append("Tiempo de espera: ").append(order.data.getTotalWeight()).append(" minutos.").append("\n\n");

        }

        new CustomAlert("Top 10 entregas con más distancia", route.toString());

    }

    @FXML
    protected void top10ClientsWithMoreOrders() {
        SinglyLinkedList<User> users = temporalInformation.getUsersTree().searchByLevels();

        if (users.size() > 1) {
            boolean swapped = true;
            while (swapped) {

                SinglyNode<User> previous = null;
                SinglyNode<User> current = users.getHead();
                swapped = false;

                for (SinglyNode<User> next = current.next; next != null; next = current.next) {

                    if (current.data.getMyOrders().size() < next.data.getMyOrders().size()) {
                        if (previous != null) {
                            previous.next = next;
                        } else {
                            users.setFirstNode(next);
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

        int i = 0;
        StringBuilder content = new StringBuilder();
        for (SinglyNode<User> user = users.getHead(); user != null; user = user.next) {
            i++;
            if (i >= 10) break;

            content.append(i).append(". ").append(user.data).append(" | Ordenes: ").append(user.data.getMyOrders().size()).append("\n\n");
        }

        new CustomAlert("Top 10 clientes con más órdenes", content.toString());

    }

    @FXML
    protected void top10CouriersWithMoreOrders() {
        SinglyLinkedList<Long> courierIds = temporalInformation.getCourierIds();
        SinglyLinkedList<Courier> couriers = new SinglyLinkedList<>();

        for (SinglyNode<Long> courierId = courierIds.getHead(); courierId != null; courierId = courierId.next) {
            Courier courier = temporalInformation.getCourierHashTable().get(courierId.data);
            couriers.addToList(courier);
        }

        if (couriers.size() > 1) {
            boolean swapped = true;
            while (swapped) {

                SinglyNode<Courier> previous = null;
                SinglyNode<Courier> current = couriers.getHead();
                swapped = false;

                for (SinglyNode<Courier> next = current.next; next != null; next = current.next) {

                    if (current.data.getOrders().size() < next.data.getOrders().size()) {
                        if (previous != null) {
                            previous.next = next;
                        } else {
                            couriers.setFirstNode(next);
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

        int i = 0;
        StringBuilder content = new StringBuilder();
        for (SinglyNode<Courier> courier = couriers.getHead(); courier != null; courier = courier.next) {
            i++;
            if (i >= 10) break;

            content.append(i).append(". ").append(courier.data).append(" | Ordenes: ").append(courier.data.getOrders().size()).append("\n\n");
        }

        new CustomAlert("Top 10 mensajeros con más órdenes", content.toString());

    }
}
