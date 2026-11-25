# Product Requirements Document (PRD) & Project Brief
## Naulia Zahra — Personal Interactive Portfolio & Booking Platform

> **Document Status**
> - **Date:** 2026-11-23
> - **Status:** Active product requirement source; aligned to `reference/*` and the binding decisions in `docs/change-request/CR-AUDIT.md` (D1–D7). Supersedes the pre-implementation PRD.
> - **Visual reference:** `reference/DESIGN.md` + `reference/code.html` — source of truth for visual design, section composition, and component behavior.
> - **Implementation reference:** `PLAN.md` (verified codebase state and completed CR status). Mandatory engineering rules: `RULES.md` R1–R8; patterns: `SKILL.md`.
> - **Deviation policy:** working product behavior (audio playback, scroll-spy, form validation) is preserved unless the reference explicitly replaces it; every intentional deviation is listed in §8.

---

### 1. Executive Summary & Project Overview
- **Project Name:** Naulia Zahra Interactive Portfolio Website
- **Owner / Talent:** Naulia Zahra
- **Core Value Proposition:** Single-page interactive digital home and booking portal showcasing Naulia Zahra’s multidisciplinary expertise as a Children’s & STEM Book Editor, Stage MC/Moderator, and Voice-Over/Audiobook Artist.
- **Key Deliverables:**
  - High-fidelity responsive web experience (desktop/tablet/mobile) built in Kotlin Multiplatform + Compose Multiplatform (`wasmJs` only).
  - Curated Lumina design system with the reference token set (Outfit + Hanken Grotesk; coral/`#9D422C` system).
  - Interactive audio demo showcase with working playback, scrubber and single-active-player behavior; category filters retained as a documented enhancement.
  - Editorial book gallery with category filters and reference-style metadata.
  - MC/live-stage track record with metrics, two featured event showcases and a trusted-client row.
  - Collaboration/booking form with pure validation, configurable endpoint, and four direct channels (WA, Email, Instagram, LinkedIn).
  - Indonesian-primary copy; About-only ID/EN toggle (site-wide localization deferred, see §8).

---

### 2. User Personas & Target Audience
1. **Children’s Book Publishers & Acquisition Editors (e.g., Gramedia, Mizan, BIP, Erlangga)**
   - *Needs:* Fast evaluation of developmental editing skills, age-graded vocabulary competence (ages 3–12), science fact-checking rigor, and proven collaboration track record.
2. **Event Organizers, Festival Curators & Cultural Institutions (e.g., Kemendikbud, book fests, science expos)**
   - *Needs:* Verification of stage charisma, crowd management for 500+ attendees, bilingual moderation ability, and audience engagement proof.
3. **Audiobook Producers & Animation Studios**
   - *Needs:* Instant listening access to vocal versatility, character acting range, vocal clarity, and audio production readiness.

---

### 3. Core Information Architecture & Section Hierarchy

`NavSection.sectionId` values are the reference anchors themselves; there is no separate internal ID set. The same IDs drive Compose scroll targets, hidden DOM anchors, hash deep links, footer links and scroll-spy.

| Section ID (= DOM anchor) | Desktop nav label (ID) | Bottom bar / footer label (ID / EN) | Composition (source: `code.html`) |
|---|---|---|---|
| `#home` | — (wordmark; no Home nav item) | Beranda / Home | Headline + role subheadline + body; dual CTA; trust badges (MC Resmi Gramedia, 15+ Judul Buku Diedit, Narator VO Ekspresif); portrait with gradient scrim and "PANGGUNG & STUDIO" caption. Reference status pill is an empty placeholder comment, so no pill is rendered; layered studio/voice-reel card deferred (CR4.1). |
| `#voice-audio-reel` | Buku Suara & VO | Audio / Audio | Eyebrow "🎙️ REEL SUARA & BUKU SUARA"; title "Karya Pilihan Buku Suara & VO"; 2-column featured cards with category badge, duration, description, play button, scrubber/time labels and tag chip. Category tabs are a documented deviation. |
| `#editorial-portfolio` | Karya Editorial | Karya / Books | Eyebrow "📖 LITERATUR ANAK & SAINS"; title "Pilihan Karya Editorial & Buku"; 1/2/3-column responsive grid; category filter tabs (`Semua/Sains/Buku Cerita/Ensiklopedia`); card metadata = category, age, role, publisher, optional page count. All 9 real books retained (deviation). |
| `#mc-live-stage` | MC & Panggung | Acara / Events | Centered header "Menghangatkan Panggung dengan Antusiasme & Energi"; 4 metric cards (50+ Acara Langsung, Gramedia Mitra Resmi, 10+ Judul Buku Suara, 98% Retensi Klien); 2 wide event showcases (venue/city, audience badge, role/date, description, tags); trusted-client row (Gramedia, Kompas Edukasi, Kemendikbud Literacy, Mizan Kids). |
| `#about-naulia` | Tentang | Profil / Profile | Two columns: philosophy narrative + quote left; 6 capability cards with icon/title/description right. About-only ID/EN toggle retained. |
| `#contact` | Kontak | Kontak / Contact | Service chips; "Nama & Nama Instansi / Penerbit"; "Email atau Nomor WhatsApp"; "Cakupan Proyek & Perkiraan Tanggal"; submit CTA; direct WA/Email/IG/LinkedIn channels; "Respon dijamin dalam 24 jam hari kerja." note. |
| (footer) | — | Navigasi & Hak Cipta / Footer | Brand statement, navigation anchors, contact/social links, location tag and copyright. |

