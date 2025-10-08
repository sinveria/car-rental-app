package ru.sinveria.rentcar.domain.usecase

import javax.inject.Inject

class ValidateLicenseNumberUseCase @Inject constructor() {
    operator fun invoke(license: String): String {
        return when {
            license.isEmpty() -> "Номер водительского удостоверения обязателен"
            !isValidLicenseNumber(license) -> "Номер должен содержать 10 цифр"
            else -> ""
        }
    }

    private fun isValidLicenseNumber(license: String): Boolean {
        return license.length == 10 && license.all { it.isDigit() }
    }
}