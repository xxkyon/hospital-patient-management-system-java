# Hospital Patient Management System (Console Application)

## Overview

This project is a Java-based console application that simulates a daily hospital patient flow management system. It was designed with a strong focus on clean architecture, separation of concerns, data validation, persistence, and real-world logic.

The system manages patient registration and their progression through different hospital sectors, while persisting the daily state and generating logs for patients who have already been treated.

This project was built as a portfolio piece to demonstrate backend fundamentals, object-oriented design, state management, file persistence, and defensive programming practices.

---

## Main Features

1. Patient registration with validation
2. Queue-based patient flow between hospital sectors
3. Daily state persistence using JSON
4. Automatic reset when a new day starts
5. Logging of treated patients into a text file
6. Menu-driven console interface
7. Input validation and error handling
8. Modular and extensible architecture

---

## Hospital Workflow Model

The hospital operates with a fixed flow of sectors:

1. Reception
2. Medical
3. Medication
4. Seen (treated and exited)

Each patient must pass through these sectors in order. Internally, each sector is represented by its own queue structure.

---

## Application Flow

When the application starts:

1. The system loads the current daily state from disk
2. If no state exists or the stored date is different from today, a new state is created
3. The user interacts with the system via a console menu
4. All operations update the in-memory state
5. After every relevant action, the state is persisted again

This guarantees data consistency even if the program is closed and reopened during the same day.

---

## Menu Options

The main menu provides the following options:

1. Register patient  
2. Call next patient from reception  
3. Call next patient from medical  
4. Call next patient from medication  
5. Check daily state  
0. Exit  

Each option is validated before execution to prevent invalid input.

---

## Patient Registration

During registration, the system:

1. Requests a patient password (ticket number)
2. Requests the patient name
3. Validates both fields
4. Asks for confirmation before saving
5. Registers the patient in the reception queue

### Validation Rules

Password:
- Must contain exactly 5 digits
- Must be between 10000 and 99999
- Must be unique for the current day

Name:
- Cannot be empty or blank
- Allows letters, spaces, accents, apostrophes, and hyphens

If any validation fails, the user is prompted to try again.

---

## Patient Entity

Each patient stores the following data:

- Ticket number
- Name
- Current sector
- Entry time
- Exit time (only set when treatment is completed)

Entry time is automatically assigned at registration.  
Exit time is automatically assigned when the patient leaves the medication sector.

---

## Queue System

The system uses a generic abstract queue implementation:

- HospitalQueue<T>

Concrete queues extend this abstraction:

- ReceptionQueue
- MedicalQueue
- MedicationQueue

This approach ensures:

- FIFO behavior
- Code reuse
- Easy extensibility if new sectors are added

---

## Daily State Management

The DailyState object represents the entire hospital state for a single day.

It contains:

- Current date
- Reception queue
- Medical queue
- Medication queue
- Set of used passwords

Only one DailyState exists per day.

---

## Persistence Strategy

### Daily State Persistence

The daily state is persisted using JSON serialization with Gson.

- File name: daily_state.json
- Storage strategy: overwrite on every save
- Automatically reloaded when the application starts

If the stored date does not match the current system date, the state is discarded and recreated.

This ensures that each day starts with a clean hospital state.

---

### Seen Patients Log

Patients who complete all sectors are logged to a text file.

- File name: patients_seen.txt
- Append-only log
- Each line includes:
  - Timestamp
  - Ticket number
  - Patient name
  - Final sector

This file serves as a historical audit log and is intentionally separated from the daily operational state.

---

## Service Layer

The HospitalService class contains all business logic.

Responsibilities include:

1. Registering patients
2. Moving patients between sectors
3. Updating patient sector and timestamps
4. Persisting state changes
5. Printing the daily state

The UI layer never manipulates queues or state directly.

---

## Storage Abstraction

Persistence is abstracted via the StateStorage interface.

This allows:

- Swapping JSON storage for another implementation
- Easier testing
- Clear separation between logic and persistence

Current implementation:
- JsonStorage

---

## Input Validation

All user inputs are validated using a dedicated utility class.

Validation includes:

- Menu option range checks
- Password format checks
- Name format checks

This avoids duplicated logic and improves maintainability.

---

## Error Handling

The system is defensive by design:

- Invalid menu options are rejected
- Empty queues are handled gracefully
- File I/O failures do not crash the application
- Default states are created if persistence fails

---

## Project Structure

The project is organized into clear packages:

- app: Application entry point
- model: Domain entities and enums
- queue: Queue abstractions and implementations
- service: Business logic
- state: Daily state representation
- util: Utilities and persistence helpers

---

## Technologies Used

- Java 17
- Maven
- Gson for JSON serialization
- Standard Java I/O
- Object-oriented programming principles

---

## Design Decisions

1. Console-based interface to focus on logic and architecture
2. JSON persistence for transparency and easy debugging
3. One state per day to mirror real hospital workflows
4. Append-only log for treated patients to preserve history
5. Generic queue abstraction to support scalability

---

## Limitations

- No graphical user interface
- No concurrent access handling
- No multi-day history browsing inside the application
- No external database integration

These limitations are intentional to keep the project focused and readable.

---

## Possible Improvements

1. Graphical or web-based interface
2. Multi-day state browsing
3. Database-backed persistence
4. Reporting and analytics features
5. Unit and integration tests
6. Authentication for staff users

---

## How to Run

1. Clone the repository
2. Open the project in an IDE that supports Maven
3. Ensure Java 17 is installed
4. Run the Main class
5. Interact using the console menu

---

## Author Notes

This project was built to demonstrate clean backend design, real-world modeling, and disciplined separation of responsibilities. It emphasizes clarity, correctness, and extensibility over unnecessary complexity.

It is suitable as a foundation for larger systems or as a teaching example for backend fundamentals.
