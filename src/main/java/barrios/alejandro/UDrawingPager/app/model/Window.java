package barrios.alejandro.UDrawingPager.app.model;

import barrios.alejandro.UDrawingPager.structures.controller.LinkedQueue;
import barrios.alejandro.UDrawingPager.structures.controller.LinkedStack;

public class Window {

    private final int id;
    private final LinkedStack<Image> images;
    private final LinkedQueue<Client> clients;

    public Window(int id) {
        this.id = id;
        images = new LinkedStack<>();
        clients = new LinkedQueue<>();
    }

    public int getId() {
        return id;
    }

    public LinkedStack<Image> getImagesStack() {
        return images;
    }

    public LinkedQueue<Client> getClientsQueue() {
        return clients;
    }
}
