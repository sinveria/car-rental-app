package ru.sinveria.rentcar.domain.usecase

import android.util.Patterns
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {
    operator fun invoke(email: String): String {
        return when {
            email.isEmpty() -> "Электронная почта обязательна"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Введите корректный адрес электронной почты"
            else -> ""
        }
    }
}