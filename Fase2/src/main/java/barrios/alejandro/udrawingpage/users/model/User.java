package barrios.alejandro.udrawingpage.users.model;

import barrios.alejandro.udrawingpage.structures.controller.*;

import java.math.BigInteger;

public class User {

    public long dpi;
    private String name;
    private String password;
    private Rol rol;
    private DoublyCircularLinkedList albumes;
    private BinarySearchTree capas;
    private AvlTree images;

    public User() {

    }

    public User(long dpi, String name, String password, Rol rol) {
        this.name = name;
        this.dpi = dpi;
        this.password = password;
        this.rol = rol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return name;
    }
}
