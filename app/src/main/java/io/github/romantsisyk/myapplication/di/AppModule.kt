package io.github.romantsisyk.myapplication.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.romantsisyk.myapplication.data.repository.SunriseSunsetRepository
import io.github.romantsisyk.myapplication.domain.repository.SunriseSunsetRepositoryInterface
import io.github.romantsisyk.myapplication.domain.usecase.GetSunriseSunsetUseCase
import io.github.romantsisyk.sunrisesunsetsdk.SunriseSunsetSDK
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSunriseSunsetSDK(): SunriseSunsetSDK {
        return try {
            SunriseSunsetSDK.builder()
                .setBaseUrl("https://api.sunrise-sunset.org/") // Example: Add base URL
                .setTimeoutSeconds(30) // Example: Set a timeout in seconds
                .build().also {
                    Log.e("AppModule","SunriseSunsetSDK initialized successfully with builder configuration")
                }
        } catch (e: Exception) {
            Log.e("AppModule", "Error initializing SunriseSunsetSDK: ${e.message}", e)
            throw e
        }
    }


    @Provides
    @Singleton
    fun provideRepository(sdk: SunriseSunsetSDK): SunriseSunsetRepositoryInterface {
        return SunriseSunsetRepository(sdk)
    }

    @Provides
    @Singleton
    fun provideUseCase(repository: SunriseSunsetRepositoryInterface): GetSunriseSunsetUseCase {
        return GetSunriseSunsetUseCase(repository)
    }
}