package com.example.bkashclone.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bkashclone.data.repository.AuthRepository
import com.example.bkashclone.domain.model.HomeData
import com.example.bkashclone.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    init {
        loadUserData()
        loadHomeData()
    }
    
    private fun loadUserData() {
        viewModelScope.launch {
            authRepository.getUser().collect { user ->
                _uiState.value = _uiState.value.copy(
                    user = user,
                    isLoading = false
                )
                // Update home data with actual user balance
                if (user != null) {
                    updateHomeDataWithUserBalance(user)
                }
            }
        }
    }
    
    private fun updateHomeDataWithUserBalance(user: User) {
        val currentHomeData = _uiState.value.homeData
        val updatedHomeData = currentHomeData?.copy(
            balance = "৳${user.balance.toInt()}"
        ) ?: HomeData(
            balance = "৳${user.balance.toInt()}",
            services = emptyList(),
            offers = emptyList(),
            bundles = emptyList()
        )
        
        _uiState.value = _uiState.value.copy(
            homeData = updatedHomeData
        )
    }
    
    private fun loadHomeData() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        
        viewModelScope.launch {
            try {
                // Get user data to set the correct balance
                val user = authRepository.getUser().firstOrNull()
                val balance = if (user != null) "৳${user.balance.toInt()}" else "৳0"
                
                val mockHomeData = HomeData(
                    balance = balance,
                    services = emptyList(),
                    offers = emptyList(),
                    bundles = emptyList()
                )
                
                _uiState.value = _uiState.value.copy(
                    homeData = mockHomeData,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to load home data",
                    isLoading = false
                )
            }
        }
    }
    
    fun refreshData() {
        loadHomeData()
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = "")
    }
    
    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _uiState.value = _uiState.value.copy(
                user = null,
                homeData = null
            )
        }
    }
}

data class HomeUiState(
    val user: User? = null,
    val homeData: HomeData? = null,
    val isLoading: Boolean = true,
    val errorMessage: String = "",
    val showBalance: Boolean = false
)
