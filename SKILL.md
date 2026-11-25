---
name: naulia-portfolio-kmp-web
description: >
  Step-by-step execution skill for building the Naulia Zahra personal portfolio
  as a Kotlin Multiplatform (KMP) + Compose Multiplatform (wasmJs) web application.
  Covers project scaffold, Lumina design system, data layer, ViewModels, all UI screens,
  JS interop, accessibility, and production deployment.
  Web-only target — no Android or iOS.
---

# SKILL.md — Naulia Zahra Portfolio
## KMP + Compose Multiplatform (Web) Execution Guide

> **Before every task**, read these four documents in order:
> 1. [`PRD.md`](./PRD.md) — Product requirements, sections, design system, personas
> 2. [`PLAN.md`](./PLAN.md) — Architecture, project structure, data models, tech decisions
> 3. [`RULES.md`](./RULES.md) — Mandatory Kotlin, KMP, and Compose best practices
> 4. This file (`SKILL.md`) — Execution patterns, component recipes, anti-patterns

---

## 1. Project Context Snapshot

| Field | Value |
|---|---|
| **App** | Naulia Zahra Interactive Portfolio & Booking Platform |
| **Target Platform** | Web-only (browser, WASM runtime) |
| **UI Framework** | Compose Multiplatform (`wasmJs` target) |
| **Shared Logic** | Kotlin Multiplatform (`commonMain`) |
| **Design System** | Curated Lumina per `reference/DESIGN.md` + `PRD.md` §5 — Outfit display/headline + Hanken Grotesk body/label, coral `#9D422C` / `#FF8E72` system |
| **Language** | Bahasa Indonesia (primary); About-only ID/EN toggle (site-wide localization deferred) |
| **Root Package** | `id.naulia.portfolio` |
| **Module** | `:composeApp` |
| **Entry Point** | `wasmJsMain/kotlin/.../main.kt` → `ComposeViewport` |

---

## 2. Environment Setup Checklist

Before writing any code, verify:
- [ ] **JDK 17+** installed (`java -version`)
- [ ] **Kotlin 2.1.0** configured in `libs.versions.toml`
- [ ] **Compose Multiplatform 1.7.3** in version catalog
- [ ] `wasmJs` target declared in `composeApp/build.gradle.kts` (and **only** `wasmJs`)
- [ ] No `android`, `jvm`, or `ios` targets in `composeApp/build.gradle.kts`
- [ ] `./gradlew :composeApp:wasmJsBrowserDevelopmentRun` launches a browser tab

---

## 3. Source Set Map

```
composeApp/src/
├── commonMain/kotlin/id/naulia/portfolio/
│   ├── App.kt
│   ├── navigation/         NavSection.kt, Navigator.kt
│   ├── data/
│   │   ├── model/          AudioTrack, Book, McEvent, ContactForm, enums
│   │   ├── repository/     ContentRepository (interface), LocalContentRepository
│   │   └── cms/            CmsDataSource (Phase 2 stub)
│   ├── ui/
│   │   ├── theme/          LuminaTheme, LuminaColors, LuminaTypography, LuminaDimens
│   │   ├── components/     Reusable atoms (chip, card, equalizer, nav bar)
│   │   └── screens/        HeroScreen, AudioReelScreen, EditorialWorksScreen,
│   │                       McStageScreen, AboutScreen, ContactScreen, FooterSection
│   ├── viewmodel/          AudioViewModel, ContactViewModel, CmsViewModel
│   ├── audio/              AudioPlayerContract (expect)
│   ├── platform/           PlatformUtils (expect)
│   └── di/                 AppContainer
│
└── wasmJsMain/kotlin/id/naulia/portfolio/
    ├── main.kt             Entry point
    ├── audio/              HtmlAudioPlayer (actual)
    ├── navigation/         ScrollObserver (actual)
    ├── platform/           PlatformUtils (actual)
    └── GlobalStyleInjector.kt
```

---

## 4. Core Patterns Reference

### 4.1 ViewModel Pattern (commonMain — no Android dependency)

```kotlin
class AudioViewModel(
    private val repository: ContentRepository,
    private val player: AudioPlayerContract
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val _uiState = MutableStateFlow(AudioUiState())
    val uiState: StateFlow<AudioUiState> = _uiState.asStateFlow()

    fun playTrack(track: AudioTrack) {
        scope.launch {
            _uiState.update { it.copy(activeTrackId = track.id, isPlaying = true) }
            player.load(track.assetUrl)
            player.play()
        }
    }

    fun onCleared() { scope.cancel() }
}
```

### 4.2 Screen Composable Pattern

