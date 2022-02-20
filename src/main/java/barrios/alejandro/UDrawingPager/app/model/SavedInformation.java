package barrios.alejandro.UDrawingPager.app.model;

import barrios.alejandro.UDrawingPager.structures.controller.LinkedQueue;

public class SavedInformation {

    private static SavedInformation savedInformation = null;
    private int hatchQt = 0;
    private LinkedQueue<Client> receptionQueue = new LinkedQueue<>();
    private Printer colorPrinter = new Printer(1, PType.COLOR);
    private Printer bnwPrinter = new Printer(2, PType.BLACK_N_WHITE);

    public static SavedInformation getInstance() {
        if (savedInformation == null)
            savedInformation = new SavedInformation();
        return savedInformation;
    }

    public int getHatchQt() {
        return hatchQt;
    }

    public LinkedQueue<Client> getReceptionQueue() {
        return receptionQueue;
    }

    public void setWindowsQt(int hatchQt) {
        this.hatchQt = hatchQt;
    }
}
