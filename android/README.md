# বাংলা বয়স গণনা — Android

This folder contains the Play Store Android wrapper for the existing Bengali age calculator.

## Before production

The project intentionally uses Google's official **test** AdMob App ID and banner ad unit ID. Do not publish with test IDs if you want real ad revenue.

Replace these values:

- `android/app/src/main/AndroidManifest.xml` → `com.google.android.gms.ads.APPLICATION_ID`
- `android/app/src/main/java/com/muyeedlab/agecalculator/MainActivity.kt` → `adUnitId`

Create your own AdMob app/ad unit in AdMob and use the IDs provided by Google.

## Build

Open the `android/` folder in Android Studio and let Gradle sync. Use JDK 17.

For a Play Store bundle:

```bash
gradle :app:bundleRelease
```

The release bundle will be under `app/build/outputs/bundle/release/`.

A Play Store release must be signed with a release/upload key. Keep the keystore and passwords private; never commit them to GitHub.

## Play Store checklist

- Set your real AdMob App ID and ad unit ID.
- Create a release/upload keystore and keep it private.
- Build a signed `.aab`.
- Complete the Play Console Data safety and content declarations.
- Host a public privacy policy URL and use that URL in Play Console.
- Complete internal testing first.
- If your personal Play developer account was created after November 13, 2023, complete the required closed test with at least 12 opted-in testers continuously for 14 days before requesting production access.
