package barrios.alejandro.udrawingpage.dashboard.controller;

import barrios.alejandro.udrawingpage.utils.TemporalInformation;
import javafx.fxml.FXML;

public class PopupSendController {

    private final TemporalInformation temporalInformation;

    public PopupSendController() {
        temporalInformation = TemporalInformation.getInstance();
    }

    @FXML
    public void initialize() {
        System.out.println("HOLA POPUP");
    }

}
