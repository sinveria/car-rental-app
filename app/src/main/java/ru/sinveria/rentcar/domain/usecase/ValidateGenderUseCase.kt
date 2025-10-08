package ru.sinveria.rentcar.domain.usecase

import javax.inject.Inject

class ValidateGenderUseCase @Inject constructor() {
    operator fun invoke(gender: String): String {
        return if (gender.isEmpty()) "Выбор пола обязателен" else ""
    }
}