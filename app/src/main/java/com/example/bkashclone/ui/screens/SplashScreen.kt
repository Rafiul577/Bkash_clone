package com.example.bkashclone.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.bkashclone.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }
    
    // Loading circle animation states
    var leftCircleAnimating by remember { mutableStateOf(false) }
    var middleCircleAnimating by remember { mutableStateOf(false) }
    var rightCircleAnimating by remember { mutableStateOf(false) }
    
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(500) // Start left circle first
        leftCircleAnimating = true
        delay(200) // Start middle circle
        middleCircleAnimating = true
        delay(200) // Start right circle
        rightCircleAnimating = true
        delay(2000) // Wait for animation to complete
        onSplashComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD81B60)), // Darker magenta background #D81B60
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // White Origami Bird Logo
            Image(
                painter = painterResource(id = R.drawable.splash_origami),
                contentDescription = "Origami Bird Logo",
                modifier = Modifier.size(200.dp)
            )
            
            Spacer(modifier = Modifier.height(80.dp))
            
            // Three Loading Circles with Connected Motion
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Circle - White
                LoadingCircle(
                    color = Color.White,
                    isAnimating = leftCircleAnimating,
                    delay = 0
                )
                
                // Middle Circle - Light Pink #F8BBD0
                LoadingCircle(
                    color = Color(0xFFF8BBD0),
                    isAnimating = middleCircleAnimating,
                    delay = 200
                )
                
                // Right Circle - Darker Pink #EC407A
                LoadingCircle(
                    color = Color(0xFFEC407A),
                    isAnimating = rightCircleAnimating,
                    delay = 400
                )
            }
        }
    }
}

@Composable
private fun LoadingCircle(
    color: Color,
    isAnimating: Boolean,
    delay: Int
) {
    var startScale by remember { mutableStateOf(false) }
    
    LaunchedEffect(key1 = isAnimating) {
        if (isAnimating) {
            delay(delay.toLong())
            startScale = true
        }
    }
    
    val scale by animateFloatAsState(
        targetValue = if (startScale) 1.3f else 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "circle_scale"
    )
    
    Box(
        modifier = Modifier
            .size(16.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(color)
    )
}
