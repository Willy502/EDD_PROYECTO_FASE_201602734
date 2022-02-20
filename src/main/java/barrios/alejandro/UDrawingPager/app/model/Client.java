package barrios.alejandro.UDrawingPager.app.model;

import barrios.alejandro.UDrawingPager.structures.controller.SinglyLinkedList;

public class Client {

    private final int id;
    private final String name;
    private final SinglyLinkedList<Image> images;
    private int steps;

    public Client(int id, String name) {
        this.id = id;
        this.name = name;
        images = new SinglyLinkedList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SinglyLinkedList<Image> getImages() {
        return images;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
}
