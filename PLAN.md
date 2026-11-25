# Naulia Zahra — Portfolio Platform: Comprehensive Development Plan

> **Tech Stack:** Kotlin Multiplatform (KMP) · Compose Multiplatform (`wasmJs` browser target only) · Kotlin/JS interop<br>
> **Target Platform:** Web-only — no Android, JVM or iOS targets (`settings.gradle.kts` includes only `:composeApp`)<br>
> **Design System:** Curated Lumina per `reference/DESIGN.md` + `reference/code.html`; frontmatter palette canonical (see `PRD.md` §5)<br>
> **Decision source:** `docs/change-request/CR-AUDIT.md` — binding decisions D1–D7. `PRD.md` is the requirement source; `RULES.md`/`SKILL.md` govern engineering.<br>
> **Status rule:** nothing is marked complete unless it is verified in code or by the listed command (§11).

---

## Current State (final verification 2026-11-23, CR7.2)

> The CR2 prompt template cited 2026-05-23; CR1.1/CR2 used 2026-11-23 and CR7.2 re-verified the tree after CR6.

**Implemented (verified in `composeApp/src` and the distribution):**
- Build: `wasmJs` browser target, `binaries.executable()`, dev-server port 8081, single `:composeApp`, Gradle catalog. `compileKotlinWasmJs` and `wasmJsBrowserDistribution` pass; 33/33 referenced assets resolve with zero HEIF in the bundle.
- Theme: full Curated Lumina `LuminaColorScheme` (primary/container, secondary, tertiary, surface, outline, inverse, error and fixed families) mirrored into Material3; Outfit display/headline + Hanken Grotesk body/label with leading/tracking; spacing/radius/elevation/motion tokens; `FocusRing`; one combined font stylesheet link plus deduping runtime injector.
- Navigation: `NavSection.sectionId` values are the reference anchors (`home`, `voice-audio-reel`, `editorial-portfolio`, `mc-live-stage`, `about-naulia`, `contact`); `Navigator` + `SectionScrollCoordinator` + `ScrollBridge` + wasm `ScrollObserver` (hidden DOM anchors + hash sync). Active section comes from a Compose scroll snapshot flow, not `IntersectionObserver`. Floating pill top nav, 6-destination floating bottom bar, 5/3/4 footer.
- Screens: hero with reference copy/badges/dual CTA/portrait scrim caption (studio card deferred); audio 2-col featured cards + category filters + single-active-player; editorial 9 books + filters + reference metadata; MC 4 metrics + 2 showcases + trusted-client row; About two-column cards + quote + About-only ID/EN toggle; contact reference fields + 4 channels + config guard + 2xx check + live regions.
- Data: `LocalContentRepository` (7 tracks, 9 books, 9 events, 4 metrics); `val`-only models; `BookCategory`/`BookFilter` + pure `List<Book>.filterByCategory`; `PortfolioMetrics.DEFAULT`; `CmsDataSource` remote methods remain `TODO("Phase 2 — Remote CMS")` (Future).
- ViewModels: `AudioViewModel` (single-active-player, scrub/seek, end state, 250 ms polling, category reset), `ContactViewModel` + pure `ContactValidation` + `SubmissionState`, `CmsViewModel` + `ContentState`; StateFlow + `SupervisorJob`, manual `AppContainer` DI.
- Platform: `AudioPlayerContract`/provider + `HtmlAudioPlayer`; `ImageLoader` fetch/Base64; `PlatformUtils` (URL/WA/email/`prefersReducedMotion`, `noopener,noreferrer`); `ScrollObserver`; `FontLoader`; `GlobalStyleInjector`; `ContactConfigProvider` (`__NAULIA_CONTACT_CONFIG__`).
- Assets/config: canonical `resources/{audio,images}`; repo-root `assets/` retained only as source. Formspree endpoint and contact handles are owner-supplied placeholders in `ContactConfig`.

**Deferred / not implemented:** configured backend endpoint and verified handles; hero layered studio card; site-wide localization; remote CMS/admin; case-study detail overlays; `Book.pageCount` seed values; manual browser screenshot/keyboard/screen-reader smoke. CR1 and CR2 changed documentation only; CR3–CR7 changed app code as recorded in §9.

