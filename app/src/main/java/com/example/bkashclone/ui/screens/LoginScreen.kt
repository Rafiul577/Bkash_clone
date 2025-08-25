package com.example.bkashclone.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.*
import com.example.bkashclone.R
import com.example.bkashclone.ui.theme.BkashPink
import com.example.bkashclone.ui.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    onBackPressed: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    onCreateAccount: () -> Unit = {},
    onLanguageSwitch: () -> Unit = {},
    onQrLogin: () -> Unit = {},
    onFingerprint: () -> Unit = {},
    onForgotPin: () -> Unit = {},
    loginViewModel: LoginViewModel
) {
    val loginState by loginViewModel.uiState.collectAsState()
    var isPhoneInputMode by remember { mutableStateOf(false) }
    
    // Validation states - Updated to handle multiple phone number formats
    val isPhoneValid = loginState.phoneNumber.length >= 11 && (
        loginState.phoneNumber.matches(Regex("^\\+88\\s\\d{11}$")) || // +88 01536087049
        loginState.phoneNumber.matches(Regex("^\\+88\\d{11}$")) || // +8801536087049
        loginState.phoneNumber.matches(Regex("^\\d{11}$")) || // 01536087049
        loginState.phoneNumber.matches(Regex("^\\d{13}$")) // 8801536087049
    )
    val isPinValid = loginState.pin.length in 4..6
    val canSubmit = isPhoneValid && isPinValid && !loginState.isLoading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackPressed,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "Back",
                    tint = BkashPink,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(Modifier.weight(1f))
            OutlinedButton(
                onClick = onLanguageSwitch,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .height(32.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BkashPink),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
            ) {
                Text(
                    text = "বাংলা",
                    color = BkashPink,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Default
                )
            }
        }

        // Logo & QR Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "bKash Logo",
                modifier = Modifier
                    .padding(start = 24.dp)
                    .size(40.dp)
            )
            Spacer(Modifier.weight(1f))
            IconButton(
                onClick = onQrLogin,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_qr_code),
                    contentDescription = "QR Code",
                    tint = BkashPink,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Login Instructions
        Text(
            text = "Log In",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontFamily = FontFamily.Default,
            modifier = Modifier
                .padding(top = 16.dp, start = 24.dp)
        )
        Text(
            text = "to your bKash account",
            fontSize = 16.sp,
            color = Color(0xFF555555),
            fontFamily = FontFamily.Default,
            modifier = Modifier
                .padding(start = 24.dp, top = 4.dp)
        )

        // Phone Number Section
        Text(
            text = "Account Number",
            fontSize = 14.sp,
            color = Color(0xFF757575),
            fontFamily = FontFamily.Default,
            modifier = Modifier.padding(start = 24.dp, top = 20.dp)
        )
        
        // Phone number input with clickable area for numberpad
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 4.dp)
                .height(56.dp)
                .border(
                    width = 1.dp,
                    color = if (isPhoneValid || loginState.phoneNumber.isEmpty()) Color(0xFFBDBDBD) else Color.Red,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(Color.White, RoundedCornerShape(12.dp))
                .clickable { isPhoneInputMode = true }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (loginState.phoneNumber.isEmpty()) "01XXXXXXXXX" else loginState.phoneNumber,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (loginState.phoneNumber.isEmpty()) Color(0xFFBDBDBD) else Color.Black,
                    fontFamily = FontFamily.Default,
                    modifier = Modifier.weight(1f)
                )
                if (isPhoneInputMode) {
                    Text(
                        text = "Tap to edit",
                        fontSize = 12.sp,
                        color = BkashPink,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }

        // PIN Section
        Text(
            text = "bKash PIN",
            fontSize = 14.sp,
            color = Color(0xFF757575),
            fontFamily = FontFamily.Default,
            modifier = Modifier.padding(start = 24.dp, top = 20.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 4.dp)
                .fillMaxWidth()
                .height(56.dp)
                .border(
                    width = 1.dp,
                    color = if (isPinValid || loginState.pin.isEmpty()) Color(0xFFBDBDBD) else Color.Red,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(Color.White, RoundedCornerShape(12.dp))
                .clickable { isPhoneInputMode = false }
        ) {
            // PIN masked display
            Text(
                text = "*".repeat(loginState.pin.length),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                fontFamily = FontFamily.Default,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            )
            // Fingerprint icon
            IconButton(
                onClick = onFingerprint,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_fingerprint),
                    contentDescription = "Fingerprint",
                    tint = BkashPink,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Error message
        if (loginState.showError) {
            Text(
                text = loginState.errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                fontFamily = FontFamily.Default,
                modifier = Modifier.padding(start = 24.dp, top = 8.dp)
            )
        }

        // Forgot PIN
        Text(
            text = "Forgot PIN? Try PIN Reset",
            fontSize = 14.sp,
            color = BkashPink,
            fontFamily = FontFamily.Default,
            modifier = Modifier
                .padding(start = 24.dp, top = 8.dp)
                .clickable { onForgotPin() }
        )

        Spacer(Modifier.height(16.dp))

        // Numeric Keypad - Now handles both phone and PIN input
        NumericKeypad(
            phoneNumber = loginState.phoneNumber,
            pin = loginState.pin,
            isPhoneInputMode = isPhoneInputMode,
            onPhoneChange = { 
                if (it.length <= 16) {
                    loginViewModel.updatePhoneNumber(it)
                }
            },
            onPinChange = {
                if (it.length <= 6) {
                    loginViewModel.updatePin(it)
                }
            },
            onSubmit = {
                if (canSubmit) {
                    onLoginSuccess()
                } else {
                    // Show appropriate error message
                    when {
                        !isPhoneValid -> loginViewModel.clearError()
                        !isPinValid -> loginViewModel.clearError()
                        else -> loginViewModel.clearError()
                    }
                }
            },
            isLoading = loginState.isLoading
        )
    }
}

@Composable
fun NumericKeypad(
    phoneNumber: String,
    pin: String,
    isPhoneInputMode: Boolean,
    onPhoneChange: (String) -> Unit,
    onPinChange: (String) -> Unit,
    onSubmit: () -> Unit,
    isLoading: Boolean = false
) {
    val keys = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("", "0", "X")
    )
    
    val currentInput = if (isPhoneInputMode) phoneNumber else pin
    val maxLength = if (isPhoneInputMode) 16 else 6
    val isValid = if (isPhoneInputMode) {
        currentInput.length >= 11 && (
            currentInput.matches(Regex("^\\+88\\s\\d{11}$")) ||
            currentInput.matches(Regex("^\\+88\\d{11}$")) ||
            currentInput.matches(Regex("^\\d{11}$")) ||
            currentInput.matches(Regex("^\\d{13}$"))
        )
    } else {
        pin.length in 4..6
    }
    val canSubmit = isValid && !isLoading

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5))
            .padding(top = 8.dp)
    ) {
        // Input mode indicator
        Text(
            text = if (isPhoneInputMode) "Enter Phone Number" else "Enter PIN",
            fontSize = 14.sp,
            color = Color(0xFF757575),
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )
        
        // Next button above keypad
        Button(
            onClick = onSubmit,
            enabled = canSubmit,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (canSubmit) BkashPink else Color(0xFFE0E0E0),
                contentColor = if (canSubmit) Color.White else Color(0xFF9E9E9E)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = if (isLoading) "Logging in..." else "Next →",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Default
            )
        }

        // Keypad grid
        for (row in keys) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (key in row) {
                    when (key) {
                        "" -> Spacer(Modifier.size(64.dp))
                        "X" -> IconButton(
                            onClick = {
                                if (currentInput.isNotEmpty() && !isLoading) {
                                    val newValue = currentInput.dropLast(1)
                                    if (isPhoneInputMode) onPhoneChange(newValue) else onPinChange(newValue)
                                }
                            },
                            modifier = Modifier
                                .size(64.dp)
                                .background(Color.Transparent, CircleShape)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_clear),
                                contentDescription = "Clear",
                                tint = if (isLoading) Color(0xFFBDBDBD) else Color(0xFF212121),
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        else -> Button(
                            onClick = { 
                                if (currentInput.length < maxLength && !isLoading) {
                                    val newValue = currentInput + key
                                    if (isPhoneInputMode) onPhoneChange(newValue) else onPinChange(newValue)
                                }
                            },
                            modifier = Modifier
                                .size(64.dp)
                                .background(Color.Transparent, CircleShape),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = if (isLoading) Color(0xFFBDBDBD) else Color(0xFF212121)
                            ),
                            shape = CircleShape,
                            contentPadding = PaddingValues(0.dp),
                            enabled = !isLoading
                        ) {
                            Text(
                                text = key,
                                fontSize = 20.sp,
                                color = if (isLoading) Color(0xFFBDBDBD) else Color(0xFF212121),
                                fontFamily = FontFamily.Default
                            )
                        }
                    }
                }
            }
        }
    }
}
