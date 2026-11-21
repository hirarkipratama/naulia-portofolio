# PROMPTS.md — Naulia Zahra Portfolio: Step-by-Step KMP + Compose for Web Build Prompts

> **Stack:** Kotlin Multiplatform · Compose Multiplatform (wasmJs) · Web-only  
> **Execute these prompts sequentially in your AI coding assistant.**  
> Each prompt is self-contained. Paste it, review the output, then proceed to the next.

---

## ⚠️ MANDATORY CONTEXT — Read Before Every Prompt

Before executing any prompt in this file, you **must** read and internalize the four reference documents below. Every code decision, naming convention, pattern, and architectural choice must comply with them.

| Document | Path | What it governs |
|---|---|---|
| **PRD.md** | [`./PRD.md`](./PRD.md) | Product requirements, sections, design system, personas, UX behaviors |
| **PLAN.md** | [`./PLAN.md`](./PLAN.md) | Architecture, project structure, data models, tech stack decisions, phases |
| **RULES.md** | [`./RULES.md`](./RULES.md) | Mandatory Kotlin, KMP, Compose best practices — zero exceptions |
| **SKILL.md** | [`./SKILL.md`](./SKILL.md) | Execution patterns, component recipes, anti-patterns, build commands |

> [!IMPORTANT]
> If any generated code violates a rule in **RULES.md**, reject it and ask for a correction before proceeding. Rules are not optional.

### Pre-Execution Checklist (run before each prompt)
- [ ] I have read the PRD.md sections relevant to this phase
- [ ] I have reviewed the PLAN.md architecture for this phase
- [ ] I have refreshed RULES.md sections R1–R8
- [ ] I have reviewed SKILL.md sections 4 (patterns) and 11 (anti-patterns)
- [ ] After completion: `./gradlew :composeApp:compileProductionExecutableKotlinWasmJs` passes with zero errors

---

## ═══ PHASE 1 — PROJECT SCAFFOLD & THEME ═══

> **Phase 1 Reference Docs:**
> - PRD.md §6 (Technical Stack) · §5 (Design System)
> - PLAN.md §1 (Project Structure) · §10 (Dependencies)
> - RULES.md R2 (KMP Rules) · R3.7 (Theme Usage) · R5.1 (Package Structure)
> - SKILL.md §2 (Environment Setup) · §3 (Source Set Map) · §10 (Build Commands)

---

### PROMPT 1.1 — KMP Project Initialization

> **Rules applied:** R2.1 (commonMain is truth), R2.4 (source set discipline), R5.1 (package structure)  
> **PRD ref:** §6 Technical Stack · §5 Design System name "Curated Lumina"  
> **PLAN ref:** §1 Project Structure, §10 Dependencies (libs.versions.toml)

```
You are setting up a Kotlin Multiplatform (KMP) project targeting Web ONLY via the wasmJs target using Compose Multiplatform.

MANDATORY RULES (from RULES.md):
- R2.4: wasmJs target ONLY — do NOT add android, jvm, or ios targets
- R2.1: All shared logic in commonMain; JS interop in wasmJsMain only
- R5.1: Root package is id.naulia.portfolio

Project details:
- Project name: naulia-personal-portofolio
- Root package: id.naulia.portfolio
- Group ID: id.naulia
- Compose Multiplatform version: 1.7.3
- Kotlin version: 2.1.0

Tasks:
1. Create `settings.gradle.kts`:
   - Set rootProject.name = "naulia-personal-portofolio"
   - Include module ":composeApp"
   - Add plugin management with Compose Multiplatform, Kotlin Multiplatform, and Kotlin Compose Compiler plugin repos

2. Create `gradle/libs.versions.toml` with these exact dependencies:
   - kotlin = "2.1.0"
   - compose-multiplatform = "1.7.3"
   - ktor = "3.0.3"
   - kotlinx-coroutines = "1.9.0"
   - kotlinx-serialization = "1.7.3"
   - Libraries: compose-runtime, compose-foundation, compose-material3, ktor-client-core, ktor-client-js, ktor-serialization-json, kotlinx-coroutines-core, kotlinx-serialization-json
   - Plugins: kotlin-multiplatform, kotlin-serialization, compose-multiplatform, compose-compiler

3. Create root `build.gradle.kts` applying only the version catalog.

4. Create `composeApp/build.gradle.kts`:
   - Apply plugins: kotlin.multiplatform, compose.multiplatform, compose.compiler, kotlin.serialization
   - Configure kotlin.multiplatform { wasmJs { browser { ... }; binaries.executable() } }
   - Add sourceSets: commonMain (compose.runtime, compose.foundation, compose.material3, coroutines, serialization), wasmJsMain (ktor-client-js, ktor-serialization-json)
   - Do NOT add jvm, android, or iOS targets — web only.

5. Create `composeApp/src/wasmJsMain/kotlin/id/naulia/portfolio/main.kt`:
   - Import androidx.compose.ui.ExperimentalComposeUiApi and androidx.compose.ui.window.ComposeViewport
   - Entry: fun main() { ComposeViewport(kotlinx.browser.document.body!!) { App() } }

6. Create `composeApp/src/commonMain/kotlin/id/naulia/portfolio/App.kt`:
   - Simple placeholder: @Composable fun App() { Text("Naulia Zahra Portfolio — Loading…") }

Do NOT add any other targets. Output all files with full content.
```

---

### PROMPT 1.2 — Lumina Design System Theme

> **Rules applied:** R3.7 (theme usage, no hardcoded colors/sizes), R5.1 (package: ui.theme), R1.8 (naming)  
> **PRD ref:** §5 Design System "Curated Lumina" — palette tokens, Outfit typography, `#ff8e72` coral accent  
> **PLAN ref:** §1 Project Structure (ui`/`theme/), SKILL.md §7 (Lumina Color Token Reference)

```
Implement the "Curated Lumina" design system for a Compose Multiplatform (wasmJs) project.
Root package: id.naulia.portfolio.ui.theme

Create these files in `composeApp/src/commonMain/kotlin/id/naulia/portfolio/ui/theme/`:

1. **LuminaColors.kt**
   Define a data class LuminaColorScheme and a val LuminaLightColors with:
   - background: Color = Color(0xFFFFF8F3)       // Warm Cream
   - surface: Color = Color(0xFFF9F2ED)           // Soft Sand
   - surfaceVariant: Color = Color(0xFFFFFFFF)    // White
   - primary: Color = Color(0xFFFF8E72)           // Coral (main accent)
   - primaryVariant: Color = Color(0xFFF59E0B)    // Sunny Yellow CTA
   - secondary: Color = Color(0xFF6EAB9E)         // Sage Mint (STEM)
   - tertiary: Color = Color(0xFFA78BCA)          // Soft Lavender
   - onBackground: Color = Color(0xFF2D1F14)      // Deep warm brown text
   - onSurface: Color = Color(0xFF4A3728)         // Medium brown text
   - onPrimary: Color = Color(0xFFFFFFFF)
   - outline: Color = Color(0xFFE8DDD6)           // Warm border

2. **LuminaTypography.kt**
   - Define TextStyle scales using the Outfit font family (declare FontFamily with generic sans-serif fallback since custom fonts load via CSS injection)
   - Scales: displayLarge (40sp bold), headlineLarge (32sp semibold), headlineMedium (24sp semibold), titleLarge (20sp medium), titleMedium (16sp medium), bodyLarge (16sp normal), bodyMedium (14sp normal), labelLarge (14sp semibold), labelSmall (12sp medium)

3. **LuminaTheme.kt**
   - @Composable fun LuminaTheme(content: @Composable () -> Unit)
   - Create a MaterialTheme using Material3 ColorScheme mapped from LuminaColorScheme
   - Inject Outfit Google Font via LaunchedEffect + kotlinx.browser.document creating a <link> tag: href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap"
   - Provide LuminaColorScheme via CompositionLocal (LocalLuminaColors)
   - Wrap content in MaterialTheme

4. **LuminaDimens.kt**
   - Object with spacing: xs=4.dp, sm=8.dp, md=16.dp, lg=24.dp, xl=40.dp, xxl=64.dp
   - cornerRadius: card=16.dp, pill=50.dp, button=12.dp
   - maxContentWidth = 1280.dp
```

