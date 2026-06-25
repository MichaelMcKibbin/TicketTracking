module com.michaelmckibbin.tickettracking {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires eu.hansolo.tilesfx;
    requires java.logging;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires static org.junit.jupiter.api;

    opens com.tickettracking to javafx.fxml, org.junit.platform.commons, org.junit.jupiter.api, org.junit.jupiter.engine;
    exports com.tickettracking;
}