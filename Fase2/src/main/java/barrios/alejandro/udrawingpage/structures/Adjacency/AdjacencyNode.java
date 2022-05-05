package barrios.alejandro.udrawingpage.structures.Adjacency;

import barrios.alejandro.udrawingpage.place.model.Route;
import barrios.alejandro.udrawingpage.place.model.Town;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyLinkedList;

public class AdjacencyNode {

    public Town vertix;
    public SinglyLinkedList<Route> connection;
    public AdjacencyNode next;

    public AdjacencyNode(Town vertix) {
        this.vertix = vertix;
        connection = new SinglyLinkedList<>();
    }

}