**ID contract:** the six IDs above are canonical everywhere. `ScrollObserver` injects hidden DOM anchors for them and syncs `window.location.hash` via `history.replaceState`; `Navigator` scrolls with fixed-nav offset. No dual-ID mapping exists (updated in CR7.2).

**Acceptance criteria (IA)**
- [x] All six sections and footer render in one scrollable page using the canonical reference anchor IDs/labels above (verified CR7.2).
- [ ] Hero contains the three trust badges and both CTAs; primary CTA targets contact, secondary targets audio/books.
- [ ] MC section contains 4 metrics, 2 featured event cards and the trusted-client row.

---

### 4. Interactive Specifications & UX Behaviors
- **Anchor navigation / scroll-spy:** clicking a nav item scrolls to the target with fixed-nav offset compensation via `SectionScrollCoordinator`; active state comes from a Compose `snapshotFlow` of scroll position (`Navigator.onScrollPositionChanged`), while hidden DOM anchors and `history.replaceState` keep hash deep links working. Smooth scrolling, the 20-step navigator animation and CSS `scroll-behavior` are all gated on `prefers-reduced-motion`.
- **Audio (preserved working behavior):** single-active-player (playing a track pauses the previous one); play/pause with dynamic `contentDescription`; real duration/progress from the HTML audio element; scrubber updates progress and seeks; elapsed/duration labels; equalizer animates only while playing and stops under reduced motion; category tabs reset playback on switch.
- **Books:** category filter tabs derive from the immutable book list, never mutate seed data; switching filters updates the grid immediately; default filter is `Semua`; card hover elevation/lift is non-essential and disabled under reduced motion.
- **Contact chips:** four service chips (`Editor Buku`, `MC Acara / Moderator`, `Voice Over Karakter`, `Kolaborasi Lengkap`); multi-select with selected/unselected styling, `role`/`stateDescription` semantics, and at least one required for valid submission.
- **Contact form:** pure validation (name non-blank; email or `08…`/`+62…` WhatsApp; ≥1 service; scope ≥10 chars) with inline field errors; submission states Idle → Loading → Success/Error; success copy "Pesan Terkirim! Terima Kasih."; endpoint and handles come from `ContactConfig` (runtime override `__NAULIA_CONTACT_CONFIG__`), not screen literals; validation and submission outcomes are announced via polite live regions.
- **Keyboard & focus:** visible focus rings on nav, chips, buttons, inputs and audio controls; play/pause and seek are keyboard operable; all clickable non-button surfaces carry `Role`/`contentDescription`/`stateDescription` per R3.9.
- **Mobile ergonomics:** floating bottom bar with exactly **6** thumb-friendly destinations — `Beranda`, `Audio`, `Karya`, `Acara`, `Profil`, `Kontak` (labels from `NavSection.all`), while desktop shows five longer nav labels plus an invite CTA. No section is removed to satisfy the older 5-destination draft. Book grid uses 1/2/3 columns on mobile/tablet/desktop; metric grid uses 2×2 on mobile and 4 columns on desktop.

