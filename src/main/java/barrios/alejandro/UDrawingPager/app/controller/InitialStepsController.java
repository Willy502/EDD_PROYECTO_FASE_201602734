package barrios.alejandro.UDrawingPager.app.controller;

import barrios.alejandro.UDrawingPager.app.model.SavedInformation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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

    public void loadInput() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON files", "json");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("FILE: " + chooser.getSelectedFile().getAbsolutePath());
        }
    }

}
