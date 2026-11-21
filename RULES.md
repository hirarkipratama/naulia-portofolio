# RULES.md — Naulia Zahra Portfolio
## Best Practices: Kotlin · KMP · Compose Multiplatform (Web)

> These rules are **mandatory** for every file, function, and component written in this project.  
> Every AI prompt execution must follow these rules. If a rule conflicts with a shortcut, the rule wins.

---

## ══ R1. KOTLIN LANGUAGE RULES ══

### R1.1 — Null Safety
- **Never use `!!` (non-null assertion)** unless inside a scope where nullability is already guaranteed by contract (e.g., inside a null-check block). Prefer `?.let { }`, `?: return`, or `requireNotNull()` with a descriptive message.
- Always prefer safe-call chains over nested null checks.
  ```kotlin
  // ❌ BAD
  val name = user!!.profile!!.name!!
  
  // ✅ GOOD
  val name = user?.profile?.name ?: "Anonymous"
  ```

### R1.2 — Immutability First
- All data classes must use `val` for every property. Never use `var` in a data class.
- Use `List<T>` (not `MutableList<T>`) in public APIs and function signatures.
- Use `copy()` to create modified instances; never mutate data objects in-place.
  ```kotlin
  // ❌ BAD
  data class AudioTrack(var title: String)
  
  // ✅ GOOD
  data class AudioTrack(val title: String)
  val updated = track.copy(title = "New Title")
  ```

### R1.3 — Sealed Classes & Exhaustive When
- Use `sealed class` / `sealed interface` for all UI state, navigation, and result types.
- Always use `when` as an expression (not a statement) on sealed types — the compiler enforces exhaustiveness.
  ```kotlin
  // ✅ GOOD — exhaustive, compile-enforced
  val label = when (state) {
      is SubmissionState.Idle    -> "Submit"
      is SubmissionState.Loading -> "Sending…"
      is SubmissionState.Success -> "Sent ✓"
      is SubmissionState.Error   -> "Retry"
  }
  ```

### R1.4 — Extension Functions
- Prefer extension functions over utility classes for domain-specific helpers.
- Never create a file named `Utils.kt` or `Helper.kt`. Name files after the domain concept they extend.
  ```kotlin
  // ✅ GOOD — AudioTrack+Extensions.kt
  fun AudioTrack.formattedDuration(): String {
      val m = durationSec / 60
      val s = durationSec % 60
      return "$m:${s.toString().padStart(2, '0')}"
  }
  ```

### R1.5 — Enums with Behavior
- Enums that carry display labels must expose them as properties, not via `when` lookups at the call site.
  ```kotlin
  // ✅ GOOD
  enum class AudioCategory(val labelBahasa: String, val labelEnglish: String) {
      ALL("Semua", "All"),
      STORY("Buku Cerita", "Story Books")
  }
  val label = category.labelBahasa  // No when() needed
  ```

### R1.6 — Coroutines
- **Never use `GlobalScope`**. All coroutines must be launched from a scoped `CoroutineScope` (ViewModel scope or `rememberCoroutineScope()` in Compose).
- Always use `SupervisorJob()` in ViewModel scopes so one failed child doesn't cancel siblings.
- Prefer `StateFlow` over `LiveData` (this is a KMP project — LiveData is Android-only).
- Always handle exceptions with `try/catch` or `CoroutineExceptionHandler` inside coroutines that call external APIs.
  ```kotlin
  // ✅ GOOD — ViewModel scope pattern
  private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
  
  fun loadData() {
      scope.launch {
          try {
              val result = repository.fetch()
              _state.update { it.copy(data = result, isLoading = false) }
          } catch (e: Exception) {
              _state.update { it.copy(errorMessage = e.message, isLoading = false) }
          }
      }
  }
  ```

### R1.7 — Flow & StateFlow
- Always expose `StateFlow` (never `MutableStateFlow`) from ViewModels to UI.
- Use `.update { }` on `MutableStateFlow` instead of direct assignment to avoid race conditions.
- Use `.stateIn()` to convert cold flows to hot StateFlows with a defined start policy.
  ```kotlin
  // ❌ BAD
  _state.value = _state.value.copy(isLoading = true)
  
  // ✅ GOOD
  _state.update { it.copy(isLoading = true) }
  ```

