# CR-AUDIT.md — Change-Request Audit & Source-of-Truth Decisions

> **Phase:** CR1.1 (Audit & Source-of-Truth Decisions)  
> **Date:** 2026-11-23  
> **Status:** Audit complete. No app code changed in this prompt.  
> **Governing rule:** For visual design, section composition, and component behavior, `reference/DESIGN.md` + `reference/code.html` win. Working product behavior (audio playback, scroll-spy, form validation) is preserved unless the reference explicitly replaces it, and every deviation is documented in PRD/PLAN/this audit (`docs/change-request/PROMPTS.md:21-30`).

---

## 1. Scope & Sources

| Source | Path | Governs | Notes |
|---|---|---|---|
| Reference Design Spec | `reference/DESIGN.md` | Curated Lumina tokens, typography, layout, elevation, shapes, components | Frontmatter tokens (`:3-135`) treated as canonical; prose palette architecture (`:146-164`) treated as descriptive |
| Reference HTML | `reference/code.html` | Target section structure, copy, hierarchy, interactions | Indonesian copy; single-page anchor nav; no build tooling |
| Product requirements | `PRD.md` | IA, interaction specs, mobile ergonomics, milestones | Current version predates implementation (e.g. §6 describes vanilla HTML, §7 milestones stale) |
| Execution plan | `PLAN.md` | Architecture, structure, models, build/deploy | Current version is scaffold-style with unchecked phases and planned-only files |
| Mandatory rules | `RULES.md` | Kotlin/KMP/Compose constraints (R1-R8) | Applies to all later CR implementation prompts |
| Execution patterns | `SKILL.md` | Component recipes, anti-patterns, build commands | Contains a second, older Lumina color table (`:373-387`) that must be reconciled in CR2 |
| Change-request prompts | `docs/change-request/PROMPTS.md` | CR phase sequence and acceptance criteria | Source of this prompt; quick order at `:353-371` |
| Current implementation — shared | `composeApp/src/commonMain/kotlin/id/naulia/portfolio/**` | App, data, viewmodels, navigation, UI, theme, expect declarations | 6 sections, 7 tracks, 9 books, 9 events, `PortfolioMetrics` |
| Current implementation — platform | `composeApp/src/wasmJsMain/kotlin/id/naulia/portfolio/**` | `main.kt`, `HtmlAudioPlayer`, `ScrollObserver`, `PlatformUtils`, `ImageLoader`, `FontLoader`, `GlobalStyleInjector` | Wasm-only JS interop; single `wasmJs` target |
| Current implementation — web assets | `composeApp/src/wasmJsMain/resources/**` | `index.html`, favicons, `audio/`, `images/` | Also contains duplicate `assets/` tree and one `.heif` file |
| Build config | `gradle/libs.versions.toml`, `build.gradle.kts`, `composeApp/build.gradle.kts`, `settings.gradle.kts` | Versions, targets, tasks | Kotlin 2.1.0, Compose 1.7.3, Ktor 3.0.3, coroutines 1.9.0, serialization 1.7.3 |

**Verified current state (facts used below):**

