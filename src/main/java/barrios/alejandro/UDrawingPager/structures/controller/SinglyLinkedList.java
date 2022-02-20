package barrios.alejandro.UDrawingPager.structures.controller;

import barrios.alejandro.UDrawingPager.structures.model.Linked;
import barrios.alejandro.UDrawingPager.structures.model.Node;

public class SinglyLinkedList<T> extends Linked<T> {

    public void append(T data) {
        addToList(data);
    }

    public void remove(int position) {
        Node<T> current = this.head;
        Node<T> previous = null;

        for (int i = 0; i < position; i++) {
            previous = current;
            current = current.next;
        }

        if (previous != null) {
            previous.next = previous.next.next;
        } else {
            this.head = this.head.next;
        }
        size--;

    }

    public Node<T> getFirstNode() {
        return getHead();
    }

    public void setFirstNode(Node<T> head) {
        this.head = head;
    }

    public T getPosition(int position) {
        Node<T> current = this.head;
        for (int i = 0; i < position; i++) current = current.next;
        return current.data;
    }

    public int size() {
        return size;
    }
}