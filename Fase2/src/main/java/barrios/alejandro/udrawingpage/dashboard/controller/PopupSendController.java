package barrios.alejandro.udrawingpage.dashboard.controller;

import barrios.alejandro.udrawingpage.place.model.Order;
import barrios.alejandro.udrawingpage.place.model.Route;
import barrios.alejandro.udrawingpage.place.model.Town;
import barrios.alejandro.udrawingpage.structures.Adjacency.AdjacencyList;
import barrios.alejandro.udrawingpage.structures.Adjacency.AdjacencyConnections;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyLinkedList;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyNode;
import barrios.alejandro.udrawingpage.structures.hash.HashTable;
import barrios.alejandro.udrawingpage.users.model.Courier;
import barrios.alejandro.udrawingpage.utils.CustomAlert;
import barrios.alejandro.udrawingpage.utils.TemporalInformation;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class PopupSendController {

    private final TemporalInformation temporalInformation;
    @FXML
    protected ComboBox<Courier> comboCourier;
    @FXML
    protected ComboBox<Town> comboFranchise;
    @FXML
    protected Label lblUbicacion;
    private SinglyLinkedList<Route> selected = new SinglyLinkedList<>();

    public PopupSendController() {
        temporalInformation = TemporalInformation.getInstance();
    }

    @FXML
    public void initialize() {
        populateCombos();

        String location = temporalInformation.getLoguedUser().getTown().toString();
        location += ", " + temporalInformation.getLoguedUser().getAddress();
        lblUbicacion.setText(location);
    }

    private void populateCombos() {
        if (temporalInformation.getCourierHashTable() != null) {
            HashTable<Courier> courierHashTable = temporalInformation.getCourierHashTable();
            comboCourier.getItems().clear();

            SinglyLinkedList<Long> ids = temporalInformation.getCourierIds();
            for (SinglyNode<Long> current = ids.getHead(); current != null; current = current.next) {
                Courier courier = courierHashTable.get(current.data);
                comboCourier.getItems().add(courier);
            }
        }

        if (temporalInformation.getTownSinglyLinkedList() != null) {
            SinglyLinkedList<Town> towns = temporalInformation.getTownSinglyLinkedList();
            comboFranchise.getItems().clear();

            for (SinglyNode<Town> current = towns.getHead(); current != null; current = current.next) {
                Town town = current.data;
                if (town.isSnSucursal())
                    comboFranchise.getItems().add(town);
            }
        }

    }

    @FXML
    protected void sendImage() {
        AdjacencyList adjacencyList = temporalInformation.getRoutes();

        AdjacencyConnections route = new AdjacencyConnections(comboFranchise.getValue());
        route.connections.copyList(saveConnections(adjacencyList.getEdge(comboFranchise.getValue()), comboFranchise.getValue(), route));
        checkBetterRoute(route);
    }

    private SinglyLinkedList<AdjacencyConnections> saveConnections(SinglyLinkedList<Route> connectionRoutes, Town mainVertix, AdjacencyConnections padre) {

        SinglyLinkedList<AdjacencyConnections> adjacency = new SinglyLinkedList<>();
        SinglyNode<Route> current = connectionRoutes.getHead();

        return runThroug(mainVertix, current, adjacency, padre);
        /*System.out.print("VERTIX: " + mainVertix.getId());
        for (SinglyNode<AdjencyConnections> adj = research.getHead(); adj != null; adj = adj.next) {
            System.out.print(" -> " + adj.data.vertix.getId());
        }*/

    }

    private SinglyLinkedList<AdjacencyConnections> runThroug(Town mainVertix, SinglyNode<Route> current, SinglyLinkedList<AdjacencyConnections> adjacency, AdjacencyConnections padre) {

        if (current == null)
            return adjacency;

        if (current.data.getTown().getId() != mainVertix.getId() && searchExistInFather(current.data.getTown(), padre) == null) {

            AdjacencyList subAdjacencyList = temporalInformation.getRoutes();
            AdjacencyConnections subAdjacencyConnection = new AdjacencyConnections(current.data.getTown(), current.data.getWeight(), padre);

            if (current.data.getTown().getId() != temporalInformation.getLoguedUser().getTown().getId()) {

                SinglyLinkedList<AdjacencyConnections> subAdjacency = new SinglyLinkedList<>();
                SinglyNode<Route> subCurrent = subAdjacencyList.getEdge(subAdjacencyConnection.vertix).getHead();
                subAdjacencyConnection.connections.copyList(runThroug(mainVertix, subCurrent, subAdjacency, subAdjacencyConnection));
            }

            adjacency.addToList(subAdjacencyConnection);
        }

        current = current.next;
        return runThroug(mainVertix, current, adjacency, padre);
    }

    private Town searchExistInFather(Town toSearch, AdjacencyConnections padre) {

        if (padre == null)
            return null;

        if (padre.vertix == toSearch)
            return padre.vertix;

        return searchExistInFather(toSearch, padre.padre);

    }

    private void checkBetterRoute(AdjacencyConnections adjacencyConnections) {

        SinglyLinkedList<AdjacencyConnections> adjacencies = adjacencyConnections.connections;
        SinglyNode<AdjacencyConnections> current = adjacencies.getHead();
        researchRoute(current);
        SinglyLinkedList<Route> routeInOrder = new SinglyLinkedList<>();

        int weight = 0;

        for (int i = selected.size() - 1; i >= 0; i--) {
            routeInOrder.addToList(selected.getPos(i));
        }

        StringBuilder route = new StringBuilder();
        for (SinglyNode<Route> temp = routeInOrder.getHead(); temp != null; temp = temp.next) {
            weight += temp.data.getWeight();
            route.append(" -> ").append(temp.data.getTown());
        }
        route.append("\n").append("Tiempo de espera: ").append(weight).append(" minutos");
        route.append("\n").append("Sede: ").append(comboFranchise.getValue());
        route.append("\n").append("Destino: ").append(temporalInformation.getLoguedUser().getTown()).append(", ").append(temporalInformation.getLoguedUser().getAddress());
        route.append("\n").append("Cliente: ").append(temporalInformation.getLoguedUser().getName());
        route.append("\n").append("Mensajero: ").append(comboCourier.getValue());

        temporalInformation.getOrders().addToList(new Order(
                temporalInformation.getLoguedUser(),
                routeInOrder,
                weight,
                comboCourier.getValue(),
                comboFranchise.getValue()
        ));

        temporalInformation.getLoguedUser().getMyOrders().addToList(new Order(
                null,
                routeInOrder,
                weight,
                comboCourier.getValue(),
                comboFranchise.getValue()
        ));

        new CustomAlert("Envío almacenado", route.toString());
        lblUbicacion.getScene().getWindow().hide();
    }

    private SinglyLinkedList<Route> researchRoute(SinglyNode<AdjacencyConnections> current) {

        if (current == null) {
            return selected;
        }

        if (current.data.vertix.getId() != temporalInformation.getLoguedUser().getTown().getId()) {
            SinglyNode<AdjacencyConnections> subCurrent = current.data.connections.getHead();
            researchRoute(subCurrent);
        } else {

            if (selected.size() > 0) {
                SinglyLinkedList<Route> temporal = new SinglyLinkedList<>();
                addWithFather(current.data, temporal);

                int weightTemp = 0;
                int weightSelected = 0;

                for (SinglyNode<Route> temp = temporal.getHead(); temp != null; temp = temp.next) {
                    weightTemp += temp.data.getWeight();
                }

                for (SinglyNode<Route> temp = selected.getHead(); temp != null; temp = temp.next) {
                    weightSelected += temp.data.getWeight();
                }

                if (weightTemp < weightSelected) {
                    selected = temporal;
                }

            } else {
                selected = addWithFather(current.data, selected);
            }

        }

        current = current.next;
        return researchRoute(current);
    }

    private SinglyLinkedList<Route> addWithFather(AdjacencyConnections padre, SinglyLinkedList<Route> selectedRoute) {

        if(padre == null)
            return selectedRoute;

        selectedRoute.addToList(new Route(
                padre.vertix,
                padre.weight
        ));

        return addWithFather(padre.padre, selectedRoute);

    }

}