- Theme: 11 color tokens only; `primary = #FF8E72`, `primaryVariant = #F59E0B`, `secondary = #6EAB9E`, `tertiary = #A78BCA`; no error/inverse/container token families (`LuminaColors.kt:7-21`). Typography is a single Outfit-based scale; `OutfitFontFamily = FontFamily.SansSerif` with no bundled font file (`LuminaTypography.kt:11`). Hanken Grotesk does not exist anywhere in app code or resources. Dimens: spacing `4/8/16/24/40/64.dp`, radii `16/50/12.dp`, no elevation/shadow tokens (`LuminaDimens.kt:6-38`).
- Nav: 6 `NavSection` entries (`home`, `featured-audiobook`, `editorial-works`, `mc-stage`, `about`, `contact`) used by both nav bars, footer, and scroll-spy (`NavSection.kt:6-21`, `App.kt:104-130`). Top bar is an elevated rectangle with a compact hamburger menu; bottom bar is mobile-only with 6 thumb targets (`TopNavBar.kt:48-131`, `BottomTabBar.kt:40-86`).
- Hero: headline/subheadline/body, 2 CTAs, 3 credential chips, portrait with gradient; no layered voice-reel/studio card (`HeroScreen.kt:135-208`).
- Audio: header "Buku Suara & Demo VO", 4 category `FilterChip`s, 7 tracks, single-active player with equalizer and scrubber (`AudioReelScreen.kt:52-94`, `AudioViewModel.kt:48-95`).
- Books: header "Pilihan Karya Editorial & Buku", 9 books, 2/3-column grid, stats row, no filters (`EditorialWorksScreen.kt:34-108`). `Book` has `coverAsset`/`synopsis` but no `pageCount` (`Book.kt:5-14`).
- MC: 4 metrics, 9 events in cards/list, one text credibility line; no trusted-client logo row (`McStageScreen.kt:48-151`).
- About: philosophy narrative + quote + 6 capability pills in a `FlowRow`; ID/EN toggle is local to this screen only (`AboutScreen.kt:44-226`, `BioStrings.kt:18-70`).
- Contact: multi-select service chips, Nama Lengkap, Email/Nomor WhatsApp, Deskripsi Singkat Proyek, Pesan Tambahan; WA + Email only; Ktor POST to `https://formspree.io/f/YOUR_FORM_ID` with no HTTP status check (`ContactScreen.kt:60-293`, `ContactValidation.kt:6-43`, `AppContainer.kt:18-30`, `ContactViewModel.kt:73-95`).
- Assets: code references `audio/…` and `images/…`; identical files also exist under `assets/audio`, `assets/images` and a third copy at repo root `assets/`. One unreferenced `.heif` event image exists in each tree.

---

## 2. Decision Log (required)

> Labels are exactly `Adopt Reference`, `Keep Current`, or `Hybrid`. These decisions are binding for CR2-CR7 unless explicitly revised in a later prompt; CR2 must write them into PRD/PLAN and CR7 must verify them.

