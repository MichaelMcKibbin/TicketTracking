package com.tickettracking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TicketApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Create the TicketService
        TicketService ticketService = new TicketService();

        // Create the FXMLLoader
        FXMLLoader fxmlLoader = new FXMLLoader(TicketApplication.class.getResource("/views/main-view.fxml"));

        // Set up the controller factory
        fxmlLoader.setControllerFactory(param -> {
            if (param == MainViewController.class) {
                MainViewController controller = new MainViewController();
                controller.setTicketService(ticketService);
                return controller;
            }
            return null;
        });

        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Ticket Tracking System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}


