package barrios.alejandro.UDrawingPager.structures.controller;

import barrios.alejandro.UDrawingPager.structures.model.Linked;
import barrios.alejandro.UDrawingPager.structures.model.Node;

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
        return head != null ?head.data : null;
    }

    public Node<T> getFirstNode() {
        return getHead();
    }

    public T getPosition(int position) {
        return getPos(position);
    }

    public int size() {
        return size;
    }

}
