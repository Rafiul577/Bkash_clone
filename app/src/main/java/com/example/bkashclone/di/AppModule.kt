package com.example.bkashclone.di

import android.content.Context
import com.example.bkashclone.data.local.DataStoreManager
import com.example.bkashclone.data.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context
    
    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }
    
    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: com.example.bkashclone.data.api.ApiService,
        dataStoreManager: DataStoreManager
    ): AuthRepository {
        return AuthRepository(apiService, dataStoreManager)
    }
}
