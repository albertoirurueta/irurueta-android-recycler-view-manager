# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres to
[Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- Antora documentation site for the library.
- Gitflow-based GitHub Actions workflows for building, testing and publishing.
- This changelog, backfilled from the existing releases.

### Changed

- Build toolchain updated to Gradle 9.9.1, Android Gradle Plugin 9.5.1 and Kotlin 2.4.21.

### Fixed

- Corrections to the README.

## [1.1.5] - 2025-12-27

### Changed

- Minimum supported Android SDK raised from 24 to 26 (apps targeting API 24-25 can no longer use this release).
- Dependency and build tooling updates: AGP 8.13.2, Kotlin 2.3.0, Compose BOM 2025.12.01, Dokka 2.1.0,
  SonarQube plugin 7.2.2, MockK 1.14.7 and Maven publish plugin 0.35.0.
- Includes the changes prepared for 1.1.4, which was never tagged or published as a separate release.

## [1.1.3] - 2025-09-21

### Changed

- Updated the `hermes` API dependency from 1.0.0 to 1.2.0 (exposed transitively to consumers).
- Dependency and build tooling updates: AGP 8.13.0, Kotlin 2.2.20, Material 1.13.0, Compose BOM 2025.09.00,
  AndroidX Test and Espresso, SonarQube plugin 6.3.1, MockK 1.14.5 and Maven publish plugin 0.34.0.

## [1.1.2] - 2025-07-16

### Changed

- Migrated the Kotlin JVM target configuration to the `compilerOptions` DSL (still targeting JVM 17).
- Compose BOM updated to 2025.07.00 in the sample app.

## [1.1.1] - 2025-07-13

### Changed

- Version bump of the 1.0.7 content; no library code or dependency changes.

## [1.0.7] - 2025-07-13

### Changed

- Dependency and build tooling updates: AGP 8.11.1, Kotlin 2.2.0, Gradle 8.14.3, Compose BOM 2025.06.01 and
  Maven publish plugin 0.33.0 (publishing to Maven Central through the default Central Portal configuration).

## [1.0.6] - 2025-06-15

### Changed

- Gradle wrapper updated to 8.14.2; no library code changes.

## [1.0.5] - 2025-06-07

### Added

- Sample app demonstrating `RecyclerViewManager` usage, plus instrumented test scaffolding for the library.
- License headers and KDoc documentation across the source files.

### Changed

- Compose BOM updated to 2025.06.00.

### Fixed

- Corrected SonarQube report paths for unit test results and Android Lint output.

## [1.0.4] - 2025-06-03

### Changed

- Publishing workflow adjustments; no library code changes.

## [1.0.3] - 2025-06-03

### Added

- `RecyclerViewManager`, a utility that compares old and new lists of items (using caller-provided item identity
  and content comparators) and notifies a `RecyclerView.Adapter` of the resulting inserts, removals, updates and
  moves.
- Publication to Maven Central as `com.irurueta:irurueta-android-recycler-view-manager`, with Dokka-generated API
  documentation.

[Unreleased]: https://github.com/albertoirurueta/irurueta-android-recycler-view-manager/compare/1.1.5...HEAD
[1.1.5]: https://github.com/albertoirurueta/irurueta-android-recycler-view-manager/compare/1.1.3...1.1.5
[1.1.3]: https://github.com/albertoirurueta/irurueta-android-recycler-view-manager/compare/1.1.2...1.1.3
[1.1.2]: https://github.com/albertoirurueta/irurueta-android-recycler-view-manager/compare/1.1.1...1.1.2
[1.1.1]: https://github.com/albertoirurueta/irurueta-android-recycler-view-manager/compare/1.0.7...1.1.1
[1.0.7]: https://github.com/albertoirurueta/irurueta-android-recycler-view-manager/compare/1.0.6...1.0.7
[1.0.6]: https://github.com/albertoirurueta/irurueta-android-recycler-view-manager/compare/1.0.5...1.0.6
[1.0.5]: https://github.com/albertoirurueta/irurueta-android-recycler-view-manager/compare/1.0.4...1.0.5
[1.0.4]: https://github.com/albertoirurueta/irurueta-android-recycler-view-manager/compare/1.0.3...1.0.4
[1.0.3]: https://github.com/albertoirurueta/irurueta-android-recycler-view-manager/releases/tag/1.0.3
