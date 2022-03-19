package barrios.alejandro.udrawingpage.structures.controller;

public class DoublyCircularLinkedList {

    private Node head;
    private int size = 0;

    public void createAlbum() {
        if (head == null) {
            head = new Node(size + 1);
            head.imHead = true;
            head.next = head;
            head.prev = head;
            size++;
            return;
        }
        Node current = head;
        while (!current.next.imHead) current = current.next;
        Node newNode = new Node(size + 1);
        newNode.imHead = false;
        newNode.next = head;
        current.next = newNode;
        newNode.prev = current;
        head.prev = newNode;
        size++;
    }

    public SinglyLinkedList getPos(int position) {
        Node current = head;
        for (int i = 0; i < position; i++) current = current.next;
        return current.images;
    }

    public int size() {
        return size;
    }

    static class Node {
        boolean imHead;
        Node next;
        Node prev;
        SinglyLinkedList images;
        int id;
        public Node(int id) {
            this.id = id;
            images = new SinglyLinkedList();
        }

    }

}