### R1.8 — Naming Conventions
| Concept | Convention | Example |
|---|---|---|
| Files | PascalCase | `AudioViewModel.kt` |
| Classes | PascalCase | `AudioUiState` |
| Sealed subclasses | PascalCase | `SubmissionState.Loading` |
| Functions | camelCase | `playTrack()` |
| Constants | SCREAMING_SNAKE | `MAX_TRACKS` |
| Private fields | `_` prefix + camelCase | `_uiState` |
| Public expose | no prefix | `uiState` |
| Composables | PascalCase | `AudioTrackCard()` |
| Lambda params | descriptive camelCase | `onTrackClick: (AudioTrack) -> Unit` |

---

## ══ R2. KOTLIN MULTIPLATFORM (KMP) RULES ══

### R2.1 — commonMain is the Source of Truth
- All **business logic, state management, data models, and repository interfaces** must live in `commonMain`.
- `wasmJsMain` contains ONLY platform-specific implementations (JS interop, browser APIs).
- Never import `kotlinx.browser.*` or `org.w3c.dom.*` in `commonMain` files.

### R2.2 — expect/actual Usage
- Use `expect`/`actual` only when behavior genuinely differs per platform (audio API, scroll, URL opening).
- Provide meaningful default or stub behavior in `expect` declarations where possible.
- Always pair `expect` declarations with `actual` implementations for every configured target.
  ```kotlin
  // commonMain — expect
  expect fun openExternalUrl(url: String)
  
  // wasmJsMain — actual
  actual fun openExternalUrl(url: String) {
      kotlinx.browser.window.open(url, "_blank")
  }
  ```

### R2.3 — No Android-Only Dependencies in commonMain
- **Forbidden in commonMain**: `androidx.lifecycle.ViewModel`, `android.content.*`, `LiveData`, `Context`, `Activity`.
- Use plain Kotlin classes with `CoroutineScope` as ViewModel replacements in `commonMain`.
- Use `kotlinx.serialization` (not `Gson` or `Jackson`) for JSON — it is KMP-native.

### R2.4 — Source Set Discipline
```
commonMain  — models, interfaces, ViewModels, use-cases, shared logic
wasmJsMain  — actual implementations, JS interop, HTML DOM, browser APIs
```
Never create a `jvmMain`, `androidMain`, or `iosMain` source set in this project — web only.

### R2.5 — Dependency Injection (Manual DI)
- Use a simple `AppContainer` object for DI — no Koin, Hilt, or Dagger.
- All ViewModel instantiation goes through `AppContainer`.
- In Compose, obtain ViewModels via `remember { AppContainer.audioViewModel }` — not via `viewModel()` (Android-only).

### R2.6 — Serialization
- All models that cross a data boundary (network, storage) must be annotated `@Serializable`.
- Use `kotlinx.serialization.json.Json { ignoreUnknownKeys = true }` as the JSON decoder to future-proof against API additions.
- Never use `Any` or `Map<String, Any>` as a model type — always use typed data classes.

---

## ══ R3. COMPOSE MULTIPLATFORM RULES ══

### R3.1 — Composable Function Rules
- Every `@Composable` must be **pure** — no side effects in the composition body.
- Side effects go inside `LaunchedEffect`, `SideEffect`, or `DisposableEffect`.
- Never call `suspend` functions directly inside a composable body.
  ```kotlin
  // ❌ BAD
  @Composable
  fun MyScreen() {
      val data = runBlocking { repository.fetchData() } // NEVER
  }
  
  // ✅ GOOD
  @Composable
  fun MyScreen(viewModel: CmsViewModel) {
      val state by viewModel.contentState.collectAsState()
      LaunchedEffect(Unit) { viewModel.refresh() }
  }
  ```

### R3.2 — State Hoisting
- **Always hoist state** up to the lowest common ancestor that needs it.
- Composables that only display data must accept state as parameters, not create it.
- Internal ephemeral UI state (hover, expanded, focused) may live inside the composable with `remember`.
  ```kotlin
  // ✅ GOOD — state hoisted, composable is stateless
  @Composable
  fun AudioTrackCard(
      track: AudioTrack,
      isActive: Boolean,
      isPlaying: Boolean,
      onTogglePlay: () -> Unit,
      onSeek: (Double) -> Unit,
      modifier: Modifier = Modifier
  )
  ```

### R3.3 — Modifier as Last Parameter
- Every public `@Composable` must have `modifier: Modifier = Modifier` as its **last parameter** (before trailing lambda).
- Never hardcode sizes/padding inside a composable that could be overridden by its caller.
  ```kotlin
  // ✅ GOOD
  @Composable
  fun SectionHeader(
      title: String,
      subtitle: String,
      modifier: Modifier = Modifier  // ← always last
  )
  ```

