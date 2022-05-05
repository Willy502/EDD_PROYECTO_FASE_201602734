package barrios.alejandro.udrawingpage.structures.Adjacency;

import barrios.alejandro.udrawingpage.place.model.Route;
import barrios.alejandro.udrawingpage.place.model.Town;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyLinkedList;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyNode;

public class AdjacencyList {

    AdjacencyNode head;
    private int size = 0;

    public void addEdge(Town source, Town destination, int weight) {

        if (head == null) {
            head = new AdjacencyNode(source);
            head.connection.addToList(new Route(destination, weight));
            size++;
            if (source.getId() != destination.getId()) {
                AdjacencyNode nextAdj = new AdjacencyNode(destination);
                nextAdj.connection.addToList(new Route(source, weight));
                head.next = nextAdj;
                size++;
            }
            return;
        }

        AdjacencyNode current = head;
        boolean vertixFound = false;
        while (current != null) {

            if (current.vertix.getId() == source.getId()) {
                vertixFound = true;
                break;
            }

            if (current.next == null)
                break;
            current = current.next;
        }

        if (vertixFound) {
            current.connection.addToList(new Route(destination, weight));
        } else {
            AdjacencyNode newNode = new AdjacencyNode(source);
            newNode.connection.addToList(new Route(destination, weight));
            current.next = newNode;
            size++;
        }


        // add edge back
        current = head;
        vertixFound = false;
        while (current != null) {

            if (current.vertix.getId() == destination.getId()) {
                vertixFound = true;
                break;
            }

            if (current.next == null)
                break;
            current = current.next;
        }

        if (vertixFound) {
            current.connection.addToList(new Route(source, weight));
        } else {
            AdjacencyNode newNode = new AdjacencyNode(destination);
            newNode.connection.addToList(new Route(source, weight));
            current.next = newNode;
            size++;
        }

    }

    public void printGraph() {
        System.out.println(size);
        AdjacencyNode current = head;
        while (current != null) {
            System.out.print("Vertex " + current.vertix.getId() + " is connected to: ");
            SinglyLinkedList<Route> node = current.connection;
            SinglyNode<Route> sn = node.getHead();
            while (sn != null) {
                System.out.print(sn.data.getTown().getId() + " ");
                sn = sn.next;
            }
            System.out.println("");
            current = current.next;
        }
    }

}
