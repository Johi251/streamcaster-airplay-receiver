# StreamCaster Receiver v1.0.0

Erster öffentlicher Build: AirPlay-2-Empfänger für Amazon Fire TV Stick
(arm64, Android 9+), mit StreamCaster-Hub.

## Enthalten

- **AirPlay-Spiegelung und -Audio** über den UxPlay-Kern (H.264/H.265,
  AAC/ALAC), mDNS als `StreamCaster TV` im lokalen Netz
- **StreamCaster-Hub**: 6-stelliger Raum-Code, Signaling-Status, AirPlay-Status
- **URL-Casting** über den StreamCaster-Signaling-Worker
- **ExoPlayer/Media3**: MP4, HLS, VTT-Untertitel, Seek, Lautstärke
- **State-Sync** an den Sender
- **D-Pad-Bedienung**, deutsche UI, StreamCaster-Branding
- Signiertes Release-APK (v2-Signatur), 15 MB

## Installation (Fire TV)

```
① Fire TV → „Suchen“ → „Downloader“ installieren (Amazon Appstore, gratis)
② Downloader öffnen → APK-URL eingeben:
   https://github.com/Johi251/streamcaster-airplay-receiver/releases/latest/download/streamcaster-receiver.apk
③ „Apps aus unbekannten Quellen“ für Downloader erlauben → installieren
④ „StreamCaster Receiver“ starten → Raum-Code erscheint
```

iPhone/iPad: Kontrollzentrum → Bildschirmspiegelung → **StreamCaster TV**.
Beide Geräte müssen im selben WLAN sein; AP-Isolation am Router muss aus sein.

## Bekannte Grenzen

- WebRTC-Screen/Camera-Mirroring aus der Webapp wird noch nicht dekodiert.
  Für iPhone-Spiegelung ist AirPlay der vorgesehene Weg.
- Kein Chromecast-Receiver und kein DLNA-Renderer in dieser Version.
- Kein DRM (Netflix, Apple TV App …) über AirPlay.
- Nur arm64-v8a (Fire TV Stick 4K und neuer).
- Dieser Build wurde noch nicht auf einem physischen Fire TV Stick getestet.

## Prüfsumme

```
SHA-256: 7b4100b32b5d89a83c1c8c6a29dfd87d82191f6d21d7b0955286062562434a08
```

Quellcode: GPL-3.0, vollständig in diesem Repository inklusive Submodule.