### R3.4 — Recomposition Safety
- Never put expensive computations (sorting, filtering, mapping) directly in a composable body.
- Use `remember(key)` or `derivedStateOf` to cache derived state.
  ```kotlin
  // ❌ BAD
  @Composable
  fun BookGrid(books: List<Book>, filter: AgeRange) {
      val filtered = books.filter { it.ageRange == filter }  // runs every recomposition
  }
  
  // ✅ GOOD
  val filtered = remember(books, filter) {
      books.filter { it.ageRange == filter }
  }
  ```

### R3.5 — Stability & Performance
- Mark classes passed to composables as `@Stable` or `@Immutable` when they are data classes with only `val` properties.
- Use `key()` in `LazyColumn`/`LazyRow` items to help Compose identify items correctly.
  ```kotlin
  items(tracks, key = { it.id }) { track ->
      AudioTrackCard(track = track, ...)
  }
  ```

### R3.6 — Animation Rules
- Always respect `prefers-reduced-motion`. Query it once and pass it down as a parameter.
- Use `animateContentSize()` for size transitions. Use `AnimatedVisibility` for show/hide.
- Use `infiniteTransition` only inside `AnimatedEqualizer` — do not create infinite animations on every screen refresh.
- Never use `delay()` inside a composable body for animation timing — use `animatable` or `infiniteTransition`.

### R3.7 — Theme Usage
- **Never hardcode colors or sizes.** Always use `MaterialTheme.colorScheme.*` or `LocalLuminaColors.current.*`.
- **Never hardcode font sizes.** Always use `MaterialTheme.typography.*`.
- **Never hardcode spacing.** Always use `LuminaDimens.*` (xs, sm, md, lg, xl, xxl).
  ```kotlin
  // ❌ BAD
  Text("Title", fontSize = 24.sp, color = Color(0xFFFF8E72))
  
  // ✅ GOOD
  Text("Title", style = MaterialTheme.typography.headlineMedium,
       color = MaterialTheme.colorScheme.primary)
  ```

### R3.8 — Component Extraction Rules
- Extract a composable into its own file when:
  1. It is used in more than one screen, OR
  2. Its internal code exceeds ~80 lines, OR
  3. It has distinct semantic meaning (e.g., `AudioTrackCard`, `BookCoverCard`)
- File name must match the composable name exactly.

### R3.9 — Accessibility (Mandatory)
- Every interactive composable must have `Modifier.semantics { }` with:
  - `contentDescription` for images and icon-only buttons
  - `role = Role.Button` for clickable non-button elements
  - `stateDescription` for toggle/selection states
- Form fields: rely on `OutlinedTextField`'s built-in `label` parameter — no extra semantics needed.
- Audio play/pause button: `contentDescription` must update dynamically:
  ```kotlin
  Modifier.semantics {
      contentDescription = if (isPlaying) "Pause ${track.title}" else "Play ${track.title}"
  }
  ```

### R3.10 — Composable Naming
| Type | Rule | Example |
|---|---|---|
| Screens | Noun + "Screen" | `AudioReelScreen` |
| Reusable UI | Noun | `AudioTrackCard`, `SectionHeader` |
| Containers/Layouts | Noun + "Layout" or "Section" | `HeroLayout`, `FooterSection` |
| Dialogs/Sheets | Noun + "Dialog" | `BookDetailDialog` |
| Previews | Same name + "Preview" | `AudioTrackCardPreview` |

---

## ══ R4. JS INTEROP RULES (wasmJsMain) ══

### R4.1 — Isolate All JS Interop
- All `kotlinx.browser`, `org.w3c.dom`, and `js("...")` calls must live exclusively in `wasmJsMain` source set.
- Expose a Kotlin interface in `commonMain` and implement it in `wasmJsMain` using `actual`.

### R4.2 — Defensive JS Calls
- Always guard JS interop calls for null safety:
  ```kotlin
  // ✅ GOOD
  actual fun openUrl(url: String) {
      kotlinx.browser.window.open(url, "_blank")
      // window is non-null in a browser context — safe
  }
  
  // For DOM queries always check null:
  val el = document.getElementById(sectionId) ?: return
  ```

### R4.3 — CSS Injection
- All global CSS must be injected once in `main.kt` via `GlobalStyleInjector.injectGlobalStyles()` before `ComposeViewport` is called.
- Never inject CSS inside composable functions — it causes duplicate injection on recomposition.

### R4.4 — Event Listeners Cleanup
- Any JS event listener attached in a `LaunchedEffect` or `DisposableEffect` must be removed in the `onDispose` block.
  ```kotlin
  DisposableEffect(Unit) {
      val handler: (Event) -> Unit = { ... }
      window.addEventListener("scroll", handler)
      onDispose { window.removeEventListener("scroll", handler) }
  }
  ```

