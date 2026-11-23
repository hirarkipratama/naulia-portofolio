# PROMPTS.md — Curated Lumina Redesign & Doc Alignment Change Request

> **Scope:** Align existing KMP + Compose/Wasm implementation, `PRD.md`, and `PLAN.md` with `reference/DESIGN.md` and `reference/code.html`.  
> **Execute sequentially.** Each prompt is self-contained; verify before moving on.

---

## ⚠️ MANDATORY CONTEXT

Read before every prompt:

| Document | Path | Governs |
|---|---|---|
| **Reference Design Spec** | [`../../reference/DESIGN.md`](../../reference/DESIGN.md) | Curated Lumina tokens, typography, layout, elevation, shapes, components |
| **Reference HTML** | [`../../reference/code.html`](../../reference/code.html) | Target section structure, copy intent, interactions, content hierarchy |
| **PRD.md** | [`../../PRD.md`](../../PRD.md) | Product requirements (must be updated, not blindly followed) |
| **PLAN.md** | [`../../PLAN.md`](../../PLAN.md) | Architecture/phase plan (must be updated to match reality) |
| **RULES.md** | [`../../RULES.md`](../../RULES.md) | Mandatory Kotlin/KMP/Compose rules |
| **SKILL.md** | [`../../SKILL.md`](../../SKILL.md) | Patterns, anti-patterns, build commands |

> [!IMPORTANT]
> **Source-of-truth decision:** For visual design, section composition, and component behavior, `reference/DESIGN.md` + `reference/code.html` win. For existing working product behavior, preserve unless reference explicitly replaces it; document any intentional deviation in PRD/PLAN.

### Pre-Execution Checklist
- [ ] Read relevant reference sections (`DESIGN.md` tokens/components, `code.html` section markup).
- [ ] Confirm impacted files in `composeApp/src/commonMain` and `wasmJsMain`.
- [ ] Follow RULES.md: no `!!`, no hardcoded theme values, state hoisting, `expect/actual` isolation.
- [ ] After each implementation prompt: `./gradlew :composeApp:compileKotlinWasmJs` passes.
- [ ] After UI changes: verify desktop + mobile layout and reduced-motion behavior.

---

## Gap Matrix (must be addressed)

| Area | Reference | Current | Required Change |
|---|---|---|---|
| Design tokens | Outfit + Hanken Grotesk, coral `#FF8E72/#9D422C` system, detailed spacing/radius/elevation | Outfit-only, different palette/scale | Rebuild theme tokens to match reference; map to Material3/LocalLumina |
| Hero | Status context, trust badges, portrait stage, layered voice-reel/studio card, dual CTA | Headline + chips + portrait + 2 CTAs | Add missing hero subcomponents/content; keep responsive behavior |
| Audio/VO | “Reel Suara & Buku Suara” featured cards, scrubber, tags; no category tabs in reference | Category-filtered interactive player list | Decide parity: keep filters as enhancement or match reference; document in PRD/PLAN and implement consistently |
| Editorial/Books | 6 book cards, category/filter comment, age/publisher/role metadata | 9 books, no filters, stats row | Add filter/tabs or documented deviation; ensure card metadata matches reference style |
| MC/Stage | 4 metrics, 2 event showcase cards, trusted client logo row | Metrics + event cards/list, text credibility line | Add trusted client/logo row component and reference-aligned card layout |
| About | Philosophy + 6 capabilities grid, quote | Philosophy + bilingual toggle + capabilities pills | Preserve bilingual toggle if desired, but align layout/copy structure to reference; document scope |
| Contact | Service chips, Name+Instansi, Email/WA, scope+date, WA/email/IG/LinkedIn, 24h note | Name, contact, scope, optional message, WA/email only, multi-select | Align form fields/channels/validation/CTA feedback to reference; update model/ViewModel |
| Nav/Footer | Reference anchor IDs/labels differ | `NavSection` 6 items, bottom tab mobile | Reconcile IDs/labels/mobile nav with PRD; keep scroll-spy/a11y intact |
| Docs | PRD/PLAN stale/incomplete | Implemented code diverged | Update PRD/PLAN with actual state, deltas, decisions, roadmap |

---

## ═══ PHASE CR1 — AUDIT & SOURCE-OF-TRUTH DECISIONS ═══