**Acceptance criteria (interaction — code-verified in CR7.1; browser smoke remains manual)**
- [x] Audio: one active player; scrub/seek, end state (clears `activeTrackId`), filter reset, equalizer + reduced motion. Keyboard control requires manual browser confirmation.
- [x] Form: invalid input is rejected by pure validation; Loading/Success/Error render with live-region announcements; external links open via `PlatformUtils` with `noopener,noreferrer`.
- [x] Active nav matches the visible section via scroll snapshot flow; focus rings visible on all interactive surfaces; reduced motion disables non-essential animation.

---

### 5. Design System & Visual Guidelines ("Curated Lumina")

Reference frontmatter (`reference/DESIGN.md:3-135`) is canonical; the prose "Palette Architecture" (`:146-164`) is descriptive intent for ambience/accents. Do not mix the two without updating `CR-AUDIT.md` (D2).

**Color tokens** (frontmatter kebab-case → planned `LuminaColorScheme` / Material3 field names):

| Token family (`DESIGN.md`) | Hex values | Use |
|---|---|---|
| `primary`, `on-primary`, `primary-container`, `on-primary-container`, `inverse-primary` | `#9D422C`, `#FFFFFF`, `#FF8E72`, `#762612`, `#FFB4A2` | Primary CTAs, active indicators, statement surfaces |
| `secondary*` | `#735C00`, `#FFFFFF`, `#FECF27`, `#705900` | Highlights, spotlight badges, celebratory accents |
| `tertiary*` | `#006C52`, `#FFFFFF`, `#55C09C`, `#004B38` | Availability/success tags, interactive category tags |
| `error*` | `#BA1A1A`, `#FFFFFF`, `#FFDAD6`, `#93000A` | Error states and validation messaging |
| `surface*` | `surface #FFF8F3`, `dim #DFD9D4`, `bright #FFF8F3`, `container-lowest #FFFFFF`, `container-low #F9F2ED`, `container #F3EDE7`, `container-high #EDE7E2`, `container-highest #E7E1DC`, `variant #E7E1DC` | Page, card and nested-container planes |
| `on-surface`, `on-surface-variant`, `inverse-surface`, `inverse-on-surface`, `surface-tint` | `#1D1B18`, `#56423e`, `#32302D`, `#F6F0EA`, `#9D422C` | Text on surfaces, scrim captions, tinting |
| `outline`, `outline-variant`, `background`, `on-background` | `#89726D`, `#DCC0BA`, `#FFF8F3`, `#1D1B18` | Hairline borders, canvas |
| `*-fixed` families | primary `#FFDAD2/#FFB4A2/#3C0700/#7E2C17`; secondary `#FFE087/#EFC112/#231A00/#574500`; tertiary `#8CF6D0/#70DAB4/#002117/#00513D` | High-emphasis tonal containers |

**Typography** — Outfit for display/headline; Hanken Grotesk for body/label; fallback-safe families; line-height and tracking are part of the token, not optional:

| Style token | Family | Size / Weight | Line height / Tracking | M3 mapping (intent) |
|---|---|---|---|---|
| `display-hero` / `-mobile` | Outfit | 4.5rem / 600 · 2.75rem / 600 | 1.05 / −0.03em · 1.1 / −0.02em | `displayLarge` (hero) |
| `headline-lg` / `-mobile` | Outfit | 3rem / 600 · 2rem / 600 | 1.15 / −0.025em · 1.2 / −0.02em | `headlineLarge` |
| `headline-md` | Outfit | 2rem / 500 | 1.25 / −0.015em | `headlineMedium` |
| `headline-sm` | Outfit | 1.375rem / 500 | 1.3 / −0.01em | `titleLarge` |
| `body-lg` | Hanken Grotesk | 1.125rem / 400 | 1.6 / −0.005em | `bodyLarge` |
| `body-md` | Hanken Grotesk | 1rem / 400 | 1.55 / 0 | `bodyMedium` |
| `body-sm` | Hanken Grotesk | 0.875rem / 400 | 1.5 / 0.005em | `bodySmall` |
| `label-md` | Hanken Grotesk | 0.8125rem / 600 | 1.2 / 0.04em | `labelLarge` |
| `label-sm` | Hanken Grotesk | 0.6875rem / 700 | 1.1 / 0.06em | `labelSmall` |

**Spacing / layout rhythm:** 8px-based internal rhythm using reference tokens `space-xs 6px`, `space-sm 12px`, `space-md 20px`, `space-lg 32px`, `space-xl 56px`; gutters 24px desktop / 16px mobile; margins 48px desktop / 20px mobile. 12-column desktop grid (max width 1280px), 6-column tablet, 4-column mobile.

