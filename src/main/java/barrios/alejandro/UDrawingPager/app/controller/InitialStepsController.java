package barrios.alejandro.UDrawingPager.app.controller;

import barrios.alejandro.UDrawingPager.app.model.SavedInformation;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InitialStepsController {

    public void askHatch() {
        String options = "¿Cuantas ventanillas desea agregar?";
        System.out.println(options);
        System.out.print("> ");

        try {
            saveHatch();
        } catch (InputMismatchException e) {
            System.out.println("Ingresa un valor numérico");
            askHatch();
        }

    }

    private void saveHatch() throws InputMismatchException {
        Scanner sc = new Scanner(System.in);
        int qty = sc.nextInt();

        SavedInformation.getInstance().setWindowsQt(qty);
        System.out.println("Ventanillas almacenadas exitosamente");
    }

}
