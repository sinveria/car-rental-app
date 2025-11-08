package ru.sinveria.rentcar.domain.usecase

import javax.inject.Inject

class ValidateAddressUseCase @Inject constructor() {
    operator fun invoke(address: String): String {
        return when {
            address.isBlank() -> "Укажите адрес"
            address.length < 5 -> "Адрес слишком короткий"
            else -> ""
        }
    }
}

class ValidateBrandUseCase @Inject constructor() {
    operator fun invoke(brand: String): String {
        return when {
            brand.isBlank() -> "Укажите марку"
            brand.length < 2 -> "Название марки слишком короткое"
            else -> ""
        }
    }
}

class ValidateModelUseCase @Inject constructor() {
    operator fun invoke(model: String): String {
        return when {
            model.isBlank() -> "Укажите модель"
            model.length < 1 -> "Название модели слишком короткое"
            else -> ""
        }
    }
}

class ValidateYearUseCase @Inject constructor() {
    operator fun invoke(yearStr: String): String {
        val year = yearStr.toIntOrNull()
        return when {
            yearStr.isBlank() -> "Укажите год"
            year == null -> "Год должен быть числом"
            else -> ""
        }
    }
}

class ValidateMileageUseCase @Inject constructor() {
    operator fun invoke(mileageStr: String): String {
        val mileage = mileageStr.toIntOrNull()
        return when {
            mileageStr.isBlank() -> "Укажите пробег"
            mileage == null -> "Пробег должен быть числом"
            mileage < 0 -> "Пробег не может быть отрицательным"
            mileage > 1_000_000 -> "Проверьте значение пробега"
            else -> ""
        }
    }
}

class ValidateDescriptionUseCase @Inject constructor() {
    operator fun invoke(description: String): String {
        return when {
            description.isBlank() -> "Введите описание автомобиля"
            description.length < 10 -> "Описание должно содержать минимум 10 символов"
            description.length > 1000 -> "Описание слишком длинное"
            else -> ""
        }
    }
}