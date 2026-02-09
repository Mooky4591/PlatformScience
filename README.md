# PlatformScience Driver Assignment App

## Overview

This project is an Android application built for Platform Science’s driver assignment challenge. The app assigns drivers to shipments using a suitability score algorithm, with an emphasis on modern Android architecture, testability, and separation of concerns.

Highlights:

* Clear domain modeling
* Testable business logic
* MVVM with unidirectional state
* Jetpack Compose UI

---

## Problem Summary

Given:

* A list of drivers
* A list of shipment destinations

The app computes a suitability score for each driver–shipment pairing and produces a one-to-one assignment between drivers and shipments based on those scores.

---

## Suitability Score Rules

For a given driver and shipment destination:

1. **Even street name length**

   * Base score = (number of vowels in the driver’s name) × 1.5

2. **Odd street name length**

   * Base score = (number of consonants in the driver’s name) × 1.0

3. **Common factors**

   * If the length of the driver’s name and the length of the destination street name share any common factors greater than 1, the base score is increased by 50%

All scoring logic is isolated in the domain layer and unit tested.

---

## Assignment Strategy

* Drivers and shipments are assigned in a one-to-one mapping
* Suitability scores are calculated per pairing
* Assignment logic is encapsulated in the domain layer
* The resulting assignments are persisted locally and displayed via the UI

The assignment logic is decoupled from UI and data sources to allow independent testing and future extension.

---

## Architecture

The app follows a clean MVVM architecture with explicit layering:

### Presentation

* Jetpack Compose UI
* ViewModels expose immutable UI state

### Domain

* Assignment logic
* Suitability scoring
* Parsing and transformation rules

### Data

* Room database
* DAOs for drivers, shipments, and assignments
* Seeded data source for initial driver and shipment data

Dependency injection is handled via Hilt.

---

## Key Technologies

* Kotlin
* Jetpack Compose
* Hilt
* Room
* Navigation Component
* JUnit
* Compose UI Testing

---

## Project Structure

```text
app/
  src/
    main/
      java/com/scottrobinson/platformscience/
        data/
          local/
            roomdb/
              database/
              daos/
        driverlist/
          domain/
            assignment/
            parser/
            seeder/
            suitability/
          presentation/
            viewmodel/
        driverassignment/
          di/
          domain/
          presentation/
            viewmodel/
        navigation/
        main/
    test/
      java/com/scottrobinson/platformscience/
    androidTest/
      java/com/scottrobinson/platformscience/
```

Generated Hilt and Room code is excluded for clarity.

---

## Key Classes

* `driverlist.domain.suitability.SuitabilityScorerImpl`
  Implements the suitability score calculation rules.

* `driverlist.domain.assignment.AssignmentImpl`
  Handles driver–shipment assignment logic.

* `driverlist.domain.seeder.RoomShipmentDriverSeederImpl`
  Seeds initial driver and shipment data into the database.

* `driverlist.presentation.viewmodel.HomeScreenViewModel`
  Drives the driver list and assignment flow.

* `driverassignment.presentation.viewmodel.DriverAssignmentScreenViewModel`
  Exposes assignment results to the UI.

---

## Testing

### Unit Tests

* Validate suitability score calculation
* Verify vowel and consonant counting
* Confirm common factor detection
* Cover boundary cases for name and street lengths

### UI Tests

* Compose screen rendering
* Navigation flows
* Assignment result presentation

Run tests:

```bash
./gradlew test
```

```bash
./gradlew connectedAndroidTest
```

Test reports:

```text
app/build/reports/tests/
```

---

## Getting Started

### Requirements

* Android Studio (Giraffe or newer)
* JDK 11+
* Android SDK 24+

### Running the App

1. Clone the repository
2. Open the project in Android Studio
3. Allow Gradle to sync
4. Run on an emulator or physical device

---

## Notes

* Business logic is testable and UI-agnostic
* No network dependencies are required
* The project is intentionally scoped for clarity and correctness

---

## License

This project is provided strictly for demonstration and interview evaluation purposes.
