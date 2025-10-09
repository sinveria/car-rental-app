package ru.sinveria.rentcar.domain.usecase

import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String): String {
        return if (password.isEmpty()) "Пароль обязателен" else ""
    }
}