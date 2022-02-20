package barrios.alejandro.UDrawingPager.app.controller;

import barrios.alejandro.UDrawingPager.app.model.Image;
import barrios.alejandro.UDrawingPager.app.model.PType;
import barrios.alejandro.UDrawingPager.app.model.SavedInformation;
import barrios.alejandro.UDrawingPager.app.model.Window;
import barrios.alejandro.UDrawingPager.structures.model.Node;

public class OptionsController {

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

    public void runStep() {

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
    }

}
