package barrios.alejandro.UDrawingPager.app.model;

import barrios.alejandro.UDrawingPager.structures.controller.SinglyLinkedList;

public class Client {

    private int id;
    private String name;
    private SinglyLinkedList<Image> images;

    public Client(int id, String name) {
        this.id = id;
        this.name = name;
        images = new SinglyLinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SinglyLinkedList<Image> getImages() {
        return images;
    }
}