### PROMPT CR1.1 — Create Change-Request Audit Doc

> **RULES:** R5.3 no magic decisions hidden in code; document clearly.  
> **Refs:** PRD §3-7, PLAN §1-12, reference files.

```
Create `docs/change-request/CR-AUDIT.md` in this repo.

Include:
1. **Scope & Sources**: list `reference/DESIGN.md`, `reference/code.html`, `PRD.md`, `PLAN.md`, and current implementation paths.
2. **Decision Log (required)** — for each, mark `Adopt Reference`, `Keep Current`, or `Hybrid`, with rationale:
   - Typography: Outfit+Hanken Grotesk vs Outfit-only.
   - Color tokens: reference palette vs current Lumina colors.
   - Audio section: reference static featured cards vs current filterable player.
   - Editorial section: 6-card+filters vs current 9-card no-filter grid.
   - Contact form schema: reference fields/channels vs current ContactForm.
   - Mobile navigation: PRD 5-destination bottom bar vs current 6-destination.
   - Bilingual behavior: site-wide vs About-only toggle.
3. **Gap Checklist** table with columns: `ID`, `Area`, `Reference`, `Current`, `Decision`, `Files`, `PRD update`, `PLAN update`, `Status`.
4. **Risk notes**: asset duplication, `.heif` web compatibility, placeholder Formspree endpoint, hardcoded contact info, Wasm a11y/keyboard.
5. **Acceptance criteria**: visual parity targets, no regressions in audio playback/form validation, docs updated.

Do not change app code in this prompt. Keep it factual and actionable.
```

---

## ═══ PHASE CR2 — PRD & PLAN ALIGNMENT ═══

### PROMPT CR2.1 — Update PRD.md to Match Reference + Decisions

> **RULES:** R8.1 docs changes; be precise.  
> **Refs:** `CR-AUDIT.md` decisions, `reference/DESIGN.md`, `reference/code.html`.

```
Update `PRD.md` so it is the current product requirement source.

Required updates:
1. Add a **Document Status** block at top: date, status, and "Visual reference: reference/DESIGN.md + reference/code.html".
2. **Design System (§5)**: replace outdated tokens with Curated Lumina reference tokens (colors, typography families/scales intent, radii, elevation, spacing rhythm). Keep token names aligned with implementation plan.
3. **Core IA (§3)**: reconcile section IDs/names/content with chosen decision. If keeping current IDs, add explicit mapping table to reference IDs (`voice-audio-reel`, `editorial-portfolio`, `mc-live-stage`, `about-naulia`, `contact`).
4. **Interactive Specs (§4)**: include audio scrubber/equalizer/single-active-player, book filter behavior (if adopted), contact chip behavior, scroll-spy, reduced motion, keyboard/focus requirements.
5. **Mobile ergonomics**: resolve 5 vs 6 bottom-nav destinations; document exact destinations.
6. **Bilingual**: specify scope (site-wide or About-only) and content ownership.
7. **Technical/Integration (§6-7)**: mark Formspree/WA integration as configured/placeholder with env/config requirement; add asset format policy (WebP preferred, no HEIF in web bundle unless converted).
8. Add **Non-goals / Deviations from reference** section for any intentional Hybrid/Keep Current decisions.

Keep PRD concise and testable; use bullet acceptance criteria where useful.
```

### PROMPT CR2.2 — Update PLAN.md to Reflect Actual Codebase + New Work

> **Refs:** actual `composeApp/src` tree, `CR-AUDIT.md`, updated PRD.

