package barrios.alejandro.UDrawingPager.app.controller;

import barrios.alejandro.UDrawingPager.app.model.*;
import barrios.alejandro.UDrawingPager.structures.controller.SinglyLinkedList;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class InitialStepsController {

    SavedInformation savedInformation = SavedInformation.getInstance();

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
            try {
                saveFileInformation(chooser.getSelectedFile().getAbsolutePath());
            } catch (FileNotFoundException e) {
                System.out.println("No se ha encontrado el archivo");
            }

        } else {
            System.out.println("No has seleccionado ningún archivo");
        }
    }

    private void saveFileInformation(String path) throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader(path));
        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
        jsonObject.keySet().forEach(key -> {
            JsonObject value = jsonObject.getAsJsonObject(key);
            Client client = new Client(
                    Integer.parseInt(value.get("id_cliente").getAsString()),
                    value.get("nombre_cliente").getAsString()
            );
            // Save color images
            for (int i = 0; i < Integer.parseInt(value.get("img_color").getAsString()); i++) {
                client.getImages().append(new Image(PType.COLOR, client));
            }

            // Save black and white images
            for (int i = 0; i < Integer.parseInt(value.get("img_bw").getAsString()); i++) {
                client.getImages().append(new Image(PType.BLACK_N_WHITE, client));
            }

            savedInformation.getReceptionQueue().add(client);
        });
        System.out.println("Carga masiva finalizada exitósamente");
    }

}