| ID | Topic | Options | Decision | Rationale | Consequence |
|---|---|---|---|---|---|
| **D1** | Typography | Reference: Outfit display/headline + Hanken Grotesk body/label with defined scale/leading/tracking. Current: Outfit-only, 13 ad-hoc styles, no leading/tracking. | **Adopt Reference** | Reference is source of truth for visual design (`PROMPTS.md:22`); body/label pairing is core to the curated editorial identity. | Add Hanken Grotesk weights to `index.html` and `FontLoader`, define `display-hero`/`headline-*`/`body-*`/`label-*` styles with fallback-safe families, keep Material3 typography mapping, remove duplicate Outfit stylesheet load. |
| **D2** | Color tokens | Reference: full Curated Lumina family (`primary #9D422C`, `primary-container #FF8E72`, secondary/tertiary, surface containers, outline, inverse, error). Current: 11-token older palette with amber `#F59E0B`. | **Adopt Reference** | Frontmatter token table is canonical and complete; current palette has no container/inverse/error semantics and reuses `onPrimary` for all container roles (`LuminaTheme.kt:19-37`). | Rebuild `LuminaColorScheme` + Material3 `lightColorScheme`; update `PublisherBadgeColor` mapping; remove ad-hoc hex from screens. Resolve the reference's internal frontmatter-vs-prose palette conflict by using frontmatter hex values and documenting prose as descriptive (R5.3). |
| **D3** | Audio section | Reference: static 2-col featured "Karya Pilihan Buku Suara & VO" cards with badge/duration/description/play/scrubber/tags, no category tabs. Current: filterable 7-track player list with real playback. | **Hybrid** | Playback, scrubber and single-active-player are working behavior to preserve (`PROMPTS.md:22,376`); filters are already specified in PRD §4 and are a useful enhancement. | Keep category tabs, restyle them and the cards to reference structure, adopt reference heading/copy, two-column desktop layout and metadata. Document the filter tabs as a deliberate deviation in PRD/PLAN. |
| **D4** | Editorial section | Reference: 6 illustrative cards + category/filter comment, age/publisher/role/page metadata. Current: 9 real book cards, stats row, no filters. | **Hybrid** | The 9 books use real local assets and should not be discarded; reference metadata style improves parity. | Keep all 9 books, add category filters (`Semua/Sains/Buku Cerita/Ensiklopedia` mapped from model), adopt reference card metadata and 1/2/3-column grid, add optional `pageCount` only if seed data can supply it; otherwise document omission. Remove or restyle the stats row per reference. |
| **D5** | Contact form schema | Reference: service chips, "Nama & Instansi", "Email atau WhatsApp", "Cakupan Proyek & Perkiraan Tanggal", WA/Email/IG/LinkedIn channels, 24h note. Current: 5 fields, WA/Email only, multi-select chips, different success copy. | **Hybrid** | Reference composition/copy/channels win; existing pure validation and Ktor submission scaffold are working behavior worth preserving. Keep multi-select chips (PRD §3 explicitly specifies multi-select and "Kolaborasi Lengkap" makes single-select redundant). | Extend `ContactForm` + `ContactValidation` to the reference fields, adopt reference labels/copy/24h note, add IG/LinkedIn channels via `PlatformUtils`, keep multi-select and document it as an intentional deviation; make the Formspree endpoint configurable and add HTTP status handling. |
| **D6** | Mobile navigation | PRD §4: 5 destinations (Beranda, Buku Suara, Acara, Profil, Kontak). Current: 6 destinations derived from `NavSection.all`. | **Keep Current** | All 6 content sections must remain reachable; PRD's 5-item list omits Books and is stale. Reference also exposes all sections as anchors. | Keep 6 destinations, update PRD §4/§3 with the exact list and update PLAN. Do not remove a section from the bottom bar; restyle to the reference floating pill with reference-friendly labels. |
| **D7** | Bilingual behavior | Site-wide ID/EN toggle vs current About-only toggle. | **Hybrid** | Site-wide translation would require translating all seed content (tracks, books, events, contact copy) and is not in the reference HTML (Indonesian-only). About-only toggle is working and accessible. | Preserve the About-only toggle for this change request with `role`/`stateDescription`, keep Indonesian primary copy everywhere else, and document site-wide localization plus content ownership as deferred future work in PRD/PLAN. |

**Palette conflict note:** `reference/DESIGN.md` frontmatter defines `primary = #9D422C` and `primary-container = #FF8E72`, while the prose "Palette Architecture" (`:151-156`) calls `#FF8E72` the primary and lists accent hexes not present in the token table. D2 uses the frontmatter table as canonical; the prose is treated as descriptive intent. CR3 must not mix the two without updating this audit.

---

## 3. Gap Checklist

> Kotlin paths are relative to `composeApp/src/commonMain/kotlin/id/naulia/portfolio/` unless prefixed `wasmJsMain/`. `Status` reflects CR1.1 completion only; all implementation work is open.

