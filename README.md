# 🏦 bKash Clone - Complete Mobile App

A fully functional bKash mobile app clone built with **Android Jetpack Compose** and **Node.js backend**, featuring authentication, home screen with services, and balance management.

## 🎯 **Project Overview**

This project demonstrates a complete mobile banking app with:
- **Frontend**: Android app with bKash-style UI
- **Backend**: Node.js API with MongoDB database
- **Authentication**: Phone number + PIN login system
- **Features**: Balance display, services grid, animations

---

## 🚀 **Quick Start Guide**

### **Prerequisites**
- ✅ **Android Studio** (latest version)
- ✅ **Node.js** (v16 or higher)
- ✅ **MongoDB** (running locally)
- ✅ **Java 17** or higher

### **Step 1: Start the Backend**
```bash
# Navigate to backend directory
cd backend

# Install dependencies
npm install

# Create test users in database
node simple-users.js

# Start the server
npm start
```

**Expected Output:**
```
Connected to MongoDB
Server running on port 3000
```

### **Step 2: Open in Android Studio**
1. **Open Android Studio**
2. **Open Project**: Select `/Users/rafi/Documents/projects/Bkash_clone`
3. **Wait for Gradle sync** to complete
4. **Connect device/emulator**
5. **Click Run** (green play button)

---

## 📱 **Test Credentials**

Use any of these credentials to test the app:

| User | Phone Number | PIN | Balance |
|------|-------------|-----|---------|
| **Rafiul Hasan** | `01536087049` | `1234` | **৳5,000** |
| **Ahmed Khan** | `01712345678` | `5678` | **৳2,500** |
| **Fatima Begum** | `01898765432` | `9999` | **৳7,500** |
| **Mohammad Ali** | `01611223344` | `1111` | **৳1,200** |
| **Ayesha Rahman** | `01987654321` | `2222` | **৳8,900** |

---

## 🏗️ **Project Structure**

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

---

## 🎨 **Features Implemented**

### **Authentication System**
- ✅ Phone number + PIN login
- ✅ Input validation (Bangladeshi phone numbers)
- ✅ In-app numberpad for PIN entry
- ✅ JWT token management
- ✅ Session persistence

### **Home Screen**
- ✅ User profile display
- ✅ **Balance display with correct amounts**
- ✅ Interactive balance reveal animation
- ✅ Services grid (4x4 layout with 16 services)
- ✅ Banner carousel
- ✅ Multiple content sections
- ✅ Bottom navigation

### **UI/UX**
- ✅ Responsive design
- ✅ Material Design principles
- ✅ bKash brand colors and styling
- ✅ Smooth animations
- ✅ Error handling and loading states

---

## 🛠️ **Technical Stack**

### **Frontend (Android)**
- **Language**: Kotlin
[![bKash Clone](https://img.shields.io/badge/bKash-Clone-orange)](https://github.com/Rafiul577/bkash-clone)
- **Architecture**: MVVM + Repository Pattern
- **DI**: Hilt
- **Networking**: Retrofit + OkHttp
- **Local Storage**: DataStore
- **State Management**: StateFlow

### **Backend (Node.js)**
- **Runtime**: Node.js
- **Security**: bcrypt for PIN hashing


## 🔧 **Troubleshooting**

### **Common Issues & Solutions**

```bash
# Check if MongoDB is running
mongod --version
# Check if port 3000 is available
lsof -i :3000

# Kill existing process if needed
```

./gradlew clean
./gradlew assembleDebug

#### **3. Login Issues**
- **401 Error**: Check phone number format (no +88 prefix)
- **400 Error**: Ensure PIN is 4-6 digits
- **Network Error**: Verify backend is running on port 3000

#### **4. Balance Showing 0**
- ✅ **FIXED**: Balance now shows correct amounts
- If issue persists, check backend logs for user data

### **Network Configuration**
The app is configured for:
- **Emulator**: `http://10.0.2.2:3000/api/`
- **Physical Device**: `http://localhost:3000/api/`

For physical devices, ensure your device and computer are on the same network.

### **Backend Testing**
```bash
# Health check
curl http://localhost:3000/health

# Test login
curl -X POST http://localhost:3000/api/auth/login \
  -H "Content-Type: application/json" \

### **Android Testing**

---

## 🎯 **Current Status**
### **✅ Project Status: READY TO RUN**

- ✅ **All Core Features**: Implemented and tested
- ✅ **Authentication**: Working with proper validation
- ✅ **Integration**: Frontend-backend communication working
- ✅ **Error Handling**: Comprehensive error management
