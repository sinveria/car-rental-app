package ru.sinveria.rentcar.data.repository

import ru.sinveria.rentcar.domain.repository.AuthRepository
import ru.sinveria.rentcar.data.local.entity.UserEntity
import ru.sinveria.rentcar.data.local.entity.AuthTokenEntity
import ru.sinveria.rentcar.domain.usecase.LoginResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val localRepository: LocalRepositoryImpl
) : AuthRepository {

    override suspend fun login(email: String, password: String): LoginResult {
        return try {
            val user = localRepository.getUserByEmail(email)

            if (user != null && user.password == password) {
                val tokenEntity = AuthTokenEntity(
                    userId = user.id,
                    token = "generated_token_${System.currentTimeMillis()}",
                    expiresAt = System.currentTimeMillis() + 24 * 60 * 60 * 1000
                )
                localRepository.saveToken(tokenEntity)

                LoginResult(isSuccess = true, token = tokenEntity.token)
            } else {
                LoginResult(isSuccess = false, errorMessage = "Неверные данные")
            }
        } catch (e: Exception) {
            LoginResult(isSuccess = false, errorMessage = "Ошибка авторизации: ${e.message}")
        }
    }

    override suspend fun register(
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
        return try {
            val existingUser = localRepository.getUserByEmail(email)
            if (existingUser != null) {
                return LoginResult(isSuccess = false, errorMessage = "Пользователь с таким email уже существует")
            }

            val userEntity = UserEntity(
                email = email,
                password = password,
                lastName = lastName,
                firstName = firstName,
                middleName = middleName,
                birthDate = birthDate,
                gender = gender,
                licenseNumber = licenseNumber,
                licenseIssueDate = licenseIssueDate,
                licensePhotoPath = licensePhotoPath,
                passportPhotoPath = passportPhotoPath,
                profilePhotoPath = profilePhotoPath
            )

            localRepository.saveUser(userEntity)

            val tokenEntity = AuthTokenEntity(
                userId = userEntity.id,
                token = "generated_token_${System.currentTimeMillis()}",
                expiresAt = System.currentTimeMillis() + 24 * 60 * 60 * 1000
            )
            localRepository.saveToken(tokenEntity)

            LoginResult(isSuccess = true, token = tokenEntity.token)
        } catch (e: Exception) {
            LoginResult(isSuccess = false, errorMessage = "Ошибка регистрации: ${e.message}")
        }
    }

    override suspend fun getCurrentToken(): String? {
        val tokenEntity = localRepository.getCurrentToken()
        return tokenEntity?.token
    }

    override suspend fun logout() {
        localRepository.clearTokens()
    }
}