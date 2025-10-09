package ru.sinveria.rentcar.domain.usecase

import javax.inject.Inject

class ValidateIssueDateUseCase @Inject constructor() {
    operator fun invoke(date: String): String {
        return when {
            date.isEmpty() -> "Дата выдачи обязательна"
            !isValidDateFormat(date) -> "Некорректный формат даты (DD/MM/YYYY)"
            !isValidDate(date) -> "Введите корректную дату выдачи"
            else -> ""
        }
    }

    private fun isValidDateFormat(date: String): Boolean {
        val pattern = "^\\d{2}/\\d{2}/\\d{4}$".toRegex()
        return pattern.matches(date)
    }

    private fun isValidDate(date: String): Boolean {
        if (!isValidDateFormat(date)) return false

        val parts = date.split("/")
        if (parts.size != 3) return false

        val day = parts[0].toIntOrNull() ?: return false
        val month = parts[1].toIntOrNull() ?: return false
        val year = parts[2].toIntOrNull() ?: return false

        if (year < 1900 || year > 2100) return false
        if (month < 1 || month > 12) return false

        val daysInMonth = when (month) {
            2 -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }

        return day in 1..daysInMonth
    }
}