---

## 0. Tech Stack Decision Rationale

| Concern | Choice | Reason |
|---|---|---|
| Language | Kotlin | Single language across all layers |
| UI Framework | Compose Multiplatform for Web (`wasmJs`) | Declarative, fully Kotlin, Compose paradigm |
| Shared Logic | `commonMain` | Models, repository, ViewModels, UI, theme shared |
| Web target | `wasmJs` browser | Full Compose runtime; only configured target |
| CMS / Content | Local `LocalContentRepository` objects → remote CMS is Future | Zero backend, type-safe, fast deploy |
| Audio | HTML5 `<audio>` via `kotlinx-browser` interop | Native browser playback/seek |
| Form Backend | Ktor POST to configurable Formspree-style endpoint | Lightweight, no server; currently placeholder |
| Styling | Compose theme + `LocalLuminaColors`/`LocalLuminaTypeScale` + injected global CSS | Follow RULES R3.7/R4.3; no per-screen magic values |
| Build | Gradle Kotlin DSL + `wasmJsBrowserDistribution` | Standard KMP toolchain |
| Deployment | Static bundle (Netlify / GitHub Pages / Vercel) | Zero-server CDN hosting |

---

## 1. Project Structure (actual)

```
naulia-personal-portofolio/
├── build.gradle.kts, settings.gradle.kts, gradle.properties
├── gradle/libs.versions.toml              # version catalog (§10)
├── PRD.md, PLAN.md, RULES.md, SKILL.md
├── docs/change-request/
│   ├── PROMPTS.md                         # CR phase prompts
│   └── CR-AUDIT.md                        # CR1 decisions, gaps, risks
├── reference/
│   ├── DESIGN.md                          # Curated Lumina tokens (canonical)
│   └── code.html                          # target composition/copy
│
└── composeApp/                            # only Gradle module
    ├── build.gradle.kts
    └── src/
        ├── commonMain/kotlin/id/naulia/portfolio/
        │   ├── App.kt
        │   ├── config/ContactConfig.kt, ContactConfigProvider.kt  # expect + runtime override
        │   ├── audio/
        │   │   ├── AudioPlayerContract.kt
        │   │   └── AudioPlayerProvider.kt  # expect
        │   ├── data/
        │   │   ├── cms/CmsDataSource.kt    # remote stub — TODO(Phase 2)
        │   │   ├── model/
        │   │   │   ├── AudioTrack.kt, AudioTrack+Extensions.kt, AudioCategory.kt
        │   │   │   ├── Book.kt, BookCategory.kt, BookFilter.kt, AgeRange.kt
        │   │   │   ├── EditorRole.kt, Publisher.kt, McEvent.kt, PortfolioMetrics.kt
        │   │   │   ├── ContactForm.kt, ServiceType.kt
        │   │   ├── repository/
        │   │   │   ├── ContentRepository.kt
        │   │   │   └── LocalContentRepository.kt  # 7 tracks / 9 books / 9 events
        │   ├── di/AppContainer.kt
        │   ├── image/ImageLoader.kt        # expect
        │   ├── navigation/
        │   │   ├── NavSection.kt           # 6 reference anchor IDs
        │   │   ├── Navigator.kt
        │   │   ├── ScrollBridge.kt
        │   │   └── SectionScrollCoordinator.kt
        │   ├── platform/PlatformUtils.kt   # expect
        │   ├── ui/
        │   │   ├── components/
        │   │   │   ├── AnimatedEqualizer.kt, AudioScrubber.kt, AudioTrackCard.kt
        │   │   │   ├── BookCoverCard.kt, CapabilityCard.kt, CredentialChip.kt
        │   │   │   ├── EventPhotoCard.kt, MetricCard.kt, SectionHeader.kt
        │   │   │   ├── LuminaButtons.kt, LuminaCard.kt, LuminaFilterChip.kt
        │   │   │   ├── LuminaPill.kt, LuminaTextField.kt, SelectableServicePill.kt
        │   │   │   ├── TopNavBar.kt, BottomTabBar.kt, TrustedClientRow.kt
        │   │   ├── icons/LuminaIcons.kt
        │   │   ├── screens/
        │   │   │   ├── HeroScreen.kt, AudioReelScreen.kt, EditorialWorksScreen.kt
        │   │   │   ├── McStageScreen.kt, AboutScreen.kt, BioStrings.kt
        │   │   │   ├── ContactScreen.kt, FooterSection.kt
        │   │   └── theme/
        │   │       ├── LuminaTheme.kt, LuminaColors.kt, LuminaTypography.kt
        │   │       ├── LuminaDimens.kt, PublisherBadgeColor.kt, Responsive.kt
        │   │       ├── BookCategoryTone.kt, FocusRing.kt
        │   │       └── FontLoader.kt        # expect
        │   └── viewmodel/
        │       ├── AudioViewModel.kt, ContactViewModel.kt, CmsViewModel.kt
        │       ├── ContactValidation.kt, ContentState.kt, SubmissionState.kt
        │
        └── wasmJsMain/
            ├── kotlin/id/naulia/portfolio/
            │   ├── main.kt                 # entry point
            │   ├── GlobalStyleInjector.kt  # global CSS (R4.3)
            │   ├── audio/{AudioPlayerProvider.kt,HtmlAudioPlayer.kt}
            │   ├── config/ContactConfigProvider.kt  # actual (runtime override)
            │   ├── image/ImageLoader.wasmJs.kt
            │   ├── navigation/ScrollObserver.kt
            │   ├── platform/PlatformUtils.kt
            │   └── ui/theme/FontLoader.kt
            └── resources/
                ├── index.html              # web shell; one font stylesheet + favicons
                ├── favicon*.png/.ico/.svg, apple-touch-icon.png
                ├── audio/*.mp3             # 7 tracks, code fetches "audio/…"
                └── images/{audio,hero,projects/books,projects/events}/*.webp
```

