package barrios.alejandro.UDrawingPager.structures.model;

public abstract class Linked<T> {

    protected Node<T> head;
    protected int size = 0;

    protected void addToList(T data) {
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

    protected Node<T> getHead() {
        return head;
    }

    public T getPos(int position) {
        Node<T> current = this.head;
        for (int i = 0; i < position; i++) current = current.next;
        return current.data;
    }

}
