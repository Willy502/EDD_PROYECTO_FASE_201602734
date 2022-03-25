package barrios.alejandro.udrawingpage.dashboard.controller;

import barrios.alejandro.udrawingpage.structures.controller.AvlTree;
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
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DashboardController {

    @FXML
    protected Label lblName, lblGraph;
    @FXML
    protected MenuItem btnLoadClients, btnLoadCapas, btnLoadImages, btnLoadAlbums;
    @FXML
    protected StackPane mainPane;
    @FXML
    protected TextField txtNoLayer, txtCapas;
    @FXML
    protected ComboBox<BinarySearchTree> comboImages, comboGraphFullImage;
    @FXML
    protected Button btnPreorder, btnInorder, btnPostorder, btnImagesTree, btnLayersTree, btnAlbumsList, btnClientsTree;
    @FXML
    protected HBox hboxLayer, hboxImageLayer, hboxLayersS;
    @FXML
    protected Line line;

    private final TemporalInformation temporalInformation;

    public DashboardController() {
        temporalInformation = TemporalInformation.getInstance();
    }

    @FXML
    public void initialize() {
        lblName.setText(temporalInformation.getLoguedUser().getName());
        fillChoicer();

        if (temporalInformation.getLoguedUser().getRol() == Rol.ADMIN) {
            hideAdmin();
        } else {
            hideClient();
        }
    }

    private void hideAdmin() {
        btnLoadCapas.setVisible(false);
        btnLoadImages.setVisible(false);
        btnLoadAlbums.setVisible(false);

        btnImagesTree.setVisible(false);
        btnLayersTree.setVisible(false);
        btnAlbumsList.setVisible(false);
        hboxLayer.setVisible(false);
        hboxImageLayer.setVisible(false);
        line.setVisible(false);
        lblGraph.setVisible(false);
        comboGraphFullImage.setVisible(false);
        btnPreorder.setVisible(false);
        btnInorder.setVisible(false);
        btnPostorder.setVisible(false);
        hboxLayersS.setVisible(false);
    }

    private void hideClient() {
        btnLoadClients.setVisible(false);
        btnClientsTree.setVisible(false);
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

    private void fillChoicer() {
        if (temporalInformation.getLoguedUser().getImages() != null) {
            temporalInformation.getLoguedUser().getImages().fillImages();
            comboGraphFullImage.getItems().clear();
            comboImages.getItems().clear();
            AvlTree images = temporalInformation.getLoguedUser().getImages();

            SinglyLinkedList<BinarySearchTree> imagesList = images.getImages();
            for (SinglyLinkedList.Node<BinarySearchTree> image = imagesList.getHead(); image != null; image = image.next) {
                comboImages.getItems().add(image.data);
                comboGraphFullImage.getItems().add(image.data);
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
                int maxX = 0;
                int maxY = 0;

                for (JsonElement capa : jsonArray) {
                    JsonObject item = (JsonObject) capa;
                    JsonArray pexels = item.get("pixeles").getAsJsonArray();

                    for (JsonElement pexelsInfo : pexels) {
                        JsonObject info = (JsonObject) pexelsInfo;
                        int fila = info.get("fila").getAsInt();
                        if (fila > maxX) maxX = fila;
                        int columna = info.get("columna").getAsInt();
                        if (columna > maxY) maxY = columna;
                    }
                }

                for (JsonElement capa : jsonArray) {
                    JsonObject item = (JsonObject) capa;
                    int idCapa = item.get("id_capa").getAsInt();
                    JsonArray pexels = item.get("pixeles").getAsJsonArray();

                    SparceMatrix newCapa = new SparceMatrix(idCapa, maxX, maxY);
                    pexels.forEach(pexelsInfo -> {
                        JsonObject info = (JsonObject) pexelsInfo;
                        int fila = info.get("fila").getAsInt();
                        int columna = info.get("columna").getAsInt();
                        String color = info.get("color").getAsString();
                        newCapa.saveCell(fila, columna, color);
                    });
                    temporalInformation.getLoguedUser().getCapas().insert(newCapa);
                }


                new CustomAlert("Carga finalizada", "Carga masiva de capas finalizada exitosamente");
            }
            case "btnLoadImages" -> {
                temporalInformation.getLoguedUser().setImages();
                jsonArray.forEach(image -> {
                    JsonObject imageInfo = (JsonObject) image;
                    int imageId = imageInfo.get("id").getAsInt();
                    BinarySearchTree bstImage = new BinarySearchTree(imageId);
                    BinarySearchTree layers = temporalInformation.getLoguedUser().getCapas();

                    JsonArray layersArray = imageInfo.get("capas").getAsJsonArray();
                    int i = 0;
                    while (i < layersArray.size()) {
                        int idCapa = layersArray.get(i).getAsInt();
                        SparceMatrix searchLay = layers.searchLayer(idCapa);
                        if (searchLay != null) bstImage.insert(searchLay);
                        i++;
                    }


                    temporalInformation.getLoguedUser().getImages().insert(bstImage);
                });
                new CustomAlert("Carga finalizada", "Carga masiva de imagenes finalizada exitosamente");
                fillChoicer();
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

    @FXML
    protected void structuresState(ActionEvent event) {
        String id = ((Button) event.getSource()).getId();

        switch (id) {
            case "btnLayer" -> new StructuresReport().buildLayer(mainPane, Integer.parseInt(txtNoLayer.getText()));
            case "btnImagesTree" -> new StructuresReport().buildAvlImages(mainPane, temporalInformation.getLoguedUser().getImages());
            case "btnLayersTree" -> new StructuresReport().buildBinaryLayers(mainPane, temporalInformation.getLoguedUser().getCapas());
            case "btnAlbumsList" -> new StructuresReport().buildCircularAlbums(mainPane, temporalInformation.getLoguedUser().getAlbumes());
            case "btnImageAndLayers" -> new StructuresReport().buildImageAndLayers(mainPane, temporalInformation.getLoguedUser().getImages().search(Integer.parseInt(comboImages.getValue().toString())));
        }

    }

    @FXML
    protected void buildImageOrder(ActionEvent event) {
        String id = ((Button) event.getSource()).getId();

        switch (id) {
            case "btnPreorder" -> new GraphImage().buildImage(mainPane, temporalInformation.getLoguedUser().getImages().search(Integer.parseInt(comboGraphFullImage.getValue().toString())), "PREORDER");
            case "btnInorder" -> new GraphImage().buildImage(mainPane, temporalInformation.getLoguedUser().getImages().search(Integer.parseInt(comboGraphFullImage.getValue().toString())), "INORDER");
            case "btnPostorder" -> new GraphImage().buildImage(mainPane, temporalInformation.getLoguedUser().getImages().search(Integer.parseInt(comboGraphFullImage.getValue().toString())), "POSTORDER");
            case "btnCapas" -> new GraphImage().buildImage(mainPane, txtCapas.getText());
        }
    }

    @FXML
    protected void goToReports(ActionEvent event) {
        String pack = "/barrios/alejandro/udrawingpage/dashboard/";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(pack + "reports-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
