package com.tickettracking;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import com.tickettracking.Ticket;  // Import Ticket class

public class EditTicketDialogController {
    @FXML private TextField titleField;
    @FXML private ComboBox<Ticket.Status> statusComboBox;  // Use Ticket.Status
    @FXML private ComboBox<Ticket.Priority> priorityComboBox;
    @FXML private TextField assignedToField;

    private Ticket ticket;

    @FXML
    public void initialize() {
        // Initialize combo boxes with enum values
        statusComboBox.getItems().setAll(Ticket.Status.values());
        priorityComboBox.getItems().setAll(Ticket.Priority.values());
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;

        // Populate fields with ticket data
        if (ticket != null) {
            titleField.setText(ticket.getTitle());
            statusComboBox.setValue(ticket.getStatus());
            priorityComboBox.setValue(ticket.getPriority());
            assignedToField.setText(ticket.getAssignedTo());
        }
    }

    @FXML
    private void handleSave() {
        if (ticket != null) {
            ticket.setTitle(titleField.getText());
            ticket.setStatus(statusComboBox.getValue());
            ticket.setPriority(priorityComboBox.getValue());
            ticket.setAssignedTo(assignedToField.getText());
        }
        closeDialog();
    }

    @FXML
    private void handleCancel() {
        closeDialog();
    }

    private void closeDialog() {
        titleField.getScene().getWindow().hide();
    }
}
