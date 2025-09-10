# Kotlin Multiplatform Quiz Application

This is a Kotlin Multiplatform project featuring a Quiz application with Compose Multiplatform UI and a Ktor-based backend server. The project targets Android, Desktop (JVM), iOS, and WebAssembly (WASM) platforms.

Always reference these instructions first and fallback to search or bash commands only when you encounter unexpected information that does not match the info here.

## Working Effectively

### Prerequisites
- Java 17 is required (OpenJDK Temurin 17.0.16+8 is installed and working)
- Android SDK is available at `/usr/local/lib/android/sdk` with platforms android-33 through android-36
- Gradle 8.11 is automatically downloaded and used
- Network connectivity required for dependency resolution (Google Maven repository access needed)

### Build Commands and Timing

**CRITICAL: NEVER CANCEL builds or long-running commands. Set timeouts of 60+ minutes for safety.**

#### Working Commands (Validated)
- **Server build**: `./gradlew :server:build` - **27 seconds** - NEVER CANCEL, timeout 60+ minutes
- **Server tests**: `./gradlew :server:test` - **1 second** - Tests pass successfully
- **List all tasks**: `./gradlew tasks` - **2 seconds** - Shows all available build targets
- **Server tasks**: `./gradlew :server:tasks` - **20 seconds** - Lists server-specific tasks

#### Network-Dependent Commands (May Fail in Restricted Environments)
- **Full project build**: `./gradlew build` - **FAILS due to dl.google.com connectivity issues**
- **Android builds**: Any Android-related tasks fail due to Android Gradle Plugin download issues
- **Desktop build**: `./gradlew :composeApp:desktopJar` - **FAILS due to androidx dependency resolution**
- **WASM build**: `./gradlew :composeApp:wasmJsBrowserDistribution` - **FAILS due to dependency resolution**

### Known Issues and Workarounds

#### Network Connectivity Problems
- **Android Gradle Plugin**: Cannot resolve `com.android.application.gradle.plugin:8.7.3` from Google repositories
- **AndroidX Dependencies**: Cannot resolve dependencies from `dl.google.com` (DNS resolution fails)
- **Workaround**: Use `--offline` mode after initial dependency download, or work in environments with full internet access

#### iOS Targets
- iOS targets (iosArm64, iosSimulatorArm64, iosX64) are disabled on Linux build machines
- Add `kotlin.native.ignoreDisabledTargets=true` to `gradle.properties` to suppress warnings

### Running the Application

#### Server
- **Start server**: `PORT=8080 ./gradlew :server:run` - Runs on port 8080 (default 9091 may conflict)
- **Build fat JAR**: `./gradlew :server:buildFatJar` - Creates standalone JAR
- **Configuration**: Edit `server/src/main/resources/application.yaml` for port and settings

#### Client Applications
- **Desktop**: Build currently fails due to network issues - requires full internet connectivity
- **Android**: Build currently fails due to network issues - requires full internet connectivity  
- **WASM**: Build currently fails due to network issues - requires full internet connectivity
- **iOS**: Build disabled on Linux (use macOS for iOS development)

### Validation and Testing

#### Manual Validation Steps
After making code changes:
1. **Always test server build**: `./gradlew :server:build` (27 seconds)
2. **Always run server tests**: `./gradlew :server:test` (1 second)
3. **Test server startup**: `PORT=8080 ./gradlew :server:run` (should start without errors)
4. **Verify API endpoint**: `curl http://localhost:8080/quiz` (should return quiz JSON)

#### Before Committing
- **Server tests must pass**: `./gradlew :server:test`
- **Server must build**: `./gradlew :server:build`
- **No new compilation errors**: Check Kotlin compilation warnings

### Code Structure

#### Key Directories
- `composeApp/src/commonMain/kotlin/` - Shared UI code (Compose Multiplatform)
- `composeApp/src/desktopMain/kotlin/` - Desktop-specific code
- `composeApp/src/androidMain/kotlin/` - Android-specific code (when accessible)
- `composeApp/src/iosMain/kotlin/` - iOS-specific code  
- `composeApp/src/wasmJsMain/kotlin/` - WASM-specific code
- `server/src/main/kotlin/com/worldline/quiz/` - Ktor server code
- `server/src/main/resources/` - Server configuration and static resources

#### Important Files
- `gradle/libs.versions.toml` - Dependency versions (Kotlin 2.1.20, Compose 1.8.0-beta02)
- `settings.gradle.kts` - Project structure (composeApp + server modules)
- `gradle.properties` - Build configuration (Java 17, memory settings)

### Application Architecture

The Quiz app consists of:
- **UI Layer**: Compose Multiplatform with Navigation (WelcomeScreen → QuestionScreen → ScoreScreen)
- **ViewModel**: `QuizViewModel` handles state management
- **Repository**: `QuizRepository` manages data sources
- **Data Sources**: Mock, API (Ktor client), and KStore for persistence
- **Server**: Ktor-based API serving quiz questions at `/quiz` endpoint

### Troubleshooting Build Issues

#### If Android builds fail:
1. Check network connectivity to `dl.google.com`
2. Verify Android SDK installation: `ls $ANDROID_HOME/platforms`
3. Try `./gradlew --refresh-dependencies` if network is available
4. Consider working offline after initial dependency download

#### If server fails to start:
1. Check port availability: `PORT=8080 ./gradlew :server:run`
2. Verify configuration in `server/src/main/resources/application.yaml`
3. Check server logs for specific error messages

#### Memory Issues:
- Gradle is configured with 2GB heap: `org.gradle.jvmargs=-Xmx2048M`
- Kotlin daemon uses 2GB: `kotlin.daemon.jvmargs=-Xmx2048M`
- Increase if builds fail with OutOfMemoryError

### Development Workflow

1. **Start with server development**: Always verify server builds and tests pass
2. **Test API endpoints**: Use curl or HTTP client to test server functionality  
3. **Client development**: Requires full network connectivity for dependency resolution
4. **Use validated commands**: Stick to known working Gradle tasks documented above
5. **Time all operations**: Server build takes 27s, tests take 1s - expect these timings

### CI/CD Integration

The project includes GitHub Actions workflow (`.github/workflows/publish-to-gitlab-pages.yml`):
- Builds WASM target: `./gradlew wasmJsBrowserDistribution`
- Publishes to GitLab Pages
- **Requires network connectivity** to succeed

### Common Development Tasks

- **Add new quiz questions**: Edit `server/src/main/kotlin/com/worldline/quiz/plugins/Routing.kt`
- **Modify UI screens**: Edit files in `composeApp/src/commonMain/kotlin/screens/`
- **Update dependencies**: Edit `gradle/libs.versions.toml`
- **Server configuration**: Edit `server/src/main/resources/application.yaml`

### Time Estimates

- **Server build**: 27 seconds - NEVER CANCEL
- **Server tests**: 1 second  
- **Task listing**: 2 seconds
- **Dependency resolution**: 5-15 seconds (network dependent)
- **Full project setup**: 2-5 minutes (network dependent)

**CRITICAL**: Always allow adequate time for builds. Use timeouts of 60+ minutes for safety. Build failures are usually due to network connectivity, not build system issues.