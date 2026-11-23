# Naulia Zahra — Portfolio Platform: Comprehensive Development Plan

> **Tech Stack:** Kotlin Multiplatform (KMP) · Compose Multiplatform (`wasmJs` browser target only) · Kotlin/JS interop<br>
> **Target Platform:** Web-only — no Android, JVM or iOS targets (`settings.gradle.kts` includes only `:composeApp`)<br>
> **Design System:** Curated Lumina per `reference/DESIGN.md` + `reference/code.html`; frontmatter palette canonical (see `PRD.md` §5)<br>
> **Decision source:** `docs/change-request/CR-AUDIT.md` — binding decisions D1–D7. `PRD.md` is the requirement source; `RULES.md`/`SKILL.md` govern engineering.<br>
> **Status rule:** nothing is marked complete unless it is verified in code or by the listed command (§11).

---

## Current State (as of 2026-11-23)

> The CR2 prompt template cited 2026-05-23; this is the actual inspection/execution date used by CR1.1 and CR2.

**Implemented (verified in `composeApp/src`):**
- `wasmJs` browser target with `binaries.executable()` and dev-server port 8081; single module `:composeApp`; Gradle Kotlin DSL + `gradle/libs.versions.toml`.
- Theme: `LuminaTheme.kt`, `LuminaColors.kt` (11-color older palette: `primary #FF8E72`, `primaryVariant #F59E0B`, etc.), `LuminaTypography.kt` (Outfit-only generic `SansSerif`, 13 styles, no leading/tracking), `LuminaDimens.kt`, `PublisherBadgeColor.kt`, `Responsive.kt`, common `FontLoader.kt`; no Hanken Grotesk, elevation/shadow tokens or container/inverse/error color families yet.
- Navigation/shell: `NavSection.all` with 6 IDs, `Navigator`, `SectionScrollCoordinator`, `ScrollBridge`, wasm `ScrollObserver`; `TopNavBar`, `BottomTabBar` (6 thumb targets), `FooterSection`; IntersectionObserver scroll-spy; `App.kt` composes all sections.
- Six screens + footer: `HeroScreen` (headline/chips/2 CTAs/portrait scrim), `AudioReelScreen` (4 category filters, 7 tracks), `EditorialWorksScreen` (9 books, category filters, reference metadata/cards, no stats row), `McStageScreen` (4 metric cards, 2 wide event showcases, trusted-client text/icon row), `AboutScreen` + `BioStrings` (two-column philosophy + quote, 6 capability cards, About-only ID/EN toggle), `ContactScreen` (multi-select chips, 5 fields, WA + Email only; 24h appears in success copy, not as the reference's standalone note).
- Data: `LocalContentRepository` with 7 `AudioTrack`, 9 `Book`, 9 `McEvent`, `PortfolioMetrics`; `ContentRepository` interface; `CmsDataSource` remote methods are `TODO("Phase 2 — Remote CMS")`; all models `val`-only, most `@Serializable`.
- ViewModels: `AudioViewModel` (single-active-player, scrub/seek, end state, progress polling, category reset), `ContactViewModel` + pure `ContactValidation` + `SubmissionState`, `CmsViewModel` + `ContentState`; plain Kotlin `CoroutineScope(SupervisorJob())`, `StateFlow`, manual DI via `AppContainer`.
- Platform interop: `AudioPlayerContract`/provider + wasm `HtmlAudioPlayer`, `ImageLoader` expect + wasm actual (fetch/Base64/ImageBitmap), `PlatformUtils` expect + wasm actual (open URL/WA/email, `prefersReducedMotion`), `FontLoader` actual, `GlobalStyleInjector` (focus ring + reduced-motion CSS override).
- Contact integration scaffold: Ktor `HttpClient` with content negotiation, POST to placeholder `https://formspree.io/f/YOUR_FORM_ID`; no HTTP status check yet.
- Web assets: `wasmJsMain/resources/index.html` (favicons, Outfit + Noto Color Emoji links), `audio/`, `images/`; duplicate `assets/audio`, `assets/images` tree and one unreferenced `.heif` per image tree.

**Not implemented / gaps driving CR3–CR7:** after CR3–CR5, remaining gaps are reference contact composition/channels/config, ID mapping exposure for reference anchors, reduced-motion gating for smooth/animated scroll, asset dedupe/WebP conversion, and final QA/a11y checks. Hero studio card and site-wide localization stay deferred. No app code or assets were changed in CR1 or CR2.

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
        │   ├── audio/
        │   │   ├── AudioPlayerContract.kt
        │   │   └── AudioPlayerProvider.kt  # expect
        │   ├── data/
        │   │   ├── cms/CmsDataSource.kt    # remote stub — TODO(Phase 2)
        │   │   ├── model/
        │   │   │   ├── AudioTrack.kt, AudioTrack+Extensions.kt, AudioCategory.kt
        │   │   │   ├── Book.kt, AgeRange.kt, EditorRole.kt, Publisher.kt
        │   │   │   ├── McEvent.kt, PortfolioMetrics.kt
        │   │   │   ├── ContactForm.kt, ServiceType.kt
        │   │   ├── repository/
        │   │   │   ├── ContentRepository.kt
        │   │   │   └── LocalContentRepository.kt  # 7 tracks / 9 books / 9 events
        │   ├── di/AppContainer.kt
        │   ├── image/ImageLoader.kt        # expect
        │   ├── navigation/
        │   │   ├── NavSection.kt           # 6 internal IDs
        │   │   ├── Navigator.kt
        │   │   ├── ScrollBridge.kt
        │   │   └── SectionScrollCoordinator.kt
        │   ├── platform/PlatformUtils.kt   # expect
        │   ├── ui/
        │   │   ├── components/
        │   │   │   ├── AnimatedEqualizer.kt, AudioScrubber.kt, AudioTrackCard.kt
        │   │   │   ├── BookCoverCard.kt, CredentialChip.kt, EventPhotoCard.kt
        │   │   │   ├── MetricCard.kt, SectionHeader.kt, SelectableServicePill.kt
        │   │   │   ├── TopNavBar.kt, BottomTabBar.kt
        │   │   ├── icons/LuminaIcons.kt
        │   │   ├── screens/
        │   │   │   ├── HeroScreen.kt, AudioReelScreen.kt, EditorialWorksScreen.kt
        │   │   │   ├── McStageScreen.kt, AboutScreen.kt, BioStrings.kt
        │   │   │   ├── ContactScreen.kt, FooterSection.kt
        │   │   └── theme/
        │   │       ├── LuminaTheme.kt, LuminaColors.kt, LuminaTypography.kt
        │   │       ├── LuminaDimens.kt, PublisherBadgeColor.kt, Responsive.kt
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
            │   ├── image/ImageLoader.wasmJs.kt
            │   ├── navigation/ScrollObserver.kt
            │   ├── platform/PlatformUtils.kt
            │   └── ui/theme/FontLoader.kt
            └── resources/
                ├── index.html              # web shell; font links + favicons
                ├── favicon*.png/.ico/.svg, apple-touch-icon.png
                ├── audio/*.mp3             # code fetches "audio/…"
                ├── images/{audio,hero,projects/books,projects/events}/*.webp
                ├── images/projects/events/*.heif   # unreferenced — remove/convert (§12)
                └── assets/{audio,images}/…         # duplicate tree — dedupe (§12)
```

**Legacy / Future directories (not part of the build):** repo-root `assets/`, `shared/`, `webApp/` are scaffold leftovers — `settings.gradle.kts` includes only `:composeApp` (CR6.3 confirms before deleting). `cms-admin/` + separate `/admin` entry are **Future**, not present in the tree. No test source sets exist yet; pure functions in `ContactValidation`/`AudioTrack+Extensions` are the first candidates.

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
data class AudioTrack(                 // not @Serializable — add if crossing a boundary
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
    val publisher: Publisher, val ageRange: AgeRange,
    val category: BookCategory, val roles: List<EditorRole>,
    val coverAsset: String, val year: Int,
    val synopsis: String,
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
data class ContactForm(                // CR6.1 extends for reference fields
    val name: String = "", val contact: String = "",
    val services: Set<ServiceType> = emptySet(),
    val scope: String = "", val message: String = "",
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
                        val labelEnglish: String, val imageVector: ImageVector) {
    object Home    : NavSection("home",              "Beranda",   "Home",    …)
    object Audio   : NavSection("featured-audiobook","Buku Suara","Audio",   …)
    object Books   : NavSection("editorial-works",   "Buku",      "Books",   …)
    object Stage   : NavSection("mc-stage",          "Acara",     "Events",  …)
    object About   : NavSection("about",             "Profil",    "Profile", …)
    object Contact : NavSection("contact",           "Kontak",    "Contact", …)
    companion object { val all = listOf(Home, Audio, Books, Stage, About, Contact) }
}
```

Scroll-based, not route-based. `IntersectionObserver` (wasm) drives the active section; CR6.2 keeps the internal IDs stable while exposing reference anchors (`voice-audio-reel`, `editorial-portfolio`, `mc-live-stage`, `about-naulia`, `contact`) to DOM/footer links, with fixed-nav scroll offset and reduced-motion gating. Mobile bottom bar keeps all 6 destinations (D6).

---

## 6. CMS Strategy

Phase 1 content is `LocalContentRepository` objects (no backend). `CmsDataSource` declares `fetchTracks/fetchBooks/fetchEvents` as `TODO("Phase 2 — Remote CMS")`. A separate admin entry/remote JSON flow is **Future** and must not be described as implemented.

---

## 7. Audio Playback Architecture

`AudioPlayerContract` (commonMain `expect`) → `HtmlAudioPlayer` (wasmJs `actual`) wraps `HTMLAudioElement` (`load/play/pause/seekTo/getCurrentTime/getDuration/setOnTimeUpdate/setOnEnded/release`). `AudioViewModel` loads one track at a time (pausing the previous), falls back to `AudioTrack.durationSec` when the element duration is unavailable, and polls progress every 250 ms. CR4.2 preserves all of this while restyling to the reference and keeping filters as a documented deviation.

---

## 8. Form Submission

```
ContactViewModel.submit()
  → validateContactForm()  (pure; inline field errors)
  → Ktor HttpClient.post(configuredEndpoint) { contentType(JSON); setBody(form) }
  → 2xx: SubmissionState.Success   / exception or non-2xx: SubmissionState.Error
```

Endpoint currently `https://formspree.io/f/YOUR_FORM_ID` in `AppContainer` (placeholder, uncommitted production value required). CR6.1 makes it configurable and adds HTTP status handling; CR6.1 also moves WA/Email/IG/LinkedIn handles to config and opens them via `PlatformUtils`. No secrets or real endpoint IDs in source or commits (R8.3).

---

## 9. Development Phases & CR Status

### Completed (verified in code)
- [x] KMP scaffold, single `wasmJs` target, `:composeApp` module, Gradle catalog.
- [x] Initial Lumina theme (11 colors, Outfit scale, dimens/breakpoints), 6-section shell, top/bottom nav + footer, scroll-spy.
- [x] All six screens with local seed content: 7 audio tracks with real playback/scrubber/equalizer, 9 books, 9 events + 4 metrics, About toggle, contact form scaffold.
- [x] Local content repository, ViewModels with StateFlow, manual DI, wasm interop (audio/image/scroll/platform/fonts/global CSS).
- [x] CR1 Audit: `docs/change-request/CR-AUDIT.md` with D1–D7 decisions, gap checklist, risks and acceptance criteria; app code untouched.
- [x] CR2 Docs (2026-11-23): `PRD.md` rewritten with reference tokens, ID mapping, interaction specs, config/asset policy and deviations; this `PLAN.md` rewritten to the actual file tree, models, dependencies, status and CR backlog. Documentation-only — no runtime behavior changed.
- [x] CR3.1 Theme (2026-11-23): full Curated Lumina color families mapped into `LuminaColorScheme` + Material3 (fixed tokens omitted where CMP 1.7.3 lacks them), Outfit/Hanken scale with leading/tracking and button style, spacing/gutter/margin/radius/elevation/size/motion tokens, combined font CSS injection, shell focus/theme colors updated.
- [x] CR3.2 Primitives (2026-11-23): `LuminaPrimaryButton`, `LuminaSecondaryButton`, `LuminaIconButton`, `StatusPill`/`SectionEyebrow`/`PillTone`, `LuminaFilterChip`, `LuminaCard`, `LuminaTextField`; cards/chips/headers/inputs migrated, reduced-motion hover/lift gated; `compileKotlinWasmJs` and `wasmJsBrowserDistribution` pass.
- [x] CR4 Hero/Audio (2026-11-23): hero uses reference copy with italic accent, dual CTA, three trust badges and portrait scrim/caption (studio card deferred with `TODO("CR4.1")`, documented in PRD §8); audio uses reference heading/eyebrow/subtitle, 2-column featured cards with category badge, duration, description, play/pause, scrubber/time labels and tag chips, filters retained per D3; `AudioTrack.description` added to model and seed data. `compileKotlinWasmJs` and `wasmJsBrowserDistribution` pass.
- [x] CR5 Books/MC/About (2026-11-23): editorial filters + reference cards/grid for 9 books, MC metrics/events/trusted-client row, About two-column cards + quote with About-only ID/EN toggle. `pageCount` is nullable with no verified seed values; documented in PRD §8. `compileKotlinWasmJs` and `wasmJsBrowserDistribution` pass.

### In Progress
- None. CR5 is complete; next prompt is CR6.1 (contact form). CR6–CR7 items below stay unchecked until implemented and verified.

### Change Request Backlog (execute in `PROMPTS.md` order; do not mark done before verification)
- [x] **CR3 Theme** — CR3.1 rebuilt `LuminaColorScheme` + Material3 scheme with reference families, Hanken Grotesk body/label + Outfit display with leading/tracking, elevation/radius/spacing tokens, deduped font loading; CR3.2 added shared CTA/ghost/icon buttons, chips, cards, 52px inputs, eyebrow header primitives and migrated screens.
- [x] **CR4 Hero/Audio** — CR4.1 hero copy, 3 trust badges, dual CTA, portrait scrim/caption, studio card deferred with `TODO("CR4.1")` + PRD note; CR4.2 reference audio heading/2-col featured layout with badge/duration/description/play/scrubber/tags, filters kept, playback semantics intact.
- [x] **CR5 Books/MC/About** — CR5.1 category filters, reference metadata/cards and 1/2/3-column grid for all 9 books, stats row removed, nullable `pageCount` documented (no verified values, year fallback); CR5.2 centered header, 4 metric cards with supporting text, 2 wide event showcases with role/description, text/icon trusted-client row, metrics single-sourced; CR5.3 two-column About with quote block, 6 capability cards, reference-aligned copy and accessible About-only ID/EN toggle. `compileKotlinWasmJs` and `wasmJsBrowserDistribution` pass.
- [ ] **CR6 Contact/Nav/Footer/Assets** — CR6.1 reference fields/24h note/4 channels/configurable endpoint/status handling; CR6.2 floating pill nav, 6-destination mobile bar, reference anchors + footer, keyboard/focus/scroll offset; CR6.3 canonical WebP assets, remove `.heif`, dedupe trees, no 404s.
- [ ] **CR7 QA/Deploy** — compile/dev-run/distribution, responsive checks, audio/form/scroll-spy smoke tests, reduced motion + focus verification, record results in `CR-AUDIT.md`, final PRD/PLAN/changelog sync.
- [ ] Future (deferred): configured Formspree/equivalent backend, remote CMS + admin UI, case-study modals, site-wide localization with owner-owned translations, hero studio card.

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

**Explicit verification commands (run after each implementation prompt from `PROMPTS.md`):**
```bash
./gradlew :composeApp:compileKotlinWasmJs                  # required compile gate (CR3 onward)
./gradlew :composeApp:wasmJsBrowserDevelopmentRun          # manual smoke: layout, audio, form, no 404s
./gradlew :composeApp:wasmJsBrowserDistribution            # production bundle
```
`SKILL.md` also references `compileProductionExecutableKotlinWasmJs` and `wasmJsTest`; no test source sets exist yet — add pure-function tests before relying on the latter.

| Step | Action |
|---|---|
| Local dev | `./gradlew :composeApp:wasmJsBrowserDevelopmentRun` (port 8081 per `build.gradle.kts`) |
| Production build | `./gradlew :composeApp:wasmJsBrowserDistribution` |
| Output | `composeApp/build/dist/wasmJs/productionExecutable/` |
| Deploy | Static host (Netlify / GitHub Pages / Vercel) — build then publish output dir; no secrets in client bundle |

---

## 12. Asset & Deployment Hygiene (CR6.3)

1. **Canonical source:** `composeApp/src/wasmJsMain/resources/{audio,images}` — data/repo already fetches `audio/…` and `images/…`; the mirrored `resources/assets/{audio,images}` tree and legacy repo-root `assets/` must be removed or documented as a generation source (verify no references before deleting source assets outside `resources`).
2. **Formats:** WebP for all web images (SVG for icons); convert `MC_Ulang_Tahun_Penerbit_BIP_34.heif` to `.webp` with the same basename or remove it from the web bundle — browsers cannot decode HEIF and it is unreferenced. No HEIF in the bundle unless converted.
3. **Dedupe & budget:** keep one copy of each MP3/WebP; enforce R7.2–R7.3 limits (covers ≤200KB, event photos ≤400KB, portrait ≤150KB/640×760, audio ≤2MB); lazy-load audio until user interaction.
4. **Paths:** relative only — current seed data uses `audio/*.mp3`, `images/audio/*.webp`, `images/hero/*.webp`, `images/projects/books/*.webp`, `images/projects/events/*.webp`; R5.4's `images/covers|events|portrait` layout is a normalization target for CR6.3, not current reality. Verify `index.html` font links and wasm fetch paths resolve with no console errors/404s.
5. **Output check:** confirm `composeApp/build/dist/wasmJs/productionExecutable/` contains the expected `index.html`, favicons, `composeApp.js/.wasm`, `audio/` and `images/`; exclude `build/`, `.gradle/`, `local.properties`, endpoint IDs and keys from commits (R8.3).

---

## 13. Accessibility Checklist (CR7.1)

- [ ] Contrast ≥4.5:1 for all text using the reference token pairs.
- [ ] `contentDescription` on images/icon-only buttons; dynamic play/pause label; `Role`/`stateDescription` on chips and clickable surfaces (R3.9).
- [ ] Focus rings visible on nav, chips, buttons, inputs and audio controls (CSS override plus Compose semantics).
- [ ] Keyboard: play/pause and seek operable; form fully navigable; anchor links reachable.
- [ ] `prefers-reduced-motion`: equalizer, card lift, smooth scroll and `Navigator` animation all gated; CSS override is not sufficient by itself.
- [ ] Responsive 1/2/3-column grids and 6-destination bottom bar verified at `LuminaDimens.Breakpoints` (mobile <480dp, tablet 480–1279dp, desktop ≥1280dp).
