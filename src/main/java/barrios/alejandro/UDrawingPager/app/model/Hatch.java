package barrios.alejandro.UDrawingPager.app.model;

import barrios.alejandro.UDrawingPager.structures.controller.LinkedQueue;
import barrios.alejandro.UDrawingPager.structures.controller.LinkedStack;

public class Hatch {

    private int id;
    private LinkedStack<Image> images;
    private LinkedQueue<Client> clients;

    public Hatch(int id) {
        this.id = id;
        images = new LinkedStack<>();
        clients = new LinkedQueue<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LinkedStack<Image> getImagesStack() {
        return images;
    }

    public LinkedQueue<Client> getClientsQueue() {
        return clients;
    }
}
