# PlatformScience Driver Assignment App

## Overview

This is an Android application for Platform Science's driver assignment challenge. The app demonstrates modern Android development best practices, including Jetpack Compose for UI, Hilt for dependency injection, Room for local data storage, and robust unit and UI testing. The core feature is to assign drivers to shipments based on a suitability score algorithm.

## Features

- **Driver List:** View a list of drivers loaded from a local data source.
- **Shipment List:** View a list of shipments (destinations) loaded from a local data source.
- **Assignment Algorithm:** Assigns drivers to shipments using a suitability score based on the rules provided in the challenge.
- **Suitability Score:**
  - If the street name length is even, the score is the number of vowels in the driver’s name multiplied by 1.5.
  - If the street name length is odd, the score is the number of consonants in the driver’s name multiplied by 1.0.
  - If the length of the shipment's destination street name shares any common factors (besides 1) with the length of the driver’s name, the score is increased by 50% above the base score.
- **Modern Android Architecture:**
  - MVVM pattern
  - Jetpack Compose UI
  - Hilt for dependency injection
  - Room for local database
  - Navigation component for screen transitions
- **Testing:**
  - Unit tests for core logic (including the suitability scorer)
  - Instrumented UI tests for Compose screens and navigation

## Project Structure

```
PlatformScience/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/scottrobinson/platformscience/
│   │   │   │   ├── driverlist/...
│   │   │   │   ├── driverassignment/...
│   │   │   │   ├── data/local/roomdb/...
│   │   │   │   ├── navigation/...
│   │   │   │   └── main/...
│   │   ├── test/
│   │   │   └── java/com/scottrobinson/platformscience/...
│   │   └── androidTest/
│   │       └── java/com/scottrobinson/platformscience/...
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Getting Started

### Prerequisites
- Android Studio (Giraffe or newer recommended)
- JDK 11+
- Android SDK 24+

### Build & Run
1. Clone the repository:
   ```
   git clone <repo-url>
   ```
2. Open the project in Android Studio.
3. Let Gradle sync and download dependencies.
4. Run the app on an emulator or device.

### Running Tests
- **Unit tests:**
  ```
  ./gradlew test
  ```
- **Instrumented (UI) tests:**
  ```
  ./gradlew connectedAndroidTest
  ```
- Test reports are generated in `app/build/reports/tests/`.

## Key Classes & Packages

- `driverlist.domain.suitability.SuitabilityScorerImpl` — Implements the suitability score algorithm.
- `driverlist.presentation.screens.DriverListScreen` — Compose UI for displaying drivers.
- `driverassignment.presentation.screen.DriverAssignmentScreen` — Compose UI for assigning drivers to shipments.
- `data.local.roomdb` — Room database setup, DAOs, and entities.
- `navigation` — Navigation graph and screen definitions.
- `main` — Application and theme setup.

## Suitability Score Algorithm

1. **Even street name length:**
   - Score = (number of vowels in driver name) × 1.5
2. **Odd street name length:**
   - Score = (number of consonants in driver name) × 1.0
3. **Common factors:**
   - If the length of the shipment's destination street name and the length of the driver’s name share any common factors (other than 1), increase the score by 50%.

## Testing

- **Unit tests:** Located in `app/src/test/java/` (e.g., `SuitabilityScorerImplTest`).
- **UI/Instrumented tests:** Located in `app/src/androidTest/java/` (e.g., `DriverListScreenTest`, `NavigationUiTest`).
- Run all tests with Gradle or from Android Studio.

## Code Quality

- Follows MVVM and clean architecture principles.
- Uses dependency injection (Hilt) for testability and modularity.
- All business logic is unit tested.
- UI is tested with Compose UI test framework.

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes
4. Push to your branch (`git push origin feature/your-feature`)
5. Open a pull request

## License

This project is for demonstration and interview purposes only. See [LICENSE](LICENSE) if present.

## Contact

For questions or feedback, contact Scott Robinson or open an issue in the repository.
