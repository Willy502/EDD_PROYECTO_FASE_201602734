package barrios.alejandro.udrawingpage.structures.Adjacency;

import barrios.alejandro.udrawingpage.place.model.Route;
import barrios.alejandro.udrawingpage.place.model.Town;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyLinkedList;

public class AdjencyConnections {

    public Town vertix;
    public int weight;
    public SinglyLinkedList<AdjencyConnections> connections;
    public AdjencyConnections padre;

    public AdjencyConnections(Town vertix) {
        this.vertix = vertix;
        connections = new SinglyLinkedList<>();
    }

    public AdjencyConnections(Town vertix, int weight, AdjencyConnections padre) {
        this.vertix = vertix;
        this.weight = weight;
        this.padre = padre;
        connections = new SinglyLinkedList<>();
    }

}
