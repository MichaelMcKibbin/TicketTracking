package com.tickettracking;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainViewController {
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

    private ObservableList<com.tickettracking.Ticket> tickets = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize table columns
        setupTableColumns();

        // Setup filters
        setupFilters();

        // Load initial data
        loadTickets();
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

    @FXML
    private void createNewTicket() {
        // Open ticket creation form
    }

    private void filterTickets() {
        // Implement search/filter logic
    }

    public void exitApplication(ActionEvent actionEvent) {
    }
}