| ID | Area | Reference | Current | Decision | Files | PRD update | PLAN update | Status |
|---|---|---|---|---|---|---|---|---|
| CR-01 | Color tokens | Full Curated Lumina token family (`DESIGN.md:3-50`) | 11 tokens, amber `#F59E0B`, no container/inverse/error (`ui/theme/LuminaColors.kt:7-21`) | Adopt Reference (D2) | `ui/theme/LuminaColors.kt`, `ui/theme/LuminaTheme.kt`, `ui/theme/PublisherBadgeColor.kt` | §5 replace token table | §1, §3, §10-11 theme tasks | Open → CR3.1 |
| CR-02 | Typography | Outfit display + Hanken Grotesk body/label, leading/tracking (`DESIGN.md:51-117`) | Outfit-only generic SansSerif, no leading/tracking (`ui/theme/LuminaTypography.kt:11-94`) | Adopt Reference (D1) | `ui/theme/LuminaTypography.kt`, `wasmJsMain/ui/theme/FontLoader.kt`, `wasmJsMain/resources/index.html` | §5 font families and scale intent | §1, §4, §10-11 font loading | Open → CR3.1 |
| CR-03 | Dimens / shapes / elevation | 8px base spacing, 8/16/24px + pill radii, 4 elevation levels (`DESIGN.md:118-124,166-197`) | Spacing 4/8/16/24/40/64, radii 16/50/12, no shadow tokens (`ui/theme/LuminaDimens.kt:6-38`) | Adopt Reference | `ui/theme/LuminaDimens.kt`, `ui/theme/Responsive.kt`, `ui/theme/LuminaTheme.kt` | §5 spacing/radius/elevation rhythm | §1, §4 token inventory | Open → CR3.1 |
| CR-04 | Shared primitives | Pill CTA, ghost/icon buttons, status chips, card lift, 52px inputs, eyebrow header (`DESIGN.md:198-219`) | Per-screen styling; no shared button/card/input primitives | Adopt Reference | `ui/components/*`, `ui/theme/*` | §4, §5 component states | §1 component list, §9 phase tasks | Open → CR3.2 |
| CR-05 | Hero | Status pill, reference headline/copy, trust badges, dual CTA, portrait scrim/caption, layered studio card (`code.html:19-80`) | Headline + 3 chips + 2 CTAs + portrait; no studio card (`ui/screens/HeroScreen.kt:135-208`) | Adopt Reference (defer or TODO the studio card) | `ui/screens/HeroScreen.kt`, `ui/components/CredentialChip.kt`, `ui/theme/LuminaTheme.kt` | §3, §4 hero content | §1, §5 hero tasks | Open → CR4.1 |
| CR-06 | Audio | "Karya Pilihan Buku Suara & VO", 2 featured cards with badge/duration/description/play/scrubber/tags; no tabs (`code.html:81-82`) | Header "Buku Suara & Demo VO", 4 filter tabs, 7 tracks, single-active player (`ui/screens/AudioReelScreen.kt:52-94`) | Hybrid (D3) | `ui/screens/AudioReelScreen.kt`, `ui/components/AudioTrackCard.kt`, `ui/components/AudioScrubber.kt`, `viewmodel/AudioViewModel.kt` | §3 copy/ID, §4 filters as deviation | §1, §4, §5 audio tasks | Open → CR4.2 |
| CR-07 | Editorial / books | 6 cards, category filters, age/role/publisher/page metadata (`code.html:83-204`) | 9 cards, no filters, stats row, no page count (`ui/screens/EditorialWorksScreen.kt:34-108`) | Hybrid (D4) | `ui/screens/EditorialWorksScreen.kt`, `ui/components/BookCoverCard.kt`, `data/model/Book.kt`, `data/repository/LocalContentRepository.kt` | §3 book count/filters, §4 filter behavior | §3 `Book` fields, §5 books tasks | Open → CR5.1 |
| CR-08 | MC / stage | Centered header, 4 metrics, 2 wide event cards with venue/audience/role/date/tags (`code.html:205-301`) | 4 metrics, 9 compact event cards/list, different header (`ui/screens/McStageScreen.kt:48-151`) | Adopt Reference | `ui/screens/McStageScreen.kt`, `ui/components/MetricCard.kt`, `ui/components/EventPhotoCard.kt`, `data/model/PortfolioMetrics.kt` | §3 metrics/events composition | §3 model fields, §5 MC tasks | Open → CR5.2 |
| CR-09 | Trusted clients | Logo row: Gramedia, Kompas Edukasi, Kemendikbud Literacy, Mizan Kids (`code.html:302-323`) | Single text credibility line, no component (`ui/screens/McStageScreen.kt:111-120,145`) | Adopt Reference | `ui/screens/McStageScreen.kt`, new `ui/components/TrustedClientRow.kt` | §3 credibility row | §1 new component, §5 MC tasks | Open → CR5.2 |
| CR-10 | About | Two columns: philosophy + quote, 6 capability cards with icon/title/description (`code.html:325-405`) | Narrative + quote + 6 pill surfaces in `FlowRow`; About-only EN toggle (`ui/screens/AboutScreen.kt:44-226`) | Adopt Reference layout; Hybrid for toggle (D7) | `ui/screens/AboutScreen.kt`, `ui/screens/BioStrings.kt`, `ui/icons/LuminaIcons.kt` | §3, §4 bilingual scope | §1, §5 About tasks | Open → CR5.3 |
| CR-11 | Contact form | Name & Instansi, Email or WhatsApp, scope + date, service chips, 24h note, success copy (`code.html:406-494`) | Nama Lengkap, Email/WA, Deskripsi, Pesan Tambahan, different success copy (`ui/screens/ContactScreen.kt:60-293`) | Hybrid (D5) | `ui/screens/ContactScreen.kt`, `data/model/ContactForm.kt`, `viewmodel/ContactViewModel.kt`, `viewmodel/ContactValidation.kt` | §3, §4 fields/validation/CTA states | §3 form model, §5 contact tasks | Open → CR6.1 |
| CR-12 | Contact channels & config | WA, Email, Instagram, LinkedIn; personal data must be config before production (`code.html:422-451`, `PROMPTS.md:271-274`) | WA + Email only; hardcoded `+6281234567890`, `naulia@email.com`, `@nauliazahra`; placeholder Formspree ID (`ui/screens/ContactScreen.kt:158-187,286-290`, `di/AppContainer.kt:30`) | Adopt Reference | `ui/screens/ContactScreen.kt`, `ui/screens/FooterSection.kt`, `platform/PlatformUtils.kt`, `di/AppContainer.kt`, `wasmJsMain/platform/PlatformUtils.kt` | §4 channels, §6 config/env policy | §8, §10-11 integration | Open → CR6.1 |
| CR-13 | Nav / footer / IDs | Reference anchors `voice-audio-reel`, `editorial-portfolio`, `mc-live-stage`, `about-naulia`, `contact`; floating pill nav and richer footer (`code.html:533`) | 6 internal IDs; rectangular top nav + hamburger; 6-destination bottom bar; 3-column footer (`NavSection.kt:6-21`, `TopNavBar.kt`, `BottomTabBar.kt`, `FooterSection.kt`) | Keep Current IDs + mapping table; Adopt Reference styling | `navigation/NavSection.kt`, `navigation/Navigator.kt`, `navigation/SectionScrollCoordinator.kt`, `wasmJsMain/navigation/ScrollObserver.kt`, `ui/components/TopNavBar.kt`, `ui/components/BottomTabBar.kt`, `ui/screens/FooterSection.kt` | §3 ID mapping table, §4 mobile 6 destinations | §5 nav model, §9 a11y | Open → CR6.2 |
| CR-14 | Bilingual scope | Reference copy is Indonesian-only; PRD asks for ID/EN support | About-only local toggle (`AboutScreen.kt:46-66`) | Hybrid (D7) | `ui/screens/AboutScreen.kt`, `ui/screens/BioStrings.kt` | §3/§4 scope and content ownership | §9, §12 localization follow-up | Open → CR5.3, CR2 |
| CR-15 | Asset hygiene | Single canonical WebP asset source; no HEIF in web bundle | 3 duplicate asset trees + unreferenced `.heif` in each (`wasmJsMain/resources/{,assets/}...`) | Adopt Reference | `wasmJsMain/resources/**`, `data/repository/LocalContentRepository.kt`, `.gitignore` | §6 asset format policy | §1, §11 asset/deploy hygiene | Open → CR6.3 |
| CR-16 | Wasm a11y & motion | Visible focus, keyboard control, reduced motion for non-essential animation (`DESIGN.md`, `RULES.md` R3.9/R4, `PROMPTS.md:326-333`) | CSS focus/reduced-motion override exists, but smooth scroll and 20-step navigator animation are unconditional; custom clickable surfaces need roles/state descriptions (`GlobalStyleInjector.kt:9-14`, `Navigator.kt:28-34`, `ScrollObserver.kt:16-17`) | Adopt Reference | `ui/components/*`, `ui/screens/*`, `wasmJsMain/kotlin/.../GlobalStyleInjector.kt`, `navigation/Navigator.kt` | §4 keyboard/focus/reduced-motion specs | §12 a11y checklist | Open → CR7.1 |
| CR-17 | Docs alignment | PRD/PLAN must describe actual code + CR decisions | PRD §6 still specifies vanilla HTML; PLAN phases unchecked; SKILL.md §7 color table conflicts with reference | Adopt Reference decisions | `PRD.md`, `PLAN.md`, `SKILL.md` §7, this file | Whole-document refresh | Whole-document refresh | Open → CR2.1, CR2.2, CR7.2 |

