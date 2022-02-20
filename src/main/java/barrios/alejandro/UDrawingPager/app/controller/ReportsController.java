package barrios.alejandro.UDrawingPager.app.controller;

import barrios.alejandro.UDrawingPager.app.model.Client;
import barrios.alejandro.UDrawingPager.app.model.Image;
import barrios.alejandro.UDrawingPager.app.model.PType;
import barrios.alejandro.UDrawingPager.app.model.SavedInformation;
import barrios.alejandro.UDrawingPager.structures.controller.SinglyLinkedList;
import barrios.alejandro.UDrawingPager.structures.model.Node;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ReportsController {

    SavedInformation savedInformation = SavedInformation.getInstance();

    public void reportMenu() {
        String reportOptions = """
                1. Top 5 clientes com más imágenes a color
                2. Top 5 clientes con menos imágenes en blanco y negro
                3. Cliente con más pasos en el sistema
                4. Seleccionar cliente""";
        System.out.println(reportOptions);

        try {
            selectReportOption();
        } catch (InputMismatchException e) {
            System.out.println("Ingresa un valor numérico");
            reportMenu();
        }
    }

    private void selectReportOption() throws InputMismatchException {
        System.out.print("> ");
        Scanner sc = new Scanner(System.in);

        int optionSelected = sc.nextInt();
        if (optionSelected >= 1 && optionSelected <= 4) {
            optionReportSelected(optionSelected);
        } else {
            System.out.println("Opción inválida");
            selectReportOption();
        }
    }

    private void optionReportSelected(int option) {
        switch (option) {
            case 1 -> top5Color();
            case 2 -> top5Bnw();
            case 3 -> moreSteps();
            case 4 -> selectClient();
        }
    }

    private void top5Color() {
        SinglyLinkedList<Client> clients = savedInformation.getAttendedClients();
        if (clients.size() > 1) {
            for (Node<Client> clientNode = clients.getFirstNode(); clientNode != null; clientNode = clientNode.next) {
                Node<Client> previous = null;
                Node<Client> current = clients.getFirstNode();
                for (Node<Client> next = current.next; next != null; next = current.next) {
                    int noOfColorCurrent = 0;
                    int noOfColorNext = 0;
                    for (Node<Image> imageNode = current.data.getImages().getFirstNode(); imageNode != null; imageNode = imageNode.next) {
                        if (imageNode.data.getType() == PType.COLOR) noOfColorCurrent++;
                    }
                    for (Node<Image> imageNode = next.data.getImages().getFirstNode(); imageNode != null; imageNode = imageNode.next) {
                        if (imageNode.data.getType() == PType.COLOR) noOfColorNext++;
                    }
                    if (noOfColorCurrent < noOfColorNext) {
                        if (previous != null) {
                            previous.next = next;
                        } else {
                            clients.setFirstNode(next);
                        }
                        current.next = next.next;
                        next.next = current;
                        previous = next;
                    } else {
                        previous = current;
                        current = next;
                    }
                }
            }
        }

        int i = 1;
        for (Node<Client> clientNode = clients.getFirstNode(); clientNode != null; clientNode = clientNode.next) {
            Client client = clientNode.data;
            System.out.println(i + ". " + client.getName());
            if (i > 5) break;
            i++;
        }

    }

    private void top5Bnw() {

    }

    private void moreSteps() {

    }

    private void selectClient() {

    }

}
