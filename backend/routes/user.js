const express = require('express');
const jwt = require('jsonwebtoken');
const User = require('../models/User');

const router = express.Router();

// Middleware to verify JWT token
const authenticateToken = (req, res, next) => {
  const authHeader = req.headers['authorization'];
  const token = authHeader && authHeader.split(' ')[1];

  if (!token) {
    return res.status(401).json({ error: 'Access token required' });
  }

  jwt.verify(token, process.env.JWT_SECRET, (err, user) => {
    if (err) {
      return res.status(403).json({ error: 'Invalid or expired token' });
    }
    req.user = user;
    next();
  });
};

// Get user profile
router.get('/profile', authenticateToken, async (req, res) => {
  try {
    const user = await User.findById(req.user.userId).select('-pin');
    if (!user) {
      return res.status(404).json({ error: 'User not found' });
    }
    res.json({ user });
  } catch (error) {
    console.error('Profile fetch error:', error);
    res.status(500).json({ error: 'Failed to fetch profile' });
  }
});

// Get home data (user info + services)
router.get('/home-data', authenticateToken, async (req, res) => {
  try {
    const user = await User.findById(req.user.userId).select('-pin');
    if (!user) {
      return res.status(404).json({ error: 'User not found' });
    }

    // Mock data for services, offers, and bundles
    const homeData = {
      user: {
        id: user._id,
        name: user.name,
        phone: user.phone,
        balance: user.balance
      },
      services: [
        { id: 1, name: 'Send Money', icon: 'send', color: '#E91E63' },
        { id: 2, name: 'Mobile Recharge', icon: 'phone', color: '#2196F3' },
        { id: 3, name: 'Cash Out', icon: 'account_balance', color: '#4CAF50' },
        { id: 4, name: 'Payment', icon: 'payment', color: '#FF9800' },
        { id: 5, name: 'Add Money', icon: 'add_circle', color: '#9C27B0' },
        { id: 6, name: 'More', icon: 'more_horiz', color: '#607D8B' }
      ],
      offers: [
        { id: 1, title: 'Free Cash Out', description: 'No charge for cash out this month', image: 'offer1' },
        { id: 2, title: 'Recharge Bonus', description: 'Get 10% bonus on mobile recharge', image: 'offer2' }
      ],
      bundles: [
        { id: 1, name: 'Internet Bundle', price: '৳29', validity: '7 days', description: '1GB daily' },
        { id: 2, name: 'Voice Bundle', price: '৳38', validity: '7 days', description: '100 minutes' }
      ]
    };

    res.json(homeData);
  } catch (error) {
    console.error('Home data fetch error:', error);
    res.status(500).json({ error: 'Failed to fetch home data' });
  }
});

module.exports = router;
