# Product Requirements Document (PRD) & Project Brief
## Naulia Zahra — Personal Interactive Portfolio & Booking Platform

---

### 1. Executive Summary & Project Overview
- **Project Name:** Naulia Zahra Interactive Portfolio Website
- **Owner / Talent:** Naulia Zahra
- **Core Value Proposition:** Single-page interactive digital home and booking portal showcasing Naulia Zahra’s multidisciplinary expertise as a Children’s & STEM Book Editor, Stage MC/Moderator, and Voice-Over/Audiobook Artist.
- **Key Deliverables:**
  - High-fidelity Responsive Web Experience (Desktop & Mobile optimized viewports)
  - Interactive Audio Demo Showcase with category filtering & live playback scrubber
  - Curated Editorial Book Works Gallery (Children's literature & STEM factual works)
  - Live Stage & Moderation track record with key credibility metrics & photo documentation
  - Direct Booking & Inquiry Lead Capture Engine
  - Bilingual adaptation support (primary current release: Bahasa Indonesia & English)

---

### 2. User Personas & Target Audience
1. **Children’s Book Publishers & Acquisition Editors (e.g., Gramedia, Mizan, BIP, Erlangga)**
   - *Needs:* Fast evaluation of developmental editing skills, age-graded vocabulary competence (Ages 3–12), science fact-checking rigor, and proven collaboration track record.
2. **Event Organizers, Festival Curators & Cultural Institutions (e.g., Kemendikbud, Book Fests, Science Expos)**
   - *Needs:* Verification of stage charisma, crowd management capacity for 500+ attendees, bilingual moderation ability, and audience engagement proof.
3. **Audiobook Producers & Animation Studios**
   - *Needs:* Instant listening access to vocal versatility, character acting range, vocal clarity, and audio production readiness (binaural/mastered demo).

---

### 3. Core Information Architecture & Section Hierarchy

| Section ID | Name (Bahasa Indonesia / English) | Primary Purpose & Key Components |
|---|---|---|
| `#home` | **Hero Section** / *Beranda* | High-impact positioning, portrait asset, credential chips ("Gramedia Book MC", "15+ Titles Edited", "Expressive VO"), primary action CTAs ("Undang untuk Acara / VO", "Jelajahi Demo"). |
| `#featured-audiobook` | **Buku Suara & VO** / *Audio Reel* | Interactive audio demo player with category filters (*Semua*, *Buku Cerita*, *Sains*, *VO Karakter*), waveform/scrub bar, elapsed time, genre tags. |
| `#editorial-works` | **Pilihan Karya Editorial & Buku** / *Editorial Works* | 2x3 responsive book cover showcase with age tags (*Usia 7–12*, *Usia 4–8*), publisher provenance (Gramedia, BIP, Mizan, Erlangga), and specific role badges. |
| `#mc-stage` | **MC Panggung & Moderator Acara** / *Stage & Events* | Quantitative metrics grid (*50+ Acara*, *Mitra Resmi Gramedia*, *10+ Audiobook*, *98% Retensi*), high-res event photography with contextual metadata tags. |
| `#about` | **Filosofi Editorial & Keahlian** / *Philosophy & Bio* | Mission statement quote, editorial philosophy narrative, and 6 core capability pills (Developmental editing, STEM simplification, character VO, stage hosting, etc.). |
| `#contact` | **Formulir Kolaborasi & Reservasi** / *Inquiry Engine* | Multi-select service pills (*Editor Buku*, *MC Acara*, *VO Karakter*, *Kolaborasi Lengkap*), lead form inputs (Name, Email/WhatsApp, Scope), and direct WhatsApp/Email links. |
| Footer | **Navigasi & Hak Cipta** / *Footer* | Quick anchor jump links, agency/studio contact info, copyright, and location tags (*Jakarta & Daring*). |

---

### 4. Interactive Specifications & UX Behaviors
- **Smooth Anchor Scroll Navigation:**
  - Clicking any navigation item smoothly scrolls to the target section with dynamic sticky header offset compensation (`window.scrollTo({ behavior: 'smooth' })`).
  - Active navigation state reflects the current section in view via `IntersectionObserver`.
- **Audio Reel Micro-Interactions:**
  - Single-active-player state: Playing one track pauses all other active players.
  - Live animated visualizer equalizer bars that pulse when audio is in "Playing" state.
  - Interactive scrubber progress bar with updating timestamp counters.
  - Category filter tabs with instant state switching and spring feedback.
- **Mobile Ergonomics:**
  - Sticky bottom tab bar (5 destinations: Beranda, Buku Suara, Acara, Profil, Kontak) designed for one-handed thumb navigation.
  - High-density 2-column grid for book publications and stage metrics to minimize excessive vertical fatigue.

---

### 5. Design System & Visual Guidelines
- **Design System Name:** Curated Lumina (`LIGHT` mode, `#ff8e72` warm coral accent, Outfit typography)
- **Palette Tokens:**
  - **Dominant Tone:** Warm Cream & Soft Sand (`#fff8f3`, `#f9f2ed`, `#ffffff`)
  - **Primary Warm Accent / CTA:** Sunny Yellow / Coral (`#f59e0b` / `#ff8e72`)
  - **Secondary Accents:** Sage Mint / Soft Teal (STEM & Science tags) and Soft Lavender
  - **Typography:** Modern Geometric Sans-serif (`Outfit`), paired with warm serif/italic accents for emotional storytelling hooks.
- **Imagery & Tone:** Human-centric, energetic, friendly, and intellectually rigorous.

---

### 6. Technical Stack & Implementation Requirements
- **Frontend Architecture:** Vanilla Semantic HTML5, modern CSS3 (Tailwind utility system), and lightweight vanilla ES6+ JavaScript.
- **Performance Budget:** 
  - Zero heavy external runtime dependencies (no bulky React/Vue runtimes needed for static single-page portfolio).
  - WebP/SVG optimized visual assets for sub-1.5s First Contentful Paint (FCP).
- **Accessibility (a11y):**
  - Minimum contrast ratio 4.5:1 for all textual elements.
  - Focus visible outlines and keyboard navigability across all interactive buttons and form inputs.
  - Support for `prefers-reduced-motion` media queries.

---

### 7. Milestones & Future Roadmap
1. **Phase 1 (Completed):** Desktop & Mobile visual prototype release with interactive audio playback preview states and smooth animated navigation.
2. **Phase 2 (Completed):** Localization & tone-of-voice alignment into Bahasa Indonesia.
3. **Phase 3 (Next Step):** Webhook / API integration for the collaboration form (e.g., direct WhatsApp API handoff or Formspree/Resend integration).
4. **Phase 4 (Future):** Dedicated case study detail modal overlays for key published books and video reel embeds for live MC events.
