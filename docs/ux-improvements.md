# UX Improvements & Shortcomings

## List / Grid Layout

### Grid vs List
- Swap `LazyColumn` for `LazyVerticalGrid` with `GridCells.Adaptive(minSize)` — adapts to screen width and handles landscape/tablets naturally
- Offer a toggle between list and grid in the top bar, persisted to a preference
- Grouped passes in grid mode: stacked card visual with slightly offset cards behind the front card

### Card Design

Apple's guidelines do not specify a grid layout, so the card design is the core creative challenge. Pass data is inconsistent across issuers — some have strip images, some logos, some neither. Cards need to look reasonable across all cases.

**Visual fallback hierarchy:** strip image → background image → solid `backgroundColor` → default app theme color

**Option A — Logo + primary field, color background**
```
┌─────────────────┐  ┌─────────────────┐
│ [LOGO]          │  │ [LOGO]          │
│                 │  │                 │
│  JOHN DOE       │  │  UA 2345        │
│  Boarding Pass  │  │  Boarding Pass  │
│                 │  │                 │
│  Jan 15 · 2:30p │  │  Jan 16 · 8:00a │
└─────────────────┘  └─────────────────┘
```

**Option B — Strip image dominant (recommended default)**
```
┌─────────────────┐  ┌─────────────────┐
│█████████████████│  │█████████████████│
│█  STRIP IMAGE  █│  │█  STRIP IMAGE  █│
│█████████████████│  │█████████████████│
│ [LOGO]          │  │ [LOGO]          │
│ UA 2345         │  │ Walgreens       │
│ Jan 15 · 2:30p  │  │ Balance: $24.50 │
└─────────────────┘  └─────────────────┘
```

**Option C — Boarding pass specific template**
```
┌─────────────────┐  ┌─────────────────┐
│ [LOGO]  UA 2345 │  │ [LOGO]  DL 101  │
│                 │  │                 │
│  JFK  →  LAX   │  │  BOS  →  SFO   │
│                 │  │                 │
│  Gate B12       │  │  Gate A4        │
│  Jan 15 · 2:30p │  │  Jan 16 · 8:00a │
└─────────────────┘  └─────────────────┘
```

**Option D — Minimal, logo + barcode**
```
┌─────────────────┐  ┌─────────────────┐
│ [LOGO]          │  │ [LOGO]          │
│ United Airlines │  │ Walgreens       │
│                 │  │                 │
│    ▄▀▄▀▄▀▄▀    │  │    ▄▀▄▀▄▀▄▀    │
│    ▀▄▀▄▀▄▀▄    │  │    ▀▄▀▄▀▄▀▄    │
│    ▄▀▄▀▄▀▄▀    │  │    ▄▀▄▀▄▀▄▀    │
└─────────────────┘  └─────────────────┘
```
*Note: Option D is risky — barcodes at card size are too small to scan and waste space.*

**Recommendation:** Option B as the default template for most pass types, with Option C as a boarding-pass-specific override. Pass type detection already exists in the codebase.

### Pass Color Handling
- **Option A:** Force light theme rendering for the pass composable regardless of system theme — pass colors always render as the issuer intended
- **Option B:** Read pass `backgroundColor`, derive if light or dark, adapt surrounding chrome accordingly

---

## List Screen Shortcomings

- **Passes are too small** — cards in the list show minimal content, no date/time context
  - *Option A:* Taller cards showing strip/logo image, primary field, and relevant date
  - *Option B:* Toggle between compact and expanded card modes

- **No date/time awareness** — ordering and display are at the mercy of whatever the pass encodes in its header fields
  - *Option A:* Parse `relevantDate` and render it consistently on every card (e.g. "Jan 15 · 2:30 PM")
  - *Option B:* Sticky date section headers — Today, Upcoming, Past

- **No expired/upcoming/today indicator** — no visual distinction between past, present, and future passes at a glance
  - *Option A:* Color-coded left border or badge — green = upcoming/active, grey = past/expired
  - *Option B:* Automatic list sections with expired passes dimmed

