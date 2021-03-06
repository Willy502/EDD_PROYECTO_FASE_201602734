package barrios.alejandro.UDrawingPager.app.controller;

import barrios.alejandro.UDrawingPager.app.model.SavedInformation;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuController {

    RunController runController;
    InitialStepsController initialStepsController;
    SavedInformation savedInformation = SavedInformation.getInstance();

    public MenuController() {
        runController = new RunController();
        initialStepsController = new InitialStepsController();
    }

    public void showMenu() {
        String menuOptions = """
                Menú principal
                1. Parámetros Iniciales
                2. Ejecutar paso
                3. Estado en memoria de las estructuras
                4. Reportes
                5. Acerca de
                6. Salir""";
        System.out.println(menuOptions);

        try {
            selectMenuOption();
        } catch (InputMismatchException e) {
            System.out.println("Ingresa un valor numérico");
            clearScreen();
            showMenu();
        }
    }

    private void selectMenuOption() throws InputMismatchException {
        System.out.print("> ");
        Scanner sc = new Scanner(System.in);

        int optionSelected = sc.nextInt();
        if (optionSelected >= 1 && optionSelected <= 6) {
            optionSelected(optionSelected);
        } else {
            System.out.println("Opción inválida");
            clearScreen();
            selectMenuOption();
        }
    }

    private void clearScreen() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception ignored) {}
    }

    private void optionSelected(int option) {
        if (option > 1 && savedInformation.getLinkedWindows() == null) {
            System.out.println("No haz indicado una cantidad de ventanillas");
            showMenu();
            return;
        }
        switch (option) {
            case 1:
                try {
                    initialSteps();
                } catch (InputMismatchException e) {
                    System.out.println("Ingresa un valor numérico");
                    optionSelected(1);
                }
                break;

            case 2:
                runController.runStep();
                break;

            case 3:
                new GraphController().graphMenu();
                break;

            case 4:
                new ReportsController().reportMenu();
                break;

            case 5:
                runController.about();
                break;

            case 6:
                System.exit(0);
                break;
        }
        showMenu();
    }

    private void initialSteps() throws InputMismatchException {

        String submenuOptions = """
                1. Carga masiva de clientes
                2. Cantidad de ventanillas""";
        System.out.println(submenuOptions);
        System.out.print("> ");

        Scanner sc = new Scanner(System.in);

        int optionSelected = sc.nextInt();

        switch (optionSelected) {
            case 1 -> initialStepsController.loadInput();
            case 2 -> initialStepsController.askHatch();
            default -> {
                System.out.println("Ingresa una opción válida");
                optionSelected(1);
            }
        }

        showMenu();
    }

}
