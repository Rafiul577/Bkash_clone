const mongoose = require('mongoose');
const User = require('./models/User');
require('dotenv').config();

async function createTestUser() {
  try {
    // Connect to MongoDB
    await mongoose.connect(process.env.MONGODB_URI || 'mongodb://localhost:27017/bkash-clone');
    console.log('Connected to MongoDB');

    // Test users with different phone number formats
    const testUsers = [
      {
        name: 'Rafiul Hasan (International)',
        phone: '+88 01536087049',
        pin: '1234',
        balance: 1000
      },
      {
        name: 'Rafiul Hasan (Local)',
        phone: '01536087049',
        pin: '1234',
        balance: 1000
      },
      {
        name: 'Rafiul Hasan (No Space)',
        phone: '+8801536087049',
        pin: '1234',
        balance: 1000
      }
    ];

    for (const userData of testUsers) {
      // Check if user already exists
      const existingUser = await User.findOne({ phone: userData.phone });
      if (existingUser) {
        console.log(`User already exists: ${userData.phone}`);
        continue;
      }

      // Create test user
      const testUser = new User(userData);
      await testUser.save();
      console.log(`Test user created: ${userData.name} - ${userData.phone}`);
    }

    console.log('\nYou can now login with any of these:');
    console.log('Phone: +88 01536087049, PIN: 1234');
    console.log('Phone: 01536087049, PIN: 1234');
    console.log('Phone: +8801536087049, PIN: 1234');

  } catch (error) {
    console.error('Error creating test users:', error);
  } finally {
    await mongoose.disconnect();
    console.log('Disconnected from MongoDB');
  }
}

createTestUser();
