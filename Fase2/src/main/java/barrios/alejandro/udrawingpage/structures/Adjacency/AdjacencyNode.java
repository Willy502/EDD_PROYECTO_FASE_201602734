package barrios.alejandro.udrawingpage.structures.Adjacency;

import barrios.alejandro.udrawingpage.place.model.Town;

public class AdjacencyNode {

    public Town data;
    public AdjacencyNode next;

    public AdjacencyNode() {}

    public AdjacencyNode(Town town) {
        data = town;
    }

}
