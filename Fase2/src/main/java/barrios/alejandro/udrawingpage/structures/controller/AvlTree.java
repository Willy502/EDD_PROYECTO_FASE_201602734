package barrios.alejandro.udrawingpage.structures.controller;

public class AvlTree { // Images tree
    // Delete missing

    private Node root;

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
