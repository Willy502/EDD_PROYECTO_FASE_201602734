package barrios.alejandro.udrawingpage.structures.Adjacency;

import barrios.alejandro.udrawingpage.place.model.Town;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyLinkedList;

public class AdjacencyConnections {

    public Town vertix;
    public int weight;
    public SinglyLinkedList<AdjacencyConnections> connections;
    public AdjacencyConnections padre;

    public AdjacencyConnections(Town vertix) {
        this.vertix = vertix;
        connections = new SinglyLinkedList<>();
    }

    public AdjacencyConnections(Town vertix, int weight, AdjacencyConnections padre) {
        this.vertix = vertix;
        this.weight = weight;
        this.padre = padre;
        connections = new SinglyLinkedList<>();
    }

}
