package barrios.alejandro.UDrawingPager.app.model;

import barrios.alejandro.UDrawingPager.structures.controller.LinkedQueue;
import barrios.alejandro.UDrawingPager.structures.controller.SinglyLinkedList;

public class SavedInformation {

    private static SavedInformation savedInformation = null;
    private SinglyLinkedList<Window> linkedWindows;
    private LinkedQueue<Client> receptionQueue;
    private SinglyLinkedList<Client> attendedClients;
    private SinglyLinkedList<Client> waitingList;
    private Printer colorPrinter;
    private Printer bnwPrinter;
    private int stepsRunning;

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

    public void setAttendedClients(SinglyLinkedList<Client> attendedClients) {
        this.attendedClients = attendedClients;
    }

    public SinglyLinkedList<Client> getAttendedClients() {
        return attendedClients;
    }

    public SinglyLinkedList<Client> getWaitingList() {
        return waitingList;
    }

    public void setWaitingList(SinglyLinkedList<Client> waitingList) {
        this.waitingList = waitingList;
    }

    public int getStepsRunning() {
        return stepsRunning;
    }

    public void setStepsRunning(int stepsRunning) {
        this.stepsRunning = stepsRunning;
    }
}