```
Update `PLAN.md` to be an execution plan, not stale scaffold.

Required updates:
1. Add **Current State (as of 2026-05-23)** section: what is implemented (theme, nav, screens, audio player, CMS local repo, contact validation/submission scaffold, wasm build).
2. Fix **Project Structure (§1)** to match actual files/packages. Remove nonexistent planned files or mark them Future. Include actual files: `BioStrings.kt`, `LuminaDimens.kt`, `Responsive.kt`, `PublisherBadgeColor.kt`, `SectionScrollCoordinator.kt`, `ScrollBridge.kt`, `image/ImageLoader*`, `GlobalStyleInjector.kt`, `wasmJsMain/resources/*`.
3. Update **Data Models (§3)** with current fields (`AudioTrack.coverAsset`, `Book.synopsis`, `McEvent.city`, `PortfolioMetrics` if present).
4. Update **Dependencies/Build (§10-11)** to current versions and actual commands.
5. Replace Phase checklists with: `Completed`, `In Progress`, `Change Request Backlog`. Add CR phases: CR1 Audit, CR2 Docs, CR3 Theme, CR4 Hero/Audio, CR5 Books/MC/About, CR6 Contact/Nav/Footer, CR7 QA/Deploy.
6. Add **Asset & Deployment Hygiene**: single canonical asset source, convert/remove `.heif`, dedupe audio/images, verify `composeApp` wasm output.
7. Add explicit test/verification commands:
   - `./gradlew :composeApp:compileKotlinWasmJs`
   - `./gradlew :composeApp:wasmJsBrowserDevelopmentRun`
   - `./gradlew :composeApp:wasmJsBrowserDistribution`

Do not mark work complete unless verified in code.
```

---

## ═══ PHASE CR3 — DESIGN SYSTEM PARITY ═══

### PROMPT CR3.1 — Implement Curated Lumina Theme Tokens

> **RULES:** R3.7 no hardcoded colors/sizes, R1.2 immutability, R5.1 package structure.  
> **Refs:** `reference/DESIGN.md:3-219`, `LuminaTheme.kt`, `LuminaColors.kt`, `LuminaTypography.kt`, `LuminaDimens.kt`.

```
Update the Compose theme to match `reference/DESIGN.md` Curated Lumina.

Files: `composeApp/src/commonMain/kotlin/id/naulia/portfolio/ui/theme/*`

1. **Colors**: map reference tokens (`primary`, `on-primary`, `primary-container`, `secondary*`, `tertiary*`, `surface*`, `outline*`, `inverse*`, `error*`) into `LuminaColorScheme` and Material3 `lightColorScheme`. Preserve semantic names; avoid ad-hoc hex in screens/components.
2. **Typography**: add Hanken Grotesk for body/label and keep Outfit for display/headline per reference. Provide fallback-safe font families. Define styles matching reference scale intent (`display-hero`, `headline-*`, `body-*`, `label-*`) while keeping Material3 typography mapping.
3. **Dimens/Shapes**: add spacing/radius/elevation tokens from reference (8px base, 16px medium, 24px large, pill, shadow levels as Compose equivalents). Avoid magic numbers in components.
4. **Theme API**: expose tokens via `LocalLuminaColors`/theme objects; ensure existing components compile.
5. **Wasm font loading**: update `index.html`/font injector to load Outfit + Hanken Grotesk weights used.

Acceptance: no hardcoded old palette in touched theme files; app compiles.
```

### PROMPT CR3.2 — Component Visual Primitives

> **Refs:** `reference/DESIGN.md` Components/Elevation/Shapes.

```
Refactor shared UI primitives to Curated Lumina component rules.

Files: `ui/components/*`, `ui/theme/*`

1. **Buttons/Chips**: create or update reusable components for Primary CTA pill, Secondary/Ghost, Icon button, status/badge chips with reference states (hover/scale/shadow/border).
2. **Cards**: unify card surface, border, radius, hover lift/shadow behavior; respect `prefersReducedMotion`.
3. **Inputs**: update `OutlinedTextField`/custom field styling for 52px height feel, 8px radius, focus ring/shadow equivalent.
4. **Section header/tag**: match reference eyebrow pill + headline + subtitle pattern.
5. Remove duplicated styling from screens; screens consume primitives.

Verify accessibility: focus visible, contrast-safe text colors, semantics preserved.
```

---

## ═══ PHASE CR4 — HERO & AUDIO PARITY ═══

### PROMPT CR4.1 — Hero Redesign

> **Refs:** `reference/code.html` hero, `HeroScreen.kt`.

```
Update `HeroScreen.kt` to align with reference hero composition.

Implement:
- Eyebrow/status pill if present in reference (or document omission).
- Headline/subheadline/body copy structure; keep Indonesian primary copy from reference where appropriate.
- Trust/credibility badges row (Gramedia MC, 15+ Judul Buku Diedit, Narator VO Ekspresif) with icons and surface styling.
- Primary CTA: "Undang untuk Acara / VO" -> Contact. Secondary CTA: "Jelajahi Buku & Demo Audio" -> Audio/Books per decision.
- Portrait container with gradient/scrim and caption overlay.
- Optional layered studio/voice-reel floating card if adopted; if deferred, add TODO with CR ID and PRD note.

Keep responsive mobile/desktop layout and reduced-motion-safe animations.
```

### PROMPT CR4.2 — Audio Reel Section Alignment

> **Refs:** `reference/code.html:82`, `AudioReelScreen.kt`, `AudioTrackCard.kt`, `AudioViewModel.kt`.

```
Align audio section with reference and CR decision.

If adopting reference:
- Rename section heading/copy to "Karya Pilihan Buku Suara & VO" style.
- Use featured card layout (2-col desktop) with category badge, duration, description, play button, scrubber, time labels, tag chip.
- Keep single-active-player and real progress/scrub behavior.

If hybrid:
- Preserve category filter tabs but style them per Curated Lumina and place them below/near header as reference-friendly enhancement.
- Document deviation in PRD/PLAN.

Do not regress: keyboard play/pause/seek semantics, reduced-motion equalizer, audio asset loading.
```

---

## ═══ PHASE CR5 — BOOKS, MC, ABOUT PARITY ═══

### PROMPT CR5.1 — Editorial Works Section

> **Refs:** `reference/code.html` editorial section, `EditorialWorksScreen.kt`, `BookCoverCard.kt`.

```
Update editorial/books section per reference decisions.

- Match header eyebrow/title/subtitle.
- Add category filter tabs if adopted (`Semua/Sains/Buku Cerita/Ensiklopedia` or mapped from model). Implement filtering in UI/ViewModel without mutating data.
- Use reference-aligned card metadata: category/tag, age, role, publisher, page count if available; if page count not in model, add optional field in `Book` + seed data + PRD/PLAN update.
- Grid: 1/2/3 columns responsive; card hover lift/shadow per reference.
- Keep existing real assets; dedupe/canonicalize paths.

Add/adjust tests or pure filter functions where practical.
```

### PROMPT CR5.2 — MC/Stage + Trusted Clients

> **Refs:** `reference/code.html` MC section, `McStageScreen.kt`, `EventPhotoCard.kt`.

```
Align MC & Live Stage section to reference.

- Header with eyebrow/title/subtitle centered as reference.
- 4 metric cards with reference styling.
- 2-column featured event showcase cards on desktop; responsive stack on mobile. Include venue/city, audience badge, role/date, description, tags.
- Add **Trusted Client Logo Row** component below events (Gramedia, Kompas Edukasi, Kemendikbud Literacy, Mizan Kids or data-driven list). Use text+icon badges if logos unavailable; note asset needs.
- Keep metrics/events sourced from `ContentRepository`.

Ensure semantic descriptions for event photos and metric values.
```

### PROMPT CR5.3 — About Section

> **Refs:** `reference/code.html` about section, `AboutScreen.kt`, `BioStrings.kt`.

```
Align About section while preserving approved enhancements.

- Match two-column layout: philosophy narrative + quote block left, capabilities grid right.
- Convert capability pills to reference capability cards/grid (icon, title, description) unless decision keeps pills; document.
- Keep bilingual toggle only if CR decision says Hybrid/Keep Current; ensure it affects all relevant About copy and is accessible (`role`, `stateDescription`).
- Use reference copy tone; avoid placeholder/lorem.

Update `BioStrings`/models as needed with immutable data.
```

---

## ═══ PHASE CR6 — CONTACT, NAV, FOOTER, ASSETS ═══

### PROMPT CR6.1 — Contact Form & Channels

> **Refs:** `reference/code.html` contact, `ContactScreen.kt`, `ContactViewModel.kt`, `ContactForm.kt`, `PlatformUtils.kt`.

```
Align contact/collaboration section with reference.

- Form fields: Name & Instansi/Penerbit, Email or WhatsApp, Project scope & estimated date/message. Add fields to `ContactForm` and validation rules; keep validation pure/testable.
- Service chips: match reference labels/selected styling. Decide single-select vs multi-select; implement and document.
- Submit CTA: loading/success/error states; success copy "Pesan Terkirim..." or approved equivalent.
- Direct channels: WhatsApp, Email, Instagram, LinkedIn. Use `PlatformUtils` expect/actual for external links.
- Add "Respon dijamin dalam 24 jam hari kerja" note if kept.
- Remove placeholder personal data before production; move endpoint/contact handles to config/constants clearly marked.

Do not commit secrets; Formspree ID must be configurable.
```

### PROMPT CR6.2 — Navigation, Footer, and Section IDs

> **Refs:** `NavSection.kt`, `TopNavBar.kt`, `BottomTabBar.kt`, `FooterSection.kt`, `ScrollObserver.kt`, `SectionScrollCoordinator.kt`.

```
Reconcile navigation with reference and PRD.

- Map/rename sections as decided (keep internal IDs stable if needed, but expose correct labels and anchor behavior).
- Desktop nav: reference floating/pill style and active state.
- Mobile nav: implement decided destination count and labels; ensure thumb-friendly tap targets.
- Footer: align columns/content/social/contact/location/copyright with reference.
- Scroll-spy: ensure IntersectionObserver/scroll coordinator uses final IDs; smooth scroll offset accounts for fixed nav.

Verify keyboard navigation and visible focus rings.
```

### PROMPT CR6.3 — Asset Hygiene & Web Compatibility

> **Refs:** PLAN asset rules, `composeApp/src/wasmJsMain/resources`.

```
Clean up assets for web delivery.

1. Pick one canonical asset location for wasm resources; remove duplicates or document generation flow.
2. Convert `.heif` event images to `.webp`; update references in data/repo.
3. Ensure audio/image paths resolve in wasm build (`assets/...` vs `images/...` consistency).
4. Optimize large images/audio where possible; keep quality acceptable.
5. Add/update `.gitignore` only if needed; do not delete source assets outside resources unless confirmed unused.

Confirm `wasmJsBrowserDevelopmentRun` loads images/audio without 404s.
```

---

## ═══ PHASE CR7 — QA, A11Y, AND FINAL DOC PASS ═══

### PROMPT CR7.1 — Verification & Accessibility

> **RULES:** R3.9 a11y, R7 performance, R6 quality.  
> **Refs:** SKILL build commands.

```
Run verification and fix issues found.

Commands:
- `./gradlew :composeApp:compileKotlinWasmJs`
- `./gradlew :composeApp:wasmJsBrowserDevelopmentRun` (manual smoke)
- `./gradlew :composeApp:wasmJsBrowserDistribution`

Checks:
- Desktop/mobile responsive layout at reference breakpoints.
- Active nav/scroll-spy correctness after ID/label changes.
- Audio: single active player, scrubber, play/pause, end state, keyboard control.
- Contact: validation, loading, success, error, external links.
- Reduced motion disables non-essential animation.
- Focus visible on nav, chips, buttons, inputs, audio controls.
- No console errors/404s for assets.

Record results in `docs/change-request/CR-AUDIT.md` Status column.
```

### PROMPT CR7.2 — Final PRD/PLAN/Changelog Sync

```
After implementation, update docs to match final code.

- `PRD.md`: mark implemented vs deferred items; update roadmap/milestones.
- `PLAN.md`: check completed CR backlog items; add remaining future work.
- `docs/change-request/CR-AUDIT.md`: final decision/status table, known limitations, follow-ups.
- Optional: add `docs/change-request/CHANGELOG.md` summarizing user-visible changes.

Ensure no doc claims features that are not implemented.
```

---

## Quick Execution Order

```text
CR1.1 Audit & decisions
CR2.1 PRD update
CR2.2 PLAN update
CR3.1 Theme tokens
CR3.2 Component primitives
CR4.1 Hero
CR4.2 Audio
CR5.1 Books
CR5.2 MC/Stage
CR5.3 About
CR6.1 Contact
CR6.2 Nav/Footer/IDs
CR6.3 Assets
CR7.1 QA/A11y
CR7.2 Final doc sync
```

## Notes for Executor

- **Do not blindly pixel-clone** reference HTML; implement equivalent Compose/Wasm components following RULES.md.
- **Preserve working behavior** (audio playback, scroll-spy, form validation) unless explicitly replaced.
- **Document every deviation** from reference in PRD/PLAN/CR-AUDIT.  
- **Prefer small commits** per CR prompt (`docs(change-request): ...`, `feat(theme): ...`, etc.).  