```kotlin
@Composable
fun AudioReelScreen(
    audioViewModel: AudioViewModel,
    modifier: Modifier = Modifier          // R3.3 — modifier as last param
) {
    val uiState by audioViewModel.uiState.collectAsState()   // collect state

    Column(modifier = modifier) {
        SectionHeader(                      // extracted reusable component
            title = "Buku Suara & Demo VO",
            subtitle = "Demo audio rekaman VO Naulia Zahra"
        )
        // UI driven by uiState — never by internal remember state
    }
}
```

### 4.3 expect/actual Pattern

```kotlin
// commonMain/audio/AudioPlayerContract.kt
interface AudioPlayerContract {
    fun load(url: String)
    fun play()
    fun pause()
    fun seekTo(seconds: Double)
    fun getCurrentTime(): Double
    fun getDuration(): Double
    fun setOnTimeUpdate(callback: (Double) -> Unit)
    fun setOnEnded(callback: () -> Unit)
    fun release()
}

// commonMain/platform/PlatformUtils.kt
expect fun openExternalUrl(url: String)
expect fun openWhatsApp(number: String, message: String)
expect fun openEmail(address: String)
expect fun prefersReducedMotion(): Boolean

// wasmJsMain/platform/PlatformUtils.kt
actual fun openExternalUrl(url: String) { window.open(url, "_blank") }
actual fun openWhatsApp(number: String, message: String) {
    val encoded = encodeURIComponent(message)
    window.open("https://wa.me/$number?text=$encoded", "_blank")
}
actual fun openEmail(address: String) { window.open("mailto:$address") }
actual fun prefersReducedMotion(): Boolean =
    window.matchMedia("(prefers-reduced-motion: reduce)").matches
```

### 4.4 StateFlow Update Pattern

```kotlin
// ✅ Always use .update { } — never direct .value assignment
_uiState.update { currentState ->
    currentState.copy(
        isLoading = false,
        tracks = newTracks
    )
}
```

### 4.5 Lumina Theme Usage

```kotlin
@Composable
fun CredentialChip(label: String) {
    val colors = LocalLuminaColors.current         // ✅ via CompositionLocal
    val dimens = LuminaDimens                       // ✅ centralized spacing

    Surface(
        shape = RoundedCornerShape(dimens.cornerRadius.pill),
        color = colors.primary,
        modifier = Modifier.padding(dimens.spacing.xs)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,  // ✅ no hardcoded size
            color = colors.onPrimary,
            modifier = Modifier.padding(
                horizontal = dimens.spacing.md,
                vertical = dimens.spacing.sm
            )
        )
    }
}
```

### 4.6 JS Interop (wasmJsMain only)

```kotlin
// wasmJsMain/navigation/ScrollObserver.kt — hidden anchors + hash, not IntersectionObserver
actual fun setupScrollObserver(navigator: Navigator) {
    val idsJson = NavSection.all.joinToString(prefix = "[", postfix = "]") { "\"${it.sectionId}\"" }
    val initial = installSectionAnchors(idsJson) { id ->
        NavSection.fromSectionId(id)?.let(navigator::navigateTo)
    }
    NavSection.fromSectionId(initial)?.let(navigator::navigateTo)
}

actual fun smoothScrollToSection(sectionId: String) {
    if (prefersReducedMotion()) scrollIntoViewInstant(sectionId) else scrollIntoViewSmooth(sectionId)
}
```

Active section is driven in `commonMain` by `snapshotFlow { scrollPosition }` → `Navigator.onScrollPositionChanged`; `ScrollObserver` only installs hidden anchors, syncs the hash via `history.replaceState`, and scrolls with reduced-motion gating.

---

## 5. UI Component Recipes

### 5.1 SectionHeader (reusable)

```kotlin
@Composable
fun SectionHeader(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(bottom = LuminaDimens.spacing.lg)) {
        Text(title, style = MaterialTheme.typography.headlineLarge,
             color = MaterialTheme.colorScheme.onBackground)
        Spacer(Modifier.height(LuminaDimens.spacing.sm))
        Text(subtitle, style = MaterialTheme.typography.bodyLarge,
             color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
    }
}
```

### 5.2 AnimatedEqualizer (play state indicator)

```kotlin
@Composable
fun AnimatedEqualizer(
    isPlaying: Boolean,
    reducedMotion: Boolean,
    modifier: Modifier = Modifier
) {
    val barCount = 5
    val infiniteTransition = rememberInfiniteTransition()

    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
        repeat(barCount) { index ->
            val animatedHeight by if (isPlaying && !reducedMotion) {
                infiniteTransition.animateFloat(
                    initialValue = 8f, targetValue = 32f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(300 + index * 70, easing = EaseInOutSine),
                        repeatMode = RepeatMode.Reverse
                    )
                )
            } else {
                remember { mutableStateOf(if (isPlaying) 20f else 8f) }
            }

            Box(
                Modifier
                    .width(4.dp)
                    .height(animatedHeight.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
    }
}
```

