package ru.sinveria.rentcar.domain.repository

import ru.sinveria.rentcar.domain.usecase.LoginResult

interface AuthRepository {
    suspend fun login(email: String, password: String): LoginResult

}