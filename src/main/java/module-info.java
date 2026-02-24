module org.example.firstlabasyncshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires javafx.graphics;

    opens org.example.firstlabasyncshop to javafx.fxml;
    exports org.example.firstlabasyncshop;
    exports org.example.firstlabasyncshop.ref;
    opens org.example.firstlabasyncshop.ref to javafx.fxml;
}