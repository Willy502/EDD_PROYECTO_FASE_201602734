module barrios.alejandro.udrawingpage  {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.google.gson;
    requires jbcrypt;

    opens barrios.alejandro.udrawingpage to javafx.fxml;
    opens barrios.alejandro.udrawingpage.users.controller to javafx.fxml;
    opens barrios.alejandro.udrawingpage.dashboard.controller to javafx.fxml;
    exports barrios.alejandro.udrawingpage;
    exports barrios.alejandro.udrawingpage.utils;
    opens barrios.alejandro.udrawingpage.utils to javafx.fxml;
}