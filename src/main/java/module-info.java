module EvanSmithC868 {
    requires java.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.desktop;
    requires java.sql.rowset;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires mysql.connector.j;
    //requires org.junit.jupiter.api;
    requires java.logging;
    requires javafx.graphics;
    //requires org.junit.jupiter.engine; // Add JUnit 5 Engine
    //requires org.junit.platform.commons; // Add JUnit platform commons

    // Open JavaFX packages
    opens model to javafx.base, javafx.fxml;
    opens controller to javafx.fxml;

    // Expose your own application packages
    exports main;
    exports DAO;
    exports model;
    exports controller;
    opens main to javafx.base, javafx.fxml;
}
