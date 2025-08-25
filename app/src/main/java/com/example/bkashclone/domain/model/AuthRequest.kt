package com.example.bkashclone.domain.model

data class LoginRequest(
    val phone: String,
    val pin: String
)

data class RegisterRequest(
    val name: String,
    val phone: String,
    val pin: String
)

data class AuthResponse(
    val message: String,
    val token: String,
    val user: User
)
