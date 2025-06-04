package com.tickettracking;

import com.tickettracking.Ticket;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.time.LocalDateTime;


public class TicketDialogController {
    @FXML
    private TextField titleField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private ComboBox<Ticket.Priority> priorityComboBox;  // Changed to Ticket.Priority

    private Ticket ticket;

//    @FXML
//    public void initialize() {
//        // Initialize components using Ticket.Priority
//        priorityComboBox.setItems(FXCollections.observableArrayList(Ticket.Priority.values()));
//    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
        // Populate the fields with ticket data
        titleField.setText(ticket.getTitle());
        descriptionArea.setText(ticket.getDescription());
        priorityComboBox.setValue(ticket.getPriority());
    }

    public Ticket getTicket() {
        // Create and return a new ticket from the form data
        return new Ticket(
            titleField.getText(),
            descriptionArea.getText(),
            priorityComboBox.getValue(),
            LocalDateTime.now()
        );
    }
    @FXML
    public void initialize() {
        // Initialize the priority combo box
        priorityComboBox.getItems().addAll(Ticket.Priority.values());

        // Add listeners to update the ticket object when fields change
        titleField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ticket != null) {
                ticket.setTitle(newValue);
            }
        });

        descriptionArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ticket != null) {
                ticket.setDescription(newValue);
            }
        });

        priorityComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (ticket != null) {
                ticket.setPriority(newValue);
            }
        });
    }
}


