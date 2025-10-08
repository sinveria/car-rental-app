package ru.sinveria.rentcar.domain.usecase

import javax.inject.Inject

class ValidateTermsUseCase @Inject constructor() {
    operator fun invoke(agreed: Boolean): String {
        return if (!agreed) "Необходимо согласиться с условиями обслуживания и политикой конфиденциальности" else ""
    }
}