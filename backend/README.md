# bKash Clone Backend

Node.js REST API backend for the bKash clone mobile wallet application.

## 🚀 Quick Start

1. **Install dependencies:**
   ```bash
   npm install
   ```

2. **Set up environment variables:**
   Create a `.env` file in the backend directory:
   ```env
   MONGODB_URI=mongodb://localhost:27017/bkash-clone
   JWT_SECRET=your-super-secret-jwt-key-change-in-production
   JWT_EXPIRES_IN=7d
   PORT=3000
   ```

3. **Start MongoDB:**
   Make sure MongoDB is running on your system or use a cloud service.

4. **Run the server:**
   ```bash
   # Development mode with auto-reload
   npm run dev
   
   # Production mode
   npm start
   ```

5. **Test the API:**
   ```bash
   # Health check
   curl http://localhost:3000/health
   
   # Register a user
   curl -X POST http://localhost:3000/api/auth/register \
     -H "Content-Type: application/json" \
     -d '{"name":"John Doe","phone":"+8801712345678","pin":"1234"}'
   ```

## 📊 API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login

### User Data (Protected)
- `GET /api/user/profile` - Get user profile
- `GET /api/user/home-data` - Get home screen data

### Health Check
- `GET /health` - API status

## 🔒 Security Features

- **PIN Encryption**: All PINs are hashed using bcrypt
- **JWT Authentication**: Secure token-based authentication
- **Input Validation**: Request validation using express-validator
- **Security Headers**: Helmet.js for security headers
- **CORS Protection**: Configurable CORS settings

## 🗄️ Database Schema

### User Collection
```javascript
{
  _id: ObjectId,
  name: String (required, min 2 chars),
  phone: String (required, unique, mobile format),
  pin: String (required, hashed),
  balance: Number (default: 0),
  isActive: Boolean (default: true),
  createdAt: Date,
  updatedAt: Date
}
```

## 🛠️ Development

### Project Structure
```
backend/
├── models/          # MongoDB schemas
├── routes/          # API route handlers
├── middleware/      # Custom middleware
├── utils/           # Utility functions
├── server.js        # Main server file
├── package.json     # Dependencies
└── .env            # Environment variables
```

### Available Scripts
- `npm start` - Start production server
- `npm run dev` - Start development server with nodemon
- `npm test` - Run tests

### Environment Variables
- `MONGODB_URI` - MongoDB connection string
- `JWT_SECRET` - Secret key for JWT signing
- `JWT_EXPIRES_IN` - JWT token expiration time
- `PORT` - Server port (default: 3000)

## 🧪 Testing

Run the test suite:
```bash
npm test
```

## 📝 API Examples

### User Registration
```bash
curl -X POST http://localhost:3000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "phone": "+8801712345678",
    "pin": "1234"
  }'
```

### User Login
```bash
curl -X POST http://localhost:3000/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "phone": "+8801712345678",
    "pin": "1234"
  }'
```

### Get Profile (with auth token)
```bash
curl -X GET http://localhost:3000/api/user/profile \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## 🔧 Configuration

### MongoDB Connection
The app connects to MongoDB using the connection string from `MONGODB_URI`. For local development, ensure MongoDB is running on the default port (27017).

### JWT Configuration
- **Secret**: Change the `JWT_SECRET` in production
- **Expiration**: Default is 7 days, configurable via `JWT_EXPIRES_IN`

### CORS Settings
CORS is enabled by default. Modify the CORS configuration in `server.js` if needed.

## 🚨 Production Considerations

1. **Change JWT Secret**: Use a strong, unique secret key
2. **Environment Variables**: Use proper environment variable management
3. **Database Security**: Use MongoDB authentication and network security
4. **HTTPS**: Enable HTTPS in production
5. **Rate Limiting**: Consider adding rate limiting middleware
6. **Logging**: Implement proper logging and monitoring
7. **Error Handling**: Customize error responses for production

## 📚 Dependencies

### Core Dependencies
- `express` - Web framework
- `mongoose` - MongoDB ODM
- `bcryptjs` - Password hashing
- `jsonwebtoken` - JWT authentication
- `cors` - CORS middleware
- `helmet` - Security headers
- `morgan` - HTTP request logging

### Development Dependencies
- `nodemon` - Auto-restart on file changes
- `jest` - Testing framework

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is for educational purposes only.
