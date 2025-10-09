package ru.sinveria.rentcar.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.sinveria.rentcar.domain.repository.AuthRepository
import ru.sinveria.rentcar.domain.repository.ConnectionRepository
import ru.sinveria.rentcar.domain.usecase.CheckConnectionUseCase
import ru.sinveria.rentcar.domain.usecase.CheckPermissionsUseCase
import ru.sinveria.rentcar.domain.usecase.ImagePickerUseCase
import ru.sinveria.rentcar.domain.usecase.LoginUseCase
import ru.sinveria.rentcar.domain.usecase.ValidateBirthDateUseCase
import ru.sinveria.rentcar.domain.usecase.ValidateConfirmPasswordUseCase
import ru.sinveria.rentcar.domain.usecase.ValidateEmailUseCase
import ru.sinveria.rentcar.domain.usecase.ValidateGenderUseCase
import ru.sinveria.rentcar.domain.usecase.ValidateIssueDateUseCase
import ru.sinveria.rentcar.domain.usecase.ValidateLicenseNumberUseCase
import ru.sinveria.rentcar.domain.usecase.ValidateNameUseCase
import ru.sinveria.rentcar.domain.usecase.ValidatePasswordUseCase
import ru.sinveria.rentcar.domain.usecase.ValidateTermsUseCase
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

    @Provides
    @Singleton
    fun provideValidateConfirmPasswordUseCase(): ValidateConfirmPasswordUseCase {
        return ValidateConfirmPasswordUseCase()
    }

    @Provides
    @Singleton
    fun provideValidateTermsUseCase(): ValidateTermsUseCase {
        return ValidateTermsUseCase()
    }

    @Provides
    @Singleton
    fun provideValidateNameUseCase(): ValidateNameUseCase {
        return ValidateNameUseCase()
    }

    @Provides
    @Singleton
    fun provideValidateBirthDateUseCase(): ValidateBirthDateUseCase {
        return ValidateBirthDateUseCase()
    }

    @Provides
    @Singleton
    fun provideValidateGenderUseCase(): ValidateGenderUseCase {
        return ValidateGenderUseCase()
    }

    @Provides
    @Singleton
    fun provideValidateLicenseNumberUseCase(): ValidateLicenseNumberUseCase {
        return ValidateLicenseNumberUseCase()
    }

    @Provides
    @Singleton
    fun provideValidateIssueDateUseCase(): ValidateIssueDateUseCase {
        return ValidateIssueDateUseCase()
    }

    @Provides
    @Singleton
    fun provideCheckPermissionsUseCase(): CheckPermissionsUseCase {
        return CheckPermissionsUseCase()
    }

    @Provides
    @Singleton
    fun provideImagePickerUseCase(): ImagePickerUseCase {
        return ImagePickerUseCase()
    }
}