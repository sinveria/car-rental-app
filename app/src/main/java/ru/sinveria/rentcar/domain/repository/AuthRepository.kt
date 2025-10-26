package ru.sinveria.rentcar.domain.repository

import ru.sinveria.rentcar.domain.usecase.LoginResult

interface AuthRepository {
    suspend fun login(email: String, password: String): LoginResult

    suspend fun register(
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
    ): LoginResult

    suspend fun getCurrentToken(): String?
    suspend fun logout()
}