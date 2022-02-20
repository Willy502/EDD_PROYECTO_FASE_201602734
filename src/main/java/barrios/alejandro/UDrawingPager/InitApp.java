package barrios.alejandro.UDrawingPager;

import barrios.alejandro.UDrawingPager.app.controller.MenuController;
import barrios.alejandro.UDrawingPager.app.model.PType;
import barrios.alejandro.UDrawingPager.app.model.Printer;
import barrios.alejandro.UDrawingPager.app.model.SavedInformation;
import barrios.alejandro.UDrawingPager.structures.controller.LinkedQueue;
import barrios.alejandro.UDrawingPager.structures.controller.SinglyLinkedList;

public class InitApp {

    SavedInformation savedInformation;

    public InitApp() {
        savedInformation = SavedInformation.getInstance();
        savedInformation.setBnwPrinter(new Printer(PType.BLACK_N_WHITE));
        savedInformation.setColorPrinter(new Printer(PType.COLOR));
        savedInformation.setReceptionQueue(new LinkedQueue<>());
        savedInformation.setAttendedClients(new SinglyLinkedList<>());
        savedInformation.setWaitingList(new SinglyLinkedList<>());
        savedInformation.setStepsRunning(0);

        new MenuController().showMenu();
    }
}
