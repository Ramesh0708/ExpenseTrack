# ExpenseTrack

An Android expense tracker backed by Firebase. Sign up with email and password, set up a profile with a photo and your name, then add, categorise, view and delete expenses.

## Features
- Splash screen, sign-up and login (Firebase Auth / FirebaseUI)
- Profile with photo upload (Firebase Storage)
- Add, view and delete expenses with categories (Firebase Realtime Database)
- Pie chart of spending by category (`ExpensePieGraph`)

## Screenshots
| Login | Expenses | Add expense | Profile |
|---|---|---|---|
| ![](App%20Images/Login%20Page.png) | ![](App%20Images/Expense%20Activity%20Page.png) | ![](App%20Images/Add%20Expense%20Activity%20Page.png) | ![](App%20Images/Profile%20Page.png) |

## Tech stack
Java, Android SDK (compileSdk 29, minSdk 16), AndroidX, Material Components, Firebase Auth, Realtime Database, Storage and Messaging.

## Build
1. Open the project in Android Studio.
2. Create a Firebase project, enable Email/Password auth, Realtime Database and Storage, and put your own `google-services.json` in `app/`.
3. Run it on a device or emulator, or build from the command line:
   ```bash
   ./gradlew assembleDebug
   ```
