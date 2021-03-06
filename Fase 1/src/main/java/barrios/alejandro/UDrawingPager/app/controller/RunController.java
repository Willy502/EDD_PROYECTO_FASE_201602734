package barrios.alejandro.UDrawingPager.app.controller;

import barrios.alejandro.UDrawingPager.app.model.*;
import barrios.alejandro.UDrawingPager.structures.controller.SinglyLinkedList;
import barrios.alejandro.UDrawingPager.structures.model.Node;

import java.util.Random;

public class RunController {

    SavedInformation savedInformation = SavedInformation.getInstance();

    public void about() {
        String information = """
                Wilfred Alejandro Barrios Ola
                201602734
                Estructuras de Datos
                Sección A
                """;
        System.out.println(information);
    }

    private void randomUsers() {
        SinglyLinkedList<String> names = new SinglyLinkedList<>();
        names.append("Mirai");
        names.append("Carlos");
        names.append("Risa");
        names.append("Nao");
        names.append("Akihito");

        SinglyLinkedList<String> lastNames = new SinglyLinkedList<>();
        lastNames.append("Kuriyama");
        lastNames.append("López");
        lastNames.append("Koizumi");
        lastNames.append("Tomori");
        lastNames.append("Kanbara");

        SinglyLinkedList<String> fullName = new SinglyLinkedList<>();
        int randomBoundary = new Random().nextInt(3);
        for (int i = 0; i < randomBoundary; i++) {

            fullName.append(
                    names.getPosition(new Random().nextInt(5))
                            + " " + lastNames.getPosition(new Random().nextInt(5))
            );
        }

        for (int i = 0; i < fullName.size(); i++) {
            Client client = new Client(savedInformation.getReceptionQueue().size(), fullName.getPosition(i));
            randomBoundary = new Random().nextInt(4);
            for (int j = 0; j < randomBoundary; j++) {
                if ((new Random().nextInt(5) + 1) % 2 == 0) {
                    client.getImages().append(new Image(PType.COLOR, client));
                } else {
                    client.getImages().append(new Image(PType.BLACK_N_WHITE, client));
                }
            }

            savedInformation.getReceptionQueue().add(client);
        }
    }

