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

    public User(long dpi, String name, String password, Rol rol, String email, String username, Town town, String phone, String address) {
        super(dpi, name, password, phone, address);
        this.rol = rol;
        this.email = email;
        this.username = username;
        this.town = town;
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

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return name;
    }
}
