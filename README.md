# StreamCaster AirPlay Receiver (Fire TV Stick APK)

Eigenständiges Android-Projekt, das den Fire TV Stick in einen AirPlay-
Receiver verwandelt und gleichzeitig als StreamCaster-Receiver fungiert.

## Status

- ✅ Projekt-Gerüst (Gradle + Kotlin) — vorhanden
- ✅ StreamCaster-Signaling-Client (Room-Code + WebSocket) — implementiert
- ⬜ AirPlay-Server (UxPlay NDK-Port) — TODO, siehe PROMPT.md
- ⬜ ExoPlayer-Media-Playback — TODO
- ⬜ Glassmorphism-UI / Fernbedienung — TODO

## Vollständiger Build-Prompt

`PROMPT.md` — kopiere den gesamten Inhalt in **Claude Code** (CLI) oder
**Cursor** (IDE). Empfohlen: Claude 3.5 Sonnet (Kotlin/NDK-Stärke).
Kostenlos-Alternative: Gemini 2.5 Pro in AI Studio.

## Integrations-Referenz der Webapp

`../ACCESS.md` — alle Live-URLs, Signaling-/Proxy-Endpunkte, Protokoll-Spec.
Die Webapp liegt im Schwesterordner (`../apps`, `../packages`, …).

## So weiterarbeiten

1. Diesen Ordner in Claude Code / Cursor öffnen
2. `PROMPT.md` als Aufgabe übergeben (oder unten kopieren)
3. Android SDK + NDK installieren (Claude Code fragt ggf. danach)
4. APK bauen, auf GitHub Releases veröffentlichen
5. Release-URL an Webapp-Team melden → wird in `streamcaster.pages.dev`
   als Download-Link eingetragen (Konstante `AIRPLAY_RECEIVER_APK_URL`)

## Sideloading auf Fire TV

```
① Fire TV → „Find" → „Search" → „Downloader" (Amazon Appstore, gratis)
② Downloader öffnen → APK-URL eingeben
③ „Apps aus unbekannten Quellen" erlauben → APK installieren
④ App starten → Room-Code → iPhone: Control Center → Screen Mirroring → Fire TV
```
