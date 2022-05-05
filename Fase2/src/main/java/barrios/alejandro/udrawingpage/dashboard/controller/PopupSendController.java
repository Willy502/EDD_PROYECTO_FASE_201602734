package barrios.alejandro.udrawingpage.dashboard.controller;

import barrios.alejandro.udrawingpage.place.model.Route;
import barrios.alejandro.udrawingpage.place.model.Town;
import barrios.alejandro.udrawingpage.structures.Adjacency.AdjacencyList;
import barrios.alejandro.udrawingpage.structures.Adjacency.AdjencyConnections;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyLinkedList;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyNode;
import barrios.alejandro.udrawingpage.structures.hash.HashTable;
import barrios.alejandro.udrawingpage.users.model.Courier;
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

        AdjencyConnections route = new AdjencyConnections(comboFranchise.getValue());
        //route.connections.copyList(saveConnections( adjacencyList.getEdge(comboFranchise.getValue()) ));
        saveConnections(adjacencyList.getEdge(comboFranchise.getValue()), comboFranchise.getValue(), route);

    }

    private void saveConnections(SinglyLinkedList<Route> connectionRoutes, Town mainVertix, AdjencyConnections padre) {

        AdjacencyList adjacencyList = temporalInformation.getRoutes();
        SinglyLinkedList<AdjencyConnections> adjency = new SinglyLinkedList<>();

        SinglyNode<Route> current = connectionRoutes.getHead();

        SinglyLinkedList<AdjencyConnections> research = runThroug(mainVertix, current, adjency, padre);
        System.out.print("VERTIX: " + mainVertix.getId());
        for (SinglyNode<AdjencyConnections> adj = research.getHead(); adj != null; adj = adj.next) {
            System.out.print(" -> " + adj.data.vertix.getId());
        }

    }

    private SinglyLinkedList<AdjencyConnections> runThroug(Town mainVertix, SinglyNode<Route> current, SinglyLinkedList<AdjencyConnections> adjency, AdjencyConnections padre) {

        if (current == null)
            return adjency;

        if (current.data.getTown().getId() != mainVertix.getId() && searchExistInFather(current.data.getTown(), padre) == null) {
            System.out.println("PRIMARY: " + current.data.getTown().getId());
            AdjacencyList subAdjacencyList = temporalInformation.getRoutes();
            AdjencyConnections subAdjencyConnection = new AdjencyConnections(current.data.getTown(), current.data.getWeight(), padre);

            if (current.data.getTown().getId() != temporalInformation.getLoguedUser().getTown().getId()) {

                SinglyLinkedList<AdjencyConnections> subAdjency = new SinglyLinkedList<>();
                SinglyNode<Route> subCurrent = subAdjacencyList.getEdge(subAdjencyConnection.vertix).getHead();
                subAdjencyConnection.connections.copyList(runThroug(mainVertix, subCurrent, subAdjency, subAdjencyConnection));
            }

            adjency.addToList(subAdjencyConnection);
        }

        current = current.next;
        return runThroug(mainVertix, current, adjency, padre);
    }

    private Town searchExistInFather(Town toSearch, AdjencyConnections padre) {

        if (padre == null)
            return null;

        if (padre.vertix == toSearch)
            return padre.vertix;

        return searchExistInFather(toSearch, padre.padre);

    }

}
