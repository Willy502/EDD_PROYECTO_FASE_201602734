package barrios.alejandro.UDrawingPager.app.model;

import barrios.alejandro.UDrawingPager.structures.controller.LinkedQueue;

public class Printer {

    private final int id;
    private final int stepsTiming;
    private int missingTime;
    private final LinkedQueue<Image> imagesQueue;

    public Printer(int id, PType type) {
        this.id = id;
        if (type == PType.COLOR) stepsTiming = 2;
        else stepsTiming = 1;
        missingTime = stepsTiming;
        imagesQueue = new LinkedQueue<>();
    }

    public int getId() {
        return id;
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
