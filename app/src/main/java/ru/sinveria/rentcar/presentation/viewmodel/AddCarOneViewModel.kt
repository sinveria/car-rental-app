package ru.sinveria.rentcar.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.sinveria.rentcar.data.repository.SharedCarDataRepository
import ru.sinveria.rentcar.domain.usecase.ValidateAddressUseCase
import javax.inject.Inject

@HiltViewModel
class AddCarOneViewModel @Inject constructor(
    private val validateAddressUseCase: ValidateAddressUseCase,
    private val sharedCarDataRepository: SharedCarDataRepository
) : ViewModel() {

    val addCarData = sharedCarDataRepository.addCarData
    var address by mutableStateOf("")
    var addressError by mutableStateOf("")
    var addressTouched by mutableStateOf(false)

    val isNextEnabled: Boolean
        get() = address.isNotBlank() && addressError.isEmpty()

    fun onAddressChange(value: String) {
        address = value
        validateAddress()
    }

    fun onAddressFocusChanged(focused: Boolean) {
        if (!focused && !addressTouched) {
            addressTouched = true
            validateAddress()
        }
    }

    private fun validateAddress() {
        addressError = validateAddressUseCase(address)
    }

    fun onNext() {
        addressTouched = true
        validateAddress()
        if (isNextEnabled) {
            sharedCarDataRepository.updateStepOne(address)
        }
    }
}