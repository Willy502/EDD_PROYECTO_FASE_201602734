package barrios.alejandro.udrawingpage.users.model;

import barrios.alejandro.udrawingpage.place.model.Town;
import barrios.alejandro.udrawingpage.structures.controller.*;

public class User extends Person {

    private String email;
    private Town town;
    private Rol rol;
    private String username;
    private DoublyCircularLinkedList albumes;
    private BinarySearchTree capas;
    private AvlTree images;

    public User() {}

    public User(long dpi, String name, String password, Rol rol) {
        super(dpi, name, password);
        this.rol = rol;
    }

    public DoublyCircularLinkedList getAlbumes() {
        return albumes;
    }

    public void setAlbumes() {
        albumes = new DoublyCircularLinkedList();
    }

    public BinarySearchTree getCapas() {
        return capas;
    }

    public void setCapas() {
        capas = new BinarySearchTree();
    }

    public AvlTree getImages() {
        return images;
    }

    public void setImages() {
        images = new AvlTree();
    }

    public Rol getRol() {
        return rol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return name;
    }
}
