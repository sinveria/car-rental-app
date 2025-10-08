package ru.sinveria.rentcar.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.sinveria.rentcar.domain.repository.ConnectionRepository
import ru.sinveria.rentcar.domain.usecase.CheckConnectionUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideCheckConnectionUseCase(
        connectionRepository: ConnectionRepository
    ): CheckConnectionUseCase {
        return CheckConnectionUseCase(connectionRepository)
    }
}