### 5.3 Audio Scrubber

```kotlin
@Composable
fun AudioScrubber(
    progressSec: Double,
    durationSec: Double,
    onSeek: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    val progress = remember(progressSec, durationSec) {
        if (durationSec > 0) (progressSec / durationSec).toFloat().coerceIn(0f, 1f) else 0f
    }

    Column(modifier = modifier) {
        Slider(
            value = progress,
            onValueChange = { onSeek(it * durationSec) },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary
            )
        )
        Row {
            Text(progressSec.toTimeString(), style = MaterialTheme.typography.labelSmall)
            Spacer(Modifier.weight(1f))
            Text(durationSec.toTimeString(), style = MaterialTheme.typography.labelSmall)
        }
    }
}

fun Double.toTimeString(): String {
    val total = toInt()
    val m = total / 60
    val s = total % 60
    return "$m:${s.toString().padStart(2, '0')}"
}
```

### 5.4 Service Selection Pill (Contact Form)

```kotlin
@Composable
fun SelectableServicePill(
    service: ServiceType,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        if (isSelected) MaterialTheme.colorScheme.primary
        else Color.Transparent
    )
    val textColor by animateColorAsState(
        if (isSelected) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.primary
    )

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(LuminaDimens.cornerRadius.pill),
        color = backgroundColor,
        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
        modifier = modifier.semantics {
            role = Role.Checkbox
            stateDescription = if (isSelected) "Dipilih" else "Tidak dipilih"
            contentDescription = service.labelBahasa
        }
    ) {
        Text(
            text = service.labelBahasa,
            style = MaterialTheme.typography.labelLarge,
            color = textColor,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        )
    }
}
```

---

## 6. Section ID → Composable Mapping

| Canonical section ID (= DOM anchor) | NavSection | Screen Composable |
|---|---|---|
| `#home` | `NavSection.Home` | `HeroScreen.kt` |
| `#voice-audio-reel` | `NavSection.Audio` | `AudioReelScreen.kt` |
| `#editorial-portfolio` | `NavSection.Books` | `EditorialWorksScreen.kt` |
| `#mc-live-stage` | `NavSection.Stage` | `McStageScreen.kt` |
| `#about-naulia` | `NavSection.About` | `AboutScreen.kt` |
| `#contact` | `NavSection.Contact` | `ContactScreen.kt` |
| — | — | `FooterSection.kt` |

These IDs are used everywhere (Compose scroll targets, hidden DOM anchors, hash deep links, footer links, scroll-spy); there is no separate internal ID set.

---

## 7. Lumina Color Token Reference

> Canonical source: `reference/DESIGN.md` frontmatter and `PRD.md` §5. Do not reintroduce the old 11-token palette (`#F59E0B`, `#6EAB9E`, `#A78BCA`).

| Token | Hex | Usage |
|---|---|---|
| `primary` / `onPrimary` | `#9D422C` / `#FFFFFF` | Deep coral CTAs, text on primary |
| `primaryContainer` / `onPrimaryContainer` | `#FF8E72` / `#762612` | Active indicators, chip/container text (CR7.1 contrast fix) |
| `inversePrimary` | `#FFB4A2` | Inverse theme accents |
| `secondary` / `secondaryContainer` | `#735C00` / `#FFE087` | Warm yellow support tags |
| `tertiary` / `tertiaryContainer` | `#006C52` / `#55C09C` | Mint/STEM accents |
| `background` / `onBackground` | `#FFF8F3` / `#1D1B18` | Warm cream canvas, body text |
| `surface*` (lowest → highest) | `#FFFFFF` → `#E7E1DC` | Cards, nav, containers, dividers |
| `outline` / `outlineVariant` | `#89726D` / `#DCC0BA` | Borders, dividers |
| `error` / `errorContainer` | `#BA1A1A` / `#FFDAD6` | Error text and surfaces |

Typography: Outfit for display/headline, Hanken Grotesk for body/label with defined line height and letter spacing; see `PRD.md` §5 for the full scale.

---

## 8. Responsive Breakpoints

```kotlin
// Define in LuminaDimens.kt
object Breakpoints {
    val mobile = 480.dp
    val tablet = 768.dp
    val desktop = 1280.dp
}

// Usage pattern in composables
@Composable
fun rememberWindowWidth(): Dp {
    // wasmJsMain actual returns window.innerWidth.dp
    return getWindowWidth()  // expect/actual
}

@Composable
fun BookGrid(books: List<Book>) {
    val width = rememberWindowWidth()
    val columns = when {
        width < Breakpoints.tablet  -> 2
        width < Breakpoints.desktop -> 3
        else                        -> 3
    }
    // Use AdaptiveGrid or manual column count
}
```

