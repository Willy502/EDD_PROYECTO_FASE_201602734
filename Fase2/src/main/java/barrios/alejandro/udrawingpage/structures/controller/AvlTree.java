package barrios.alejandro.udrawingpage.structures.controller;

import barrios.alejandro.udrawingpage.graph.Graph;

public class AvlTree { // Images tree
    // Delete missing

    private Node root;
    private String result;
    private SinglyLinkedList<BinarySearchTree> images;

    public void insert(BinarySearchTree image) {
        if (root == null) {
            root = new Node(image);
            return;
        }
        binaryInsert(root, image);
    }

    private void binaryInsert(Node current, BinarySearchTree image) {

        if (image.id >= current.image.id) {
            // right
            if (current.rightBranch == null) {
                current.rightBranch = new Node(image);
            } else {
                current = current.rightBranch;
                binaryInsert(current, image);
            }
        } else {
            // left
            if (current.leftBranch == null) {
                current.leftBranch = new Node(image);
            } else {
                current = current.leftBranch;
                binaryInsert(current, image);
            }
        }

        current.height = 1 + maxHeight(height(current.leftBranch), height(current.rightBranch));

        int fe = getFE(current); // Factor de equilibrio

        // right rotate
        if (fe > 1 && image.id < current.leftBranch.image.id) root = rightRotate(current);

        // Left rotate
        if (fe < -1 && image.id > current.rightBranch.image.id) root = leftRotate(current);

        // Double right rotate
        if (fe > 1 && image.id > current.leftBranch.image.id) {
            current.leftBranch = leftRotate(current.leftBranch);
            root = rightRotate(current);
        }

        // Double left rotate
        if (fe < -1 && image.id < current.rightBranch.image.id) {
            current.rightBranch = rightRotate(current.rightBranch);
            root = leftRotate(current);
        }
    }

    private int getFE(Node current) {
        if (current == null) return 0;

        return height(current.leftBranch) - height(current.rightBranch);
    }

    private int height(Node current) {
        if (current == null) return 0;
        return current.height;
    }

    public void fillImages() {
        images = new SinglyLinkedList<>();
        fillImages(root);

    }
    private void fillImages(Node nodo) {
        if (nodo == null) return;
        images.addToList(nodo.image);

        fillImages(nodo.leftBranch);
        fillImages(nodo.rightBranch);
    }

    private int maxHeight(int a, int b) {
        return Math.max(a, b);
    }

    private Node rightRotate(Node current) {
        Node newRoot = current.leftBranch;
        Node temp = newRoot.rightBranch;

        newRoot.rightBranch = current;
        current.leftBranch = temp;

        current.height = maxHeight(height(current.leftBranch), height(current.rightBranch)) + 1;
        newRoot.height = maxHeight(height(newRoot.leftBranch), height(newRoot.rightBranch)) + 1;

        return newRoot;
    }

    private Node leftRotate(Node current) {
        Node newRoot = current.rightBranch;
        Node temp = newRoot.leftBranch;

        newRoot.leftBranch = current;
        current.rightBranch = temp;

        current.height = maxHeight(height(current.leftBranch), height(current.rightBranch)) + 1;
        newRoot.height = maxHeight(height(newRoot.leftBranch), height(newRoot.rightBranch)) + 1;

        return newRoot;
    }

    public BinarySearchTree search(int idImage) {
        return binarySearch(root, idImage);
    }

    private BinarySearchTree binarySearch(Node current, int idImage) {

        if (idImage == current.image.id) return current.image;

        if (idImage > current.image.id) {
            // right
            if (current.rightBranch != null) {
                current = current.rightBranch;
                return binarySearch(current, idImage);
            } else {
                return null;
            }
        } else {
            // left
            if (current.leftBranch != null) {
                current = current.leftBranch;
                return binarySearch(current, idImage);
            } else {
                return null;
            }
        }
    }

    private void recursiveGraph(Node nodo, Node parent) {
        if (nodo == null) return;

        result += nodo.image.id + "[width=.5 height=.5, style=filled, color=green];\n";
        if (parent != null) {
            result += parent.image.id + " -> " + nodo.image.id + ";\n";
        }
        recursiveGraph(nodo.leftBranch, nodo);

        recursiveGraph(nodo.rightBranch, nodo);
    }

    public void graphAvl() {

        result = "digraph G {\n";
        result += "node[shape=circle];\n";

        recursiveGraph(root, null);

        result += "}\n";
        Graph.GenerarImagen("AVL_IMAGENES", result);
    }

    public SinglyLinkedList<BinarySearchTree> getImages() {
        return images;
    }

    static class Node {
        int height;
        BinarySearchTree image;
        Node rightBranch, leftBranch;

        public Node(BinarySearchTree image) {
            this.image = image;
            height = 1;
        }
    }

}
