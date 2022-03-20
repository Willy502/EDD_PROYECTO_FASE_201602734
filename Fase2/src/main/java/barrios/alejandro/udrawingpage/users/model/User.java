package barrios.alejandro.udrawingpage.users.model;

import barrios.alejandro.udrawingpage.structures.controller.*;

public class User {

    public long dpi;
    private String name;
    private String password;
    private DoublyCircularLinkedList albumes;
    private SinglyLinkedList<SparceMatrix> capas;
    private AvlTree images;

    public User(long dpi, String name, String password) {
        this.name = name;
        this.dpi = dpi;
        this.password = password;
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

    public SinglyLinkedList<SparceMatrix> getCapas() {
        return capas;
    }

    public void setCapas() {
        capas = new SinglyLinkedList<>();
    }

    public AvlTree getImages() {
        return images;
    }

    public void setImages() {
        images = new AvlTree();
    }
}