**Radii:** `sm 4px`, `DEFAULT 8px` (inputs, small buttons), `md 12px`, `lg 16px` (secondary cards, media), `xl 24px` (hero/featured cards, panels), `full 9999px` (pills, tags, primary CTAs) — exposed via `LuminaDimens`.

**Elevation (Compose equivalents of reference levels):** L0 flat warm surface; L1 white card + 1px `#F0EDE4` hairline + soft warm shadow `0 4px 16px -2px rgba(28,26,23,0.04), 0 1px 3px 0 rgba(28,26,23,0.02)`; L2 hover/active lift 2–4px with coral-tinted `0 12px 28px -4px rgba(255,142,114,0.12), 0 4px 8px -2px rgba(28,26,23,0.03)`; L3 floating/pill nav `rgba(255,255,255,0.88)`, 12px blur, `#F0EDE4` border, `0 20px 40px -8px rgba(28,26,23,0.08)`.

**Components:** pill primary CTA (Hover scale ~1.02 + coral shadow), secondary/ghost button, 44px circular icon button, status/case-study/spotlight pills, white 24px-radius cards with nested 16px media radius and hover lift, 52px text inputs with 8px radius and coral focus ring. No hardcoded hex, font size or spacing in screens/components (R3.7).

**Acceptance criteria (design — verified CR7.1)**
- [x] `LuminaColorScheme` + Material3 scheme expose every family above; no ad-hoc old-palette hex in touched theme files.
- [x] Both fonts load exactly once in the WASM shell (static link + deduping injector); display vs body/label styles follow the table.
- [x] Shared primitives are used by screens; focus/contrast/reduced-motion states implemented (CR7.1 closed the remaining gaps).

---

### 6. Technical Stack & Integration Requirements
- **Architecture:** KMP `commonMain` (models, repository, ViewModels, UI, theme, `expect` contracts) + `wasmJsMain` (JS interop, `actual` implementations, web resources). Web-only, single `wasmJs` browser target; manual DI via `AppContainer`.
- **Dependencies (as built):** Kotlin 2.1.0, Compose Multiplatform 1.7.3, Ktor 3.0.3 (`core`, `content-negotiation`, `js`, `serialization-json`), coroutines 1.9.0, serialization 1.7.3. Do not add runtime dependencies without R7.1 justification.
- **Form backend — PLACEHOLDER:** `ContactConfig` defaults to `https://formspree.io/f/YOUR_FORM_ID`; `ContactViewModel` treats it as unconfigured, POSTs with `expect/actual` Ktor client, accepts only 2xx and exposes Idle/Loading/Success/Error. Owner must inject a real endpoint via `__NAULIA_CONTACT_CONFIG__` (never commit it, R8.3).
- **Direct channels — PLACEHOLDER:** WA/Email/IG/LinkedIn links open through `PlatformUtils` (`expect`/`actual`, `noopener,noreferrer`). Handles live in `ContactConfig`; the shipped defaults are samples and IG/LinkedIn URLs are empty (`#` fallback) until owner-supplied. No secrets in source or commits.
- **Asset policy:** one canonical web asset source at `composeApp/src/wasmJsMain/resources/{audio,images}`; relative paths `audio/…` and `images/…` per R5.4. Images WebP (SVG for icons); **no HEIF in the web bundle unless converted to WebP**. Duplicate `resources/assets` trees were removed in CR6.3 (repo-root source assets retained); CR7.1 verified 33/33 referenced assets in the distribution and zero HEIF. Fonts: Outfit + Hanken Grotesk loaded once via `index.html` with fallback families.
- **Accessibility:** ≥4.5:1 text contrast, visible focus, keyboard operability and `prefers-reduced-motion` support across all interactive surfaces (R3.9, R4).
- **Content ownership:** Indonesian copy is primary and owned by the talents/owner; English text exists only for About toggles. Any future site-wide translation requires owner-reviewed content before rollout.

---

