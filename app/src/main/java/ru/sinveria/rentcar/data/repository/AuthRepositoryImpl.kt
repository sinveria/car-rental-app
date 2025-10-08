package ru.sinveria.rentcar.data.repository

import ru.sinveria.rentcar.domain.repository.AuthRepository
import ru.sinveria.rentcar.domain.usecase.LoginResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    override suspend fun login(email: String, password: String): LoginResult {
        // заглушка
        return if (email.isNotEmpty() && password.isNotEmpty()) {
            LoginResult(isSuccess = true)
        } else {
            LoginResult(isSuccess = false, errorMessage = "Неверные данные")
        }
    }
}