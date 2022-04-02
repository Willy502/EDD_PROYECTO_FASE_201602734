package barrios.alejandro.udrawingpage.dashboard.controller;

import barrios.alejandro.udrawingpage.structures.controller.AvlTree;
import barrios.alejandro.udrawingpage.structures.controller.BinarySearchTree;
import barrios.alejandro.udrawingpage.structures.controller.SinglyLinkedList;
import barrios.alejandro.udrawingpage.structures.controller.SparceMatrix;
import barrios.alejandro.udrawingpage.users.controller.UserController;
import barrios.alejandro.udrawingpage.users.model.Rol;
import barrios.alejandro.udrawingpage.users.model.User;
import barrios.alejandro.udrawingpage.utils.CustomAlert;
import barrios.alejandro.udrawingpage.utils.TemporalInformation;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class DashboardController {

    @FXML
    protected Label lblName, clientFormTitle;
    @FXML
    protected MenuItem btnLoadClients, btnLoadCapas, btnLoadImages, btnLoadAlbums, btnLoadImage;
    @FXML
    protected StackPane mainPane;
    @FXML
    protected ComboBox<BinarySearchTree> comboImages, comboGraphFullImage;
    @FXML
    protected VBox vboxClient, mainBox, registerBox;
    @FXML
    protected TextField txtCapas, txtNoLayer, txtName, txtDpi;
    @FXML
    protected Button btnClientsTree, btnEdit;
    @FXML
    protected PasswordField txtPassword;
    @FXML
    protected ListView<User> listViewClients;

    private final TemporalInformation temporalInformation;

    public DashboardController() {
        temporalInformation = TemporalInformation.getInstance();
    }

    @FXML
    public void initialize() {
        lblName.setText(temporalInformation.getLoguedUser().getName());
        fillChoicer();
        populateListView();
        btnEdit.setOnMouseClicked(event -> createUser());

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
        btnLoadImage.setVisible(false);
        mainBox.getChildren().remove(vboxClient);
    }

    private void hideClient() {
        btnLoadClients.setVisible(false);
        mainBox.getChildren().remove(btnClientsTree);
        mainBox.getChildren().remove(registerBox);
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
            } catch (FileNotFoundException | NullPointerException e) {
                new CustomAlert("Archivo no encontrado", "El archivo seleccionado ha sido eliminado o se encuentra corrupto");
            }

        }

    }

    private void populateListView() {
        ObservableList<User> usersList = FXCollections.observableArrayList();
        SinglyLinkedList<User> users = temporalInformation.getUsersTree().searchByLevels();

        for (SinglyLinkedList.Node<User> user = users.getHead(); user != null; user = user.next)
            usersList.add(user.data);
        listViewClients.setItems(usersList);

        listViewClients.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                User userSelected = listViewClients.getSelectionModel().getSelectedItem();
                editClient(userSelected);
            }
        });
    }

    private void editClient(User user) {
        clientFormTitle.setText("Editar " + user.dpi);
        txtDpi.setText(Long.toString(user.dpi));
        txtName.setText(user.getName());
        txtPassword.setText(user.getPassword());
        btnEdit.setText("Finalizar edición");
        btnEdit.setOnMouseClicked(mouseEvent -> {

            final User searchedUser;
            try {
                searchedUser = temporalInformation.getUsersTree().searchUserByDpi(Long.parseLong(txtDpi.getText()));
    
                if (txtName.getText() == "" || txtPassword.getText() == "")
                    throw new NullPointerException();
    
            } catch (Exception e) {
                new CustomAlert("Error", "Faltan campos");
                return;
            }

            if (searchedUser == null) {
                user.setName(txtName.getText());
                user.setPassword(txtPassword.getText());
                user.dpi = Long.parseLong(txtDpi.getText());
                new CustomAlert("Usuario editado exitosamente", "Se ha editado un usuario exitosamente");
                clientFormTitle.setText("Registrar cliente");
                btnEdit.setText("Registrar");
                populateListView();
                clearFields();
                btnEdit.setOnMouseClicked(event -> createUser());
            }
            else new CustomAlert("Usuario existente", "Este DPI ya ha sido tomado por otro usuario");

            
        });

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

    private void massiveCharge(String id, String path) throws FileNotFoundException, NullPointerException {
        JsonReader reader = new JsonReader(new FileReader(path));
        JsonArray jsonArray = null;
        if (!Objects.equals(id, "btnLoadImage"))
            jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

        switch (id) {
            case "btnLoadClients" -> {
                assert jsonArray != null;
                jsonArray.forEach(user -> {
                    JsonObject item = (JsonObject) user;
                    User insideUser = new User(
                            Long.parseLong(item.get("dpi").getAsString()),
                            item.get("nombre_cliente").getAsString(),
                            item.get("password").getAsString(),
                            Rol.CLIENT
                    );

                    if (temporalInformation.getUsersTree().searchUserByDpi(insideUser.dpi) == null)
                        temporalInformation.getUsersTree().insert(insideUser);
                });
                new CustomAlert("Carga finalizada", "Carga masiva de clientes finalizada exitosamente");
                populateListView();
            }
            case "btnLoadCapas" -> {
                if (temporalInformation.getLoguedUser().getCapas() == null)
                    temporalInformation.getLoguedUser().setCapas();
                int maxX = 0;
                int maxY = 0;

                assert jsonArray != null;
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
                    if (temporalInformation.getLoguedUser().getCapas().searchLayer(newCapa.id) == null)
                        temporalInformation.getLoguedUser().getCapas().insert(newCapa);
                }


                new CustomAlert("Carga finalizada", "Carga masiva de capas finalizada exitosamente");
            }
            case "btnLoadImages" -> {
                if (temporalInformation.getLoguedUser().getImages() == null)
                    temporalInformation.getLoguedUser().setImages();
                assert jsonArray != null;
                jsonArray.forEach(image -> {
                    JsonObject imageInfo = (JsonObject) image;
                    createImage(imageInfo);
                });
                new CustomAlert("Carga finalizada", "Carga masiva de imagenes finalizada exitosamente");
                fillChoicer();
            }
            case "btnLoadAlbums" -> {
                if (temporalInformation.getLoguedUser().getAlbumes() == null)
                    temporalInformation.getLoguedUser().setAlbumes();
                assert jsonArray != null;
                jsonArray.forEach(album -> {

                    JsonObject albumInfo = (JsonObject) album;
                    String nombreAlbum = albumInfo.get("nombre_album").getAsString();
                    if (temporalInformation.getLoguedUser().getAlbumes().searchAlbumByNombre(nombreAlbum) == null) {
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
                    }
                    

                });
                new CustomAlert("Carga finalizada", "Carga masiva de albumes finalizada exitosamente");
            }
            case "btnLoadImage" -> {
                if (temporalInformation.getLoguedUser().getImages() == null)
                    temporalInformation.getLoguedUser().setImages();
                JsonObject imageInfo = JsonParser.parseReader(reader).getAsJsonObject();
                createImage(imageInfo);
                new CustomAlert("Carga finalizada", "Imagen cargada exitosamente");
                fillChoicer();
            }
        }
    }

    private void createImage(JsonObject imageInfo) {
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

        if (temporalInformation.getLoguedUser().getImages().search(bstImage.id) == null)
            temporalInformation.getLoguedUser().getImages().insert(bstImage);
    }

    @FXML
    protected void structuresState(ActionEvent event) {
        String id = ((Button) event.getSource()).getId();
        try {
            switch (id) {
                case "btnClientsTree" -> new StructuresReport().buildBTree(mainPane, temporalInformation.getUsersTree());
                case "btnLayer" -> new StructuresReport().buildLayer(mainPane, Integer.parseInt(txtNoLayer.getText()));
                case "btnImagesTree" -> new StructuresReport().buildAvlImages(mainPane, temporalInformation.getLoguedUser().getImages());
                case "btnLayersTree" -> new StructuresReport().buildBinaryLayers(mainPane, temporalInformation.getLoguedUser().getCapas());
                case "btnAlbumsList" -> new StructuresReport().buildCircularAlbums(mainPane, temporalInformation.getLoguedUser().getAlbumes());
                case "btnImageAndLayers" -> new StructuresReport().buildImageAndLayers(mainPane, temporalInformation.getLoguedUser().getImages().search(Integer.parseInt(comboImages.getValue().toString())));
            }
        } catch(Exception e) {
            new CustomAlert("Error", "No se ha cargado información previa");
        }
        

    }

    @FXML
    protected void buildImageOrder(ActionEvent event) {
        String id = ((Button) event.getSource()).getId();

        try {
            switch (id) {
                case "btnPreorder" -> new GraphImage().buildImage(mainPane, temporalInformation.getLoguedUser().getImages().search(Integer.parseInt(comboGraphFullImage.getValue().toString())), "PREORDER");
                case "btnInorder" -> new GraphImage().buildImage(mainPane, temporalInformation.getLoguedUser().getImages().search(Integer.parseInt(comboGraphFullImage.getValue().toString())), "INORDER");
                case "btnPostorder" -> new GraphImage().buildImage(mainPane, temporalInformation.getLoguedUser().getImages().search(Integer.parseInt(comboGraphFullImage.getValue().toString())), "POSTORDER");
                case "btnAmplitud" -> new GraphImage().buildImage(mainPane, temporalInformation.getLoguedUser().getImages().search(Integer.parseInt(comboGraphFullImage.getValue().toString())), "AMPLITUD");
                case "btnCapas" -> new GraphImage().buildImage(mainPane, txtCapas.getText());
            }
        } catch (Exception e) {
            new CustomAlert("Error", "No se ha cargado información previa");
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

    @FXML
    protected void createUser() {
        User user;

        try {
            user = temporalInformation.getUsersTree().searchUserByDpi(Long.parseLong(txtDpi.getText()));

            if (txtName.getText() == "" || txtPassword.getText() == "")
                throw new NullPointerException();

        } catch (Exception e) {
            new CustomAlert("Error", "Faltan campos");
            return;
        }

        if (user == null) new UserController().createClient(txtName.getText(), Long.parseLong(txtDpi.getText()), txtPassword.getText());
        else new CustomAlert("Usuario existente", "Este DPI ya ha sido tomado por otro usuario");
        clearFields();
        populateListView();
    }

    private void clearFields() {
        txtPassword.clear();
        txtName.clear();
        txtDpi.clear();
    }

}
