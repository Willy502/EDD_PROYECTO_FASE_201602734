package barrios.alejandro.UDrawingPager.structures.controller;

import barrios.alejandro.UDrawingPager.structures.model.Linked;

public class LinkedQueue<T> extends Linked<T> {

    public void add(T data) {
        addToList(data);
    }

    public void remove() {
        if (size > 1) {
            this.head = this.head.next;
            size--;
            return;
        }
        this.head = null;
        size--;
    }

    public T getFirst() {
        return head.data;
    }

    public int size() {
        return size;
    }

}
