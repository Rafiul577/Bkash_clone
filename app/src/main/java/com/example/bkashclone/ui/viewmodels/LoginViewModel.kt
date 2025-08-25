package com.example.bkashclone.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bkashclone.data.repository.AuthRepository
import com.example.bkashclone.domain.model.AuthResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    
    fun updatePhoneNumber(phone: String) {
        Log.d("LoginViewModel", "Updating phone number: $phone")
        _uiState.value = _uiState.value.copy(
            phoneNumber = phone,
            errorMessage = "",
            showError = false
        )
    }
    
    fun updatePin(pin: String) {
        Log.d("LoginViewModel", "Updating PIN: ${"*".repeat(pin.length)}")
        _uiState.value = _uiState.value.copy(
            pin = pin,
            errorMessage = "",
            showError = false
        )
    }
    
    fun login() {
        val currentState = _uiState.value
        Log.d("LoginViewModel", "Login attempt - Phone: ${currentState.phoneNumber}, PIN: ${"*".repeat(currentState.pin.length)}")
        
        // Validate inputs
        if (!isValidPhoneNumber(currentState.phoneNumber)) {
            Log.d("LoginViewModel", "Phone number validation failed")
            _uiState.value = currentState.copy(
                errorMessage = "Please enter a valid phone number",
                showError = true
            )
            return
        }
        
        if (!isValidPin(currentState.pin)) {
            Log.d("LoginViewModel", "PIN validation failed")
            _uiState.value = currentState.copy(
                errorMessage = "PIN must be 4-6 digits",
                showError = true
            )
            return
        }
        
        Log.d("LoginViewModel", "Validation passed, starting login process")
        
        // Start login process
        _uiState.value = currentState.copy(
            isLoading = true,
            errorMessage = "",
            showError = false
        )
        
        viewModelScope.launch {
            try {
                Log.d("LoginViewModel", "Calling authRepository.login()")
                val result = authRepository.login(currentState.phoneNumber, currentState.pin)
                
                result.fold(
                    onSuccess = { authResponse ->
                        Log.d("LoginViewModel", "Login successful: ${authResponse.message}")
                        _uiState.value = currentState.copy(
                            isLoading = false,
                            loginSuccess = true,
                            authResponse = authResponse
                        )
                    },
                    onFailure = { exception ->
                        Log.e("LoginViewModel", "Login failed: ${exception.message}", exception)
                        _uiState.value = currentState.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "Login failed",
                            showError = true
                        )
                    }
                )
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Exception during login: ${e.message}", e)
                _uiState.value = currentState.copy(
                    isLoading = false,
                    errorMessage = "Network error. Please try again.",
                    showError = true
                )
            }
        }
    }
    
    fun clearError() {
        Log.d("LoginViewModel", "Clearing error")
        _uiState.value = _uiState.value.copy(
            errorMessage = "",
            showError = false
        )
    }
    
    fun resetLoginSuccess() {
        Log.d("LoginViewModel", "Resetting login success")
        _uiState.value = _uiState.value.copy(
            loginSuccess = false,
            authResponse = null
        )
    }
    
    private fun isValidPhoneNumber(phone: String): Boolean {
        val isValid = phone.length >= 11 && (
            phone.matches(Regex("^\\+88\\s\\d{11}$")) || // +88 01536087049
            phone.matches(Regex("^\\+88\\d{11}$")) || // +8801536087049
            phone.matches(Regex("^\\d{11}$")) || // 01536087049
            phone.matches(Regex("^\\d{13}$")) // 8801536087049
        )
        Log.d("LoginViewModel", "Phone validation: $phone -> $isValid")
        return isValid
    }
    
    private fun isValidPin(pin: String): Boolean {
        val isValid = pin.length in 4..6 && pin.matches(Regex("^\\d+$"))
        Log.d("LoginViewModel", "PIN validation: ${"*".repeat(pin.length)} -> $isValid")
        return isValid
    }
}

data class LoginUiState(
    val phoneNumber: String = "01536087049",
    val pin: String = "",
    val isLoading: Boolean = false,
    val showError: Boolean = false,
    val errorMessage: String = "",
    val loginSuccess: Boolean = false,
    val authResponse: AuthResponse? = null
)
