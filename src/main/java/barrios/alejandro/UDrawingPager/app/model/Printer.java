package barrios.alejandro.UDrawingPager.app.model;

import barrios.alejandro.UDrawingPager.structures.controller.LinkedQueue;

public class Printer {

    private int id;
    private PType type;
    private LinkedQueue<Image> imagesQueue;

    public Printer(int id, PType type) {
        this.id = id;
        this.type = type;
        imagesQueue = new LinkedQueue<>();
    }

    public int getId() {
        return id;
    }

    public PType getType() {
        return type;
    }

    public LinkedQueue<Image> getImagesQueue() {
        return imagesQueue;
    }
}
