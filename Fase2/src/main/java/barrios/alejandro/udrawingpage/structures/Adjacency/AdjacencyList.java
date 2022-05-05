package barrios.alejandro.udrawingpage.structures.Adjacency;

import barrios.alejandro.udrawingpage.place.model.Town;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyLinkedList;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyNode;

public class AdjacencyList {

    int vertex;
    SinglyLinkedList<AdjacencyNode> towns;

    public AdjacencyList(int vertex) {
        this.vertex = vertex;
        towns = new SinglyLinkedList<>();
    }

    public void addEdge(Town source, Town destination, int weight) {

        // add edge
        SinglyNode<AdjacencyNode> current;
        for (current = towns.getHead(); current != null; current = current.next) {
            if (current.data.data.getId() == source.getId()) break;
        }

        if (current != null) {
            AdjacencyNode currentAdj = current.data;
            while (currentAdj.next != null) currentAdj = currentAdj.next;
            currentAdj.next = new AdjacencyNode(destination, weight);
        } else {
            current = towns.getHead();
            while (current.next != null) current = current.next;
            AdjacencyNode newNode = new AdjacencyNode(source);
            newNode.next = new AdjacencyNode(destination, weight);
            current.next = new SinglyNode<>(newNode);
        }

        // add back edge
        for (current = towns.getHead(); current != null; current = current.next) {
            if (current.data.data.getId() == destination.getId()) break;
        }

        if (current != null) {
            AdjacencyNode currentAdj = current.data;
            while (currentAdj.next != null) currentAdj = currentAdj.next;
            currentAdj.next = new AdjacencyNode(source, weight);
        } else {
            current = towns.getHead();
            while (current.next != null) current = current.next;
            AdjacencyNode newNode = new AdjacencyNode(destination);
            newNode.next = new AdjacencyNode(source, weight);
            current.next = new SinglyNode<>(newNode);
        }

    }

    public void printGraph() {
        for (int i = 0; i < towns.size(); i++) {
            System.out.println("Vertex " + towns.getPos(i).data.getId() + " is connected to: ");
            AdjacencyNode node = towns.getPos(i);
            node = node.next;
            while (node != null) {
                System.out.print(node.data.getId() + " ");
                node = node.next;
            }
        }
    }

}
