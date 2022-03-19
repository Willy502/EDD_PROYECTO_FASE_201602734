package barrios.alejandro.udrawingpage.structures.controller;

public class BinarySearchTree {
    // Delte missing

    public int id;
    private Node root;

    public BinarySearchTree(int id) {
        this.id = id;
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


    static class Node {
        SparceMatrix capa;
        Node rightBranch, leftBranch;

        public Node(SparceMatrix capa) {
            this.capa = capa;
        }
    }

}
