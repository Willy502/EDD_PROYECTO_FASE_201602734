package barrios.alejandro.udrawingpage.structures.controller;

import barrios.alejandro.udrawingpage.graph.Graph;
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

    public void graphCircular() {
        String result = "digraph G {\n";
        result += "node[shape=box];\n";

        Node current = head;
        do {
            result += current.album.name.replaceAll("\\s","") + "[group=1];\n";
            current = current.next;
        } while(!current.imHead);

        current = head;
        do {
            result += current.album.name.replaceAll("\\s","") + " -> " + current.next.album.name.replaceAll("\\s","") + ";\n";
            result += current.album.name.replaceAll("\\s","") + " -> " + current.prev.album.name.replaceAll("\\s","") + ";\n";
            current = current.next;
        } while (!current.imHead);

        result += " { rank=same;\n";
        current = head;
        do {
            result += current.album.name.replaceAll("\\s","") + ";\n";
            current = current.next;
        } while (!current.imHead);
        result += "}\n";

        current = head;
        do {
            int altura = 2;
            SinglyLinkedList.Node<BinarySearchTree> image = current.album.images.getHead();
            while (image != null) {
                result += current.album.name.replaceAll("\\s","") + "_" + image.data.id + "[group= " + altura + "];\n";

                result += " { rank=same;\n";
                result += current.album.name.replaceAll("\\s","") + "_" + image.data.id + ";\n";
                result += "};\n";

                image = image.next;
                altura++;
            }

            image = current.album.images.getHead();
            if (image != null)
                result += current.album.name.replaceAll("\\s","") + " -> " + current.album.name.replaceAll("\\s","") + "_" + image.data.id + ";\n";
            while (image != null) {
                if (image.next != null)
                    result += current.album.name.replaceAll("\\s","") + "_" + image.data.id + " -> " + current.album.name.replaceAll("\\s","") + "_" + image.next.data.id + ";\n";
                image = image.next;
            }

            current = current.next;
        } while (!current.imHead);

        result += "}\n";
        Graph.GenerarImagen("CIRCULAR_ALBUMS", result);
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
