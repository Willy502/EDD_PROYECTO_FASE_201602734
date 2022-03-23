package barrios.alejandro.udrawingpage.dashboard.controller;

import barrios.alejandro.udrawingpage.structures.controller.BinarySearchTree;
import barrios.alejandro.udrawingpage.structures.controller.SinglyLinkedList;
import barrios.alejandro.udrawingpage.structures.controller.SparceMatrix;
import barrios.alejandro.udrawingpage.users.model.Rol;
import barrios.alejandro.udrawingpage.users.model.User;
import barrios.alejandro.udrawingpage.utils.CustomAlert;
import barrios.alejandro.udrawingpage.utils.TemporalInformation;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DashboardController {

    @FXML
    protected Label lblName;

    @FXML
    protected MenuItem btnLoadClients, btnLoadCapas, btnLoadImages, btnLoadAlbums;

    private TemporalInformation temporalInformation;

    public DashboardController() {
        temporalInformation = TemporalInformation.getInstance();
    }

    @FXML
    public void initialize() {
        lblName.setText(temporalInformation.getLoguedUser().getName());

        if (temporalInformation.getLoguedUser().getRol() == Rol.ADMIN) {
            btnLoadCapas.setVisible(false);
            btnLoadImages.setVisible(false);
            btnLoadAlbums.setVisible(false);
        } else {
            btnLoadClients.setVisible(false);
        }
    }

    @FXML
    protected void logout(ActionEvent event) {
        temporalInformation.setLoguedUser(null);
        String pack = "/barrios/alejandro/udrawingpage/users/";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(pack + "login-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void loadFile(ActionEvent event) {
        String id = ((MenuItem) event.getSource()).getId();
        Stage stage = (Stage) lblName.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON Files", "*.json")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                massiveCharge(id, selectedFile.getAbsolutePath());
            } catch (FileNotFoundException e) {
                new CustomAlert("Archivo no encontrado", "El archivo seleccionado ha sido eliminado o se encuentra corrupto");
            }

        }

    }

    private void massiveCharge(String id, String path) throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader(path));
        JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

        switch (id) {
            case "btnLoadClients" -> {
                jsonArray.forEach(user -> {
                    JsonObject item = (JsonObject) user;
                    User insideUser = new User(
                            Long.parseLong(item.get("dpi").getAsString()),
                            item.get("nombre_cliente").getAsString(),
                            item.get("password").getAsString(),
                            Rol.CLIENT
                    );
                    temporalInformation.getUsersTree().insert(insideUser);
                });
                new CustomAlert("Carga finalizada", "Carga masiva de clientes finalizada exitosamente");
                temporalInformation.getUsersTree().showBTree();
            }
            case "btnLoadCapas" -> {
                temporalInformation.getLoguedUser().setCapas();
                jsonArray.forEach(capa -> {
                    JsonObject item = (JsonObject) capa;
                    int idCapa = item.get("id_capa").getAsInt();
                    JsonArray pexels = item.get("pixeles").getAsJsonArray();

                    int maxX = 0;
                    int maxY = 0;

                    for (JsonElement pexelsInfo : pexels) {
                        JsonObject info = (JsonObject) pexelsInfo;
                        int fila = info.get("fila").getAsInt();
                        if (fila > maxX) maxX = fila;
                        int columna = info.get("columna").getAsInt();
                        if (columna > maxY) maxY = fila;
                    }

                    SparceMatrix newCapa = new SparceMatrix(idCapa, maxX, maxY);
                    pexels.forEach(pexelsInfo -> {
                        JsonObject info = (JsonObject) pexelsInfo;
                        int fila = info.get("fila").getAsInt();
                        int columna = info.get("columna").getAsInt();
                        String color = info.get("color").getAsString();
                        newCapa.saveCell(fila, columna, color);
                    });
                    temporalInformation.getLoguedUser().getCapas().addToList(newCapa);
                });
                new CustomAlert("Carga finalizada", "Carga masiva de capas finalizada exitosamente");
                SparceMatrix newCapa = new SparceMatrix(10, 9, 9);
                newCapa.graphMatrix();
            }
            case "btnLoadImages" -> {
                temporalInformation.getLoguedUser().setImages();
                jsonArray.forEach(image -> {
                    JsonObject imageInfo = (JsonObject) image;
                    int imageId = imageInfo.get("id").getAsInt();
                    BinarySearchTree bstImage = new BinarySearchTree(imageId);
                    SinglyLinkedList<SparceMatrix> layers = temporalInformation.getLoguedUser().getCapas();

                    JsonArray layersArray = imageInfo.get("capas").getAsJsonArray();
                    int i = 0;
                    while (i < layersArray.size()) {
                        int idCapa = layersArray.get(i).getAsInt();
                        for (int j = 0; j < layers.size(); j++) {
                            SparceMatrix layer = layers.getPos(j);
                            if (layer.id == idCapa) {
                                bstImage.insert(layer);
                                break;
                            }
                        }
                        i++;
                    }


                    temporalInformation.getLoguedUser().getImages().insert(bstImage);
                });
                new CustomAlert("Carga finalizada", "Carga masiva de imagenes finalizada exitosamente");
            }
            case "btnLoadAlbums" -> {
                temporalInformation.getLoguedUser().setAlbumes();
                jsonArray.forEach(album -> {

                    JsonObject albumInfo = (JsonObject) album;
                    String nombreAlbum = albumInfo.get("nombre_album").getAsString();
                    temporalInformation.getLoguedUser().getAlbumes().createAlbum(nombreAlbum);

                    JsonArray imagesArray = albumInfo.get("imgs").getAsJsonArray();
                    int i = 0;
                    while (i < imagesArray.size()) {
                        int idImage = imagesArray.get(i).getAsInt();
                        BinarySearchTree image = temporalInformation.getLoguedUser().getImages().search(idImage);
                        if (image != null) {
                            temporalInformation.getLoguedUser().getAlbumes().getLastAlbum().addToList(image);
                        }
                        i++;
                    }

                });
                new CustomAlert("Carga finalizada", "Carga masiva de albumes finalizada exitosamente");
            }
        }
    }

}
