package barrios.alejandro.udrawingpage.structures.controller;

public class Queue<T> {

    private Node<T> head;
    private int size = 0;

    public void queue(T data) {
        if (head == null) {
            this.head = new Node<>(data);
            size++;
            return;
        }
        Node<T> current = this.head;
        while (current.next != null) current = current.next;
        current.next = new Node<>(data);
        size++;
    }

    public void pop() {
        if (size > 1) {
            this.head = this.head.next;
            size--;
            return;
        }
        this.head = null;
        size--;
    }

    public T getFirst() {
        return head != null ? head.data : null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size > 0 ? false : true;
    }

    static class Node<T> {

        public Node<T> next;
        public T data;
    
        public Node(T data) {
            this.data = data;
        }
    
    }

}
