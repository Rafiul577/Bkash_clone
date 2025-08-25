package com.example.bkashclone.data.api

import com.example.bkashclone.domain.model.AuthResponse
import com.example.bkashclone.domain.model.HomeData
import com.example.bkashclone.domain.model.LoginRequest
import com.example.bkashclone.domain.model.RegisterRequest
import com.example.bkashclone.domain.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
    
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>
    
    @GET("user/profile")
    suspend fun getProfile(@Header("Authorization") token: String): Response<User>
    
    @GET("user/home-data")
    suspend fun getHomeData(@Header("Authorization") token: String): Response<HomeData>
}
