package com.tickettracking;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.tickettracking.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import java.util.ArrayList;

public class MainViewController {
    private final TicketService ticketService;
    private ObservableList<com.tickettracking.Ticket> tickets = FXCollections.observableArrayList();

    private boolean hasUnsavedChanges = false;

    @FXML public TableColumn idColumn;
    @FXML public TableColumn titleColumn;
    @FXML public TableColumn statusColumn;
    @FXML public TableColumn priorityColumn;
    @FXML public TableColumn assignedToColumn;
    @FXML public TableColumn createdAtColumn;
    @FXML
    private TableView<com.tickettracking.Ticket> ticketTable;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<com.tickettracking.Ticket.Status> statusFilter;
    @FXML
    private ComboBox<com.tickettracking.Ticket.Priority> priorityFilter;
    @FXML private Label saveIndicatorLabel;



    @FXML
    public void initialize() {
        // Initialize table columns
        setupTableColumns();

        // Setup filters
        setupFilters();

        // Load initial data
        loadTickets();
    }

    public MainViewController() {
        ticketService = new TicketService();
        tickets = FXCollections.observableArrayList();
        loadExistingTickets();
    }

    private Ticket showTicketDialog(Ticket ticket) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ticket-dialog.fxml"));
            Parent root = loader.load();

            TicketDialogController controller = loader.getController();
            controller.setTicket(ticket);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ticket Details");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            return ticket; // return the modified ticket

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    try {
//        // Load the dialog FXML
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ticket-dialog.fxml"));
//        Parent dialogContent = loader.load();
//
//        // Create the dialog
//        Dialog<Ticket> dialog = new Dialog<>();
//        dialog.setTitle("New Ticket");
//        dialog.setHeaderText("Create a new ticket");
//        dialog.getDialogPane().setContent(dialogContent);
//
//        // Add buttons
//        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
//        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
//
//        // Get the controller
//        TicketDialogController controller = loader.getController();
//
//        // Set result converter
//        dialog.setResultConverter(buttonType -> {
//            if (buttonType == saveButtonType) {
//                return controller.getTicket();
//            }
//            return null;
//        });
//
//        // Show dialog and wait for result
//        Optional<Ticket> result = dialog.showAndWait();
//        return result.orElse(null);
//
//    } catch (IOException e) {
//        showErrorMessage("Error loading ticket dialog: " + e.getMessage());
//        return null;
//    }



private void showSuccessMessage(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Success");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

private void showErrorMessage(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

private void updateSaveIndicator() {
    // Assuming you have a Label or other node to show save status
    if (hasUnsavedChanges) {
        // Update UI to show unsaved changes
        saveIndicatorLabel.setText("Unsaved Changes *");
        saveIndicatorLabel.setStyle("-fx-text-fill: red;");
    } else {
        saveIndicatorLabel.setText("All Changes Saved");
        saveIndicatorLabel.setStyle("-fx-text-fill: green;");
    }
}

private boolean showSavePrompt() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Unsaved Changes");
    alert.setHeaderText("You have unsaved changes");
    alert.setContentText("Would you like to save your changes?");

    Optional<ButtonType> result = alert.showAndWait();
    return result.isPresent() && result.get() == ButtonType.OK;
}


    private void loadExistingTickets() {
        tickets.addAll(ticketService.loadTickets());
    }

//    @FXML
//    private void createNewTicket() {
//        Ticket newTicket = showTicketDialog();
//        if (newTicket != null) {
//            tickets.add(newTicket);
//            markAsUnsaved();
//        }
//    }

    @FXML
    private void createNewTicket() {
        // Create a new empty ticket
        Ticket newTicket = new Ticket();  // Assuming you have a default constructor

        // Show dialog with the new empty ticket
        Ticket result = showTicketDialog(newTicket);

        if (result != null) {
            tickets.add(result);
            markAsUnsaved();
        }
    }

    @FXML
    private void save() {
        saveAllTickets(); // Manual save option
    }

    private void saveAllTickets() {
        try {
            ticketService.saveTickets(new ArrayList<>(tickets));
            // Optionally show success message
            showSuccessMessage("Tickets saved successfully");
        } catch (Exception e) {
            // Show error message
            showErrorMessage("Failed to save tickets: " + e.getMessage());
        }
    }

    private void loadTickets() {
    }

    private void setupTableColumns() {
        // Configure table columns with cell value factories
    }

    private void setupFilters() {
        // Add listeners to search field and combo boxes
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTickets();
        });
    }



    private void filterTickets() {
        // Implement search/filter logic
    }

    private void markAsUnsaved() {
        hasUnsavedChanges = true;
        // Optionally update UI to indicate unsaved changes
        updateSaveIndicator();
    }



    @FXML
    private void exitApplication() {
        if (hasUnsavedChanges) {
            boolean shouldSave = showSavePrompt();
            if (shouldSave) {
                saveAllTickets();
            }
        }
        // Close application
        Platform.exit();
    }


}
