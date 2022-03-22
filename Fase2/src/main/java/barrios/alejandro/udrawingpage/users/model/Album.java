package barrios.alejandro.udrawingpage.users.model;

import barrios.alejandro.udrawingpage.structures.controller.BinarySearchTree;
import barrios.alejandro.udrawingpage.structures.controller.SinglyLinkedList;

public class Album {
    public int id;
    public String name;
    public SinglyLinkedList<BinarySearchTree> images;

    public Album(int id, String name) {
        this.id = id;
        this.name = name;
        images = new SinglyLinkedList<>();
    }
}
