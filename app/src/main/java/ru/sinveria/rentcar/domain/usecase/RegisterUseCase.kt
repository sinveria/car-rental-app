package ru.sinveria.rentcar.domain.usecase

import ru.sinveria.rentcar.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val copyImageUseCase: CopyImageUseCase
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        lastName: String,
        firstName: String,
        middleName: String,
        birthDate: String,
        gender: String,
        licenseNumber: String,
        licenseIssueDate: String,
        licensePhotoPath: String?,
        passportPhotoPath: String?,
        profilePhotoPath: String?
    ): LoginResult {

        val permanentLicensePhoto = licensePhotoPath?.let { copyImageUseCase(it) }
        val permanentPassportPhoto = passportPhotoPath?.let { copyImageUseCase(it) }
        val permanentProfilePhoto = profilePhotoPath?.let { copyImageUseCase(it) }

        return authRepository.register(
            email = email,
            password = password,
            lastName = lastName,
            firstName = firstName,
            middleName = middleName,
            birthDate = birthDate,
            gender = gender,
            licenseNumber = licenseNumber,
            licenseIssueDate = licenseIssueDate,
            licensePhotoPath = permanentLicensePhoto,
            passportPhotoPath = permanentPassportPhoto,
            profilePhotoPath = permanentProfilePhoto
        )
    }
}