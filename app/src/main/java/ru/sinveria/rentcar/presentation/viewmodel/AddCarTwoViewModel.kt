package ru.sinveria.rentcar.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.sinveria.rentcar.data.repository.SharedCarDataRepository
import ru.sinveria.rentcar.domain.usecase.ValidateBrandUseCase
import ru.sinveria.rentcar.domain.usecase.ValidateDescriptionUseCase
import ru.sinveria.rentcar.domain.usecase.ValidateModelUseCase
import ru.sinveria.rentcar.domain.usecase.ValidateYearUseCase
import ru.sinveria.rentcar.domain.usecase.ValidateMileageUseCase
import javax.inject.Inject

@HiltViewModel
class AddCarTwoViewModel @Inject constructor(
    private val validateBrandUseCase: ValidateBrandUseCase,
    private val validateModelUseCase: ValidateModelUseCase,
    private val validateYearUseCase: ValidateYearUseCase,
    private val validateMileageUseCase: ValidateMileageUseCase,
    private val validateDescriptionUseCase: ValidateDescriptionUseCase,
    private val sharedCarDataRepository: SharedCarDataRepository
) : ViewModel() {

    var year by mutableStateOf("")
    var brand by mutableStateOf("")
    var model by mutableStateOf("")
    var transmission by mutableStateOf("")
    var mileage by mutableStateOf("")
    var description by mutableStateOf("")

    var yearError by mutableStateOf("")
    var brandError by mutableStateOf("")
    var modelError by mutableStateOf("")
    var transmissionError by mutableStateOf("")
    var mileageError by mutableStateOf("")
    var descriptionError by mutableStateOf("")

    var yearTouched by mutableStateOf(false)
    var brandTouched by mutableStateOf(false)
    var modelTouched by mutableStateOf(false)
    var transmissionTouched by mutableStateOf(false)
    var mileageTouched by mutableStateOf(false)
    var descriptionTouched by mutableStateOf(false)

    val isSubmitEnabled: Boolean
        get() = year.isNotBlank() &&
                brand.isNotBlank() &&
                model.isNotBlank() &&
                transmission.isNotBlank() &&
                mileage.isNotBlank() &&
                description.isNotBlank() &&
                yearError.isEmpty() &&
                brandError.isEmpty() &&
                modelError.isEmpty() &&
                transmissionError.isEmpty() &&
                mileageError.isEmpty() &&
                descriptionError.isEmpty()

    fun onYearChange(value: String) {
        year = value
        if (yearTouched) validateYear()
    }

    fun onBrandChange(value: String) {
        brand = value
        if (brandTouched) validateBrand()
    }

    fun onModelChange(value: String) {
        model = value
        if (modelTouched) validateModel()
    }

    fun onTransmissionChange(value: String) {
        transmission = value
        if (transmissionTouched) validateTransmission()
    }

    fun onMileageChange(value: String) {
        mileage = value
        if (mileageTouched) validateMileage()
    }

    fun onDescriptionChange(value: String) {
        description = value
        if (descriptionTouched) validateDescription()
    }

    fun onYearFocusChanged(focused: Boolean) {
        if (!focused && !yearTouched) {
            yearTouched = true
            validateYear()
        }
    }

    fun onBrandFocusChanged(focused: Boolean) {
        if (!focused && !brandTouched) {
            brandTouched = true
            validateBrand()
        }
    }

    fun onModelFocusChanged(focused: Boolean) {
        if (!focused && !modelTouched) {
            modelTouched = true
            validateModel()
        }
    }

    fun onMileageFocusChanged(focused: Boolean) {
        if (!focused && !mileageTouched) {
            mileageTouched = true
            validateMileage()
        }
    }

    fun onDescriptionFocusChanged(focused: Boolean) {
        if (!focused && !descriptionTouched) {
            descriptionTouched = true
            validateDescription()
        }
    }

    private fun validateYear() {
        yearError = validateYearUseCase(year)
    }

    private fun validateBrand() {
        brandError = validateBrandUseCase(brand)
    }

    private fun validateModel() {
        modelError = validateModelUseCase(model)
    }

    private fun validateTransmission() {
        transmissionError = if (transmission.isBlank()) "Выберите тип трансмиссии" else ""
    }

    private fun validateMileage() {
        mileageError = validateMileageUseCase(mileage)
    }

    private fun validateDescription() {
        descriptionError = validateDescriptionUseCase(description)
    }

    fun onSubmit() {
        yearTouched = true
        brandTouched = true
        modelTouched = true
        transmissionTouched = true
        mileageTouched = true
        descriptionTouched = true

        validateYear()
        validateBrand()
        validateModel()
        validateTransmission()
        validateMileage()
        validateDescription()

        if (isSubmitEnabled) {
            val parsedYear = year.toIntOrNull() ?: 0
            val parsedMileage = mileage.toIntOrNull() ?: 0

            sharedCarDataRepository.updateStepTwo(
                year = parsedYear,
                brand = brand,
                model = model,
                transmission = transmission,
                mileage = parsedMileage,
                description = description
            )
        }
    }
}