---

## 4. Risk Notes

| Risk | Evidence | Impact | Mitigation / owner phase |
|---|---|---|---|
| **Asset duplication** | Identical audio/image files exist in `wasmJsMain/resources/audio`, `wasmJsMain/resources/assets/audio`, and repo-root `assets/audio`; same for `images/`. Code only fetches `audio/` and `images/` (`LocalContentRepository.kt:19-251`). | Larger WASM distribution, conflicting edits, ambiguous canonical source. | Choose `wasmJsMain/resources/{audio,images}` as canonical; delete duplicates from the web bundle (keep repo-root source assets only if confirmed needed), document generation/copy flow (CR6.3). |
| **`.heif` web compatibility** | `MC_Ulang_Tahun_Penerbit_BIP_34.heif` exists in all three image trees and is unreferenced by seed data. | Browsers generally cannot decode HEIF; if referenced later it renders as a broken/blank image. | Convert to WebP with the same basename, update references if adopted, remove original from the web bundle; keep outside-resources originals until confirmed unused (CR6.3). |
| **Placeholder Formspree endpoint** | `FORM_ENDPOINT = "https://formspree.io/f/YOUR_FORM_ID"` (`AppContainer.kt:30`); POST treats any non-exception as success and does not check HTTP status (`ContactViewModel.kt:73-95`). | Form silently fails in production and reports false success. | Make endpoint/config injectable (env/config, never committed), check `response.status`, distinguish 2xx from error, and test Idle/Loading/Success/Error states (CR6.1, CR7.1). |
| **Hardcoded contact info** | `+6281234567890`, `naulia@email.com`, `@nauliazahra`, "Jakarta & Daring", MC credibility string live in screen source (`ContactScreen.kt:288-290`, `FooterSection.kt:210`, `McStageScreen.kt:145`). Reference HTML uses different values (`+62 812-3456-7890`, `nauliazahra.studio@gmail.com`). | Wrong lead destination and sensitive personal data committed to source; conflict with reference copy. | Move all contact handles/endpoints to one clearly marked config object with verified owner-approved values; no secrets in files or commits (CR6.1). |
| **Wasm a11y / keyboard** | CSS `:focus-visible` exists, but smooth scroll, JS `scrollIntoView`, and the 20-step `Navigator` animation ignore reduced motion; custom `Surface` click targets need `role`/`stateDescription`; scrubber and filter chips need keyboard semantics. | Keyboard users and reduced-motion users hit unfocusable controls or unwanted motion. | Apply R3.9/R4.4, gate all smooth/animated scrolling on `prefersReducedMotion()`, verify focus rings on nav/chips/buttons/inputs/audio controls, and smoke-test in browser (CR6.2, CR7.1). |
| **Duplicate font loading** | Outfit CSS2 link is in `index.html:15` and injected again by `wasmJsMain/.../FontLoader.kt:9-16`; dedup selector only checks its own tag. | Extra network request, possible FOUT/weight mismatch; CR3 must fix while adding Hanken Grotesk. | Single static stylesheet link for both families, with FontLoader dedup against both `data-naulia-font` and existing `link[href*=fonts.googleapis]` (CR3.1). |
| **Reference palette ambiguity** | `DESIGN.md` frontmatter (`:18-21`) vs prose (`:151-156`) disagree on which hex is `primary`. | Theme rebuild could lock in the wrong token semantics. | Use frontmatter as canonical per D2; update CR-AUDIT/PRD if a human owner overrides (CR2.1, CR3.1). |
| **Incomplete model/state contracts** | `AudioTrack` is not `@Serializable`; `Book.synopsis` is never populated; `ContentState.DEFAULT_METRICS` duplicates `PortfolioMetrics`; CMS methods are `TODO("Phase 2 — Remote CMS")`. | Future CMS work and CR model edits may diverge silently. | Keep models `val`-only and `@Serializable` where crossing boundaries; single-source metric values; leave CMS as documented Future in PLAN (CR2.2, CR5.1). |
| **Image loading in Wasm** | `ImageLoader.wasmJs.kt` fetches and Base64-encodes every image before Skia decode with a silent fallback comment. | Large duplicate assets increase memory/startup cost; failures look like empty placeholders. | Canonicalize/downsize assets per R7.2 and surface a visible fallback only if approved; verify no 404s in the dev server (CR6.3, CR7.1). |

