package barrios.alejandro.UDrawingPager.app.model;

import barrios.alejandro.UDrawingPager.structures.controller.LinkedQueue;

public class Printer {

    private int id;
    private PType type;
    private int stepsTiming;
    private int missingTime;
    private LinkedQueue<Image> imagesQueue;

    public Printer(int id, PType type) {
        this.id = id;
        this.type = type;
        if (this.type == PType.COLOR) stepsTiming = 2;
        else stepsTiming = 1;
        missingTime = stepsTiming;
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

    public int getStepsTiming() {
        return stepsTiming;
    }

    public int getMissingTime() {
        return missingTime;
    }

    public void setMissingTime(int missingTime) {
        this.missingTime = missingTime;
    }
}
