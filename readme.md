# Ticket Tracking System (JavaFX)
<p align="center">
  <img src="https://img.shields.io/badge/Java-21-blue.svg" alt="Java Version">
  <img src="https://img.shields.io/badge/JavaFX-21-blue.svg" alt="JavaFX Version">
  <img src="https://img.shields.io/badge/CSS-3-blue.svg" alt="CSS Version">
  <img src="https://img.shields.io/badge/Build-Maven-informational.svg" alt="Maven Build">
  <img src="https://img.shields.io/badge/Tests-73%20passing-brightgreen.svg" alt="Tests">
  <img src="https://img.shields.io/github/languages/top/MichaelMcKibbin/TicketTrackingApp" alt="Top Language">
</p>

A desktop **Trouble Ticket Tracking System** built with **Java 21 and JavaFX, with JSON persistence.**  
It lets you create, view, edit, filter, and delete support tickets, with basic status/priority handling and comments.

This project is ideal for learning JavaFX, MVC patterns, and desktop data management.

---

## Features

- Create new tickets with:
  - Title
  - Description
  - Status (e.g. NEW, IN_PROGRESS, ON_HOLD, RESOLVED, CLOSED)
  - Priority (LOW, MEDIUM, HIGH, CRITICAL)
  - Assigned To (e.g. name or 'support1', 'admin', etc.)

- View all tickets in a sortable table:
  - ID, Title, Status, Priority, Assigned To, Created At, Description

- Filter and search:
  - Filter by **Status** and **Priority**
  - Free-text search on ID, title, status, assigned to, and description

- Edit existing tickets:
  - Double-click a ticket row to open the edit dialog
  - Update status, priority, description, assignment, etc.

- Comment support:
  - Add comments to a ticket in the edit dialog
  - Each comment stores content, author, and timestamp

- JSON persistence:
  - Tickets are stored in a local `tickets.json` file
  - Uses Jackson and `JavaTimeModule` to handle `LocalDateTime`

- Sample data:
  - Includes `sample_tickets.json` with example tickets you can copy/use as seed data

- Basic save indicator:
  - A status bar label shows when changes are saved or if there are unsaved changes

---

## Tech Stack

| Component     | Library / Version                                                                                               |
|---------------|-----------------------------------------------------------------------------------------------------------------|
| Language      | Java 21                                                                                                         |
| UI toolkit    | JavaFX 21 (`javafx.controls`, `javafx.fxml`, `javafx.web`, `javafx.swing`)                                      |
| UI extras     | ControlsFX 11.1.2, FormsFX 11.6.0, TilesFX 11.48                                                                |
| JSON          | Jackson Databind 2.15.3 + `jackson-datatype-jsr310` (for `LocalDateTime`)                                       |
| Testing       | JUnit Jupiter 5.10.0                                                                                            |
| Build         | Maven (via wrapper), `maven-compiler-plugin` 3.11.0, `maven-surefire-plugin` 3.1.2, `javafx-maven-plugin` 0.0.8 |
| Module system | JPMS (`module-info.java`) — module name `com.michaelmckibbin.tickettracking`                                    |
| Architecture  | MVC (Model-View-Controller)                                                                                     |

---

## Project Structure

```
src/
  main/
    java/
      module-info.java                        # JPMS module descriptor
      com/tickettracking/
        TicketApplication.java                # JavaFX entry point (extends Application)
        MainViewController.java               # Main window controller (table, filters, menu)
        Ticket.java                           # Ticket domain model (Status & Priority enums)
        Comment.java                          # Comment model (content, author, timestamp)
        User.java                             # User model (with Role enum)
        TicketService.java                    # Ticket CRUD + JSON persistence
        UserService.java                      # In-memory user list + current user
        TicketDialogController.java           # "New Ticket" dialog controller
        EditTicketDialogController.java       # "Edit Ticket" + comments controller

    resources/
      sample_tickets.json                     # Example seed data — copy to tickets/tickets.json
      tickets/
        tickets.json                          # Live data file (read/written at runtime)
      images/                                 # Application images/icons
      views/
        main-view.fxml                        # Main window layout
        ticket-dialog.fxml                    # New Ticket dialog layout
        edit-ticket-dialog.fxml               # Edit Ticket dialog layout
        styles.css                            # JavaFX CSS styling

  test/
    java/
      com/tickettracking/
        TicketTest.java                       # Unit tests for Ticket model
        CommentTest.java                      # Unit tests for Comment model
        UserTest.java                         # Unit tests for User model
        UserServiceTest.java                  # Unit tests for UserService
        TicketServiceTest.java                # Unit tests for TicketService CRUD & validation
```

---

## Prerequisites

| Requirement    | Version                                                     |
|----------------|-------------------------------------------------------------|
| JDK            | 21 or later                                                 |
| Maven          | Not required — Maven Wrapper (`mvnw`) is included           |
| IDE (optional) | IntelliJ IDEA (recommended), or any IDE with JavaFX support |

> **No system-wide Maven installation is needed.** All commands below use the included `mvnw.cmd` (Windows) / `mvnw` (Linux/macOS) wrapper.

---

## Building

```
# Windows
.\mvnw.cmd clean compile
```
```
# Linux / macOS
./mvnw clean compile
```

---

## Running the Application

### From the IDE
Open the project in IntelliJ IDEA and run `TicketApplication.java` directly.  
IntelliJ handles the JavaFX module path automatically.

### From the command line (Maven JavaFX plugin)
```
# Windows
.\mvnw.cmd clean javafx:run
```
```
# Linux / macOS
./mvnw clean javafx:run
```

### Data file location
Tickets are persisted to:
```
src/main/resources/tickets/tickets.json
```
Sample seed data is available at `src/main/resources/sample_tickets.json` — copy its contents into `tickets.json` to pre-populate the app.

---

## Running the Tests

```
# Windows
.\mvnw.cmd test
```
```
# Linux / macOS
./mvnw test
```

All 73 tests should pass with no failures or errors.

### Test suite breakdown

| Test class | Tests | What it covers |
|---|---|---|
| `TicketTest` | 12 | Ticket model — field getters/setters, enums, defaults |
| `CommentTest` | 15 | Comment model — content, author, timestamp |
| `UserTest` | 12 | User model — fields, Role enum |
| `UserServiceTest` | 14 | UserService — add, find, current-user management |
| `TicketServiceTest` | 20 | TicketService — save, update, delete, validation, edge cases |

> Tests that intentionally trigger validation errors (e.g. null ID, missing title) throw `IllegalArgumentException` directly — these are expected and do **not** produce SEVERE log output.

---


---

## UI Features
- Color-coded priority column (low=green → high=red)
- Double-click a ticket to open the Edit dialog
- Clean, responsive JavaFX UI
- Separate dialogs for:
  - **New Ticket**
  - **Edit Ticket**

---

## Potential Adaptations / Variations

- Invoice Management System
- Bug Tracking System
- Customer Relationship Management (CRM) System
- Help Desk Ticketing System
- Project Task Management System
- Asset Management System
- Inventory Tracking System
- Order Management System
- Appointment Scheduling System
- Event Registration System
- Personal Task Organizer / Diary
- Student Assignment Tracker
- Personal Knowledge Base / Note-Taking System
- Vulnerability Tracking System
- Evidence Management System
- PenTesting / QA Tracking System
- Bug Bounty / Security Issue Tracking System

The potential adaptations are endless, as the core functionality of Creating, Reading, Updating, Deleting (CRUD), filtering, and persisting records can be applied to many domains.

