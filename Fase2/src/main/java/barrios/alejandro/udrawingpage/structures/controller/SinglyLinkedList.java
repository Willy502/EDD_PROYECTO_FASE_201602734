package barrios.alejandro.udrawingpage.structures.controller;

public class SinglyLinkedList {

    private Node head;
    private int size = 0;

    public void addToList(BinarySearchTree image) {
        if (head == null) {
            this.head = new Node(image);
            size++;
            return;
        }
        Node current = this.head;
        while (current.next != null) current = current.next;
        current.next = new Node(image);
        size++;
    }

    public Node getHead() {
        return head;
    }

    public BinarySearchTree getPos(int position) {
        Node current = this.head;
        for (int i = 0; i < position; i++) current = current.next;
        return current.image;
    }

    public void setFirstNode(Node head) {
        this.head = head;
    }

    public int size() {
        return size;
    }

    static class Node {
        Node next;
        BinarySearchTree image;

        public Node(BinarySearchTree image) {
            this.image = image;
        }
    }

}
