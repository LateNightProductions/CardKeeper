# CardKeeper

Android app for scanning and storing barcodes and Apple Wallet passes (.pkpass files).

## Module Architecture

Modules are organized into four layers. Dependencies must only flow downward — a module may depend on modules in lower layers, never on modules in the same or higher layers.

```
Layer 4 · App
  :app
  → may depend on any module

Layer 3 · Features
  :items  :code-detail  :pass-detail  :scan  :create
  → may depend on Layer 2 and Layer 1
  → must NOT depend on other Layer 3 modules

Layer 2 · Shared UI
  :compose-common  :barcode-image  :code-ui-common  :pass-ui-common
  → may depend on Layer 1a (domain interfaces/types) only
  → must NOT depend on Layer 1b (data implementations)

Layer 1b · Data Implementations
  :data:barcode  :data:pkpass
  → may depend on Layer 1a only
  → must NOT depend on each other

Layer 1a · Domain
  :data:core  :data:types
  → no project dependencies
```

### Layer rules in plain language

- Feature modules are isolated from each other. If two features need to share something, it belongs in a Layer 2 shared UI module or Layer 1 domain module — not in one of the features.
- Shared UI modules know about domain types (e.g. `BarcodeFormat`, `PkPassModel`) but never import from a data implementation module (`:data:barcode`, `:data:pkpass`).
- Data implementation modules own their internal details. Entities, DAOs, and utility classes (e.g. `PassDateUtils`) stay inside the module — expose behavior through the service interface instead.
- Service interfaces return domain model types, not entity types.

### Where to put new things

| What | Where |
|---|---|
| New screen | New Layer 3 module, or add to an existing feature module if closely related |
| Reusable Compose component | Layer 2 shared UI module |
| Domain type or interface | `:data:types` or `:data:core` |
| Database entity, DAO, Room migration | `:data:barcode` or `:data:pkpass` |
| App-level wiring (DI, navigation) | `:app` |