---

## 5. Acceptance Criteria

**Visual parity targets**

- [ ] `LuminaColorScheme` exposes every reference token family (primary/on-primary/primary-container, secondary*, tertiary*, surface*/on-surface*, outline*, inverse*, error*) with no ad-hoc hex in screens/components; Material3 `lightColorScheme` mirrors them (`CR-01`, `CR-03`).
- [ ] Outfit is used for display/headline styles and Hanken Grotesk for body/label styles with reference weights, sizes, line-heights and letter-spacing; both load exactly once in the WASM shell (`CR-02`).
- [ ] Section composition matches `code.html`: hero badges + dual CTA + portrait caption (`CR-05`), audio featured cards (`CR-06`), editorial cards with category metadata/filters (`CR-07`), MC metrics + 2 wide event cards + trusted-client row (`CR-08`, `CR-09`), About two-column cards + quote (`CR-10`), reference contact fields/channels/copy (`CR-11`, `CR-12`).
- [ ] Responsive 1/2/3-column grids and mobile/desktop nav render without overflow at the reference breakpoints; desktop top nav is a floating pill and bottom nav has the 6 documented destinations (`CR-13`).
- [ ] Every intentional deviation from the reference (audio filters, 9 books, multi-select service chips, About-only bilingual toggle, 6-destination nav) appears in PRD, PLAN and this audit.