**Legacy / Future directories (not part of the build):** repo-root `assets/`, `shared/`, `webApp/` are scaffold leftovers — `settings.gradle.kts` includes only `:composeApp`. CR6.3 deduped the web bundle; repo-root `assets/` remains only as a generation/source tree. `cms-admin/` + separate `/admin` entry are **Future**, not present in the tree. No test source sets exist yet; pure functions in `ContactValidation`/`BookFilter` are the first candidates.

---

## 2. Architecture Overview

```
Browser (WASM runtime)
  main.kt → renderComposable → App.kt
      ├─ LuminaTheme (LocalLuminaColors / LocalLuminaTypeScale / MaterialTheme)
      ├─ Navigator + SectionScrollCoordinator + ScrollBridge ↔ ScrollObserver (JS)
      └─ Screens: Hero | AudioReel | EditorialWorks | McStage | About | Contact | Footer

commonMain
  ViewModels: AudioViewModel | ContactViewModel | CmsViewModel
  Data: ContentRepository ← LocalContentRepository (+ CmsDataSource stub, Future)
  Contracts (expect): AudioPlayerContract/provider, ImageLoader, PlatformUtils, FontLoader

wasmJsMain (JS interop only)
  HtmlAudioPlayer | ImageLoader.wasmJs | ScrollObserver | PlatformUtils | FontLoader | GlobalStyleInjector

External
  Formspree-style HTTP endpoint (configurable, currently placeholder) | WA/Email/IG/LinkedIn URLs
```

---

## 3. Data Models (actual `commonMain` fields)

