package barrios.alejandro.UDrawingPager.controller;

import barrios.alejandro.UDrawingPager.model.SavedInformation;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InitialStepsController {

    public void askWindows() {
        String options = "¿Cuantas ventanillas desea agregar?";
        System.out.println(options);
        System.out.print("> ");

        try {
            saveWindows();
        } catch (InputMismatchException e) {
            System.out.println("Ingresa un valor numérico");
            askWindows();
        }

    }

    private void saveWindows() throws InputMismatchException {
        Scanner sc = new Scanner(System.in);
        int qty = sc.nextInt();

        SavedInformation.getInstance().setWindowsQt(qty);
        System.out.println("Ventanillas almacenadas exitosamente");
    }

}