**No regressions**

- [ ] Audio: single-active-player, play/pause, real progress and scrub/seek, end state, category filter reset, equalizer with reduced motion, and keyboard play/pause/seek all still work (`CR-06`).
- [ ] Form: pure validation rules (name, email/WA, at least one service, scope ≥10 chars) still reject invalid input; Loading/Success/Error states render; success uses approved copy; valid submissions reach a configurable endpoint with HTTP status handling (`CR-11`, `CR-12`).
- [ ] Scroll-spy, anchor offsets for the fixed nav, keyboard focus rings, and `prefers-reduced-motion` behavior remain intact after ID/style changes (`CR-13`, `CR-16`).
- [ ] `./gradlew :composeApp:compileKotlinWasmJs` passes after each implementation prompt; `wasmJsBrowserDevelopmentRun` loads images/audio with no console errors or 404s; `wasmJsBrowserDistribution` succeeds.

**Docs updated**

- [ ] `PRD.md` has a Document Status block, reference-backed design system, section ID mapping table, interaction specs, 6-destination mobile nav, bilingual scope, config/asset policy, and Non-goals/Deviations section.
- [ ] `PLAN.md` has verified Current State, actual file tree/models, completed vs in-progress vs CR backlog, asset/deployment hygiene, and the three verification commands.
- [ ] `docs/change-request/CR-AUDIT.md` Status column records the outcome of CR2-CR7, with known limitations and follow-ups.
- [ ] No document claims a feature that is absent from the code.

**CR1.1 exit condition:** this file exists, contains all five required sections, and no app code was modified.

---

## 6. CR1.1 Execution Record

- Mandatory context read: `reference/DESIGN.md`, `reference/code.html`, `PRD.md`, `PLAN.md`, `RULES.md`, `SKILL.md`, plus `docs/change-request/PROMPTS.md`.
- Current implementation inspected: `composeApp/src/commonMain`, `composeApp/src/wasmJsMain`, web resources, build config.
- App code, asset files and build config: **unchanged**.
- Build verification: **not applicable** for this documentation-only prompt (first compile gate applies to CR3.1).
- Next prompt: `CR2.1 — Update PRD.md to Match Reference + Decisions`.