---

### PROMPT 1.3 — Navigation Model & Sealed Class

> **Rules applied:** R1.3 (sealed classes + exhaustive when), R2.1 (commonMain), R4.1 (JS interop isolated to wasmJsMain)  
> **PRD ref:** §3 Core Information Architecture (all 6 section IDs) · §4 Smooth Anchor Scroll, IntersectionObserver  
> **PLAN ref:** §5 Navigation Model, §1 navigation/ package

```
Create the navigation model for a Compose Multiplatform (wasmJs) single-page portfolio app.
Package: id.naulia.portfolio.navigation

Files to create in `composeApp/src/commonMain/kotlin/id/naulia/portfolio/navigation/`:

1. **NavSection.kt**
   ```kotlin
   sealed class NavSection(
       val sectionId: String,         // HTML element id for JS scroll targeting
       val labelBahasa: String,       // Bahasa Indonesia label
       val labelEnglish: String,      // English label
       val icon: String               // Unicode emoji icon for mobile tab bar
   ) {
       object Home    : NavSection("home",               "Beranda",    "Home",    "🏠")
       object Audio   : NavSection("featured-audiobook", "Buku Suara", "Audio",   "🎙️")
       object Books   : NavSection("editorial-works",    "Buku",       "Books",   "📚")
       object Stage   : NavSection("mc-stage",           "Acara",      "Events",  "🎤")
       object About   : NavSection("about",              "Profil",     "Profile", "👤")
       object Contact : NavSection("contact",            "Kontak",     "Contact", "✉️")

       companion object {
           val all: List<NavSection> = listOf(Home, Audio, Books, Stage, About, Contact)
       }
   }
   ```

2. **Navigator.kt**
   - class Navigator with:
     - private val _activeSection = MutableStateFlow<NavSection>(NavSection.Home)
     - val activeSection: StateFlow<NavSection> = _activeSection.asStateFlow()
     - fun navigateTo(section: NavSection) — updates _activeSection and triggers JS smooth scroll
     - fun updateActiveSectionFromScroll(sectionId: String) — called by JS IntersectionObserver

3. **ScrollObserver.kt** in `wasmJsMain` only:
   - Use `kotlinx.browser.document` and JS interop to set up `IntersectionObserver`
   - Observe all elements with ids from NavSection.all.map { it.sectionId }
   - On intersection, call navigator.updateActiveSectionFromScroll(sectionId)
   - Also implement smoothScrollToSection(sectionId: String) using window.document.getElementById(id)?.scrollIntoView(js("({behavior: 'smooth', block: 'start'})"))

Use `kotlinx.coroutines.flow.MutableStateFlow` and `StateFlow`.
```

---

## ═══ PHASE 2 — DATA LAYER & CONTENT MODELS ═══

> **Phase 2 Reference Docs:**
> - PRD.md §3 (Section Hierarchy — all data entities defined here) · §1 (Key Deliverables)
> - PLAN.md §3 (Data Models) · §6 (CMS Strategy) · §1 data/ package structure
> - RULES.md R1.2 (Immutability), R1.5 (Enums), R2.3 (no Android deps), R2.6 (Serialization)
> - SKILL.md §3 (Source Set Map) · §11 (Anti-Patterns: no `var` in data class)

---

### PROMPT 2.1 — Domain Models

