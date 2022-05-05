package barrios.alejandro.udrawingpage.utils;

import barrios.alejandro.udrawingpage.place.model.Town;
import barrios.alejandro.udrawingpage.structures.controller.BTreeV2;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyLinkedList;
import barrios.alejandro.udrawingpage.structures.hash.HashTable;
import barrios.alejandro.udrawingpage.users.model.Courier;
import barrios.alejandro.udrawingpage.users.model.User;

public class TemporalInformation {

    private static TemporalInformation temporalInformation = null;
    private BTreeV2 usersTree;
    private HashTable<Courier> courierHashTable;
    private SinglyLinkedList<Town> townSinglyLinkedList;
    private User loguedUser;

    public static TemporalInformation getInstance() {
        if (temporalInformation == null)
            temporalInformation = new TemporalInformation();
        return temporalInformation;
    }

    public BTreeV2 getUsersTree() {
        return usersTree;
    }

    public void setUsersTree(BTreeV2 usersTree) {
        this.usersTree = usersTree;
    }

    public User getLoguedUser() {
        return loguedUser;
    }

    public void setLoguedUser(User loguedUser) {
        this.loguedUser = loguedUser;
    }

    public HashTable<Courier> getCourierHashTable() {
        return courierHashTable;
    }

    public void setCourierHashTable(HashTable<Courier> courierHashTable) {
        this.courierHashTable = courierHashTable;
    }

    public SinglyLinkedList<Town> getTownSinglyLinkedList() {
        return townSinglyLinkedList;
    }

    public void setTownSinglyLinkedList(SinglyLinkedList<Town> townSinglyLinkedList) {
        this.townSinglyLinkedList = townSinglyLinkedList;
    }
}