```kotlin
@Immutable
data class AudioTrack(                 // not @Serializable — no wire boundary
    val id: String,
    val title: String,
    val category: AudioCategory,       // ALL, STORY, SCIENCE, CHARACTER_VO
    val durationSec: Int,
    val assetUrl: String,              // "audio/…"
    val coverAsset: String = "",       // "images/audio/…"
    val description: String = "",      // CR4.2 featured-card description
    val tags: List<String> = emptyList(),
)

@Serializable
enum class BookCategory(val label: String) { SAINS("Sains"), BUKU_CERITA("Buku Cerita"), ENSIKLOPEDIA("Ensiklopedia") }

enum class BookFilter(val label: String, val category: BookCategory?) {
    ALL("Semua", null), SAINS("Sains", BookCategory.SAINS), BUKU_CERITA("Buku Cerita", BookCategory.BUKU_CERITA), ENSIKLOPEDIA("Ensiklopedia", BookCategory.ENSIKLOPEDIA)
}

@Serializable
data class Book(
    val id: String, val title: String,
    val publisher: Publisher, val category: BookCategory, val ageRange: AgeRange,
    val roles: List<EditorRole>, val coverAsset: String, val year: Int,
    val synopsis: String = "",
    val pageCount: Int? = null,         // nullable; no verified seed values yet
)

@Serializable
data class McEvent(
    val id: String, val eventName: String, val date: String,
    val venue: String, val city: String,
    val estimatedAttendees: Int, val photoAsset: String, val tags: List<String>,
    val role: String = "", val description: String = "",
)

@Serializable
data class PortfolioMetrics(           // single source via DEFAULT
    val totalEvents: Int, val officialPartners: String,
    val audiobooksProduced: Int, val clientRetentionPct: Int, val titlesEdited: Int,
) { companion object { val DEFAULT = PortfolioMetrics(50, "Gramedia", 10, 98, 15) } }

@Serializable
data class ContactForm(                // CR6.1 reference field names
    val nameAndInstitution: String = "", val contact: String = "",
    val services: Set<ServiceType> = emptySet(),
    val projectScope: String = "", val message: String = "",
)
```

Metrics are single-sourced from `PortfolioMetrics.DEFAULT` (CR5.2); `ContentState.DEFAULT_METRICS` was removed. Book filtering lives in the pure `List<Book>.filterByCategory(BookFilter)` function (CR5.1). All public properties remain `val` (R1.2).

---

## 4. State Management

| ViewModel | State (actual) | Key fields / behavior |
|---|---|---|
| `AudioViewModel` | `AudioUiState` (`@Stable`) | `tracks`, `filteredTracks`, `activeTrackId`, `isPlaying`, `progressSec`, `durationSec`, `selectedCategory`, `isLoading`; 250 ms progress polling; single-active-player; `onCleared()` releases player |
| `ContactViewModel` | `FormUiState` (`@Stable`) | `form`, `validation`, `submissionState: SubmissionState`, `selectedServices`; pure `validateContactForm`; POST with Idle/Loading/Success/Error |
| `CmsViewModel` | `ContentState` (`@Stable`) | `tracks`, `books`, `events`, `metrics`, `isLoading`, `errorMessage`; loads from `ContentRepository` |

All ViewModels are plain Kotlin classes with `SupervisorJob + Dispatchers.Main`, expose `StateFlow`, use `.update {}`, and are obtained in Compose via `remember { AppContainer.… }` (R1.6/R1.7/R2.5).

---

## 5. Navigation Model (actual)

```kotlin
sealed class NavSection(val sectionId: String, val labelBahasa: String,
                        val labelEnglish: String, val navLabelBahasa: String,
                        val navLabelEnglish: String, val imageVector: ImageVector) {
    object Home    : NavSection("home",               "Beranda", "Home",    "Beranda",           "Home",            …)
    object Audio   : NavSection("voice-audio-reel",   "Audio",   "Audio",   "Buku Suara & VO",   "Audiobooks & VO", …)
    object Books   : NavSection("editorial-portfolio","Karya",   "Books",   "Karya Editorial",   "Editorial Works", …)
    object Stage   : NavSection("mc-live-stage",      "Acara",   "Events",  "MC & Panggung",     "MC & Stage",      …)
    object About   : NavSection("about-naulia",       "Profil",  "Profile", "Tentang",           "About",           …)
    object Contact : NavSection("contact",            "Kontak",  "Contact", "Kontak",            "Contact",         …)
    companion object {
        val all = listOf(Home, Audio, Books, Stage, About, Contact)
        val primaryNav = listOf(Books, Audio, Stage, About, Contact) // Home is the wordmark
        fun fromSectionId(id: String): NavSection? = all.firstOrNull { it.sectionId == id }
    }
}
```

