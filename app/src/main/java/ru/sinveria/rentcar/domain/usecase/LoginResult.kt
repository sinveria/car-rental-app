package ru.sinveria.rentcar.domain.usecase

data class LoginResult(
    val isSuccess: Boolean,
    val errorMessage: String? = null,
    val token: String? = null
)