---

## ══ R5. FILE & PROJECT ORGANIZATION RULES ══

### R5.1 — Package Structure
```
id.naulia.portfolio
├── App.kt
├── navigation/       — NavSection, Navigator, ScrollObserver
├── data/
│   ├── model/        — Pure data classes (no logic)
│   ├── repository/   — Interfaces + implementations
│   └── cms/          — Remote data sources (Phase 2)
├── ui/
│   ├── theme/        — LuminaTheme, Colors, Typography, Dimens
│   ├── components/   — Reusable composables
│   └── screens/      — One file per section screen
├── viewmodel/        — ViewModels + UiState classes
├── audio/            — AudioPlayerContract (expect/actual)
├── platform/         — PlatformUtils (expect/actual)
└── di/               — AppContainer
```

### R5.2 — One Class Per File
- Each Kotlin file contains exactly one top-level class, sealed class, or composable group.
- Exception: Small related enums and data classes with <15 lines may be co-located in one file (e.g., `AudioCategory.kt` with its companion).

### R5.3 — No Magic Strings
- All user-facing strings (labels, error messages, placeholders) must be defined in a companion object or top-level `object` — never inline in composables.
  ```kotlin
  // ✅ GOOD
  object Strings {
      const val FORM_NAME_ERROR = "Nama tidak boleh kosong"
      const val FORM_CONTACT_ERROR = "Masukkan email atau nomor WhatsApp yang valid"
  }
  ```

### R5.4 — Assets Reference Convention
- Audio assets: `"audio/track_XX.mp3"` — relative path from web root
- Book covers: `"images/covers/book_XX.webp"` — relative path
- Event photos: `"images/events/event_XX.webp"`
- Portrait: `"images/portrait/portrait_hero.webp"`
- Never hardcode absolute paths. All asset URLs must be relative for portability.

---

## ══ R6. TESTING & QUALITY RULES ══

### R6.1 — ViewModel Testability
- ViewModels must accept all dependencies (repository, http client, audio player) as constructor parameters — never instantiate them internally.
- This enables unit testing by injecting fakes/mocks.

### R6.2 — Pure Functions First
- Domain logic (validation, filtering, formatting) must live in pure functions or extension functions in `commonMain`, not in composables or ViewModels directly.
- Pure functions are testable without Compose or coroutine infrastructure.

### R6.3 — Lint & Analysis
- Zero compiler warnings allowed in `commonMain`. Suppress with `@Suppress` only when accompanied by a code comment explaining why.
- All `TODO()` markers must include a phase tag: `TODO("Phase 2 — Remote CMS")`.

---

## ══ R7. PERFORMANCE RULES ══

### R7.1 — WASM Bundle Size
- Do not add dependencies that are not strictly necessary. Every new dependency increases WASM bundle size.
- Prefer Kotlin stdlib solutions over third-party libraries for string manipulation, collections, and math.

### R7.2 — Image Assets
- All images must be in **WebP** format, max 200KB for covers, max 400KB for event photos.
- Portrait image: max 150KB, 640×760px.
- Never embed base64 images in code.

### R7.3 — Audio Assets
- Max 2MB per audio demo track (target ~128kbps MP3).
- Lazy-load audio: do not preload `<audio>` elements until the user interacts with the Audio section.

### R7.4 — Lazy Rendering
- Use `LazyColumn` / `LazyRow` for any list with more than 5 items.
- Use `animateContentSize()` instead of animating height directly (more performant in Compose).

---

## ══ R8. GIT & COMMIT RULES ══

### R8.1 — Conventional Commits
```
feat(audio): add equalizer animation to AudioTrackCard
fix(contact): validate WhatsApp number format with +62 prefix
style(theme): align coral token to #FF8E72 across all components
refactor(nav): extract ScrollObserver to dedicated wasmJsMain file
docs(prompts): add Phase 6 accessibility prompt
chore(deps): bump Compose Multiplatform to 1.7.3
```

### R8.2 — Branch Strategy
- `main` — production-ready, deploys automatically
- `feature/<phase>-<description>` — e.g., `feature/phase2-audio-viewmodel`
- Never commit directly to `main` from a feature implementation

### R8.3 — Never Commit
- `build/` directory
- `.gradle/` directory
- Asset placeholders (use README stubs in `assets/` subdirectories)
- API keys or Formspree endpoint IDs (use environment variables or a `.env` file gitignored)