- **Grouped passes not visually distinct** — grouped passes (e.g. outbound + return flight) look the same as single passes
  - *Option A:* Stacked card with count badge, expand on tap before navigating
  - *Option B:* Single card summarizing both legs (e.g. "JFK → LAX · LAX → JFK"), navigates to a group detail screen

- **No search across pass content** — search doesn't reach into flight numbers, airline names, event names, etc.
  - *Option A:* Index all field values into a searchable text column at import time, query in DAO
  - *Option B:* In-memory search expanded to match all structured field values — simpler but slower at scale

- **No favorites/pinning** — no way to pin frequently used passes to the top
  - *Option A:* Boolean `pinned` flag on entity, always sorts above rest regardless of `sortOrder`
  - *Option B:* Reuse existing `sortOrder` with a high sentinel value for pinned — no schema change needed

---

## Pass Detail Screen

- **No long-press preview** — no way to peek at a pass without fully navigating to it
  - *Option A:* Bottom sheet with condensed pass preview (logo, primary field, barcode) and Open/Dismiss actions
  - *Option B:* Popup overlay that dismisses on release, no sheet chrome

- **No barcode zoom** — no way to zoom in on a QR code or barcode for easier scanning
  - *Option A:* Tap barcode → fullscreen barcode-only screen with max brightness
  - *Option B:* Tap barcode → expand in-place with shared element transition, dimming the rest of the pass

- **Brightness doesn't auto-maximize** — standard wallet UX expectation when presenting a pass for scanning
  - *Option A:* Maximize brightness on pass detail entry, restore on exit
  - *Option B:* Only maximize brightness on barcode tap — less aggressive, avoids jarring shifts when just reading pass details

- **No haptic feedback** — no feedback when presenting a pass
  - *Option A:* `HapticFeedbackConstants.CONFIRM` on pass detail screen load
  - *Option B:* Haptic only on barcode zoom/tap, keeping it contextual

- **No share/export** — no way to share or export a .pkpass file from within the app
  - *Option A:* Reconstruct .pkpass ZIP from stored entity + image files, share via `ACTION_SEND` with pkpass MIME type
  - *Option B:* Share barcode data only as a QR code or deep link — simpler but loses full pass structure

---

## Pass Updates

### Pull to Refresh
- *Option A:* Wrap pass detail in `PullToRefreshBox` (Material3), wired to the existing auto-update worker
- *Option B:* Refresh icon button in the pass detail top bar — less discoverable but avoids scroll interference

### Change Log
A new database table tracking what changed between pass updates. Each row represents one changed field from one update event:

| Column | Type | Notes |
|---|---|---|
| `id` | auto-increment | primary key |
| `passId` | String | foreign key to PkPassEntity |
| `timestamp` | Long | epoch millis of the update |
| `fieldKey` | String | which field changed (e.g. `gate`, `departureTime`) |
| `oldValue` | String | value before update (local diff) |
| `newValue` | String | value after update (local diff) |
| `changeMessage` | String? | nullable — provider-supplied message (e.g. "Gate changed to %@"), `%@` substituted with `newValue` |

**Display logic:** prefer `changeMessage` (substituted) when present; otherwise construct a message from `oldValue`/`newValue`.

**Fields worth diffing locally:**
- `relevantDate`
- `expirationDate`
- Primary, secondary, auxiliary, and back field values

**Fields not worth diffing:** images, translations.

**Display options:**
- *Option A:* Collapsible section at the bottom of pass detail, newest changes first
- *Option B:* Badge on the list card, tapping opens a dedicated change history bottom sheet

### changeMessage Spec Support
- The Apple Wallet spec includes a `changeMessage` field on individual pass fields — currently not parsed from pass JSON. Needs to be added to the entity and populated on update.

### Pass Update Badge in List
- *Option A:* Dot badge derived from unread change log rows, cleared on pass open
- *Option B:* Distinct card background tint until the user opens the pass

---

## General / Platform

- **No home screen widget** — no quick access to frequently used passes from the home screen
  - *Option A:* Glance widget showing next upcoming pass by `relevantDate` with tap-to-open
  - *Option B:* Configurable widget where user pins a specific pass — useful for transit cards, frequent-flyer cards
