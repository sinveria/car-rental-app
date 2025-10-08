package ru.sinveria.rentcar.domain.usecase

import javax.inject.Inject

class ValidateConfirmPasswordUseCase @Inject constructor() {
    operator fun invoke(password: String, confirmPassword: String): String {
        return when {
            confirmPassword.isEmpty() -> "Подтверждение пароля обязательно"
            password != confirmPassword -> "Пароли не совпадают"
            else -> ""
        }
    }
}