module barrios.alejandro.udrawingpage  {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens barrios.alejandro.udrawingpage to javafx.fxml;
    opens barrios.alejandro.udrawingpage.users.controller to javafx.fxml;
    exports barrios.alejandro.udrawingpage;
}