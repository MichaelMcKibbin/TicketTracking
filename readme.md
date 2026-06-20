# Ticket Tracking System (JavaFX)
<p align="center">
  <img src="https://img.shields.io/badge/Java-21-blue.svg" alt="Java Version">
  <img src="https://img.shields.io/badge/JavaFX-21-blue.svg" alt="JavaFX Version">
<img src="https://img.shields.io/badge/CSS-3-blue.svg" alt="CSS Version">
  <img src="https://img.shields.io/badge/Build-Maven-informational.svg" alt="Maven Build">
  <img src="https://img.shields.io/github/languages/top/MichaelMcKibbin/TicketTrackingApp" alt="Top Language">
</p>

A desktop **Trouble Ticket Tracking System** built with **Java 17+ and JavaFX, with JSON persistence.**  
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

- **Language:** Java 21
- **UI toolkit:** JavaFX (`javafx.controls`, `javafx.fxml`)
- **JSON:** Jackson (`com.fasterxml.jackson.databind`, `com.fasterxml.jackson.datatype.jsr310`)
- **Build / IDE:**
    - Designed to run directly from an IDE such as IntelliJ IDEA
    - Uses `module-info.java` for JavaFX module configuration

---

## Project Structure

```text
src/
  main/
    java/
      module-info.java
      com/
        tickettracking/
          TicketApplication.java      # JavaFX entry point
          MainViewController.java     # Main window controller (table, filters, menu)
          Ticket.java                 # Ticket domain model
          Comment.java                # Comment model
          User.java                   # User model (with Role enum)
          TicketService.java          # Ticket CRUD + JSON persistence
          UserService.java            # In-memory user list + current user
          TicketDialogController.java # "New Ticket" dialog controller
          EditTicketDialogController.java # "Edit Ticket" + comments controller

    resources/
      sample_tickets.json             # Example ticket data (JSON)
      views/
        main-view.fxml                # Main window layout
        ticket-dialog.fxml            # New Ticket dialog layout
        edit-ticket-dialog.fxml       # Edit Ticket dialog layout
        styles.css                    # JavaFX CSS styling

```


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

The potential adaptations are endless, as the core functionality of creating, viewing, editing, filtering, and persisting records can be applied to many domains.

