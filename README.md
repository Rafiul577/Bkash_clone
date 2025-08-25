# 🏦 bKash Clone - Mobile App

A full-stack clone of the bKash mobile app's login and home pages, built for educational purposes using **Android Jetpack Compose** (front-end) and **Node.js with MongoDB** (back-end). This project showcases mobile app development and AI-assisted coding with Cursor AI.

[![bKash Clone](https://img.shields.io/badge/bKash-Clone-orange)](https://github.com/Rafiul577/bkash-clone)

## 🎯 Project Overview

This repository contains a fully functional clone of the bKash mobile app, Bangladesh's leading mobile financial service. Developed for educational purposes, it replicates the login and home pages with a focus on UI/UX and full-stack integration. **Note**: This is not an official bKash app and should not be used for real transactions or sensitive data.

- **Frontend**: Android app with bKash-style UI using Jetpack Compose.
- **Backend**: Node.js API with MongoDB for data storage.
- **Features**: Phone number + PIN authentication, balance display, services grid, and animations.
- **AI Assistance**: Built entirely using Cursor AI's Agent AI features for code generation, UI design, API integration, and debugging.

## 🛠️ Technical Stack

### Frontend (Android)
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM + Repository Pattern
- **Dependency Injection**: Hilt
- **Networking**: Retrofit + OkHttp
- **Local Storage**: DataStore
- **State Management**: StateFlow

### Backend (Node.js)
- **Runtime**: Node.js (v16+)
- **Framework**: Express.js
- **Database**: MongoDB with Mongoose
- **Authentication**: JWT
- **Security**: bcrypt for PIN hashing
- **Validation**: Custom phone number validation

## 🏗️ Project Structure

```
Bkash_clone/
├── 📱 app/                          # Android Application
│   ├── src/main/
│   │   ├── java/com/example/bkashclone/
│   │   │   ├── MainActivity.kt      # Main entry point
│   │   │   ├── ui/screens/          # UI screens
│   │   │   ├── ui/viewmodels/       # ViewModels
│   │   │   ├── data/                # Data layer
│   │   │   └── di/                  # Dependency injection
│   │   └── res/                     # Resources
│   │       ├── drawable/            # 50+ icons/images
│   │       ├── xml/                 # Config files
│   │       └── values/              # Strings, colors, themes
│   └── build.gradle.kts             # Build configuration
├── 🌐 backend/                      # Node.js Backend
│   ├── routes/                      # API routes
│   ├── models/                      # Database models
│   ├── server.js                    # Main server
│   ├── simple-users.js              # Test user creation
│   └── package.json                 # Dependencies
└── README.md                        # This file
```

## 🎨 Features Implemented

### Authentication System
- Phone number + PIN login with Bangladeshi phone number validation.
- In-app number pad for PIN entry.
- JWT-based authentication with session persistence.

### Home Screen
- User profile and balance display with reveal animation.
- 4x4 services grid with 16 interactive services.
- Banner carousel and multiple content sections.
- Bottom navigation for seamless UX.

### UI/UX
- Responsive design following Material Design principles.
- bKash brand colors and styling.
- Smooth animations and comprehensive error handling.

## 🚀 Setup Instructions

These instructions are designed to help anyone set up and run the bKash clone project on their own device.

### Prerequisites
- **Android Studio** (latest version, e.g., Koala or later)
- **Node.js** (v16 or higher)
- **MongoDB** (installed and running locally)
- **Java 17** or higher
- A compatible Android device or emulator (Android 7.0+ recommended)
- Git installed for cloning the repository
- A stable internet connection for dependency downloads

### Step 1: Clone the Repository
1. Open a terminal on your device.
2. Clone the project:
   ```bash
   git clone https://github.com/Rafiul577/bkash-clone.git
   ```
3. Navigate to the project directory:
   ```bash
   cd bkash-clone
   ```

### Step 2: Set Up the Backend
1. Navigate to the backend directory:
   ```bash
   cd backend
   ```
2. Install Node.js dependencies:
   ```bash
   npm install
   ```
3. Ensure MongoDB is running locally:
   ```bash
   mongod --version
   ```
   If MongoDB is not installed, download and install it from [mongodb.com](https://www.mongodb.com/try/download/community) and start the service (e.g., `mongod` in a separate terminal).
4. Create test users in the database:
   ```bash
   node simple-users.js
   ```
5. Start the backend server:
   ```bash
   npm start
   ```
6. Verify the server is running (output should be):
   ```
   Connected to MongoDB
   Server running on port 3000
   ```

### Step 3: Set Up the Android App
1. Open **Android Studio** on your device.
2. Select **Open an existing project** and choose the `bkash-clone` folder (e.g., `/path/to/bkash-clone`).
3. Wait for Gradle to sync (this may take a few minutes to download dependencies).
4. Configure an Android device or emulator:
   - For an emulator, create a virtual device in Android Studio (Android 7.0+ recommended).
   - For a physical device, enable USB debugging and connect it to your computer via USB, or ensure it’s on the same network as the backend server.
5. Update the API URL in the app’s network configuration:
   - For emulators, use `http://10.0.2.2:3000/api/` (edit in the app’s network settings, e.g., `data/network` files).
   - For physical devices, use `http://<your-computer-ip>:3000/api/` (find your computer’s IP with `ifconfig` on macOS/Linux or `ipconfig` on Windows, and ensure both devices are on the same Wi-Fi network).
6. Click the **Run** button (green play button) in Android Studio to build and deploy the app.

### Step 4: Test the App
- Use the test credentials below to log in.
- Verify that the balance displays correctly and the services grid is interactive.

## 📱 Test Credentials

| User            | Phone Number  | PIN  | Balance  |
|-----------------|---------------|------|----------|
| Rafiul Hasan    | `01536087049` | `1234` | ৳5,000  |
| Ahmed Khan      | `01712345678` | `5678` | ৳2,500  |
| Fatima Begum    | `01898765432` | `9999` | ৳7,500  |
| Mohammad Ali    | `01611223344` | `1111` | ৳1,200  |
| Ayesha Rahman   | `01987654321` | `2222` | ৳8,900  |