> **Rules applied:** R1.2 (val only in data classes), R2.6 (@Serializable), R1.5 (enums with display properties), R5.2 (one class per file)  
> **PRD ref:** §3 Section Hierarchy (AudioTrack → #featured-audiobook, Book → #editorial-works, McEvent → #mc-stage, ContactForm → #contact)  
> **PLAN ref:** §3 Data Models (complete type definitions with fields)

```
Create all Kotlin data models for the Naulia Zahra portfolio app.
Package: id.naulia.portfolio.data.model
Location: `composeApp/src/commonMain/kotlin/id/naulia/portfolio/data/model/`

Create these files:

1. **AudioCategory.kt**
   ```kotlin
   enum class AudioCategory(val labelBahasa: String, val labelEnglish: String) {
       ALL("Semua", "All"),
       STORY("Buku Cerita", "Story Books"),
       SCIENCE("Sains", "Science"),
       CHARACTER_VO("VO Karakter", "Character VO")
   }
   ```

2. **AudioTrack.kt**
   ```kotlin
   @Serializable
   data class AudioTrack(
       val id: String,
       val title: String,
       val category: AudioCategory,
       val durationSec: Int,
       val assetUrl: String,
       val tags: List<String>,
       val description: String = ""
   )
   ```

3. **Publisher.kt** — enum: GRAMEDIA, BIP, MIZAN, ERLANGGA, OTHER (each with displayName String)

4. **AgeRange.kt** — enum: AGE_3_6("Usia 3–6"), AGE_4_8("Usia 4–8"), AGE_7_12("Usia 7–12"), ALL_AGES

5. **EditorRole.kt** — enum: DEVELOPMENTAL_EDITOR, STEM_FACT_CHECKER, LANGUAGE_CONSULTANT, SERIES_EDITOR (each with displayBadge String)

6. **Book.kt**
   ```kotlin
   @Serializable
   data class Book(
       val id: String,
       val title: String,
       val publisher: Publisher,
       val ageRange: AgeRange,
       val roles: List<EditorRole>,
       val coverAsset: String,
       val year: Int,
       val synopsis: String = ""
   )
   ```

7. **McEvent.kt**
   ```kotlin
   @Serializable
   data class McEvent(
       val id: String,
       val eventName: String,
       val date: String,
       val venue: String,
       val city: String,
       val estimatedAttendees: Int,
       val photoAsset: String,
       val tags: List<String>
   )
   ```

8. **ServiceType.kt** — enum: BOOK_EDITOR("Editor Buku"), MC_EVENT("MC Acara"), CHARACTER_VO("VO Karakter"), FULL_COLLABORATION("Kolaborasi Lengkap")

9. **ContactForm.kt**
   ```kotlin
   data class ContactForm(
       val name: String = "",
       val contact: String = "",        // Email or WhatsApp number
       val services: Set<ServiceType> = emptySet(),
       val scope: String = "",
       val message: String = ""
   )
   ```

10. **PortfolioMetrics.kt**
    ```kotlin
    data class PortfolioMetrics(
        val totalEvents: Int,          // 50+
        val officialPartners: String,  // "Mitra Resmi Gramedia"
        val audiobooksProduced: Int,   // 10+
        val clientRetentionPct: Int,   // 98
        val titlesEdited: Int          // 15+
    )
    ```

Add `@Serializable` to all data classes. Use `kotlinx.serialization`.
```

---

### PROMPT 2.2 — Content Repository & Seed Data

> **Rules applied:** R2.1 (interface in commonMain), R1.2 (immutable lists), R5.4 (relative asset paths), R6.3 (TODO tags with phase)  
> **PRD ref:** §3 all section data — publisher names (Gramedia, BIP, Mizan, Erlangga), metrics (50+ events, 98% retention, 15+ titles), audio categories  
> **PLAN ref:** §6 CMS Strategy (Phase 1 = LocalContentRepository, Phase 2 stub), §1 data/repository/ and data/cms/ packages

```
Create the data repository layer for the Naulia Zahra portfolio.
Package: id.naulia.portfolio.data.repository
Location: `composeApp/src/commonMain/kotlin/id/naulia/portfolio/data/`

1. **repository/ContentRepository.kt** — interface:
   ```kotlin
   interface ContentRepository {
       fun getAudioTracks(): List<AudioTrack>
       fun getBooks(): List<Book>
       fun getMcEvents(): List<McEvent>
       fun getMetrics(): PortfolioMetrics
       fun getTracksByCategory(category: AudioCategory): List<AudioTrack>
   }
   ```

2. **repository/LocalContentRepository.kt** — implements ContentRepository with hardcoded seed data:

   Audio Tracks (minimum 6):
   - id="track_01", title="Kisah Si Kancil dan Buaya", category=STORY, durationSec=145, assetUrl="audio/track_01.mp3", tags=["Cerita Rakyat", "Karakter Ekspresif"]
   - id="track_02", title="Mengapa Langit Biru? — Buku Sains Anak", category=SCIENCE, durationSec=187, assetUrl="audio/track_02.mp3", tags=["Sains", "Edukatif"]
   - id="track_03", title="Demo Karakter: Nenek Sihir & Peri", category=CHARACTER_VO, durationSec=93, assetUrl="audio/track_03.mp3", tags=["Karakter", "Dramatik"]
   - id="track_04", title="Buku Cerita: Petualangan Dino Kecil", category=STORY, durationSec=210, assetUrl="audio/track_04.mp3", tags=["Adventure", "Anak-anak"]
   - id="track_05", title="Seri Sains: Sistem Tata Surya", category=SCIENCE, durationSec=168, assetUrl="audio/track_05.mp3", tags=["Astronomi", "STEM"]
   - id="track_06", title="Narasi Dokumenter: Hutan Hujan Indonesia", category=CHARACTER_VO, durationSec=124, assetUrl="audio/track_06.mp3", tags=["Narasi", "Alam"]

   Books (minimum 6):
   - id="book_01", title="Dino Kecil Belajar Berbagi", publisher=GRAMEDIA, ageRange=AGE_4_8, roles=[DEVELOPMENTAL_EDITOR], coverAsset="images/covers/book_01.webp", year=2023
   - id="book_02", title="Seri STEM: Kenapa Air Mendidih?", publisher=BIP, ageRange=AGE_7_12, roles=[STEM_FACT_CHECKER, DEVELOPMENTAL_EDITOR], coverAsset="images/covers/book_02.webp", year=2023
   - id="book_03", title="Peri Hutan dan Teman-Teman", publisher=MIZAN, ageRange=AGE_3_6, roles=[DEVELOPMENTAL_EDITOR], coverAsset="images/covers/book_03.webp", year=2022
   - id="book_04", title="Atlas Anak: Hewan Indonesia", publisher=ERLANGGA, ageRange=AGE_7_12, roles=[STEM_FACT_CHECKER, LANGUAGE_CONSULTANT], coverAsset="images/covers/book_04.webp", year=2024
   - id="book_05", title="Kisah Pahlawan Cilik", publisher=GRAMEDIA, ageRange=AGE_4_8, roles=[SERIES_EDITOR], coverAsset="images/covers/book_05.webp", year=2024
   - id="book_06", title="Sains Ajaib: Tubuh Kita", publisher=BIP, ageRange=AGE_7_12, roles=[STEM_FACT_CHECKER], coverAsset="images/covers/book_06.webp", year=2023

   MC Events (minimum 5):
   - Various Gramedia events, book fests, science expos in Jakarta 2022–2024

   Metrics: totalEvents=50, officialPartners="Mitra Resmi Gramedia", audiobooksProduced=10, clientRetentionPct=98, titlesEdited=15

3. **cms/CmsDataSource.kt** — stub placeholder for Phase 2:
   ```kotlin
   // Phase 2: Remote CMS integration via ktor-client-js
   // Will fetch JSON from a configured backend URL
   class CmsDataSource(private val httpClient: HttpClient) {
       suspend fun fetchTracks(): List<AudioTrack> = TODO("Phase 2 - Remote CMS")
       suspend fun fetchBooks(): List<Book> = TODO("Phase 2 - Remote CMS")
       suspend fun fetchEvents(): List<McEvent> = TODO("Phase 2 - Remote CMS")
   }
   ```
```

---

## ═══ PHASE 3 — VIEWMODELS & STATE ═══

> **Phase 3 Reference Docs:**
> - PRD.md §4 (Audio Reel Micro-Interactions: single-active-player, scrubber, category filter) · §6 (Form requirements)
> - PLAN.md §4 (State Management table) · §7 (Audio Playback Architecture) · §8 (Form Submission flow)
> - RULES.md R1.6 (Coroutines: SupervisorJob, no GlobalScope), R1.7 (StateFlow: .update{}), R2.3 (no Android ViewModel), R6.1 (Testability via constructor DI)
> - SKILL.md §4.1 (ViewModel Pattern), §4.4 (StateFlow Update Pattern), §11 (Anti-Patterns)

---

### PROMPT 3.1 — AudioViewModel

> **Rules applied:** R1.6 (SupervisorJob CoroutineScope), R1.7 (StateFlow .update{}), R2.3 (no Android dep), R6.1 (DI via constructor)  
> **PRD ref:** §4 Audio Reel Micro-Interactions — "Single-active-player state", "Live animated visualizer", "Interactive scrubber progress bar"  
> **PLAN ref:** §4 State Management (AudioUiState fields), §7 Audio Playback Architecture, SKILL.md §4.3 (expect/actual AudioPlayerContract)

```
Create AudioViewModel for the Naulia Zahra portfolio (Compose Multiplatform, commonMain).
Package: id.naulia.portfolio.viewmodel

File: `composeApp/src/commonMain/kotlin/id/naulia/portfolio/viewmodel/AudioViewModel.kt`

Requirements:
- Pure Kotlin class (NO Android ViewModel dependency)
- Constructor: AudioViewModel(private val repository: ContentRepository, private val player: AudioPlayerContract)
- Inner data class: AudioUiState(
    val tracks: List<AudioTrack>,
    val filteredTracks: List<AudioTrack>,
    val activeTrackId: String?,
    val isPlaying: Boolean,
    val progressSec: Double,
    val durationSec: Double,
    val selectedCategory: AudioCategory,
    val isLoading: Boolean
  )
- Expose: val uiState: StateFlow<AudioUiState>
- Functions:
  - fun selectCategory(category: AudioCategory): filters tracks and resets player
  - fun playTrack(track: AudioTrack): pauses current, loads new track URL, plays, starts progress polling
  - fun pauseCurrentTrack()
  - fun resumeCurrentTrack()
  - fun seekTo(seconds: Double)
  - fun togglePlay(track: AudioTrack): if track is active → toggle play/pause; else → playTrack(track)
  - private fun startProgressPolling(): uses CoroutineScope + while(isActive) { delay(250); update progressSec }
- Use CoroutineScope(SupervisorJob() + Dispatchers.Main)

Also create `composeApp/src/commonMain/kotlin/id/naulia/portfolio/audio/AudioPlayerContract.kt`:
- interface AudioPlayerContract with: load(url), play(), pause(), seekTo(seconds), getCurrentTime(), getDuration(), setOnTimeUpdate(callback), setOnEnded(callback), release()

And `composeApp/src/wasmJsMain/kotlin/id/naulia/portfolio/audio/HtmlAudioPlayer.kt`:
- class HtmlAudioPlayer : AudioPlayerContract
- Uses HTMLAudioElement from kotlinx.browser.document
- Implements all interface methods via JS interop
- Use `js("...")` calls or org.w3c.dom APIs as available in kotlin-stdlib-wasm-js
```

---

### PROMPT 3.2 — ContactViewModel

> **Rules applied:** R1.3 (sealed SubmissionState), R1.6 (Coroutines), R1.7 (StateFlow), R6.1 (testable DI), R6.2 (validation as pure function)  
> **PRD ref:** §3 #contact — "Multi-select service pills", "Name, Email/WhatsApp, Scope" inputs · §4 Form lead capture requirements  
> **PLAN ref:** §4 State Management (FormState), §8 Form Submission, SKILL.md §5.4 (SelectableServicePill recipe)

```
Create ContactViewModel for the collaboration/booking form.
Package: id.naulia.portfolio.viewmodel
File: `composeApp/src/commonMain/kotlin/id/naulia/portfolio/viewmodel/ContactViewModel.kt`

Requirements:
- Pure Kotlin class, no Android dependency
- Sealed class SubmissionState: Idle, Loading, Success, Error(message: String)
- Inner data class FormValidation(
    val nameError: String?, val contactError: String?,
    val servicesError: String?, val scopeError: String?
  ) with val isValid = all nulls
- Inner data class FormUiState(
    val form: ContactForm,
    val validation: FormValidation,
    val submissionState: SubmissionState,
    val selectedServices: Set<ServiceType>
  )
- Expose: val uiState: StateFlow<FormUiState>
- Functions:
  - fun updateName(name: String)
  - fun updateContact(contact: String)
  - fun toggleService(service: ServiceType): add/remove from selectedServices set
  - fun updateScope(scope: String)
  - fun updateMessage(message: String)
  - fun submit(): validates → if valid, POST to Formspree endpoint (inject endpoint URL as constructor param `formEndpoint: String`)
  - fun resetForm()
  - private fun validate(): FormValidation — name required, contact required (email or starts with 08/+62), at least 1 service selected, scope ≥ 10 chars
- Formspree POST body: JSON with all form fields
- Use ktor HttpClient with JsonContentConverter for the submission
- Provide HttpClient as constructor param for testability
```

---

### PROMPT 3.3 — CmsViewModel

> **Rules applied:** R2.5 (manual DI via AppContainer), R1.6 (exception handling in coroutines), R2.2 (expect/actual for audioPlayer)  
> **PRD ref:** §1 Key Deliverables (all content types: audio, books, events, metrics)  
> **PLAN ref:** §4 State Management (ContentState fields), §6 CMS Strategy, SKILL.md §4.1 (ViewModel pattern)

```
Create CmsViewModel that orchestrates all content loading.
Package: id.naulia.portfolio.viewmodel
File: `composeApp/src/commonMain/kotlin/id/naulia/portfolio/viewmodel/CmsViewModel.kt`

Requirements:
- Constructor: CmsViewModel(private val repository: ContentRepository)
- data class ContentState(
    val tracks: List<AudioTrack> = emptyList(),
    val books: List<Book> = emptyList(),
    val events: List<McEvent> = emptyList(),
    val metrics: PortfolioMetrics = PortfolioMetrics(...defaults...),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
  )
- Expose: val contentState: StateFlow<ContentState>
- In init {}: launch coroutine → load all content from repository → update state
- fun refresh(): re-loads all content
- Handle exceptions gracefully: set errorMessage, isLoading = false

Also create `composeApp/src/commonMain/kotlin/id/naulia/portfolio/di/AppContainer.kt`:
- Simple manual DI container (no Koin/Hilt — keep it minimal)
- object AppContainer {
    val repository: ContentRepository = LocalContentRepository()
    val audioPlayer: AudioPlayerContract  // expect/actual pattern
    val audioViewModel by lazy { AudioViewModel(repository, audioPlayer) }
    val contactViewModel by lazy { ContactViewModel(httpClient = ..., formEndpoint = "https://formspree.io/f/YOUR_FORM_ID") }
    val cmsViewModel by lazy { CmsViewModel(repository) }
  }
- Use expect/actual for audioPlayer: expect val audioPlayer in commonMain, actual val audioPlayer = HtmlAudioPlayer() in wasmJsMain
```

---

## ═══ PHASE 4 — UI SCREENS ═══

> **Phase 4 Reference Docs:**
> - PRD.md §3 (Section Hierarchy — all 6 sections with IDs, components, content), §4 (UX Behaviors), §5 (Design System: Lumina colors, Outfit font)
> - PLAN.md §1 ui/screens/ structure, SKILL.md §6 (Section ID → Composable mapping)
> - RULES.md R3.1 (pure composables), R3.2 (state hoisting), R3.3 (modifier last param), R3.4 (recomposition safety), R3.6 (animation), R3.7 (theme usage), R3.8 (extraction), R3.9 (accessibility), R3.10 (naming)
> - SKILL.md §4.2 (Screen pattern), §5 (Component Recipes), §8 (Responsive Breakpoints), §9 (A11y Checklist)

---

### PROMPT 4.1 — App Root & Sticky Navigation Bar

> **Rules applied:** R3.1 (pure composables, side effects in LaunchedEffect), R3.2 (state hoisting), R3.9 (a11y semantics on nav), R4.1 (JS interop isolated)  
> **PRD ref:** §3 all section IDs · §4 "Smooth Anchor Scroll Navigation", "Active navigation state via IntersectionObserver", "Sticky bottom tab bar 5 destinations"  
> **PLAN ref:** §5 Navigation Model, SKILL.md §4.6 (JS Interop pattern for ScrollObserver), §6 (Section ID mapping)

```
Create the root App composable and sticky navigation header for Naulia Zahra's portfolio.
Package: id.naulia.portfolio
Files in `composeApp/src/commonMain/kotlin/id/naulia/portfolio/`

1. **App.kt** — Root composable:
   - Wrap in LuminaTheme
   - Provide AppContainer viewmodels via remember
   - Use Box(Modifier.fillMaxSize()) with:
     a. LazyColumn or Column with verticalScroll for all 6 screen sections stacked vertically
     b. Each section wrapped in a Box with Modifier.id(section.sectionId) — use `attrsModifier { id(section.sectionId) }` for JS targeting
     c. Sticky TopNavBar at top (always visible)
     d. Mobile BottomTabBar (visible only on small screens — use LocalWindowInfo or a custom breakpoint state)
   - Collect activeSection from Navigator.activeSection
   - Set up ScrollObserver in LaunchedEffect(Unit)

2. **TopNavBar.kt** in `ui/components/`:
   - Surface with shadow, full width, sticky position (inject CSS position: sticky via Modifier or HTML attr)
   - Row with: Naulia logo/name text on left, nav links on right (desktop), hamburger on mobile
   - Each nav item: Text button styled with primary color when active, onBackground when inactive
   - On click: navigator.navigateTo(section) → triggers JS smooth scroll
   - Design: background = LuminaColors.surface, height = 64.dp, horizontal padding = LuminaDimens.xl
   - Naulia Zahra wordmark: Outfit font, coral color, 20sp semibold

3. **BottomTabBar.kt** in `ui/components/`:
   - Row of 6 icon+label items for mobile (shown when screen width < 768px)
   - Fixed at bottom: use CSS position: fixed bottom: 0 injected via attrsModifier
   - Active tab: coral color + filled background chip; inactive: onSurface muted
   - Each item: Column { Icon text (emoji); Text(label, 10sp) }
   - Background: surface color, top border outline color, slight shadow up

Use `collectAsState()` to observe Navigator.activeSection for highlight state.
```

---

### PROMPT 4.2 — Hero Section

> **Rules applied:** R3.3 (modifier last param), R3.7 (Lumina theme tokens, no hardcoded colors), R3.9 (a11y: portrait contentDescription, CTA role), R5.3 (no magic strings)  
> **PRD ref:** §3 #home — "credential chips (Gramedia Book MC, 15+ Titles Edited, Expressive VO)", CTA labels "Undang untuk Acara / VO" and "Jelajahi Demo", portrait asset  
> **PLAN ref:** §9 Phase 1 deliverables (HeroScreen), SKILL.md §7 (Lumina Color Reference for chip coral background)

```
Create the HeroScreen composable for the Naulia Zahra portfolio.
File: `composeApp/src/commonMain/kotlin/id/naulia/portfolio/ui/screens/HeroScreen.kt`

Design spec (from PRD "Curated Lumina" design system):
- Background: Warm Cream gradient (0xFFFFF8F3 → 0xFFF9F2ED)
- Full-viewport-height section (100vh via Modifier + CSS)
- Layout (desktop): Row — left side 55% text content, right side 45% portrait image
- Layout (mobile): Column — portrait on top, text below

Content to implement:
1. **Credential chips row** — 3 chips with coral background + white text:
   - "Mitra Resmi Gramedia" (with ⭐ icon)
   - "15+ Judul Buku Diedit"
   - "Suara Ekspresif VO"
   Each chip: Rounded pill shape, Outfit 12sp semibold, coral background (0xFFFF8E72)

2. **Headline**: "Naulia Zahra" — Outfit 56sp bold, deep warm brown
   **Subheadline**: "Editor Buku · MC Panggung · Seniman Suara" — 20sp medium, coral color
   **Description paragraph**: 16sp body, warm brown/60% opacity, 2–3 lines about being a multidisciplinary creative

3. **CTA Buttons row**:
   - Primary: "Undang untuk Acara / VO" — coral filled button, Outfit 15sp semibold, cornerRadius=12.dp, onClick → navigator.navigateTo(Contact)
   - Secondary: "Jelajahi Demo Audio" — outlined button coral border, same size, onClick → navigator.navigateTo(Audio)

4. **Portrait placeholder**: Box with coral tinted rounded rectangle placeholder (use painterResource or Box with background if image not yet available), size 320x380dp, cornerRadius=24.dp, slight drop shadow

5. **Scroll-down indicator**: animated downward-pointing chevron at bottom center, pulsing animation (animateFloat with RepeatMode.Reverse)

Use LuminaTheme colors throughout. All text must meet 4.5:1 contrast ratio.
```

---

### PROMPT 4.3 — Audio Reel Screen

> **Rules applied:** R3.1 (no suspend in body), R3.2 (hoisted state from AudioViewModel), R3.4 (remember for derived state), R3.6 (animation + reduced-motion), R3.9 (a11y on play/pause)  
> **PRD ref:** §3 #featured-audiobook — category filters (Semua/Buku Cerita/Sains/VO Karakter), scrubber, timestamps, genre tags · §4 "Single-active-player", "Live animated visualizer", "Interactive scrubber", "spring feedback"  
> **PLAN ref:** §7 Audio Playback Architecture, SKILL.md §5.2 (AnimatedEqualizer recipe), §5.3 (AudioScrubber recipe)

```
Create the AudioReelScreen composable for the interactive audio demo player.
File: `composeApp/src/commonMain/kotlin/id/naulia/portfolio/ui/screens/AudioReelScreen.kt`

Accepts: audioViewModel: AudioViewModel
Collects: val uiState by audioViewModel.uiState.collectAsState()

Layout:
1. **Section header**: "Buku Suara & Demo VO" headline + subtitle

2. **Category filter tabs row** (horizontal scrollable):
   - AudioCategory.entries.forEach → FilterChip
   - Selected chip: coral background, white text, slight scale animation
   - Unselected: outline style, onSurface color
   - On click: audioViewModel.selectCategory(category)
   - Spring animation between selections

3. **Track list** (LazyColumn or Column with tracks):
   For each AudioTrack in uiState.filteredTracks, create an **AudioTrackCard**:

   AudioTrackCard (extract to separate file `ui/components/AudioTrackCard.kt`):
   - Card with surface background, coral left border (4dp), 16dp padding, cornerRadius=16dp
   - Row layout:
     a. **PlayPauseButton**: CircleButton 48dp, coral filled when this track is active+playing, outlined otherwise
        - Shows ▶ or ⏸ based on uiState (activeTrackId == track.id && isPlaying)
        - onClick: audioViewModel.togglePlay(track)
     b. **Track info column**: title (bodyLarge semibold), category tag chip (secondary color), tags row
     c. **Duration text**: labelSmall, right-aligned (format as "M:SS")
   
   **Expanded player** (shown when track.id == uiState.activeTrackId):
   - Animated expand (animateContentSize())
   - **AnimatedEqualizer**: Row of 5 vertical bars animating height with infiniteTransition when isPlaying
     Each bar: width=4dp, cornerRadius=2dp, coral color, animating between 8dp and 32dp with staggered delays
   - **Progress scrubber**: LinearProgressIndicator + Slider(value=progress/duration) for seeking, onClick → audioViewModel.seekTo(...)
   - **Time display**: Row { Text("0:${elapsed}"); Spacer(weight=1f); Text("0:${total}") }
   - **prefers-reduced-motion**: if reducedMotion → equalizer bars static height, no animation

Respect prefers-reduced-motion: query via JS `window.matchMedia("(prefers-reduced-motion: reduce)").matches` in a remember block.
```

---

### PROMPT 4.4 — Editorial Works (Books Showcase)

> **Rules applied:** R3.4 (remember for grid column derivation from window width), R3.5 (key= in lazy items), R3.7 (publisher badge colors from Lumina tokens), R3.9 (book cover contentDescription)  
> **PRD ref:** §3 #editorial-works — "2x3 responsive book cover showcase", age tags (Usia 7–12, Usia 4–8), publisher provenance (Gramedia, BIP, Mizan, Erlangga), role badges  
> **PLAN ref:** §3 Book model (publisher, ageRange, roles, coverAsset), SKILL.md §8 (Responsive Breakpoints)

```
Create the EditorialWorksScreen composable for the books gallery.
File: `composeApp/src/commonMain/kotlin/id/naulia/portfolio/ui/screens/EditorialWorksScreen.kt`

Accepts: cmsViewModel: CmsViewModel
Collects: val contentState by cmsViewModel.contentState.collectAsState()

Layout:
1. **Section header**: "Pilihan Karya Editorial & Buku" with subtitle "Buku Anak & STEM yang Pernah Diedit"

2. **Books grid** (2-column responsive grid):
   - Desktop: 3-column LazyVerticalGrid or use FlowRow
   - Mobile: 2-column grid
   - Each item: BookCoverCard (extract to `ui/components/BookCoverCard.kt`)

   BookCoverCard:
   - Card with surface background, cornerRadius=16dp, shadow elevation=4dp
   - Top: cover image placeholder (Box with sage mint gradient background + book title centered as text placeholder until real images available, aspect ratio 3:4)
   - Bottom padding area:
     a. Publisher badge: small chip (Gramedia=coral, BIP=yellow, Mizan=mint, Erlangga=lavender)
     b. Title: bodyLarge semibold, 2 lines max, ellipsis
     c. AgeRange chip: outline style, labelSmall
     d. Role badges row: each EditorRole as tiny chip, wrapping
     e. Year text: labelSmall, muted color
   - Hover state (desktop): slight scale(1.03f) + shadow increase — use Modifier.hoverable + animateFloatAsState

3. **Stats strip** below grid:
   - Row of 3 stat blocks: "15+ Judul", "4 Penerbit", "2022–2024"
   - Each: Column { Text(number, displayLarge, coral); Text(label, bodySmall, muted) }
```

---

### PROMPT 4.5 — MC Stage & Events Screen

> **Rules applied:** R3.6 (hover tilt animation + reduced-motion guard), R3.5 (@Stable on MetricCard data), R3.9 (event photo alt text), R5.4 (relative asset paths for photos)  
> **PRD ref:** §3 #mc-stage — "50+ Acara, Mitra Resmi Gramedia, 10+ Audiobook, 98% Retensi", high-res event photography, contextual metadata tags  
> **PLAN ref:** §3 McEvent model and PortfolioMetrics, SKILL.md §7 (Lumina colors: coral for metric cards)

```
Create the McStageScreen composable for the MC/Moderator showcase.
File: `composeApp/src/commonMain/kotlin/id/naulia/portfolio/ui/screens/McStageScreen.kt`

Accepts: cmsViewModel: CmsViewModel

Layout:
1. **Section header**: "MC Panggung & Moderator Acara"

2. **Metrics grid** (2x2 on mobile, 4-column row on desktop):
   MetricCard component (extract to `ui/components/MetricCard.kt`):
   - Card with coral gradient background (coral → coral-dark)
   - Large number: displayLarge white bold (with "+" suffix where applicable)
   - Label: bodyMedium white/80%
   - Slight 3D tilt animation on hover
   Data:
   - "50+" / "Total Acara"
   - "Mitra Resmi" / "Gramedia Official"
   - "10+" / "Audiobook Diproduksi"
   - "98%" / "Retensi Klien"

3. **Event photo gallery**:
   - LazyRow (horizontal scroll on mobile) or FlowRow (desktop 3-column)
   - Each: EventPhotoCard (extract to `ui/components/EventPhotoCard.kt`)
     * Top: photo placeholder Box (warm gradient, 16:9 ratio, overlaid with event name text as placeholder)
     * Bottom: event name (titleMedium), date + venue (bodySmall muted), tags row (small chips)
     * estimatedAttendees badge: top-right overlay, coral background, white text "👥 500+"

4. **Credibility strip**: horizontal row of text logos/names:
   "Gramedia · Kemendikbud · BookFest Jakarta · Science Expo · Pesta Buku ·"
   Styled as muted marquee-like row (can be static on first impl)
```

---

### PROMPT 4.6 — About Section

> **Rules applied:** R3.2 (bilingual toggle state local to composable — not global), R3.7 (typography for mission quote), R3.9 (capability pills: role=Button semantics), R5.3 (BioStrings companion object — no inline strings)  
> **PRD ref:** §3 #about — mission statement quote, editorial philosophy, "6 core capability pills (Developmental editing, STEM simplification, character VO, stage hosting…)"  
> **PLAN ref:** §9 Phase 2 (AboutScreen), SKILL.md §5.1 (SectionHeader recipe)

```
Create the AboutScreen composable for the philosophy & bio section.
File: `composeApp/src/commonMain/kotlin/id/naulia/portfolio/ui/screens/AboutScreen.kt`

Layout:
1. **Mission quote block**:
   - Large opening " quotation mark in coral (96sp Outfit italic)
   - Quote text: "Setiap kata yang saya edit, setiap acara yang saya pandu, dan setiap suara yang saya rekam adalah undangan untuk anak-anak Indonesia mencintai ilmu pengetahuan."
   - Attribution: "— Naulia Zahra" in coral italic

2. **Editorial philosophy narrative** (desktop: 2-column, mobile: single column):
   Left column:
   - Heading: "Filosofi Editorial"
   - 2–3 short paragraphs about her approach to children's books, STEM simplification, age-appropriate language
   Right column:
   - Heading: "Panggung & Suara"
   - 2–3 paragraphs about MC philosophy, voice acting craft

3. **Core capability pills** (wrapping FlowRow):
   6 pills with icons, coral/mint/lavender alternating colors:
   - 🖊️ "Developmental Editing"
   - 🔬 "STEM Science Simplification"
   - 🎭 "Character Voice Acting"
   - 🎤 "Live Stage Hosting"
   - 👶 "Children's Age-Grade Specialist"
   - 🌐 "Bilingual (ID/EN) Moderation"
   Each pill: Rounded pill shape, 14sp semibold, horizontal padding 16dp, vertical 8dp

4. **Bilingual toggle** (small toggle button top-right of section):
   - "🇮🇩 ID | EN 🇬🇧" toggle
   - On toggle: swap displayed text strings (use a local `isEnglish` remember state)
   - For now implement static bilingual strings as companion object BioStrings { val bahasa = ...; val english = ... }
```

---

### PROMPT 4.7 — Contact & Collaboration Form

> **Rules applied:** R1.3 (sealed SubmissionState), R3.2 (all state from ContactViewModel), R3.9 (service pills: role=Checkbox, stateDescription), R4.1 (WhatsApp/email via PlatformUtils in wasmJsMain), R6.2 (validation as pure function), R5.3 (Strings object for error messages)  
> **PRD ref:** §3 #contact — "Multi-select service pills (Editor Buku, MC Acara, VO Karakter, Kolaborasi Lengkap)", "Name, Email/WhatsApp, Scope", WhatsApp/Email direct links  
> **PLAN ref:** §8 Form Submission architecture, §4 ContactViewModel FormState, SKILL.md §4.3 (PlatformUtils expect/actual), §5.4 (SelectableServicePill recipe)

```
Create the ContactScreen composable with the full collaboration inquiry form.
File: `composeApp/src/commonMain/kotlin/id/naulia/portfolio/ui/screens/ContactScreen.kt`

Accepts: contactViewModel: ContactViewModel
Collects: val uiState by contactViewModel.uiState.collectAsState()

Layout:
1. **Section header**: "Formulir Kolaborasi & Reservasi"
   Subtitle: "Undang Naulia untuk proyek editorial, MC acara, atau rekaman VO"

2. **Service multi-select pills** (required, multi-select):
   ServiceType.entries.forEach → SelectableServicePill
   SelectableServicePill: pill shape, coral filled when selected, outlined when not
   On click: contactViewModel.toggleService(service)
   Show validation error below if none selected on submit attempt

3. **Form fields** (vertical column, 16dp spacing):
   a. OutlinedTextField: "Nama Lengkap" — contactViewModel.updateName()
      Validation error: show if nameError != null
   b. OutlinedTextField: "Email / Nomor WhatsApp" — contactViewModel.updateContact()
      helperText: "Contoh: nama@email.com atau 08xxxxxxxxxx"
      Validation error: show if contactError != null
   c. OutlinedTextField: "Deskripsi Singkat Proyek / Acara" (multiline, minLines=3)
      contactViewModel.updateScope()
   d. OutlinedTextField: "Pesan Tambahan (Opsional)" (multiline, minLines=2)
      contactViewModel.updateMessage()

   All fields: cornerRadius=12dp, coral focus border color, Outfit typography

4. **Submit button**: full-width, coral filled, "Kirim Permintaan Kolaborasi"
   - Shows CircularProgressIndicator when SubmissionState.Loading
   - Disabled during loading
   - On click: contactViewModel.submit()

5. **Success state**: AnimatedVisibility when SubmissionState.Success
   - Green checkmark illustration (use Unicode ✅ in large size as placeholder)
   - "Terima kasih! Naulia akan menghubungi Anda dalam 1×24 jam." text
   - "Kirim Permintaan Lain" outlined button → contactViewModel.resetForm()

6. **Error state**: show error Snackbar/banner if SubmissionState.Error

7. **Direct contact strip** below form:
   Row of two buttons:
   - WhatsApp: "💬 Chat via WhatsApp" → PlatformUtils.openWhatsApp("+6281234567890", "Halo Naulia, saya ingin berkolaborasi...")
   - Email: "✉️ Kirim Email" → PlatformUtils.openEmail("naulia@email.com")

Create `composeApp/src/wasmJsMain/kotlin/id/naulia/portfolio/platform/PlatformUtils.kt`:
- fun openWhatsApp(number: String, message: String): uses window.open("https://wa.me/${number}?text=${encodeURIComponent(message)}")
- fun openEmail(email: String): uses window.open("mailto:${email}")
- fun openUrl(url: String): uses window.open(url, "_blank")
```

---

## ═══ PHASE 5 — INTEGRATION & WIRING ═══

> **Phase 5 Reference Docs:**
> - PRD.md §4 (all UX behaviors: smooth scroll, intersection observer, single-player) · §3 Footer (quick links, copyright, location tags)
> - PLAN.md §1 App.kt and FooterSection, §5 Navigation Model
> - RULES.md R3.1 (LaunchedEffect for setup), R4.3 (CSS injection once in main.kt), R4.4 (event listener cleanup in DisposableEffect)
> - SKILL.md §4.6 (JS Interop ScrollObserver), §10 (Build Commands)

---

### PROMPT 5.1 — Wire Everything Together in App.kt

> **Rules applied:** R3.1 (LaunchedEffect for scroll observer setup), R2.5 (AppContainer for ViewModel access), R4.4 (DisposableEffect for listener cleanup), R2.2 (expect/actual setupScrollObserver)  
> **PRD ref:** §3 all section IDs must be rendered in page order · §4 all UX behaviors active in final integrated app  
> **PLAN ref:** §1 App.kt role, §5 Navigation Model, SKILL.md §6 (Section ID → Composable mapping table)

```
Update App.kt to wire all screens, viewmodels, navigation, and scroll behavior together.
File: `composeApp/src/commonMain/kotlin/id/naulia/portfolio/App.kt`

Full implementation:

```kotlin
@Composable
fun App() {
    LuminaTheme {
        val navigator = remember { Navigator() }
        val cmsViewModel = remember { AppContainer.cmsViewModel }
        val audioViewModel = remember { AppContainer.audioViewModel }
        val contactViewModel = remember { AppContainer.contactViewModel }
        val activeSection by navigator.activeSection.collectAsState()

        // Set up scroll observer (wasmJs actual) in LaunchedEffect
        LaunchedEffect(Unit) {
            setupScrollObserver(navigator)  // expect/actual function
        }

        Box(Modifier.fillMaxSize()) {
            // Main scrollable content
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.height(64.dp))  // top nav offset
                HeroScreen(navigator = navigator)
                AudioReelScreen(audioViewModel = audioViewModel)
                EditorialWorksScreen(cmsViewModel = cmsViewModel)
                McStageScreen(cmsViewModel = cmsViewModel)
                AboutScreen()
                ContactScreen(contactViewModel = contactViewModel)
                FooterSection(navigator = navigator)
            }

            // Sticky top nav
            TopNavBar(
                activeSection = activeSection,
                onSectionClick = { navigator.navigateTo(it) },
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}
```

Also implement `expect fun setupScrollObserver(navigator: Navigator)` in commonMain
and `actual fun setupScrollObserver(navigator: Navigator)` in wasmJsMain (using IntersectionObserver JS interop).

Create `ui/screens/FooterSection.kt`:
- Dark surface background (deepBrown)
- Row: Left=name + tagline; Middle=quick links (anchor nav); Right=contact info + "Jakarta & Daring" + copyright
- Quick links onclick → navigator.navigateTo(section)
```

---

### PROMPT 5.2 — Footer & Final Polish

> **Rules applied:** R4.3 (CSS injection once in main.kt via GlobalStyleInjector), R3.7 (Lumina dark surface for footer), R3.9 (footer links: role=Button semantics), R5.3 (no magic string copyright year)  
> **PRD ref:** §3 Footer — "Quick anchor jump links, agency/studio contact info, copyright, location tags (Jakarta & Daring)"  
> **PLAN ref:** §9 Phase 4 (Polish & Deploy checklist), SKILL.md §10 (build/run commands)

```
Create FooterSection and add final CSS polish injections for the Naulia portfolio.
Files to create/update:

1. **FooterSection.kt** (`ui/screens/FooterSection.kt`):
   Background: deep warm brown (0xFF2D1F14)
   Text: warm cream
   Layout (desktop Row, mobile Column):
   - Brand column: "Naulia Zahra" coral wordmark, tagline "Editor · MC · Suara", "Jakarta & Daring 🌐"
   - Links column: "Navigasi" heading + all NavSection links in bodyMedium cream text
   - Contact column: "Hubungi" heading + WhatsApp number + email + Instagram handle placeholder
   - Copyright row at bottom: "© 2024–2025 Naulia Zahra. All rights reserved."

2. **GlobalStyleInjector.kt** (`wasmJsMain` only):
   - fun injectGlobalStyles() — called once in main.kt before renderComposable
   - Injects via document.createElement("style") + styleElement.innerHTML:
     ```css
     * { box-sizing: border-box; margin: 0; }
     html { scroll-behavior: smooth; font-family: 'Outfit', sans-serif; }
     :focus-visible { outline: 2px solid #FF8E72; outline-offset: 2px; }
     @media (prefers-reduced-motion: reduce) {
       *, *::before, *::after { animation-duration: 0.001ms !important; transition-duration: 0.001ms !important; }
     }
     /* Custom scrollbar */
     ::-webkit-scrollbar { width: 6px; }
     ::-webkit-scrollbar-track { background: #FFF8F3; }
     ::-webkit-scrollbar-thumb { background: #FF8E72; border-radius: 3px; }
     ```

3. **Update main.kt**:
   ```kotlin
   fun main() {
       injectGlobalStyles()
       ComposeViewport(document.body!!) {
           App()
       }
   }
   ```
```

---

## ═══ PHASE 6 — BUILD, OPTIMIZE & DEPLOY ═══

> **Phase 6 Reference Docs:**
> - PRD.md §6 (Performance Budget: sub-1.5s FCP, WebP/SVG assets, zero heavy runtime) · §6 Accessibility (4.5:1 contrast, focus visible, prefers-reduced-motion)
> - PLAN.md §9 Phase 4 (accessibility, deploy), §11 Deployment (commands and targets)
> - RULES.md R7 (Performance: WASM bundle, WebP max sizes, lazy rendering), R3.9 (a11y mandatory), R8 (Git/CI)
> - SKILL.md §9 (A11y Execution Checklist), §10 (Build Commands), §11 (Anti-Patterns)

---

### PROMPT 6.1 — Build Configuration & Optimization

> **Rules applied:** R7.1 (minimal dependencies, WASM bundle size), R7.2–R7.3 (WebP/audio asset constraints), R8.2 (branch strategy), R8.3 (never commit build/)  
> **PRD ref:** §6 Performance Budget ("sub-1.5s FCP, WebP/SVG, zero heavy external runtime"), §7 Milestones (Phase 3 webhook integration)  
> **PLAN ref:** §11 Deployment table (commands, output dir, Netlify/GitHub Pages)

```
Finalize the build configuration for production deployment of the Naulia portfolio (wasmJs).

1. Update `composeApp/build.gradle.kts` with:
   - wasmJs { browser { commonWebpackConfig { cssSupport { enabled = true } } } }
   - Add webpack optimization for production: minimize = true
   - Configure processResources to copy `assets/` folder to output

2. Create `.github/workflows/deploy.yml` for GitHub Pages:
   ```yaml
   name: Deploy to GitHub Pages
   on:
     push:
       branches: [main]
   jobs:
     build-deploy:
       runs-on: ubuntu-latest
       steps:
         - uses: actions/checkout@v4
         - uses: actions/setup-java@v4
           with: { java-version: '17', distribution: 'temurin' }
         - name: Build WASM Distribution
           run: ./gradlew :composeApp:wasmJsBrowserDistribution
         - name: Deploy to GitHub Pages
           uses: peaceiris/actions-gh-pages@v3
           with:
             github_token: ${{ secrets.GITHUB_TOKEN }}
             publish_dir: composeApp/build/dist/wasmJs/productionExecutable
   ```

3. Create `netlify.toml` as alternative deploy config:
   ```toml
   [build]
     command = "./gradlew :composeApp:wasmJsBrowserDistribution"
     publish = "composeApp/build/dist/wasmJs/productionExecutable"
   [build.environment]
     JAVA_VERSION = "17"
   ```

4. Create placeholder asset directories with README stubs:
   - `assets/audio/README.md`: "Place .mp3 audio demo files here: track_01.mp3 … track_06.mp3"
   - `assets/images/covers/README.md`: "Place .webp book cover images here: book_01.webp … book_06.webp"
   - `assets/images/events/README.md`: "Place .webp MC event photos here"
   - `assets/images/portrait/README.md`: "Place portrait_hero.webp here (recommended: 640x760px, WebP)"

5. Run command to verify project structure:
   `./gradlew :composeApp:wasmJsBrowserDevelopmentRun --info`
   Report any dependency resolution errors and suggest fixes.
```

---

### PROMPT 6.2 — Accessibility Audit & Final Checks

> **Rules applied:** R3.9 (ALL a11y semantics, every screen), R3.6 (prefers-reduced-motion), R7.4 (lazy rendering for large lists), R6.3 (zero compiler warnings)  
> **PRD ref:** §6 Accessibility — "4.5:1 contrast ratio", "Focus visible outlines and keyboard navigability", "prefers-reduced-motion support"  
> **PLAN ref:** §12 Accessibility Checklist (all items must pass), SKILL.md §9 (A11y Execution Checklist — complete all 8 items)

```
Perform a full accessibility and quality pass on the Naulia Zahra portfolio codebase.

Review and update ALL screen composables to ensure:

1. **Semantic roles**: Every interactive element has Modifier.semantics applied:
   - Buttons: role = Role.Button, contentDescription set
   - Images/placeholders: contentDescription = "Book cover: ${book.title}" etc.
   - Audio play buttons: contentDescription = "Play ${track.title}" or "Pause ${track.title}"
   - Form fields: already handled by OutlinedTextField label parameter

2. **Keyboard navigation**: 
   - Verify Tab order is logical (top → bottom, left → right)
   - AudioTrackCard: Space bar triggers togglePlay
   - Scrubber Slider: Arrow keys seek ±5 seconds
   - Service pills in ContactScreen: Space toggles selection, Enter submits

3. **Color contrast audit** — verify these pairs meet 4.5:1:
   - Coral (#FF8E72) on White (#FFF8F3): check and adjust if needed (may need darker coral)
   - Deep brown (#2D1F14) on Cream (#FFF8F3): ✅ passes
   - Coral text on cream for navigation labels

4. **AnimatedEqualizer prefers-reduced-motion**:
   - Query `window.matchMedia("(prefers-reduced-motion: reduce)")` via JS interop
   - If true: render static bars at mid height, no animation
   - Expose as `val prefersReducedMotion: Boolean` from a `remember` block in wasmJsMain

5. **Focus visible**: Global CSS already injects `:focus-visible` outline. Verify Compose focusable modifiers don't suppress it.

6. **Performance checklist**:
   - All images use WebP format (confirmed in asset READMEs)
   - Audio files: recommend max 2MB per track (note in AudioTrackCard description)
   - No blocking synchronous JS in main thread
   - Lazy loading: use LazyColumn for event photo gallery if >5 items

Report all issues found and apply fixes directly.
```

---

## ═══ PHASE 7 — CMS ADMIN UI (PHASE 2 FUTURE) ═══

---

### PROMPT 7.1 — Simple CMS Admin Entry Point (Future Reference)

```
NOTE: This is a Phase 2 future prompt. Do not implement now.

When ready to implement the CMS Admin:

Create a separate Compose entry point at `/admin` route:
- Module: `cms-admin/` (separate Gradle submodule)
- Simple password gate (localStorage token: "naulia_admin_token")
- Three admin tabs: Audio Tracks | Books | MC Events
- Each tab: a list with Edit/Delete actions + an Add New form
- Save to: GitHub Gist API (simple, free) OR Supabase REST API
- Main portfolio site reads from the same public JSON URL

Setup:
1. Add `cms-admin` to settings.gradle.kts
2. Configure separate wasmJs entry in cms-admin/build.gradle.kts
3. Admin auth: simple sha256 hash comparison of password against stored hash in localStorage
4. CRUD forms: re-use the domain model data classes from composeApp/src/commonMain
5. Publish alongside main site but at /admin/ path
```

---

## Quick Reference — Execution Order

```
Phase 1:  Prompt 1.1 → 1.2 → 1.3
Phase 2:  Prompt 2.1 → 2.2
Phase 3:  Prompt 3.1 → 3.2 → 3.3
Phase 4:  Prompt 4.1 → 4.2 → 4.3 → 4.4 → 4.5 → 4.6 → 4.7
Phase 5:  Prompt 5.1 → 5.2
Phase 6:  Prompt 6.1 → 6.2
Phase 7:  Prompt 7.1 (Future)
```

> **Tip:** After each prompt, run `./gradlew :composeApp:wasmJsBrowserDevelopmentRun` to preview in browser before moving to the next prompt.
