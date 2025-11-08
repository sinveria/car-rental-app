package ru.sinveria.rentcar.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.sinveria.rentcar.data.repository.LocalRepositoryImpl
import ru.sinveria.rentcar.domain.repository.AuthRepository
import ru.sinveria.rentcar.domain.repository.CarRepository
import ru.sinveria.rentcar.domain.repository.ConnectionRepository
import ru.sinveria.rentcar.domain.usecase.CheckConnectionUseCase
import ru.sinveria.rentcar.domain.usecase.CheckPermissionsUseCase
import ru.sinveria.rentcar.domain.usecase.CopyImageUseCase
import ru.sinveria.rentcar.domain.usecase.GetCarsUseCase
import ru.sinveria.rentcar.domain.usecase.GetCurrentUserUseCase
import ru.sinveria.rentcar.domain.usecase.GetUserUseCase
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
import ru.sinveria.rentcar.domain.usecase.RegisterUseCase
import ru.sinveria.rentcar.domain.usecase.SaveCarUseCase
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

    @Provides
    @Singleton
    fun provideCopyImageUseCase(
        context: Context
    ): CopyImageUseCase {
        return CopyImageUseCase(context)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(
        authRepository: AuthRepository,
        copyImageUseCase: CopyImageUseCase
    ): RegisterUseCase {
        return RegisterUseCase(authRepository, copyImageUseCase)
    }

    @Provides
    @Singleton
    fun provideGetUserUseCase(
        localRepository: LocalRepositoryImpl
    ): GetUserUseCase {
        return GetUserUseCase(localRepository)
    }

    @Provides
    @Singleton
    fun provideSaveCarUseCase(carRepository: CarRepository): SaveCarUseCase {
        return SaveCarUseCase(carRepository)
    }

    @Provides
    @Singleton
    fun provideGetCarsUseCase(carRepository: CarRepository): GetCarsUseCase {
        return GetCarsUseCase(carRepository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentUserUseCase(authRepository: AuthRepository): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(authRepository)
    }
}