package ru.sinveria.rentcar.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.sinveria.rentcar.domain.repository.AuthRepository
import ru.sinveria.rentcar.domain.repository.ConnectionRepository
import ru.sinveria.rentcar.domain.usecase.CheckConnectionUseCase
import ru.sinveria.rentcar.domain.usecase.LoginUseCase
import ru.sinveria.rentcar.domain.usecase.ValidateEmailUseCase
import ru.sinveria.rentcar.domain.usecase.ValidatePasswordUseCase
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

    @Provides
    @Singleton
    fun provideValidateEmailUseCase(): ValidateEmailUseCase {
        return ValidateEmailUseCase()
    }

    @Provides
    @Singleton
    fun provideValidatePasswordUseCase(): ValidatePasswordUseCase {
        return ValidatePasswordUseCase()
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }
}