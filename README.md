# SpinDeck

An Android music player built as a native app version of the DecoPlayer vinyl-widget
concept: your local tracks, shown as a spinning record whose glow color adapts to the
album art, with a tonearm that lifts and drops as you play/pause.

This is an original, unaffiliated project — not the DecoPlayer desktop app itself,
which is a separate paid Windows tool by Attisdev. Renamed to avoid any confusion
with that product.

## What it does

- Scans your device's `MediaStore` for local audio (no internet, no accounts)
- Library tab: browse and tap any track to play it
- Now Playing tab: spinning vinyl UI
  - Disc rotates continuously while playing, freezes on pause
  - Tonearm animates down onto the record on play, lifts on pause
  - Glow ring behind the disc samples the album art's dominant color (via `Palette`)
  - Seek bar, play/pause/skip, track title & artist
- Background playback via a `MediaSessionService` (Media3), so music keeps playing
  when you leave the app, with lock-screen / notification transport controls

## Requirements

- Android Studio (Koala or newer recommended)
- Android SDK 34, min SDK 24 (Android 7.0+)
- JDK 17

## Getting it running

1. Open this folder in Android Studio (`File > Open`).
2. Let it sync Gradle — it will pull in Media3, Coil, and Palette automatically.
3. Run on a device or emulator with some local audio files (or push a few MP3s to
   `/Music` on a test device/emulator with `adb push song.mp3 /sdcard/Music/`).
4. Grant the audio permission prompt on first launch.

The launcher icon is a simple vector placeholder — regenerate a proper one anytime
via **Android Studio > New > Image Asset**.

## Project layout

```
app/src/main/java/com/spindeck/player/
  MainActivity.kt              navigation + permission handling
  data/Track.kt                track model
  data/MusicScanner.kt         MediaStore query
  playback/PlaybackService.kt  Media3 background playback service
  playback/PlayerViewModel.kt  connects UI state to the MediaController
  ui/LibraryScreen.kt          track list
  ui/VinylScreen.kt            the spinning-record now-playing screen
  ui/theme/                    colors, type, Material3 theme
```

## Getting an installable APK without installing anything locally

This repo includes a GitHub Actions workflow (`.github/workflows/build.yml`) that
builds a debug APK automatically, using GitHub's own build machines — you don't
need Android Studio, Gradle, or an SDK on your computer at all.

1. Push this project to a GitHub repo (see below).
2. On GitHub, open the **Actions** tab of your repo. The "Build APK" workflow runs
   automatically on every push to `main` (or trigger it manually with the
   **Run workflow** button).
3. Once it finishes (a couple of minutes), open that run and download the
   **SpinDeck-debug-apk** artifact — it's a zip containing `app-debug.apk`.
4. Copy `app-debug.apk` to your phone (email it to yourself, Google Drive, USB, etc.).
5. On your phone, tap the file to install. Android will ask you to allow installs
   from that source (Settings > apps that can install unknown apps) — approve it,
   then install.

This is a **debug build** (self-signed with Android's default debug key), which is
normal for sideloading and totally fine to install — it just means it isn't going
through the Play Store's signing process.

## Pushing this to your own GitHub repo

I don't currently have an active GitHub connection in this chat (no GitHub MCP
connector is available to me here), so I can't push on your behalf. Easiest path:

```bash
cd SpinDeck
git init
git add .
git commit -m "Initial commit: SpinDeck Android app"
git branch -M main
git remote add origin https://github.com/<your-username>/<your-repo>.git
git push -u origin main
```

If you'd rather I do the push directly from this environment, share a repo URL and
a fine-grained personal access token (repo scope only) and I can run the `git push`
here — happy to do that if you'd prefer.

## Ideas for next passes

- Bundle the Unbounded / Space Grotesk fonts as `.ttf` in `res/font` for the exact
  look of the web widget
- Selectable tonearm styles (classic/minimal/deco/industrial), like the web version
- Playlists, search, and a mini-player bar on the Library screen
- Home-screen widget (App Widget) version for true "desktop widget" behavior
