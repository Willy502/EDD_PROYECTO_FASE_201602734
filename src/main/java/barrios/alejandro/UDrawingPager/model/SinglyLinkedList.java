package barrios.alejandro.UDrawingPager.model;

public class SinglyLinkedList<T> {

    private Node<T> head;
    private int size = 0;

    public void append(T data) {
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

    public T getPosition(int position) {
        Node<T> current = this.head;
        for (int i = 0; i < position; i++) current = current.next;
        return current.data;
    }

    public int size() {
        return size;
    }

    static class Node<T> {

        protected Node<T> next;
        protected T data;

        public Node(T data) {
            this.data = data;
        }
    }
}