### 7. Milestones & Roadmap
1. **Completed (pre-CR):** KMP scaffold + single `wasmJs` target; six-section Compose UI; 11-token Lumina theme; 7 audio tracks with single-active-player/scrubber; 9 books; 9 events + 4 metrics; About ID/EN toggle; contact form with pure validation and Ktor POST scaffold; local `LocalContentRepository`; scroll-spy and WASM build/deploy pipeline.
2. **Completed (CR1, 2026-11-23):** `docs/change-request/CR-AUDIT.md` records scope, D1–D7 decisions, gap checklist, risks and acceptance criteria; no app code changed.
3. **Completed (CR2, 2026-11-23):** PRD (this document) and `PLAN.md` aligned to reference + audit decisions; documentation-only, no runtime behavior changed.
4. **Completed (CR3, 2026-11-23):** full Curated Lumina token system plus shared button/card/chip/input primitives.
5. **Completed (CR4, 2026-11-23):** hero reference copy/badges/portrait caption and audio reference cards with playback behavior preserved.
6. **Completed (CR5, 2026-11-23):** editorial filters + reference metadata/grid for all 9 books; MC centered header, 4 metric cards, 2 wide event showcases and text/icon trusted-client row; About two-column layout with 6 capability cards, quote block and accessible About-only ID/EN toggle. `pageCount` is a nullable model field with no verified seed values, so cards fall back to publisher/year metadata. `compileKotlinWasmJs` and `wasmJsBrowserDistribution` pass.
7. **Completed (CR6, 2026-11-23):** reference contact fields/channels/config guard, floating pill nav + 6-destination bottom bar + reference anchors/footer, canonical WebP assets with duplicate/HEIF cleanup.
8. **Completed (CR7, 2026-11-23):** compile + distribution pass, 33/33 referenced assets resolve, remaining a11y/motion/contrast/semantics/font-dedup defects fixed; results recorded in `CR-AUDIT.md` §7–§8; PRD/PLAN/SKILL/changelog synced.
9. **Future / deferred:** owner-supplied Formspree endpoint + verified contact handles; CMS admin UI + remote content; case-study detail overlays; site-wide ID/EN localization with owner-owned translations; hero layered studio card (CR4.1); manual browser screenshot/keyboard/screen-reader smoke.

---

### 8. Non-goals / Deviations from Reference

Binding decisions from `docs/change-request/CR-AUDIT.md`; rationale is recorded there and must not be re-litigated in code.

| Decision | Deviation from reference | Scope for this change request |
|---|---|---|
| D1 / D2 — Adopt Reference | None intended. Replaces Outfit-only type and the 11-token old palette with the full Curated Lumina system. | CR3.1 |
| D3 — Hybrid (Audio) | Category filter tabs retained although reference has no tabs. Reference heading, 2-card composition, metadata and reduced-motion rules are adopted. | CR4.2 |
| D4 — Hybrid (Editorial) | 9 real local books retained instead of the reference’s 6 illustrative cards; stats row removed. Category filters and 1/2/3-column grid adopted. `pageCount` added as `Int?`, but no verified seed values exist, so cards show publisher/year fallback; omission documented. | CR5.1 |
| D5 — Hybrid (Contact) | Multi-select service chips retained (reference script is single-select); pure validation and Ktor submission scaffold preserved; reference fields/copy/channels/24h note adopted. | CR6.1 |
| D6 — Keep Current (Mobile nav) | 6 bottom-bar destinations (`Beranda/Audio/Karya/Acara/Profil/Kontak`) instead of the stale PRD 5-item draft; reference floating-pill styling adopted. Section IDs are the reference anchors, not legacy internal IDs. | CR6.2, verified CR7.2 |
| D7 — Hybrid (Bilingual) | ID/EN toggle remains About-only; reference is Indonesian-only. Site-wide localization and content ownership are future work. | CR5.3, future |
| Deferred | Hero layered studio/voice-reel card (CR4.1); trusted-client row uses text/icon badges because no logo assets are available (CR5.2); remote CMS/admin and case-study modals; owner-supplied endpoint/handles and manual browser smoke (CR7.1). | CR4.1 / CR5.2 / CR7 / future |

**Non-goals:** no Android/iOS targets, no pixel-cloning of the reference HTML, no new runtime dependencies without justification, no committing endpoints/keys/contact secrets, no rewriting working audio/form behavior except where the reference explicitly replaces it.

**Final doc acceptance (CR7.2, verified 2026-11-23)**
- [x] This PRD contains a Document Status block, reference-backed §5 tokens, §3 canonical ID table, §4 interaction specs, 6-destination mobile nav, bilingual scope, §6 config/asset policy and §8 deviations.
- [x] No statement claims a feature absent from the code; CR1–CR7 are marked complete and deferred items are explicitly listed in §7/§8. QA evidence lives in `docs/change-request/CR-AUDIT.md` §7.
