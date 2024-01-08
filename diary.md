
### Initial Changes
- Upgraded project to Gradle8
- Disabled Jetifier for the project as the check found 0 declared dependencies that require Jetifier in the project.

### Task 3
- Added Dependency Injection support using Hilt
- Created a repository to handle the stopwatch timer so that it can run independent of the MainActivity, but still can be controlled by MainActivity.