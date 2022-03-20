package barrios.alejandro.udrawingpage.structures.controller;

public class SinglyLinkedList<T> {

    private Node<T> head;
    private int size = 0;

    public void addToList(T data) {
        if (head == null) {
            this.head = new Node<T>(data);
            size++;
            return;
        }
        Node<T> current = this.head;
        while (current.next != null) current = current.next;
        current.next = new Node<T>(data);
        size++;
    }

    public Node<T> getHead() {
        return head;
    }

    public T getPos(int position) {
        Node<T> current = this.head;
        for (int i = 0; i < position; i++) current = current.next;
        return current.data;
    }

    public void setFirstNode(Node<T> head) {
        this.head = head;
    }

    public int size() {
        return size;
    }

    static class Node<T> {
        Node<T> next;
        T data; // BinarySearchTree for images, capa for capas

        public Node(T data) {
            this.data = data;
        }
    }

}