Scroll-based, not route-based. `ScrollObserver` injects hidden DOM anchors and syncs the hash via `history.replaceState`; active section comes from a Compose `snapshotFlow` on scroll position consumed by `Navigator.onScrollPositionChanged`. Scroll targets use the same `NavSection.sectionId` values with a fixed-nav offset; smooth scroll and animation are gated by `prefersReducedMotion()` (CR7.1 also gated the JS side and CSS `scroll-behavior`). Mobile bottom bar keeps all 6 destinations (D6).

---

## 6. CMS Strategy

Phase 1 content is `LocalContentRepository` objects (no backend). `CmsDataSource` declares `fetchTracks/fetchBooks/fetchEvents` as `TODO("Phase 2 — Remote CMS")`. A separate admin entry/remote JSON flow is **Future** and must not be described as implemented.

---

## 7. Audio Playback Architecture

`AudioPlayerContract` (commonMain `expect`) → `HtmlAudioPlayer` (wasmJs `actual`) wraps `HTMLAudioElement` (`load/play/pause/seekTo/getCurrentTime/getDuration/setOnTimeUpdate/setOnEnded/release`). `AudioViewModel` loads one track at a time (pausing the previous), falls back to `AudioTrack.durationSec` when the element duration is unavailable, and polls progress every 250 ms. CR4.2 restyled this to the reference while keeping filters as a documented deviation; CR7.1 fixed the end-state so `setOnEnded` clears `activeTrackId`, `isPlaying`, `progressSec` and `durationSec`.

---

## 8. Form Submission

```
ContactViewModel.submit()
  → validateContactForm()  (pure; inline field errors)
  → Ktor HttpClient.post(configuredEndpoint) { contentType(JSON); setBody(form) }
  → 2xx: SubmissionState.Success   / exception or non-2xx: SubmissionState.Error
```

`ContactConfig` (CR6.1) defaults to `https://formspree.io/f/YOUR_FORM_ID` and is overridden at runtime via `window.__NAULIA_CONTACT_CONFIG__`; `ContactConfigProvider` is `expect`/`actual`. `ContactViewModel` accepts only 2xx, has Idle/Loading/Success/Error states, and CR7.1 added live regions for errors/success. WA/Email/IG/LinkedIn handles are in the same config and open via `PlatformUtils` with `noopener,noreferrer`. Real endpoint/handles are owner-supplied placeholders — no secrets in source or commits (R8.3).

---

## 9. Development Phases & CR Status

### Completed (verified in code)
- [x] KMP scaffold, single `wasmJs` target, `:composeApp` module, Gradle catalog.
- [x] Initial Lumina theme (11 colors, Outfit scale, dimens/breakpoints), 6-section shell, top/bottom nav + footer, scroll-spy.
- [x] All six screens with local seed content: 7 audio tracks with real playback/scrubber/equalizer, 9 books, 9 events + 4 metrics, About toggle, contact form scaffold.
- [x] Local content repository, ViewModels with StateFlow, manual DI, wasm interop (audio/image/scroll/platform/fonts/global CSS).
- [x] CR1 Audit: `docs/change-request/CR-AUDIT.md` with D1–D7 decisions, gap checklist, risks and acceptance criteria; app code untouched.
- [x] CR2 Docs (2026-11-23): `PRD.md` rewritten with reference tokens, ID mapping, interaction specs, config/asset policy and deviations; this `PLAN.md` rewritten to the actual file tree, models, dependencies, status and then-planned CR backlog. Documentation-only — no runtime behavior changed.
- [x] CR3.1 Theme (2026-11-23): full Curated Lumina color families mapped into `LuminaColorScheme` + Material3 (fixed tokens omitted where CMP 1.7.3 lacks them), Outfit/Hanken scale with leading/tracking and button style, spacing/gutter/margin/radius/elevation/size/motion tokens, combined font CSS injection, shell focus/theme colors updated.
- [x] CR3.2 Primitives (2026-11-23): `LuminaPrimaryButton`, `LuminaSecondaryButton`, `LuminaIconButton`, `StatusPill`/`SectionEyebrow`/`PillTone`, `LuminaFilterChip`, `LuminaCard`, `LuminaTextField`; cards/chips/headers/inputs migrated, reduced-motion hover/lift gated; `compileKotlinWasmJs` and `wasmJsBrowserDistribution` pass.
- [x] CR4 Hero/Audio (2026-11-23): hero uses reference copy with italic accent, dual CTA, three trust badges and portrait scrim/caption (studio card deferred with `TODO("CR4.1")`, documented in PRD §8); audio uses reference heading/eyebrow/subtitle, 2-column featured cards with category badge, duration, description, play/pause, scrubber/time labels and tag chips, filters retained per D3; `AudioTrack.description` added to model and seed data. `compileKotlinWasmJs` and `wasmJsBrowserDistribution` pass.
- [x] CR5 Books/MC/About (2026-11-23): editorial filters + reference cards/grid for 9 books, MC metrics/events/trusted-client row, About two-column cards + quote with About-only ID/EN toggle. `pageCount` is nullable with no verified seed values; documented in PRD §8. `compileKotlinWasmJs` and `wasmJsBrowserDistribution` pass.

