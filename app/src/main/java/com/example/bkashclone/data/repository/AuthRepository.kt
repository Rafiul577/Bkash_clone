package com.example.bkashclone.data.repository

import com.example.bkashclone.data.api.ApiService
import com.example.bkashclone.data.local.DataStoreManager
import com.example.bkashclone.domain.model.AuthResponse
import com.example.bkashclone.domain.model.LoginRequest
import com.example.bkashclone.domain.model.RegisterRequest
import com.example.bkashclone.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager
) {
    
    suspend fun login(phone: String, pin: String): Result<AuthResponse> {
        return try {
            val request = LoginRequest(phone = phone, pin = pin)
            val response = apiService.login(request)
            
            if (response.isSuccessful) {
                response.body()?.let { authResponse ->
                    // Save token and user data
                    dataStoreManager.saveAuthToken(authResponse.token)
                    dataStoreManager.saveUser(authResponse.user)
                    Result.success(authResponse)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Login failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun register(name: String, phone: String, pin: String): Result<AuthResponse> {
        return try {
            val request = RegisterRequest(name = name, phone = phone, pin = pin)
            val response = apiService.register(request)
            
            if (response.isSuccessful) {
                response.body()?.let { authResponse ->
                    // Save token and user data
                    dataStoreManager.saveAuthToken(authResponse.token)
                    dataStoreManager.saveUser(authResponse.user)
                    Result.success(authResponse)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Registration failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun logout() {
        dataStoreManager.clearAuthToken()
        dataStoreManager.clearUser()
    }
    
    fun getAuthToken(): Flow<String?> {
        return dataStoreManager.getAuthToken()
    }
    
    fun getUser(): Flow<User?> {
        return dataStoreManager.getUser()
    }
    
    suspend fun isLoggedIn(): Boolean {
        return dataStoreManager.getAuthToken().firstOrNull() != null
    }
}
