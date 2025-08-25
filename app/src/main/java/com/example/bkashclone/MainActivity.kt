package com.example.bkashclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bkashclone.ui.screens.ComingSoonScreen
import com.example.bkashclone.ui.screens.HomeScreen
import com.example.bkashclone.ui.screens.LoginScreen
import com.example.bkashclone.ui.screens.SplashScreen
import com.example.bkashclone.ui.theme.BkashCloneTheme
import com.example.bkashclone.ui.viewmodels.HomeViewModel
import com.example.bkashclone.ui.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BkashCloneTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BkashApp()
                }
            }
        }
    }
}

@Composable
fun BkashApp() {
    val navController = rememberNavController()
    var showSplash by remember { mutableStateOf(true) }
    
    if (showSplash) {
        SplashScreen(
            onSplashComplete = {
                showSplash = false
            }
        )
    } else {
        NavHost(
            navController = navController,
            startDestination = "login"
        ) {
            composable("login") {
                val loginViewModel: LoginViewModel = hiltViewModel()
                val loginState by loginViewModel.uiState.collectAsState()
                
                LaunchedEffect(loginState.loginSuccess) {
                    if (loginState.loginSuccess) {
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                        loginViewModel.resetLoginSuccess()
                    }
                }
                
                LoginScreen(
                    onBackPressed = {
                        // Go back to splash (or could be disabled)
                    },
                    onLoginSuccess = {
                        // The LoginScreen now handles the validation and calls this when ready
                        // We need to get the current state from the ViewModel
                        loginViewModel.login()
                    },
                    onCreateAccount = {
                        // TODO: Navigate to registration screen
                    },
                    onLanguageSwitch = {
                        // TODO: Implement language switch
                    },
                    onQrLogin = {
                        navController.navigate("coming_soon")
                    },
                    onFingerprint = {
                        // TODO: Implement fingerprint login
                    },
                    onForgotPin = {
                        navController.navigate("coming_soon")
                    },
                    loginViewModel = loginViewModel
                )
            }
            
            composable("home") {
                val homeViewModel: HomeViewModel = hiltViewModel()
                HomeScreen(homeViewModel = homeViewModel)
            }
            
            composable("coming_soon") {
                ComingSoonScreen(
                    onBackPressed = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}