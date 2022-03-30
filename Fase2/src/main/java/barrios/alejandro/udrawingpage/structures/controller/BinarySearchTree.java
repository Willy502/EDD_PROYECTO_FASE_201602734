package barrios.alejandro.udrawingpage.structures.controller;

import barrios.alejandro.udrawingpage.graph.Graph;
import barrios.alejandro.udrawingpage.utils.TemporalInformation;

public class BinarySearchTree { // Image
    // Delete missing

    public int id;
    private Node root;
    private String result;
    private SinglyLinkedList<SparceMatrix> layers;
    private SinglyLinkedList<SparceMatrix> leafLayers;

    public BinarySearchTree(int id) {
        this.id = id;
    }

    public BinarySearchTree() {

    }

    public Node getRoot() {
        return root;
    }

    public void insert(SparceMatrix capa) {
        if (root == null) {
            root = new Node(capa);
            root.leaf = false;
            return;
        }
        binaryInsert(root, capa);
    }

    private void binaryInsert(Node current, SparceMatrix capa) {
        if (capa.id >= current.capa.id) {
            // right
            if (current.rightBranch == null) {
                current.rightBranch = new Node(capa);
                current.leaf = false;
            } else {
                current = current.rightBranch;
                binaryInsert(current, capa);
            }
        } else {
            // left
            if (current.leftBranch == null) {
                current.leftBranch = new Node(capa);
                current.leaf = false;
            } else {
                current = current.leftBranch;
                binaryInsert(current, capa);
            }
        }
        current.height = 1 + maxHeight(height(current.leftBranch), height(current.rightBranch));
    }

    public SparceMatrix searchLayer(int idCapa) {
        return binarySearch(root, idCapa);
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

    private void recursiveGraph(Node nodo, Node parent, String color) {
        if (nodo == null) return;

        result += "sub_" + nodo.capa.id + "[label=\"" + nodo.capa.id + "\" width=.5 height=.5, style=filled, color=" + color +"];\n";
        if (parent != null) {
            result += "sub_" + parent.capa.id + " -> sub_" + nodo.capa.id + ";\n";
        }
        recursiveGraph(nodo.leftBranch, nodo, color);

        recursiveGraph(nodo.rightBranch, nodo, color);
    }

    public void graphBinary() {

        result = "digraph G {\n";
        result += "node[shape=circle];\n";

        recursiveGraph(root, null, "green");

        result += "}\n";
        Graph.GenerarImagen("BINARY_LAYER", result);
    }

    public void graphBinaryWithImage(String imageId) {
        result = "digraph G {\n";
        result += "node[shape=circle];\n";

        TemporalInformation temporalInformation = TemporalInformation.getInstance();
        String treeGraph = temporalInformation.getLoguedUser().getImages().graphAvlIntern();
        result += treeGraph;

        recursiveGraph(root, null,"yellow");

        result += imageId + " -> sub_" + root.capa.id;
        result += "}\n";
        Graph.GenerarImagen("BINARY_LAYER", result);
    }

    public void orderLayers(String order) {
        layers = new SinglyLinkedList<>();
        leafLayers = new SinglyLinkedList<>();
        switch (order) {
            case "PREORDER" -> preorder(root);
            case "INORDER" -> inorder(root);
            case "POSTORDER" -> postorder(root);
            case "AMPLITUD" -> amplitud(root);
        }

    }

    public SinglyLinkedList<SparceMatrix> getLeafLayers() {
        return leafLayers;
    }

    private void preorder(Node nodo) {
        if (nodo == null) return;
        if(nodo.leaf) leafLayers.addToList(nodo.capa);
        layers.addToList(nodo.capa);

        preorder(nodo.leftBranch);
        preorder(nodo.rightBranch);
    }

    private void inorder(Node nodo) {
        if (nodo == null) return;
        inorder(nodo.leftBranch);
        layers.addToList(nodo.capa);
        inorder(nodo.rightBranch);
    }

    private void postorder(Node nodo) {
        if (nodo == null) return;
        postorder(nodo.leftBranch);
        postorder(nodo.rightBranch);
        layers.addToList(nodo.capa);
    }

    private void amplitud(Node node) {
        Queue<Node> queue = new Queue<>();
        Node aux = null;

        if (node != null) {
            queue.queue(node);

            while(!queue.isEmpty()) {
                aux = queue.getFirst();
                queue.pop();
                layers.addToList(aux.capa);
                if (aux.leftBranch != null)
                    queue.queue(aux.leftBranch);
                if (aux.rightBranch != null)
                    queue.queue(aux.rightBranch);
            }
        }

        System.out.println();

    }

    public SinglyLinkedList<SparceMatrix> getOrderedLayers() {
        return layers;
    }

    private int maxHeight(int a, int b) {
        return Math.max(a, b);
    }

    private int height(Node current) {
        if (current == null) return 0;
        return current.height;
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }

    public static class Node {
        SparceMatrix capa;
        boolean leaf;
        public int height;
        Node rightBranch, leftBranch;

        public Node(SparceMatrix capa) {
            this.capa = capa;
            leaf = true;
            height = 1;
        }
    }

}
