package ru.sinveria.rentcar.domain.usecase

import javax.inject.Inject

class ValidateNameUseCase @Inject constructor() {
    operator fun invoke(name: String, fieldName: String): String {
        return if (name.isEmpty()) "$fieldName обязателен" else ""
    }
}