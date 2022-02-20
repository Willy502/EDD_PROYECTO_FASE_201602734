package barrios.alejandro.UDrawingPager;

import barrios.alejandro.UDrawingPager.app.controller.MenuController;
import barrios.alejandro.UDrawingPager.app.model.PType;
import barrios.alejandro.UDrawingPager.app.model.Printer;
import barrios.alejandro.UDrawingPager.app.model.SavedInformation;
import barrios.alejandro.UDrawingPager.structures.controller.LinkedQueue;

public class InitApp {

    SavedInformation savedInformation;

    public InitApp() {
        savedInformation = SavedInformation.getInstance();
        savedInformation.setBnwPrinter(new Printer(1, PType.BLACK_N_WHITE));
        savedInformation.setColorPrinter(new Printer(2, PType.COLOR));
        savedInformation.setReceptionQueue(new LinkedQueue<>());

        new MenuController().showMenu();
    }
}