### In Progress
- None. CR1–CR7 are complete and verified; only owner configuration, manual browser smoke and deferred features remain.

### Change Request Status (executed in `PROMPTS.md` order)
- [x] **CR3 Theme** — CR3.1 rebuilt `LuminaColorScheme` + Material3 scheme with reference families, Hanken Grotesk body/label + Outfit display with leading/tracking, elevation/radius/spacing tokens, deduped font loading; CR3.2 added shared CTA/ghost/icon buttons, chips, cards, 52px inputs, eyebrow header primitives and migrated screens.
- [x] **CR4 Hero/Audio** — CR4.1 hero copy, 3 trust badges, dual CTA, portrait scrim/caption, studio card deferred with `TODO("CR4.1")` + PRD note; CR4.2 reference audio heading/2-col featured layout with badge/duration/description/play/scrubber/tags, filters kept, playback semantics intact.
- [x] **CR5 Books/MC/About** — CR5.1 category filters, reference metadata/cards and 1/2/3-column grid for all 9 books, stats row removed, nullable `pageCount` documented (no verified values, year fallback); CR5.2 centered header, 4 metric cards with supporting text, 2 wide event showcases with role/description, text/icon trusted-client row, metrics single-sourced; CR5.3 two-column About with quote block, 6 capability cards, reference-aligned copy and accessible About-only ID/EN toggle. `compileKotlinWasmJs` and `wasmJsBrowserDistribution` pass.
- [x] **CR6 Contact/Nav/Footer/Assets** — CR6.1 reference fields/24h note/4 channels/runtime config + 2xx status handling; CR6.2 floating pill nav, 6-destination mobile bar, reference anchors + footer, hidden DOM anchors/hash sync and fixed-nav offset; CR6.3 canonical WebP assets, removed `.heif` and duplicate web trees, no 404s.
- [x] **CR7 QA/Deploy** — compile + distribution pass, 33/33 referenced assets resolve, a11y/motion/contrast/semantics/font-dedup defects fixed, results recorded in `CR-AUDIT.md` §7–§8, PRD/PLAN/SKILL/changelog synced. `wasmJsBrowserDevelopmentRun` browser smoke remains manual (see §11).
- [ ] Future (deferred): owner-supplied endpoint/handles + manual screenshot/keyboard/screen-reader smoke, remote CMS + admin UI, case-study modals, site-wide localization with owner-owned translations, hero studio card.

---

## 10. Key Dependencies (`gradle/libs.versions.toml` — actual)

```toml
[versions]
kotlin = "2.1.0"; compose-multiplatform = "1.7.3"; ktor = "3.0.3"
kotlinx-coroutines = "1.9.0"; kotlinx-serialization = "1.7.3"

[libraries]  # aliases used by composeApp/build.gradle.kts
compose-runtime, compose-foundation, compose-material3
ktor-client-core, ktor-client-content-negotiation, ktor-serialization-json
kotlinx-coroutines-core, kotlinx-serialization-json
# wasmJsMain additionally: ktor-client-js

[plugins]
kotlin-multiplatform, kotlin-serialization, compose-multiplatform, compose-compiler
```

`commonMain` declares Compose runtime/foundation/material3, coroutines, serialization and Ktor core/content-negotiation/json; `wasmJsMain` adds `ktor-client-js`. No other targets or heavyweight dependencies (R7.1).

