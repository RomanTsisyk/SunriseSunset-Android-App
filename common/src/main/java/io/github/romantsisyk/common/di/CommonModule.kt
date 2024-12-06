package io.github.romantsisyk.common.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.romantsisyk.common.domain.repository.SunriseSunsetRepositoryInterface
import io.github.romantsisyk.sunrisesunsetsdk.SunriseSunsetSDK
import io.github.romantsisyk.common.data.repository.SunriseSunsetRepository
import io.github.romantsisyk.common.domain.usecase.GetSunriseSunsetUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    @Singleton
    fun provideSunriseSunsetSDK(): SunriseSunsetSDK {
        return try {
            SunriseSunsetSDK.builder()
                .setBaseUrl("https://api.sunrise-sunset.org/") // Example: Set base URL
                .setTimeoutSeconds(30) // Example: Set timeout in seconds
                .build().also {
                    Log.d("CommonModule", "SunriseSunsetSDK initialized successfully.")
                }
        } catch (e: Exception) {
            Log.e("CommonModule", "Error initializing SunriseSunsetSDK: ${e.message}", e)
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
