package barrios.alejandro.UDrawingPager.app.controller;

import barrios.alejandro.UDrawingPager.app.model.SavedInformation;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;

import java.awt.*;
import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

import static guru.nidi.graphviz.attribute.Rank.RankDir.LEFT_TO_RIGHT;
import static guru.nidi.graphviz.model.Factory.*;

public class GraphController {

    SavedInformation savedInformation = SavedInformation.getInstance();

    public void graphMenu() {
        String graphOptions = """
                1. Cola de recepción
                2. Lista de ventanillas
                3. Pila de Imágenes
                4. Lista de clientes atendidos
                5. Cola de impresión a color
                6. Cola de impresión blanco y negro
                7. Lista de clientes en espera""";
        System.out.println(graphOptions);

        try {
            selectGraphOption();
        } catch (InputMismatchException e) {
            System.out.println("Ingresa un valor numérico");
            graphMenu();
        }
    }

    private void selectGraphOption() throws InputMismatchException {
        System.out.print("> ");
        Scanner sc = new Scanner(System.in);

        int optionSelected = sc.nextInt();
        if (optionSelected >= 1 && optionSelected <= 7) {
            optionGraphSelected(optionSelected);
        } else {
            System.out.println("Opción inválida");
            selectGraphOption();
        }
    }

    private void optionGraphSelected(int option) {
        switch (option) {
            case 1 -> receptionQueue();
            case 2 -> windowsList();
            case 3 -> imagesStack();
            case 4 -> attendedClientsList();
            case 5 -> colorPrinterQueue();
            case 6 -> bnwPrinterQueue();
            case 7 -> waitingClientsList();
        }
    }

    private void receptionQueue() {
        Graph graph = mutGraph("Reception Queue").setDirected(true).use((gr, ctx) -> {
            for (int i = 0; i < savedInformation.getReceptionQueue().size(); i++) {
                mutNode(Integer.toString(i)).add(Label.of(savedInformation.getReceptionQueue().getPosition(i).getName()));
            }

            for (int i = 0; i < savedInformation.getReceptionQueue().size() - 1; i++) {
                mutNode(Integer.toString(i)).addLink(mutNode(Integer.toString(i+1)));
            }
        }).toImmutable().graphAttr().with(Rank.dir(LEFT_TO_RIGHT));

        produceGraph(graph, "Reception Queue");
    }

    private void windowsList() {
        Graph graph = mutGraph("Windows List").setDirected(true).use((gr, ctx) -> {
            for (int i = 0; i < savedInformation.getLinkedWindows().size(); i++) {
                mutNode(Integer.toString(i)).add(Label.of(String.valueOf(savedInformation.getLinkedWindows().getPosition(i).getId())));
            }

            for (int i = 0; i < savedInformation.getLinkedWindows().size() - 1; i++) {
                mutNode(Integer.toString(i)).addLink(mutNode(Integer.toString(i+1)));
            }
        }).toImmutable().graphAttr().with(Rank.dir(LEFT_TO_RIGHT));

        produceGraph(graph, "Windows List");
    }

    private void imagesStack() {
        Graph graph = mutGraph("Images stack per window").setDirected(true).use((gr, ctx) -> {
            StringBuilder unique_id = new StringBuilder();
            for (int i = 0; i < savedInformation.getLinkedWindows().size(); i++) {
                mutNode(Integer.toString(i)).add(Label.of(String.valueOf(savedInformation.getLinkedWindows().getPosition(i).getId())));

                unique_id.append("0");
                for (int j = 0; j < savedInformation.getLinkedWindows().getPosition(i).getImagesStack().size(); j++) {
                    mutNode(unique_id.toString() + j).add(Label.of(savedInformation.getLinkedWindows().getPosition(i).getImagesStack().getPosition(j).getType().toString()));
                }
            }


            for (int i = 0; i < savedInformation.getLinkedWindows().size() - 1; i++) {
                mutNode(Integer.toString(i)).addLink(mutNode(Integer.toString(i+1)));
            }

            unique_id = new StringBuilder();
            for (int i = 0; i < savedInformation.getLinkedWindows().size(); i++) {
                unique_id.append("0");
                for (int j = 0; j < savedInformation.getLinkedWindows().getPosition(i).getImagesStack().size(); j++) {
                    mutNode(j == 0 ? String.valueOf(i) : unique_id.toString() + (j-1)).addLink(mutNode(unique_id.toString() + j));
                }
            }


        }).toImmutable().graphAttr().with(Rank.dir(LEFT_TO_RIGHT));

        produceGraph(graph, "Images stack per window");
    }

