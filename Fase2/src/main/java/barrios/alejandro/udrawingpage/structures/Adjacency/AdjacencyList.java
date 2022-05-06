package barrios.alejandro.udrawingpage.structures.Adjacency;

import barrios.alejandro.udrawingpage.graph.Graph;
import barrios.alejandro.udrawingpage.place.model.Route;
import barrios.alejandro.udrawingpage.place.model.Town;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyLinkedList;
import barrios.alejandro.udrawingpage.structures.SinglyLinkedList.SinglyNode;

public class AdjacencyList {

    AdjacencyNode head;
    private int size = 0;
    private String result;

    public SinglyLinkedList<Route> getEdge(Town source) {

        AdjacencyNode current = head;

        while (current != null) {
            if (current.vertix == source)
                return current.connection;
            current = current.next;
        }

        return null;
    }

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

    public void buildGraph() {

        result = "digraph G {\n";
        result += "node[shape=square];\n";

        AdjacencyNode current = head;
        int i = 1;
        while (current != null) {

            result += "a" + current.vertix.getId() + "[label=" + current.vertix.getId() + " group=" + i + "];\n";
            i++;
            current = current.next;
        }

        // Basic connections
        current = head;
        while (current != null) {

            if (current.next != null) {
                result += "a" + current.vertix.getId() + " -> " + "a" + current.next.vertix.getId() + ";\n";
            }
            current = current.next;
        }

        // Subgraph
        i = 1;
        current = head;
        while (current != null) {

            SinglyLinkedList<Route> node = current.connection;
            SinglyNode<Route> sn = node.getHead();

            while (sn != null) {
                result += "a" + current.vertix.getId() + "a" + sn.data.getTown().getId() + "[label=" + sn.data.getTown().getId() + " group=" + i + "];\n";
                sn = sn.next;
            }

            i++;
            current = current.next;
        }

        // Sub connections
        current = head;
        while (current != null) {

            SinglyLinkedList<Route> node = current.connection;
            SinglyNode<Route> sn = node.getHead();

            if (sn != null)
                result += "a" + current.vertix.getId() + " -> " + "a" + current.vertix.getId() + "a" + sn.data.getTown().getId() + "[label=\"conecta con: \"];\n";
            while (sn != null) {

                if (sn.next != null) {
                    result += "a" + current.vertix.getId() + "a" + sn.data.getTown().getId() + " -> " + "a" + current.vertix.getId() + "a" + sn.next.data.getTown().getId() + "[arrowhead=none];\n";
                }
                sn = sn.next;
            }

            result += "{ rank=same;\n";
            result += "a" + current.vertix.getId() + ";\n";
            sn = node.getHead();
            while (sn != null) {
                result += "a" + current.vertix.getId() + "a" + sn.data.getTown().getId() + ";\n";
                sn = sn.next;
            }
            result += "}\n";

            current = current.next;
        }

        result += "}";
        Graph.GenerarImagen("ADJACENCY_LIST", result);
    }

}
