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

        printTop(clients, PType.COLOR);

    }

    private void top5Bnw() {
        SinglyLinkedList<Client> clients = savedInformation.getAttendedClients();
        if (clients.size() > 1) {
            for (Node<Client> clientNode = clients.getFirstNode(); clientNode != null; clientNode = clientNode.next) {
                Node<Client> previous = null;
                Node<Client> current = clients.getFirstNode();
                for (Node<Client> next = current.next; next != null; next = current.next) {
                    int noOfBnwCurrent = 0;
                    int noOfBnwNext = 0;
                    for (Node<Image> imageNode = current.data.getImages().getFirstNode(); imageNode != null; imageNode = imageNode.next) {
                        if (imageNode.data.getType() == PType.BLACK_N_WHITE) noOfBnwCurrent++;
                    }
                    for (Node<Image> imageNode = next.data.getImages().getFirstNode(); imageNode != null; imageNode = imageNode.next) {
                        if (imageNode.data.getType() == PType.BLACK_N_WHITE) noOfBnwNext++;
                    }
                    if (noOfBnwCurrent > noOfBnwNext) {
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

        printTop(clients, PType.BLACK_N_WHITE);

    }

    private void moreSteps() {
        SinglyLinkedList<Client> clients = savedInformation.getAttendedClients();
        if (clients.size() > 1) {
            for (Node<Client> clientNode = clients.getFirstNode(); clientNode != null; clientNode = clientNode.next) {
                Node<Client> previous = null;
                Node<Client> current = clients.getFirstNode();
                for (Node<Client> next = current.next; next != null; next = current.next) {
                    if (current.data.getSteps() > next.data.getSteps()) {
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

        System.out.println("Nombre: " + clients.getPosition(clients.size() - 1).getName());
        System.out.println("ID: " + clients.getPosition(clients.size() - 1).getId());
        System.out.println("Pasos: " + clients.getPosition(clients.size() - 1).getSteps());
    }

    private void selectClient() {
        SinglyLinkedList<Client> clients = savedInformation.getAttendedClients();
        int i = 1;
        for (Node<Client> clientNode = clients.getFirstNode(); clientNode != null; clientNode = clientNode.next) {
            Client client = clientNode.data;
            System.out.println(i + ". " + client.getName());
            i++;
        }
        System.out.println("Selecciona el cliente para visualizar su información");
        System.out.print("> ");
        Scanner sc = new Scanner(System.in);

        try {
            int optionSelected = sc.nextInt();
            if (optionSelected >= 1 && optionSelected <= clients.size()) {
                Client client = clients.getPosition(optionSelected - 1);
                System.out.println("Nombre: " + client.getName());
                System.out.println("ID: " + client.getId());
                System.out.println("Pasos: " + client.getSteps());
                int j = 1;
                for (Node<Image> imageNode = client.getImages().getFirstNode(); imageNode != null; imageNode = imageNode.next) {
                    Image image = imageNode.data;
                    System.out.println(j + ". " + (image.getType() == PType.COLOR ? "Imágen a color" : "Imágen blanco y negro"));
                    j++;
                }
            }
        } catch (InputMismatchException ignored) {
            System.out.println("Ingresa un valor numérico");
            selectClient();
        }
    }

    private void printTop(SinglyLinkedList<Client> clients, PType type) {
        int i = 1;
        for (Node<Client> clientNode = clients.getFirstNode(); clientNode != null; clientNode = clientNode.next) {
            Client client = clientNode.data;
            int totalPrinted = 0;
            for (Node<Image> imageNode = client.getImages().getFirstNode(); imageNode != null; imageNode = imageNode.next) {
                Image image = imageNode.data;
                if (image.getType() == type) {
                    totalPrinted++;
                }
            }
            System.out.println(i + ". " + client.getName() + " | Impresas: " + totalPrinted);
            if (i >= 5) break;
            i++;
        }
    }

}