---

## 9. Accessibility Execution Checklist

Run this checklist after implementing each screen composable:

- [x] Every `Image` / placeholder `Box` has `Modifier.semantics { contentDescription = "..." }`
- [x] Every clickable non-Button element has the correct `Role` (nav items and filter chips use `stateDescription`; chips use `Role.RadioButton`, service pills `Role.Checkbox`)
- [x] Audio play/pause contentDescription updates with state; scrubber exposes time via `stateDescription`
- [x] Focus rings via `Modifier.focusRing(...)` on nav, chips, language toggle, buttons and inputs (plus CSS `:focus-visible`)
- [x] All `clickable` surfaces carry role semantics
- [x] `prefersReducedMotion()` gates equalizer, card lift/content size, chip/pill/button color animations, smooth scroll (JS + CSS) and `Navigator` animations
- [x] Form validation errors use `isError`/supporting text plus `LiveRegionMode.Polite`; success message is a live region
- [x] `Text` contrast checked against reference token pairs (CR7.1 fixed active nav to `onPrimaryContainer`) — see `CR-AUDIT.md` §7
- [ ] Manual browser smoke still required: tab order, screen reader announcements and responsive screenshots

---

## 10. Build & Run Commands

```bash
# Start development server (manual smoke — still pending in CR7)
./gradlew :composeApp:wasmJsBrowserDevelopmentRun

# Production build (PASS in CR7.1; webpack size/devtool warnings only)
./gradlew :composeApp:wasmJsBrowserDistribution

# Fast compile gate (PASS in CR7.1)
./gradlew :composeApp:compileKotlinWasmJs

# Preferred compile alias from older phases; no test source sets exist yet
./gradlew :composeApp:compileProductionExecutableKotlinWasmJs
./gradlew :composeApp:wasmJsTest
```

CR7.1 evidence: 33/33 referenced assets resolve in `composeApp/build/dist/wasmJs/productionExecutable/`; zero HEIF in the bundle. See `CR-AUDIT.md` §7.

---

## 11. Common Anti-Patterns to Avoid

| ❌ Anti-Pattern | ✅ Correct Approach |
|---|---|
| `import kotlinx.browser.*` in `commonMain` | Move to `wasmJsMain` only |
| `androidx.lifecycle.ViewModel` in `commonMain` | Use plain Kotlin class + CoroutineScope |
| Hardcoded color: `Color(0xFFFF8E72)` in composable | `MaterialTheme.colorScheme.primary` |
| Hardcoded size: `fontSize = 24.sp` in composable | `MaterialTheme.typography.headlineMedium` |
| `mutableStateOf()` for server data in composable | Hoist to ViewModel `StateFlow` |
| `runBlocking { }` in composable body | `LaunchedEffect` + `collectAsState()` |
| `var` in data class | `val` + `copy()` |
| `GlobalScope.launch { }` | `scope.launch { }` with scoped CoroutineScope |
| `!!` operator on nullable | `?.let { }` or `?: return` |
| `Utils.kt` or `Helper.kt` file | Named extension file e.g. `AudioTrack+Extensions.kt` |
| Inline string literals in composables | `object Strings { const val ... }` |
| Absolute asset paths `/Users/...` | Relative paths `"audio/track_01.mp3"` |
| Adding `jvm`, `android`, `ios` targets | `wasmJs` target only |

---

## 12. Phase Execution Protocol

**Before starting any phase:**
1. Re-read `PRD.md` sections relevant to that phase
2. Re-read `PLAN.md` architecture section for the phase
3. Re-read `RULES.md` rules R1–R8 to refresh constraints
4. Re-read this `SKILL.md` sections 4–9 for patterns and recipes

**During implementation:**
- Follow prompt from `PROMPTS.md` exactly
- Apply all applicable RULES.md rules to every file written
- Use component recipes from SKILL.md section 5 as starting templates
- Refer to section ID mapping (SKILL.md section 6) to link PRD sections to code

**After completing each prompt:**
- Run `./gradlew :composeApp:compileKotlinWasmJs` — zero errors required (alias: `compileProductionExecutableKotlinWasmJs`)
- Run `./gradlew :composeApp:wasmJsBrowserDistribution` — production bundle, verify assets
- Run `./gradlew :composeApp:wasmJsBrowserDevelopmentRun` — visual/keyboard check in browser
- Verify accessibility checklist (section 9) for any new screen composable
- Commit using Conventional Commits format (RULES.md R8.1)
