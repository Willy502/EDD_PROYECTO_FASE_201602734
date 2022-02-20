package barrios.alejandro.UDrawingPager.app.model;

import barrios.alejandro.UDrawingPager.structures.controller.LinkedQueue;
import barrios.alejandro.UDrawingPager.structures.controller.SinglyLinkedList;

public class SavedInformation {

    private static SavedInformation savedInformation = null;
    private SinglyLinkedList<Window> linkedWindows;
    private LinkedQueue<Client> receptionQueue;
    private Printer colorPrinter;
    private Printer bnwPrinter;

    public static SavedInformation getInstance() {
        if (savedInformation == null)
            savedInformation = new SavedInformation();
        return savedInformation;
    }

    public SinglyLinkedList<Window> getLinkedWindows() {
        return linkedWindows;
    }

    public void setLinkedWindows(SinglyLinkedList<Window> linkedWindows) {
        this.linkedWindows = linkedWindows;
    }

    public LinkedQueue<Client> getReceptionQueue() {
        return receptionQueue;
    }

    public void setReceptionQueue(LinkedQueue<Client> receptionQueue) {
        this.receptionQueue = receptionQueue;
    }

    public Printer getColorPrinter() {
        return colorPrinter;
    }

    public void setColorPrinter(Printer colorPrinter) {
        this.colorPrinter = colorPrinter;
    }

    public Printer getBnwPrinter() {
        return bnwPrinter;
    }

    public void setBnwPrinter(Printer bnwPrinter) {
        this.bnwPrinter = bnwPrinter;
    }
}
