package barrios.alejandro.UDrawingPager.controller;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuController {

    private static MenuController menuControllerSingleton = null;

    public static MenuController getInstance() {
        if (menuControllerSingleton == null)
            menuControllerSingleton = new MenuController();
        return menuControllerSingleton;
    }

    public void showMenu() {
        String menuOptions = "1. Parámetros Iniciales \n" +
                "2. Ejecutar paso \n" +
                "3. Estado en memoria de las estructuras \n" +
                "4. Reportes \n" +
                "5. Acerca de \n" +
                "6. Salir";
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
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }

    private void optionSelected(int option) {
        switch (option) {
            case 1:
                System.out.println(option);
                showMenu();
                break;

            case 2:
                System.out.println(option);
                showMenu();
                break;

            case 3:
                System.out.println(option);
                showMenu();
                break;

            case 4:
                System.out.println(option);
                showMenu();
                break;

            case 5:
                System.out.println(option);
                showMenu();
                break;

            case 6:
                System.exit(0);
                break;
        }
    }

}
