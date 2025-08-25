# bKash Clone UI Screens

## Splash Screen Implementation

### Overview
The splash screen is the initial loading screen that appears when the app starts. It features a beautiful darker magenta background with an origami bird logo and animated loading circles.

### Features
- **Darker Magenta Background**: Solid #D81B60 background color
- **Origami Bird Logo**: White origami bird image (splash_origami.png)
- **Loading Animation**: Three colored circles with connected motion
  - Left: White (#FFFFFF)
  - Middle: Light Pink (#F8BBD0)
  - Right: Darker Pink (#EC407A)

### Animation Details
- **Duration**: 2-3 seconds total
- **Circle Animation**: Sequential start with 200ms delays
- **Scale Effect**: Circles grow from 0.8x to 1.3x size
- **Smooth Motion**: EaseInOutCubic easing for natural movement
- **Infinite Loop**: Continuous animation until navigation

### Navigation
After the animation completes, the splash screen automatically navigates to the login screen via the `onSplashComplete` callback.

## Login Screen Implementation

### Overview
The login screen provides a complete authentication interface with input validation, numeric keypad, and navigation options.

### Features
- **Top Bar**: Back button and bKash logo (aligned left, logo under back button)
- **Title Section**: "Log In" with subtitle "Log In to your bkash account" (left-aligned)
- **Input Fields**:
  - Account Number field (11-digit phone number validation, read-only for numpad input)
  - PIN field (5-digit masked input, read-only for numpad input)
- **Action Buttons**:
  - "Forgot PIN? Try PIN Reset" link (pink text, left-aligned)
  - "Create Account" button (outlined style with pink border)
  - "Next" button (pink background with right arrow)
- **Standard Keyboard Input**: 
  - Account Number field uses phone keyboard
  - PIN field uses numeric password keyboard
  - No custom numpad - uses mobile's built-in keyboard

### Validation
- **Account Number**: Must be exactly 11 digits
- **PIN**: Must be exactly 5 digits
- **Error Display**: Shows validation errors below input fields
- **Real-time Validation**: Clears errors as user types

### Navigation
- **Back Button**: Returns to previous screen
- **Login Success**: Navigates to home screen
- **Create Account**: Placeholder for registration navigation

### Files
- `SplashScreen.kt` - Main splash screen composable
- `splash_origami.png` - Origami bird logo image
- `LoginScreen.kt` - Complete login screen implementation
- `back_button.png` - Back button image
- `logo.jpg` - bKash logo image
- `HomeScreen.kt` - Placeholder home screen

## Usage Examples

### Splash Screen
```kotlin
SplashScreen(
    onSplashComplete = {
        // Navigate to next screen
        showSplash = false
    }
)
```

### Login Screen
```kotlin
LoginScreen(
    onBackPressed = { /* Handle back navigation */ },
    onLoginSuccess = { /* Navigate to home */ },
    onCreateAccount = { /* Navigate to registration */ }
)
```

## Image Resources
All required images are stored in `app/src/main/res/drawable/`:
- `splash_origami.png` - Origami bird for splash screen
- `back_button.png` - Back navigation button
- `logo.jpg` - bKash application logo
