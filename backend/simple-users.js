const mongoose = require('mongoose');
const User = require('./models/User');
require('dotenv').config();

async function createSimpleUsers() {
  try {
    // Connect to MongoDB
    await mongoose.connect(process.env.MONGODB_URI || 'mongodb://localhost:27017/bkash-clone');
    console.log('Connected to MongoDB');

    // Clear existing users
    await User.deleteMany({});
    console.log('Cleared existing users');

    // Simple test users with different phone formats and balances
    const testUsers = [
      {
        name: 'Rafiul Hasan',
        phone: '01536087049',
        pin: '1234',
        balance: 5000
      },
      {
        name: 'Ahmed Khan',
        phone: '01712345678',
        pin: '5678',
        balance: 2500
      },
      {
        name: 'Fatima Begum',
        phone: '01898765432',
        pin: '9999',
        balance: 7500
      },
      {
        name: 'Mohammad Ali',
        phone: '01611223344',
        pin: '1111',
        balance: 1200
      },
      {
        name: 'Ayesha Rahman',
        phone: '01987654321',
        pin: '2222',
        balance: 8900
      }
    ];

    // Create users
    for (const userData of testUsers) {
      const user = new User(userData);
      await user.save();
      console.log(`✅ Created user: ${userData.name} - ${userData.phone} - Balance: ৳${userData.balance}`);
    }

    console.log('\n🎉 All test users created successfully!');
    console.log('\n📱 Login Credentials:');
    console.log('=====================================');
    testUsers.forEach((user, index) => {
      console.log(`${index + 1}. ${user.name}`);
      console.log(`   Phone: ${user.phone}`);
      console.log(`   PIN: ${user.pin}`);
      console.log(`   Balance: ৳${user.balance}`);
      console.log('');
    });

    console.log('💡 You can use any of these credentials to test the app!');

  } catch (error) {
    console.error('❌ Error creating users:', error);
  } finally {
    await mongoose.disconnect();
    console.log('Disconnected from MongoDB');
  }
}

createSimpleUsers();
