# Changelog

All notable changes to the Naulia Zahra Interactive Portfolio are documented here. Dates use `YYYY-MM-DD`.

## [Unreleased]

### Planned
- Owner-supplied Formspree endpoint and verified WA/Email/Instagram/LinkedIn handles.
- Manual browser smoke: responsive screenshots, keyboard/tab order, screen-reader announcements, console/404 check.
- Deferred: remote CMS/admin, case-study detail overlays, site-wide localization, hero layered studio card.

## [2026-11-23] — CR7 Verification & Final Docs

### Fixed
- Active nav item contrast: `onPrimary` → `onPrimaryContainer` (was 2.24:1).
- Missing Compose focus rings on `LuminaFilterChip` and the About language toggle.
- Missing `Role`/`stateDescription` semantics on nav items, filter chips and the audio scrubber; contact errors/success now use live regions.
- `prefers-reduced-motion()` gaps in JS smooth scroll, CSS `scroll-behavior` and Compose color/content-size animations.
- Audio end state now clears `activeTrackId`, `isPlaying`, `progressSec` and `durationSec` on `setOnEnded`.
- Duplicate font loading from `index.html` plus `FontLoader`; font selector now dedupes the combined stylesheet.
- External links now open with `noopener,noreferrer`.

### Added
- `docs/change-request/CR-AUDIT.md` §7 `CR7.1 Verification Record` and §8 known limitations/follow-ups.
- Final sync of `PRD.md`, `PLAN.md`, `SKILL.md` and this changelog.

### Verified
- `./gradlew :composeApp:compileKotlinWasmJs` — PASS.
- `./gradlew :composeApp:wasmJsBrowserDistribution` — PASS with webpack devtool/performance/size warnings only.
- 33/33 referenced `audio/`/`images/` assets resolve in the distribution; zero HEIF files.
- `./gradlew :composeApp:wasmJsBrowserDevelopmentRun` browser smoke remains manual.

## [2026-11-23] — CR6 Contact, Navigation & Assets

### Added
- `ContactConfig` with `__NAULIA_CONTACT_CONFIG__` runtime override, reference contact fields, four channels and 2xx HTTP status handling.
- Canonical section IDs (`#home`, `#voice-audio-reel`, `#editorial-portfolio`, `#mc-live-stage`, `#about-naulia`, `#contact`) used by nav, hidden anchors, hash links and footer.
- Floating pill desktop nav, floating 6-destination bottom bar and richer 5/3/4 footer.
- WebP asset set in `composeApp/src/wasmJsMain/resources/{audio,images}`.

### Removed
- Duplicate `resources/assets/{audio,images}` tree and unreferenced HEIF files from the web bundle.

## [2026-11-23] — CR5 Books, MC & About

### Added
- Editorial category filters and reference-style cards/grid for all nine books; nullable `pageCount` documented.
- MC metrics, two wide event showcases and a text/icon trusted-client row; metrics single-sourced from `PortfolioMetrics.DEFAULT`.
- Two-column About layout with quote block, six capability cards and an About-only ID/EN toggle.

## [2026-11-23] — CR4 Hero & Audio

### Added
- Reference hero copy, trust badges, dual CTAs and portrait scrim caption; layered studio card deferred.
- Two-column featured audio cards with badges, duration, description, play/pause, scrubber and tag chips; playback/filter behavior preserved.

## [2026-11-23] — CR3 Design System

### Added
- Full Curated Lumina color families mirrored into Material3, Outfit/Hanken Grotesk type scale with leading/tracking, and spacing/radius/elevation/motion tokens.
- Shared primitives: buttons, pills, chips, cards, text fields and section headers.
- Deduped font loading and CSS focus/reduced-motion baseline.

## [2026-11-23] — CR1–CR2 Documentation Baseline

### Added
- `docs/change-request/CR-AUDIT.md` with initial gaps, D1–D7 decisions and acceptance criteria.
- Reference-backed `PRD.md` and actual-codebase `PLAN.md` used as the source of truth for later change requests.