    public void runStep() {

        randomUsers(); // Append 3 random users
        savedInformation.setStepsRunning(savedInformation.getStepsRunning() + 1);
        System.out.println("---------- Paso: " + savedInformation.getStepsRunning() + " ----------");

        // Process printers
        // Color printer
        Image imageP = savedInformation.getColorPrinter().getImagesQueue().getFirst();
        if (imageP != null) {
            if (savedInformation.getColorPrinter().getMissingTime() == 0) {
                savedInformation.getColorPrinter().setMissingTime(savedInformation.getColorPrinter().getStepsTiming());
                for (Node<Client> clientNode = savedInformation.getWaitingList().getFirstNode(); clientNode != null; clientNode = clientNode.next) {
                    Client client = clientNode.data;
                    if (imageP.getClient() == client) {
                        client.getImages().append(imageP);
                        savedInformation.getColorPrinter().getImagesQueue().remove();
                        System.out.println("-------------------");
                        System.out.println("Se completa la impresión de una imagen a color y se le entrega al cliente que la solicitó");
                        break;
                    }
                }
            } else {
                System.out.println("-------------------");
                System.out.println("Imágen a color en proceso de impresión");
                savedInformation.getColorPrinter().setMissingTime(savedInformation.getColorPrinter().getMissingTime() - 1);
            }
        }

        // Black and white printer
        imageP = savedInformation.getBnwPrinter().getImagesQueue().getFirst();
        if (imageP != null) {
            if (savedInformation.getBnwPrinter().getMissingTime() == 0) {
                savedInformation.getBnwPrinter().setMissingTime(savedInformation.getBnwPrinter().getStepsTiming());
                for (Node<Client> clientNode = savedInformation.getWaitingList().getFirstNode(); clientNode != null; clientNode = clientNode.next) {
                    Client client = clientNode.data;
                    if (imageP.getClient() == client) {
                        client.getImages().append(imageP);
                        savedInformation.getBnwPrinter().getImagesQueue().remove();
                        System.out.println("-------------------");
                        System.out.println("Se completa la impresión de una imagen a blanco y negro y se le entrega al cliente que la solicitó");
                        break;
                    }
                }
            } else {
                System.out.println("-------------------");
                System.out.println("Imágen blanco y negro en proceso de impresión");
                savedInformation.getBnwPrinter().setMissingTime(savedInformation.getBnwPrinter().getMissingTime() - 1);
            }
        }

        // Moving from waiting list to attended clients list
        for (int i = 0; i < savedInformation.getWaitingList().size(); i++) {
            Client client = savedInformation.getWaitingList().getPosition(i);
            boolean colorFound = false;
            for (Node<Image> imageNode = savedInformation.getColorPrinter().getImagesQueue().getFirstNode(); imageNode != null; imageNode = imageNode.next) {
                if (imageNode.data.getClient() == client) {
                    colorFound = true;
                    break;
                }
            }

            boolean bnwFound = false;
            for (Node<Image> imageNode = savedInformation.getBnwPrinter().getImagesQueue().getFirstNode(); imageNode != null; imageNode = imageNode.next) {
                if (imageNode.data.getClient() == client) {
                    bnwFound = true;
                    break;
                }
            }

            if (!colorFound && !bnwFound) {
                savedInformation.getAttendedClients().append(client);
                savedInformation.getWaitingList().remove(i);
                System.out.println("-------------------");
                client.setSteps(savedInformation.getStepsRunning() - client.getSteps());
                System.out.println("El cliente " + client.getName() + " ya posee todas sus imágenes impresas y sale de la empresa" +
                        " registrando el tiempo total de " + client.getSteps() + " pasos");
            }
        }

        // Add to window queue and remove from reception queue
        for (Node<Window> windowNode = savedInformation.getLinkedWindows().getFirstNode(); windowNode != null; windowNode = windowNode.next) {
            Window window = windowNode.data;

            // Add client to the window
            if (window.getClientsQueue().size() == 0) {
                if (savedInformation.getReceptionQueue().size() > 0) {
                    window.getClientsQueue().add(savedInformation.getReceptionQueue().getFirst());
                    System.out.println("--------------------");
                    System.out.println("El cliente " + savedInformation.getReceptionQueue().getFirst().getName() +
                            " Ingresa a la ventanilla " + window.getId());
                    savedInformation.getReceptionQueue().getFirst().setSteps(savedInformation.getStepsRunning());
                    savedInformation.getReceptionQueue().remove();
                }
            } else {

                // Move images from client to window images stack
                if (window.getClientsQueue().getFirst().getImages().size() > 0) {
                    window.getImagesStack().push(
                            window.getClientsQueue().getFirst().getImages().getPosition(0)
                    );
                    System.out.println("--------------------");
                    System.out.println("La ventanilla " + window.getId() + " recibe una imagen del cliente "
                            + window.getClientsQueue().getFirst().getName());
                    window.getClientsQueue().getFirst().getImages().remove(0);
                } else {

                    // Move client from window to waiting list
                    savedInformation.getWaitingList().append(
                            window.getClientsQueue().getFirst()
                    );
                    System.out.println("--------------------");
                    System.out.println("El cliente " + window.getClientsQueue().getFirst().getName()
                            + " es atendido e ingresa a la lista de espera");
                    window.getClientsQueue().remove();

                    // Move Images from window stack to printer
                    Image image = window.getImagesStack().getLast();
                    while (image != null) {
                        if (image.getType() == PType.COLOR) {
                            savedInformation.getColorPrinter().getImagesQueue().add(image);
                        } else {
                            savedInformation.getBnwPrinter().getImagesQueue().add(image);
                        }
                        window.getImagesStack().pop();
                        image = window.getImagesStack().getLast();
                    }
                    System.out.println("La ventanilla " + window.getId()
                            + " envía las imágenes del cliente "
                            + savedInformation.getWaitingList()
                            .getPosition(savedInformation.getWaitingList().size() - 1).getName()
                            + " a sus respectivas colas de impresión");

                }
            }
        }
        System.out.println("--------------------");
    }

}
