package ru.sinveria.rentcar.presentation.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.sinveria.rentcar.domain.model.RegistrationData
import ru.sinveria.rentcar.domain.usecase.RegisterUseCase
import javax.inject.Inject

@HiltViewModel
class RegistrationSharedViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _registrationData = MutableStateFlow(RegistrationData())
    val registrationData: StateFlow<RegistrationData> = _registrationData

    private val _registrationState = MutableStateFlow<RegistrationState>(RegistrationState.Idle)
    val registrationState: StateFlow<RegistrationState> = _registrationState

    init {
        println("🔄 DEBUG: RegistrationSharedViewModel CREATED")
        println("📊 DEBUG: Initial data: ${_registrationData.value}")
    }

    fun updateStepOneData(email: String, password: String) {
        println("📝 DEBUG: updateStepOneData - Email: '$email', Password: '$password'")
        _registrationData.value = _registrationData.value.copy(
            email = email,
            password = password
        )
        println("📊 DEBUG: Current data after step 1: ${_registrationData.value}")
    }

    fun updateStepTwoData(
        lastName: String,
        firstName: String,
        middleName: String,
        birthDate: String,
        gender: String
    ) {
        println("📝 DEBUG: updateStepTwoData - Name: '$firstName $lastName', BirthDate: '$birthDate', Gender: '$gender'")
        _registrationData.value = _registrationData.value.copy(
            lastName = lastName,
            firstName = firstName,
            middleName = middleName,
            birthDate = birthDate,
            gender = gender
        )
        println("📊 DEBUG: Current data after step 2: ${_registrationData.value}")
    }

    fun updateStepThreeData(
        licenseNumber: String,
        licenseIssueDate: String,
        licensePhotoPath: String?,
        passportPhotoPath: String?,
        profilePhotoPath: String?
    ) {
        println("📝 DEBUG: updateStepThreeData - License: '$licenseNumber', IssueDate: '$licenseIssueDate'")
        _registrationData.value = _registrationData.value.copy(
            licenseNumber = licenseNumber,
            licenseIssueDate = licenseIssueDate,
            licensePhotoPath = licensePhotoPath,
            passportPhotoPath = passportPhotoPath,
            profilePhotoPath = profilePhotoPath
        )
        println("📊 DEBUG: Current data before registration: ${_registrationData.value}")
    }

    fun registerUser() {
        viewModelScope.launch {
            _registrationState.value = RegistrationState.Loading
            try {
                val data = _registrationData.value
                println("🚀 DEBUG: Starting registration with email: ${data.email}")

                val result = registerUseCase(
                    email = data.email,
                    password = data.password,
                    lastName = data.lastName,
                    firstName = data.firstName,
                    middleName = data.middleName,
                    birthDate = data.birthDate,
                    gender = data.gender,
                    licenseNumber = data.licenseNumber,
                    licenseIssueDate = data.licenseIssueDate,
                    licensePhotoPath = data.licensePhotoPath,
                    passportPhotoPath = data.passportPhotoPath,
                    profilePhotoPath = data.profilePhotoPath
                )

                if (result.isSuccess) {
                    println("🎉 DEBUG: Registration SUCCESS for: ${data.email}")
                    _registrationState.value = RegistrationState.Success(result.token)
                } else {
                    println("❌ DEBUG: Registration FAILED: ${result.errorMessage}")
                    _registrationState.value = RegistrationState.Error(
                        result.errorMessage ?: "Ошибка регистрации"
                    )
                }
            } catch (e: Exception) {
                println("💥 DEBUG: Registration EXCEPTION: ${e.message}")
                _registrationState.value = RegistrationState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }

    fun clearData() {
        println("🧹 DEBUG: Clearing registration data")
        _registrationData.value = RegistrationData()
        _registrationState.value = RegistrationState.Idle
        println("📊 DEBUG: Data after clear: ${_registrationData.value}")
    }
}

sealed class RegistrationState {
    object Idle : RegistrationState()
    object Loading : RegistrationState()
    data class Success(val token: String?) : RegistrationState()
    data class Error(val message: String) : RegistrationState()
}