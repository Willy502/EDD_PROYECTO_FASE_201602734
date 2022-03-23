package barrios.alejandro.udrawingpage.structures.controller;

import barrios.alejandro.udrawingpage.graph.Graph;

public class BinarySearchTree { // Image
    // Delete missing

    public int id;
    private Node root;
    private String result;

    public BinarySearchTree(int id) {
        this.id = id;
    }

    public BinarySearchTree() {

    }

    public void insert(SparceMatrix capa) {
        if (root == null) {
            root = new Node(capa);
            return;
        }
        binaryInsert(root, capa);
    }

    private void binaryInsert(Node current, SparceMatrix capa) {
        if (capa.id >= current.capa.id) {
            // right
            if (current.rightBranch == null) {
                current.rightBranch = new Node(capa);
            } else {
                current = current.rightBranch;
                binaryInsert(current, capa);
            }
        } else {
            // left
            if (current.leftBranch == null) {
                current.leftBranch = new Node(capa);
            } else {
                current = current.leftBranch;
                binaryInsert(current, capa);
            }
        }
    }

    public SparceMatrix searchLayer(int idCapa) {
        SparceMatrix value = binarySearch(root, idCapa);
        return value;
    }

    private SparceMatrix binarySearch(Node current, int idCapa) {
        if (idCapa == current.capa.id) return current.capa;

        if (idCapa > current.capa.id) {
            // right
            if (current.rightBranch != null) {
                current = current.rightBranch;
                return binarySearch(current, idCapa);
            } else {
                return null;
            }
        } else if (idCapa < current.capa.id) {
            // left
            if (current.leftBranch != null) {
                current = current.leftBranch;
                return binarySearch(current, idCapa);
            } else {
                return null;
            }
        }
        return null;
    }

    private void recursiveGraph(Node nodo, Node parent) {
        if (nodo == null) return;

        result += nodo.capa.id + "[width=.5 height=.5, style=filled, color=green];\n";
        if (parent != null) {
            result += parent.capa.id + " -> " + nodo.capa.id + ";\n";
        }
        recursiveGraph(nodo.leftBranch, nodo);

        recursiveGraph(nodo.rightBranch, nodo);
    }

    public void graphBinary() {

        result = "digraph G {\n";
        result += "node[shape=circle];\n";

        recursiveGraph(root, null);

        result += "}\n";
        Graph.GenerarImagen("BINARY_LAYER", result);
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }

    static class Node {
        SparceMatrix capa;
        Node rightBranch, leftBranch;

        public Node(SparceMatrix capa) {
            this.capa = capa;
        }
    }

}
