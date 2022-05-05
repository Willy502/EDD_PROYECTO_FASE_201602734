package barrios.alejandro.udrawingpage.structures.Adjacency;

import barrios.alejandro.udrawingpage.place.model.Town;

public class AdjacencyNode {

    public Town data;
    public AdjacencyNode next;
    public int weight;

    public AdjacencyNode() {}

    public AdjacencyNode(Town town, int weight) {
        data = town;
        this.weight = weight;
    }

    public AdjacencyNode(Town town) {
        data = town;
    }

}