    private void attendedClientsList() {
        Graph graph = mutGraph("Attended Clients").setDirected(true).use((gr, ctx) -> {
            for (int i = 0; i < savedInformation.getAttendedClients().size(); i++) {
                mutNode(Integer.toString(i)).add(Label.of(savedInformation.getAttendedClients().getPosition(i).getName()));
            }

            for (int i = 0; i < savedInformation.getAttendedClients().size() - 1; i++) {
                mutNode(Integer.toString(i)).addLink(mutNode(Integer.toString(i+1)));
            }
        }).toImmutable().graphAttr().with(Rank.dir(LEFT_TO_RIGHT));

        produceGraph(graph, "Attended Clients");
    }

    private void colorPrinterQueue() {
        Graph graph = mutGraph("Color Printer Queue").setDirected(true).use((gr, ctx) -> {
            for (int i = 0; i < savedInformation.getColorPrinter().getImagesQueue().size(); i++) {
                mutNode(Integer.toString(i)).add(Label.of( savedInformation.getColorPrinter().getImagesQueue().getPosition(i).getType().toString()));
            }

            for (int i = 0; i < savedInformation.getColorPrinter().getImagesQueue().size() - 1; i++) {
                mutNode(Integer.toString(i)).addLink(mutNode(Integer.toString(i+1)));
            }
        }).toImmutable().graphAttr().with(Rank.dir(LEFT_TO_RIGHT));

        produceGraph(graph, "Color Printer Queue");
    }

    private void bnwPrinterQueue() {
        Graph graph = mutGraph("Black and white Printer Queue").setDirected(true).use((gr, ctx) -> {
            for (int i = 0; i < savedInformation.getBnwPrinter().getImagesQueue().size(); i++) {
                mutNode(Integer.toString(i)).add(Label.of( savedInformation.getBnwPrinter().getImagesQueue().getPosition(i).getType().toString()));
            }

            for (int i = 0; i < savedInformation.getBnwPrinter().getImagesQueue().size() - 1; i++) {
                mutNode(Integer.toString(i)).addLink(mutNode(Integer.toString(i+1)));
            }
        }).toImmutable().graphAttr().with(Rank.dir(LEFT_TO_RIGHT));

        produceGraph(graph, "Black and white Printer Queue");
    }

    private void waitingClientsList() {
        Graph graph = mutGraph("Waiting Clients List").setDirected(true).use((gr, ctx) -> {
            for (int i = 0; i < savedInformation.getWaitingList().size(); i++) {
                mutNode(Integer.toString(i)).add(Label.of(savedInformation.getWaitingList().getPosition(i).getName()));
            }

            for (int i = 0; i < savedInformation.getWaitingList().size() - 1; i++) {
                mutNode(Integer.toString(i)).addLink(mutNode(Integer.toString(i+1)));
            }
        }).toImmutable().graphAttr().with(Rank.dir(LEFT_TO_RIGHT));

        produceGraph(graph, "Waiting Clients List");
    }

    private void produceGraph(Graph graph, String name) {
        try {
            File file = new File("out/" + name + ".png");
            Graphviz.fromGraph(graph).render(Format.PNG).toFile(file);
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
            System.out.println("Grafica generada exitósamente");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ha ocurrido un problema");
        }
    }

}
