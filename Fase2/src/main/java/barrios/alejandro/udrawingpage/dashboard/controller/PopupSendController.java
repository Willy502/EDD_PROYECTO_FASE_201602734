package barrios.alejandro.udrawingpage.dashboard.controller;

import barrios.alejandro.udrawingpage.place.model.Town;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyLinkedList;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyNode;
import barrios.alejandro.udrawingpage.structures.hash.HashTable;
import barrios.alejandro.udrawingpage.users.model.Courier;
import barrios.alejandro.udrawingpage.utils.TemporalInformation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class PopupSendController {

    private final TemporalInformation temporalInformation;
    @FXML
    protected ComboBox<Courier> comboCourier;
    @FXML
    protected ComboBox<Town> comboFranchise;
    @FXML
    protected Button btnSend;

    public PopupSendController() {
        temporalInformation = TemporalInformation.getInstance();
    }

    @FXML
    public void initialize() {
        populateCombos();
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

}
