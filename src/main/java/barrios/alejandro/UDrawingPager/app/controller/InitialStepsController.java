package barrios.alejandro.UDrawingPager.app.controller;

import barrios.alejandro.UDrawingPager.app.model.SavedInformation;
import barrios.alejandro.UDrawingPager.app.model.Window;
import barrios.alejandro.UDrawingPager.structures.controller.SinglyLinkedList;

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
        SavedInformation savedInformation = SavedInformation.getInstance();
        Scanner sc = new Scanner(System.in);
        int qty = sc.nextInt();

        savedInformation.setLinkedWindows(new SinglyLinkedList<>());
        for (int i = 1; i <= qty; i++) {
            savedInformation.getLinkedWindows().append(new Window(i));
        }
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
