package barrios.alejandro.udrawingpage.structures.controller;

import barrios.alejandro.udrawingpage.users.model.Album;

public class DoublyCircularLinkedList {

    private Node head;
    private int size = 0;

    public void createAlbum(String name) {
        if (head == null) {
            head = new Node(size + 1, name);
            head.imHead = true;
            head.next = head;
            head.prev = head;
            size++;
            return;
        }
        Node current = head;
        while (!current.next.imHead) current = current.next;
        Node newNode = new Node(size + 1, name);
        newNode.imHead = false;
        newNode.next = head;
        current.next = newNode;
        newNode.prev = current;
        head.prev = newNode;
        size++;
    }

    public SinglyLinkedList<BinarySearchTree> getPos(int position) {
        Node current = head;
        for (int i = 0; i < position; i++) current = current.next;
        return current.album.images;
    }

    public SinglyLinkedList<BinarySearchTree> getLastAlbum() {
        Node current = head;
        while (!current.next.imHead) current = current.next;
        return current.album.images;
    }

    public int size() {
        return size;
    }

    static class Node {
        boolean imHead;
        Node next;
        Node prev;
        Album album;
        public Node(int id, String name) {
            album = new Album(id, name);
            album.images = new SinglyLinkedList<>();
        }

    }

}
