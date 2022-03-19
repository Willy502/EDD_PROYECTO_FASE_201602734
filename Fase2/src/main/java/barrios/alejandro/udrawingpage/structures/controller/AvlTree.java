package barrios.alejandro.udrawingpage.structures.controller;

public class AvlTree {

    private Node root;

    public void insert(BinarySearchTree image) {
        if (root == null) {
            root = new Node(image);
            return;
        }

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
    }

    private void balanceNode(Node current) {

    }

    static class Node {
        int height;
        BinarySearchTree image;
        Node rightBranch, leftBranch;

        public Node(BinarySearchTree image) {
            this.image = image;
        }
    }

}
