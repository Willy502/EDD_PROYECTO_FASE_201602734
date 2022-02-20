package barrios.alejandro.UDrawingPager.structures.controller;

import barrios.alejandro.UDrawingPager.structures.model.Linked;
import barrios.alejandro.UDrawingPager.structures.model.Node;

public class LinkedStack<T> extends Linked<T> {

    public void push(T data) {
        addToList(data);
    }

    public T getLast() {
        Node<T> current = this.head;
        while (current.next != null) current = current.next;
        return current.data;
    }

    public void pop() {
        Node<T> current = this.head;
        Node<T> previous = null;

        while (current.next != null) {
            previous = current;
            current = current.next;
        }

        if (previous != null) {
            previous.next = previous.next.next;
        } else {
            this.head = null;
        }
        size--;
    }

    public int size() {
        return size;
    }

}
