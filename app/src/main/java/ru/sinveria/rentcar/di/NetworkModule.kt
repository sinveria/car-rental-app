package ru.sinveria.rentcar.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.sinveria.rentcar.data.repository.ConnectionRepositoryImpl
import ru.sinveria.rentcar.domain.repository.ConnectionRepository
import ru.sinveria.rentcar.utils.NetworkUtils
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkUtils(@ApplicationContext context: Context): NetworkUtils {
        return NetworkUtils(context)
    }

    @Provides
    @Singleton
    fun provideConnectionRepository(networkUtils: NetworkUtils): ConnectionRepository {
        return ConnectionRepositoryImpl(networkUtils)
    }
}