---

## 11. Build, Verification & Deployment

**Explicit verification commands and CR7.1 results:**
```bash
./gradlew :composeApp:compileKotlinWasmJs                  # PASS (2026-11-23, after a11y/motion fixes)
./gradlew :composeApp:wasmJsBrowserDevelopmentRun          # MANUAL — browser smoke not executed in this session
./gradlew :composeApp:wasmJsBrowserDistribution            # PASS; webpack devtool/performance/size warnings only
```
Static check: 33/33 referenced `audio/`/`images/` paths resolve in `composeApp/build/dist/wasmJs/productionExecutable/`; zero HEIF files in the bundle. `SKILL.md` also references `compileProductionExecutableKotlinWasmJs` and `wasmJsTest`; no test source sets exist yet — add pure-function tests before relying on the latter. Evidence is in `CR-AUDIT.md` §7–§8.

| Step | Action |
|---|---|
| Local dev | `./gradlew :composeApp:wasmJsBrowserDevelopmentRun` (port 8081 per `build.gradle.kts`) |
| Production build | `./gradlew :composeApp:wasmJsBrowserDistribution` |
| Output | `composeApp/build/dist/wasmJs/productionExecutable/` |
| Deploy | Static host (Netlify / GitHub Pages / Vercel) — build then publish output dir; no secrets in client bundle |

---

## 12. Asset & Deployment Hygiene (CR6.3)

1. **Canonical source (resolved CR6.3):** only `composeApp/src/wasmJsMain/resources/{audio,images}` ships; the mirrored `resources/assets/{audio,images}` tree was removed. Repo-root `assets/` is retained as a generation/source tree only.
2. **Formats (resolved):** web images are WebP (SVG for icons); the unreferenced `MC_Ulang_Tahun_Penerbit_BIP_34.heif` was removed from the web bundle. CR7.1 verified 0 HEIF files in the distribution.
3. **Dedupe & budget:** one copy of each MP3/WebP in the web bundle; R7.2–R7.3 limits (covers ≤200KB, event photos ≤400KB, portrait ≤150KB/640×760, audio ≤2MB) and lazy-loaded audio remain owner/browser-verification items (not re-measured in CR7.1).
4. **Paths (verified statically):** relative only — `audio/*.mp3`, `images/audio/*.webp`, `images/hero/*.webp`, `images/projects/books/*.webp`, `images/projects/events/*.webp`. CR7.1 checked all 33 referenced paths in the distribution; R5.4's `images/covers|events|portrait` layout remains a future normalization target. Browser console/404 verification is still manual.
5. **Output check:** `composeApp/build/dist/wasmJs/productionExecutable/` contains the expected `index.html`, favicons, `composeApp.js/.wasm`, `audio/` and `images/`. Exclude `build/`, `.gradle/`, `local.properties`, endpoint IDs and keys from commits (R8.3).

---

## 13. Accessibility Checklist (CR7.1)

- [x] Contrast ≥4.5:1 for text using reference token pairs; CR7.1 fixed active nav item from `onPrimary` to `onPrimaryContainer` (2.24:1 → compliant).
- [x] `contentDescription` on images/icon-only buttons; dynamic play/pause label; `Role`/`stateDescription` on chips, nav state and scrubber time (R3.9); contact status uses live regions.
- [x] Focus rings visible on nav, chips, buttons, inputs and audio controls; CR7.1 added Compose `focusRing` to `LuminaFilterChip` and the About language toggle in addition to the CSS override.
- [x] Code-level keyboard support: play/pause, seek, form fields and anchor links are operable; actual tab order remains a manual browser smoke item (CR-AUDIT §7).
- [x] `prefers-reduced-motion`: equalizer, card lift/content size, pill/chip/button color animations, smooth scroll (JS + CSS) and `Navigator` animation all gated (CR7.1 closed the remaining gaps).
- [x] Responsive 1/2/3-column grids and 6-destination bottom bar implemented at `LuminaDimens.Breakpoints` (mobile <480dp, tablet 480–1279dp, desktop ≥1280dp); visual screenshot verification remains manual.
