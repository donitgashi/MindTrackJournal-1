# MindTrack Journal — Android (with CI + Robolectric)

- Multi-activity (List, Detail, Add/Edit)
- Room 2.8.0 + Flow + Coroutines
- Material 3 UI + Photo Picker
- **Robolectric** DAO test and **GitHub Actions CI**

## Open in Android Studio
- File → Open → `MindTrackJournal` (this folder)
- Let Gradle sync; Android Studio will fetch the wrapper JAR from the URL in
  `gradle/wrapper/gradle-wrapper.properties` (no manual steps).
- Run configuration: **app** (API 26+).

## CLI (optional)
- CI uses `gradle/gradle-build-action` with **Gradle 8.13** even without a local wrapper.